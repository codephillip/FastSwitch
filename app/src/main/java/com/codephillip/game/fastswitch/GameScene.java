package com.codephillip.game.fastswitch;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * Created by codephillip on 7/15/16.
 */
public class GameScene extends Scene {
    private static final String TAG = GameScene.class.getSimpleName();
    Engine engine;
    Sprite sprite;
    Context context;
    private Sprite sprite2;
    private Text timeLeftText, livesText, pointsText, highPointsText, bountyText;
    private Sprite backgroundSprite;
    private AnimatedSprite explosionAnimatedSprite;
    private AnimatedSprite animatedSprite1, animatedSprite2, animatedSprite3, animatedSprite4, animatedSprite5, animatedSprite6;
    private Sprite heartSprite;
    private Sprite coinSprite;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;
    private float initialX = 165;
    private float initialY = 120;
    private final int RECTANGLE_DIMENSIONS = 200;
    private static final String POINTS = "points";

    //TODO [REMOVE ON RELEASE]
//    private int timeLength = 2;
    private int timeLength = 30;
    private float switchSpeed = 1.1f;
    private final int[] correctTileNumbers = {2, 4, 6, 7, 9, 11};
    private static int correctCount = 0;
    private static int wrongCount = 0;
    private int lives = 5;
    private int points = 2;
    private int textCount = 2;
    private int pointsTextYIncrement = 20;
    private Scene scene;
    private Text winOrLoseText;
    private Sprite nextOrRestartSprite;
    private int targetPoints = 500;

    public GameScene(Context context, Engine engine) {
        this.context = context;
        this.engine = engine;
    }

