package com.codephillip.game.fastswitch;

import android.content.Context;
import android.util.Log;

import org.andengine.engine.Engine;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.color.Color;

/**
 * Created by codephillip on 7/15/16.
 */
public class GameOverScene extends Scene {
    private static final String TAG = GameOverScene.class.getSimpleName();
    private Engine engine;
    private Context context;
    private Sprite backgroundSprite;
    private Sprite nextOrRestartSprite, menuSprite;
    private Text pointsText, highPointsText;
    private Text winOrLoseText;

    public GameOverScene(Context context, Engine engine) {
        this.context = context;
        this.engine = engine;
        attachChild(null);
        registerTouchArea(null);
    }

    @Override
    public void attachChild(IEntity pEntity) {
        Log.d(TAG, "attachChild: GAMEOVER finished");
        this.setBackground(new Background(Color.GREEN));

        backgroundSprite = new Sprite(Utils.positionX, Utils.positionY, ResourceManager.backgroundTextureRegion, engine.getVertexBufferObjectManager());

        nextOrRestartSprite = new Sprite(Utils.CAMERA_WIDTH / 2, Utils.CAMERA_HEIGHT / 2 - 90, setNextorRestartSprite(Utils.getHasWonGame()), engine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent superTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (superTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        this.setAlpha(0.5f);
                        break;
                    case TouchEvent.ACTION_UP:
                        this.setAlpha(1.0f);
                        clearChildScene();
                        SceneManager.setCurrentScene(AllScenes.GAME, SceneManager.createGameScene());
                        Log.d(TAG, "onAreaTouched: clicked");
                        break;
                }
                return super.onAreaTouched(superTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        menuSprite = new Sprite(Utils.CAMERA_WIDTH / 2, Utils.CAMERA_HEIGHT / 2 - 170, ResourceManager.menuITextureRegion, engine.getVertexBufferObjectManager()) {
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

        super.attachChild(backgroundSprite);
        super.attachChild(winOrLoseText);
        super.attachChild(pointsText);
        super.attachChild(highPointsText);
        super.attachChild(nextOrRestartSprite);
        super.attachChild(menuSprite);
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

        winOrLoseText = new Text(0, 0, ResourceManager.winOrLoseFont, "YOU WIN", 25, engine.getVertexBufferObjectManager());
        winOrLoseText.setPosition(Utils.CAMERA_WIDTH / 2, Utils.CAMERA_HEIGHT / 2 + 150);
        if (hasWonGame) {
            winOrLoseText.setText("YOU WIN");
        } else {
            winOrLoseText.setText("YOU LOSE");
        }

        pointsText = new Text(0, 0, ResourceManager.menuFont, "Score: 500", 25, engine.getVertexBufferObjectManager());
        pointsText.setPosition(Utils.CAMERA_WIDTH / 2, Utils.CAMERA_HEIGHT / 2 + 60);
        pointsText.setText("Score: " + Utils.getPoints());

        highPointsText = new Text(0, 0, ResourceManager.font, "Hi-Score: 1000", 25, engine.getVertexBufferObjectManager());
        highPointsText.setPosition(Utils.CAMERA_WIDTH / 2, Utils.CAMERA_HEIGHT / 2);
        highPointsText.setText("Hi-Score: " + Utils.getHiScore());
    }

    private ITextureRegion setNextorRestartSprite(boolean hasWonGame) {
        ITextureRegion textureRegion;
        if (hasWonGame) {
            textureRegion = ResourceManager.resumeITextureRegion;
        } else {
            textureRegion = ResourceManager.restartITextureRegion;
        }
        return textureRegion;
    }

    private void setWinOrLoseFontColor(boolean hasWonGame) {
        if (hasWonGame) {
            ResourceManager.winOrLoseFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
                    "fnt/sanchez.ttf", 100, true, android.graphics.Color.YELLOW);
        } else {
            ResourceManager.winOrLoseFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
                    "fnt/sanchez.ttf", 100, true, android.graphics.Color.RED);
        }
        ResourceManager.winOrLoseFont.load();
    }
}
