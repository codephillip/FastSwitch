package com.codephillip.game.fastswitch;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import java.io.IOException;

public class ResourceManager {

    private static ResourceManager INSTANCE = null;

    public static Engine engine;
    public static Context context;

    public static BitmapTextureAtlas backgroundTextureAtlas;
    public static ITextureRegion backgroundTextureRegion;

    public BitmapTextureAtlas overlayTextureAtlas;
    public static ITextureRegion overlayTextureRegion;

    public BitmapTextureAtlas explosionTextureAtlas;
    public static ITiledTextureRegion explosionTiledTextureRegion;

    public BitmapTextureAtlas fruitTextureAtlas;
    public static ITiledTextureRegion fruitTiledTextureRegion;

    public BitmapTextureAtlas stageTextureAtlas;
    public static TextureRegion speedTapITextureRegion;
    public static TextureRegion oneTapTiledTextureRegion;;

    public BitmapTextureAtlas menuButtonsTextureAtlas;
    public static ITextureRegion exitITextureRegion;
    public static ITextureRegion playITextureRegion;
    public static ITextureRegion restartITextureRegion;
    public static ITextureRegion resumeITextureRegion;
    public static ITextureRegion menuITextureRegion;
    public static ITextureRegion pauseITextureRegion;
    public static ITextureRegion instructionsITextureRegion;
    public static ITextureRegion nextLevelITextureRegion;
    public static ITextureRegion topPlayersITextureRegion;

    public BitmapTextureAtlas menuButtonsTextureAtlas2;
    public static TextureRegion creditsITextureRegion;
    public static TextureRegion startITextureRegion;
    public static ITextureRegion heartITextureRegion;

    public static Font font, bountyFont;
    public static Font menuFont, smallMenuFont, titleFont, levelFont;
    public static Font instructionFont;
    public static Font pointsFont;
    public static Font topPlayerFont;

    public static Music gameSound, finalWinSound, finalFailSound;
    public static Sound wrongTileSound, rightTileSound, lifeUpSound, lifeDownSound, deathSound, bountySound;
    public static FirebaseAnalytics firebaseAnalytics;
    public static AdView adView;
    public static Activity mainActivity;

    private ResourceManager() {
    }

