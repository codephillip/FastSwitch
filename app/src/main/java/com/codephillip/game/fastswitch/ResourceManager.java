package com.codephillip.game.fastswitch;

import android.content.Context;
import android.graphics.Color;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import java.io.IOException;

public class ResourceManager extends Object {

	private static ResourceManager INSTANCE = null;

	public static Engine engine;
	public static Context context;
	public float cameraWidth;
	public float cameraHeight;
	
	public BitmapTextureAtlas backgroundTextureAtlas;
	public static ITextureRegion backgroundTextureRegion;

	public BitmapTextureAtlas overlayTextureAtlas;
	public static ITextureRegion overlayTextureRegion;

	public BitmapTextureAtlas explosionTextureAtlas;
	public static ITiledTextureRegion explosionTiledTextureRegion;

	public BitmapTextureAtlas fruitTextureAtlas;
	public static ITiledTextureRegion fruitTiledTextureRegion;

	public BitmapTextureAtlas heartTextureAtlas;
	public static ITextureRegion heartITextureRegion;

	public BitmapTextureAtlas coinTextureAtlas;
	public static ITextureRegion coinITextureRegion;

	public BitmapTextureAtlas menuButtonsTextureAtlas;
	public static ITextureRegion exitITextureRegion;
	public static ITextureRegion playITextureRegion;
	public static ITextureRegion restartITextureRegion;
	public static ITextureRegion resumeITextureRegion;
	public static ITextureRegion menuITextureRegion;
	public static ITextureRegion pauseITextureRegion;
	public static ITextureRegion instructionsITextureRegion;
	public static ITextureRegion nextLevelITextureRegion;
	public static ITextureRegion topITextureRegion;

	public static Font font, bountyFont;
	public static Font menuFont, smallMenuFont, winOrLoseFont;

	public static Music gameSound;
	public static Sound wrongTileSound, rightTileSound, lifeUpSound, lifeDownSound, deathSound, bountySound;

	private ResourceManager(){
	}

	public static ResourceManager getInstance(){
		if (INSTANCE == null) 
			INSTANCE = new ResourceManager();
		return INSTANCE;
	}

	public void setup(final Engine pEngine, final Context pContext, final float pCameraWidth, final float pCameraHeight){
		engine = pEngine;
		context = pContext;
		cameraWidth = pCameraWidth;
		cameraHeight = pCameraHeight;
	}
	
	// Loads all game resources.
	public static void loadGameScreenResources() {
		getInstance().loadGameTextures();
		getInstance().loadSounds();
		getInstance().loadFonts();
	}
	
	private void loadGameTextures(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		menuButtonsTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 250, 500, TextureOptions.BILINEAR);
		menuButtonsTextureAtlas.load();

		BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuButtonsTextureAtlas, context, "button_sprite2.png", 0, 0);
		exitITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas, 2, 2, 250, 60);
		int value = 6;
		instructionsITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas, 2, 60-value, 250, 60);
		menuITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas, 2, 118-value, 250, 60);
		nextLevelITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas, 2, 176-value, 250, 60);
		playITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas, 2, 234-value, 250, 60);
		restartITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas, 2, 292-value, 250, 60);
		resumeITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas, 2, 350-value, 250, 60);
		topITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas, 2, 408-value, 250, 60);

		backgroundTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 1024, 512, TextureOptions.DEFAULT);
		backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureAtlas, context, "background.png", 0, 0);
		backgroundTextureAtlas.load();

		overlayTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 775, 431, TextureOptions.DEFAULT);
		overlayTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(overlayTextureAtlas, context, "overlay.png", 0, 0);
		overlayTextureAtlas.load();

		fruitTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 900, 500, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		fruitTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(fruitTextureAtlas, context, "spritesheet1.png", 0, 0, 4, 3);
		fruitTextureAtlas.load();

		explosionTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 768, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		explosionTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(explosionTextureAtlas, context, "explosion.png", 0, 0, 3, 4);
		explosionTextureAtlas.load();

		heartTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		heartITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(heartTextureAtlas, context, "heart.png", 0, 0);
		heartTextureAtlas.load();

		coinTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		coinITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(coinTextureAtlas, context, "coin.png", 0, 0);
		coinTextureAtlas.load();
	}
	
	private void loadSounds(){
		if(gameSound==null) {
			try {
				gameSound = MusicFactory.createMusicFromAsset(engine.getMusicManager(), context, "mfx/game_music.mp3");
				wrongTileSound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), context, "mfx/wrong_tile.ogg");
				rightTileSound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), context, "mfx/right_tile.ogg");
				lifeUpSound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), context, "mfx/life_up.ogg");
				bountySound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), context, "mfx/bounty.ogg");
				lifeDownSound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), context, "mfx/death.ogg");
				deathSound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), context, "mfx/death.ogg");
				gameSound.setLooping(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void loadFonts(){
		font = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
				"fnt/pipedream.ttf", 46, true, Color.BLACK);
		font.load();

		bountyFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
				"fnt/pipedream.ttf", 80, true, Color.GREEN);
		bountyFont.load();

		menuFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
				"fnt/pipedream.ttf", 60, true, Color.WHITE);
		menuFont.load();

		smallMenuFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
				"fnt/pipedream.ttf", 46, true, Color.WHITE);
		smallMenuFont.load();
	}
}