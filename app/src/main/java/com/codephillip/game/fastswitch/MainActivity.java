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
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
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

    private BitmapTextureAtlas fruitTextureAtlas;
    private ITiledTextureRegion fruitTiledTextureRegion;
    private AnimatedSprite animatedSprite1, animatedSprite2, animatedSprite3, animatedSprite4, animatedSprite5, animatedSprite6;

    private static final Color[] COLOUR = {
            Color.GREEN, Color.BLACK, Color.BLUE, Color.YELLOW, Color.RED, Color.CYAN
    };
    private Music gameSound;
    private Sound wrongTileSound, rightTileSound, lifeUpSound, lifeDownSound;
    private int count = 120;
    private double colourNumber = 1.0;
    private float switchSpeed = 1.1f;
    //todo create enum of 6 colours and switch to respond

    private static int correctCount = 0;
    private static int wrongCount = 0;
    private int life = 5;

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
    public synchronized void onPauseGame() {
        gameSound.pause();
        super.onPauseGame();
    }

    @Override
    public synchronized void onResumeGame() {
        gameSound.resume();
        super.onResumeGame();
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        backgroundTextureAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 1024, 512, TextureOptions.DEFAULT);
        backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureAtlas, this, "background.png", 0, 0);
        backgroundTextureAtlas.load();

        fruitTextureAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 414, 276, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        fruitTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(fruitTextureAtlas,this, "spritesheet.png", 0, 0, 3, 2);
        fruitTextureAtlas.load();

        try {
            gameSound = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "mfx/game_music.mp3");
            wrongTileSound = SoundFactory.createSoundFromAsset(getEngine().getSoundManager(), this, "mfx/wrong_tile.ogg");
            rightTileSound = SoundFactory.createSoundFromAsset(getEngine().getSoundManager(), this, "mfx/right_tile.ogg");
            lifeUpSound = SoundFactory.createSoundFromAsset(getEngine().getSoundManager(), this, "mfx/life_up.ogg");
            lifeDownSound = SoundFactory.createSoundFromAsset(getEngine().getSoundManager(), this, "mfx/life_down.ogg");
            gameSound.setLooping(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {
        Scene scene = new Scene();
//        scene.setBackground(new Background(Color.WHITE));
        gameSound.play();
        pOnCreateSceneCallback.onCreateSceneFinished(scene);
    }

    @Override
    public void onPopulateScene(final Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {

        final float positionX = WIDTH * 0.5f;
        final float positionY = HEIGHT * 0.5f;
        backgroundSprite = new Sprite(positionX, positionY, backgroundTextureRegion, mEngine.getVertexBufferObjectManager());
        pScene.attachChild(backgroundSprite);

        animatedSprite1 = new AnimatedSprite(initialX, initialY, fruitTiledTextureRegion, mEngine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        break;
                    case TouchEvent.ACTION_UP:
                        Log.d(TAG, "onAreaTouched: " + this.getColor());
                        Log.d(TAG, "onAreaTouched: " + this.getColor().getBlue());
                        checkTileColor(this);
                        break;
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        animatedSprite2 = new AnimatedSprite(initialX + RECTANGLE_DIMENSIONS + 20, initialY, fruitTiledTextureRegion, mEngine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        break;
                    case TouchEvent.ACTION_UP:
                        Log.d(TAG, "onAreaTouched: " + this.getColor());
                        Log.d(TAG, "onAreaTouched: " + this.getColor().getBlue());
                        checkTileColor(this);
                        break;
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        animatedSprite3 = new AnimatedSprite(initialX + (RECTANGLE_DIMENSIONS * 2) + (20 * 2), initialY, fruitTiledTextureRegion, mEngine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        break;
                    case TouchEvent.ACTION_UP:
                        Log.d(TAG, "onAreaTouched: " + this.getColor());
                        Log.d(TAG, "onAreaTouched: " + this.getColor().getBlue());
                        checkTileColor(this);
                        break;
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        //top sprites
        animatedSprite4 = new AnimatedSprite(initialX, initialY + RECTANGLE_DIMENSIONS + 20, fruitTiledTextureRegion, mEngine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        break;
                    case TouchEvent.ACTION_UP:
                        Log.d(TAG, "onAreaTouched: " + this.getColor());
                        Log.d(TAG, "onAreaTouched: " + this.getColor().getBlue());
                        checkTileColor(this);
                        break;
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        animatedSprite5 = new AnimatedSprite(initialX + RECTANGLE_DIMENSIONS + 20, initialY + RECTANGLE_DIMENSIONS + 20, fruitTiledTextureRegion, mEngine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        break;
                    case TouchEvent.ACTION_UP:
                        Log.d(TAG, "onAreaTouched: " + this.getColor());
                        Log.d(TAG, "onAreaTouched: " + this.getColor().getBlue());
                        checkTileColor(this);
                        break;
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        animatedSprite6 = new AnimatedSprite(initialX + (RECTANGLE_DIMENSIONS * 2) + (20 * 2), initialY + RECTANGLE_DIMENSIONS + 20, fruitTiledTextureRegion, mEngine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        break;
                    case TouchEvent.ACTION_UP:
                        Log.d(TAG, "onAreaTouched: " + this.getColor());
                        Log.d(TAG, "onAreaTouched: " + this.getColor().getBlue());
                        checkTileColor(this);
                        break;
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        pScene.attachChild(animatedSprite1);
        pScene.attachChild(animatedSprite2);
        pScene.attachChild(animatedSprite3);
        pScene.attachChild(animatedSprite4);
        pScene.attachChild(animatedSprite5);
        pScene.attachChild(animatedSprite6);

        pScene.registerTouchArea(animatedSprite1);
        pScene.registerTouchArea(animatedSprite2);
        pScene.registerTouchArea(animatedSprite3);
        pScene.registerTouchArea(animatedSprite4);
        pScene.registerTouchArea(animatedSprite5);
        pScene.registerTouchArea(animatedSprite6);

        pScene.registerUpdateHandler(new TimerHandler(switchSpeed, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                count--;
                try {
                    Log.d(TAG, "onTimePassed: Seconds#" + (count % 5 == 0));
                    changeTileColor();
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                if (count == 0) {
                    pScene.unregisterUpdateHandler(pTimerHandler);
                    Log.d(TAG, "onTimePassed: FINISHED");
                    gameOver();
                }
                pTimerHandler.reset();
            }
        }));

        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    private void gameOver() {
        gameSound.stop();
    }

    private void checkTileColor(AnimatedSprite rectangle) {
        /*hit correct tile 3 times to gain life
        * hitting wrong time erases your correctCount
        * failure to gain life in 5 seconds, penalty is to lose a life
        * hit the wrong tile 3 time, penalty is to lose a life
        * */
        if (rectangle.getColor().getBlue() == 1.0) {
            correctCount++;
            Log.d(TAG, "checkTileColor: CORRECT COUNT " + correctCount);
            if (correctCount == 2) {
                correctCount = 0;
                lifeUpSound.play();
                gainLife();
            } else {
                rightTileSound.play();
            }
        } else {
            wrongCount++;
            if (wrongCount == 3) {
                wrongCount = 0;
                lifeDownSound.play();
                loseLife();
            } else {
                wrongTileSound.play();
            }
            correctCount = 0;
        }
    }

    private void gainLife() {
        life++;
        Log.d(TAG, "gainLife: "+life);
    }

    private void loseLife() {
        life--;
        if (life <= 0) gameOver();
        Log.d(TAG, "loseLife: "+life);
    }

    private void changeTileColor() {
        animatedSprite1.setCurrentTileIndex(randInt(0, 5));
        animatedSprite2.setCurrentTileIndex(randInt(0, 5));
        animatedSprite3.setCurrentTileIndex(randInt(0, 5));
        animatedSprite4.setCurrentTileIndex(randInt(0, 5));
        animatedSprite5.setCurrentTileIndex(randInt(0, 5));
        animatedSprite6.setCurrentTileIndex(randInt(0, 5));
    }

    public static int randInt(int min, int max) {
        int randomNum = min + (int) (Math.random() * ((max - min) + 1));
        Log.d("RANDOM", String.valueOf(randomNum));
        return randomNum;
    }
}
