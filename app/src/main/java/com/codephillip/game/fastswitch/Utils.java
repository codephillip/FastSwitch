package com.codephillip.game.fastswitch;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by codephillip on 7/16/16.
 */
public class Utils {
    private static final String NICKNAME = "nickname";
    private static final String TAG = Utils.class.getSimpleName();
    private static final String PLAYER_ID = "player_id";
    static Context context = ResourceManager.context;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String HI_POINTS = "high_points";
    public static final String POINTS = "points";
    public static final String GAME_TIME_LEFT = "game_time_left";
    public static final String HAS_WON_GAME = "has_won_game";
    public static final String LIVES = "lives";
    public static final String TARGET_SCORE = "target_score";
    public static final String HAS_PAUSED_GAME = "has_paused_game";
    public static final String LEVEL = "level";
    public static final String SWITCH_SPEED = "switch_speed";
    public static final String GAME_TYPE = "game_type";
    public static final String GAME_TIME = "game_time";
    public static final String WRONG_COUNT = "wrong_count";
    public static final String INCREMENT_LIFE = "increment_life";
    public static final int CAMERA_WIDTH = 800;
    public static final int CAMERA_HEIGHT = 480;
    public static final int SPEED_TAP = 1;
    public static final int ONE_TAP = 2;
    //when using positionX and positionX plotting system.
    //The point 0,0 is at the center of the screen like a graph
    public static final float positionX = CAMERA_WIDTH * 0.5f;
    public static final float positionY = CAMERA_HEIGHT * 0.5f;
    public static final String POST = "post";

    public static void saveIntPref(String prefString, int value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(prefString, value);
        editor.apply();
    }

    //all return int methods use saveIntPref()
    public static int getHiScore() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(HI_POINTS, 0);
    }

    public static int getScores() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(POINTS, 0);
    }

    public static int getGameTimeLeft() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(GAME_TIME_LEFT, 30);
    }

    public static int getLives() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(LIVES, 30);
    }

    public static int getTargetScore() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(TARGET_SCORE, 500);
    }

    public static int getLevel() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(LEVEL, 1);
    }

    public static int getGameType() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(GAME_TYPE, 1);
    }

    public static int getGameTime() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(GAME_TIME, 30);
    }

    public static int getWrongCount() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(WRONG_COUNT, 3);
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

    public static void savePausedGame(String prefString, boolean hasPausedGame) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(prefString, hasPausedGame);
        editor.apply();
    }

    public static boolean getPausedGame() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(HAS_PAUSED_GAME, false);
    }

    public static void saveIncrementLife(String prefString, boolean hasPausedGame) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(prefString, hasPausedGame);
        editor.apply();
    }

    public static boolean getIncrementLife() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(INCREMENT_LIFE, true);
    }

    public static void saveSwitchSpeed(String prefString, float switchSpeed) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(prefString, switchSpeed);
        editor.apply();
    }

    public static float getSwitchSpeed() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getFloat(SWITCH_SPEED, 1.1f);
    }

    public static void pauseMusic() {
        ResourceManager.engine.getMusicManager().setMasterVolume(0);
    }

    public static void playMusic() {
        ResourceManager.engine.getMusicManager().setMasterVolume(1.0f);
    }

    public static void resetLevel() {
        Utils.saveIntPref(Utils.LEVEL, 1);
    }

    public static void logAnalyticsScene(String scene) {
        Bundle bundle = new Bundle();
        bundle.putString("Scene", scene);
        ResourceManager.firebaseAnalytics.logEvent("Scene", bundle);
    }

    public static void logAnalyticsLevel(String level) {
        Bundle bundle = new Bundle();
        bundle.putString("Level", level);
        ResourceManager.firebaseAnalytics.logEvent("LEVEL", bundle);
    }

    public static void resumeAds() {
        ResourceManager.mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (ResourceManager.adView != null || !ResourceManager.adView.isShown()) {
                    ResourceManager.adView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public static String getEmail() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("email", "playerName@example.com");
    }

    public static void saveEmail() {
        String email = "nickname";
        for (String emailaddress : getAccountEmails()) {
            email = emailaddress;
        }

        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email", email);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "saveEmail() USER EMAIL " + getAccountEmails().get(0));
    }

    public static ArrayList<String> getAccountEmails() {
        // Get manager
        AccountManager am = AccountManager.get(ResourceManager.context);
        Account[] acc = am.getAccounts();
        ArrayList<String> emails = new ArrayList<String>();
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        for (Account a : acc) {
            Matcher matcher = pattern.matcher(a.name);
            if (matcher.matches() && !emails.contains(a.name)) {
                emails.add(a.name);
            }
        }
        return emails;
    }

    public static String getNickname() {
        return Utils.getEmail().split("@")[0];
    }

    public static void savePlayerId(Long id) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(PLAYER_ID, id);
        editor.apply();
    }

    public static Long getPlayerId() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getLong(PLAYER_ID, 0);
    }
}
