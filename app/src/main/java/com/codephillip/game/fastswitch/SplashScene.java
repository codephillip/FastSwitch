//package com.codephillip.game.fastswitch;
//
//import android.content.Context;
//import android.util.Log;
//
//import org.andengine.engine.Engine;
//import org.andengine.engine.handler.IUpdateHandler;
//import org.andengine.engine.handler.timer.ITimerCallback;
//import org.andengine.engine.handler.timer.TimerHandler;
//import org.andengine.entity.IEntity;
//import org.andengine.entity.scene.ITouchArea;
//import org.andengine.entity.scene.Scene;
//import org.andengine.entity.sprite.Sprite;
//import org.andengine.input.touch.TouchEvent;
//
///**
// * Created by codephillip on 7/15/16.
// */
//public class SplashScene extends Scene {
//    private static final String TAG = SplashScene.class.getSimpleName();
//    Engine engine;
//    Sprite sprite;
//    Context context;
//
//    public SplashScene(Context context, Engine engine) {
//        this.context = context;
//        this.engine = engine;
//    }
//
//    @Override
//    public void attachChild(IEntity pEntity) {
//        sprite = new Sprite(600 / 2, 480 / 2, ResourceManager.splashTextureRegion, engine.getVertexBufferObjectManager()){
//            @Override
//            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
//                switch (pSceneTouchEvent.getAction()) {
//                    case TouchEvent.ACTION_DOWN:
//                        this.setAlpha(0.5f);
//                        break;
//                    case TouchEvent.ACTION_UP:
//                        this.setAlpha(1.0f);
//                        updateUI();
//                        Log.d(TAG, "onAreaTouched: clicked");
//                        break;
//                }
//                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
//            }
//        };
//        super.attachChild(sprite);
//    }
//
//    @Override
//    public void registerTouchArea(ITouchArea pTouchArea) {
//        super.registerTouchArea(sprite);
//    }
//
//    @Override
//    public void registerUpdateHandler(IUpdateHandler pUpdateHandler) {
//        super.registerUpdateHandler(pUpdateHandler);
//    }
//
//    //creates a delay of 3 seconds
//    public void updateUI(){
//        this.registerUpdateHandler(new TimerHandler(3f, true, new ITimerCallback() {
//            @Override
//            public void onTimePassed(TimerHandler pTimerHandler) {
//                //stops the timer
//                unregisterUpdateHandler(pTimerHandler);
//                SceneManager.loadMenuResources();
//                SceneManager.setCurrentScene(AllScenes.MENU, SceneManager.createMenuScene());
//            }
//        }));
//    }
//}
