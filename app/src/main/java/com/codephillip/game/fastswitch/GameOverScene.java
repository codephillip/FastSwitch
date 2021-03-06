package com.codephillip.game.fastswitch;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.Engine;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.color.Color;

import java.io.IOException;

/**
 * Created by codephillip on 7/15/16.
 */
public class GameOverScene extends Scene {
    private static final String TAG = GameOverScene.class.getSimpleName();
    private final Engine engine;
    private final Context context;
    private Sprite backgroundSprite;
    private Sprite nextOrRestartSprite, menuSprite;
    private Text scoresText, targetScoreText;
    private Text winOrLoseText;
    private Text levelText;

    public GameOverScene(Context context, Engine engine) {
        this.context = context;
        this.engine = engine;
        attachChild(null);
        registerTouchArea(null);
        Utils.resumeAds();
        logAnalyticsGameOver();
    }

    public static void logAnalyticsGameOver() {
        Bundle bundle = new Bundle();
        bundle.putString("Level", String.valueOf(Utils.getLevel()));
        bundle.putString("Lives", String.valueOf(Utils.getLives()));
        bundle.putString("Score", String.valueOf(Utils.getScores()));
        ResourceManager.firebaseAnalytics.logEvent("GAMEOVER", bundle);
    }

    @Override
    public void attachChild(IEntity pEntity) {
        Log.d(TAG, "attachChild: GAMEOVER finished");

        backgroundSprite = new Sprite(Utils.positionX, Utils.positionY, ResourceManager.backgroundTextureRegion, engine.getVertexBufferObjectManager());
        Sprite overlaySprite = new Sprite(Utils.positionX, Utils.positionY, ResourceManager.overlayTextureRegion, engine.getVertexBufferObjectManager());
        overlaySprite.setColor(Color.BLACK);
        overlaySprite.setAlpha(0.7f);

        nextOrRestartSprite = new Sprite(Utils.CAMERA_WIDTH / 2 + 20, Utils.CAMERA_HEIGHT / 2 - 120, setNextorRestartSprite(Utils.getHasWonGame()), engine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent superTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (superTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        this.setAlpha(0.5f);
                        break;
                    case TouchEvent.ACTION_UP:
                        this.setAlpha(1.0f);
                        clearChildScene();
                        Utils.playMusic();
                        SceneManager.setCurrentScene(AllScenes.OBJECTIVE, SceneManager.createObjectiveScene());
                        Log.d(TAG, "onAreaTouched: clicked");
                        break;
                }
                return super.onAreaTouched(superTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        menuSprite = new Sprite(Utils.CAMERA_WIDTH / 2 + 20, Utils.CAMERA_HEIGHT / 2 - 170, ResourceManager.menuITextureRegion, engine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent superTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (superTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        this.setAlpha(0.5f);
                        break;
                    case TouchEvent.ACTION_UP:
                        this.setAlpha(1.0f);
                        clearChildScene();
                        SceneManager.setCurrentScene(AllScenes.MENU, SceneManager.createMenuScene());
                        Log.d(TAG, "onAreaTouched: clicked");
                        break;
                }
                return super.onAreaTouched(superTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        showStatistics(Utils.getHasWonGame());
        updateHiScore(Utils.getHasWonGame());
        super.attachChild(backgroundSprite);
        super.attachChild(overlaySprite);
        super.attachChild(winOrLoseText);
        super.attachChild(levelText);
        super.attachChild(scoresText);
        super.attachChild(targetScoreText);
        super.attachChild(nextOrRestartSprite);
        super.attachChild(menuSprite);
        playWinOrFailSound();
    }

    private void updateHiScore(boolean hasWonGame) {
        if (hasWonGame) {
            Utils.saveIntPref(Utils.HI_POINTS, Utils.getHiScore()+Utils.getScores());
        }
    }

    private void playWinOrFailSound() {
        Log.d(TAG, "playWinOrFailSound: running");

        try {
            ResourceManager.finalWinSound = MusicFactory.createMusicFromAsset(engine.getMusicManager(), context, "mfx/final_win.ogg");
            ResourceManager.finalFailSound = MusicFactory.createMusicFromAsset(engine.getMusicManager(), context, "mfx/final_fail.ogg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Utils.getHasWonGame()) {
            ResourceManager.finalWinSound.play();
        } else {
            ResourceManager.finalFailSound.play();
        }
    }

    @Override
    public void registerTouchArea(ITouchArea pTouchArea) {
        Log.d(TAG, "registerTouchArea: gameover");
        super.registerTouchArea(nextOrRestartSprite);
        super.registerTouchArea(menuSprite);
    }

    private void showStatistics(boolean hasWonGame) {
        backgroundSprite.detachSelf();

        setWinOrLoseFontColor(hasWonGame);

        levelText = new Text(0, 0, ResourceManager.levelFont, "LEVEL 1", 25, engine.getVertexBufferObjectManager());
        levelText.setPosition(Utils.CAMERA_WIDTH / 2, Utils.CAMERA_HEIGHT / 2 + 90);
        levelText.setText("LEVEL "+Utils.getLevel());

        winOrLoseText = new Text(0, 0, ResourceManager.titleFont, "YOU WIN", 25, engine.getVertexBufferObjectManager());
        winOrLoseText.setPosition(Utils.CAMERA_WIDTH / 2, Utils.CAMERA_HEIGHT / 2 + 150);
        if (hasWonGame) {
            winOrLoseText.setText("YOU WIN");
            Utils.saveIntPref(Utils.LEVEL, Utils.getLevel() + 1);
        } else {
            winOrLoseText.setText("YOU LOSE");
        }

        scoresText = new Text(0, 0, ResourceManager.menuFont, "Score: 500", 25, engine.getVertexBufferObjectManager());
        scoresText.setPosition(Utils.CAMERA_WIDTH / 2, Utils.CAMERA_HEIGHT / 2 + 20);
        scoresText.setText("Score: " + Utils.getScores());

        targetScoreText = new Text(0, 0, ResourceManager.smallMenuFont, "Target: 1000", 25, engine.getVertexBufferObjectManager());
        targetScoreText.setPosition(Utils.CAMERA_WIDTH / 2, Utils.CAMERA_HEIGHT / 2 - 30);
        targetScoreText.setText("Target: " + Utils.getTargetScore());
    }

    private ITextureRegion setNextorRestartSprite(boolean hasWonGame) {
        ITextureRegion textureRegion;
        if (hasWonGame) {
            textureRegion = ResourceManager.nextLevelITextureRegion;
        } else {
            textureRegion = ResourceManager.restartITextureRegion;
        }
        return textureRegion;
    }

    private void setWinOrLoseFontColor(boolean hasWonGame) {
        if (hasWonGame) {
            ResourceManager.titleFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
                    "fnt/sanchez.ttf", 80, true, android.graphics.Color.YELLOW);
        } else {
            ResourceManager.titleFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
                    "fnt/sanchez.ttf", 80, true, android.graphics.Color.RED);
        }
        ResourceManager.titleFont.load();
    }
}
