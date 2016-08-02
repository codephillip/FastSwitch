package com.codephillip.game.fastswitch;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.view.RenderSurfaceView;
import org.andengine.ui.activity.BaseGameActivity;

import java.io.IOException;

public class MainActivity extends BaseGameActivity {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;
    private static final String TAG = MainActivity.class.getSimpleName();

    private FirebaseAnalytics mFirebaseAnalytics;
    private AdView adView;

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
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
        ResourceManager.getInstance().setup(this.getEngine(), this, this.getApplicationContext(), mFirebaseAnalytics, adView, WIDTH, HEIGHT);
        ResourceManager.loadGameScreenResources();
        Utils.saveEmail();
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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onSetContentView() {
        final RelativeLayout relativeLayout = new RelativeLayout(this);
        final FrameLayout.LayoutParams relativeLayoutLayoutParams = new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        this.mRenderSurfaceView = new RenderSurfaceView(this);
        this.mRenderSurfaceView.setRenderer(this.mEngine, this);
        final android.widget.RelativeLayout.LayoutParams surfaceViewLayoutParams = new RelativeLayout.LayoutParams(BaseGameActivity.createSurfaceViewLayoutParams());
        surfaceViewLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        relativeLayout.addView(this.mRenderSurfaceView, surfaceViewLayoutParams);
        FrameLayout frameLayout = new FrameLayout(this);
        FrameLayout.LayoutParams fparams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        adView = new AdView(this);
        final FrameLayout.LayoutParams adViewLayoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);

        adView.setAdUnitId("ca-app-pub-1319732109388212/1481366283");
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.refreshDrawableState();

        //TODO deactivate testDevice on release
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .build();
//
//        adView.loadAd(adRequest);

        Log.d(TAG, "onSetContentView() returned: " + "settingup ads");

        frameLayout.addView(adView, adViewLayoutParams);
        relativeLayout.addView(frameLayout, fparams);
        this.setContentView(relativeLayout, relativeLayoutLayoutParams);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
