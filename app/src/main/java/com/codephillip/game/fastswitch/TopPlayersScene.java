package com.codephillip.game.fastswitch;

import android.content.Context;
import android.util.Log;

import com.codephillip.backend.topPlayersApi.model.TopPlayers;

import org.andengine.engine.Engine;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.FontFactory;
import org.andengine.util.adt.color.Color;

/**
 * Created by codephillip on 7/15/16.
 */
public class TopPlayersScene extends Scene {
    private static final String TAG = TopPlayersScene.class.getSimpleName();
    private final Engine engine;
    private final Context context;
    private Sprite backgroundSprite;
    private Sprite exitSprite;
    private Text instructionText;
    private Text titleText;

    public TopPlayersScene(Context context, Engine engine) {
        this.context = context;
        this.engine = engine;
        attachChild(null);
        registerTouchArea(null);
    }

    @Override
    public void attachChild(IEntity pEntity) {
        this.setBackground(new Background(Color.GREEN));

        backgroundSprite = new Sprite(Utils.positionX, Utils.positionY, ResourceManager.backgroundTextureRegion, engine.getVertexBufferObjectManager());

        Sprite overlaySprite = new Sprite(Utils.positionX, Utils.positionY, ResourceManager.overlayTextureRegion, engine.getVertexBufferObjectManager());
        overlaySprite.setColor(Color.BLACK);
        overlaySprite.setAlpha(0.7f);

        exitSprite = new Sprite(Utils.CAMERA_WIDTH / 2 + 20, Utils.CAMERA_HEIGHT / 2 - 170, ResourceManager.exitITextureRegion, engine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent superTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (superTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        this.setAlpha(0.5f);
                        break;
                    case TouchEvent.ACTION_UP:
                        this.setAlpha(1.0f);
                        clearChildScene();
                        SceneManager.setCurrentScene(AllScenes.MENU, SceneManager.createMenuScene());
                        Log.d(TAG, "onAreaTouched: clicked");
                        break;
                }
                return super.onAreaTouched(superTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        showStatistics();

        super.attachChild(backgroundSprite);
        super.attachChild(overlaySprite);
        super.attachChild(titleText);
        super.attachChild(instructionText);
        super.attachChild(exitSprite);
    }

    @Override
    public void registerTouchArea(ITouchArea pTouchArea) {
        super.registerTouchArea(exitSprite);
    }

    private void showStatistics() {
        backgroundSprite.detachSelf();

        ResourceManager.winOrLoseFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
                "fnt/sanchez.ttf", 70, true, android.graphics.Color.YELLOW);
        ResourceManager.winOrLoseFont.load();

        titleText = new Text(0, 0, ResourceManager.winOrLoseFont, "TOP PLAYERS", 25, engine.getVertexBufferObjectManager());
        titleText.setPosition(Utils.CAMERA_WIDTH / 2, Utils.CAMERA_HEIGHT / 2 + 150);

        instructionText = new Text(0, 0, ResourceManager.instructionFont, "Hi-Score: 1000", 500, engine.getVertexBufferObjectManager());
        instructionText.setPosition(Utils.CAMERA_WIDTH / 2, Utils.CAMERA_HEIGHT / 2);
        String string = "";
        try {
            for (TopPlayers topPlayer : Utils.topPlayers) {
                Log.d(TAG, "doGetRequest() GET_2: " + topPlayer.getName() + "#" + topPlayer.getEmail() + "#" + topPlayer.getPoints());
                string = string.concat(topPlayer.getName() + "\t\t\t\t\t\t\t" + topPlayer.getPoints() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (string.length() <= 0)
                    throw new UnsupportedOperationException("String has no elements");
                instructionText.setText(string);
            } catch (Exception e){
                e.printStackTrace();
                instructionText.setText("Please connect to the internet");
            }
        }
    }
}
