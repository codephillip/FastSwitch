package com.codephillip.game.fastswitch;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.adt.color.Color;

import java.io.IOException;

public class MainActivity extends BaseGameActivity {

    private Camera mCamera;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;


    @Override
    public EngineOptions onCreateEngineOptions() {
        Camera camera = new Camera(0, 0, WIDTH, HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(),
                camera);
        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {
        Scene scene = new Scene();
        scene.setBackground(new Background(Color.BLUE));
        pOnCreateSceneCallback.onCreateSceneFinished(scene);
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }
}
