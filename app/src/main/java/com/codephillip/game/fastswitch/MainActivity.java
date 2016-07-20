package com.codephillip.game.fastswitch;

import android.os.Bundle;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import java.io.IOException;

public class MainActivity extends BaseGameActivity {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;

    @Override
    public EngineOptions onCreateEngineOptions() {
        Camera camera = new Camera(0, 0, WIDTH, HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camera);
        engineOptions.getAudioOptions().setNeedsSound(true);
        engineOptions.getAudioOptions().setNeedsMusic(true);
//        // Turn on MultiTouch
        engineOptions.getTouchOptions().setNeedsMultiTouch(true);
//        // Set the Wake Lock options to prevent the engine from dumping textures when focus changes.
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        return engineOptions;
    }

    @Override
    public synchronized void onPauseGame() {
        super.onPauseGame();
    }

    @Override
    public synchronized void onResumeGame() {
        super.onResumeGame();
    }

    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
        ResourceManager.getInstance().setup(this.getEngine(), this.getApplicationContext(), WIDTH, HEIGHT);
        ResourceManager.loadGameScreenResources();
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {
        pOnCreateSceneCallback.onCreateSceneFinished(SceneManager.createSplashScene());
    }

    @Override
    public void onPopulateScene(final Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }
}
