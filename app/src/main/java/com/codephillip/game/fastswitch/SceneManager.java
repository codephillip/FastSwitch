package com.codephillip.game.fastswitch;

import android.util.Log;

import org.andengine.entity.scene.Scene;

/**
 * Created by codephillip on 7/15/16.
 */
public class SceneManager {
    private static final String TAG = SceneManager.class.getSimpleName();
    private SceneManager INSTANCE = null;

    public SceneManager getInstance(){
        if (INSTANCE == null) 
            return INSTANCE = new SceneManager();
        return INSTANCE;
    }

    public static Scene createSplashScene() {
        SplashScene splashScene = new SplashScene(ResourceManager.context, ResourceManager.engine);
        return splashScene;
    }

    public static Scene createMenuScene() {
        MenuScene menuScene = new MenuScene(ResourceManager.context, ResourceManager.engine);
        Log.d(TAG, "createMenuScene: finished");
        return menuScene;
    }

    public static Scene createGameOverScene() {
        GameOverScene gameOverScene = new GameOverScene(ResourceManager.context, ResourceManager.engine);
        Log.d(TAG, "createMenuScene: finished");
        return gameOverScene;
    }

    public static Scene createGameScene() {
        GameScene gameScene = new GameScene(ResourceManager.context, ResourceManager.engine);
        Log.d(TAG, "createMenuScene: finished");
        return gameScene;
    }

    public static Scene createPauseScene() {
        PauseScene pauseScene = new PauseScene(ResourceManager.context, ResourceManager.engine);
        Log.d(TAG, "createMenuScene: finished");
        return pauseScene;
    }

    public static Scene createInstructionScene() {
        InstructionScene instructionScene = new InstructionScene(ResourceManager.context, ResourceManager.engine);
        Log.d(TAG, "createMenuScene: finished");
        return instructionScene;
    }

    public static Scene createObjectiveScene() {
        ObjectiveScene objectiveScene = new ObjectiveScene(ResourceManager.context, ResourceManager.engine);
        Log.d(TAG, "createMenuScene: finished");
        return objectiveScene;
    }
    
    public static Scene createStageScene() {
        StageScene stageScene = new StageScene(ResourceManager.context, ResourceManager.engine);
        Log.d(TAG, "createMenuScene: finished");
        return stageScene;
    }
    
    public static void setCurrentScene(AllScenes currentScene, Scene scene) {
        //after we set the enum's current placeholder
        //we're going to tell the engine to go to the
        //newly made current scene.
        switch (currentScene) {
            case SPLASH:
                Log.d(TAG, "setCurrentScene: SPLASH SCENE");
                ResourceManager.engine.setScene(scene);
                break;
            case MENU:
                Log.d(TAG, "setCurrentScene: MENU SCENE");
                ResourceManager.engine.setScene(scene);
                break;
            case GAME:
                Log.d(TAG, "setCurrentScene: GAME SCENE");
                ResourceManager.engine.setScene(scene);
                break;
            case GAME0VER:
                Log.d(TAG, "setCurrentScene: GAMEOVER");
                ResourceManager.engine.setScene(scene);
                break;
            case PAUSE:
                Log.d(TAG, "setCurrentScene: PAUSE SCENE");
                ResourceManager.engine.setScene(scene);
                break;
            case INSTRUCTION:
                ResourceManager.engine.setScene(scene);
                break;
            case OBJECTIVE:
                Log.d(TAG, "setCurrentScene: OBJECTIVE SCENE");
                ResourceManager.engine.setScene(scene);
                break;
            case STAGE:
                Log.d(TAG, "setCurrentScene: OBJECTIVE SCENE");
                ResourceManager.engine.setScene(scene);
                break;

            default:
                throw new UnsupportedOperationException("Unknown sceen");
        }
    }
}
