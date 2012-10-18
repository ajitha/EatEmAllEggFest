
/*
   Copyright 2012 Ajitha Samarasinghe

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package com.ajitha.v3game;

import java.io.IOException;
import java.util.ArrayList;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.CubicBezierCurveMoveModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.sprite.vbo.ITiledSpriteVertexBufferObject;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;
import org.andengine.util.modifier.ease.EaseBackOut;
import org.andengine.util.modifier.ease.EaseExponentialIn;
import org.andengine.util.modifier.ease.EaseExponentialInOut;
import org.andengine.util.modifier.ease.EaseExponentialOut;
import org.andengine.util.modifier.ease.EaseStrongOut;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


/* 
* @author Ajitha Samarasinghe
* 
*/

public class MainMenu extends BaseGameActivity{
	// ===========================================================
		// Constants
		// ===========================================================
		
		private static final int CAMERA_WIDTH = 800;
		private static final int CAMERA_HEIGHT = 1280;

		// ===========================================================
		// Fields
		// ===========================================================

		
		private BuildableBitmapTextureAtlas buttonsAtlas;
		private TiledTextureRegion playTextureRegion;
		private TiledTextureRegion optionsTextureRegion;
		private TiledTextureRegion levelsTextureRegion;
		private Camera camera;
		private Handler handler;
		private Display display;
		private int actualWidth;
		private int actualHeight;
		private float actualRatio;
		private boolean yStretched;
		private float yScaleFactor;
		private boolean xStretched;
		private float xScaleFactor;
		private BuildableBitmapTextureAtlas splashAtlas;
		private TextureRegion copyrightTextureRegion;
		private TextureRegion blackoutTextureRegion;
		private Sprite blackoutS;
		private Sprite copyS;
		private Scene splashScene;
		private Scene loadScene;
		private BuildableBitmapTextureAtlas loadTextureAtlas;
		private TextureRegion loadBackgroundTextureRegion;
		private TextureRegion shipLightTextureReagion;
		private Sprite loadBackground;
		private Sprite shipLight;
		private BuildableBitmapTextureAtlas loadItemsTextureAtlas;
		private TextureRegion eggthreeTR;
		private TextureRegion loadingTR;
		private TextureRegion labelbackTR;
		private TextureRegion emomanyTR;
		private TextureRegion eggmanyTR;
		private TextureRegion alienmedTR;
		private TextureRegion aliensmlTR;
		private TextureRegion alienbigTR;
		private TextureRegion labelTR;
		private TiledTextureRegion playTR;
		private TiledTextureRegion optionsTR;
		private TiledTextureRegion creditsTR;
		private Sprite medalien;
		private Sprite bigalien;
		private Sprite smlalien;
		private Sprite label;
		private Sprite loading;
		private Sprite eggthree;
		private Sprite play;
		private Sprite options;
		private Sprite credits;
		private Sprite labelback;
		private Sprite emos;
		private Sprite eggs;
		private TextureRegion twitterTR;
		private TextureRegion facebookTR;
		private Sprite tw;
		private Sprite fb;
		private Font unrealFont;
		private boolean firstLaunch;
		public static String [] levelNames = new String[]{"1.start",  "edit3.1",   "2",  "3.2",  "6",   "edit6.1",  "5.9",  "7"  ,"8",  "9",
															"10.2",  "11.0",   "2.start5",    "12.0",  "13.0",  "14.0",   "15.0",  "16.0",  "17.0"  ,"18.0" ,
															"thunder.s",   "19.0", "20.0",  "22.2"  ,"24.4",  "3.start"  , "grenade"   ,"26.7", "27.1"  ,"28" ,
															
															"29",  "30.charges",   "31",  "32",  "33",   "34",  "35.flame2",  "35"  ,"36",  "37" 
															,"38" , "39" ,   "40" ,  "41",   "42"   ,"43",  "47real" , "45.1"
															
															
															
															
															,"h3",  "a1",   "a2",  "a3",  "a4",   "a5",  "a6",  "b11"  ,"b2",  "b2another2",
															"b333",  "b44",   "b5",    "b6",  "c1",  "c2",   "c4",  "c5",  "c6"  ,"c7" ,
															"thunder.s",   "19.0", "20.0",  "22.2"  ,"24.4",  "3.start"  , "grenade"   ,"26.7", "27.1"  ,"28" ,
															
															"c3",  "h1",   "h2",  "h4"
															
															 
		};
														
		private TiledTextureRegion levelsTR;
		public ArrayList<Level> levels = new ArrayList<Level>();
		public float startx = -100;
		public boolean readytoScoll = false;
		public boolean scollingInProgress = false;
		public boolean downFirst = false;
		public boolean onLevelMenu = false;
		public boolean onMainMenu = false;
		private boolean levelVisitedOnce = false;
		private boolean transitionOnProgress = false;
		private TextureRegion sureTR;
		private TextureRegion yesTR;
		private TextureRegion noTR;
		private Scene quitScene;
		private Sprite sure;
		private Sprite yes;
		private Sprite no;
		private Rectangle backfade;
		private boolean inquitmenu = false;
		private BuildableBitmapTextureAtlas loadItemsTextureAtlas2;
		private boolean menubuttonpressed = false;
		private Sprite emos2;
		private TiledTextureRegion toggleTR;
		private Font optionsFont;
		
		
		public boolean sound = true;
		public boolean music = true;
		public boolean particles = true;
		private Scene optionsScene;
		private Sprite musicL;
		private Sprite soundsL;
		private Sprite particlesL;
		private TiledSprite musicB;
		private TiledSprite soundsB;
		private TiledSprite particlesB;
		private boolean onOptionsMenu = false;
		private BuildableBitmapTextureAtlas loadItemsTextureAtlas3;
		private TextureRegion particlesTR;
		private TextureRegion soundTR;
		private TextureRegion musicTR;
		private Music bgmusic;
		private Music tapm;
		private Music lockedm;
		private Sprite labelsml;
		private TextureRegion lvlstarTR;
		private TextureRegion lvlstaremptyTR;
		
		public int totStars;
		private TextureRegion totStarTR;
		private Sprite totStar;
		private Text totStarlabel;
		private BuildableBitmapTextureAtlas loadItemsTextureAtlas4;
		private TextureRegion aTR;
		private TextureRegion bTR;
		private TextureRegion cTR;
		private TextureRegion dTR;
		private TextureRegion eTR;
		private TextureRegion fTR;
		private BuildableBitmapTextureAtlas loadItemsTextureAtlas5;
		private TextureRegion creditTR;
		private BuildableBitmapTextureAtlas loadItemsTextureAtlas6;
		private TextureRegion moreTR;
		private Sprite credit;
		
		public boolean oncredits = false;
		private Sprite more;
		private int levelid;
		private boolean supporteddevs;
		private boolean mIsPausedBefore;
		
		
		public TextureRegion lockNormalTR;
		public TextureRegion lockPaidTR;
		
		
		public boolean fullVersion ;
		public boolean onMapMenu;
		public ArrayList<Area> areas = new ArrayList<MainMenu.Area>();
		private Area selArea;
		private BuildableBitmapTextureAtlas AreasTextureAtlas1;
		private BuildableBitmapTextureAtlas mapbgTextureAtlas;
		private TextureRegion mapbgTR;
		private Sprite mapbg;
		private  TiledTextureRegion areaaTR;
		private  TiledTextureRegion areabTR;
		private  TiledTextureRegion areacTR;
		private TiledTextureRegion areadTR;
		private  TiledTextureRegion areaeTR;
		private  TiledTextureRegion areafTR;
		private TiledTextureRegion areagTR;
		private  TiledTextureRegion areahTR;
		private BuildableBitmapTextureAtlas AreasTextureAtlas2;
		private Text areaLabel;
		
		
		
		//for map
		public class Area extends TiledSprite{
			public float x;
			public float y;
			public int index;
			public boolean state;
			public Sprite lockNormal ;
			public Sprite lockPaid ;
			public Sprite star ;
			public int stars = 0;
			public int minLevel ;
			public int maxLevel ;
			
			public boolean selected = false; 
			
			public Text starLabel;
			
			public Area(
					float pX,
					float pY,
					float pWidth,
					float pHeight,
					ITiledTextureRegion pTiledTextureRegion,
					ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject) {
				super(pX, pY, pWidth, pHeight, pTiledTextureRegion,
						pTiledSpriteVertexBufferObject);
				// TODO Auto-generated constructor stub
			}
			
			public Area(float x, float y , int index , TiledTextureRegion areaTR){
				super(x, y, areaTR.getWidth(), areaTR.getHeight(), areaTR, mEngine.getVertexBufferObjectManager());
				
				this.x = x;
				this.y = y;
				this.index = index;
				
				SharedPreferences areaSF = getSharedPreferences("AREA", 0);
				this.state = areaSF.getBoolean("AREA"+index+"STATE", false);
				
				
				this.lockNormal = new Sprite(this.getWidth()/2 - lockNormalTR.getWidth()/2  , this.getHeight()/2 - lockNormalTR.getHeight()/2 , lockNormalTR.deepCopy(), getVertexBufferObjectManager());
				this.lockPaid = new Sprite(this.getWidth()/2 - lockPaidTR.getWidth()/2  , this.getHeight()/2 - lockPaidTR.getHeight()/2 , lockPaidTR.deepCopy(), getVertexBufferObjectManager());
			
//				minLevel = (index-1)*18 ;
//				maxLevel = minLevel + 17 ;
				
				minLevel = (index-1)*9 ;
				maxLevel = minLevel + 8 ;
				
				Log.d("aj", "           " +levels.size());
				
				for (int i = minLevel ; i <= maxLevel ; i ++){
					stars += levels.get(i).stars;
				}
				this.star = new Sprite(Area.this.getWidth()/2 - 150  ,Area.this.getHeight()/2 - 55 -10 , totStarTR.deepCopy(), getVertexBufferObjectManager());
				
				this.starLabel = new Text(Area.this.getWidth()/2 - 150 + 75 +25  , Area.this.getHeight()/2 - 50 -20 ,unrealFont , "x"+stars+"/27" ,new TextOptions(HorizontalAlign.LEFT),getVertexBufferObjectManager());
				//AutoWrap.CJK, 200f,
				
				this.starLabel.setColor(Color.WHITE);
				this.starLabel.setScale(0.7f);
				
				this.star.setScaleCenterX(star.getWidth());
				this.starLabel.setScaleCenterX(0);
				
				this.star.setScale(this.star.getScaleX() * 0.5f);
				this.starLabel.setScale(this.starLabel.getScaleX() * 0.5f);
				
				starLabel.setAlpha(0);
				star.setAlpha(0);
				lockNormal.setAlpha(0);
				lockPaid.setAlpha(0);
				
			}
			
			
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()){
					
					if(state == true){
						if(transitionOnProgress == false){
							
							
							SharedPreferences asp = getSharedPreferences("AREA", 0);
							SharedPreferences.Editor aspe = asp.edit();
							aspe.putInt("CURRENTAREA", this.index);
							aspe.commit();
							
							areaLabel.setText("--Area "+this.index+"--");
							MapToLevelThen(Area.this);
						}
					}
					
				}
				
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
			
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				
				// state 0 = unlocked , state 1 = locked
				
