package com.codephillip.game.fastswitch;

import android.content.Context;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

/**
 * Created by codephillip on 7/15/16.
 */
public class SplashScene extends Scene {
    private Engine engine;
    private Context context;
    private Sprite playSprite;
    private Sprite backgroundSprite;

    public SplashScene(Context context, Engine engine) {
        this.context = context;
        this.engine = engine;
        attachChild(null);
        registerUpdateHandler(null);
    }

    @Override
    public void attachChild(IEntity pEntity) {
        backgroundSprite = new Sprite(Utils.positionX, Utils.positionY, ResourceManager.backgroundTextureRegion, engine.getVertexBufferObjectManager());

        Text highPointsText = new Text(0, 0, ResourceManager.font, "LOADING...", 15, engine.getVertexBufferObjectManager());
        highPointsText.setPosition(Utils.CAMERA_WIDTH / 2, Utils.CAMERA_HEIGHT / 2);

        super.attachChild(backgroundSprite);
        super.attachChild(highPointsText);
    }

    @Override
    public void registerUpdateHandler(IUpdateHandler pUpdateHandler) {
        super.registerUpdateHandler(new TimerHandler(3f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                //stops the timer
                unregisterUpdateHandler(pTimerHandler);
                SceneManager.setCurrentScene(AllScenes.MENU, SceneManager.createMenuScene());
            }
        }));
    }
}