    @Override
    public void attachChild(IEntity pEntity) {
        Log.d(TAG, "attachChild: finished");

        final float positionX = WIDTH * 0.5f;
        final float positionY = HEIGHT * 0.5f;
        backgroundSprite = new Sprite(positionX, positionY, ResourceManager.backgroundTextureRegion, engine.getVertexBufferObjectManager());
        super.attachChild(backgroundSprite);

        heartSprite = new Sprite(positionX, positionY + 215, ResourceManager.heartITextureRegion, engine.getVertexBufferObjectManager());
        super.attachChild(heartSprite);

        coinSprite = new Sprite(positionX + 170, positionY + 215, ResourceManager.coinITextureRegion, engine.getVertexBufferObjectManager());
        super.attachChild(coinSprite);

        explosionAnimatedSprite = new AnimatedSprite(0, 0, ResourceManager.explosionTiledTextureRegion, engine.getVertexBufferObjectManager());
        explosionAnimatedSprite.setAlpha(0.5f);
//        explosionAnimatedSprite.animate(100);

        animatedSprite1 = new AnimatedSprite(initialX, initialY, ResourceManager.fruitTiledTextureRegion, engine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent superTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (superTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        this.setAlpha(0.5f);
                        break;
                    case TouchEvent.ACTION_UP:
                        this.setAlpha(1.0f);
                        checkTileColor(this);
                        attachExplosionAnimation(this);
                        animateExplosion();
                        break;
                    default:
                        return true;
                }
                return super.onAreaTouched(superTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        animatedSprite2 = new AnimatedSprite(initialX + RECTANGLE_DIMENSIONS + 20, initialY, ResourceManager.fruitTiledTextureRegion, engine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent superTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (superTouchEvent.getAction()) {
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
                return super.onAreaTouched(superTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        animatedSprite3 = new AnimatedSprite(initialX + (RECTANGLE_DIMENSIONS * 2) + (20 * 2), initialY, ResourceManager.fruitTiledTextureRegion, engine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent superTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (superTouchEvent.getAction()) {
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
                return super.onAreaTouched(superTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        //top sprites
        animatedSprite4 = new AnimatedSprite(initialX, initialY + RECTANGLE_DIMENSIONS + 20, ResourceManager.fruitTiledTextureRegion, engine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent superTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (superTouchEvent.getAction()) {
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
                return super.onAreaTouched(superTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        animatedSprite5 = new AnimatedSprite(initialX + RECTANGLE_DIMENSIONS + 20, initialY + RECTANGLE_DIMENSIONS + 20, ResourceManager.fruitTiledTextureRegion, engine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent superTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (superTouchEvent.getAction()) {
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
                return super.onAreaTouched(superTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        animatedSprite6 = new AnimatedSprite(initialX + (RECTANGLE_DIMENSIONS * 2) + (20 * 2), initialY + RECTANGLE_DIMENSIONS + 20, ResourceManager.fruitTiledTextureRegion, engine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent superTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (superTouchEvent.getAction()) {
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
                return super.onAreaTouched(superTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        timeLeftText = new Text(0, 0, ResourceManager.font, "TIME: 00", 15, engine.getVertexBufferObjectManager());
        super.attachChild(timeLeftText);
        timeLeftText.setPosition(WIDTH / 2 - (timeLeftText.getWidth() / 2) - 90, HEIGHT / 2 - (timeLeftText.getHeight() / 2) + 240);

        livesText = new Text(0, 0, ResourceManager.font, "3", 5, engine.getVertexBufferObjectManager());
        super.attachChild(livesText);
        livesText.setPosition(WIDTH / 2 - (livesText.getWidth() / 2) + 70, HEIGHT / 2 - (livesText.getHeight() / 2) + 240);
        livesText.setText("" + lives);

        pointsText = new Text(0, 0, ResourceManager.font, "2", 10, engine.getVertexBufferObjectManager());
        super.attachChild(pointsText);
        pointsText.setPosition(WIDTH / 2 - (livesText.getWidth() / 2) + 240, HEIGHT / 2 - (livesText.getHeight() / 2) + 240);
        pointsText.setText("" + points);

        bountyText = new Text(0, 0, ResourceManager.bountyFont, "+100", 10, engine.getVertexBufferObjectManager());
        super.attachChild(bountyText);
        bountyText.setPosition(WIDTH / 2, HEIGHT / 2);

        super.attachChild(explosionAnimatedSprite);
        super.attachChild(animatedSprite1);
        super.attachChild(animatedSprite2);
        super.attachChild(animatedSprite3);
        super.attachChild(animatedSprite4);
        super.attachChild(animatedSprite5);
        super.attachChild(animatedSprite6);

        super.registerTouchArea(animatedSprite1);
        super.registerTouchArea(animatedSprite2);
        super.registerTouchArea(animatedSprite3);
        super.registerTouchArea(animatedSprite4);
        super.registerTouchArea(animatedSprite5);
        super.registerTouchArea(animatedSprite6);
    }

    @Override
    public void registerTouchArea(ITouchArea pTouchArea) {
        Log.d(TAG, "registerTouchArea: menu");
        super.registerTouchArea(sprite);
    }

    @Override
    public void registerUpdateHandler(IUpdateHandler pUpdateHandler) {
        super.registerUpdateHandler(pUpdateHandler);
    }

//    public void updateUI(){
//        //5f is 5 seconds
//        this.registerUpdateHandler(new TimerHandler(5f, true, new ITimerCallback() {
//            @Override
//            public void onTimePassed(TimerHandler pTimerHandler) {
//                unregisterUpdateHandler(pTimerHandler);
//                SceneManager.loadSplashResources();
//                SceneManager.setCurrentScene(AllScenes.SPLASH, SceneManager.createSplashScene());
//            }
//        }));
//    }

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
        timeLeftText.setText("TIME: " + timeLength);
    }

    ///
    private void gameOver(boolean hasWonGame) {
        ResourceManager.gameSound.stop();
        if (points > getHiScore()) storePref(POINTS, points);
        storeStatistics();
        showStatistics(hasWonGame);
        attachChildrenToPauseScreen();
    }

    private void storeStatistics() {
        if (points > getHiScore()) storePref(POINTS, points);
    }

    private void storePref(String prefString, int value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(prefString, value);
        editor.apply();
    }

    private int getHiScore() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int hiScore = prefs.getInt(POINTS, 0);
        return hiScore;
    }

    private void showStatistics(boolean hasWonGame) {
        backgroundSprite.detachSelf();

        setWinOrLoseFontColor(hasWonGame);

        winOrLoseText = new Text(0, 0, ResourceManager.winOrLoseFont, "YOU WIN", 25, engine.getVertexBufferObjectManager());
        winOrLoseText.setPosition(WIDTH / 2, HEIGHT / 2 + 150);
        if (hasWonGame) {
            winOrLoseText.setText("YOU WIN");
        } else {
            winOrLoseText.setText("YOU LOSE");
        }

        pointsText = new Text(0, 0, ResourceManager.menuFont, "Score: 500", 25, engine.getVertexBufferObjectManager());
        pointsText.setPosition(WIDTH / 2, HEIGHT / 2 + 60);
        pointsText.setText("Score: " + points);

        highPointsText = new Text(0, 0, ResourceManager.font, "Hi-Score: 1000", 25, engine.getVertexBufferObjectManager());
        highPointsText.setPosition(WIDTH / 2, HEIGHT / 2);
        highPointsText.setText("Hi-Score: " + getHiScore());

        setNextorRestartSprite(hasWonGame);
    }

    private void setNextorRestartSprite(boolean hasWonGame) {
        ITextureRegion textureRegion;
        if (hasWonGame) {
            textureRegion = ResourceManager.resumeITextureRegion;
        } else {
            textureRegion = ResourceManager.restartITextureRegion;
        }

        nextOrRestartSprite = new Sprite(WIDTH / 2, HEIGHT / 2 - 90, textureRegion, engine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent superTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (superTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        this.setAlpha(0.5f);
                        break;
                    case TouchEvent.ACTION_UP:
                        this.setAlpha(1.0f);
                        Log.d(TAG, "onAreaTouched: clicked");
                        break;
                }
                return super.onAreaTouched(superTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
    }

    private void setWinOrLoseFontColor(boolean hasWonGame) {
        if (hasWonGame) {
            ResourceManager.winOrLoseFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
                    "fnt/sanchez.ttf", 100, true, Color.YELLOW);
        } else {
            ResourceManager.winOrLoseFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
                    "fnt/sanchez.ttf", 100, true, Color.RED);
        }
        ResourceManager.winOrLoseFont.load();
    }

    private void attachChildrenToPauseScreen() {
        scene.attachChild(backgroundSprite);
        scene.attachChild(winOrLoseText);
        scene.attachChild(pointsText);
        scene.attachChild(highPointsText);
        scene.attachChild(nextOrRestartSprite);
        scene.registerTouchArea(nextOrRestartSprite);
    }
    ///

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

            if (correctCount % 10 == 0) giveBounty(100);

            if (correctCount % 5 == 0) {
                ResourceManager.lifeUpSound.play();
                gainLife();
                gainPoints();
            } else {
                gainPoints();
                ResourceManager.rightTileSound.play();
            }
        } else {
            wrongCount++;
            if (wrongCount == 3) {
                wrongCount = 0;
                correctCount = 0;
                ResourceManager.lifeDownSound.play();
                loseLife();
            } else {
                ResourceManager.wrongTileSound.play();
            }
        }
    }

    private void gainPoints() {
        points += lives * 2;
        pointsText.setText("" + points);
        if (pointsText.getText().toString().length() > textCount) {
            pointsText.setX(WIDTH / 2 - (livesText.getWidth() / 2) + 230 + pointsTextYIncrement);
            pointsTextYIncrement += 35;
            textCount += 1;
        }
        Log.d(TAG, "gainPoints: timeLength" + pointsText.getText().toString().length());
    }

    private void gainLife() {
        lives++;
        livesText.setText("" + lives);
        Log.d(TAG, "gainLife: " + lives);
    }

    private void giveBounty(int points) {
        resetCorrectCount();
        ResourceManager.bountySound.play();
        this.points += points;
    }

    private void resetCorrectCount() {
        correctCount = 0;
    }

    private void loseLife() {
        lives--;
        livesText.setText("" + lives);
        if (lives <= 0) {
            ResourceManager.deathSound.play();
            gameOver(false);
        }
        Log.d(TAG, "loseLife: " + lives);
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
