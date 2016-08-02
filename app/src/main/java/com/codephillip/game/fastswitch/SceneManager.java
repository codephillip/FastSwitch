package com.codephillip.game.fastswitch;

import org.andengine.entity.scene.Scene;

/**
 * Created by codephillip on 7/15/16.
 */
public class SceneManager {
    private static final String TAG = SceneManager.class.getSimpleName();
    private SceneManager INSTANCE = null;

    public SceneManager getInstance() {
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
        return menuScene;
    }

    public static Scene createGameOverScene() {
        GameOverScene gameOverScene = new GameOverScene(ResourceManager.context, ResourceManager.engine);
        return gameOverScene;
    }

    public static Scene createGameScene() {
        GameScene gameScene = new GameScene(ResourceManager.context, ResourceManager.engine);
        return gameScene;
    }

    public static Scene createPauseScene() {
        PauseScene pauseScene = new PauseScene(ResourceManager.context, ResourceManager.engine);
        return pauseScene;
    }

    public static Scene createInstructionScene() {
        InstructionScene instructionScene = new InstructionScene(ResourceManager.context, ResourceManager.engine);
        return instructionScene;
    }

    public static Scene createObjectiveScene() {
        ObjectiveScene objectiveScene = new ObjectiveScene(ResourceManager.context, ResourceManager.engine);
        return objectiveScene;
    }

    public static Scene createStageScene() {
        StageScene stageScene = new StageScene(ResourceManager.context, ResourceManager.engine);
        return stageScene;
    }

    public static Scene createTopPlayersScene() {
        TopPlayersScene topPlayersScene = new TopPlayersScene(ResourceManager.context, ResourceManager.engine);
        return topPlayersScene;
    }

    public static void setCurrentScene(AllScenes currentScene, Scene scene) {
        //after we set the enum's current placeholder
        //we're going to tell the engine to go to the
        //newly made current scene.
        switch (currentScene) {
            case SPLASH:
                ResourceManager.engine.setScene(scene);
                break;
            case MENU:
                ResourceManager.engine.setScene(scene);
                break;
            case GAME:
                ResourceManager.engine.setScene(scene);
                break;
            case GAME0VER:
                ResourceManager.engine.setScene(scene);
                break;
            case PAUSE:
                ResourceManager.engine.setScene(scene);
                break;
            case INSTRUCTION:
                ResourceManager.engine.setScene(scene);
                break;
            case OBJECTIVE:
                ResourceManager.engine.setScene(scene);
                break;
            case STAGE:
                ResourceManager.engine.setScene(scene);
                break;
            case TOP_PLAYERS:
                ResourceManager.engine.setScene(scene);
                break;

            default:
                throw new UnsupportedOperationException("Unknown sceen");
        }
    }
}
