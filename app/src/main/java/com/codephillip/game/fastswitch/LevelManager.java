package com.codephillip.game.fastswitch;

import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.music.exception.MusicReleasedException;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;

import java.io.IOException;

/**
 * Created by codephillip on 20/07/16.
 */
public class LevelManager {

    private static final String MUSIC1 = "game_music0.ogg";
    private static final String MUSIC2 = "game_music1.ogg";
    private static final String MUSIC3 = "game_music2.ogg";
    private static final String BACKGROUND1 = "background1.png";
    private static final String BACKGROUND2 = "background2.png";
    private static final String BACKGROUND3 = "background3.png";

    public static void setLevelAttributes() {
        setGameType();
        int targetScore;
        float switchSpeed;
        switch (Utils.getLevel()) {
            case 1:
                Utils.saveIntPref(Utils.GAME_TIME, 30);
                changeBackground(BACKGROUND1);
                changeGameMusic(MUSIC1);
                switchSpeed = 1.1f;
                targetScore = 500;
                break;
            case 2:
                switchSpeed = 1.05f;
                targetScore = 600;
                break;
            case 3:
                switchSpeed = 0.9f;
                targetScore = 800;
                break;
            case 4:
                Utils.saveIntPref(Utils.GAME_TIME, 60);
                changeBackground(BACKGROUND2);
                changeGameMusic(MUSIC2);
                switchSpeed = 0.85f;
                targetScore = 1000;
                break;
            case 5:
                switchSpeed = 0.8f;
                targetScore = 1500;
                break;
            case 6:
                switchSpeed = 0.8f;
                targetScore = 2000;
                break;
            case 7:
                Utils.saveIntPref(Utils.GAME_TIME, 90);
                changeBackground(BACKGROUND3);
                changeGameMusic(MUSIC3);
                switchSpeed = 0.8f;
                targetScore = 2500;
                break;
            case 8:
                switchSpeed = 0.8f;
                targetScore = 3200;
                break;
            case 9:
                switchSpeed = 0.8f;
                targetScore = 3500;
                break;
            case 10:
                switchSpeed = 0.8f;
                targetScore = 4000;
                break;
            default:
                throw new UnsupportedOperationException("Level not found");
        }
        Utils.logAnalyticsLevel("level "+ Utils.getLevel());
        Utils.saveIntPref(Utils.TARGET_SCORE, targetScore);
        Utils.saveSwitchSpeed(Utils.SWITCH_SPEED, switchSpeed);
    }

    private static void setGameType() {
        if (Utils.getGameType() == Utils.ONE_TAP){
            Utils.saveIntPref(Utils.LIVES, 1);
            Utils.saveIntPref(Utils.WRONG_COUNT, 1);
            Utils.saveIncrementLife(Utils.INCREMENT_LIFE, false);
        }
        else{
            Utils.saveIntPref(Utils.LIVES, 5);
            Utils.saveIntPref(Utils.WRONG_COUNT, 3);
            Utils.saveIncrementLife(Utils.INCREMENT_LIFE, true);
        }
    }

    private static void changeGameMusic(String music) {
        //prevents old gameSound from overlapping with the new one
        try {
            ResourceManager.gameSound.stop();
        } catch (MusicReleasedException e) {
            e.printStackTrace();
        }
        try {
            ResourceManager.gameSound = MusicFactory.createMusicFromAsset(ResourceManager.engine.getMusicManager(), ResourceManager.context, "mfx/"+music);
            ResourceManager.gameSound.setLooping(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void changeBackground(String background) {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        ResourceManager.backgroundTextureAtlas = new BitmapTextureAtlas(ResourceManager.engine.getTextureManager(), 1024, 512, TextureOptions.DEFAULT);
        ResourceManager.backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(ResourceManager.backgroundTextureAtlas, ResourceManager.context, background, 0, 0);
        ResourceManager.backgroundTextureAtlas.load();
    }
}