				if(fullVersion && state){
					this.setCurrentTileIndex(1);
					lockNormal.setVisible(false);
					lockPaid.setVisible(false);
					
					star.setVisible(true);
					starLabel.setVisible(true);
				}
				if(fullVersion && !state){
					this.setCurrentTileIndex(0);
					lockNormal.setVisible(true);
					lockPaid.setVisible(false);
					
					star.setVisible(false);
					starLabel.setVisible(false);
				}
				if(!fullVersion && state){
					this.setCurrentTileIndex(1);
					lockNormal.setVisible(false);
					lockPaid.setVisible(false);
					
					star.setVisible(true);
					starLabel.setVisible(true);
				}
				if(!fullVersion && !state){
					this.setCurrentTileIndex(0);
					lockNormal.setVisible(false);
					lockPaid.setVisible(true);
					
					star.setVisible(false);
					starLabel.setVisible(false);
				}
				
				
				super.onManagedUpdate(pSecondsElapsed);
			}
			
			
			
		}
		
		public class Level extends TiledSprite{
			public float start;
			public float end;
			public int index;
			public String name;
			public boolean unlocked;
			public int stars;
			public Text label;
			public int page;
			public int column;
			public int row;
			public Text starlabel;
			public Sprite s1;
			public Sprite s2;
			public Sprite s3;
		
			public Level(
					float pX,
					float pY,
					float pWidth,
					float pHeight,
					ITiledTextureRegion pTiledTextureRegion,
					ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject) {
				super(pX, pY, pWidth, pHeight, pTiledTextureRegion,
						pTiledSpriteVertexBufferObject);
			
			}
			public Level(int index){
				
				
//				super( 800*(index/ 9 +1 ) +75 + (index % 3)*225, 300 + ((index %9)/3)*225 , 200, 200, levelsTR,mEngine.getVertexBufferObjectManager());
				
				
				//for map
//				super( 800*(  (index/9)%2 +1 ) +75 + (index % 3)*225, 300 + ((index %9)/3)*225 , 200, 200, levelsTR,mEngine.getVertexBufferObjectManager());
				
//				super( 800*(  (index/9)%2 +1 ) +75 + (index % 3)*225  - 800, 300 + ((index %9)/3)*225 + 1280 , 200, 200, levelsTR,mEngine.getVertexBufferObjectManager());
				
				super( 75 + (index % 3)*225 , 300 + ((index %9)/3)*225 + 1280 , 200, 200, levelsTR,mEngine.getVertexBufferObjectManager());
				
				
				SharedPreferences levelsp = getSharedPreferences("LEVEL", 0);
				this.index = index;
				this.name = levelsp.getString("LEVEL"+index+"NAME", "noname");
				this.unlocked = levelsp.getBoolean("LEVEL"+index+"UNLOCKED", false);
				
				this.setCullingEnabled(true);
				this.stars = levelsp.getInt("LEVEL"+index+"STARS", 0);
				int starT = levelsp.getInt("LEVEL"+index+"STARS", 0);
				
				page = index/ 9 ;
				column = index % 3 ;
				row = (index %9)/3 ;
				
//				int labeltext = index + 1 ;
				int labeltext = (index)%9 + 1 ;
				
				
				label = new Text(0, 30,unrealFont , ""+labeltext,new TextOptions(AutoWrap.CJK, 200f,HorizontalAlign.CENTER),getVertexBufferObjectManager());
				label.setColor(Color.WHITE);
				
				if(starT == 0){
					s1= new Sprite(30, 137, lvlstaremptyTR.deepCopy(), getVertexBufferObjectManager());
					s2= new Sprite(78, 137, lvlstaremptyTR.deepCopy(), getVertexBufferObjectManager());
					s3= new Sprite(126, 137, lvlstaremptyTR.deepCopy(), getVertexBufferObjectManager());
				}
				
				if(starT == 1){
					s1= new Sprite(30, 137, lvlstarTR.deepCopy(), getVertexBufferObjectManager());
					s2= new Sprite(78, 137, lvlstaremptyTR.deepCopy(), getVertexBufferObjectManager());
					s3= new Sprite(126, 137, lvlstaremptyTR.deepCopy(), getVertexBufferObjectManager());
				}
				if(starT == 2){
					s1= new Sprite(30, 137, lvlstarTR.deepCopy(), getVertexBufferObjectManager());
					s2= new Sprite(78, 137, lvlstarTR.deepCopy(), getVertexBufferObjectManager());
					s3= new Sprite(126, 137, lvlstaremptyTR.deepCopy(), getVertexBufferObjectManager());
				}
				
				//to eleminate initstars 6 problem
				if(starT >= 3){
					s1= new Sprite(30, 137, lvlstarTR.deepCopy(), getVertexBufferObjectManager());
					s2= new Sprite(78, 137, lvlstarTR.deepCopy(), getVertexBufferObjectManager());
					s3= new Sprite(126, 137, lvlstarTR.deepCopy(), getVertexBufferObjectManager());
				}
				
				
				
				
			}
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				if(pSceneTouchEvent.isActionDown()){
					
					this.setScale(1.1f);
					if(sound){
						if(unlocked){
							tapm.play();
						}
						else{
							lockedm.play();
						}
					}
					
					handler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							
							setScale(1);
							
							if(unlocked){
								SharedPreferences sp = getSharedPreferences("LEVEL", 0);
								SharedPreferences.Editor editor = sp.edit();
								editor.putInt("CURRENTLEVEL", index);
								editor.commit();
								
								
								handler.postDelayed(new Runnable() {
									
									@Override
									public void run() {
										
										SharedPreferences sp = getSharedPreferences("LOADING", 0);
										SharedPreferences.Editor editor = sp.edit();
										editor.putBoolean("loadenable", false);
										editor.commit();
										
										if(index == 0){
											Intent intent = new Intent(MainMenu.this, Story.class);
											finish();
											startActivity(intent);
										}
										else{
											Intent intent = new Intent(MainMenu.this, Test.class);
											finish();
											startActivity(intent);
										}
										
										
									}
								}, 2);
								
							}
							
						}
					}, 100);
				}
				
				return super
						.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
			
			
			
		}
		
		
		@Override
		public EngineOptions onCreateEngineOptions() {
			
			SharedPreferences CL = getSharedPreferences("LEVEL", 0);
			levelid = CL.getInt("CURRENTLEVEL", 0);
			
			SharedPreferences suportdevs = getSharedPreferences("DIALOG", 0);
			supporteddevs = suportdevs.getBoolean("SHOWN", false);
			
			SharedPreferences firstLaunchpref = getSharedPreferences("FIRST_LAUNCH", 0);
			firstLaunch = firstLaunchpref.getBoolean("firstlaunch", true);
			
			
			
			
			/// in the first launch
			if(firstLaunch){
				SharedPreferences firstLaunchpref2 = getSharedPreferences("FIRST_LAUNCH", 0);
				SharedPreferences.Editor editor = firstLaunchpref2.edit();
				editor.putBoolean("firstlaunch", false);
				editor.commit();
				
				initializeSharedPrefs();
			}
			
			setnamestoPrefs();
			
			
			SharedPreferences version = getSharedPreferences("VERSION", 0);
			fullVersion = version.getBoolean("FULLVERSION", true);
			
			
			SharedPreferences loading = getSharedPreferences("LOADING", 0);
			loading.getBoolean("loadenable", true);
			
			SharedPreferences options = getSharedPreferences("OPTIONS", 0);
			sound = options.getBoolean("SOUNDS", true);
			music = options.getBoolean("MUSIC", true);
			particles = options.getBoolean("PARTICLES", true);
			
			handler = new Handler();
			
			display = getWindowManager().getDefaultDisplay();
			actualWidth = display.getWidth();
			actualHeight = display.getHeight();
			
			
			actualRatio = (float)actualHeight/(float)actualWidth;
			
			if (  Math.abs(actualRatio-1.6) < 0.01 ){
				
			}
			else if(actualRatio > 1.6){
				yStretched = true;
				float z = (float)actualWidth / 800 * 1280 ;
				yScaleFactor = ( z / actualHeight  );

			}
			else{
				xStretched = true;
				float z = (float)actualHeight / 1280 * 800 ;
				xScaleFactor = ( z / actualWidth  );
				System.out.println(actualRatio + "  " + xScaleFactor);
			}
			
			camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
			
			EngineOptions eo = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
					new FillResolutionPolicy(), camera);
			eo.getAudioOptions().setNeedsMusic(true);
			eo.getAudioOptions().setNeedsSound(true);
			
			return eo;
		}
		
		
		private void initializeSharedPrefs() {
			SharedPreferences levelsp = getSharedPreferences("LEVEL", 0);
			SharedPreferences.Editor editor = levelsp.edit();
			
			for(int i = 0 ; i < levelNames.length ; i ++){
				if(i==0){
					editor.putString("LEVEL"+i+"NAME" , levelNames[i]  );
					editor.putInt("LEVEL"+1+"INDEX", i);
					editor.putBoolean("LEVEL"+i+"UNLOCKED", true);
					editor.putInt("LEVEL"+1+"STARS", 0);
				}
				else{
					editor.putString("LEVEL"+i+"NAME" , levelNames[i]  );
					editor.putInt("LEVEL"+1+"INDEX", i);
					editor.putBoolean("LEVEL"+i+"UNLOCKED", false);
					editor.putInt("LEVEL"+1+"STARS", 0);
				}
				
			}
			editor.commit();
			
			//far map
			SharedPreferences areasp = getSharedPreferences("AREA", 0);
			SharedPreferences.Editor ed = areasp.edit();
			
			ed.putBoolean("AREA1STATE", true);
//			ed.putBoolean("AREA2STATE", true);
//			ed.putBoolean("AREA3STATE", true);
//			ed.putBoolean("AREA4STATE", true);
//			ed.putBoolean("AREA5STATE", true);
//			ed.putBoolean("AREA6STATE", true);
//			ed.putBoolean("AREA7STATE", true);
//			ed.putBoolean("AREA8STATE", true);
			
			ed.commit();
			
			SharedPreferences versionsp = getSharedPreferences("VERSION", 0);
			SharedPreferences.Editor e = versionsp.edit();
			
			
			//for full version
			e.putBoolean("FULLVERSION", true);
			
			//for free
//			e.putBoolean("FULLVERSION", false);
			
			e.commit();
		}
		
		private void setnamestoPrefs() {
			SharedPreferences levelsp = getSharedPreferences("LEVEL", 0);
			SharedPreferences.Editor editor = levelsp.edit();
			
			for(int i = 0 ; i < levelNames.length ; i ++){
					editor.putString("LEVEL"+i+"NAME" , levelNames[i]  );
			}
			editor.commit();
			
			
		}

		public void initSplash(){
			splashScene = new Scene();
			splashScene.setBackground(new Background(1,1,1));
			blackoutS = new Sprite(0, 500, blackoutTextureRegion, getVertexBufferObjectManager()){
				@Override
				public void setAlpha(float pAlpha) {
				
					super.setAlpha(pAlpha);
					super.setColor(pAlpha, pAlpha, pAlpha);
				}
			};
			copyS = new Sprite(0, 1200, copyrightTextureRegion, getVertexBufferObjectManager()){
				@Override
				public void setAlpha(float pAlpha) {
					
					super.setAlpha(pAlpha);
					super.setColor(pAlpha, pAlpha, pAlpha);
				}
			};
			this.blackoutS.setAlpha(1);
			this.copyS.setAlpha(1);
			
			splashScene.attachChild(blackoutS);
			splashScene.attachChild(copyS);
			
			
		}
		
		@Override
		public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback)throws Exception {
			
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
			splashAtlas = new BuildableBitmapTextureAtlas(getTextureManager(), 1024, 256 , TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			copyrightTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashAtlas, this, "copyright.png");
			blackoutTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashAtlas, this, "blackout.png");
			
			this.splashAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
			this.splashAtlas.load();
			
			loadTextureAtlas = new BuildableBitmapTextureAtlas(getTextureManager(), 1024, 1024 , TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			loadBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadTextureAtlas, this, "loadinga.png");
			shipLightTextureReagion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadTextureAtlas, this, "shiplight.png") ;
			try {
				loadTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
				loadTextureAtlas.load();
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			pOnCreateResourcesCallback.onCreateResourcesFinished();
			
		}
		
		
		@Override
		public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)throws Exception {
			// TODO Auto-generated method stub
			initSplash();
			pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
		}
		
		public void optionsToMain(){
			transitionOnProgress = true;
			
			SharedPreferences sp = getSharedPreferences("OPTIONS", 0);
			SharedPreferences.Editor editor = sp.edit();
			editor.putBoolean("SOUNDS", sound);
			editor.putBoolean("MUSIC", music);
			editor.putBoolean("PARTICLES", particles);
			
			editor.commit();
			
			AlphaModifier am = new AlphaModifier(0.5f, 1, 0);
			am.addModifierListener(new IModifierListener<IEntity>() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					// TODO Auto-generated method stub
					loadScene.clearChildScene();
					emos.registerEntityModifier(new AlphaModifier(0.5f, 0, 1));
					eggs.registerEntityModifier(new AlphaModifier(0.5f, 0, 1));
					fb.registerEntityModifier(new AlphaModifier(0.5f,0, 1));
					tw.registerEntityModifier(new AlphaModifier(0.5f, 01, 1));
					
					label.registerEntityModifier(new MoveXModifier(0.5f, label.getX(), label.getX()+100));
//					labelback.registerEntityModifier(0.5f, 0 , 0.4f);
					
					play.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.1f, 0, 1),new ParallelEntityModifier(new AlphaModifier(0.5f, 0, 1), new MoveXModifier(0.5f, play.getX(),play.getX()-400 , EaseExponentialOut.getInstance()))));
					options.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.3f, 0, 1),new ParallelEntityModifier(new AlphaModifier(0.5f, 0, 1), new MoveXModifier(0.5f, play.getX(),play.getX()-400,EaseExponentialOut.getInstance()))));
					credits.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.5f, 0, 1),new ParallelEntityModifier(new AlphaModifier(0.5f, 0, 1), new MoveXModifier(0.5f, play.getX(),play.getX()-400,EaseExponentialOut.getInstance()))));
					
					
					CubicBezierCurveMoveModifier cbmb = new CubicBezierCurveMoveModifier(1f, bigalien.getX(), bigalien.getY(), bigalien.getX()+30, bigalien.getY()-30, bigalien.getX()+150, bigalien.getY() -200, bigalien.getX()+300 ,bigalien.getY()+400, EaseExponentialOut.getInstance());
					ScaleModifier sm = new ScaleModifier(1, 1f, 0.75f);
					
					sm.addModifierListener(new IModifierListener<IEntity>() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							transitionOnProgress = false;
							onMainMenu = true;
							onOptionsMenu = false;
							
						}
					});
					
					bigalien.registerEntityModifier(new ParallelEntityModifier(sm,cbmb));
				}
			});
			
			
			musicB.registerEntityModifier(new AlphaModifier(0.5f, 1, 0));
			musicL.registerEntityModifier(new AlphaModifier(0.5f, 1, 0));
			soundsB.registerEntityModifier(new AlphaModifier(0.5f, 1, 0));
			soundsL.registerEntityModifier(new AlphaModifier(0.5f,1, 0));
			particlesB.registerEntityModifier(new AlphaModifier(0.5f, 1, 0));
			particlesL.registerEntityModifier(am);
			
		}
		
		public void mainToOptions(){
			transitionOnProgress = true ;
			
			
			emos.registerEntityModifier(new AlphaModifier(0.5f, 1, 0));
			eggs.registerEntityModifier(new AlphaModifier(0.5f, 1, 0));
			fb.registerEntityModifier(new AlphaModifier(0.5f, 1, 0));
			tw.registerEntityModifier(new AlphaModifier(0.5f, 1, 0));
			
			
//			labelback.registerEntityModifier(new AlphaModifier(0.5f, labelback.getAlpha(), 0));
			label.registerEntityModifier(new MoveXModifier(0.5f, label.getX(), label.getX()-100));
			play.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.1f, 1, 1), new ParallelEntityModifier(new AlphaModifier(0.9f, 1, 0), new MoveXModifier(0.5f, play.getX(),play.getX()+400 , EaseExponentialInOut.getInstance()))));
			options.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.3f, 1, 1),new ParallelEntityModifier(new AlphaModifier(0.9f, 1, 0), new MoveXModifier(0.5f, options.getX(),options.getX()+400 , EaseExponentialInOut.getInstance()))  ));
			credits.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.5f, 1, 1), new ParallelEntityModifier(new AlphaModifier(0.9f, 1, 0), new MoveXModifier(0.5f, credits.getX(),credits.getX()+400 , EaseExponentialInOut.getInstance())) ));
			
			
			CubicBezierCurveMoveModifier cbmb = new CubicBezierCurveMoveModifier(1f, bigalien.getX(), bigalien.getY(), bigalien.getX()-300, bigalien.getY()-100, bigalien.getX()-400, bigalien.getY()-200, bigalien.getX()- 300 ,bigalien.getY()-400, EaseExponentialIn.getInstance());
			ScaleModifier sm = new ScaleModifier(1, 0.75f,1f);
			
			sm.addModifierListener(new IModifierListener<IEntity>() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					// TODO Auto-generated method stub
					loadScene.setChildScene(optionsScene,false,true,true);
					musicB.registerEntityModifier(new AlphaModifier(1, 0, 1));
					musicL.registerEntityModifier(new AlphaModifier(1, 0, 1));
					soundsB.registerEntityModifier(new AlphaModifier(1, 0, 1));
					soundsL.registerEntityModifier(new AlphaModifier(1, 0, 1));
					particlesB.registerEntityModifier(new AlphaModifier(1, 0, 1));
					
					AlphaModifier amm = new AlphaModifier(1, 0, 1);
					particlesL.registerEntityModifier(amm);
					
					
					handler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							transitionOnProgress = false;
							onOptionsMenu = true;
							onMainMenu = false;
							
						}
					}, 1000);
				}
			});
			
			bigalien.registerEntityModifier(new ParallelEntityModifier(sm,cbmb));
		}
		
		
		public void createOptionsScene(){
			optionsScene = new Scene();
			
			SharedPreferences sp = getSharedPreferences("OPTIONS", 0);
			sound = sp.getBoolean("SOUNDS", true);
			music = sp.getBoolean("MUSIC", true);
			particles = sp.getBoolean("PARTICLES", true);
			
			
			
//			musicL = new Text(200, 300, optionsFont, "Music",new TextOptions(AutoWrap.CJK, 200f,HorizontalAlign.CENTER), getVertexBufferObjectManager());
//			soundsL = new Text(200, 500, optionsFont, "Sounds", getVertexBufferObjectManager());
//			particlesL = new Text(200, 700, optionsFont, "Particles", getVertexBufferObjectManager());
			
			musicL = new Sprite(200-28, 510 + 120, musicTR, getVertexBufferObjectManager());
			soundsL = new Sprite(200-28, 380+ 120, soundTR, getVertexBufferObjectManager());
			particlesL = new Sprite(200-28, 640+ 120, particlesTR, getVertexBufferObjectManager());
			
			
			
			musicB = new TiledSprite(500, 630, toggleTR.deepCopy(), getVertexBufferObjectManager()){
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if(pSceneTouchEvent.isActionDown()){
						if(music){
							music = false;
							musicB.setCurrentTileIndex(1);
							bgmusic.pause();
						}
						else{
							music = true;
							musicB.setCurrentTileIndex(0);
							bgmusic.resume();
						}
					}
					return true;
				}
			};
			soundsB = new TiledSprite(500, 500, toggleTR.deepCopy(), getVertexBufferObjectManager()){
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if(pSceneTouchEvent.isActionDown()){
						if(sound){
							sound = false;
							soundsB.setCurrentTileIndex(1);
						}
						else{
							sound = true;
							soundsB.setCurrentTileIndex(0);
							tapm.play();
						}
					}
					return true;
				}
			};
			particlesB = new TiledSprite(500, 760, toggleTR.deepCopy(), getVertexBufferObjectManager()){
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if(pSceneTouchEvent.isActionDown()){
						if(particles){
							particles = false;
							particlesB.setCurrentTileIndex(1);
						}
						else{
							particles = true;
							particlesB.setCurrentTileIndex(0);
						}
					}
					return true;
				}
			};
			
			if(sound){
				soundsB.setCurrentTileIndex(0);
			}
			else{
				soundsB.setCurrentTileIndex(1);
			}
			
			if(music){
				musicB.setCurrentTileIndex(0);
			}
			else{
				musicB.setCurrentTileIndex(1);
			}
			
			if(particles){
				particlesB.setCurrentTileIndex(0);
			}
			else{
				particlesB.setCurrentTileIndex(1);
			}
			
			musicB.setAlpha(0);
			musicL.setAlpha(0);
			soundsB.setAlpha(0);
			soundsL.setAlpha(0);
			particlesB.setAlpha(0);
			particlesL.setAlpha(0);
			
			optionsScene.attachChild(musicB);
			optionsScene.attachChild(musicL);
			optionsScene.attachChild(soundsB);
			optionsScene.attachChild(soundsL);
			optionsScene.attachChild(particlesB);
			optionsScene.attachChild(particlesL);
			
			optionsScene.registerTouchArea(musicB);
			optionsScene.registerTouchArea(soundsB);
			optionsScene.registerTouchArea(particlesB);
			
			
			
			
			
		}
		
		
		
		private void loadLoadScenes() {
			
			
			/// Load scene touch events
			/// Scolling
			loadScene = new Scene(){
				@Override
				public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
					
					
					if (readytoScoll && scollingInProgress == false) {
						
						if (pSceneTouchEvent.isActionDown()) {
							// to avoid the issue of execiting actionUp before actionDown
							downFirst = true;
							
							
							startx = pSceneTouchEvent.getX();
							for (Level l : levels) {
								l.start = l.getX();
							}
						}
						if (startx != -100) {
							float distance = pSceneTouchEvent.getX() - startx;
							for (Level l : levels) {
								l.setX(l.start + distance);
							}
						}
						
						
						if(pSceneTouchEvent.isActionUp() && downFirst){
							float endx = pSceneTouchEvent.getX();
							if (Math.abs(startx - endx) > 60) {
								scollingInProgress = true;
								
								for (Level l : levels) {
									l.end = l.getX();
								}
								if (endx > startx) {
									
									// avoid first page further scolling
									if(levels.get(0).start > 0){
										for(Level l : levels){
											MoveXModifier mm = new MoveXModifier(
													0.2f, l.getX(), l.start,EaseBackOut.getInstance());
											mm.addModifierListener(new IModifierListener<IEntity>() {

												@Override
												public void onModifierStarted(
														IModifier<IEntity> pModifier,
														IEntity pItem) {
													// TODO Auto-generated method stub
													scollingInProgress = true;
												}

												@Override
												public void onModifierFinished(
														IModifier<IEntity> pModifier,
														IEntity pItem) {
													// TODO Auto-generated method stub
													scollingInProgress = false;
//													for (Level l : levels) {
//														l.start = l.getX();
//														
//														 
//													}
												}
											});
											l.registerEntityModifier(mm);
										}
									}
									
									// normal scolling
									else {
										float xtogo = 800 - (endx - startx);
										for (Level l : levels) {
											MoveXModifier mm = new MoveXModifier(
													0.3f, l.getX(), l.getX()
															+ xtogo,
													EaseBackOut.getInstance());
											mm.addModifierListener(new IModifierListener<IEntity>() {

												@Override
												public void onModifierStarted(
														IModifier<IEntity> pModifier,
														IEntity pItem) {
													// TODO Auto-generated method stub

												}

												@Override
												public void onModifierFinished(
														IModifier<IEntity> pModifier,
														IEntity pItem) {
													// TODO Auto-generated method stub
													scollingInProgress = false;
													for (Level l : levels) {
														l.start = l.getX();
													}
												}
											});
											l.registerEntityModifier(mm);
										}
									}
								}
								
								// if endx < startx
								else {
									
									// avoid further scolling
									if(levels.get(levels.size()-1).start <800){
										for(Level l : levels){
											MoveXModifier mm = new MoveXModifier(
													0.2f, l.getX(), l.start,EaseBackOut.getInstance());
											mm.addModifierListener(new IModifierListener<IEntity>() {

												@Override
												public void onModifierStarted(
														IModifier<IEntity> pModifier,
														IEntity pItem) {
													// TODO Auto-generated method stub
													scollingInProgress = true;
												}

												@Override
												public void onModifierFinished(
														IModifier<IEntity> pModifier,
														IEntity pItem) {
													// TODO Auto-generated method stub
													scollingInProgress = false;
//													for (Level l : levels) {
//														l.start = l.getX();
//														
//														 
//													}
												}
											});
											l.registerEntityModifier(mm);
										}
									}
									
									// normal scolling
									else{
										float xtogo = 800 - (startx - endx);
										for (Level l : levels) {
											MoveXModifier mm = new MoveXModifier(
													0.3f, l.getX(), l.getX()
															- xtogo,
													EaseBackOut.getInstance());
											mm.addModifierListener(new IModifierListener<IEntity>() {

												@Override
												public void onModifierStarted(
														IModifier<IEntity> pModifier,
														IEntity pItem) {
													// TODO Auto-generated method stub
													scollingInProgress = true;
												}

												@Override
												public void onModifierFinished(
														IModifier<IEntity> pModifier,
														IEntity pItem) {
													// TODO Auto-generated method stub
													scollingInProgress = false;
													for (Level l : levels) {
														l.start = l.getX();

													}

												}
											});
											l.registerEntityModifier(mm);
										}
									}
								}
							}
							else{
								for(Level l : levels){
									MoveXModifier mm = new MoveXModifier(
											0.2f, l.getX(), l.start,EaseBackOut.getInstance());
									mm.addModifierListener(new IModifierListener<IEntity>() {

										@Override
										public void onModifierStarted(
												IModifier<IEntity> pModifier,
												IEntity pItem) {
											// TODO Auto-generated method stub
											scollingInProgress = true;
										}

										@Override
										public void onModifierFinished(
												IModifier<IEntity> pModifier,
												IEntity pItem) {
											// TODO Auto-generated method stub
											scollingInProgress = false;
//											for (Level l : levels) {
//												l.start = l.getX();
//												
//												 
//											}
										}
									});
									l.registerEntityModifier(mm);
								}
							}
							
						}
						
					}
					return super.onSceneTouchEvent(pSceneTouchEvent);
					
				}
			};
			loadBackground = new Sprite(CAMERA_WIDTH/2 - loadBackgroundTextureRegion.getWidth()/2	, CAMERA_HEIGHT/2 - loadBackgroundTextureRegion.getHeight()/2, loadBackgroundTextureRegion, getVertexBufferObjectManager()){
				@Override
				protected void preDraw(GLState pGLState, Camera pCamera) {
					
					super.preDraw(pGLState, pCamera);
					pGLState.enableDither();
				}
			};
			
			
			
			
			loadScene.attachChild(loadBackground);
			
			
			
			
			loadLevelFromPrefs();
			
			
			
			
			
			medalien = new Sprite(148, 30, alienmedTR, getVertexBufferObjectManager());
			bigalien = new Sprite(211, 48, alienbigTR, getVertexBufferObjectManager());
			smlalien = new Sprite(412, 40, aliensmlTR, getVertexBufferObjectManager());
			label = new Sprite(240, 402, labelTR.deepCopy(), getVertexBufferObjectManager()){
				@Override
				protected void preDraw(GLState pGLState, Camera pCamera) {
					
					super.preDraw(pGLState, pCamera);
					pGLState.enableDither();
				}
			};
			
			labelsml = new Sprite(650, 780, labelTR.deepCopy(), getVertexBufferObjectManager()){
				@Override
				protected void preDraw(GLState pGLState, Camera pCamera) {
					
					super.preDraw(pGLState, pCamera);
					pGLState.enableDither();
				}
			};
			labelsml.setScale(0.75f);
			
			labelback = new Sprite(0, 150, labelbackTR, getVertexBufferObjectManager()){
				@Override
				protected void preDraw(GLState pGLState, Camera pCamera) {
					
					super.preDraw(pGLState, pCamera);
					pGLState.enableDither();
				}
			};

			
			labelback.setAlpha(0);
			
			
			//map new
			//area label
			areaLabel = new Text(20 , 120 ,unrealFont , "--Area 0--" ,new TextOptions(HorizontalAlign.LEFT),getVertexBufferObjectManager());
			areaLabel.setScale(0.5f);
			areaLabel.setAlpha(0);
			
			loadBackground.attachChild(areaLabel);
			
			
			loading = new Sprite(25, 878, loadingTR, getVertexBufferObjectManager());
			eggthree = new Sprite(10, 932, eggthreeTR, getVertexBufferObjectManager());
			
			loadBackground.attachChild(medalien);
			
			loadBackground.attachChild(smlalien);
			loadBackground.attachChild(labelback);
			loadBackground.attachChild(label);
			loadBackground.attachChild(loading);
			loadBackground.attachChild(eggthree);
			loadBackground.attachChild(bigalien);
			
			loadBackground.attachChild(labelsml);
			
			shipLight = new Sprite(220, 210, shipLightTextureReagion, getVertexBufferObjectManager()){
				@Override
				public void setAlpha(float pAlpha) {
					
					super.setAlpha(pAlpha);
					super.setColor(pAlpha,pAlpha,pAlpha);
				}
				@Override
				protected void preDraw(GLState pGLState, Camera pCamera) {
					
					super.preDraw(pGLState, pCamera);
					pGLState.enableDither();
				}
			};
			shipLight.setRotationCenter(120, 20);
			shipLight.setAlpha(0.5f);
			
			loadBackground.attachChild(shipLight);
			
			SequenceEntityModifier seq = new SequenceEntityModifier(new RotationModifier(0.8f, 0, -25),new RotationModifier(0.7f, -25, 0),new RotationModifier(0.6f, 0, 15),new RotationModifier(0.5f, 15, 0),new RotationModifier(0.4f, 0, -15),new RotationModifier(0.4f, -15, 0),new RotationModifier(0.3f, 0, 10),new RotationModifier(0.3f, 10, 0));
			seq.addModifierListener(new IModifierListener<IEntity>() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {

					loadMainScene();
				}
			});
			
			shipLight.registerEntityModifier(seq);
			loadBackground.setScale((float)1280/1024 );
			
			quitScene = new Scene();
			backfade = new Rectangle(0, 0, 800, 1280, getVertexBufferObjectManager());
			backfade.setColor(Color.BLACK);
			backfade.setAlpha(0.6f);
			sure = new Sprite(80, 400, sureTR, getVertexBufferObjectManager());
			yes = new Sprite(400-220, 680,yesTR, getVertexBufferObjectManager()){
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					finish();
					return true;
				}
			};
			no = new Sprite(470, 680, noTR, getVertexBufferObjectManager()){
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					loadScene.clearChildScene();
					inquitmenu = false;
					return true;
				}
			};
			quitScene.registerTouchArea(yes);
			quitScene.registerTouchArea(no);
			
