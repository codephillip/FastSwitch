package com.codephillip.game.fastswitch;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.codephillip.backend.topPlayersApi.TopPlayersApi;
import com.codephillip.backend.topPlayersApi.model.TopPlayers;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

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
            TopPlayersApi.Builder builder = new TopPlayersApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://192.168.57.1:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            //online server
//            StudentTopPlayers.Builder builder = new StudentTopPlayers.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
//                    .setRootUrl("https://TopPlayerssapp-1007.appspot.com/_ah/api/");

            myApiService = builder.build();
        }
    }

    private void doPostRequest() throws IOException {
        TopPlayers topPlayers;

        try {
            if (Utils.getPlayerId() == 0) {
                topPlayers = myApiService.insert(new TopPlayers().setName(Utils.getNickname()).setEmail(Utils.getEmail()).setPoints(5555)).execute();
            } else {
                topPlayers = (myApiService.update(Utils.getPlayerId(), new TopPlayers().setName(Utils.getNickname()).setEmail(Utils.getEmail()).setPoints(7777)).execute());
            }
            Utils.savePlayerId(topPlayers.getId());
            new MyReceiver().onReceive(this, new Intent());
            Log.d(TAG, "doPostRequest() POST: " + topPlayers.getId());
        } catch (Exception e){
            e.printStackTrace();
        }
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
