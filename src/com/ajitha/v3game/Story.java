package com.ajitha.v3game;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.Display;
import android.view.KeyEvent;

public class Story extends SimpleBaseGameActivity{

	private float actualRatio;
	private boolean yStretched;
	private float yScaleFactor;
	private boolean xStretched;
	private float xScaleFactor;
	private Camera camera;
	private BuildableBitmapTextureAtlas loadItemsTextureAtlas4;
	private TextureRegion aTR;
	private TextureRegion bTR;
	private TextureRegion cTR;
	private TextureRegion eTR;
	private TextureRegion dTR;
	private TextureRegion fTR;
	private Sprite b;
	private Sprite c;
	private Sprite d;
	private Sprite e;
	private Sprite a;
	private Sprite f;
	private Sprite g;

	@Override
	public EngineOptions onCreateEngineOptions() {
		Handler handler = new Handler();
		Handler handler2 = new Handler();
		Display display = getWindowManager().getDefaultDisplay();
		int actualWidth = display.getWidth();
		int actualHeight = display.getHeight();
		
		
		SharedPreferences sp = getSharedPreferences("LEVEL", 0);
		int levelid = sp.getInt("CURRENTLEVEL", 7);
		
		
		
		actualRatio = (float)actualHeight/(float)actualWidth;
		
		if (  Math.abs(actualRatio-1.6) < 0.01 ){
			
		}
		else if(actualRatio > 1.6){
			yStretched = true;
			float z = (float)actualWidth / 800 * 1280 ;
			yScaleFactor = ( z / actualHeight  );
			System.out.println(actualRatio + "  " + yScaleFactor);
		}
		else{
			xStretched = true;
			float z = (float)actualHeight / 1280 * 800 ;
			xScaleFactor = ( z / actualWidth  );
			System.out.println(actualRatio + "  " + xScaleFactor);
		}
		
		camera = new Camera(0, 0, 640, 1024);
		
		EngineOptions eo = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
				new FillResolutionPolicy(), camera);
		eo.getAudioOptions().setNeedsMusic(true);
		eo.getAudioOptions().setNeedsSound(true);
		return eo;
	}

	@Override
	protected void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		loadItemsTextureAtlas4 = new BuildableBitmapTextureAtlas(getTextureManager(), 1024, 1024 , TextureOptions.BILINEAR);
		aTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas4, this, "storya.png");
		bTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas4, this, "storyb.png");
		cTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas4, this, "storyc.png");
		dTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas4, this, "storyd.png");
		eTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas4, this, "storye.png");
		fTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas4, this, "storyf.png");
		
		try {
			loadItemsTextureAtlas4.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(5, 5, 5));
			loadItemsTextureAtlas4.load();
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected Scene onCreateScene() {
		// TODO Auto-generated method stub
		Scene sc = new Scene();
		sc.setBackground(new Background(0,0,0));
		
		a = new Sprite(0, 0, aTR, getVertexBufferObjectManager());
		b = new Sprite(346, 0, bTR, getVertexBufferObjectManager());
		c = new Sprite(346, 190, cTR, getVertexBufferObjectManager());
		d = new Sprite(0, 400, dTR, getVertexBufferObjectManager());
		e = new Sprite(346, 538, eTR, getVertexBufferObjectManager());
		f = new Sprite(0, 815, fTR, getVertexBufferObjectManager());
		
		a.setAlpha(0);
		b.setAlpha(0);
		c.setAlpha(0);
		d.setAlpha(0);
		e.setAlpha(0);
		f.setAlpha(0);
		
		sc.attachChild(a);
		sc.attachChild(b);
		sc.attachChild(c);
		sc.attachChild(d);
		sc.attachChild(e);
		sc.attachChild(f);
		
		final SequenceEntityModifier seq1 = new SequenceEntityModifier(new AlphaModifier(0.5f, 0, 1), new AlphaModifier(2, 1, 1));
		final SequenceEntityModifier seq2 = new SequenceEntityModifier(new AlphaModifier(0.5f, 0, 1), new AlphaModifier(2, 1, 1));
		final SequenceEntityModifier seq3 = new SequenceEntityModifier(new AlphaModifier(0.5f, 0, 1), new AlphaModifier(2, 1, 1));
		final SequenceEntityModifier seq4 = new SequenceEntityModifier(new AlphaModifier(0.5f, 0, 1), new AlphaModifier(2, 1, 1));
		final SequenceEntityModifier seq5 = new SequenceEntityModifier(new AlphaModifier(0.5f, 0, 1), new AlphaModifier(2, 1, 1));
		final SequenceEntityModifier seq6 = new SequenceEntityModifier(new AlphaModifier(0.5f, 0, 1), new AlphaModifier(3, 1, 1));
		
		seq1.addModifierListener(new IModifierListener<IEntity>() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				b.registerEntityModifier(seq2);
			}
		});

		seq2.addModifierListener(new IModifierListener<IEntity>() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				c.registerEntityModifier(seq3);
			}
		});

		seq3.addModifierListener(new IModifierListener<IEntity>() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				d.registerEntityModifier(seq4);
			}
		});
		seq4.addModifierListener(new IModifierListener<IEntity>() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				e.registerEntityModifier(seq5);
			}
		});
		seq5.addModifierListener(new IModifierListener<IEntity>() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				f.registerEntityModifier(seq6);
			}
		});
		
		seq6.addModifierListener(new IModifierListener<IEntity>() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Story.this, Test.class);
				finish();
				startActivity(intent);
			}
		});
		
		
		a.registerEntityModifier(seq1);
		return sc;
	};
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN ) {
			return false;
		}
		else{
			return true;
		}
	}
}
