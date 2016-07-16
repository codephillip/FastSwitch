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

	// ======================== Splash Resources ================= //
//	public static ITextureRegion splashTextureRegion;
	
	//GAME RESOURCES
	public BitmapTextureAtlas backgroundTextureAtlas;
	public static ITextureRegion backgroundTextureRegion;

	public BitmapTextureAtlas explosionTextureAtlas;
	public static ITiledTextureRegion explosionTiledTextureRegion;

	public BitmapTextureAtlas fruitTextureAtlas;
	public static ITiledTextureRegion fruitTiledTextureRegion;

	public BitmapTextureAtlas heartTextureAtlas;
	public static ITextureRegion heartITextureRegion;

	public BitmapTextureAtlas coinTextureAtlas;
	public static ITextureRegion coinITextureRegion;

	public BitmapTextureAtlas menuButtonsTextureAtlas;
	public static ITextureRegion playITextureRegion;
	public static ITextureRegion restartITextureRegion;
	public static ITextureRegion resumeITextureRegion;
	public static ITextureRegion menuITextureRegion;

	public static Font font, bountyFont;
	public static Font menuFont, winOrLoseFont;

	public static Music gameSound;
	public static Sound wrongTileSound, rightTileSound, lifeUpSound, lifeDownSound, deathSound, bountySound;

	private ResourceManager(){
	}

	public static ResourceManager getInstance(){
		if (INSTANCE == null) INSTANCE = new ResourceManager();
		return INSTANCE;
	}

	public void setup(final Engine pEngine, final Context pContext, final float pCameraWidth, final float pCameraHeight){
		engine = pEngine;
		context = pContext;
		cameraWidth = pCameraWidth;
		cameraHeight = pCameraHeight;
	}

	public static void loadSplashScreenResources(){
//		getInstance().loadSplashTextures();
//		getInstance().loadSharedResources();
//		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	}
	
	// Loads all game resources.
	public static void loadGameScreenResources() {
		getInstance().loadGameTextures();
		getInstance().loadSharedResources();
	}
	
	// Loads all menu resources
	public static void loadMenuScreenResources() {
		getInstance().loadMenuTextures();
//		getInstance().loadSharedResources();
	}
	
	// Unloads all game resources.
	public static void unloadGameResources() {
		getInstance().unloadGameTextures();
	}

	// Unloads all menu resources
	public static void unloadMenuResources() {
		getInstance().unloadMenuTextures();
	}
	
	// Unloads all shared resources
	public static void unloadSharedResources() {
		getInstance().unloadSharedTextures();
		getInstance().unloadSounds();
		getInstance().unloadFonts();
	}
	
	//====================================================
	// PRIVATE METHODS
	//====================================================
	// Loads resources used by both the game scenes and menu scenes
	private void loadSharedResources(){
		loadSharedTextures();
		loadSounds();
		loadFonts();
	}

//	private void loadSplashTextures(){
//		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
//		// background texture - only load it if we need to:
//		if(splashTextureRegion==null) {
//			BitmapTextureAtlas splashTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
//			splashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, context, "splash.png", 0, 0);
//			splashTextureAtlas.load();
//		}
//	}
//
//	private void unloadSplashTextures(){
//		// background texture - only unload it if it is loaded:
//		if(splashTextureRegion!=null) {
//			if(splashTextureRegion.getTexture().isLoadedToHardware()) {
//				splashTextureRegion.getTexture().unload();
//				splashTextureRegion = null;
//			}
//		}
//	}
	
	private void loadGameTextures(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		menuButtonsTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 665, 210, TextureOptions.BILINEAR);
		menuButtonsTextureAtlas.load();

		BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuButtonsTextureAtlas, context, "buttons_spritesheet.png", 0, 0);
		playITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas, 2, 70, 221, 60);
		restartITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas, 223, 70, 221, 60);
		resumeITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas, 444, 70, 221, 60);
		menuITextureRegion = TextureRegionFactory.extractFromTexture(menuButtonsTextureAtlas, 444, 0, 221, 60);

		backgroundTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 1024, 512, TextureOptions.DEFAULT);
		backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureAtlas, context, "background.png", 0, 0);
		backgroundTextureAtlas.load();

		fruitTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 552, 414, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		fruitTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(fruitTextureAtlas, context, "spritesheet1.png", 0, 0, 4, 3);
		fruitTextureAtlas.load();

		explosionTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 768, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		explosionTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(explosionTextureAtlas, context, "good_explosion.png", 0, 0, 3, 4);
		explosionTextureAtlas.load();

		heartTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		heartITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(heartTextureAtlas, context, "heart.png", 0, 0);
		heartTextureAtlas.load();

		coinTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		coinITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(coinTextureAtlas, context, "coin.png", 0, 0);
		coinTextureAtlas.load();

		font = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
				"fnt/pipedream.ttf", 46, true, Color.BLACK);
		font.load();

		bountyFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
				"fnt/pipedream.ttf", 80, true, Color.GREEN);
		bountyFont.load();

		menuFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 256, context.getAssets(),
				"fnt/pipedream.ttf", 60, true, Color.BLACK);
		menuFont.load();
	}