//			yes.setScale(1.6f);
//			no.setScale(1.6f);
//			sure.setScale(1.3f);
			
			
			quitScene.attachChild(backfade);
			quitScene.attachChild(sure);
			quitScene.attachChild(yes);
			quitScene.attachChild(no);
			
			quitScene.setBackgroundEnabled(false);
			
			createOptionsScene();
			optionsScene.setBackgroundEnabled(false);
			
		}

		private void loadAreasFromPrefs() {
			// TODO Auto-generated method stub
			Area areaa = new Area(146, 158, 1, areaaTR);
			Area areab = new Area(103, 235, 2, areabTR);
			Area areac = new Area(294, 272, 3, areacTR);
			Area aread = new Area(94, 500, 4, areadTR);
			Area areae = new Area(332, 448, 5, areaeTR);
			Area areaf = new Area(294, 606, 6, areafTR);
			Area areag = new Area(132, 760, 7, areagTR);
			Area areah = new Area(122, 676, 8, areahTR);
			
			areas.add(areaa);
			areas.add(areab);
			areas.add(areac);
			areas.add(aread);
			areas.add(areae);
			areas.add(areaf);
			areas.add(areag);
			areas.add(areah);
			
			
			mapbg = new Sprite(0, 0, mapbgTR, getVertexBufferObjectManager());
			loadBackground.attachChild(mapbg);
			mapbg.setAlpha(0);
			
			for ( Area a : areas){
				a.attachChild(a.star);
				a.attachChild(a.starLabel);
				a.attachChild(a.lockNormal);
				a.attachChild(a.lockPaid);
				
				
				loadBackground.attachChild(a);
				a.setAlpha(0);
				
			}
			
		}


		private void loadLevelFromPrefs() {

			
			totStars = 0;
			
			for(int i = 0 ; i < levelNames.length ; i ++){
				Level l1 = new Level(i);
				levels.add(l1);
			}
			
			
			loadAreasFromPrefs();
			
			
			
			for (Level l1 : levels){
				if(l1.unlocked){
					l1.setCurrentTileIndex(0);
					loadScene.attachChild(l1);
					l1.attachChild(l1.label);
					l1.attachChild(l1.s1);
					l1.attachChild(l1.s2);
					l1.attachChild(l1.s3);
					totStars += l1.stars;
					
				}
				else{
					l1.setCurrentTileIndex(1);
					loadScene.attachChild(l1);

				}
				loadScene.registerTouchArea(l1);
			}
			
			
			
			totStar = new Sprite(39, 889+220, totStarTR.deepCopy(), getVertexBufferObjectManager());
			
			totStarlabel = new Text(115, 884+220,unrealFont , "x"+totStars ,new TextOptions(HorizontalAlign.LEFT),getVertexBufferObjectManager());
			//AutoWrap.CJK, 200f,
			
			totStarlabel.setColor(Color.WHITE);
			totStarlabel.setScale(0.7f);
			
			loadScene.attachChild(totStar);
			loadScene.attachChild(totStarlabel);
			
			totStarlabel.setAlpha(0);
			totStar.setAlpha(0);
			
			
			more = new Sprite(130 ,230, moreTR, getVertexBufferObjectManager()){
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if(pSceneTouchEvent.isActionDown()){
						String url = "http://www.facebook.com/pages/Eat-em-all/144865022318112";
						Intent i = new Intent(Intent.ACTION_VIEW);
						i.setData(Uri.parse(url));
						startActivity(i);
						
						
					}
					return true;
				}
			};
			
			// for china
