package com.codephillip.game.fastswitch;

import android.content.Context;
import android.util.Log;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

/**
 * Created by codephillip on 7/15/16.
 */
public class GameScene extends Scene {
    private static final String TAG = GameScene.class.getSimpleName();
    private Engine engine;
    private Context context;
    private Text timeLeftText, livesText, scoreText, targetScoreText, levelText;
    private Sprite backgroundSprite;
    private AnimatedSprite explosionAnimatedSprite;
    private Sprite pauseSprite;
    private AnimatedSprite animatedSprite1, animatedSprite2, animatedSprite3, animatedSprite4, animatedSprite5, animatedSprite6;
    private Sprite heartSprite;
    private final float initialX = 165;
    private final float initialY = 120;
    private final int RECTANGLE_DIMENSIONS = 200;

    //TODO [REMOVE ON RELEASE]
//    private int gameTimeLeft = 5;
    private int gameTimeLeft = 30;
    private final int[] correctTileNumbers = {2, 4, 6, 7, 9, 11};
    private static int correctCount = 0;
    private static int wrongCount = 0;
    private int lives = 5;
    private int points = 2;

    public GameScene(Context context, Engine engine) {
        Log.d(TAG, "GameScene: CONSTRUCTOR");
        this.context = context;
        this.engine = engine;
        attachChild(null);
        registerTouchArea(null);
        if (Utils.getPausedGame()) {
            gameTimeLeft = Utils.getGameTimeLeft();
            lives = Utils.getLives();
            points = Utils.getScores();
            updateTimeLeftText();
            updateLivesText();
            updatePointsText();
            Utils.savePausedGame(Utils.HAS_PAUSED_GAME, false);
        }
        registerUpdateHandler(null);
        Utils.logAnalyticsScene("GameScene");
    }

