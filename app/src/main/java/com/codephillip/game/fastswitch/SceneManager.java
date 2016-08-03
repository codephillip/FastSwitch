package com.codephillip.game.fastswitch;

import org.andengine.entity.scene.Scene;

/**
 * Created by codephillip on 7/15/16.
 */
public class SceneManager {
    public static Scene createSplashScene() {
        return new SplashScene(ResourceManager.context, ResourceManager.engine);
    }

    public static Scene createMenuScene() {
        return new MenuScene(ResourceManager.context, ResourceManager.engine);
    }

    public static Scene createGameOverScene() {
        return new GameOverScene(ResourceManager.context, ResourceManager.engine);
    }

    public static Scene createGameScene() {
        return new GameScene(ResourceManager.context, ResourceManager.engine);
    }

    public static Scene createPauseScene() {
        return new PauseScene(ResourceManager.context, ResourceManager.engine);
    }

    public static Scene createInstructionScene() {
        return new InstructionScene(ResourceManager.context, ResourceManager.engine);
    }

    public static Scene createObjectiveScene() {
        return new ObjectiveScene(ResourceManager.context, ResourceManager.engine);
    }

    public static Scene createStageScene() {
        return new StageScene(ResourceManager.context, ResourceManager.engine);
    }

    public static Scene createTopPlayersScene() {
        return new TopPlayersScene(ResourceManager.context, ResourceManager.engine);
    }

    public static void setCurrentScene(AllScenes currentScene, Scene scene) {
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
