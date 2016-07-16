package com.codephillip.game.fastswitch;

import android.util.Log;

import org.andengine.entity.scene.Scene;

/**
 * Created by codephillip on 7/15/16.
 */
public class SceneManager {
    private static final String TAG = SceneManager.class.getSimpleName();
    private AllScenes currentScene;
    private SceneManager INSTANCE = null;

    public SceneManager getInstance(){
        if (INSTANCE == null) return INSTANCE = new SceneManager();
        return INSTANCE;
    }

    public AllScenes getCurrentScene() {
        return currentScene;
    }

    public static void loadSplashResources() {
        ResourceManager.loadSplashScreenResources();
    }

    public static void loadGameResources() {
        ResourceManager.loadGameScreenResources();
    }

    public static void loadMenuResources() {
        ResourceManager.loadSplashScreenResources();
    }

//    public static Scene createSplashScene() {
//        SplashScene splashScene = new SplashScene(ResourceManager.getInstance().context, ResourceManager.engine);
//        splashScene.attachChild(null);
//        splashScene.registerTouchArea(null);
//        return splashScene;
//    }
//
//    public static Scene createMenuScene() {
//        MenuScene menuScene = new MenuScene(ResourceManager.getInstance().context, ResourceManager.engine);
//        menuScene.attachChild(null);
//        menuScene.registerTouchArea(null);
//        Log.d(TAG, "createMenuScene: finished");
//        return menuScene;
//    }

    public static Scene createGameScene() {
        ResourceManager.getInstance();
        GameScene gameScene = new GameScene(ResourceManager.getInstance().context, ResourceManager.engine);
        gameScene.attachChild(null);
        gameScene.registerTouchArea(null);
        Log.d(TAG, "createMenuScene: finished");
        return gameScene;
    }

    public static void setCurrentScene(AllScenes currentScene, Scene scene) {
        //after we set the enum's current placeholder
        //we're going to tell the engine to go to the
        //newly made current scene.
        switch (currentScene) {
            case SPLASH:
                Log.d(TAG, "setCurrentScene: SPLASH SCENE");
                ResourceManager.engine.setScene(scene);
                //this case we don't really have to worry about
                break;
            case MENU:
                Log.d(TAG, "setCurrentScene: MENU SCENE");
                //once we setup the engine we'll tell it
                //to load the menu scene
                ResourceManager.engine.setScene(scene);
                break;
            case GAME:
                //once we setup the engine we'll tell it
                //to load the game scene
                ResourceManager.engine.setScene(scene);
                break;
            default:
                throw new UnsupportedOperationException("Unknown sceen");
        }
    }
}
