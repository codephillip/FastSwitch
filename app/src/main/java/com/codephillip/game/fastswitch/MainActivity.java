package com.codephillip.game.fastswitch;

import android.util.Log;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.adt.color.Color;

import java.io.IOException;

public class MainActivity extends BaseGameActivity {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;
    private static final String TAG = "MAIN";

    private float initialX = 165;
    private float initialY = 120;
    private final int RECTANGLE_DIMENSIONS = 200;
    private static Rectangle rectangle1, rectangle2, rectangle3, rectangle4, rectangle5, rectangle6;

    private BitmapTextureAtlas backgroundTextureAtlas;
    private ITextureRegion backgroundTextureRegion;
    private Sprite backgroundSprite;
    private static final Color[] COLOUR = {
            Color.GREEN, Color.BLACK, Color.BLUE, Color.YELLOW, Color.RED, Color.CYAN
    };
    private Music gameSound;
    private Sound wrongTileSound;
    private int count=120;
    private double colourNumber = 1.0;
    //todo create enum of 6 colours and switch to respond

    @Override
    public EngineOptions onCreateEngineOptions() {
        Camera camera = new Camera(0, 0, WIDTH, HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(),
                camera);
        engineOptions.getAudioOptions().setNeedsSound(true);
        engineOptions.getAudioOptions().setNeedsMusic(true);
        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        backgroundTextureAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 1024, 1024, TextureOptions.DEFAULT);
        backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureAtlas, this, "parallax_background_layer_back.png", 0, 0);
        backgroundTextureAtlas.load();

        try {
            gameSound = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "mfx/game_music.ogg");
            wrongTileSound = SoundFactory.createSoundFromAsset(getEngine().getSoundManager(), this, "mfx/wrong_tile.ogg");
            gameSound.setLooping(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {
        Scene scene = new Scene();
        gameSound.play();
        pOnCreateSceneCallback.onCreateSceneFinished(scene);
    }

    @Override
    public void onPopulateScene(final Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {

        final float positionX = WIDTH * 0.5f;
        final float positionY = HEIGHT * 0.5f;
        backgroundSprite = new Sprite(positionX, positionY, backgroundTextureRegion, mEngine.getVertexBufferObjectManager());
        pScene.attachChild(backgroundSprite);

        rectangle1 = new Rectangle(initialX, initialY, RECTANGLE_DIMENSIONS, RECTANGLE_DIMENSIONS, mEngine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        break;
                    case TouchEvent.ACTION_UP:
                        Log.d(TAG, "onAreaTouched: " + this.getColor());
                        Log.d(TAG, "onAreaTouched: " + this.getColor().getBlue());
                        if (this.getColor().getBlue() == 1.0)
                            wrongTileSound.play();

                        break;
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };



        rectangle2 = new Rectangle(initialX + RECTANGLE_DIMENSIONS + 20, initialY, RECTANGLE_DIMENSIONS, RECTANGLE_DIMENSIONS, mEngine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        break;
                    case TouchEvent.ACTION_UP:
                        Log.d(TAG, "onAreaTouched: " + this.getColor());
                        Log.d(TAG, "onAreaTouched: " + this.getColor().getBlue());
                        if (this.getColor().getBlue() == 1.0)
                            wrongTileSound.play();

                        break;
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        rectangle3 = new Rectangle(initialX + (RECTANGLE_DIMENSIONS * 2) + (20 * 2), initialY, RECTANGLE_DIMENSIONS, RECTANGLE_DIMENSIONS, mEngine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        break;
                    case TouchEvent.ACTION_UP:
                        Log.d(TAG, "onAreaTouched: " + this.getColor());
                        Log.d(TAG, "onAreaTouched: " + this.getColor().getBlue());
                        if (this.getColor().getBlue() == 1.0)
                            wrongTileSound.play();

                        break;
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        //top 3 boxes
        rectangle4 = new Rectangle(initialX, initialY + RECTANGLE_DIMENSIONS + 20, RECTANGLE_DIMENSIONS, RECTANGLE_DIMENSIONS, mEngine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        break;
                    case TouchEvent.ACTION_UP:
                        Log.d(TAG, "onAreaTouched: " + this.getColor());
                        Log.d(TAG, "onAreaTouched: " + this.getColor().getBlue());
                        if (this.getColor().getBlue() == 1.0)
                            wrongTileSound.play();

                        break;
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        rectangle5 = new Rectangle(initialX + RECTANGLE_DIMENSIONS + 20, initialY + RECTANGLE_DIMENSIONS + 20, RECTANGLE_DIMENSIONS, RECTANGLE_DIMENSIONS, mEngine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        break;
                    case TouchEvent.ACTION_UP:
                        Log.d(TAG, "onAreaTouched: " + this.getColor());
                        Log.d(TAG, "onAreaTouched: " + this.getColor().getBlue());
                        if (this.getColor().getBlue() == 1.0)
                            wrongTileSound.play();

                        break;
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        rectangle6 = new Rectangle(initialX + (RECTANGLE_DIMENSIONS * 2) + (20 * 2), initialY + RECTANGLE_DIMENSIONS + 20, RECTANGLE_DIMENSIONS, RECTANGLE_DIMENSIONS, mEngine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        break;
                    case TouchEvent.ACTION_UP:
                        Log.d(TAG, "onAreaTouched: " + this.getColor());
                        Log.d(TAG, "onAreaTouched: " + this.getColor().getBlue());
                        if (this.getColor().getBlue() == 1.0)
                            wrongTileSound.play();

                        break;
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        rectangle1.setColor(Color.BLUE);
        rectangle2.setColor(Color.RED);
        rectangle3.setColor(Color.GREEN);
        rectangle4.setColor(Color.BLACK);
        rectangle5.setColor(Color.YELLOW);
        rectangle6.setColor(Color.CYAN);

        pScene.attachChild(rectangle1);
        pScene.attachChild(rectangle2);
        pScene.attachChild(rectangle3);
        pScene.attachChild(rectangle4);
        pScene.attachChild(rectangle5);
        pScene.attachChild(rectangle6);

        pScene.registerTouchArea(rectangle1);
        pScene.registerTouchArea(rectangle2);
        pScene.registerTouchArea(rectangle3);
        pScene.registerTouchArea(rectangle4);
        pScene.registerTouchArea(rectangle5);
        pScene.registerTouchArea(rectangle6);
        pScene.registerTouchArea(backgroundSprite);

        pScene.registerUpdateHandler(new TimerHandler(0.5f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                count--;
                try {
                    changeShapeColor();
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                if(count==0){
                    pScene.unregisterUpdateHandler(pTimerHandler);
                    Log.d(TAG, "onTimePassed: FINISHED");
                    //GameOver();
                }
                pTimerHandler.reset();
            }
        }));

        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    private void changeShapeColor() {
        rectangle1.setColor(COLOUR[randInt(0, 6)]);
        rectangle2.setColor(COLOUR[randInt(0, 6)]);
        rectangle3.setColor(COLOUR[randInt(0, 6)]);
        rectangle4.setColor(COLOUR[randInt(0, 6)]);
        rectangle5.setColor(COLOUR[randInt(0, 6)]);
        rectangle6.setColor(COLOUR[randInt(0, 6)]);
    }

    public static int randInt(int min, int max) {
        int randomNum = min + (int) (Math.random() * ((max - min) + 1));
        Log.d("RANDOM", String.valueOf(randomNum));
        return randomNum;
    }
}
