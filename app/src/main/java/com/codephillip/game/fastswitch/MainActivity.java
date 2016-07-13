package com.codephillip.game.fastswitch;

import android.graphics.Color;
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
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import java.io.IOException;

public class MainActivity extends BaseGameActivity {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;
    private static final String TAG = "MAIN";

    private float initialX = 165;
    private float initialY = 120;
    private final int RECTANGLE_DIMENSIONS = 200;

    private BitmapTextureAtlas backgroundTextureAtlas;
    private ITextureRegion backgroundTextureRegion;
    private Sprite backgroundSprite;

    private BitmapTextureAtlas explosionTextureAtlas;
    private ITiledTextureRegion explosionTiledTextureRegion;
    private AnimatedSprite explosionAnimatedSprite;

    private BitmapTextureAtlas fruitTextureAtlas;
    private ITiledTextureRegion fruitTiledTextureRegion;
    private AnimatedSprite animatedSprite1, animatedSprite2, animatedSprite3, animatedSprite4, animatedSprite5, animatedSprite6;

    private BitmapTextureAtlas heartTextureAtlas;
    private ITextureRegion heartITextureRegion;
    private Sprite heartSprite;

    private BitmapTextureAtlas coinTextureAtlas;
    private ITextureRegion coinITextureRegion;
    private Sprite coinSprite;

    private Font font, bountyFont;
    private Text timeLeftText, livesText, pointsText, bountyText;

    private Music gameSound;
    private Sound wrongTileSound, rightTileSound, lifeUpSound, lifeDownSound, deathSound, bountySound;
    //TODO [REMOVE ON RELEASE]
//    private int timeLength = 10;
    private int timeLength = 30;
    private float switchSpeed = 1.1f;
    private final int[] correctTileNumbers = {2, 4, 6, 7, 9, 11};
    private static int correctCount = 0;
    private static int wrongCount = 0;
    private int lives = 5;
    private int points = 2;
    private int textCount = 2;
    private int pointsTextYIncrement = 20;

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

        fruitTextureAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 552, 414, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        fruitTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(fruitTextureAtlas,this, "spritesheet1.png", 0, 0, 4, 3);
        fruitTextureAtlas.load();

        explosionTextureAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 768, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        explosionTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(explosionTextureAtlas,this, "good_explosion.png", 0, 0, 3, 4);
        explosionTextureAtlas.load();

        heartTextureAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        heartITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(heartTextureAtlas,this, "heart.png", 0, 0);
        heartTextureAtlas.load();

        coinTextureAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        coinITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(coinTextureAtlas,this, "coin.png", 0, 0);
        coinTextureAtlas.load();

        font = FontFactory.createFromAsset(this.getFontManager(), this.getTextureManager(), 256, 256, this.getAssets(),
                "fnt/pipedream.ttf", 46, true, Color.BLACK);
        font.load();

        bountyFont = FontFactory.createFromAsset(this.getFontManager(), this.getTextureManager(), 256, 256, this.getAssets(),
                "fnt/pipedream.ttf", 80, true, Color.GREEN);
        bountyFont.load();

        try {
            gameSound = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "mfx/game_music.mp3");
            wrongTileSound = SoundFactory.createSoundFromAsset(getEngine().getSoundManager(), this, "mfx/wrong_tile.ogg");
            rightTileSound = SoundFactory.createSoundFromAsset(getEngine().getSoundManager(), this, "mfx/right_tile.ogg");
            lifeUpSound = SoundFactory.createSoundFromAsset(getEngine().getSoundManager(), this, "mfx/life_up.ogg");
            bountySound = SoundFactory.createSoundFromAsset(getEngine().getSoundManager(), this, "mfx/bounty.ogg");
            lifeDownSound = SoundFactory.createSoundFromAsset(getEngine().getSoundManager(), this, "mfx/death.ogg");
            deathSound = SoundFactory.createSoundFromAsset(getEngine().getSoundManager(), this, "mfx/death.ogg");
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

        heartSprite = new Sprite(positionX, positionY+215, heartITextureRegion, mEngine.getVertexBufferObjectManager());
        pScene.attachChild(heartSprite);