//	// ============================ UNLOAD TEXTURES (GAME) =============== //
	private void unloadGameTextures(){
//		// background texture - only unload it if it is loaded:
//		if(gameBackgroundTextureRegion!=null) {
//			if(gameBackgroundTextureRegion.getTexture().isLoadedToHardware()) {
//				gameBackgroundTextureRegion.getTexture().unload();
//				gameBackgroundTextureRegion = null;
//			}
//		}
	}

	// ============================ LOAD TEXTURES (MENU) ================= //
	private void loadMenuTextures(){
//		// Store the current asset base path to apply it after we've loaded our textures
//		mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
//		// Set our menu assets folder to "assets/gfx/menu/"
//		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
//
//		// background texture:
//		if(menuBackgroundTextureRegion==null) {
//			BuildableBitmapTextureAtlas texture = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 11, 490);
//			menuBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture, context, "background.png");
//			try {
//				texture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 4));
//				texture.load();
//			} catch (TextureAtlasBuilderException e) {
//				Debug.e(e);
//			}
//		}
//
//		// Revert the Asset Path.
//		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
	}
	// ============================ UNLOAD TEXTURES (MENU) =============== //
	private void unloadMenuTextures(){
//		// background texture:
//		if(menuBackgroundTextureRegion!=null) {
//			if(menuBackgroundTextureRegion.getTexture().isLoadedToHardware()) {
//				menuBackgroundTextureRegion.getTexture().unload();
//				menuBackgroundTextureRegion = null;
//			}
//		}
	}
	
	// ============================ LOAD TEXTURES (SHARED) ================= //
	private void loadSharedTextures(){

	}
	// ============================ UNLOAD TEXTURES (SHARED) ============= //
	private void unloadSharedTextures(){
	}
	
	// =========================== LOAD SOUNDS ======================== //
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
	// =========================== UNLOAD SOUNDS ====================== //
	private void unloadSounds(){
//		if(clickSound!=null)
//			if(clickSound.isLoaded()) {
//				// Unload the clickSound object. Make sure to stop it first.
//				clickSound.stop();
//				engine.getSoundManager().remove(clickSound);
//				clickSound = null;
//			}
	}

	// ============================ LOAD FONTS ========================== //
	private void loadFonts(){
//		// Create the Font objects via FontFactory class
//		if(fontDefault32Bold==null) {
//			fontDefault32Bold = FontFactory.create(engine.getFontManager(), engine.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD),  32f, true, Color.WHITE_ARGB_PACKED_INT);
//			fontDefault32Bold.load();
//		}
//		if(fontDefault72Bold==null) {
//			fontDefault72Bold = FontFactory.create(engine.getFontManager(), engine.getTextureManager(), 512, 512, Typeface.create(Typeface.DEFAULT, Typeface.BOLD),  72f, true, Color.WHITE_ARGB_PACKED_INT);
//			fontDefault72Bold.load();
//		}
	}
	// ============================ UNLOAD FONTS ======================== //
	private void unloadFonts(){
//		// Unload the fonts
//		if(fontDefault32Bold!=null) {
//			fontDefault32Bold.unload();
//			fontDefault32Bold = null;
//		}
//		if(fontDefault72Bold!=null) {
//			fontDefault72Bold.unload();
//			fontDefault72Bold = null;
//		}
	}
}