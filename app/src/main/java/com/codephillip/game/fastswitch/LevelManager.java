package com.codephillip.game.fastswitch;

import org.andengine.audio.music.MusicFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;

import java.io.IOException;

/**
 * Created by codephillip on 20/07/16.
 */
public class LevelManager {

    private static float switchSpeed;
    private static int targetScore;
    private static final String MUSIC1 = "game_music0.ogg";
    private static final String MUSIC2 = "game_music1.ogg";
    private static final String MUSIC3 = "game_music2.ogg";
    private static final String BACKGROUND1 = "background1.png";
    private static final String BACKGROUND2 = "background2.png";
    private static final String BACKGROUND3 = "background3.png";

    public LevelManager() {
    }

    public static void setLevelAttributes() {
        switch (Utils.getLevel()) {
            case 1:
                switchSpeed = 1.1f;
                targetScore = 500;
                changeBackground(BACKGROUND2);
                changeGameMusic(MUSIC2);
                break;
            case 2:
                switchSpeed = 1.05f;
                targetScore = 600;
                changeBackground(BACKGROUND3);
                changeGameMusic(MUSIC3);
                break;
            case 3:
                switchSpeed = 0.9f;
                targetScore = 800;
                break;
            case 4:
                switchSpeed = 0.85f;
                targetScore = 1000;
                break;
            case 5:
                switchSpeed = 0.8f;
                targetScore = 1500;
                break;
            case 6:
                switchSpeed = 0.78f;
                targetScore = 2000;
                break;
            case 7:
                switchSpeed = 0.75f;
                targetScore = 2500;
                break;
            case 8:
                switchSpeed = 0.72f;
                targetScore = 3200;
                break;
            case 9:
                switchSpeed = 0.7f;
                targetScore = 3500;
                break;
            case 10:
                switchSpeed = 0.6f;
                targetScore = 4000;
                break;
            default:
                throw new UnsupportedOperationException("Level not found");
        }
        Utils.saveIntPref(Utils.TARGET_SCORE, targetScore);
        Utils.saveSwitchSpeed(Utils.SWITCH_SPEED, switchSpeed);
    }

    private static void changeGameMusic(String music) {
        try {
            ResourceManager.gameSound = MusicFactory.createMusicFromAsset(ResourceManager.engine.getMusicManager(), ResourceManager.context, "mfx/"+music);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResourceManager.gameSound.setLooping(true);
    }

    private static void changeBackground(String background) {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        ResourceManager.backgroundTextureAtlas = new BitmapTextureAtlas(ResourceManager.engine.getTextureManager(), 1024, 512, TextureOptions.DEFAULT);
        ResourceManager.backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(ResourceManager.backgroundTextureAtlas, ResourceManager.context, background, 0, 0);
        ResourceManager.backgroundTextureAtlas.load();
    }
}
