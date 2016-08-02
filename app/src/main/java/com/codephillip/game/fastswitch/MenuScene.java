package com.codephillip.game.fastswitch;

import android.content.Context;

import org.andengine.engine.Engine;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.util.adt.color.Color;

/**
 * Created by codephillip on 7/15/16.
 */
public class MenuScene extends Scene {
    private Engine engine;
    private Context context;
    private Sprite playSprite;
    private Sprite backgroundSprite;
    private Sprite instructionSprite;
    private Sprite overlaySprite;

    public MenuScene(Context context, Engine engine) {
        this.context = context;
        this.engine = engine;
        attachChild(null);
        registerTouchArea(null);
        Utils.resumeAds();
        Utils.logAnalyticsScene("MenuScene");
    }

    @Override
    public void attachChild(IEntity pEntity) {
        backgroundSprite = new Sprite(Utils.positionX, Utils.positionY, ResourceManager.backgroundTextureRegion, engine.getVertexBufferObjectManager());

        overlaySprite = new Sprite(Utils.positionX, Utils.positionY, ResourceManager.overlayTextureRegion, engine.getVertexBufferObjectManager());
        overlaySprite.setColor(Color.BLACK);
        overlaySprite.setAlpha(0.7f);

        playSprite = new Sprite(Utils.CAMERA_WIDTH / 2 + 20, Utils.CAMERA_HEIGHT / 2  - 50, ResourceManager.playITextureRegion, engine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent superTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (superTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        this.setAlpha(0.5f);
                        break;
                    case TouchEvent.ACTION_UP:
                        this.setAlpha(1.0f);
                        clearChildScene();
                        Utils.resetLevel();
                        SceneManager.setCurrentScene(AllScenes.STAGE, SceneManager.createStageScene());
                        break;
                }
                return super.onAreaTouched(superTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        instructionSprite = new Sprite(Utils.CAMERA_WIDTH / 2 + 20, Utils.CAMERA_HEIGHT / 2 - 100, ResourceManager.instructionsITextureRegion, engine.getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent superTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                switch (superTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        this.setAlpha(0.5f);
                        break;
                    case TouchEvent.ACTION_UP:
                        this.setAlpha(1.0f);
                        clearChildScene();
                        SceneManager.setCurrentScene(AllScenes.INSTRUCTION, SceneManager.createInstructionScene());
                        break;
                }
                return super.onAreaTouched(superTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        Font titleFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
                "fnt/sanchez.ttf", 100, true, android.graphics.Color.YELLOW);
        titleFont.load();

        Text titleText = new Text(0, 0, titleFont, "FAST SWITCH", 25, engine.getVertexBufferObjectManager());
        titleText.setPosition(Utils.CAMERA_WIDTH / 2, Utils.CAMERA_HEIGHT / 2 + 150);

        Font font = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
                "fnt/pipedream.ttf", 40, true, android.graphics.Color.WHITE);
        font.load();

        Text nicknameText = new Text(0, 0, font, "nickname", 50, engine.getVertexBufferObjectManager());
        nicknameText.setPosition(Utils.CAMERA_WIDTH / 2 + 20, Utils.CAMERA_HEIGHT / 2+ 70);
        nicknameText.setText(getNickname());

        Text pointsText = new Text(0, 0, ResourceManager.pointsFont, "24235", 30, engine.getVertexBufferObjectManager());
        pointsText.setPosition(Utils.CAMERA_WIDTH / 2 + 20, Utils.CAMERA_HEIGHT / 2+ 40);
        //TODO make total points in Utils
        pointsText.setText(getNickname());

        super.attachChild(backgroundSprite);
        super.attachChild(overlaySprite);
        super.attachChild(titleText);
        super.attachChild(nicknameText);
        super.attachChild(pointsText);
        super.attachChild(playSprite);
        super.attachChild(instructionSprite);

    }

    @Override
    public void registerTouchArea(ITouchArea pTouchArea) {
        super.registerTouchArea(playSprite);
        super.registerTouchArea(instructionSprite);
    }

    private String getNickname(){
        return Utils.getEmail().split("@")[0];
    }
}