//			levels.get(levels.size()-3).attachChild(more);
//			
//			loadScene.registerTouchArea(more);
			
			
		}
		
		
		
//		private void loadLevelFromPrefs() {
//
//			
//			totStars = 0;
//			
//			for(int i = 0 ; i < levelNames.length ; i ++){
//				Level l1 = new Level(i);
//				levels.add(l1);
//				if(l1.unlocked){
//					l1.setCurrentTileIndex(0);
//					loadScene.attachChild(l1);
//					l1.attachChild(l1.label);
//					l1.attachChild(l1.s1);
//					l1.attachChild(l1.s2);
//					l1.attachChild(l1.s3);
//					totStars += l1.stars;
//					
//				}
//				else{
//					l1.setCurrentTileIndex(1);
//					loadScene.attachChild(l1);
//
//				}
//				loadScene.registerTouchArea(l1);
//			}
//			
//			totStar = new Sprite(39, 889+220, totStarTR.deepCopy(), getVertexBufferObjectManager());
//			
//			totStarlabel = new Text(115, 884+220,unrealFont , "x"+totStars ,new TextOptions(HorizontalAlign.LEFT),getVertexBufferObjectManager());
//			//AutoWrap.CJK, 200f,
//			
//			totStarlabel.setColor(Color.WHITE);
//			totStarlabel.setScale(0.7f);
//			
//			loadScene.attachChild(totStar);
//			loadScene.attachChild(totStarlabel);
//			
//			totStarlabel.setAlpha(0);
//			totStar.setAlpha(0);
//			
//			
//			more = new Sprite(130 ,230, moreTR, getVertexBufferObjectManager()){
//				@Override
//				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
//						float pTouchAreaLocalX, float pTouchAreaLocalY) {
//					if(pSceneTouchEvent.isActionDown()){
//						String url = "http://www.facebook.com/pages/Eat-em-all/144865022318112";
//						Intent i = new Intent(Intent.ACTION_VIEW);
//						i.setData(Uri.parse(url));
//						startActivity(i);
//						
//						
//					}
//					return true;
//				}
//			};
//			
//			// for china
////			levels.get(levels.size()-3).attachChild(more);
////			
////			loadScene.registerTouchArea(more);
//			
//			
//		}
		
		
		@Override
		public synchronized void onResumeGame() {
			// TODO Auto-generated method stub
			super.onResumeGame();
			
			
		}

		@Override
		public synchronized void onPauseGame() {
			// TODO Auto-generated method stub
			super.onPauseGame();
			
			mIsPausedBefore = true;
			if(bgmusic != null){
				bgmusic.pause();
			}
		}
		
		public void maintolevelFirst(){
			
			levelVisitedOnce = true;
			transitionOnProgress = true;
			
			MoveYModifier mm = new MoveYModifier(1.0f, label.getY(), label.getY()-300 , EaseBackOut.getInstance());
			mm.addModifierListener(new IModifierListener<IEntity>() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					loadScene.unregisterTouchArea(fb);
					loadScene.unregisterTouchArea(tw);
					
					emos.registerEntityModifier(new MoveXModifier(0.5f, emos.getX(), emos.getX()-500 , EaseStrongOut.getInstance()));
					eggs.registerEntityModifier(new MoveXModifier(0.5f, eggs.getX(), eggs.getX()+500,EaseStrongOut.getInstance()));
					fb.registerEntityModifier(new AlphaModifier(0.2f, 1, 0));
					tw.registerEntityModifier(new AlphaModifier(0.2f, 1, 0));
					labelback.registerEntityModifier(new AlphaModifier(0.2f, labelback.getAlpha(), 0));
					
					play.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.1f, 1, 1), new ParallelEntityModifier(new AlphaModifier(0.9f, 1, 0), new MoveXModifier(0.5f, play.getX(),play.getX()+400 , EaseExponentialInOut.getInstance()))));
					options.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.3f, 1, 1),new ParallelEntityModifier(new AlphaModifier(0.9f, 1, 0), new MoveXModifier(0.5f, options.getX(),options.getX()+400 , EaseExponentialInOut.getInstance()))  ));
					credits.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.5f, 1, 1), new ParallelEntityModifier(new AlphaModifier(0.9f, 1, 0), new MoveXModifier(0.5f, credits.getX(),credits.getX()+400 , EaseExponentialInOut.getInstance())) ));
					
					CubicBezierCurveMoveModifier cbmb = new CubicBezierCurveMoveModifier(2f, bigalien.getX(), bigalien.getY(), bigalien.getX()+300, 155, bigalien.getX()+500, 120, bigalien.getX()+200, bigalien.getY()-120, EaseExponentialOut.getInstance());
					bigalien.registerEntityModifier(new ParallelEntityModifier(new ScaleModifier(1, 0.75f, 1),cbmb));
					
					
					medalien.clearEntityModifiers();
					smlalien.clearEntityModifiers();
					medalien.registerEntityModifier(new CubicBezierCurveMoveModifier(2.5f, medalien.getX(), medalien.getY(), 50, 50, 100, 100, 130, 20 ,EaseExponentialOut.getInstance()));
					smlalien.registerEntityModifier(new CubicBezierCurveMoveModifier(3f, smlalien.getX(), smlalien.getY(), 650, 50, 500, 100, 400, 40, EaseExponentialOut.getInstance()));
					
				}
				
				
				
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				
					for(Level l : levels){
						l.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier((float)(l.column*50 + l.row*30 + 1)/500 , 1, 1),new MoveXModifier(1,l.getX(), l.getX()-800 , EaseStrongOut.getInstance())));
					}
					
					labelsml.registerEntityModifier(new MoveXModifier(0.5f, labelsml.getX(), labelsml.getX() - 360  , EaseExponentialOut.getInstance()));
					totStar.registerEntityModifier(new AlphaModifier(0.5f, 0, 0.8f));
					totStarlabel.registerEntityModifier(new AlphaModifier(0.5f, 0, 0.8f));
					
					
					handler.postDelayed(new Runnable() {



						@Override
						public void run() {
							readytoScoll = true;
							onLevelMenu = true;
							onMainMenu = false;
							transitionOnProgress = false;
						}
					}, 1300);

				}
			});
			
			label.registerEntityModifier(new ParallelEntityModifier(new AlphaModifier(0.4f,1,0), mm));
		}

		
		
		public void MapToLevelFirst(final Area selectedArea ){
			
			levelVisitedOnce = true;
			transitionOnProgress = true;
			selectedArea.selected = true;
			
//			for(Level l : levels){
//				loadScene.unregisterTouchArea(l);
//				l.setVisible(false);
//				
//			}
//			for(int i = selectedArea.minLevel ; i <= selectedArea.maxLevel ; i ++ ){
//				levels.get(i).setVisible(true);
//				loadScene.registerTouchArea(levels.get(i));
//			}
//			
//			for (int i = 0 ; i < levels.size() ; i ++){
//				if(selectedArea.minLevel <= i && i <= selectedArea.maxLevel){
//					
//				}
//			}
//			
			MoveModifier mm = new MoveModifier(0.6f, selectedArea.getX(), 400 - selectedArea.getWidth()/2, selectedArea.getY(), 640 - selectedArea.getHeight()/2   ) ;  
			mm.addModifierListener(new IModifierListener<IEntity>() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					
					selectedArea.registerEntityModifier(new AlphaModifier(0.6f, 1, 0.4f));
					
					for (Area a : areas){
						if(a.selected == false){
							a.registerEntityModifier(new AlphaModifier(0.6f,1 , 0 ));
						}
					}
				}
				
				
				
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				
					for(Level l : levels){
						l.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier((float)(l.column*50 + l.row*30 + 1)/500 , 1, 1),new MoveXModifier(1,l.getX(), l.getX()-800 , EaseStrongOut.getInstance())));
					}

					handler.postDelayed(new Runnable() {



						@Override
						public void run() {
							readytoScoll = true;
							onLevelMenu = true;
							onMapMenu = false;
							transitionOnProgress = false;
						}
					}, 1300);

				}
			});
			
