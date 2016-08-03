package com.codephillip.game.fastswitch;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.codephillip.backend.topPlayersApi.TopPlayersApi;
import com.codephillip.backend.topPlayersApi.model.TopPlayers;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.List;

public class BackendService extends IntentService {

    private static final String TAG = BackendService.class.getSimpleName();
    private TopPlayersApi myApiService;

    public BackendService() {
        super("BackendService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: started service");
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
}
