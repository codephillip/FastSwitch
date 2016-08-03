package com.codephillip.game.fastswitch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = MyReceiver.class.getSimpleName();

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: STARTED");
        ResourceManager.mainActivity.startService(new Intent(ResourceManager.mainActivity.getBaseContext(), BackendService.class).putExtra(Utils.POST, false));
        clearAbortBroadcast();
    }
}