//			label.registerEntityModifier(new ParallelEntityModifier(new AlphaModifier(0.4f,1,0), mm));
			selectedArea.registerEntityModifier(mm);
		}
		


		
		
		public void MapToLevelThen(final Area selectedArea ){
			
			levelVisitedOnce = true;
			transitionOnProgress = true;
			selectedArea.selected = true;
			
//			for(Level l : levels){
//				loadScene.unregisterTouchArea(l);
//				l.setVisible(false);
//			}
//			for(int i = selectedArea.minLevel ; i <= selectedArea.maxLevel ; i ++ ){
//				levels.get(i).setVisible(true);
//				loadScene.registerTouchArea(levels.get(i));
//			}
			
			
			MoveModifier mm = new MoveModifier(0.6f, selectedArea.getX(),320 - selectedArea.getWidth()/2, selectedArea.getY(),512 - selectedArea.getHeight()/2   ) ;  
			mm.addModifierListener(new IModifierListener<IEntity>() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					
					mapbg.registerEntityModifier(new AlphaModifier(0.6f, 1, 0f));
					
					
					selectedArea.registerEntityModifier(new AlphaModifier(0.6f, 1, 0f));
					selectedArea.registerEntityModifier(new ScaleModifier(0.6f, 1, 30));
					selectedArea.registerEntityModifier(new RotationModifier(0.6f, 0, 50));
					
					for (Area a : areas){
						if(a.selected == false){
							a.registerEntityModifier(new AlphaModifier(0.2f,1 , 0 ));
							
							
						}
						loadScene.unregisterTouchArea(a);
						a.star.registerEntityModifier(new AlphaModifier(0.2f, 1, 0f));
						a.starLabel.registerEntityModifier(new AlphaModifier(0.2f, 1, 0f));
						a.lockNormal.registerEntityModifier(new AlphaModifier(0.2f, 1, 0f));
						a.lockPaid.registerEntityModifier(new AlphaModifier(0.2f, 1, 0f));
					}
				}
				
				
				
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				
//					for(Level l : levels){
//						l.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier((float)(l.column*50 + l.row*30 + 1)/500 , 1, 1),new MoveYModifier(1,l.getY(), l.getY()-1280 , EaseStrongOut.getInstance())));
//					}
					
					for(int i = selectedArea.minLevel ; i <= selectedArea.maxLevel ; i++){
						levels.get(i).registerEntityModifier(new SequenceEntityModifier(new ScaleModifier((float)(levels.get(i).column*50 + levels.get(i).row*30 + 1)/500 , 1, 1),new MoveYModifier(1,levels.get(i).getY(), levels.get(i).getY()-1280 , EaseStrongOut.getInstance())));
					}
					
					
					areaLabel.registerEntityModifier(new AlphaModifier(0.4f, 0, 1));

					handler.postDelayed(new Runnable() {



						@Override
						public void run() {
							readytoScoll = true;
							onLevelMenu = true;
							onMapMenu = false;
							transitionOnProgress = false;
						}
					}, 1000); // changed from 1300

				}
			});
			
//			label.registerEntityModifier(new ParallelEntityModifier(new AlphaModifier(0.4f,1,0), mm));
			selectedArea.registerEntityModifier(mm);
		}
		
		public void LevelToMap(){
			
			
			transitionOnProgress = true;

			
			
			for(Area a : areas){
				if(a.selected){
					selArea = a ;
				}
			}
			
			
			
			AlphaModifier mm = new AlphaModifier(0.6f, 0,1  ) ;  
			mm.addModifierListener(new IModifierListener<IEntity>() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					
					areaLabel.registerEntityModifier(new AlphaModifier(0.4f, 1, 0));
					
					
					for(int i = selArea.minLevel ; i <= selArea.maxLevel ; i ++){
						levels.get(i).registerEntityModifier(new SequenceEntityModifier(new ScaleModifier((float)(160 -levels.get(i).column*30 - levels.get(i).row*50 + 1)/500 , 1, 1),new MoveYModifier(1,levels.get(i).getY(), levels.get(i).getY()+1280 , EaseExponentialInOut.getInstance())));
					}

					mapbg.registerEntityModifier(new AlphaModifier(0.6f, 0, 1));


					
					selArea.registerEntityModifier(new ScaleModifier(0.6f, 30, 1));
					selArea.registerEntityModifier(new RotationModifier(0.6f, 50, 0));
					selArea.registerEntityModifier(new MoveModifier(0.6f, selArea.getX(), selArea.x, selArea.getY(), selArea.y));
					
					
				
					
					
					for (Area a : areas){
						if(a.selected == false){
							a.registerEntityModifier(new AlphaModifier(0.6f,0 , 1 ));
						}
					}
				}
				
				
				
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				
					for (Area a : areas){

						loadScene.registerTouchArea(a);
						a.star.registerEntityModifier(new AlphaModifier(0.2f, 0,1));
						a.starLabel.registerEntityModifier(new AlphaModifier(0.2f, 0, 1));
						a.lockNormal.registerEntityModifier(new AlphaModifier(0.2f, 0, 1f));
						a.lockPaid.registerEntityModifier(new AlphaModifier(0.2f, 0, 1f));
					}

					handler.postDelayed(new Runnable() {



						@Override
						public void run() {
							readytoScoll = false;
							onLevelMenu = false;
							onMapMenu = true;
							transitionOnProgress = false;
							selArea.selected = false ;
						}
					}, 1300);

				}
			});
			
//			label.registerEntityModifier(new ParallelEntityModifier(new AlphaModifier(0.4f,1,0), mm));
			selArea.registerEntityModifier(mm);
		}
		
		
		
		
		public void maintoMapFast(){
			
//			levelVisitedOnce = true;
			transitionOnProgress = true;
			
			MoveYModifier mm = new MoveYModifier(0.01f, label.getY(), label.getY()-300 , EaseBackOut.getInstance());
			mm.addModifierListener(new IModifierListener<IEntity>() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					loadScene.unregisterTouchArea(fb);
					loadScene.unregisterTouchArea(tw);
					
					emos.registerEntityModifier(new MoveXModifier(0.005f, emos.getX(), emos.getX()-500 , EaseStrongOut.getInstance()));
					eggs.registerEntityModifier(new MoveXModifier(0.005f, eggs.getX(), eggs.getX()+500,EaseStrongOut.getInstance()));
					fb.registerEntityModifier(new AlphaModifier(0.002f, 1, 0));
					tw.registerEntityModifier(new AlphaModifier(0.002f, 1, 0));
					labelback.registerEntityModifier(new AlphaModifier(0.002f, labelback.getAlpha(), 0));
					
					play.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.001f, 1, 1), new ParallelEntityModifier(new AlphaModifier(0.009f, 1, 0), new MoveXModifier(0.005f, play.getX(),play.getX()+400 , EaseExponentialInOut.getInstance()))));
					options.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.003f, 1, 1),new ParallelEntityModifier(new AlphaModifier(0.009f, 1, 0), new MoveXModifier(0.0005f, options.getX(),options.getX()+400 , EaseExponentialInOut.getInstance()))  ));
					credits.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.005f, 1, 1), new ParallelEntityModifier(new AlphaModifier(0.009f, 1, 0), new MoveXModifier(0.005f, credits.getX(),credits.getX()+400 , EaseExponentialInOut.getInstance())) ));
					
//					CubicBezierCurveMoveModifier cbmb = new CubicBezierCurveMoveModifier(2f, bigalien.getX(), bigalien.getY(), bigalien.getX()+300, 155, bigalien.getX()+500, 120, bigalien.getX()+200, bigalien.getY()-120, EaseExponentialOut.getInstance());
					
					CubicBezierCurveMoveModifier cbmb = new CubicBezierCurveMoveModifier(0.02f, bigalien.getX(), bigalien.getY(), bigalien.getX()+300, 155, bigalien.getX()+500, 50, bigalien.getX()+200, bigalien.getY()-400, EaseExponentialOut.getInstance());
					
					bigalien.registerEntityModifier(new ParallelEntityModifier(new ScaleModifier(0.01f, 0.75f, 1),cbmb));
					
					
					medalien.clearEntityModifiers();
					smlalien.clearEntityModifiers();
					medalien.registerEntityModifier(new CubicBezierCurveMoveModifier(0.025f, medalien.getX(), medalien.getY(), 50, 50, 100, 100, 130, 20 ,EaseExponentialOut.getInstance()));
					smlalien.registerEntityModifier(new CubicBezierCurveMoveModifier(0.03f, smlalien.getX(), smlalien.getY(), 650, 50, 500, 100, 400, 40, EaseExponentialOut.getInstance()));
					
				}
				
				
				
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				
//					for(Level l : levels){
//						l.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier((float)(l.column*50 + l.row*30 + 1)/500 , 1, 1),new MoveXModifier(1,l.getX(), l.getX()-800 , EaseStrongOut.getInstance())));
//					}
					for (Area a : areas){
						
						loadScene.registerTouchArea(a);
						a.registerEntityModifier(new AlphaModifier(0.5f, 0, 1));
						
						a.starLabel.registerEntityModifier(new AlphaModifier(0.5f, 0, 1));
						a.star.registerEntityModifier(new AlphaModifier(0.5f, 0, 1));
						a.lockNormal.registerEntityModifier(new AlphaModifier(0.5f, 0, 1));
						a.lockPaid.registerEntityModifier(new AlphaModifier(0.5f, 0, 1));
						
//						a.setVisible(true);
					}
					mapbg.registerEntityModifier(new AlphaModifier(0.5f, 0, 1));
					
					labelsml.registerEntityModifier(new MoveXModifier(0.5f, labelsml.getX(), labelsml.getX() - 360  , EaseExponentialOut.getInstance()));
					totStar.registerEntityModifier(new AlphaModifier(0.5f, 0, 0.8f));
					totStarlabel.registerEntityModifier(new AlphaModifier(0.5f, 0, 0.8f));
					
					
					handler.postDelayed(new Runnable() {

						@Override
						public void run() {
//							readytoScoll = true;
//							onLevelMenu = true;
							onMainMenu = false;
							onMapMenu = true;
							transitionOnProgress = false;
							
						}
					}, 1300);

				}
			});
			
			label.registerEntityModifier(new ParallelEntityModifier(new AlphaModifier(0.4f,1,0), mm));
		}
		
		
		
		
		
		public void maintoMap(){
			
//			levelVisitedOnce = true;
			transitionOnProgress = true;
			
			MoveYModifier mm = new MoveYModifier(1.0f, label.getY(), label.getY()-300 , EaseBackOut.getInstance());
			mm.addModifierListener(new IModifierListener<IEntity>() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					loadScene.unregisterTouchArea(fb);
					loadScene.unregisterTouchArea(tw);
					
					emos.registerEntityModifier(new MoveXModifier(0.5f, emos.getX(), emos.getX()-500 , EaseStrongOut.getInstance()));
					eggs.registerEntityModifier(new MoveXModifier(0.5f, eggs.getX(), eggs.getX()+500,EaseStrongOut.getInstance()));
					fb.registerEntityModifier(new AlphaModifier(0.2f, 1, 0));
					tw.registerEntityModifier(new AlphaModifier(0.2f, 1, 0));
					labelback.registerEntityModifier(new AlphaModifier(0.2f, labelback.getAlpha(), 0));
					
					play.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.1f, 1, 1), new ParallelEntityModifier(new AlphaModifier(0.9f, 1, 0), new MoveXModifier(0.5f, play.getX(),play.getX()+400 , EaseExponentialInOut.getInstance()))));
					options.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.3f, 1, 1),new ParallelEntityModifier(new AlphaModifier(0.9f, 1, 0), new MoveXModifier(0.5f, options.getX(),options.getX()+400 , EaseExponentialInOut.getInstance()))  ));
					credits.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.5f, 1, 1), new ParallelEntityModifier(new AlphaModifier(0.9f, 1, 0), new MoveXModifier(0.5f, credits.getX(),credits.getX()+400 , EaseExponentialInOut.getInstance())) ));
					
