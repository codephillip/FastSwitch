package com.codephillip.game.fastswitch;

import android.content.Context;

import org.andengine.engine.Engine;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.color.Color;

/**
 * Created by codephillip on 7/15/16.
 */
public class StageScene extends Scene {
    private final Engine engine;
    private final Context context;
    private Sprite speedTapSprite;
    private Sprite oneTapSprite;

    public StageScene(Context context, Engine engine) {
        this.context = context;
        this.engine = engine;
        attachChild(null);
        registerTouchArea(null);
    }

    @Override
    public void attachChild(IEntity pEntity) {
        Sprite backgroundSprite = new Sprite(Utils.positionX, Utils.positionY, ResourceManager.backgroundTextureRegion, engine.getVertexBufferObjectManager());

        Sprite overlaySprite = new Sprite(Utils.positionX, Utils.positionY, ResourceManager.overlayTextureRegion, engine.getVertexBufferObjectManager());
        overlaySprite.setColor(Color.BLACK);
        overlaySprite.setAlpha(0.7f);

        speedTapSprite = new Sprite(Utils.CAMERA_WIDTH / 2 - 150, Utils.CAMERA_HEIGHT / 2  - 40, ResourceManager.speedTapITextureRegion, engine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent superTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (superTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        this.setAlpha(0.5f);
                        break;
                    case TouchEvent.ACTION_UP:
                        this.setAlpha(1.0f);
                        clearChildScene();
                        Utils.resetLevel();
                        Utils.saveIntPref(Utils.GAME_TYPE, Utils.SPEED_TAP);
                        SceneManager.setCurrentScene(AllScenes.OBJECTIVE, SceneManager.createObjectiveScene());
                        break;
                }
                return super.onAreaTouched(superTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        oneTapSprite = new Sprite(Utils.CAMERA_WIDTH / 2 + 150, Utils.CAMERA_HEIGHT / 2 - 40, ResourceManager.oneTapTiledTextureRegion, engine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent superTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (superTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        this.setAlpha(0.5f);
                        break;
                    case TouchEvent.ACTION_UP:
                        this.setAlpha(1.0f);
                        clearChildScene();
                        Utils.saveIntPref(Utils.GAME_TYPE, Utils.ONE_TAP);
                        SceneManager.setCurrentScene(AllScenes.OBJECTIVE, SceneManager.createObjectiveScene());
                        break;
                }
                return super.onAreaTouched(superTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        Text titleText = new Text(0, 0, ResourceManager.titleFont, "GAME TYPE", 25, engine.getVertexBufferObjectManager());
        titleText.setPosition(Utils.CAMERA_WIDTH / 2, Utils.CAMERA_HEIGHT / 2 + 150);

        Text speedTapText = new Text(0, 0, ResourceManager.pointsFont, "Speed Tap", 50, engine.getVertexBufferObjectManager());
        speedTapText.setPosition(Utils.CAMERA_WIDTH / 2 - 150, Utils.CAMERA_HEIGHT / 2  - 150);

        Text oneTapText = new Text(0, 0, ResourceManager.pointsFont, "One Tap", 50, engine.getVertexBufferObjectManager());
        oneTapText.setPosition(Utils.CAMERA_WIDTH / 2 + 150, Utils.CAMERA_HEIGHT / 2 - 150);

        super.attachChild(backgroundSprite);
        super.attachChild(overlaySprite);
        super.attachChild(titleText);
        super.attachChild(speedTapText);
        super.attachChild(oneTapText);
        super.attachChild(speedTapSprite);
        super.attachChild(oneTapSprite);
    }

    @Override
    public void registerTouchArea(ITouchArea pTouchArea) {
        super.registerTouchArea(speedTapSprite);
        super.registerTouchArea(oneTapSprite);
    }
}
