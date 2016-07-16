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
import org.andengine.util.adt.color.Color;

/**
 * Created by codephillip on 7/15/16.
 */
public class GameOverScene extends Scene {  private static final String TAG = GameOverScene.class.getSimpleName();
    Engine engine;
    Sprite sprite;
    Context context;
    private Sprite sprite2;
    private Text timeLeftText, livesText, pointsText, highPointsText, bountyText;


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
//        sprite = new Sprite(800 / 2+300, 480 / 2+300, ResourceManager.splashTextureRegion, engine.getVertexBufferObjectManager()){
//            @Override
//            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
//                switch (pSceneTouchEvent.getAction()) {
//                    case TouchEvent.ACTION_DOWN:
//                        this.setAlpha(0.5f);
//                        break;
//                    case TouchEvent.ACTION_UP:
//                        this.setAlpha(1.0f);
////                        updateUI();
//                        Log.d(TAG, "onAreaTouched: clicked: MENU CLASS");
//                        SceneManager.loadSplashResources();
//                        SceneManager.setCurrentScene(AllScenes.SPLASH, SceneManager.createSplashScene());
//                        break;
//                }
//                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
//            }
//        };
//        sprite2 = new Sprite(800 / 2 + 100, 480 / 2 + 500, ResourceManager.splashTextureRegion, engine.getVertexBufferObjectManager()) {
//            @Override
//            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
//                switch (pSceneTouchEvent.getAction()) {
//                    case TouchEvent.ACTION_DOWN:
//                        this.setAlpha(0.5f);
//                        break;
//                    case TouchEvent.ACTION_UP:
//                        this.setAlpha(1.0f);
////                        updateUI();
//                        Log.d(TAG, "onAreaTouched: clicked: MENU CLASS");
//                        SceneManager.loadSplashResources();
//                        SceneManager.setCurrentScene(AllScenes.SPLASH, SceneManager.createSplashScene());
//                        break;
//                }
//                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
//            }
//        };
//        super.attachChild(sprite2);
//        super.attachChild(sprite);
    }

    @Override
    public void registerTouchArea(ITouchArea pTouchArea) {
        Log.d(TAG, "registerTouchArea: gameover");
//        super.registerTouchArea(sprite);
    }

//    public void updateUI(){
//        //5f is 5 seconds
//        this.registerUpdateHandler(new TimerHandler(5f, true, new ITimerCallback() {
//            @Override
//            public void onTimePassed(TimerHandler pTimerHandler) {
//                unregisterUpdateHandler(pTimerHandler);
//                SceneManager.loadSplashResources();
//                SceneManager.setCurrentScene(AllScenes.SPLASH, SceneManager.createSplashScene());
//            }
//        }));
//    }
}
