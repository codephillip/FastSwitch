package com.codephillip.game.fastswitch;

import android.content.Context;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

/**
 * Created by codephillip on 7/15/16.
 */
public class MenuScene extends Scene {
    private static final String TAG = MenuScene.class.getSimpleName();
    private Engine engine;
    private Context context;
    private Sprite playSprite;
    private Sprite backgroundSprite;

    public MenuScene(Context context, Engine engine) {
        this.context = context;
        this.engine = engine;
        attachChild(null);
        registerTouchArea(null);
    }

    @Override
    public void attachChild(IEntity pEntity) {
        backgroundSprite = new Sprite(Utils.positionX, Utils.positionY, ResourceManager.backgroundTextureRegion, engine.getVertexBufferObjectManager());

        playSprite = new Sprite(Utils.CAMERA_WIDTH / 2, Utils.CAMERA_HEIGHT / 2 - 90, ResourceManager.playITextureRegion, engine.getVertexBufferObjectManager()) {
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
                        break;
                }
                return super.onAreaTouched(superTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        super.attachChild(backgroundSprite);
        super.attachChild(playSprite);
    }

    @Override
    public void registerTouchArea(ITouchArea pTouchArea) {
        super.registerTouchArea(playSprite);
    }

    @Override
    public void registerUpdateHandler(IUpdateHandler pUpdateHandler) {
        super.registerUpdateHandler(pUpdateHandler);
    }
}
