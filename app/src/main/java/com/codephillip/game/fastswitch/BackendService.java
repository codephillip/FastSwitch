package com.codephillip.game.fastswitch;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.codephillip.backend.topPlayersApi.TopPlayersApi;
import com.codephillip.backend.topPlayersApi.model.TopPlayers;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.List;

public class BackendService extends IntentService {

    private static final String TAG = BackendService.class.getSimpleName();
    private static final int NOTIFICATION_ID = 400;
    private TopPlayersApi myApiService;
    private static final long TIME_PASSED = 1000 * 60 * 60 * 48;

    public BackendService() {
        super("BackendService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: started service");
        if (intent.getBooleanExtra("notification", false))
            startNotification();
        try {
            startConnection();
            if (intent.getExtras().getBoolean(Utils.POST, false)) {
                doPostRequest();
            } else {
                doGetRequest();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startConnection() {
        if (myApiService == null) {
            //devserver
//            TopPlayersApi.Builder builder = new TopPlayersApi.Builder(AndroidHttp.newCompatibleTransport(),
//                    new AndroidJsonFactory(), null)
//                    .setRootUrl("http://192.168.57.1:8080/_ah/api/")
//                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                        @Override
//                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//                            abstractGoogleClientRequest.setDisableGZipContent(true);
//                        }
//                    });

            //online server
            TopPlayersApi.Builder builder = new TopPlayersApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://dulcet-timing-133609.appspot.com/_ah/api/");

            myApiService = builder.build();
        }
    }

    private void doPostRequest() throws IOException {
        Log.d(TAG, "doPostRequest: startedPost");
        TopPlayers topPlayers = null;
        try {
            if (Utils.getPlayerId() == 0) {
                topPlayers = myApiService.insert(new TopPlayers().setName(Utils.getNickname()).setEmail(Utils.getEmail()).setPoints(Utils.getHiScore())).execute();
            } else {
                try {
                    topPlayers = (myApiService.update(Utils.getPlayerId(), new TopPlayers().setName(Utils.getNickname()).setEmail(Utils.getEmail()).setPoints(Utils.getHiScore())).execute());
                } catch (IOException e) {
                    e.printStackTrace();
                    topPlayers = myApiService.insert(new TopPlayers().setName(Utils.getNickname()).setEmail(Utils.getEmail()).setPoints(Utils.getHiScore())).execute();
                }
            }
            startBroadcastReceiver(topPlayers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startBroadcastReceiver(TopPlayers topPlayers) {
        if (topPlayers != null) {
            Utils.savePlayerId(topPlayers.getId());
            Log.d(TAG, "doPostRequest() POST: " + topPlayers.getId() + topPlayers.getName());
        }
        new MyReceiver().onReceive(this, new Intent());
    }

    private void doGetRequest() {
        try {
            List<TopPlayers> topPlayers = myApiService.list().execute().getItems();
            Utils.topPlayers = topPlayers;
            for (TopPlayers topPlayer : topPlayers) {
                Log.d(TAG, "doGetRequest() GET: " + topPlayer.getName() + "#" + topPlayer.getEmail() + "#" + topPlayer.getPoints());
                if (topPlayer.getEmail().equals(Utils.getEmail())) {
                    Utils.savePlayerId(topPlayer.getId());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startNotification() {
        Log.d(TAG, "startNotification: started");
        Context context = this.getBaseContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        long lastSync = prefs.getLong(Utils.LAST_NOTIFICATION, 0);

        if (System.currentTimeMillis() - lastSync >= TIME_PASSED) {
            int iconId = R.mipmap.ic_launcher;
            String title = context.getString(R.string.app_name);
            String contentText = "Time to crunch fruits";

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(iconId)
                            .setContentTitle(title)
                            .setContentText(contentText);

            Intent resultIntent = new Intent(context, MainActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);

            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

            Uri notification = Uri.parse("content://settings/system/notification_sound");
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.play();

            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putLong(Utils.LAST_NOTIFICATION, System.currentTimeMillis());
            editor.apply();
        }
    }
}
