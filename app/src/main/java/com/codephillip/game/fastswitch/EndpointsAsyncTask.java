package com.codephillip.game.fastswitch;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.util.Log;

import com.codephillip.backend.topPlayersApi.TopPlayersApi;
import com.codephillip.backend.topPlayersApi.model.TopPlayers;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by codephillip on 6/8/16.
 */
public class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, List<TopPlayers>> {
    private static final String TAG = EndpointsAsyncTask.class.getSimpleName();
    private static TopPlayersApi myApiService = null;
    private Context context;

    public EndpointsAsyncTask(Context context) {
        this.context = context;
    }


    @Override
    protected List<TopPlayers> doInBackground(Pair<Context, String>... params) {
        if(myApiService == null) {
            //Only do this once
            //devappserver
            TopPlayersApi.Builder builder = new TopPlayersApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
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

        try {
            //delete first item in server DB
            //List<Student> z = myApiService.listStudent().execute().getItems();
            //long id = z.get(0).getId();
            //myApiService.removeStudent(id).execute();
            //delete
            //myApiService.removeStudent(5076495651307520L).execute();
            //update
            //String x = String.valueOf(myApiService.updateStudent(new Student().setId(5066549580791808L).setWho("mememe").setWhat("deleted and gone")).execute());
            //post
            //String x = String.valueOf(myApiService.insertStudent(new Student().setWho("Makarov2").setWhat("Best in Call of duty")).execute());
            //multiple post
            //Log.d(TAG, "doInBackground: "+x);
//            return null;
            return myApiService.list().execute().getItems();
        } catch (IOException e) {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    protected void onPostExecute(List<TopPlayers> topPlayersList) {
        for (TopPlayers topPlayer : topPlayersList) {
            Log.d(TAG, "onPostExecute() returned: " + topPlayer.getName() + " : " + topPlayer.getEmail());
        }
    }
}