//					CubicBezierCurveMoveModifier cbmb = new CubicBezierCurveMoveModifier(2f, bigalien.getX(), bigalien.getY(), bigalien.getX()+300, 155, bigalien.getX()+500, 120, bigalien.getX()+200, bigalien.getY()-120, EaseExponentialOut.getInstance());
					
					CubicBezierCurveMoveModifier cbmb = new CubicBezierCurveMoveModifier(2f, bigalien.getX(), bigalien.getY(), bigalien.getX()+300, 155, bigalien.getX()+500, 50, bigalien.getX()+200, bigalien.getY()-400, EaseExponentialOut.getInstance());
					
					bigalien.registerEntityModifier(new ParallelEntityModifier(new ScaleModifier(1, 0.75f, 1),cbmb));
					
					
					medalien.clearEntityModifiers();
					smlalien.clearEntityModifiers();
					medalien.registerEntityModifier(new CubicBezierCurveMoveModifier(2.5f, medalien.getX(), medalien.getY(), 50, 50, 100, 100, 130, 20 ,EaseExponentialOut.getInstance()));
					smlalien.registerEntityModifier(new CubicBezierCurveMoveModifier(3f, smlalien.getX(), smlalien.getY(), 650, 50, 500, 100, 400, 40, EaseExponentialOut.getInstance()));
					
				}
				
				
				
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				
//					for(Level l : levels){
//						l.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier((float)(l.column*50 + l.row*30 + 1)/500 , 1, 1),new MoveXModifier(1,l.getX(), l.getX()-800 , EaseStrongOut.getInstance())));
//					}
					for (Area a : areas){
						
						loadScene.registerTouchArea(a);
						a.registerEntityModifier(new AlphaModifier(0.5f, 0, 1));
						
						a.starLabel.registerEntityModifier(new AlphaModifier(0.5f, 0, 1));
						a.star.registerEntityModifier(new AlphaModifier(0.5f, 0, 1));
						a.lockNormal.registerEntityModifier(new AlphaModifier(0.5f, 0, 1));
						a.lockPaid.registerEntityModifier(new AlphaModifier(0.5f, 0, 1));
						
//						a.setVisible(true);
					}
					mapbg.registerEntityModifier(new AlphaModifier(0.5f, 0, 1));
					
					labelsml.registerEntityModifier(new MoveXModifier(0.5f, labelsml.getX(), labelsml.getX() - 360  , EaseExponentialOut.getInstance()));
					totStar.registerEntityModifier(new AlphaModifier(0.5f, 0, 0.8f));
					totStarlabel.registerEntityModifier(new AlphaModifier(0.5f, 0, 0.8f));
					
					
					handler.postDelayed(new Runnable() {

						@Override
						public void run() {
//							readytoScoll = true;
//							onLevelMenu = true;
							onMainMenu = false;
							onMapMenu = true;
							transitionOnProgress = false;
							
						}
					}, 1300);

				}
			});
			
			label.registerEntityModifier(new ParallelEntityModifier(new AlphaModifier(0.4f,1,0), mm));
		}
		
		
		
		public void maintolevelThen(){
			transitionOnProgress = true;
			levelVisitedOnce = true;
			MoveYModifier mm = new MoveYModifier(1.0f, label.getY(), label.getY()-300 , EaseBackOut.getInstance());
			mm.addModifierListener(new IModifierListener<IEntity>() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					
					loadScene.unregisterTouchArea(fb);
					loadScene.unregisterTouchArea(tw);
					
					emos.registerEntityModifier(new MoveXModifier(0.5f, emos.getX(), emos.getX()-500 , EaseStrongOut.getInstance()));
					eggs.registerEntityModifier(new MoveXModifier(0.5f, eggs.getX(), eggs.getX()+500,EaseStrongOut.getInstance()));
					fb.registerEntityModifier(new AlphaModifier(0.2f, 1, 0));
					tw.registerEntityModifier(new AlphaModifier(0.2f, 1, 0));
					labelback.registerEntityModifier(new AlphaModifier(0.2f, labelback.getAlpha(), 0));
					
					play.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.1f, 1, 1), new ParallelEntityModifier(new AlphaModifier(0.9f, 1, 0), new MoveXModifier(0.5f, play.getX(),play.getX()+400 , EaseExponentialInOut.getInstance()))));
					options.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.3f, 1, 1),new ParallelEntityModifier(new AlphaModifier(0.9f, 1, 0), new MoveXModifier(0.5f, options.getX(),options.getX()+400 , EaseExponentialInOut.getInstance()))  ));
					credits.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.5f, 1, 1), new ParallelEntityModifier(new AlphaModifier(0.9f, 1, 0), new MoveXModifier(0.5f, credits.getX(),credits.getX()+400 , EaseExponentialInOut.getInstance())) ));
					
					
					CubicBezierCurveMoveModifier cbmb = new CubicBezierCurveMoveModifier(2f, bigalien.getX(), bigalien.getY(), bigalien.getX()+300, 155, bigalien.getX()+500, 120, bigalien.getX()+200, bigalien.getY()-120, EaseExponentialOut.getInstance());
					bigalien.registerEntityModifier(new ParallelEntityModifier(new ScaleModifier(1, 0.75f, 1),cbmb));
					
					
					medalien.clearEntityModifiers();
					smlalien.clearEntityModifiers();
					medalien.registerEntityModifier(new CubicBezierCurveMoveModifier(2.5f, medalien.getX(), medalien.getY(), 50, 50, 100, 100, 130, 20 ,EaseExponentialOut.getInstance()));
					smlalien.registerEntityModifier(new CubicBezierCurveMoveModifier(3f, smlalien.getX(), smlalien.getY(), 650, 50, 500, 100, 400, 40, EaseExponentialOut.getInstance()));
					
				}
				
				
				
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					
					for(Level l : levels){
						l.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier((float)(l.column*50 + l.row*30 + 1)/500 , 1, 1),new MoveYModifier(1,l.getY(), l.getY()-1280 , EaseStrongOut.getInstance())));
					}
					labelsml.registerEntityModifier(new MoveXModifier(0.5f, labelsml.getX(), labelsml.getX() - 360  , EaseExponentialOut.getInstance()));
					
					totStar.registerEntityModifier(new AlphaModifier(0.5f, 0, 0.8f));
					totStarlabel.registerEntityModifier(new AlphaModifier(0.5f, 0, 0.8f));
					
					handler.postDelayed(new Runnable() {



						@Override
						public void run() {
							readytoScoll = true;
							onLevelMenu = true;
							onMainMenu = false;
							transitionOnProgress = false;
						}
					}, 1300);

				}
			});
			
			label.registerEntityModifier(new ParallelEntityModifier(new AlphaModifier(0.4f, 1, 0),mm));
		}
		
		
		
		public void leveltomain(){
			transitionOnProgress = true;
			MoveYModifier mm = new MoveYModifier(1.0f, label.getY(), label.getY()+300 , EaseBackOut.getInstance());
			mm.addModifierListener(new IModifierListener<IEntity>() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					
					labelsml.registerEntityModifier(new MoveXModifier(0.5f, labelsml.getX(), labelsml.getX() + 350  , EaseExponentialOut.getInstance()));
					totStar.registerEntityModifier(new AlphaModifier(0.5f, 0.8f, 0f));
					totStarlabel.registerEntityModifier(new AlphaModifier(0.5f, 0.8f, 0f));
					
					
					loadScene.unregisterTouchArea(play);
					loadScene.unregisterTouchArea(options);
					loadScene.unregisterTouchArea(credits);
					loadScene.unregisterTouchArea(fb);
					loadScene.unregisterTouchArea(tw);
					
					for(Level l : levels){
						l.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier((float)(160 - l.column*30 - l.row*50 + 1)/500 , 1, 1),new MoveYModifier(1,l.getY(), l.getY()+1280 , EaseExponentialInOut.getInstance())));
					}
					CubicBezierCurveMoveModifier cbmb = new CubicBezierCurveMoveModifier(2f, bigalien.getX(), bigalien.getY(), bigalien.getX()-100, 155, bigalien.getX()-300, 120, -5, 154, EaseExponentialOut.getInstance());
					bigalien.registerEntityModifier(new ParallelEntityModifier(new ScaleModifier(1, 1f, 0.75f),cbmb));
					
					medalien.clearEntityModifiers();
					smlalien.clearEntityModifiers();
					medalien.registerEntityModifier(new CubicBezierCurveMoveModifier(2.5f, medalien.getX(), medalien.getY(), 50, 50, 100, 100,500, 20 ,EaseExponentialOut.getInstance()));
					smlalien.registerEntityModifier(new CubicBezierCurveMoveModifier(3f, smlalien.getX(), smlalien.getY(), 650, 50, 500, 100, 400, 60, EaseExponentialOut.getInstance()));
					
					
					handler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							label.registerEntityModifier(new AlphaModifier(1,0, 1));
							emos.registerEntityModifier(new MoveXModifier(0.5f, emos.getX(), emos.getX()+500 , EaseStrongOut.getInstance()));
							eggs.registerEntityModifier(new MoveXModifier(0.5f, eggs.getX(), eggs.getX()-500,EaseStrongOut.getInstance()));
							fb.registerEntityModifier(new AlphaModifier(0.2f, 0, 1));
							tw.registerEntityModifier(new AlphaModifier(0.2f, 0, 1));
							labelback.registerEntityModifier(new AlphaModifier(0.2f, labelback.getAlpha(), 0.4f));
							
							play.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.1f, 0, 1),new ParallelEntityModifier(new AlphaModifier(0.5f, 0, 1), new MoveXModifier(0.5f, play.getX(),play.getX()-400 , EaseExponentialOut.getInstance()))));
							options.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.3f, 0, 1),new ParallelEntityModifier(new AlphaModifier(0.5f, 0, 1), new MoveXModifier(0.5f, play.getX(),play.getX()-400,EaseExponentialOut.getInstance()))));
							credits.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.5f, 0, 1),new ParallelEntityModifier(new AlphaModifier(0.5f, 0, 1), new MoveXModifier(0.5f, play.getX(),play.getX()-400,EaseExponentialOut.getInstance()))));
							
							
							
							
						}
					}, 1000);
				}
				
				
				
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					
					handler.postDelayed(new Runnable() {
	
							@Override
							public void run() {
								transitionOnProgress = false;
								readytoScoll = false;
								onLevelMenu = false;
								onMainMenu = true;
								loadScene.registerTouchArea(play);
								loadScene.registerTouchArea(options);
								loadScene.registerTouchArea(credits);
								loadScene.registerTouchArea(fb);
								loadScene.registerTouchArea(tw);
							}
						}, 1300);
					
				}
			});
			
			label.registerEntityModifier(mm);
		}
		
		
		
		public void Maptomain(){
			transitionOnProgress = true;
			MoveYModifier mm = new MoveYModifier(1.0f, label.getY(), label.getY()+300 , EaseBackOut.getInstance());
			mm.addModifierListener(new IModifierListener<IEntity>() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					
					for (Area a : areas){

						loadScene.unregisterTouchArea(a);
						a.registerEntityModifier(new AlphaModifier(0.5f, 1, 0));

						a.starLabel.registerEntityModifier(new AlphaModifier(0.5f, 1, 0));
						a.star.registerEntityModifier(new AlphaModifier(0.5f, 1, 0));
						a.lockNormal.registerEntityModifier(new AlphaModifier(0.5f, 1, 0));
						a.lockPaid.registerEntityModifier(new AlphaModifier(0.5f, 1, 0));

						//						a.setVisible(true);
					}
					
					mapbg.registerEntityModifier(new AlphaModifier(0.5f, 1, 0));
					
					
					labelsml.registerEntityModifier(new MoveXModifier(0.5f, labelsml.getX(), labelsml.getX() + 350  , EaseExponentialOut.getInstance()));
					totStar.registerEntityModifier(new AlphaModifier(0.5f, 0.8f, 0f));
					totStarlabel.registerEntityModifier(new AlphaModifier(0.5f, 0.8f, 0f));
					
					
					loadScene.unregisterTouchArea(play);
					loadScene.unregisterTouchArea(options);
					loadScene.unregisterTouchArea(credits);
					loadScene.unregisterTouchArea(fb);
					loadScene.unregisterTouchArea(tw);
					
					
					CubicBezierCurveMoveModifier cbmb = new CubicBezierCurveMoveModifier(2f, bigalien.getX(), bigalien.getY(), bigalien.getX()-100, 155, bigalien.getX()-300, 120, -5, 154, EaseExponentialOut.getInstance());
					bigalien.registerEntityModifier(new ParallelEntityModifier(new ScaleModifier(1, 1f, 0.75f),cbmb));
					
					medalien.clearEntityModifiers();
					smlalien.clearEntityModifiers();
					medalien.registerEntityModifier(new CubicBezierCurveMoveModifier(2.5f, medalien.getX(), medalien.getY(), 50, 50, 100, 100,500, 20 ,EaseExponentialOut.getInstance()));
					smlalien.registerEntityModifier(new CubicBezierCurveMoveModifier(3f, smlalien.getX(), smlalien.getY(), 650, 50, 500, 100, 400, 60, EaseExponentialOut.getInstance()));
					
					
					handler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							label.registerEntityModifier(new AlphaModifier(1,0, 1));
							emos.registerEntityModifier(new MoveXModifier(0.5f, emos.getX(), emos.getX()+500 , EaseStrongOut.getInstance()));
							eggs.registerEntityModifier(new MoveXModifier(0.5f, eggs.getX(), eggs.getX()-500,EaseStrongOut.getInstance()));
							fb.registerEntityModifier(new AlphaModifier(0.2f, 0, 1));
							tw.registerEntityModifier(new AlphaModifier(0.2f, 0, 1));
							labelback.registerEntityModifier(new AlphaModifier(0.2f, labelback.getAlpha(), 0.4f));
							
							play.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.1f, 0, 1),new ParallelEntityModifier(new AlphaModifier(0.5f, 0, 1), new MoveXModifier(0.5f, play.getX(),play.getX()-400 , EaseExponentialOut.getInstance()))));
							options.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.3f, 0, 1),new ParallelEntityModifier(new AlphaModifier(0.5f, 0, 1), new MoveXModifier(0.5f, play.getX(),play.getX()-400,EaseExponentialOut.getInstance()))));
							credits.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.5f, 0, 1),new ParallelEntityModifier(new AlphaModifier(0.5f, 0, 1), new MoveXModifier(0.5f, play.getX(),play.getX()-400,EaseExponentialOut.getInstance()))));
							
							
							
							
						}
					}, 1000);
				}
				
				
				
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					
					handler.postDelayed(new Runnable() {
	
							@Override
							public void run() {
								transitionOnProgress = false;
								readytoScoll = false;
								onMapMenu = false;
								onMainMenu = true;
								loadScene.registerTouchArea(play);
								loadScene.registerTouchArea(options);
								loadScene.registerTouchArea(credits);
								loadScene.registerTouchArea(fb);
								loadScene.registerTouchArea(tw);
							}
						}, 1300);
					
				}
			});
			
			label.registerEntityModifier(mm);
		}
		


		private void loadMainScene() {
			
			loadScene.setTouchAreaBindingOnActionDownEnabled(true);
			loadScene.setTouchAreaBindingOnActionMoveEnabled(true);
			////////////////// play ///////////////////////////
			
			play = new TiledSprite(810, 400, playTR, getVertexBufferObjectManager()){
				

				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
//					this.setCurrentTileIndex(1);
					
					if(pSceneTouchEvent.isActionDown() && transitionOnProgress==false &&menubuttonpressed == false){
						
						if(sound){
							tapm.play();
						}
						
						
						
						menubuttonpressed = true;
						
						
						

						if(levelid > 6 && supporteddevs == false){
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									supporteddevs = true;
									showDialog(1);
									SharedPreferences sd = getSharedPreferences("DIALOG", 0);
									SharedPreferences.Editor ed = sd.edit();
									
									ed.putBoolean("SHOWN", true);
									ed.commit();
								}
							});
							}
						
							this.setCurrentTileIndex(1);
							handler.postDelayed(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									handler.postDelayed(new Runnable() {
										@Override
										public void run() {
											setCurrentTileIndex(0);
											menubuttonpressed = false;
										}
									}, 3000);
									if(levelVisitedOnce){
//										maintolevelThen();
										maintoMap();
									}
									else{
//										maintolevelFirst();
										maintoMap();
									}
								}
							}, 200);
							
						}
						
						
						
					