    @Override
    public void attachChild(IEntity pEntity) {
        Log.d(TAG, "attachChild: finished");

        backgroundSprite = new Sprite(Utils.positionX, Utils.positionY, ResourceManager.backgroundTextureRegion, engine.getVertexBufferObjectManager());
        heartSprite = new Sprite(Utils.positionX - 90, Utils.positionY + 215, ResourceManager.heartITextureRegion, engine.getVertexBufferObjectManager());
        explosionAnimatedSprite = new AnimatedSprite(0, 0, ResourceManager.explosionTiledTextureRegion, engine.getVertexBufferObjectManager());

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
                        initialiseExplosionAnimation(this);
                        break;
                    default:
                        return true;
                }
                Log.d(TAG, "onAreaTouched: clicked");
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
                        initialiseExplosionAnimation(this);
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
                        initialiseExplosionAnimation(this);
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
                        initialiseExplosionAnimation(this);
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
                        initialiseExplosionAnimation(this);
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
                        initialiseExplosionAnimation(this);
                        break;
                }
                return super.onAreaTouched(superTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        pauseSprite = new Sprite(Utils.CAMERA_WIDTH / 2 + 340, Utils.CAMERA_HEIGHT / 2 - 170, ResourceManager.pauseITextureRegion, engine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent superTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (superTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        this.setAlpha(0.5f);
                        break;
                    case TouchEvent.ACTION_UP:
                        this.setAlpha(1.0f);
                        Utils.savePausedGame(Utils.HAS_PAUSED_GAME, true);
                        Utils.saveIntPref(Utils.GAME_TIME_LEFT, gameTimeLeft);
                        Utils.saveIntPref(Utils.LIVES, lives);
                        Utils.saveIntPref(Utils.POINTS, points);
                        Utils.pauseMusic();
                        SceneManager.setCurrentScene(AllScenes.PAUSE, SceneManager.createPauseScene());
                        break;
                }
                return super.onAreaTouched(superTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        showStatistics();

        super.attachChild(backgroundSprite);
        super.attachChild(heartSprite);
        super.attachChild(livesText);
        super.attachChild(timeLeftText);
        super.attachChild(scoreText);
        super.attachChild(targetScoreText);
        super.attachChild(levelText);
        super.attachChild(animatedSprite1);
        super.attachChild(animatedSprite2);
        super.attachChild(animatedSprite3);
        super.attachChild(animatedSprite4);
        super.attachChild(animatedSprite5);
        super.attachChild(animatedSprite6);
        super.attachChild(pauseSprite);
        ResourceManager.gameSound.play();
    }

    private void showStatistics() {
        final int Y_INCREMENT = 230;
        timeLeftText = new Text(0, 0, ResourceManager.font, "TIME: 00", 15, engine.getVertexBufferObjectManager());
        timeLeftText.setPosition(Utils.CAMERA_WIDTH / 2 - (timeLeftText.getWidth() / 2) - 220, Utils.CAMERA_HEIGHT / 2 - (timeLeftText.getHeight() / 2) + Y_INCREMENT);

        levelText = new Text(0, 0, ResourceManager.font, "LEVEL 1", 15, engine.getVertexBufferObjectManager());
        levelText.setPosition(Utils.CAMERA_WIDTH / 2 - (timeLeftText.getWidth() / 2) - 100, Utils.CAMERA_HEIGHT / 2 - (timeLeftText.getHeight() / 2) + Y_INCREMENT);
        levelText.setText("Lv: "+Utils.getLevel());

        livesText = new Text(0, 0, ResourceManager.font, "3", 5, engine.getVertexBufferObjectManager());
        livesText.setPosition(Utils.CAMERA_WIDTH / 2 - (livesText.getWidth() / 2) - 50, Utils.CAMERA_HEIGHT / 2 - (livesText.getHeight() / 2) + Y_INCREMENT);
        livesText.setText("" + lives);

        scoreText = new Text(0, 0, ResourceManager.font, "2", 15, engine.getVertexBufferObjectManager());
        scoreText.setPosition(Utils.CAMERA_WIDTH / 2 - (livesText.getWidth() / 2) + 80, Utils.CAMERA_HEIGHT / 2 - (livesText.getHeight() / 2) + Y_INCREMENT);
        scoreText.setText("SCORE: " + points);

        targetScoreText = new Text(0, 0, ResourceManager.font, "2", 15, engine.getVertexBufferObjectManager());
        targetScoreText.setPosition(Utils.CAMERA_WIDTH / 2 - (livesText.getWidth() / 2) + 270, Utils.CAMERA_HEIGHT / 2 - (livesText.getHeight() / 2) + Y_INCREMENT);
        targetScoreText.setText("TARGET: " + Utils.getTargetScore());
    }

    private void initialiseExplosionAnimation(AnimatedSprite animatedSprite) {
        //explosion only happen for bombs
        if (animatedSprite.getCurrentTileIndex() == correctTileNumbers[0]
                || animatedSprite.getCurrentTileIndex() == correctTileNumbers[1]
                || animatedSprite.getCurrentTileIndex() == correctTileNumbers[2]
                || animatedSprite.getCurrentTileIndex() == correctTileNumbers[3]
                || animatedSprite.getCurrentTileIndex() == correctTileNumbers[4]
                || animatedSprite.getCurrentTileIndex() == correctTileNumbers[5]) {
            Log.d(TAG, "initialiseExplosionAnimation: NO EXPLOSION");
        } else {
            attachExplosionAnimation(animatedSprite);
            animateExplosion();
        }
    }

    private void attachExplosionAnimation(AnimatedSprite animatedSprite) {
//        if (animatedSprite.getCurrentTileIndex() == correctTileNumbers[0]){
        // Get the scene coordinates of the animatedSprite as an array.
        float[] coodinates = {animatedSprite.getX(), animatedSprite.getY()};
        // Convert the the scene coordinates of the animatedSprite to the local corrdinates of the explosionAnimatedSprite.
        float[] localCoordinates = animatedSprite.convertSceneCoordinatesToLocalCoordinates(coodinates);
        // Attach and set position of explosionAnimatedSprite
        explosionAnimatedSprite.setPosition(localCoordinates[0], localCoordinates[1]);
        explosionAnimatedSprite.detachSelf();
        animatedSprite.attachChild(explosionAnimatedSprite);
//        }
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

    @Override
    public void registerTouchArea(ITouchArea pTouchArea) {
        Log.d(TAG, "registerTouchArea: menu");
        super.registerTouchArea(pauseSprite);
        super.registerTouchArea(animatedSprite1);
        super.registerTouchArea(animatedSprite2);
        super.registerTouchArea(animatedSprite3);
        super.registerTouchArea(animatedSprite4);
        super.registerTouchArea(animatedSprite5);
        super.registerTouchArea(animatedSprite6);
    }

    @Override
    public void registerUpdateHandler(IUpdateHandler pUpdateHandler) {
        super.registerUpdateHandler(new TimerHandler(Utils.getSwitchSpeed(), true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                gameTimeLeft--;
                try {
                    updateSpriteTile();
                    updateTimeLeftText();
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                if (gameTimeLeft == 0) {
                    unregisterUpdateHandler(pTimerHandler);
                    Log.d(TAG, "onTimePassed: FINISHED");
                    if (lives >= 1 && points >= Utils.getTargetScore()) {
                        Utils.saveHasWonGame(Utils.HAS_WON_GAME, true);
                        gameOver();
                    } else {
                        Utils.saveHasWonGame(Utils.HAS_WON_GAME, false);
                        gameOver();
                    }
                }
                pTimerHandler.reset();
            }
        }));
    }

    private void updateTimeLeftText() {
        timeLeftText.setText("TIME: " + gameTimeLeft);
    }

    private void gameOver() {
        Utils.pauseMusic();
        storeStatistics();
        this.clearChildScene();
        this.clearUpdateHandlers();
        SceneManager.setCurrentScene(AllScenes.GAME0VER, SceneManager.createGameOverScene());
    }

    private void storeStatistics() {
        if (points > Utils.getHiScore()) {
            Utils.saveIntPref(Utils.HI_POINTS, points);
        }
        Utils.saveIntPref(Utils.POINTS, points);
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
            processCorrectCount();
        } else {
            processWrongCount();
        }
    }

    private void processCorrectCount() {
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
    }

    private void processWrongCount() {
        wrongCount++;
        if (wrongCount == 3) {
            wrongCount = 0;
            correctCount = 0;
            ResourceManager.lifeDownSound.play();
            ResourceManager.wrongTileSound.play();
            loseLife();
        } else {
            ResourceManager.wrongTileSound.play();
        }
    }

    private void gainPoints() {
        points += lives * 2;
        updatePointsText();
    }

    private void updatePointsText() {
        scoreText.setText("SCORE: " + points);
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
        updateLivesText();
        if (lives <= 0) {
            ResourceManager.deathSound.play();
            Utils.saveHasWonGame(Utils.HAS_WON_GAME, false);
            gameOver();
        }
        Log.d(TAG, "loseLife: " + lives);
    }

    private void updateLivesText() {
        livesText.setText("" + lives);
    }

    private void updateSpriteTile() {
        animatedSprite1.setCurrentTileIndex(Utils.randInt(0, 11));
        animatedSprite2.setCurrentTileIndex(Utils.randInt(0, 11));
        animatedSprite3.setCurrentTileIndex(Utils.randInt(0, 11));
        animatedSprite4.setCurrentTileIndex(Utils.randInt(0, 11));
        animatedSprite5.setCurrentTileIndex(Utils.randInt(0, 11));
        animatedSprite6.setCurrentTileIndex(Utils.randInt(0, 11));
    }
}