        coinSprite = new Sprite(positionX+170, positionY+215, coinITextureRegion, mEngine.getVertexBufferObjectManager());
        pScene.attachChild(coinSprite);

        explosionAnimatedSprite = new AnimatedSprite(0, 0, explosionTiledTextureRegion, mEngine.getVertexBufferObjectManager());
        explosionAnimatedSprite.setAlpha(0.5f);
//        explosionAnimatedSprite.animate(100);

        animatedSprite1 = new AnimatedSprite(initialX, initialY, fruitTiledTextureRegion, mEngine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        this.setAlpha(0.5f);
                        break;
                    case TouchEvent.ACTION_UP:
                        this.setAlpha(1.0f);
                        checkTileColor(this);
                        attachExplosionAnimation(this);
                        animateExplosion();
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
                        this.setAlpha(0.5f);
                        break;
                    case TouchEvent.ACTION_UP:
                        this.setAlpha(1.0f);
                        checkTileColor(this);
                        attachExplosionAnimation(this);
                        animateExplosion();
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
                        this.setAlpha(0.5f);
                        break;
                    case TouchEvent.ACTION_UP:
                        this.setAlpha(1.0f);
                        checkTileColor(this);
                        attachExplosionAnimation(this);
                        animateExplosion();
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
                        this.setAlpha(0.5f);
                        break;
                    case TouchEvent.ACTION_UP:
                        this.setAlpha(1.0f);
                        checkTileColor(this);
                        attachExplosionAnimation(this);
                        animateExplosion();
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
                        this.setAlpha(0.5f);
                        break;
                    case TouchEvent.ACTION_UP:
                        this.setAlpha(1.0f);
                        checkTileColor(this);
                        attachExplosionAnimation(this);
                        animateExplosion();
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
                        this.setAlpha(0.5f);
                        break;
                    case TouchEvent.ACTION_UP:
                        this.setAlpha(1.0f);
                        checkTileColor(this);
                        attachExplosionAnimation(this);
                        animateExplosion();
                        break;
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        timeLeftText = new Text(0, 0, font, "TIME: 00", 15, this.getVertexBufferObjectManager());
        pScene.attachChild(timeLeftText);
        timeLeftText.setPosition(WIDTH/2 - (timeLeftText.getWidth()/2) - 90, HEIGHT/2 - (timeLeftText.getHeight()/2) +240);

        livesText = new Text(0, 0, font, "3", 5, this.getVertexBufferObjectManager());
        pScene.attachChild(livesText);
        livesText.setPosition(WIDTH/2 - (livesText.getWidth()/2)+70, HEIGHT/2 - (livesText.getHeight()/2) +240);
        livesText.setText(""+ lives);

        pointsText = new Text(0, 0, font, "2", 10, this.getVertexBufferObjectManager());
        pScene.attachChild(pointsText);
        pointsText.setPosition(WIDTH/2 - (livesText.getWidth()/2)+240, HEIGHT/2 - (livesText.getHeight()/2) +240);
        pointsText.setText(""+points);

        bountyText = new Text(0, 0, bountyFont, "+100", 10, this.getVertexBufferObjectManager());
        pScene.attachChild(bountyText);
        bountyText.setPosition(WIDTH/2, HEIGHT/2);

        pScene.attachChild(explosionAnimatedSprite);
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
                timeLength--;
                try {
                    changeSpriteTile();
                    changeTimeLeft();
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                if (timeLength == 0) {
                    pScene.unregisterUpdateHandler(pTimerHandler);
                    Log.d(TAG, "onTimePassed: FINISHED");
                    gameOver();
                }
                pTimerHandler.reset();
            }
        }));

        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    private void attachExplosionAnimation(AnimatedSprite animatedSprite) {
        // Get the scene coordinates of the animatedSprite as an array.
        float[] coodinates = {animatedSprite.getX(), animatedSprite.getY()};
        // Convert the the scene coordinates of the animatedSprite to the local corrdinates of the explosionAnimatedSprite.
        float[] localCoordinates = animatedSprite.convertSceneCoordinatesToLocalCoordinates(coodinates);
        // Attach and set position of explosionAnimatedSprite
        explosionAnimatedSprite.setPosition(localCoordinates[0], localCoordinates[1]);
        explosionAnimatedSprite.detachSelf();
        animatedSprite.attachChild(explosionAnimatedSprite);
    }

    private void animateExplosion() {
        explosionAnimatedSprite.animate(30, false,
                new AnimatedSprite.IAnimationListener() {
                    @Override
                    public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int pInitialLoopCount) {

                    }

                    @Override
                    public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex, int pNewFrameIndex) {

                    }

                    @Override
                    public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite, int pRemainingLoopCount, int pInitialLoopCount) {

                    }

                    @Override
                    public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
                        Log.d(TAG, "onAnimationFinished: TRUE");
                        explosionAnimatedSprite.detachSelf();
                    }
                });
    }

    private void changeTimeLeft() {
        timeLeftText.setText("TIME: "+ timeLength);
    }

    private void gameOver() {
        gameSound.stop();
    }

    private void checkTileColor(AnimatedSprite animatedSprite) {
        /*hit correct tile 3 times to gain lives
        * hitting wrong time erases your correctCount
        * hit the wrong tile 3 time, penalty is to lose a lives
        * */
        if (animatedSprite.getCurrentTileIndex() == correctTileNumbers[0]
                || animatedSprite.getCurrentTileIndex() == correctTileNumbers[1]
                || animatedSprite.getCurrentTileIndex() == correctTileNumbers[2]
                || animatedSprite.getCurrentTileIndex() == correctTileNumbers[3]
                || animatedSprite.getCurrentTileIndex() == correctTileNumbers[4]
                || animatedSprite.getCurrentTileIndex() == correctTileNumbers[5]) {
            correctCount++;
            Log.d(TAG, "checkTileColor: CORRECT COUNT " + correctCount);
            Log.d(TAG, "checkTileColor: CORRECT ");

            if (correctCount%10 == 0) giveBounty(100);

            if (correctCount%5 == 0) {
                lifeUpSound.play();
                gainLife();
                gainPoints();
            } else {
                gainPoints();
                rightTileSound.play();
            }
        } else {
            wrongCount++;
            if (wrongCount == 3) {
                wrongCount = 0;
                correctCount = 0;
                lifeDownSound.play();
                loseLife();
            } else {
                wrongTileSound.play();
            }
        }
    }

    private void gainPoints() {
        points += lives*2;
        pointsText.setText(""+ points);
        if (pointsText.getText().toString().length() > textCount){
            pointsText.setX(WIDTH/2 - (livesText.getWidth()/2)+230+pointsTextYIncrement);
            pointsTextYIncrement += 35;
            textCount += 1;
        }
        Log.d(TAG, "gainPoints: timeLength"+pointsText.getText().toString().length());
    }

    private void gainLife() {
        lives++;
        livesText.setText(""+ lives);
        Log.d(TAG, "gainLife: "+ lives);
    }

    private void giveBounty(int points) {
        resetCorrectCount();
        bountySound.play();
        this.points+=points;
    }

    private void resetCorrectCount() {
        correctCount = 0;
    }

    private void loseLife() {
        lives--;
        livesText.setText(""+ lives);
        if (lives <= 0) {
            deathSound.play();
            gameOver();
        }
        Log.d(TAG, "loseLife: "+ lives);
    }

    private void changeSpriteTile() {
        animatedSprite1.setCurrentTileIndex(randInt(0, 11));
        animatedSprite2.setCurrentTileIndex(randInt(0, 11));
        animatedSprite3.setCurrentTileIndex(randInt(0, 11));
        animatedSprite4.setCurrentTileIndex(randInt(0, 11));
        animatedSprite5.setCurrentTileIndex(randInt(0, 11));
        animatedSprite6.setCurrentTileIndex(randInt(0, 11));
    }

    public static int randInt(int min, int max) {
        int randomNum = min + (int) (Math.random() * ((max - min) + 1));
        Log.d("RANDOM", String.valueOf(randomNum));
        return randomNum;
    }
}