    public static ResourceManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ResourceManager();
        return INSTANCE;
    }

    public void setup(final Engine pEngine, MainActivity mMainActivity, final Context pContext, FirebaseAnalytics mFirebaseAnalytics, AdView mAdView, final float pCameraWidth, final float pCameraHeight) {
        engine = pEngine;
        ResourceManager.context = pContext;
        firebaseAnalytics = mFirebaseAnalytics;
        adView = mAdView;
        mainActivity = mMainActivity;
        logAnalyticsEvent(mFirebaseAnalytics);
    }

    private void logAnalyticsEvent(FirebaseAnalytics mFirebaseAnalytics) {
        Bundle bundle = new Bundle();
        bundle.putString("Scene", "MenuScene");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    // Loads all game resources.
    public static void loadGameScreenResources() {
        getInstance().loadGameTextures();
        getInstance().loadSounds();
        getInstance().loadFonts();
    }

    private void loadGameTextures() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        menuButtonsTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 202, 457, TextureOptions.BILINEAR);
        menuButtonsTextureAtlas.load();

        BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuButtonsTextureAtlas, context, "button_sprite2.png", 0, 0);
        exitITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas, 2, 1, 202, 60);
        int value = 6;
        instructionsITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas, 2, 60 - value, 202, 60);
        menuITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas, 2, 118 - value, 202, 60);
        nextLevelITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas, 2, 176 - value, 202, 60);
        playITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas, 2, 234 - value, 202, 60);
        restartITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas, 2, 292 - value, 202, 60);
        resumeITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas, 2, 350 - value, 202, 60);
        topPlayersITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas, 2, 408 - value, 202, 60);

        //sprite2
        menuButtonsTextureAtlas2 = new BitmapTextureAtlas(engine.getTextureManager(), 528, 65, TextureOptions.BILINEAR);
        menuButtonsTextureAtlas2.load();
        BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuButtonsTextureAtlas2, context, "button_sprite1.png", 0, 0);
        //extractFromTexture(x then y,...)
        creditsITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas2, 2, 2, 210, 60);
        startITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas2, 317, 2, 210, 60);
        pauseITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas2, 250, 2, 60, 60);
        heartITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas2, 215, 4, 32, 32);

        backgroundTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 1024, 512, TextureOptions.DEFAULT);
        backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureAtlas, context, "background1.png", 0, 0);
        backgroundTextureAtlas.load();

        overlayTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 775, 431, TextureOptions.DEFAULT);
        overlayTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(overlayTextureAtlas, context, "overlay.png", 0, 0);
        overlayTextureAtlas.load();

        fruitTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 900, 500, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        fruitTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(fruitTextureAtlas, context, "spritesheet1.png", 0, 0, 4, 3);
        fruitTextureAtlas.load();

        explosionTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 768, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        explosionTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(explosionTextureAtlas, context, "explosion.png", 0, 0, 3, 4);
        explosionTextureAtlas.load();

        stageTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 500, 190, TextureOptions.BILINEAR);
        stageTextureAtlas.load();

        BitmapTextureAtlasTextureRegionFactory.createFromAsset(stageTextureAtlas, context, "stageButtons.png", 0, 0);
        speedTapITextureRegion = TextureRegionFactory.extractFromTexture(stageTextureAtlas, 2, 1, 248, 188);
        oneTapTiledTextureRegion = TextureRegionFactory.extractFromTexture(stageTextureAtlas, 248, 1, 248, 188);
    }

    private void loadSounds() {
        if (gameSound == null) {
            try {
                gameSound = MusicFactory.createMusicFromAsset(engine.getMusicManager(), context, "mfx/game_music0.ogg");
                wrongTileSound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), context, "mfx/wrong_tile.ogg");
                rightTileSound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), context, "mfx/right_tile.ogg");
                lifeUpSound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), context, "mfx/life_up.ogg");
                bountySound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), context, "mfx/bounty.ogg");
                lifeDownSound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), context, "mfx/death.ogg");
                deathSound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), context, "mfx/death.ogg");
                finalWinSound = MusicFactory.createMusicFromAsset(engine.getMusicManager(), context, "mfx/final_win.ogg");
                finalFailSound = MusicFactory.createMusicFromAsset(engine.getMusicManager(), context, "mfx/final_fail.ogg");
                gameSound.setLooping(true);
                wrongTileSound.setVolume(0.5f);
                rightTileSound.setVolume(0.5f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadFonts() {
        font = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
                "fnt/pipedream.ttf", 25, true, Color.BLACK);
        font.load();

        bountyFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
                "fnt/pipedream.ttf", 80, true, Color.GREEN);
        bountyFont.load();

        menuFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
                "fnt/pipedream.ttf", 60, true, Color.WHITE);
        menuFont.load();

        levelFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
                "fnt/sanchez.ttf", 45, true, Color.WHITE);
        levelFont.load();

        smallMenuFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
                "fnt/pipedream.ttf", 46, true, Color.WHITE);
        smallMenuFont.load();

        instructionFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
                "fnt/Roboto_Regular.ttf", 30, true, Color.WHITE);
        instructionFont.load();

        titleFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
                "fnt/sanchez.ttf", 70, true, android.graphics.Color.YELLOW);
        titleFont.load();

        pointsFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
                "fnt/pipedream.ttf", 30, true, android.graphics.Color.WHITE);
        pointsFont.load();

        topPlayerFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
                "fnt/Roboto_Regular.ttf", 20, true, Color.WHITE);
        topPlayerFont.load();
    }
}