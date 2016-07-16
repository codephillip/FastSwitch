package com.codephillip.game.fastswitch;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by codephillip on 7/16/16.
 */
public class Utils {
    static Context context = ResourceManager.context;
    public static final String HI_POINTS = "high_points";
    public static final String POINTS = "points";
    public static final String HAS_WON_GAME = "has_won_game";
    public static final int CAMERA_WIDTH = 800;
    public static final int CAMERA_HEIGHT = 480;
    public static final float positionX = CAMERA_WIDTH * 0.5f;
    public static final float positionY = CAMERA_HEIGHT * 0.5f;

    public static void storeIntPref(String prefString, int value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(prefString, value);
        editor.apply();
    }

    public static int getHiScore() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(HI_POINTS, 0);
    }

    public static int randInt(int min, int max) {
        int randomNum = min + (int) (Math.random() * ((max - min) + 1));
        Log.d("RANDOM", String.valueOf(randomNum));
        return randomNum;
    }

    public static void saveHasWonGame(String prefString, boolean hasWonGame) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(prefString, hasWonGame);
        editor.apply();
    }

    public static boolean getHasWonGame() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(HAS_WON_GAME, false);
    }


    public static int getPoints() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(POINTS, 0);
    }
}