//					if(pSceneTouchEvent.isActionUp()){
//						this.setCurrentTileIndex(0);
//						
//					}
//					if(pSceneTouchEvent.isActionOutside()){
//						this.setCurrentTileIndex(0);
//						
//					}
//					if(pSceneTouchEvent.isActionMove()){
//						this.setCurrentTileIndex(0);
//						
//					}
//					if(pSceneTouchEvent.isActionCancel()){
//						this.setCurrentTileIndex(0);
//						
//					}
//					
					
//					if(pSceneTouchEvent.isActionUp() && transitionOnProgress==false){
//						this.setCurrentTileIndex(0);
//						if(levelVisitedOnce){
//							maintolevelThen();
//						}
//						else{
//							maintolevelFirst();
//						}
//						
//					}
					
					
					return true;
				}
			};
			
			
			options = new TiledSprite(810, 480, optionsTR, getVertexBufferObjectManager()){
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if(pSceneTouchEvent.isActionDown() && transitionOnProgress==false && menubuttonpressed == false){
						if(sound){
							tapm.play();
						}
						
						menubuttonpressed = true;
						this.setCurrentTileIndex(1);
						handler.postDelayed(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								handler.postDelayed(new Runnable() {
									@Override
									public void run() {
										setCurrentTileIndex(0);
										menubuttonpressed = false;
									}
								}, 3000);
								mainToOptions();
							}
						}, 200);
						
					}
					

					
					return true;
				}
			};
			credits = new TiledSprite(810, 560, creditsTR, getVertexBufferObjectManager()){
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if(pSceneTouchEvent.isActionDown() && transitionOnProgress==false &&menubuttonpressed == false){
						if(sound){
							tapm.play();
						}
						
						menubuttonpressed = true;
						this.setCurrentTileIndex(1);
						handler.postDelayed(new Runnable() {
							
							@Override
							public void run() {
								loadScene.unregisterTouchArea(play);
								loadScene.unregisterTouchArea(options);
								loadScene.unregisterTouchArea(credits);
								loadScene.unregisterTouchArea(fb);
								loadScene.unregisterTouchArea(tw);
								
								oncredits = true;
								
								// TODO Auto-generated method stub
								handler.postDelayed(new Runnable() {
									@Override
									public void run() {
										setCurrentTileIndex(0);
										menubuttonpressed = false;
									}
								}, 500);
								credit.registerEntityModifier(new AlphaModifier(0.4f, 0, 1));
							}
						}, 200);
						
					}
					
					return true;
				}
			};
			emos = new Sprite(-350, 693, emomanyTR, getVertexBufferObjectManager());
			emos2 = new Sprite(-350, 693, emomanyTR, getVertexBufferObjectManager());
			emos2.setScale(0.7f);
			
			eggs = new Sprite(810, 886, eggmanyTR, getVertexBufferObjectManager());
			tw = new Sprite(364, 885, twitterTR, getVertexBufferObjectManager()){
				
					@Override
					public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
							float pTouchAreaLocalX, float pTouchAreaLocalY) {
						if(pSceneTouchEvent.isActionDown()){
							String url = "https://twitter.com/PhotonFission";
							Intent i = new Intent(Intent.ACTION_VIEW);
							i.setData(Uri.parse(url));
							startActivity(i);
						}
						return super
								.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
					}
			};
			tw.setAlpha(0);
			//for china 
//			tw.setVisible(false);
			
			
			fb = new Sprite(486, 885, facebookTR, getVertexBufferObjectManager()){
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if(pSceneTouchEvent.isActionDown()){
						String url = "http://www.facebook.com/pages/Eat-em-all/144865022318112";
						Intent i = new Intent(Intent.ACTION_VIEW);
						i.setData(Uri.parse(url));
						startActivity(i);
					}
					return super
							.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
				}
			};
			///for china
//			fb.setVisible(false);
			
			
			fb.setAlpha(0);
			loadBackground.attachChild(play);
			loadBackground.attachChild(options);
			loadBackground.attachChild(credits);
			
			
			//forchina
			loadBackground.attachChild(tw);
			loadBackground.attachChild(fb);
			
			loadBackground.attachChild(emos);
			loadBackground.attachChild(eggs);
			
			
			
			
			
			
			
			MoveModifier labelmove = new MoveModifier(0.7f, label.getX(), label.getX(), label.getY(), 160 ,EaseBackOut.getInstance());
			labelmove.addModifierListener(new IModifierListener<IEntity>() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					// TODO Auto-generated method stub
					
					if(music){
						bgmusic.play();
					}
					
					medalien.registerEntityModifier(new MoveModifier(0.5f, medalien.getX(), 0, medalien.getY(), -150));
					smlalien.registerEntityModifier(new MoveModifier(0.5f, smlalien.getX(), 800, smlalien.getY(), -150));
					bigalien.registerEntityModifier(new RotationModifier(0.5f, 0, 5));
					loading.registerEntityModifier(new MoveModifier(0.5f, loading.getX(), -200	, loading.getY(),  loading.getY()));
					eggthree.registerEntityModifier(new MoveModifier(0.5f, eggthree.getX(), eggthree.getX(), eggthree.getY(), 1400));
					labelback.registerEntityModifier(new AlphaModifier(0.5f, 0, 0.4f));
					shipLight.registerEntityModifier(new AlphaModifier(0.5f, shipLight.getAlpha(), 0));
					
					CubicBezierCurveMoveModifier cbm = (new CubicBezierCurveMoveModifier(2f, bigalien.getX(), bigalien.getY(), -100, 55, -50, 120, -5, 154));
					bigalien.registerEntityModifier(new ParallelEntityModifier(new ScaleModifier(1, 1, 0.75f) , cbm)  );
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					// TODO Auto-generated method stub
//					play.registerEntityModifier(new MoveXModifier(0.5f, play.getX(), 236, EaseStrongOut.getInstance()));
//					
//					options.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.2f, 1, 1), new MoveXModifier(0.5f,options.getX(), 236 , EaseStrongOut.getInstance())));
//					quit.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.4f, 1, 1), new MoveXModifier(0.5f,quit.getX(), 236, EaseStrongOut.getInstance())));
					
					play.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.1f, 0, 1),new ParallelEntityModifier(new AlphaModifier(0.5f, 0, 1), new MoveXModifier(0.5f, play.getX(), 236 , EaseExponentialOut.getInstance()))));
					options.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.3f, 0, 1),new ParallelEntityModifier(new AlphaModifier(0.5f, 0, 1), new MoveXModifier(0.5f,options.getX(), 236 ,EaseExponentialOut.getInstance()))));
					credits.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.5f, 0, 1),new ParallelEntityModifier(new AlphaModifier(0.5f, 0, 1), new MoveXModifier(0.5f,credits.getX(), 236,EaseExponentialOut.getInstance()))));
					
					
					
					eggs.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.8f, 1, 1), new MoveXModifier(0.5f,eggs.getX(), 351,EaseStrongOut.getInstance())));
					emos.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.8f, 1, 1), new MoveXModifier(0.5f,emos.getX(), 0,EaseStrongOut.getInstance())));
					
					fb.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(1.4f,0, 0),new ParallelEntityModifier(new MoveYModifier(0.5f,fb.getY(), 818,EaseStrongOut.getInstance() ) , new AlphaModifier(0.3f, 0, 1)  )));
					tw.registerEntityModifier( new SequenceEntityModifier(new AlphaModifier(1.4f,0, 0),new ParallelEntityModifier(new MoveYModifier(0.5f,fb.getY(), 818,EaseStrongOut.getInstance() ) , new AlphaModifier(0.3f, 0, 1)  ))  );
					handler.postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							loadScene.registerTouchArea(play);
							loadScene.registerTouchArea(options);
							loadScene.registerTouchArea(credits);
							loadScene.registerTouchArea(fb);
							loadScene.registerTouchArea(tw);
							
							onMainMenu = true;
						}
					}, 1500);
				}
			});
			label.registerEntityModifier(labelmove);
			
			credit = new Sprite(0, 0, creditTR, getVertexBufferObjectManager());
			loadBackground.attachChild(credit);
			credit.setAlpha(0);
			
			
			credit.setCullingEnabled(true);
			
			medalien.setCullingEnabled(true);
			smlalien.setCullingEnabled(true);
			bigalien.setCullingEnabled(true);
			loading.setCullingEnabled(true);
			eggthree.setCullingEnabled(true);
			labelback.setCullingEnabled(true);
			shipLight.setCullingEnabled(true);
			
			emos.setCullingEnabled(true);
			fb.setCullingEnabled(true);
			tw.setCullingEnabled(true);
			play.setCullingEnabled(true);
			options.setCullingEnabled(true);
			credits.setCullingEnabled(true);
			
			label.setCullingEnabled(true);
			labelback.setCullingEnabled(true);

		}
		
		private void loadResources() throws IllegalStateException, IOException {
			
			
			loadItemsTextureAtlas = new BuildableBitmapTextureAtlas(getTextureManager(), 1024, 1024 , TextureOptions.BILINEAR);
			eggthreeTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas, this, "eggthree.png");
			loadingTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas, this, "loading.png");
			labelbackTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas, this, "labelback.png");
			emomanyTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas, this, "emomany.png");
			eggmanyTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas, this, "eggmany.png");
			alienmedTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas, this, "alienmed.png");
			aliensmlTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas, this, "aliensml.png");
			alienbigTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas, this, "alienbig.png");
			labelTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas, this, "label.png");
			levelsTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(loadItemsTextureAtlas, this, "levels.png", 2, 1);
			
			loadItemsTextureAtlas3 = new BuildableBitmapTextureAtlas(getTextureManager(), 512, 512 , TextureOptions.BILINEAR);
			particlesTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas3, this, "particles.png");
			soundTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas3, this, "sound.png");
			musicTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas3, this, "music.png");
			totStarTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas3, this, "totstar.png");
			
			loadItemsTextureAtlas2 = new BuildableBitmapTextureAtlas(getTextureManager(), 1024, 1024 , TextureOptions.BILINEAR);
			
			creditsTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(loadItemsTextureAtlas2, this, "credits.png", 1, 2);
			playTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(loadItemsTextureAtlas2, this, "play.png", 1, 2);
			optionsTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(loadItemsTextureAtlas2, this, "options.png", 1, 2);
			
			
			twitterTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas2, this, "twitter.png");
			facebookTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas2, this, "facebook.png");
			sureTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas2, this, "sure.png");
			yesTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas2, this, "yes.png");
			noTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas2, this, "no.png");
			
			lvlstarTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas2, this, "levelstar.png");
			lvlstaremptyTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas2, this, "levelstarempty.png");
			
			toggleTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(loadItemsTextureAtlas2, this, "toggle.png", 1, 2);
			
			
			
			loadItemsTextureAtlas5 = new BuildableBitmapTextureAtlas(getTextureManager(), 1024, 1024 , TextureOptions.BILINEAR);
			creditTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas5, this, "credit.png");
			
			loadItemsTextureAtlas6 = new BuildableBitmapTextureAtlas(getTextureManager(), 512, 512 , TextureOptions.BILINEAR);
			moreTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas6, this, "more.png");
			
			
			try {
				loadItemsTextureAtlas6.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(5, 5, 5));
				loadItemsTextureAtlas6.load();
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//for map
			AreasTextureAtlas1 = new BuildableBitmapTextureAtlas(getTextureManager(), 1024, 1024 , TextureOptions.BILINEAR);
			areaaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(AreasTextureAtlas1, this, "areaa.png", 2, 1);
			
			areadTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(AreasTextureAtlas1, this, "aread.png", 2, 1);
			areaeTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(AreasTextureAtlas1, this, "areae.png", 2, 1);
			
			areagTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(AreasTextureAtlas1, this, "areag.png", 2, 1);
			
			
			lockNormalTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(AreasTextureAtlas1, this, "lockblue.png");
			lockPaidTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(AreasTextureAtlas1, this, "lockred.png");
			
			try {
				AreasTextureAtlas1.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(5, 5, 5));
				AreasTextureAtlas1.load();
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			AreasTextureAtlas2 = new BuildableBitmapTextureAtlas(getTextureManager(), 1024, 1024 , TextureOptions.BILINEAR);
			areafTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(AreasTextureAtlas2, this, "areaf.png", 2, 1);
			areahTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(AreasTextureAtlas2, this, "areah.png", 2, 1);
			areabTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(AreasTextureAtlas2, this, "areab.png", 2, 1);
			areacTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(AreasTextureAtlas2, this, "areac.png", 2, 1);
			try {
				AreasTextureAtlas2.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(5, 5, 5));
				AreasTextureAtlas2.load();
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			mapbgTextureAtlas = new BuildableBitmapTextureAtlas(getTextureManager(), 1024, 1024 , TextureOptions.BILINEAR);
			mapbgTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mapbgTextureAtlas, this, "mapback.png");
			
			try {
				mapbgTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
				mapbgTextureAtlas.load();
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
			
			
			
			
			
			
			
			try {
				loadItemsTextureAtlas5.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
				loadItemsTextureAtlas5.load();
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				loadItemsTextureAtlas3.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(5, 5, 5));
				loadItemsTextureAtlas3.load();
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			try {
				loadItemsTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(5, 5, 5));
				loadItemsTextureAtlas.load();
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				loadItemsTextureAtlas2.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(5, 5, 5));
				loadItemsTextureAtlas2.load();
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			///fonts
//			final ITexture droidFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
//			final ITexture kingdomOfHeartsFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
//			final ITexture neverwinterNightsFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
//			final ITexture plokFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
			final ITexture unrealTournamentFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 1024, 512, TextureOptions.BILINEAR);

			FontFactory.setAssetBasePath("font/");
			this.unrealFont = FontFactory.createFromAsset(this.getFontManager(), unrealTournamentFontTexture, this.getAssets(), "vanilla.ttf", 100, true, android.graphics.Color.WHITE );
			this.unrealFont.load();

			this.optionsFont = FontFactory.createFromAsset(this.getFontManager(), unrealTournamentFontTexture, this.getAssets(), "vanillaa.ttf", 25, true, android.graphics.Color.WHITE );
			this.optionsFont.load();
			
			MusicFactory.setAssetBasePath("mfx/");
			loadMusic();
			
		}
		
		private void loadMusic() throws IllegalStateException, IOException {
			// TODO Auto-generated method stub
			bgmusic = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this,"menua.ogg");
			tapm = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this,"click.ogg");
			lockedm = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this,"locked.ogg");
			
			bgmusic.setLooping(true);
			bgmusic.setVolume(0.3f);
		}


		@Override
		public void onPopulateScene(Scene pScene,
				OnPopulateSceneCallback pOnPopulateSceneCallback)
						throws Exception {
			// TODO Auto-generated method stub

			mEngine.registerUpdateHandler(new TimerHandler(3f, new ITimerCallback() 
			{
				@Override
				public void onTimePassed(final TimerHandler pTimerHandler) 
				{
					mEngine.unregisterUpdateHandler(pTimerHandler);
					try {
						loadResources();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					loadLoadScenes();         
					
					final AlphaModifier am = new AlphaModifier(1.5f, 1f, 0f);
					am.addModifierListener(new IModifierListener<IEntity>() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							mEngine.setScene(loadScene);
							loadScene.registerEntityModifier(new AlphaModifier(0.5f, 0, 1));
						}
					});
					blackoutS.registerEntityModifier(am);
					copyS.registerEntityModifier(new AlphaModifier(0.6f, 1f, 0f));
					
				}

				
			}));

			pOnPopulateSceneCallback.onPopulateSceneFinished();


		}
		
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
				if (transitionOnProgress == false) {
					if (onLevelMenu && onMainMenu == false && onOptionsMenu ==false && oncredits ==false && onMapMenu == false) {
//						leveltomain();
						LevelToMap();
					}
					if (onMainMenu&& onOptionsMenu ==false&& oncredits ==false&& onMapMenu == false) {
						if(loadScene.hasChildScene()){
							finish();
							inquitmenu = false;
						}
						else{
							loadScene.setChildScene(quitScene);
							inquitmenu = true;
						}
					}
					
					else if(inquitmenu&& onOptionsMenu ==false&& oncredits ==false&& onMapMenu == false){
						finish();
					}
					else if(onOptionsMenu&& oncredits ==false&& onMapMenu == false){
						optionsToMain();
					}
					else if(oncredits&& onMapMenu == false){
						credit.registerEntityModifier(new AlphaModifier(0.4f, 1, 0));
						loadScene.registerTouchArea(play);
						loadScene.registerTouchArea(options);
						loadScene.registerTouchArea(credits);
						loadScene.registerTouchArea(fb);
						loadScene.registerTouchArea(tw);
						oncredits = false;
					}
					else if(onMapMenu){
						Maptomain();
					}
				}
				return true;
			} 
			else if(  keyCode == KeyEvent.KEYCODE_MENU && event.getAction() == KeyEvent.ACTION_DOWN ){
				Log.d("ans", "tr:" +transitionOnProgress + " main:"+onMainMenu + " options:"+onOptionsMenu);
				return true;
			}
			
			else {
//				return super.onKeyDown(keyCode, event);
				return false;
			}
			
			
			
		}

		
		
		
		@Override
		public synchronized void onWindowFocusChanged(boolean pHasWindowFocus) {
			// TODO Auto-generated method stub
			super.onWindowFocusChanged(pHasWindowFocus);
			
			if (pHasWindowFocus) {
				if (mIsPausedBefore) {
					if(bgmusic != null){
						bgmusic.resume();

					}
					mIsPausedBefore = false;
				} else {
					//pause stuff here, except music pausing;
				}
			}
		}


		@Override
		protected Dialog onCreateDialog(int id) {
			Context mContext = MainMenu.this;
			final Dialog dialog = new Dialog(mContext);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			
			
			dialog.setContentView(R.layout.dialog);
			dialog.setTitle("Custom Dialog");
			dialog.setCanceledOnTouchOutside(false);
			dialog.setCancelable(false);
			
			TextView text = (TextView) dialog.findViewById(R.id.dialogtext);
			ImageView image = (ImageView) dialog.findViewById(R.id.dialogimage);
			ImageButton ib = (ImageButton) dialog.findViewById(R.id.imageButton1);
			ImageButton cb = (ImageButton) dialog.findViewById(R.id.imageButton2);
			TextView tips = (TextView) dialog.findViewById(R.id.textViewt);
			
			tips.setText("Spread the Word!!");
			cb.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					if(event.getAction() == MotionEvent.ACTION_DOWN){
						dialog.dismiss();
					}
					
					
					return true;
					
					
					
				}
			});
			switch (id) {
			
			case 1:
				
//				text.setText("Enjoying Eat 'em all? \nThis is an Android game developped for SAMSUNG APPS. Please support the developpers by telling your 'Samsung andoid friends' about Eat 'em all.\nThats all what we expect from you! \n\nIf you support us, we will make sure that you will get more interesting and challenging levels in future\n\nEnjoy the rest of the game!!");
				text.setText("Enjoying Eat'em all? \nPlease support the developpers by spreading the word about Eat'em All to your android buddies. Enjoy the rest of the game!!");
				
				
				image.setImageResource(R.drawable.enjoy);
				
				ib.setAlpha(0);
				
				
				
				return dialog;
			
			default:
				return null;
			}
		}
		
}
