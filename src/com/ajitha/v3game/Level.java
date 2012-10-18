

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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleAtModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.CircleOutlineParticleEmitter;
import org.andengine.entity.particle.emitter.CircleParticleEmitter;
import org.andengine.entity.particle.emitter.RectangleParticleEmitter;
import org.andengine.entity.particle.initializer.AlphaParticleInitializer;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.ScaleParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ColorParticleModifier;
import org.andengine.entity.particle.modifier.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.primitive.vbo.ILineVertexBufferObject;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.sprite.vbo.ITiledSpriteVertexBufferObject;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.StrokeFont;
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
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;
import org.andengine.util.modifier.ease.EaseBounceOut;
import org.andengine.util.modifier.ease.EaseCubicInOut;
import org.andengine.util.modifier.ease.EaseExponentialIn;
import org.andengine.util.modifier.ease.EaseExponentialOut;
import org.andengine.util.modifier.ease.EaseStrongInOut;
import org.andengine.util.modifier.ease.EaseStrongOut;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.opengl.GLES20;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/* 
* @author Ajitha Samarasinghe
* 
*/


public class Level extends BaseGameActivity {
	
	public static boolean killing = false;
	
	public float lineWidth = 7;
	
	public TiledTextureRegion pointTextureRegion;
	public TiledTextureRegion playerTextureRegion;
	
	private CameraScene losescene;
//	private Vibrator mVibrator;
	private TiledTextureRegion starTextureRegion;
	
	public Scene scene;

	public ArrayList<aPoint> aPointList = new ArrayList<aPoint>();
	public ArrayList<aStick> aStickList = new ArrayList<aStick>();

	public ArrayList<Point> PointList = new ArrayList<Point>();
	public ArrayList<Stick> StickList = new ArrayList<Stick>();
	
	public ArrayList<Stick> effectsStickList = new ArrayList<Stick>();
//	public ArrayList<State> stateList = new ArrayList<State>();

	public InitProperties initProps = new InitProperties();
	protected String fn;
	private File file;
	private Camera camera;
//	private BuildableBitmapTextureAtlas collectableAtlas;
//	private TextureRegion starRegion;
//	private TextureRegion grenadeRegion;
//	private TextureRegion thunderRegion;
//	private TextureRegion chargesRegion;
//	private TextureRegion flameRegion;
	private StrokeFont strFont;
	private BuildableBitmapTextureAtlas pointTatlas;
//	private BitmapTextureAtlas mBitmapTextureAtlas;
//	private TiledTextureRegion mFaceTextureRegion1;
//	private TextureRegion mFaceTextureRegion;

	protected boolean fileexist = false;
	public boolean playerPlaced = false;
	public boolean onaPoint = false;

	private BuildableBitmapTextureAtlas playerAtlas;
	private Handler handler;
	
	////
	public Player player;
	public Stick currentStick;
	public float distance ;
	private Scene wonScene;
	public int initStickCount = -1;
	public int removedStickCount = 0 ;
	private BuildableBitmapTextureAtlas powerAtlas;
	private TiledTextureRegion thunderTextureRegion;
	private TiledTextureRegion flameTextureRegion;
	private TiledTextureRegion grenadeTextureRegion;
	private TiledTextureRegion chargeTextureRegion;
	private TiledTextureRegion bridgeTextureRegion;
	public TiledTextureRegion[] trArray ;
	
	///
	public int[] costofbuyables = new int[]{1,2,2,3,3};
	public boolean[] powerBuyable;
	public int[] initPower ;
	public ArrayList<Power> powers = new ArrayList<Level.Power>();
	public boolean powerOn = false;
	
	boolean placingbomb = false;
	
	public Point thunderPoint;
	private FPSLogger fpsl;
	public Text timerBomb;
	private Font mFont;

	private Handler handler2;
	public float velocity = 320;
	public int initStars;
	public int maxStars = 3;
	public ArrayList<Point> flamePoints = new ArrayList<Point>();
	public ArrayList<Stick> flameSticks = new ArrayList<Stick>();
	public ArrayList<Star> starList = new ArrayList<Star>();
	private ArrayList<egg> eggListMain = new ArrayList<Level.egg>();
	
	public Point tempconnector;
	public float tt;
	public BitmapTextureAtlas mParticleTextureAtlas;
	public TextureRegion mParticleTextureRegion;
	public com.ajitha.v3game.Level.Stick effectStick;
	private BuildableBitmapTextureAtlas decorAtlas;
	private TiledTextureRegion stickTextureRegion;
	private TiledTextureRegion decopointTextureRegion;
	private TiledTextureRegion eggTextureRegion;
	private BitmapTextureAtlas rayTextureAtlas;
	private TextureRegion rayTextureRegion;
	private TiledTextureRegion redeggTextureRegion;
	private TiledTextureRegion hayholeTextureRegion;
	private TiledTextureRegion hayTextureRegion;
	private BuildableBitmapTextureAtlas effectsAtlas;
	private TiledTextureRegion thundersTextureRegion;
	private TiledTextureRegion cloudTextureRegion;
	private AlphaModifier alphaModThunder;
	private BuildableBitmapTextureAtlas backTextureAtlas1;
	private TextureRegion backTextureRegion;
	private BitmapTextureAtlas backTextureAtlas;
	private Sprite backgroud;
	
	
	
	
	
	private BuildableBitmapTextureAtlas bpowerAtlas;
	private TiledTextureRegion bstarTextureRegion;
	private TiledTextureRegion bflameTextureRegion;
	private TextureRegion stripTextureRegion;
	
	private TextureRegion buttonbackTextureRegion;
	private TiledTextureRegion explosionTextureRegion;
	private TiledTextureRegion bthunderTextureRegion;
	private TiledTextureRegion bgrenadeTextureRegion;
	private TiledTextureRegion bchargesTextureRegion;
	private TiledTextureRegion bbridgeTextureRegion;
	private TiledTextureRegion haybridgeTextureRegion;
	public Scene scene2;

	public BuildableBitmapTextureAtlas collectAtlas;

	public TiledTextureRegion collectableTextureRegion;

	public Random r = new Random();

	public Random r2;

	public Display display;

	private int actualWidth;

	private int actualHeight;

	private double actualRatio;

	private boolean yStretched = false;

	private float yScaleFactor;

	private boolean xStretched = false;

	private float xScaleFactor;

	private BuildableBitmapTextureAtlas menuAtlas;

//	private TiledTextureRegion tryagainTR;

	private TiledTextureRegion nextTR;

//	private TiledTextureRegion diedTR;

//	private TiledTextureRegion wonTR;

//	private TiledTextureRegion replayTR;

	private TiledTextureRegion menuquitTR;

	private TiledTextureRegion resumeTR;

	private TiledTextureRegion restartTR;

	private TiledTextureRegion mainmenuTR;

	private Scene pauseScene;

//	private SpriteMenuItem quitMenuItem;

	private Sprite restart;

	private Sprite resume;

	private Sprite mainmenu;

	private Sprite menuquit;

	private boolean menuaniminprocess = false;

	private Rectangle menubackdarker;

	private TextureRegion menuTR;

	private TextureRegion retryTR;

	

	private TiledTextureRegion starsmlTR;

	

	private TextureRegion winBgTR;

	private TextureRegion nextaTR;

	private BuildableBitmapTextureAtlas menuAtlas2;

	private Sprite winbg;

//	private Sprite wonlable;

	private TiledSprite starempty2;

	private TiledSprite starempty1;

	private TiledSprite starempty3;

	private TiledSprite star3;

	private TiledSprite star2;

	private TiledSprite star1;

	private Sprite nexta;

	private Sprite retrya;

	private Sprite menua;

	private Rectangle wonmenubackdarker;

	private BuildableBitmapTextureAtlas animbombAtlas;

	private TiledTextureRegion grenadeTR;

	private TextureRegion chargeTR;
	
	public String levelName;
	
	public boolean won = false; // this is to avoid force close when theres no stick to be bloown
	
	public String bgname = null ;
	public String[] bgnames = new String[]{"backa.png","backb.png","backc.png","backd.png","backe.png","backd.png","backe.png" ,
			"backa.png","backb.png","backc.png","backd.png","backe.png","backd.png","backe.png",
			"backa.png","backb.png","backc.png","backd.png","backe.png","backd.png","backe.png",
			"backa.png","backb.png","backc.png","backd.png","backe.png","backd.png","backe.png",
			"backa.png","backb.png","backc.png","backd.png","backe.png","backd.png","backe.png",
			"backa.png","backb.png","backc.png","backd.png","backe.png","backd.png","backe.png",
			"backa.png","backb.png","backc.png","backd.png","backe.png","backd.png","backe.png",
			"backa.png","backb.png","backc.png","backd.png","backe.png","backd.png","backe.png",
			"backa.png","backb.png","backc.png","backd.png","backe.png","backd.png","backe.png",
			"backa.png","backb.png","backc.png","backd.png","backe.png","backd.png","backe.png"
	}; // 

	private TextureRegion lostBgTR;

	private BuildableBitmapTextureAtlas menuAtlas3;

	private boolean gamefinished = false;

	private TextureRegion powerbgTR;

	private Sprite powerbg;

	private Rectangle starback;

	private Scene loseScene;

	private Rectangle losemenubackdarker;

	private Sprite losebg;

	private Sprite retryb;

	private Sprite menub;

	protected boolean bombBlasting = false;
	private boolean lost = false;

	private BuildableBitmapTextureAtlas loadItemsTextureAtlas;

	private TextureRegion loadingTR;

	private Scene SplashScene;

	private Sprite loading;

	private Rectangle sceneCover;

	private TextureRegion levelpauseTR;

	private TextureRegion levelrestartTR;

	private Sprite levelPause;

	private Sprite levelrestart;

	private Music bgmusic;

	private String bgMusicName;

	private Music eatm;

	private Music jumpm;

	private Music thunderm;

	private Music makethunderm;

	private Music hammerm;

	private Music explosionm;

	private Music countm;

	private Music angrym;

	private Music burnm;

	private Music winm;

	private Music losem;

	private Music staram;

	private Music starbm;

	private Music starcm;

	private StrokeFont strFont2;
	
	public boolean soundOn = true;
	public boolean musicOn = true;
	public boolean particlesOn = true;

	private Music tapm;

	private boolean eatplaingonpause = false;

	private boolean hammerplayingonpause = false;

	private TextureRegion angryTR;

	private Sprite angry;

	private int levelid;

	private boolean mIsPausedBefore;

	private int currentArea;
	
	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 1280;
	
	/////////////////////////// State //////////////////////////////////////////////
//	public class State{
//		public ArrayList<Point> tPointList  = new ArrayList<Level.Point>();
//		public ArrayList<Stick> tStickList = new ArrayList<Level.Stick>();
//		public boolean tplayerPlaced;
//		
//		
//		public float playerX ;
//		public float playerY ;
//		public int cuttentPointID;
//		
//		public ArrayList<Stick> teffectsStickList ;
//		public Player tplayer;
//		
//		public float tdistance ;
//		
//		public boolean tpowerOn ;
//		public int tinitStars;
//		
//		public ArrayList<Point> tflamePoints ;
//		public ArrayList<Star> tstarList ;
//		private ArrayList<egg> teggListMain ;
//		public ArrayList<Power> tpowers ;
//		
//		public State(){
//			
//			for(int i = 0 ; i < PointList.size() ; i++ ) {
//				Point p = new Point(PointList.get(i).getX(), PointList.get(i).getY());
//				Point ap = PointList.get(i);
//		
//				p.visitable = ap.visitable;
//				p.startable = ap.startable;
//				p.visited = ap.visited;
//				p.mustStart = ap.mustStart;
//
//				p.hasCharges = ap.hasCharges;
//				p.hasFlame = ap.hasFlame;
//				p.hasGrenade = ap.hasGrenade;
//				p.hasStar = ap.hasStar;
//				p.hasThunder = ap.hasThunder;
//
//				p.charged = ap.charged;
//				p.selected = ap.selected;
//				p.init = ap.init;
//				p.SLinit = ap.SLinit;
//				p.flameStart = ap.flameStart;
//				p.flamed = ap.flamed;
//				
//				tPointList.add(p);
//			}
//			
//			for(int i = 0 ; i < StickList.size() ; i ++){
//				Stick s = new Stick(1,1,1,1);
//				
//				s.charged = StickList.get(i).charged ;
//				s.strength = StickList.get(i).strength ;
//				
//				
//				
//				for(int j = 0 ; j < StickList.get(i).eggList.size() ; j++){
//					egg e = new egg(-10, -10);
//					egg te = StickList.get(i).eggList.get(j);
//					
//					e.strength = te.strength;
//					e.colided = te.colided;
//					e.crashed = te.crashed;
//					e.updating = te.updating;
//					e.effected = te.effected;
//					
//					s.eggList.add(e);
//				}
//				
//				tStickList.add(s);
//				
//			}
//			
//			
//			
//			
//			this.tplayerPlaced = playerPlaced;
//			this.teffectsStickList = effectsStickList;
//			
//			this.playerX = player.getX();
//			this.playerY = player.getY();
//			this.cuttentPointID = player.currentPos.ID;
//			
//			
//			
//			this.tdistance = distance;
//			this.tpowerOn = powerOn;
//			this.tinitStars = initStars;
//			this.tflamePoints = flamePoints;
//			this.tstarList = starList;
//			this.teggListMain = eggListMain;
//			this.tpowers = powers;
//			
//			
//		}
//		
//	}
	
	
//	public void loadState(State state){
//		
//		
//		
//		//point properties reset
//		for(int i = 0 ; i < PointList.size() ; i++ ) {
//			Point p = state.tPointList.get(i);
//	
//			PointList.get(i).visitable = p.visitable;
//			PointList.get(i).startable = p.startable;
//			PointList.get(i).visited = p.visited;
//			PointList.get(i).mustStart = p.mustStart;
//
//			PointList.get(i).hasCharges = p.hasCharges;
//			PointList.get(i).hasFlame = p.hasFlame;
//			PointList.get(i).hasGrenade = p.hasGrenade;
//			PointList.get(i).hasStar = p.hasStar;
//			PointList.get(i).hasThunder = p.hasThunder;
//
//			PointList.get(i).charged = p.charged;
//			PointList.get(i).selected = p.selected;
//			PointList.get(i).init = p.init;
//			PointList.get(i).SLinit = p.SLinit;
//			PointList.get(i).flameStart = p.flameStart;
//			PointList.get(i).flamed = p.flamed;
//			
//		}
//		
//		
//		for(int i = 0 ; i < StickList.size() ; i ++){
//
//			
//			StickList.get(i).charged = state.tStickList.get(i).charged;
//			StickList.get(i).strength = state.tStickList.get(i).strength;
//			
//			
//			
//			for(int j = 0 ; j < state.tStickList.get(i).eggList.size() ; j++){
//				
//
//				
//				StickList.get(i).eggList.get(j).strength = state.tStickList.get(i).eggList.get(j).strength;
//				StickList.get(i).eggList.get(j).colided = state.tStickList.get(i).eggList.get(j).colided;
//				StickList.get(i).eggList.get(j).crashed = state.tStickList.get(i).eggList.get(j).crashed;
//				StickList.get(i).eggList.get(j).updating = state.tStickList.get(i).eggList.get(j).updating;
//				StickList.get(i).eggList.get(j).effected = state.tStickList.get(i).eggList.get(j).effected;
//				
//				
//			}
//			
//			 
//			
//			
//		}
//		
//		
//		for(Stick s : StickList){
//			if(s.strength > 0){
//				for(AnimatedSprite hay : s.hayList){
//					hay.setAlpha(1);
//				}
//				
//			}
//		}
//		
//		
//		
//		
//		
//		/// player c,y and currentPos set
//		player.setX(state.playerX);
//		player.setY(state.playerY);
//		
//		for(Point p : PointList){
//			if (p.ID == state.cuttentPointID){
//				player.currentPos = p ;
//			}
//		}
//		initStars  =state.tinitStars ;
//		
//		
////		scene.reset();
//		
//		
////		playerPlaced = state.tplayerPlaced ;
////		effectsStickList = state.teffectsStickList  ;
//		
//		
////		currentStick = state.tcurrentStick  ;
////		distance=  state.tdistance ;
////		powerOn = state.tpowerOn  ;
//		
////		flamePoints = state.tflamePoints  ;
////		starList=  state.tstarList  ;
////		eggListMain= state.teggListMain  ;
////		powers =state.tpowers  ;
//		
//		
//		
//		
//		
//		
//	
//		
//			
//			
//
//
//		
////		
////		
////		for (final Stick s1 : StickList) {
////
////					scene2.attachChild(s1);
////					//				scene.attachChild(stickParticles(s.p1, s.p2));
////					//				effectsStickList.add(stickParticles(s));
////					decorate(s1);
////				
////
////			
////			
////
////		}
//		
////
////	
////		for(int i = 0 ; i < maxStars ; i ++){
////			if(i<initStars){
////				starList.add(new Star(i,1));
////			}
////			else{
////				starList.add(new Star(i,0));
////			}
////		}
////		for(Star s : starList){
////			s.setScale(0.6f);
////			scene2.attachChild(s);
////		}
//		
//		
////		if(playerPlaced==true){
////			mEngine.getScene().attachChild(player);
////			playerPlaced = true;
////			onaPoint = true;
////		}
//		
//	
//		//add power buttons to the scene
////		for(int i = 0; i <5 ; i ++){
////			Power tmp;
////			if(i == 3){
////				tmp = new Power(i)
////				{
////					@Override
////					public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX,float pTouchAreaLocalY) {
////						if(pSceneTouchEvent.isActionDown() && availability>0 && onaPoint){
////							//////
////							flamePoints.add(player.currentPos);
////							player.currentPos.flameStart = true;
////
////							if(functioning){
////								functioning= false;
////								powerOn = false;
////								if(!flamePoints.isEmpty()){
////									for(Point p : flamePoints){
////										p.flamed = false;
////										p.flameStart = false;
////									}
////									flamePoints.clear();
////									flameSticks.clear();
////									availability--;
////								}
////								this.animate(new long[]{0,100000,0}, new int[]{0,1,2}, true);
////							}
////							else{
////								for(Power p : powers){
////
////									p.functioning = false;
////
////									if(p.availability == 0){
////										p.animate(new long[]{100000,0,0}, new int[]{0,1,2}, true);
////									}
////									else{
////										p.animate(new long[]{0,100000,0}, new int[]{0,1,2}, true);
////
////									}
////								}
////
////								functioning= true;
////								powerOn = true;
////								this.animate(new long[]{0,0,10000}, new int[]{0,1,2}, true);
////								Log.d("ajitha",player.currentPos.flameStart+"");
////							}
////						}
////
////						if(availability== 0 && onaPoint && pSceneTouchEvent.isActionDown() && initStars>=cost && buyable){
////							this.availability++;
////							initStars -= cost;
////						}
////
////
////						return true;
////
////
////					}
////				};
////			}
////			else{
////				tmp = new Power(i);
////			}
////
////			powers.add(tmp);
////			mEngine.getScene().attachChild(tmp);
////			mEngine.getScene().attachChild(tmp.available);
////			mEngine.getScene().registerTouchArea(tmp);
////			mEngine.getScene().attachChild(tmp.buttonStrip);
////		}
//
////		for(Power p : powers){
////			for(powerStar ps : p.stars){
////				scene.attachChild(ps);
////			}
////		}
//		
//
//	}
	
////////////////////////////////  Star ///////////////////////////////////////////////////////////////////////////////////	
	public class Star extends TiledSprite{
		
		public int status ;
		public int index ;

	public Star(float pX, float pY, float pWidth, float pHeight,
			ITiledTextureRegion pTiledTextureRegion,
			ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject) {
		super(pX, pY, pWidth, pHeight, pTiledTextureRegion,
				pTiledSpriteVertexBufferObject);
		// TODO Auto-generated constructor stub
	}
	
	public Star(int i , int s){
		super(i*70 +15, 10, 70, 70, starTextureRegion,mEngine.getVertexBufferObjectManager());
		this.index = i;
		this.status = s;
		this.setCullingEnabled(true);
		
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		if(this.index >= initStars){
//			this.animate(new long[]{100000,0,0}, new int[]{0,1,2}, true);
			this.setCurrentTileIndex(0);
		}
		else{
//			this.animate(new long[]{0,0,100000}, new int[]{0,1,2}, true);
			this.setCurrentTileIndex(2);
		}
		
	super.onManagedUpdate(pSecondsElapsed);
	}
		
	}
	
public class powerStar extends TiledSprite{
		
		public int cost;
		public int status ;
		public int index ;

	public powerStar(float pX, float pY, float pWidth, float pHeight,
			ITiledTextureRegion pTiledTextureRegion,
			ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject) {
		super(pX, pY, pWidth, pHeight, pTiledTextureRegion,
				pTiledSpriteVertexBufferObject);
		// TODO Auto-generated constructor stub
	}
	
	public powerStar(int i , int s , int cost){
//		super(224*supi + 50 + (i-1)*23.33f, CAMERA_HEIGHT-44-20, starTextureRegion.getWidth(), starTextureRegion.getHeight(), starTextureRegion,mEngine.getVertexBufferObjectManager());
		
		super((i-1)*23.33f  + 25, 90, starTextureRegion.getWidth(), starTextureRegion.getHeight(), starTextureRegion,mEngine.getVertexBufferObjectManager());
		setCullingEnabled(true);
		this.index = i;
		this.status = s;
		this.setScale(0.433f);
		this.cost = cost;
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		if(this.cost > initStars){
			if(index < initStars){
//				this.animate(new long[]{0,1000000,0}, new int[]{0,1,2}, true);
				this.setCurrentTileIndex(1);
			}
			else{
//				this.animate(new long[]{100000,0,0}, new int[]{0,1,2}, true);
				this.setCurrentTileIndex(0);
			}
		}
		else{
//			this.animate(new long[]{0,0,100000}, new int[]{0,1,2}, true);
			this.setCurrentTileIndex(2);
		}
		
	super.onManagedUpdate(pSecondsElapsed);
	}
		
	}
	
////////////////////////////	Power    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public class Power extends TiledSprite{
		public int availability = 0 ;
		public int position ;
		public boolean functioning = false;
		public boolean buyable;
		public int cost ;
		public Text available;
		public ArrayList<powerStar> stars = new ArrayList<powerStar>();
		public Sprite buttonStrip;
		
		public Power(float pX, float pY, float pWidth, float pHeight,ITiledTextureRegion pTiledTextureRegion,ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject) {
			super(pX, pY, pWidth, pHeight, pTiledTextureRegion,pTiledSpriteVertexBufferObject);
			// TODO Auto-generated constructor stub
		}
		public Power(int i){
//			super(224*i , CAMERA_HEIGHT-224, 224, 224, trArray[i],mEngine.getVertexBufferObjectManager());
//			super(  (224-15)*i + 30 , CAMERA_HEIGHT-224 + 70, 224, 224, trArray[i],mEngine.getVertexBufferObjectManager());
			super(  (160)*i , CAMERA_HEIGHT-160  , 160, 160, trArray[i],mEngine.getVertexBufferObjectManager());
			
			this.setCullingEnabled(true);
			
			availability = initPower[i];
			position = i;
			buyable = powerBuyable[i];
			cost = costofbuyables[i];
//			available = new Text(160*i + 120, CAMERA_HEIGHT-190, strFont, "x"+initPower[i], mEngine.getVertexBufferObjectManager());
			available = new Text(110, 15, strFont, "x"+initPower[i], mEngine.getVertexBufferObjectManager());
			
			
			if(initPower[i] == 0){
//				this.animate(new long[]{10000000,0}, new int[]{0,1}, true);
				this.setCurrentTileIndex(0);
			}
			else{
//				this.animate(new long[]{0,10000000}, new int[]{0,1}, true);
				this.setCurrentTileIndex(1);
			}
			
			if(powerBuyable[i]){
				for(int z = 0 ; z < costofbuyables[i] ; z ++){
					this.stars.add(new powerStar(z, 1, costofbuyables[i]));
				}
			}
			buttonStrip = new Sprite(getX()	, getY(), stripTextureRegion, mEngine.getVertexBufferObjectManager());
			buttonStrip.setColor(new Color(Color.RED));
			buttonStrip.registerEntityModifier(new LoopEntityModifier( new SequenceEntityModifier(new AlphaModifier(0.5f, 0.5f, 1) , new AlphaModifier(0.5f,1,0.5f)))) ;
			buttonStrip.setVisible(false);
			
			this.setScale(0.9f);
			buttonStrip.setScale(0.9f);
			
			this.setAlpha((float) 0.7);
		}
		
		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
			// TODO Auto-generated method stub
			
		
			if(pSceneTouchEvent.isActionDown() && availability>0 && onaPoint){
				
				if(functioning){
					functioning= false;
					powerOn = false;
//					this.animate(new long[]{0,10000000}, new int[]{0,1}, true);
					this.setCurrentTileIndex(1);
					
				}
				else{
					for(Power p : powers){
						
						p.functioning = false;
						if(p.availability == 0){
//							p.animate(new long[]{100000,0}, new int[]{0,1}, true);
							p.setCurrentTileIndex(0);
						}
						else{
//							p.animate(new long[]{0,100000}, new int[]{0,1}, true);
							p.setCurrentTileIndex(1);
							
						}
					}
					powerOn = true;
					functioning= true;
					
					//// placing the charge on click !!
					
					//newly added  &&player.currentPos.charged == false)
					if(position == 1 &&player.currentPos.charged == false){
						player.placeBomb();
						player.currentPos.charged = true;
						powers.get(1).functioning = false;
						powers.get(1).availability --;
						powerOn = false;
					}
					
					
//					this.animate(new long[]{0,0,10000}, new int[]{0,1,2}, true);
				}
			}
			if(availability== 0 && onaPoint && pSceneTouchEvent.isActionDown() && initStars>=cost && buyable){
				this.availability++;
				for (Power p : powers){
					  p.buyable = false;
					  for(powerStar ps : p.stars){
						  ps.setVisible(false);
					  }
				}
			}
			
			
			return true;
		}
		@Override
		protected void onManagedUpdate(float pSecondsElapsed) {
			this.available.setText("x"+availability);
			if(this.availability == 0){
//				this.animate(new long[]{10}, new int[]{0}, false);
				this.setCurrentTileIndex(0);
				this.setAlpha(0.7f);
				buttonStrip.setVisible(false);
				
			}
			else if (availability != 0 && functioning == false){
//				this.animate(new long[]{10}, new int[]{1}, false);
				this.setCurrentTileIndex(1);
				this.setAlpha(0.7f);
				buttonStrip.setVisible(false);
			}
			else if (availability != 0 && functioning){
//				this.animate(new long[]{10}, new int[]{1}, false);
				this.setCurrentTileIndex(1);
				this.setAlpha(1);
				buttonStrip.setPosition(this.getX(), this.getY());
				buttonStrip.setVisible(true);
				
			}
			
			
			
			super.onManagedUpdate(pSecondsElapsed);
		}
		
	}
	public void gotoPoint(final Point p){
		
		if(onaPoint && placingbomb==false){
			
			
//		runOnUiThread(new Runnable() {
//			
//			@Override
//			public void run() {


				distance = (float) Math.sqrt((p.centerX - player.currentPos.centerX)
						* (p.centerX -  player.currentPos.centerX)
						+ (p.centerY -  player.currentPos.centerY)
						* (p.centerY -  player.currentPos.centerY));

				

				tt = distance / velocity  ;

				for (Stick s :  player.currentPos.connectedSticks) {
					if (s.p1.equals(p) && s.p2.equals(player.currentPos)) {
						currentStick = s;
					}
					if (s.p1.equals(player.currentPos) && s.p2.equals(p)) {
						currentStick = s;
					}
				}
				

				if(powerOn == false){
					
					if (p.visitable != 0 && currentStick.strength != 3 && currentStick.strength != 0) {
						///////
						
						float rotat ;
						
						if(player.currentPos.x < p.x) {
							rotat = -25 +(float) Math.toDegrees(Math.atan2(p.centerY - player.currentPos.centerY, p.centerX- player.currentPos.centerX));
						}
						else{
							rotat = 25 + (float) Math.toDegrees(Math.atan2(player.currentPos.centerY - p.centerY, player.currentPos.centerX- p.centerX));
						}
							
						
						
						
						if(rotat > 0){
							if(rotat > 70){
								rotat += 10;
							}
						}
						
						if(rotat < 0){
							if(rotat < -70){
								rotat -= 10;
							}
						}
						
//						float rotat = -30+ (float) Math.toDegrees(Math
//								.atan2(p.centerY - player.currentPos.centerY, p.centerX
//										- player.currentPos.centerX));
//						if(player.currentPos.x > p.x){
//							rotat = (180- Math.abs((rotat+30)))*-1  +90;
//						}
						
						final RotationModifier Rotmod = new RotationModifier(0.2f,
								player.getRotation(),rotat );
						Rotmod.addModifierListener(new IModifierListener<IEntity>() {

							@Override
							public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

								onaPoint = false;

								if(player.currentPos.x < p.x){
									player.turnRightIn();
								}
								else{
									player.turnLeftIn();
								}
							} 



							@Override
							public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
								// TODO Auto-generated method stub
								
							}
						});


						final MoveModifier mm =new MoveModifier(tt, player.currentPos.centerX-player.getWidth() / 2  + 1 , p.centerX- player.getWidth() / 2 +1, player.currentPos.centerY- player.getHeight() / 2-29, p.centerY- player.getHeight() / 2 - 29 );
						mm.addModifierListener(new IModifierListener<IEntity>() {

							@Override
							public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
								// TODO Auto-generated method stub

								if(player.currentPos.x < p.x){
									if(currentStick.strength == 1){
										player.eatRight();
										eatm.play();
										
									}
									else if(currentStick.strength == 2){
										player.hammerRight();
										hammerm.play();
									}
									else{
										player.bridgemoveRight();
									}
									
								}
								else{
									if(currentStick.strength == 1){
										player.eatLeft();
										eatm.play();
										
									}
									else if(currentStick.strength == 2){
										player.hammerLeft();
										hammerm.play();
									}
									else{
										player.bridgemoveleft();
									}
									
								}
//								if (currentStick.strength == 1) {
//
//									if(currentStick.p1.equals(player.currentPos)){
//
//										currentStick.registerEntityModifier(new ScaleAtModifier(tt, 1, 0, currentStick.p2.centerX- currentStick.p1.centerX, currentStick.p2.centerY- currentStick.p1.centerY));
//
//									}
//									else{
//										currentStick.registerEntityModifier(new ScaleAtModifier(tt, 1, 0, 0, 0));
//
//									}
//								}
//								
							}

							@Override
							public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
								// TODO Auto-generated method stub
								
								
							}
						});
						
						
						
						final RotationModifier rotmod2 = new RotationModifier(0.2f,
								player.getRotation(), 0);
						rotmod2.addModifierListener(new IModifierListener<IEntity>() {

							@Override
							public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
								p.visitable--;
//								RotationModifier rotmod2 = new RotationModifier(0.3f,
//										player.getRotation(), 0);
								if(player.currentPos.x < p.x){
									player.turnRightout();
								}
								else{
									player.turnLeftOut();
								}

							}

							@Override
							public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
								player.randomAnimation();
								player.currentPos = p;
								onaPoint = true;
								
								if(currentStick.strength == 2){
									currentStick.strength=1 ;
								}
								else if(currentStick.strength == 1){
									currentStick.strength=0 ;
								}
								
								if(eatm.isPlaying()){
									eatm.pause();
								}
								if(hammerm.isPlaying()){
									hammerm.pause();
								}
								
								
								
								
								currentStick = null;
//								stateList.add(new State());
								
							}
						});
						if(onaPoint){
							
							try {
								player.clearEntityModifiers();
								player.registerEntityModifier(new SequenceEntityModifier(Rotmod,mm,rotmod2));
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			
				
//			}
//		});
		
	}
	}

	
	
////////////////////////////////////////////////////   PLAYER   /////////////////////////////////////////////////////
	public class Player extends AnimatedSprite {
		public float x, y, centerX, centerY;
		public Point currentPos;
		
		public Rectangle entity = new Rectangle(this.getWidth()/2, this.getHeight()/2,20,20,getVertexBufferObjectManager());
		
		@Override
		public void setAlpha(float pAlpha) {
			super.setAlpha(pAlpha);
			super.setColor(pAlpha,pAlpha,pAlpha);
		}
		
		public Player(float pX, float pY, float pWidth, float pHeight,
				ITiledTextureRegion pTiledTextureRegion,
				ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject) {
			super(pX, pY, pWidth, pHeight, pTiledTextureRegion,
					pTiledSpriteVertexBufferObject);
			// TODO Auto-generated constructor stub
		}
		
		public SpriteParticleSystem eatParticlesLeft;
		public SpriteParticleSystem eatParticlesRight;
		
		public void umbrella(){
			animate(new long[]{60,60,60,60,100,1000,  100,60,60,60,60,60}, new int[]{0,1,2,3,4,5 ,5,4,3,2,1,0}, false);
		}
		
		public void hammerRight(){
			animate(new long[]{45,45,45,   45,45,45}, new int[]{7,8,9,9,8,7}, true);
		}
		public void hammerLeft(){
			animate(new long[]{45,45,45,   45,45,45}, new int[]{18,17,16,16,17,18}, true);
		}
		
		public void flameLeft(){
			animate(new long[]{0,0,2000,0,0}, new int[]{14,13,12,11,10}, true);
		}
		public void flameRight(){
			animate(new long[]{0,0,2000,0,0}, new int[]{71,72,73,74,75}, true);
		}
		
		//this triggers random animation when finished
		public void wonder(){
			animate(new long[]{5000,50,50,50,50,50,50,3000  ,50,50,50,2000   ,50,50,50,50,5000 }, new int[]{40,28,29,38,39,48,49,67   ,67,49,48,39,  39,38,29,28,40  }, false ,new IAnimationListener() {
				
				@Override
				public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
						int pInitialLoopCount) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
						int pRemainingLoopCount, int pInitialLoopCount) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
						int pOldFrameIndex, int pNewFrameIndex) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
					// TODO Auto-generated method stub
					randomAnimation();
				}
			});
		}
		
		
		
		public void randomAnimation(){
			
			int rand ;
			
			if(levelid < 5){
				rand = r.nextInt(3);
			}
			
			else if(levelid < 10){
				rand = r.nextInt(4);
			}
			
			else if(levelid < 15){
				rand = r.nextInt(5);
			}
			
			else if(levelid < 20){
				rand = r.nextInt(6);
			}
			else{
				rand = r.nextInt(7);
			}

			if(rand==5){
				stareyes();
			}
			
			if(rand == 4){
				wonder();
			}
			
			//jump
			if(rand == 3){
				animate(new long[]{30,30,30,30,30,30,30,30,40,40,40,40,40,40,40} ,new int[]{24,25,26,27,27,26,25,24,21,22,23,23,22,21,20}, 6 , new IAnimationListener() {
					
					@Override
					public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
							int pInitialLoopCount) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
							int pRemainingLoopCount, int pInitialLoopCount) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
							int pOldFrameIndex, int pNewFrameIndex) {
						// TODO Auto-generated method stub
						if(pOldFrameIndex == 2){
//							if(!jumpm.isPlaying()){
								jumpm.play();
								
//							}
							
						}
						if(pOldFrameIndex == 14){
							jumpm.seekTo(40);
						}
					}
					
					@Override
					public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
						// TODO Auto-generated method stub
						randomAnimation();
					}
				});
			}
			
			//angry + vibrate
			if(rand == 6){
				
				int rand2 = r.nextInt(2);
				
				if (rand2 == 0) {
					float initx = this.getX();
					float inity = this.getY();
					final SequenceEntityModifier shiver = (new SequenceEntityModifier(
							new MoveModifier(0.050f, this.getX(), initx + 9,
									this.getY(), inity + 9), new MoveModifier(
									0.050f, this.getX(), initx - 3,
									this.getY(), inity - 5), new MoveModifier(
									0.050f, this.getX(), initx, this.getY(),
									inity + 5), new MoveModifier(0.050f,
									this.getX(), initx + 9, this.getY(),
									inity + 5), new MoveModifier(0.050f,
									this.getX(), initx - 5, this.getY(),
									inity + 9), new MoveModifier(0.050f,
									this.getX(), initx + 9, this.getY(),
									inity - 3), new MoveModifier(0.050f,
									this.getX(), initx - 5, this.getY(),
									inity + 5), new MoveModifier(0.050f,
									this.getX(), initx + 9, this.getY(),
									inity - 9), new MoveModifier(0.050f,
									this.getX(), initx, this.getY(), inity)));
					animate(new long[] { 50, 50, 50, 50, 600, 1200, 50, 50, 50,
							50, 50 }, new int[] { 90, 91, 92, 93, 93, 93, 93,
							93, 92, 91, 90 }, false, new IAnimationListener() {

						@Override
						public void onAnimationStarted(
								AnimatedSprite pAnimatedSprite,
								int pInitialLoopCount) {
							// TODO Auto-generated method stub
							angrym.play();

						}

						@Override
						public void onAnimationLoopFinished(
								AnimatedSprite pAnimatedSprite,
								int pRemainingLoopCount, int pInitialLoopCount) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationFrameChanged(
								AnimatedSprite pAnimatedSprite,
								int pOldFrameIndex, int pNewFrameIndex) {
							// TODO Auto-generated method stub
							if (pOldFrameIndex == 4) {
								if (onaPoint) {
									Level.Player.this
											.registerEntityModifier(shiver);
								}
							}

						}

						@Override
						public void onAnimationFinished(
								AnimatedSprite pAnimatedSprite) {
							// TODO Auto-generated method stub
							randomAnimation();
						}
					});
				}
				else{
					randomAnimation();
				}
			}
			
			
			// double blink
			if(rand == 2 ){
				animate(new long[]{3000,50,50,50, 50,50,50,50,   50,50,50,50, 50,50,50,3000}, new int[]{80,81,82,83, 83,82,81,80,    80,81,82,83, 83,82,81,80}, 2 , new IAnimationListener() {
					
					@Override
					public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
							int pInitialLoopCount) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
							int pRemainingLoopCount, int pInitialLoopCount) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
							int pOldFrameIndex, int pNewFrameIndex) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
						// TODO Auto-generated method stub
						randomAnimation();
					}
				});
			}
			// shake
			if(rand == 1){
				animate(new long[]{90,90,90,90}, new int[]{57,58,57,59}, 15 , new IAnimationListener() {
					
					@Override
					public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
							int pInitialLoopCount) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
							int pRemainingLoopCount, int pInitialLoopCount) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
							int pOldFrameIndex, int pNewFrameIndex) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
						// TODO Auto-generated method stub
						randomAnimation();
					}
				});
			}
			//blink1
			if(rand == 0){
				animate(new long[]{5000,30,30,30, 30,30,30,3500}, new int[]{80,81,82,83, 83,82,81,80}, 2 , new IAnimationListener() {
					
					@Override
					public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
							int pInitialLoopCount) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
							int pRemainingLoopCount, int pInitialLoopCount) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
							int pOldFrameIndex, int pNewFrameIndex) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
						// TODO Auto-generated method stub
						randomAnimation();
					}
				});
			}
			
			
			


			
		}
		
		public void flameLeft(float time){
			animate(new long[]{50,40,40,40,40 , 50,40,40,40,450}, new int[]{12,11,10,11,12,  12,11,10,11,12}, true);
			AlphaModifier sm = new AlphaModifier(time,1,1);
			sm.addModifierListener(new IModifierListener<IEntity>() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					// TODO Auto-generated method stub
					animate(new long[]{50,50}, new int[]{13,14}, false);
					player.eatParticlesLeft.setParticlesSpawnEnabled(false);
				}
			});
			
			player.registerEntityModifier(sm);
		}
		public void bridgemoveRight(){
			animate(new long[]{50,50,150,50,30      ,30,50,150,50,600}, new int[]{96,97,98,97,96    ,96,97,98,97,96}, true);
		}
		public void bridgemoveleft(){
			animate(new long[]{50,50,150,50,30,30,50,150,50,600}, new int[]{99,89,88,89,99,99,89,88,89,99}, true);
		}
		
		public void flameRight(float time){
			animate(new long[]{50,40,40,40,600}, new int[]{73,74,75,74,73}, true);
			AlphaModifier sm = new AlphaModifier(time,1,1);
			sm.addModifierListener(new IModifierListener<IEntity>() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					// TODO Auto-generated method stub
					animate(new long[]{50,50}, new int[]{72,71}, false);
					player.eatParticlesRight.setParticlesSpawnEnabled(false);
				}
			});
			
			player.registerEntityModifier(sm);
		}
		
		
		public void jump(){
			animate(new long[]{30,30,30,30,30,30,30,30,40,40,40,40,40,40,40} ,new int[]{24,25,26,27,27,26,25,24,21,22,23,23,22,21,20}, true, new IAnimationListener() {
				
				@Override
				public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
						int pInitialLoopCount) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
						int pRemainingLoopCount, int pInitialLoopCount) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
						int pOldFrameIndex, int pNewFrameIndex) {
					// TODO Auto-generated method stub
					if(pOldFrameIndex == 3){
						jumpm.play();
					}
				}
				
				@Override
				public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		public void greenIn(){
			animate(new long[]{50,50,50,50,50} ,new int[]{40,41,42,43,44}  , false);
		}
		public void greenBlink(){
			animate(new long[]{80,80,80,80,80,80,80,80} ,new int[]{44,45,46,47,47,46,45,44}  , false);
		}
		public void greenOut(){
			animate(new long[]{50,50,50,50,50} ,new int[] {44,43,42,41,40}, false);
		}
		
		public void blackIn(){
			animate(new long[]{50,50,50,50,50} ,new int[]{30,31,32,33,34}  , false);
		}
		public void blackBlink(){
			animate(new long[]{40,40,40,20,20,40,40,40} ,new int[]{34,35,36,37,37,36,35,34}  , false);
		}
		public void blackOut(){
			animate(new long[]{50,50,50,50,50} ,new int[] {34,33,32,31,30}, false);
		}
		
		
		////// this triggers random animation when this is finished
		public void stareyes(){
			animate(new long[]{2000,50,50} ,new int[] {80,68,69}, false , new IAnimationListener() {
				
				@Override
				public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
						int pInitialLoopCount) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
						int pRemainingLoopCount, int pInitialLoopCount) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
						int pOldFrameIndex, int pNewFrameIndex) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
					// TODO Auto-generated method stub
					animate(new long[]{50,50,50} ,new int[] {76,77,78}, 20 ,new IAnimationListener() {
						
						@Override
						public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
								int pInitialLoopCount) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
								int pRemainingLoopCount, int pInitialLoopCount) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
								int pOldFrameIndex, int pNewFrameIndex) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
							// TODO Auto-generated method stub
							animate(new long[]{50,50,4000} ,new int[] {69,68,80}, false, new IAnimationListener() {
								
								@Override
								public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
										int pInitialLoopCount) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
										int pRemainingLoopCount, int pInitialLoopCount) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
										int pOldFrameIndex, int pNewFrameIndex) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
									// TODO Auto-generated method stub
									randomAnimation();
								}
							});
						}
					} );
				}
			});
		}
		
		public void blackGrenade(){
			animate(new long[]{70,70,30,30,700} ,new int[]{30,31,32,33,34}  , false, new IAnimationListener() {
				
				@Override
				public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
						int pInitialLoopCount) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
						int pRemainingLoopCount, int pInitialLoopCount) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
						int pOldFrameIndex, int pNewFrameIndex) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
					// TODO Auto-generated method stub
					animate(new long[]{50,50,50,50,50,50,50,50,  50,50,50,50,50,50,50,800} ,new int[]{34,35,36,37,37,36,35,34,  34,35,36,37,37,36,35,34}  ,false , new IAnimationListener() {
						
						@Override
						public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
								int pInitialLoopCount) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
								int pRemainingLoopCount, int pInitialLoopCount) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
								int pOldFrameIndex, int pNewFrameIndex) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
							// TODO Auto-generated method stub
							animate(new long[]{70,30,30,70,150} ,new int[] {34,33,32,31,30}, false);
						}
					});
				}
			});
		}
		
		
		public void turnRightIn(){
			animate(new long[]{50,50,50,50}, new int[]{50,51,52,53}, false);
		}
		public void turnRightout(){
			animate(new long[]{50,50,50,50}, new int[]{53,52,51,50}, false);
		}
		
		public void eatRight(){
			animate(new long[]{40,40,40,40,40,40,40}, new int[]{53,54,55,56,55,54,53}, true, new IAnimationListener() {

				@Override
				public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
						int pInitialLoopCount) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
						int pRemainingLoopCount, int pInitialLoopCount) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
						int pOldFrameIndex, int pNewFrameIndex) {
					// TODO Auto-generated method stub
					if(pOldFrameIndex == 0){
//						eatm.setVolume(0.5f);
//						eatm.play();
					}
					
				}
				
				@Override
				public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
					// TODO Auto-generated method stub
					
				}
			});
			};
		
	
		public void turnLeftIn(){
			animate(new long[]{50,50,50,50}, new int[]{66,65,64,63}, false);
		}
		public void turnLeftOut(){
			animate(new long[]{50,50,50,50}, new int[]{63,64,65,66}, false);
		}
		
		public void eatLeft(){
			animate(new long[]{40,40,40,40,40,40,40}, new int[]{63,62,61,60,61,62,63}, true , new IAnimationListener() {
				
				@Override
				public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
						int pInitialLoopCount) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
						int pRemainingLoopCount, int pInitialLoopCount) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
						int pOldFrameIndex, int pNewFrameIndex) {
					// TODO Auto-generated method stub
					if(pOldFrameIndex == 0){
//						eatm.setVolume(0.5f);
//						eatm.play();
					}
				}
				
				@Override
				public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
		public void shake(){
			animate(new long[]{90,90,90,90}, new int[]{57,58,57,59}, true);
		}
		
		public void surprise(){
			animate(new long[]{100,100,300,300,  300,300,100,100}, new int[]{76,77,78,79  ,79,78,77,76}, false);
		}
		
		public void blink(){
			animate(new long[]{50,30,30,30, 30,30,30,1500}, new int[]{80,81,82,83, 83,82,81,80}, true, new IAnimationListener() {
				
				@Override
				public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
						int pInitialLoopCount) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
						int pRemainingLoopCount, int pInitialLoopCount) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
						int pOldFrameIndex, int pNewFrameIndex) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		public void blinkslow(){
			animate(new long[]{1150,1130,1130,1130, 1130,1130,1130,1500}, new int[]{80,81,82,83, 83,82,81,80}, true);
		}
		
		public void doubleBlink(){
			animate(new long[]{50,50,50,50, 50,50,50,50,   50,50,50,50, 50,50,50,1500}, new int[]{80,81,82,83, 83,82,81,80,    80,81,82,83, 83,82,81,80}, true);
		}
		
		public void makethunder(){
			float initx = player.getX();
			float inity = player.getY();
			final SequenceEntityModifier shiver = (new SequenceEntityModifier(
					new MoveModifier(0.050f, player.getX(), initx+9, player.getY(),inity + 9),
					new MoveModifier(0.050f, player.getX(), initx-3, player.getY(),inity - 5),
					new MoveModifier(0.050f, player.getX(), initx, player.getY(),inity + 5),
					new MoveModifier(0.050f, player.getX(), initx+9, player.getY(),inity + 5),
					new MoveModifier(0.050f, player.getX(), initx-5, player.getY(),inity + 9),
					new MoveModifier(0.050f, player.getX(), initx+9, player.getY(),inity - 3),
					new MoveModifier(0.050f, player.getX(), initx-5, player.getY(),inity + 5),
					new MoveModifier(0.050f, player.getX(), initx+9, player.getY(),inity - 9),
					new MoveModifier(0.050f, player.getX(), initx, player.getY(),inity)
					));
			
			animate(new long[]{90,90,90,500,500, 500,90,90,90}, new int[]{84,85,86,87,87, 87,86,85,84}, false, new IAnimationListener() {
				
				@Override
				public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
						int pInitialLoopCount) {
					// TODO Auto-generated method stub
					makethunderm.play();
				}
				
				@Override
				public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
						int pRemainingLoopCount, int pInitialLoopCount) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
						int pOldFrameIndex, int pNewFrameIndex) {
					if(pNewFrameIndex==3){
						player.registerEntityModifier(shiver);
					}
					if(pNewFrameIndex==4){
						player.registerEntityModifier(shiver);
					}
					if(pNewFrameIndex==5){
						player.registerEntityModifier(shiver);
					}
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		public void placeBomb(){
			float initx = player.getX();
			float inity = player.getY();
			final SequenceEntityModifier shiver = (new SequenceEntityModifier(
					new MoveModifier(0.050f, player.getX(), initx+9, player.getY(),inity + 9),
					new MoveModifier(0.050f, player.getX(), initx-3, player.getY(),inity - 5),
					new MoveModifier(0.050f, player.getX(), initx, player.getY(),inity + 5),
					new MoveModifier(0.050f, player.getX(), initx+9, player.getY(),inity + 5),
					new MoveModifier(0.050f, player.getX(), initx-5, player.getY(),inity + 9),
					new MoveModifier(0.050f, player.getX(), initx+9, player.getY(),inity - 3),
					new MoveModifier(0.050f, player.getX(), initx-5, player.getY(),inity + 5),
					new MoveModifier(0.050f, player.getX(), initx+9, player.getY(),inity - 9),
					new MoveModifier(0.050f, player.getX(), initx, player.getY(),inity)
					));
			animate(new long[]{50,50,50,50,50,400,  50,50,50,50,50}, new int[]{90,91,92,93, 94,95,  95,93,92,91,90}, false, new IAnimationListener() {
				
				@Override
				public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
						int pInitialLoopCount) {
					// TODO Auto-generated method stub
					placingbomb = true;
					angrym.play();
				}
				
				@Override
				public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
						int pRemainingLoopCount, int pInitialLoopCount) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
						int pOldFrameIndex, int pNewFrameIndex) {
					// TODO Auto-generated method stub
					if(pOldFrameIndex==4 ){
						if(onaPoint){
							player.registerEntityModifier(shiver);
						}
					}
					
				}
				
				@Override
				public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
					// TODO Auto-generated method stub
					placingbomb = false;
					
				}
			});
		}
		
		public Player(Point p) {
			
			super(p.centerX - playerTextureRegion.getWidth() / 2 +1, p.centerY
					- playerTextureRegion.getHeight() / 2 - 29 , playerTextureRegion
					.getWidth(), playerTextureRegion.getHeight(),
					playerTextureRegion, mEngine.getVertexBufferObjectManager());
			this.setScale(1.95f);
			this.x = p.centerX - playerTextureRegion.getWidth() / 2;
			this.y = p.centerY - playerTextureRegion.getHeight() / 2;
			this.centerX = p.centerX;
			this.centerY = p.centerY;
			eatParticlesLeft = playerParticlesLeft();
			this.attachChild(eatParticlesLeft);
			eatParticlesLeft.setParticlesSpawnEnabled(false);
			eatParticlesRight = playerParticlesRight();
			this.attachChild(eatParticlesRight);
			eatParticlesRight.setParticlesSpawnEnabled(false);
			this.attachChild(this.entity);
			this.entity.setVisible(false);
			this.setCullingEnabled(true);
//			if(xStretched){
//				this.setScaleX(getScaleX()*xScaleFactor);
//			}
//			if(yStretched){
//				this.setScaleY(getScaleY()*yScaleFactor);
//			}
			randomAnimation();

		}
		
		
		
		@Override
		public void setRotation(float pRotation) {
			// TODO Auto-generated method stub
			super.setRotation(pRotation);
		}

		public void gotoPoint(final Point p) {
			
			if(onaPoint){
		
			
			
		
			distance = (float) Math.sqrt((p.centerX - currentPos.centerX)
					* (p.centerX - currentPos.centerX)
					+ (p.centerY - currentPos.centerY)
					* (p.centerY - currentPos.centerY));

//			currentStick = null;
			
//			for(Point po : PointList){
//				mEngine.getScene().unregisterTouchArea(po);
//			}
			
//			runOnUiThread(new Runnable() {
//				@Override
//				public void run() {
//					try {
//						Thread.sleep((long) (tt*1000 + 400));
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			});
			
			
			
			
			

			
			
			tt = distance / velocity  ;
			
			for (Stick s : currentPos.connectedSticks) {
				if (s.p1.equals(p) && s.p2.equals(player.currentPos)) {
					currentStick = s;
				}
				if (s.p1.equals(player.currentPos) && s.p2.equals(p)) {
					currentStick = s;
				}
			}
			
			for(Stick s : effectsStickList){
				if(currentStick.ID == s.ID){
					effectStick = s;
				}
			}
			
			
			//if charachter moving /not power On
			if(powerOn == false){
				if (p.visitable != 0 && currentStick.strength != 3 && currentStick.strength != 0) {
					///////
					float rotat ;
					
					if(player.currentPos.x < p.x) {
						rotat = -25 +(float) Math.toDegrees(Math.atan2(p.centerY - player.currentPos.centerY, p.centerX- player.currentPos.centerX));
					}
					else{
						rotat = 25 + (float) Math.toDegrees(Math.atan2(player.currentPos.centerY - p.centerY, player.currentPos.centerX- p.centerX));
					}
					
					final RotationModifier Rotmod = new RotationModifier(0.2f,
							player.getRotation(),rotat );
					Rotmod.addModifierListener(new IModifierListener<IEntity>() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
							try {
								
								
								onaPoint = false;
								
								animate(new long[] { 50, 50, 50, 50 }, new int[] { 0,
										1, 2, 3 }, false);
							} catch (Exception e) {
								
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							
						}
					});
					
					
					final MoveModifier mm =new MoveModifier(tt, currentPos.centerX-player.getWidth() / 2, p.centerX- player.getWidth() / 2, currentPos.centerY- player.getHeight() / 2, p.centerY- player.getHeight() / 2);
					mm.addModifierListener(new IModifierListener<IEntity>() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							
							try {
								
								
								animate(new long[] { 30, 30, 30, 30, 30, 30,30 },
										new int[] {3, 4, 5, 6, 5, 4,3 }, true);
								if (currentStick.strength == 1) {

									if(currentStick.p1.equals(currentPos)){
										
										currentStick.registerEntityModifier(new ScaleAtModifier(tt, 1, 0, currentStick.p2.centerX- currentStick.p1.centerX, currentStick.p2.centerY- currentStick.p1.centerY));
										effectStick.registerEntityModifier(new ScaleAtModifier(tt, 1, 0, 0, 0));
									}
									else{
										currentStick.registerEntityModifier(new ScaleAtModifier(tt, 1, 0, 0, 0));
										effectStick.registerEntityModifier(new ScaleAtModifier(tt, 1, 0, currentStick.p2.centerX- currentStick.p1.centerX, currentStick.p2.centerY- currentStick.p1.centerY));
									}
									// remove current
//							StickList.remove(currentStick);
									// here the Sticks are removed only from the StickList not from connected sticks..
									
								}
							} catch (Exception e) {
								
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							
						}
					});
					
					final RotationModifier rotmod2 = new RotationModifier(0.2f,
							player.getRotation(), 0);
					rotmod2.addModifierListener(new IModifierListener<IEntity>() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
							
							
								
								
								p.visitable--;
								RotationModifier rotmod2 = new RotationModifier(0.3f,
										player.getRotation(), 0);
								animate(new long[] { 50, 50, 50, 50 }, new int[] {
										3, 2, 1, 0 }, false);
							
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
							try {
								
								animate(new long[] { 60, 60, 60, 60 }, new int[] {8, 9, 10, 11 }, true);
								player.currentPos = p;
								onaPoint = true;
								currentStick.strength--;
								
//								scene.registerTouchArea(p);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} );
					
						
						registerEntityModifier(new SequenceEntityModifier(Rotmod,mm,rotmod2));

				}
			}
			
					

			
			// If power on 
			
			
			else{
				
				//removed && p.flamed == false to enable returning to a Flamedpoint
				
				if(powers.get(3).functioning  && p.visitable != 0 && currentStick.strength != 3 && currentStick.strength != 0   && p.flamed == false && onaPoint){
					
					
					float rotat ;
					
					if(player.currentPos.x < p.x) {
						rotat = -25 +(float) Math.toDegrees(Math.atan2(p.centerY - player.currentPos.centerY, p.centerX- player.currentPos.centerX));
					}
					else{
						rotat = 25 + (float) Math.toDegrees(Math.atan2(player.currentPos.centerY - p.centerY, player.currentPos.centerX- p.centerX));
					}
					final RotationModifier Rotmod = new RotationModifier(0.2f,
							player.getRotation(),rotat );
					
					Rotmod.addModifierListener(new IModifierListener<IEntity>() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
							
								onaPoint = false;
								
								if(player.currentPos.x < p.x){
									player.turnRightIn();
								}
								else{
									player.turnLeftIn();
								}
								
							
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							
						}
					});
					
					
//					final MoveModifier mm =new MoveModifier(tt, currentPos.centerX-player.getWidth() / 2 +7, p.centerX- player.getWidth() / 2 + 7, currentPos.centerY- player.getHeight() / 2 -30, p.centerY- player.getHeight() / 2 -30);
					final MoveModifier mm =new MoveModifier(tt, player.currentPos.centerX-player.getWidth() / 2  + 1 , p.centerX- player.getWidth() / 2 +1, player.currentPos.centerY- player.getHeight() / 2-29, p.centerY- player.getHeight() / 2 - 29 );
					mm.addModifierListener(new IModifierListener<IEntity>() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							
							
								if(player.currentPos.x < p.x){
									player.flameRight((float) (tt-0.5));
									player.eatParticlesRight.setParticlesSpawnEnabled(true);
								}
								else{
									player.flameLeft((float) (tt-0.5));
									player.eatParticlesLeft.setParticlesSpawnEnabled(true);
								}
//							
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							
						}
					});
					
					final RotationModifier rotmod2 = new RotationModifier(0.2f,
							player.getRotation(), 0);
					rotmod2.addModifierListener(new IModifierListener<IEntity>() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
							
							
								
								if(player.currentPos.x < p.x){
									player.turnRightout();
									player.eatParticlesRight.setParticlesSpawnEnabled(false);
								}
								else{
									player.turnLeftOut();
									player.eatParticlesLeft.setParticlesSpawnEnabled(false);
								}
								
								
							
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
							
								
								
							
								player.randomAnimation();
								
								
								player.currentPos = p;
								//changed here
								
								if(p.flameStart){
									flame();
								}
								else{
									flamePoints.add(p);
//									flameSticks.add(currentStick);
									
									if(currentStick != null){
										boolean b = true;
										for  (Stick s : flameSticks) {
											if(s.ID == currentStick.ID){
												b = false;
											}
										}
										if(b){
											flameSticks.add(currentStick);
										}
										
									}
								}
								onaPoint = true;
								p.flamed = true;
								p.visitable--;
								
							
						}
					} );
					
						
					registerEntityModifier(new SequenceEntityModifier(Rotmod,mm,rotmod2));

				}
			}
			
		}
		}
	}
			
//				
				
				
					
		
				
		
	
					
			

	
/////////////////////////////////////////////  Point  ///////////////////////////////////////////////////////////////////////////
	public class Point extends AnimatedSprite {

		public float x, y, centerX, centerY;
		public int visitable = 0;
		public int ID;
		public decorPoint decorpoint;
		public Sprite ray1;
		public Sprite ray2;
		public Sprite ray3;
		
		public collect c;
		
		public boolean startable = true;
		public boolean visited = false;
		public boolean mustStart = false;
		
		public boolean charged = false;

		public boolean hasStar = false;
		public boolean hasCharges = false;
		public boolean hasFlame = false;
		public boolean hasGrenade = false;
		public boolean hasThunder = false;

		public boolean selected = false;
		public boolean init = false;
		public boolean SLinit = false;
		
		public boolean flameStart = false;
		public boolean flamed = false;
		public ArrayList<Point> connectedPoints = new ArrayList<Point>();
		public ArrayList<Stick> connectedSticks = new ArrayList<Stick>();
		
		public boolean collectable =false;
		
		@Override
		public void setAlpha(float pAlpha) {
		
		super.setAlpha(pAlpha);
		super.setColor(pAlpha,pAlpha,pAlpha);
		}
		
		@Override
		protected void onManagedUpdate(float pSecondsElapsed) {
//			if(onaPoint== false && playerPlaced==true){
//				scene.unregisterTouchArea(this);
//			}
//			
//			if(onaPoint== false && playerPlaced==false){
//				scene.registerTouchArea(this);
//			}
//			if(onaPoint== true && playerPlaced==true){
//				scene.registerTouchArea(this);
//			}
//			if(tempconnector.collidesWith(this)){
//				this.setScale(2.0f);
//				this.setColor(Color.YELLOW);
//			}
//			else{
//				this.setScale(1.0f);
//				this.setColor(Color.BLUE);
//			}
			
			
			//time bomb explodes here
			if(charged){
				final Sprite charge = new Sprite(this.centerX- chargeTR.getWidth()/2, this.centerY - chargeTR.getHeight()/2, chargeTR, getVertexBufferObjectManager()){
					@Override
					public void setAlpha(float pAlpha) {
						// TODO Auto-generated method stub
						super.setAlpha(pAlpha);
						super.setColor(pAlpha,pAlpha,pAlpha);
					}
				};
				float initx = charge.getX();
				float inity = charge .getY();
				scene.attachChild(charge);
				charge.setAlpha(0);
				runOnUpdateThread(new Runnable() {
					
					@Override
					public void run() {
						scene.detachChild(player);
						scene.attachChild(player);
						
					}
				});
				
				countm.play();
				
				charge.setScale(0.65f);
				final SequenceEntityModifier shiver = (new SequenceEntityModifier(
						new MoveModifier(0.050f, charge.getX(), initx+9, charge.getY(),inity + 9),
						new MoveModifier(0.050f, charge.getX(), initx-3, charge.getY(),inity - 5),
						new MoveModifier(0.050f, charge.getX(), initx, charge.getY(),inity + 5),
						new MoveModifier(0.050f, charge.getX(), initx+9, charge.getY(),inity + 5),
						new MoveModifier(0.050f, charge.getX(), initx-5, charge.getY(),inity + 9),
						new MoveModifier(0.050f, charge.getX(), initx+9, charge.getY(),inity - 3),
						new MoveModifier(0.050f, charge.getX(), initx-5, charge.getY(),inity + 5),
						new MoveModifier(0.050f, charge.getX(), initx+9, charge.getY(),inity - 9),
						new MoveModifier(0.050f, charge.getX(), initx, charge.getY(),inity),
						new MoveModifier(0.050f, charge.getX(), initx+9, charge.getY(),inity + 9)
						));
				
				final LoopEntityModifier shiv = new LoopEntityModifier(shiver, 10);
				final SequenceEntityModifier seq = new SequenceEntityModifier(new AlphaModifier(1f, 0, 1), shiv);
				seq.addModifierListener(new IModifierListener<IEntity>() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
						// TODO Auto-generated method stub
							
					}
				});
				charge.registerEntityModifier(seq);
				
				handler2.postDelayed(new Runnable() {
					
					

					@Override
					public void run() {
						runOnUpdateThread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								charge.clearEntityModifiers();
								scene.detachChild(charge);
							}
						});
						blast(2, Level.Point.this);
						
						
						if (won ==false) {
							if (player.currentPos.ID == ID && onaPoint) {
								gamefinished = true;
								lost = true;
										
								player.blackIn();
								handler.postDelayed(new Runnable() {
									
									@Override
									public void run() {
										
										fademainscene();
										
									}
								}, 3000);
								handler.postDelayed(new Runnable() {

									@Override
									public void run() {
										player.setVisible(false);
										scene.setChildScene(loseScene, false, true, true);
										bgmusic.pause();
										losem.play();
										
									}
								}, 3500);
							}

							else if (!onaPoint) {
								boolean b = false;
								for (Stick s : Level.Point.this.connectedSticks) {
									if (s.ID == currentStick.ID) {
										b = true;

									}
								}
								if (b) {
									gamefinished = true;
									lost = true;
//									scene.setChildScene(losescene);
									player.clearEntityModifiers();
									player.setRotation(0);
									player.blackBlink();
									
									handler.postDelayed(new Runnable() {
										
										@Override
										public void run() {
											
											fademainscene();
											
										}
									}, 3000);
									handler.postDelayed(new Runnable() {

										@Override
										public void run() {
											player.setVisible(false);
											scene.setChildScene(loseScene, false, true, true);
											bgmusic.pause();
											losem.play();
										}
									}, 3500);
								}

							} else {
								player.umbrella();
							}
						}
						AlphaModifier am = new AlphaModifier(4, 1, 1);
						am.addModifierListener(new IModifierListener<IEntity>() {
							
							@Override
							public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
								// TODO Auto-generated method stub
								player.randomAnimation();
							}
						});
						player.registerEntityModifier(am);
							
						
						
					}
					
				}, 5000);
				charged = false;
			}
		super.onManagedUpdate(pSecondsElapsed);
		}
		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
				float pTouchAreaLocalX, float pTouchAreaLocalY) {
			
			
//			this.setColor(Color.GREEN);
			
			try{
			
//			tempconnector.setPosition(pSceneTouchEvent.getX()-this.getWidth()/2, pSceneTouchEvent.getY()-this.getHeight()/2);
//			tempconnector.setVisible(true);
//			tempconnector.setScale(0.4f);
//			tempconnector.setColor(new Color(0.7f,0.2f,0.1f));
			
			if (pSceneTouchEvent.isActionUp()) {
				this.setColor(Color.BLUE);
			
				

				if (playerPlaced == false) {
					
					if (this.startable) {
						player = new Player(this);
						
						mEngine.getScene().attachChild(player);
						
						player.currentPos = this;
						playerPlaced = true;
						onaPoint = true;
						player.randomAnimation();
					} else {
						// if player clicked on a non startable position
					}
				}

				else {
					
					// if player is initialized
					
					
					
					if(powerOn && onaPoint){
						currentStick = null;
						for (Stick s : player.currentPos.connectedSticks) {
							if (s.p1.equals(this) && s.p2.equals(player.currentPos)) {
								currentStick = s;
								
							}
							if (s.p1.equals(player.currentPos) && s.p2.equals(this)) {
								currentStick = s;
								
							}
						}
						//if a power is selected
						if(onaPoint && powers.get(2).functioning && bombBlasting == false){
							boolean nearby = false;
							for(Point p : this.connectedPoints){
								if(player.currentPos.equals(p)){
									nearby = true;
								}
							}//if player has selected a connected point
							if(nearby && currentStick.strength != 0){
								blast(1,this); // grenade
								
								
								AlphaModifier am = new AlphaModifier(3, 1, 1);
								am.addModifierListener(new IModifierListener<IEntity>() {
									
									@Override
									public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
										// TODO Auto-generated method stub
										
									}
									
									@Override
									public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
										// TODO Auto-generated method stub
										player.randomAnimation();
									}
								});
								player.registerEntityModifier(am);
							}
						}
						if (onaPoint && powers.get(1).functioning && player.currentPos.equals(this)){
							//TODO animation to place charges
//							final Point tmp = this;
//							
//							charged = true;
//							powers.get(1).functioning = false;
//							powers.get(1).availability --;
//							powerOn = false;
							
							
//							handler.post(new Runnable() {
//								@Override
//								public void run() {
//									int i = 500 ;
//									running = true;
//									timerBomb.setVisible(true);
//									float f = mEngine.getSecondsElapsedTotal();
//									String s = String.valueOf(f);
//									float g = mEngine.getSecondsElapsedTotal();
//									while(g-f < 5){
//										g = mEngine.getSecondsElapsedTotal();
//										s = String.valueOf(f);
////										timerBomb.setText(s);
//									}
//									
//									if(player.currentPos.equals(this)){
//										//game over scene
//									}
//									else{
//										blast(2, tmp);
//									}
//								}
//							});

						}
						// trigger flaming
						if (powers.get(3).functioning && isnearby(player.currentPos) && onaPoint) {
							player.gotoPoint(this);
						}
						
						if(powers.get(4).functioning && onaPoint){
							if(this.ID != player.currentPos.ID){
								boolean balreadyconnected = false;
								for(Stick s : StickList){
									if(s.p1.ID == this.ID && s.p2.ID == player.currentPos.ID){
										if(s.strength != 0){
											balreadyconnected = true;
										}
									}
									if(s.p2.ID == this.ID && s.p1.ID == player.currentPos.ID){
										if(s.strength != 0){
											balreadyconnected = true;
										}
									}
								}
							
								
								if(balreadyconnected==false){
									Stick s1 = new Stick(player.currentPos,this);
									s1.ID = StickList.get(StickList.size()-1).ID + 100;
									s1.strength=-1;
									
									mEngine.getScene().attachChild(s1);
									runOnUpdateThread(new Runnable() {
										
										@Override
										public void run() {
//											decorPoint(player.currentPos);
//											decorPoint(Level.Point.this);
											
											scene.detachChild(player.currentPos.decorpoint);
											scene.attachChild(player.currentPos.decorpoint);
											if(player.currentPos.c != null){
												scene.detachChild(player.currentPos.c);
												scene.attachChild(player.currentPos.c);
											};
											
											
											scene.detachChild(Point.this.decorpoint);
											scene.attachChild(Level.Point.this.decorpoint);
											
											
											if(Point.this.c != null){
												scene.detachChild(Point.this.c);
												scene.attachChild(Level.Point.this.c);
											}
											
											
											scene.detachChild(player);
											scene.attachChild(player);
											
										}
									});
									
									StickList.add(s1);
									decorateBridge(s1);
									this.connectedSticks.add(s1);
									this.connectedPoints.add(player.currentPos);

									player.currentPos.connectedPoints.add(this);
									player.currentPos.connectedSticks.add(s1);
									
									powers.get(4).functioning = false;
									powers.get(4).availability --;
									powerOn = false;
								}
							}
						}
						
//						if(powers.get(4).functioning && onaPoint && player.currentPos.ID == this.ID){
//							
//							for (Point p : PointList){
//								if(p.ID != this.ID){
//									if(tempconnector.collidesWith(p)){
//										Log.d("ajitha","collides with"+p.ID);
//										
//										boolean balreadyconnected = false;
//										for(Stick s : StickList){
//											if(s.p1.ID == this.ID && s.p2.ID == p.ID){
//												if(s.strength != 0){
//													balreadyconnected = true;
//												}
//											}
//											if(s.p2.ID == this.ID && s.p1.ID == p.ID){
//												if(s.strength != 0){
//													balreadyconnected = true;
//												}
//											}
//										}
//										Log.d("ajitha","has stick");
//										
//										if(balreadyconnected==false){
//											Log.d("ajitha","no stick");
//											
//											
//											if(tempconnector.ID == this.ID || tempconnector.ID == p.ID){
//												Log.d("ans", "temp as a point");
//											}
//											else{
//											Stick s1 = new Stick(p,this);
//											s1.ID = StickList.get(StickList.size()-1).ID + 100;
//											
//
//											//mEngine.getScene().registerTouchArea(s1);
//											mEngine.getScene().attachChild(s1);
////											mEngine.getScene().detachChild(player);
////											mEngine.getScene().attachChild(player);
//											//s1.setColor(new Color(0.6588f, 0.5843f, 0.7882f));
//											s1.setColor(Color.CYAN);
//
//											StickList.add(s1);
//
//											Log.d("ajitha",StickList.size() + " "+ StickList.get(StickList.size()-1).ID);
//
//											this.connectedSticks.add(s1);
//											this.connectedPoints.add(p);
//
//											p.connectedPoints.add(this);
//											p.connectedSticks.add(s1);
//											
//											powers.get(4).functioning = false;
//											powers.get(4).availability --;
//											powerOn = false;
//											}
//										}
//										
//										
////										handler.post(drawLines);
//									}
//								}	
//							}
//							
//							tempconnector.setPosition(-100, -100);
//						}
					
					}
					else {
						Log.d("ans","elseeee");
						if (isnearby(player.currentPos)) {
							
							gotoPoint(this);
							
							
						}
					}
				}
			}
			return true;
		}catch(NullPointerException e){
			e.printStackTrace();
			return false;
		}
		}

		

		public boolean isnearby(Point p) {
			boolean b = false;
			for (Point cp : this.connectedPoints) {
				if (p.equals(cp)) {
					b = true;
				}
			}

			return b;
		}

		@Override
		public void setPosition(float pX, float pY) {
			// TODO Auto-generated method stub
			super.setPosition(pX, pY);
			this.x = pX;
			this.y = pY;
			this.centerX = pX + this.getWidth() / 2;
			this.centerY = pY + this.getHeight() / 2;

		}

		public Point(float pX, float pY, float pWidth, float pHeight,
				ITiledTextureRegion pTiledTextureRegion,
				VertexBufferObjectManager vertexBufferObjectManager) {
			super(pX, pY, pWidth, pHeight, pTiledTextureRegion,
					vertexBufferObjectManager);
			this.setAlpha(0);

		}

		public Point(float pX, float pY) {
			super(pX, pY, pointTextureRegion.getWidth(), pointTextureRegion
					.getHeight(), pointTextureRegion, mEngine.getVertexBufferObjectManager());
			
			this.x = pX;
			this.y = pY;
			this.centerX = this.x + pointTextureRegion.getWidth() / 2;
			this.centerY = this.y + pointTextureRegion.getHeight() / 2;
			this.setCullingEnabled(true);
			this.setAlpha(0);
//			

		}

	}

	public boolean mustStart() {
		boolean b = false;
		for (Point p : PointList) {
			if (p.mustStart == true) {
				b = true;
			}
		}
		return b;
	}

	public Point getmustStart() {
		for (Point p : PointList) {
			if (p.mustStart == true) {
				return p;
			}
		}
		return null;

	}
	public void flame(){
		
		float x1 = 2000.0f;
		float x2 = 0.0f;
		float y1 = 2000.0f;
		float y2 = 0.0f;
		for(Point p : flamePoints){
			if(p.x < x1){
				x1 = p.x;
			}
			if(p.x > x2){
				x2 = p.x;
			}
			if(p.y < y1){
				y1 = p.y;
			}
			if(p.y > y2){
				y2 = p.y;
			}
		}
		for(Point p : PointList){
			if(x1<p.x && p.x<x2 && y1 < p.y && p.y < y2 ) {
				flamePoints.add(p);
			}
		}
		flameSticks.clear();
		for (int i = 0 ; i < flamePoints.size()-1 ; i ++){
			for(int j = i+1 ; j < flamePoints.size() ; j++){
				Stick tmp = getStick(flamePoints.get(i), flamePoints.get(j));
				if(tmp != null){
					flameSticks.add(tmp);
				}
			}
		}
		
		burnm.play();
		for(Stick s : flameSticks){
			thunderBurnLow(s);
			s.strength = 0;
		}
		for(Point p : PointList){
			p.flamed = false;
			p.flameStart = false;
		}
		powerOn = false;
		powers.get(3).availability -- ;
		powers.get(3).functioning = false;
		
		for(Point p : flamePoints){
			p.flamed = false;
			p.flameStart = false;
		}
//		for(Stick s : flameSticks){
//			for(egg e : s.eggList){
//				e.flamed = false;
//			}
//		}
		flamePoints.clear();
		flameSticks.clear();
	}
	
	public float getCenterX(Stick s){
		return s.p1.centerX+(s.p2.centerX-s.p1.centerX)/2;
	}
	public float getCenterY(Stick s){
		return s.p1.centerY+(s.p2.centerY-s.p1.centerY)/2;
	}
	
	public void effectThunder(final Stick s){
		final AnimatedSprite ethunder = new AnimatedSprite(-1000,-1000 ,thundersTextureRegion, getVertexBufferObjectManager());
		final AnimatedSprite ecloud = new AnimatedSprite(-1000, -1000, cloudTextureRegion, getVertexBufferObjectManager());
		ethunder.setScale((float) 1.3);
		scene.attachChild(ecloud);
		scene.attachChild(ethunder);
		
		//music effect
		
		//animation
		final Thread thunderT = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ecloud.setAlpha(0.0f);
				ecloud.setScale(0.7f);
				ecloud.setPosition(getCenterX(s)-ecloud.getWidth()/2, getCenterY(s) - ecloud.getHeight()/2-150);
				AlphaModifier am = new AlphaModifier(1.7f, 0, 1);
				am.addModifierListener(new IModifierListener<IEntity>() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
						ethunder.setPosition(getCenterX(s)-ethunder.getWidth()/2, getCenterY(s) - ethunder.getHeight()/2-50);
						ethunder.animate(100);
						thunderm.play();
						thunderBurn(s);
//						s.registerEntityModifier(new AlphaModifier(5, 1, 0));
						
						
					}
				});
				final AlphaModifier am2 = new AlphaModifier(1.7f, 1, 0);
				am2.addModifierListener(new IModifierListener<IEntity>() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
						// TODO Auto-generated method stub
						ethunder.registerEntityModifier(am2);
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
						
						runOnUpdateThread(new Runnable() {
					        @Override
					        public void run() {
					        	scene.detachChild(ethunder);
								scene.detachChild(ecloud);
								
					        }
					    });

						
						
					}
				});
				
				ecloud.registerEntityModifier(new SequenceEntityModifier(am,am2));
				
				
				
				
				
			}
			
		});
		thunderT.start();
//		runOnUiThread(new Runnable() {
//			
//			@Override
//			public void run() {
//				stickParticles(s);
//				
//			}
//		});
		
		//srick burn
		
		//flash screen
	}
	
	public void stickParticles(Point p1 , Point p2){
		float length = (float) Math.sqrt((p1.centerX - p2.centerX)
				* (p1.centerX - p2.centerX)
				+ (p1.centerY - p2.centerY)
				* (p1.centerY - p2.centerY));
		
		float rotation = (float) Math.toDegrees(Math
				.atan2(p1.centerY - p2.centerY, p1.centerX
						- p2.centerX));
		
//		RectangleParticleEmitter emitter = new RectangleParticleEmitter(p1.centerX+(p2.centerX-p1.centerX)/2, p1.centerY+(p2.centerY-p1.centerY)/2 ,length, 1);
		RectangleParticleEmitter emitter = new RectangleParticleEmitter(length/2, 0 ,length, 1);
		SpriteParticleSystem sys = new SpriteParticleSystem(emitter, 10, 10, 50, mParticleTextureRegion, this.getVertexBufferObjectManager());
		
		
		
		
		sys.addParticleInitializer(new ColorParticleInitializer<Sprite>(1, 0, 0));
		sys.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
		sys.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		sys.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-2, 2, -5, -4));
//		sys.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 360.0f));
		sys.addParticleInitializer(new ExpireParticleInitializer<Sprite>(6));

		sys.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 5, 0.5f, 1.0f));
		sys.addParticleModifier(new ColorParticleModifier<Sprite>(0, 3, 1, 1, 0, 0.5f, 0, 0));
		sys.addParticleModifier(new ColorParticleModifier<Sprite>(4, 6, 1, 1, 0.5f, 1, 0, 1));
		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 1, 0, 1));
		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(5, 6, 1, 0));
		
		Line s1 = new Line(p1.centerX+(p2.centerX-p1.centerX)/2-length/2, p1.centerY+(p2.centerY-p1.centerY)/2, p1.centerX+(p2.centerX-p1.centerX)/2+length-length/2, p1.centerY+(p2.centerY-p1.centerY)/2, getVertexBufferObjectManager());
		
		s1.attachChild(sys);
//		s1.setVisible(false);
		
		s1.setRotation(rotation);
		scene.attachChild(s1);
		
		
		
	}
	public SpriteParticleSystem playerParticlesLeft(){
		RectangleParticleEmitter emitter = new RectangleParticleEmitter(0, 50 ,2, 2);
		SpriteParticleSystem sys = new SpriteParticleSystem(emitter, 7, 20, 30, mParticleTextureRegion, this.getVertexBufferObjectManager());
		
		
		
		
		sys.addParticleInitializer(new ColorParticleInitializer<Sprite>(0, 0, 0));
		sys.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
		sys.addParticleInitializer(new ScaleParticleInitializer<Sprite>(0.5f));
		
		sys.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-50, 50, -50, 50));

		sys.addParticleInitializer(new ExpireParticleInitializer<Sprite>(0.6f));

		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 0.1f, 0, 1));
		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(0.5f, 0.6f, 1, 0));
		
		return sys;
	}
	public SpriteParticleSystem CollectParticles(float x, float y){
		RectangleParticleEmitter emitter = new RectangleParticleEmitter(x, y ,2, 2);
		SpriteParticleSystem sys = new SpriteParticleSystem(emitter, 25, 40,80, mParticleTextureRegion, this.getVertexBufferObjectManager());
		
		
		
		
		sys.addParticleInitializer(new ColorParticleInitializer<Sprite>(1, 1, 0));
		sys.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
		sys.addParticleInitializer(new ScaleParticleInitializer<Sprite>(0.2f));
		sys.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		
		sys.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-100, 100, -100, 100));

		sys.addParticleInitializer(new ExpireParticleInitializer<Sprite>(0.6f));

		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 0.1f, 0, 1));
		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(0.5f, 0.6f, 1, 0));
		
		
		
		return sys;
	}
	
	// for stars in won scene
	public SpriteParticleSystem CollectParticles2(float x, float y){
		RectangleParticleEmitter emitter = new RectangleParticleEmitter(x, y ,2, 2);
		SpriteParticleSystem sys = new SpriteParticleSystem(emitter, 45, 80,100, mParticleTextureRegion, this.getVertexBufferObjectManager());
		
		
		
		
		sys.addParticleInitializer(new ColorParticleInitializer<Sprite>(1, 1, 0));
		sys.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
		sys.addParticleInitializer(new ScaleParticleInitializer<Sprite>(0.5f));
		sys.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		
		sys.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-300, 300, -300, 300));

		sys.addParticleInitializer(new ExpireParticleInitializer<Sprite>(1.2f));

		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 0.3f, 0, 1));
		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(1f, 1.2f, 1, 0));
		
		
		
		return sys;
	}
	
	
	public SpriteParticleSystem bombParticles(float x, float y){
		RectangleParticleEmitter emitter = new RectangleParticleEmitter(x, y ,2, 2);
		SpriteParticleSystem sys = new SpriteParticleSystem(emitter, 55, 70,80, mParticleTextureRegion, this.getVertexBufferObjectManager());
		
		
		
		
		sys.addParticleInitializer(new ColorParticleInitializer<Sprite>(1, 1, 0));
		sys.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
		sys.addParticleInitializer(new ScaleParticleInitializer<Sprite>(1.8f));
		sys.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		
		sys.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-110, 110, -110, 110));

		sys.addParticleInitializer(new ExpireParticleInitializer<Sprite>(0.6f));

		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 0.1f, 0, 1));
		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(0.5f, 0.6f, 1, 0));
		sys.addParticleModifier(new ColorParticleModifier<Sprite>(0, 0.6f, 1, 1, 0, 1, 0, 0));
		
		
		return sys;
	}
	
	public SpriteParticleSystem playerParticlesRight(){
		RectangleParticleEmitter emitter = new RectangleParticleEmitter(110, 50 ,2, 2);
		SpriteParticleSystem sys = new SpriteParticleSystem(emitter, 7, 20, 30, mParticleTextureRegion, this.getVertexBufferObjectManager());
		
		
		
		
		sys.addParticleInitializer(new ColorParticleInitializer<Sprite>(0, 0, 0));
		sys.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
		sys.addParticleInitializer(new ScaleParticleInitializer<Sprite>(0.5f));
		
		sys.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-50, 50, -50, 50));

		sys.addParticleInitializer(new ExpireParticleInitializer<Sprite>(0.6f));

		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 0.1f, 0, 1));
		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(0.5f, 0.6f, 1, 0));
		
		return sys;
	}
	
	
	
	public void decorPoint(Point p){
		decorPoint dp = new decorPoint(p.centerX, p.centerY);
		p.decorpoint = dp;
		dp.setPosition(p.centerX-dp.getWidth()/2, p.centerY-dp.getHeight()/2);
		scene.attachChild(dp);
	}
	
	
	public void decorPointRays(Point p){
		
		p.ray1 = new Sprite(p.centerX-rayTextureRegion.getWidth()/2, p.centerY-rayTextureRegion.getHeight()/2, rayTextureRegion, getVertexBufferObjectManager()){
			@Override
			public void setAlpha(float pAlpha) {
				super.setAlpha(pAlpha);
				super.setColor(pAlpha,pAlpha,pAlpha);
			}
		};
		p.ray2 = new Sprite(p.centerX-rayTextureRegion.getWidth()/2, p.centerY-rayTextureRegion.getHeight()/2, rayTextureRegion, getVertexBufferObjectManager()){
			@Override
			public void setAlpha(float pAlpha) {
				super.setAlpha(pAlpha);
				super.setColor(pAlpha,pAlpha,pAlpha);
			}
		};
		p.ray3 = new Sprite(p.centerX-rayTextureRegion.getWidth()/2, p.centerY-rayTextureRegion.getHeight()/2, rayTextureRegion, getVertexBufferObjectManager()){
			@Override
			public void setAlpha(float pAlpha) {
				super.setAlpha(pAlpha);
				super.setColor(pAlpha,pAlpha,pAlpha);
			}
		};
		
		p.ray1.setScale(0.714f);
		p.ray2.setScale(0.714f);
		p.ray3.setScale(0.714f);
		
		p.ray1.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.8f, 0.3f, 1), new AlphaModifier(0.8f, 1, 0.3f))));
		p.ray2.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.8f, 0.3f, 1), new AlphaModifier(0.8f, 1, 0.3f))));
		
		
		
		
		
		scene.attachChild(p.ray1);
		scene.attachChild(p.ray2);
		scene.attachChild(p.ray3);
//		
//		ScaleModifier sm1 = new ScaleModifier(1, 1.0f, 1.3f);
//		ScaleModifier sm2 = new ScaleModifier(1, 1.3f, 1.5f);
//		ScaleModifier sm3 = new ScaleModifier(2, 1.5f, 1.7f);
//		final ScaleModifier sm4 = new ScaleModifier(3, 1.0f, 1.7f);
//		final ScaleModifier sm5 = new ScaleModifier(2, 1.0f, 1.7f);
//		sm1.addModifierListener(new IModifierListener<IEntity>() {
//			
//			@Override
//			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
//				// TODO Auto-generated method stub
//				r2.registerEntityModifier(sm4);
//			}
//		});
//		sm2.addModifierListener(new IModifierListener<IEntity>() {
//			
//			@Override
//			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
//				// TODO Auto-generated method stub
//				r3.registerEntityModifier(sm5);
//			}
//		});
//		
//		r1.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(sm1,sm2,sm3)));
		
//		r3.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.8f, 0.3f, 1), new AlphaModifier(0.8f, 1, 0.3f))));
	}
	public void yellowHayBurn(final Stick s,final AnimatedSprite hay){
		float length = (float) Math.sqrt((s.p1.centerX - s.p2.centerX)
				* (s.p1.centerX - s.p2.centerX)
				+ (s.p1.centerY - s.p2.centerY)
				* (s.p1.centerY - s.p2.centerY));
		
		float rotation = (float) Math.toDegrees(Math
				.atan2(s.p1.centerY - s.p2.centerY, s.p1.centerX
						- s.p2.centerX));
		
		RectangleParticleEmitter emitter = new RectangleParticleEmitter(30, 20 ,60, 40);
		
		
		SpriteParticleSystem sysT; //temp system to address final and null variable problem
		if(particlesOn){
			sysT = new SpriteParticleSystem(emitter, 9, 30, 40
					, mParticleTextureRegion, this.getVertexBufferObjectManager());
		}
		else{
			sysT = new SpriteParticleSystem(emitter, 0, 2, 3
					, mParticleTextureRegion, this.getVertexBufferObjectManager());
		}
		final SpriteParticleSystem sys = sysT;
		
//		s.setRotation(-1*rotation);
		
		sys.addParticleInitializer(new ColorParticleInitializer<Sprite>(1, 0, 0));
		sys.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
		sys.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
//		sys.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-1, 1, -1, 1));
//		sys.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 360.0f));
		sys.addParticleInitializer(new ExpireParticleInitializer<Sprite>(3));
		sys.addParticleInitializer(new ScaleParticleInitializer<Sprite>(1.5f));
		
		sys.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 2, 1.5f, 2.0f));
		sys.addParticleModifier(new ColorParticleModifier<Sprite>(0, 1.3f, 1, 0.5f, 0, 0.5f, 0, 0.5f));
		sys.addParticleModifier(new ColorParticleModifier<Sprite>(1.3f, 2, 1, 1, 0.5f, 1, 0, 1));
		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 1, 0, 1));
		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(1, 2, 1, 0));
		
//		final Stick s1 = new Stick(s.p1.centerX+(s.p2.centerX-s.p1.centerX)/2-length/2, s.p1.centerY+(s.p2.centerY-s.p1.centerY)/2, s.p1.centerX+(s.p2.centerX-s.p1.centerX)/2+length-length/2, s.p1.centerY+(s.p2.centerY-s.p1.centerY)/2);
//		
//		s1.setAlpha(0);
//		
//		s1.attachChild(sys);
//		s1.setRotation(rotation);
//		scene.attachChild(s1);
		
		hay.attachChild(sys);
		
		AlphaModifier am = new AlphaModifier(1f, 1,0);
		am.addModifierListener(new IModifierListener<IEntity>() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				sys.setParticlesSpawnEnabled(false);
				s.strength = 0 ;
				
			}
		});
		s.registerEntityModifier(am);
		
//		hay.registerEntityModifier(new AlphaModifier(5, 1, 0));
//		e.registerEntityModifier(new AlphaModifier(5, 1, 0));
		
	}
	
	
	public void greenHayBurn(final Stick s,final AnimatedSprite hay){
		float length = (float) Math.sqrt((s.p1.centerX - s.p2.centerX)
				* (s.p1.centerX - s.p2.centerX)
				+ (s.p1.centerY - s.p2.centerY)
				* (s.p1.centerY - s.p2.centerY));
		
		float rotation = (float) Math.toDegrees(Math
				.atan2(s.p1.centerY - s.p2.centerY, s.p1.centerX
						- s.p2.centerX));
		
		RectangleParticleEmitter emitter = new RectangleParticleEmitter(30, 20 ,60, 40);
		
		SpriteParticleSystem sysT; //temp system to address final and null variable problem
		if(particlesOn){
			sysT = new SpriteParticleSystem(emitter, 9, 20, 30
					, mParticleTextureRegion, this.getVertexBufferObjectManager());
		}
		else{
			sysT = new SpriteParticleSystem(emitter, 0, 2, 3
					, mParticleTextureRegion, this.getVertexBufferObjectManager());
		}
		
		
		
		final SpriteParticleSystem sys = sysT;
		
//		s.setRotation(-1*rotation);
		
		sys.addParticleInitializer(new ColorParticleInitializer<Sprite>(0, 1, 0));
		sys.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
		sys.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
//		sys.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-1, 1, -1, 1));
//		sys.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 360.0f));
		sys.addParticleInitializer(new ExpireParticleInitializer<Sprite>(3));
		sys.addParticleInitializer(new ScaleParticleInitializer<Sprite>(1.5f));
		
		sys.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 2, 1.5f, 2.0f));
		sys.addParticleModifier(new ColorParticleModifier<Sprite>(0, 1.3f, 0, 0.5f, 1, 0.5f, 0, 0));
		sys.addParticleModifier(new ColorParticleModifier<Sprite>(1.3f, 2, 1, 1, 0.5f, 1, 0, 1));
		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 1, 0, 1));
		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(1, 2, 1, 0));
		
//		final Stick s1 = new Stick(s.p1.centerX+(s.p2.centerX-s.p1.centerX)/2-length/2, s.p1.centerY+(s.p2.centerY-s.p1.centerY)/2, s.p1.centerX+(s.p2.centerX-s.p1.centerX)/2+length-length/2, s.p1.centerY+(s.p2.centerY-s.p1.centerY)/2);
//		
//		s1.setAlpha(0);
//		
//		s1.attachChild(sys);
//		s1.setRotation(rotation);
//		scene.attachChild(s1);
		
		hay.attachChild(sys);
		
		AlphaModifier am = new AlphaModifier(1f, 1,0);
		am.addModifierListener(new IModifierListener<IEntity>() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				sys.setParticlesSpawnEnabled(false);
				s.strength = 0 ;
				
			}
		});
		s.registerEntityModifier(am);
		
//		hay.registerEntityModifier(new AlphaModifier(5, 1, 0));
//		e.registerEntityModifier(new AlphaModifier(5, 1, 0));
		
	}
	public void exploBurnStick(final Stick s, Point p1){
		
		float length = (float) Math.sqrt((s.p1.centerX - s.p2.centerX)
				* (s.p1.centerX - s.p2.centerX)
				+ (s.p1.centerY - s.p2.centerY)
				* (s.p1.centerY - s.p2.centerY));
		
		final float tt = length/200;
		
		Point p2t = null;
		if(s.p1.ID == p1.ID){
			p2t = s.p2;
		}
		else{
			p2t = s.p1;
		}
		final Point p2 = p2t;
		
		final Point p = new Point(p1.x, p1.y){

			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {

				for(int i = 0 ; i < s.eggList.size() ; i ++){
					if(s.eggList.get(i).updating==false){



						if(s.eggList.get(i).collidesWith(this)){
							s.eggList.get(i).updating=true;
							s.eggList.get(i).strength--;

							if(s.strength == 0){
								s.eggList.get(i).animate(new long[]{30,10000000}, new int[]{0,3}, false);
								s.hayList.get(i+1).registerEntityModifier(new AlphaModifier(1.5f, 1, 0));
								s.eggList.get(i).strength = 0;							
								yellowHayBurn(s,s.hayList.get(i+1) );
								//				
							}

							AlphaModifier am = new AlphaModifier(1, 1, 1);
							final int k = i;
							am.addModifierListener(new IModifierListener<IEntity>() {

								@Override
								public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
									// TODO Auto-generated method stub
									s.eggList.get(k).updating = false;
								}
							});
							s.eggList.get(i).registerEntityModifier(am);
						}
					}
				}


				super.onManagedUpdate(pSecondsElapsed);
			}
			
		};
		p.setColor(Color.TRANSPARENT);
		p.setAlpha(0);
		CircleParticleEmitter cpe = new CircleParticleEmitter(p.getWidth()/2, p.getHeight()/2, 30);
		final SpriteParticleSystem sys = new SpriteParticleSystem(cpe, 10, 20, 30, mParticleTextureRegion, this.getVertexBufferObjectManager());
		
		sys.addParticleInitializer(new ColorParticleInitializer<Sprite>(1, 0, 0));
		sys.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
		sys.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		sys.addParticleInitializer(new ExpireParticleInitializer<Sprite>(2));
		sys.addParticleInitializer(new ScaleParticleInitializer<Sprite>(0.5f));
		
		sys.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 2, 0.5f, 2.0f));
		sys.addParticleModifier(new ColorParticleModifier<Sprite>(0, 1, 1, 1, 0, 0.5f, 0, 0));
		sys.addParticleModifier(new ColorParticleModifier<Sprite>(1, 2, 1, 1, 0.5f, 1, 0, 1));
		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 1, 0, 1));
		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(1.5f, 2, 1, 0));
		
		p.attachChild(sys);
		scene.attachChild(p);
		MoveModifier mm = new MoveModifier(tt, p.x, p2.x, p.y, p2.y,EaseStrongOut.getInstance() );
		mm.addModifierListener(new IModifierListener<IEntity>() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				runOnUpdateThread(new Runnable() {
					
					@Override
					public void run() {
//						scene.detachChild(p2);
//						scene.attachChild(p2);
						scene.detachChild(p2.decorpoint);
						scene.attachChild(p2.decorpoint);
						
						if(p2.c != null){
							scene.detachChild(p2.c);
							scene.attachChild(p2.c);
						}
						scene.detachChild(player);
						scene.attachChild(player);
					}
				});
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				sys.setParticlesSpawnEnabled(false);
				runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						scene.detachChild(p);
					}
				});
				
			}
		});
		p.registerEntityModifier(mm);
		
		
		
		
	}
	public void thunderBurnLow(final Stick s){
		float length = (float) Math.sqrt((s.p1.centerX - s.p2.centerX)
				* (s.p1.centerX - s.p2.centerX)
				+ (s.p1.centerY - s.p2.centerY)
				* (s.p1.centerY - s.p2.centerY));
		
		float rotation = (float) Math.toDegrees(Math
				.atan2(s.p1.centerY - s.p2.centerY, s.p1.centerX
						- s.p2.centerX));
		
		RectangleParticleEmitter emitter = new RectangleParticleEmitter(length/2 -10, -20 ,length-120, 40);
		
		SpriteParticleSystem sysT; //temp system to address final and null variable problem
		if(particlesOn){
			sysT = new SpriteParticleSystem(emitter, 10, 25, 50
					, mParticleTextureRegion, this.getVertexBufferObjectManager());
		}
		else{
			sysT = new SpriteParticleSystem(emitter, 0, 2, 4
					, mParticleTextureRegion, this.getVertexBufferObjectManager());
		}
		
		
		final SpriteParticleSystem sys = sysT;
		
//		s.setRotation(-1*rotation);
		
		sys.addParticleInitializer(new ColorParticleInitializer<Sprite>(1, 0, 0));
		sys.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
		sys.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		sys.addParticleInitializer(new ExpireParticleInitializer<Sprite>(3));
		sys.addParticleInitializer(new ScaleParticleInitializer<Sprite>(0.5f));
		
		sys.addParticleModifier(new ScaleParticleModifier<Sprite>(0,3, 0.5f, 2.0f));
		sys.addParticleModifier(new ColorParticleModifier<Sprite>(0, 2, 1, 1, 0, 0.5f, 0, 0));
		sys.addParticleModifier(new ColorParticleModifier<Sprite>(2, 3, 1, 1, 0.5f, 1, 0, 1));
		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 1, 0, 1));
		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(2, 3, 1, 0));
		
		final Stick s1 = new Stick(s.p1.centerX+(s.p2.centerX-s.p1.centerX)/2-length/2, s.p1.centerY+(s.p2.centerY-s.p1.centerY)/2, s.p1.centerX+(s.p2.centerX-s.p1.centerX)/2+length-length/2, s.p1.centerY+(s.p2.centerY-s.p1.centerY)/2);
		
		s1.setAlpha(0);
		
		s1.attachChild(sys);
		s1.setRotation(rotation);
		scene.attachChild(s1);
		
		AlphaModifier am = new AlphaModifier(3, 1,0);
		am.addModifierListener(new IModifierListener<IEntity>() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				sys.setParticlesSpawnEnabled(false);
				s.strength = 0 ;
				
			}
		});
		s.registerEntityModifier(am);
		for(AnimatedSprite hay : s.hayList){
			hay.registerEntityModifier(new AlphaModifier(5, 1, 0));
		}
		for(egg e : s.eggList){
			e.registerEntityModifier(new AlphaModifier(5, 1, 0));
		}
	}
	
	public void thunderBurn(final Stick s){
		float length = (float) Math.sqrt((s.p1.centerX - s.p2.centerX)
				* (s.p1.centerX - s.p2.centerX)
				+ (s.p1.centerY - s.p2.centerY)
				* (s.p1.centerY - s.p2.centerY));
		
		float rotation = (float) Math.toDegrees(Math
				.atan2(s.p1.centerY - s.p2.centerY, s.p1.centerX
						- s.p2.centerX));
		
		RectangleParticleEmitter emitter = new RectangleParticleEmitter(length/2 -10, -20 ,length-120, 40);
		
		SpriteParticleSystem sysT; //temp system to address final and null variable problem
		if(particlesOn){
			sysT = new SpriteParticleSystem(emitter, 60, 60, 200
					, mParticleTextureRegion, this.getVertexBufferObjectManager());
		}
		else{
			sysT = new SpriteParticleSystem(emitter, 0, 2, 4
					, mParticleTextureRegion, this.getVertexBufferObjectManager());
		}
		
		
		final SpriteParticleSystem sys = sysT;
		
//		s.setRotation(-1*rotation);
		
		sys.addParticleInitializer(new ColorParticleInitializer<Sprite>(1, 0, 0));
		sys.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
		sys.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
//		sys.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-1, 1, -1, 1));
//		sys.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 360.0f));
		sys.addParticleInitializer(new ExpireParticleInitializer<Sprite>(3));
		sys.addParticleInitializer(new ScaleParticleInitializer<Sprite>(0.5f));
		
		sys.addParticleModifier(new ScaleParticleModifier<Sprite>(0,3, 0.5f, 2.0f));
		sys.addParticleModifier(new ColorParticleModifier<Sprite>(0, 2, 1, 1, 0, 0.5f, 0, 0));
		sys.addParticleModifier(new ColorParticleModifier<Sprite>(2, 3, 1, 1, 0.5f, 1, 0, 1));
		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 1, 0, 1));
		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(2, 3, 1, 0));
		
		final Stick s1 = new Stick(s.p1.centerX+(s.p2.centerX-s.p1.centerX)/2-length/2, s.p1.centerY+(s.p2.centerY-s.p1.centerY)/2, s.p1.centerX+(s.p2.centerX-s.p1.centerX)/2+length-length/2, s.p1.centerY+(s.p2.centerY-s.p1.centerY)/2);
		
		s1.setAlpha(0);
		
		s1.attachChild(sys);
		s1.setRotation(rotation);
		scene.attachChild(s1);
		
		AlphaModifier am = new AlphaModifier(3, 1,0);
		am.addModifierListener(new IModifierListener<IEntity>() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				burnm.play();
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				sys.setParticlesSpawnEnabled(false);
				s.strength = 0 ;
				
			}
		});
		s.registerEntityModifier(am);
		for(AnimatedSprite hay : s.hayList){
			hay.registerEntityModifier(new AlphaModifier(5, 1, 0));
		}
		for(egg e : s.eggList){
			e.registerEntityModifier(new AlphaModifier(5, 1, 0));
		}
	}
	
	public Stick stickParticles(final Stick s){
		float length = (float) Math.sqrt((s.p1.centerX - s.p2.centerX)
				* (s.p1.centerX - s.p2.centerX)
				+ (s.p1.centerY - s.p2.centerY)
				* (s.p1.centerY - s.p2.centerY));
		
		float rotation = (float) Math.toDegrees(Math
				.atan2(s.p1.centerY - s.p2.centerY, s.p1.centerX
						- s.p2.centerX));
		
//		RectangleParticleEmitter emitter = new RectangleParticleEmitter(p1.centerX+(p2.centerX-p1.centerX)/2, p1.centerY+(p2.centerY-p1.centerY)/2 ,length, 1);
		RectangleParticleEmitter emitter = new RectangleParticleEmitter(length/2, 0 ,length, 1);
		SpriteParticleSystem sys = new SpriteParticleSystem(emitter, 60, 60, 200, mParticleTextureRegion, this.getVertexBufferObjectManager());
		
		
		
		
		sys.addParticleInitializer(new ColorParticleInitializer<Sprite>(1, 0, 0));
		sys.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
//		sys.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		sys.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-2, 2, -5, -4));
//		sys.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 360.0f));
		sys.addParticleInitializer(new ExpireParticleInitializer<Sprite>(6));

		sys.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 5, 0.5f, 1.0f));
		sys.addParticleModifier(new ColorParticleModifier<Sprite>(0, 3, 1, 1, 0, 0.5f, 0, 0));
		sys.addParticleModifier(new ColorParticleModifier<Sprite>(4, 6, 1, 1, 0.5f, 1, 0, 1));
		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 1, 0, 1));
		sys.addParticleModifier(new AlphaParticleModifier<Sprite>(5, 6, 1, 0));
		
		
//		sys.setRotation(rotation);
		
		
		final Stick s1 = new Stick(s.p1.centerX+(s.p2.centerX-s.p1.centerX)/2-length/2, s.p1.centerY+(s.p2.centerY-s.p1.centerY)/2, s.p1.centerX+(s.p2.centerX-s.p1.centerX)/2+length-length/2, s.p1.centerY+(s.p2.centerY-s.p1.centerY)/2);
		s1.ID = s.ID;
		s1.strength = s.strength;
		s1.attachChild(sys);
//		
		s1.setRotation(rotation);
//		s.attachChild(sys);
//		
//		s1.detachChild(sys);
		alphaModThunder = new AlphaModifier(5.0f, 1.0f, 0.0f);
		alphaModThunder.addModifierListener(new IModifierListener<IEntity>() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				s1.setVisible(false);
				s.setVisible(false);
			}
		});
		s1.registerEntityModifier(alphaModThunder);
		s.registerEntityModifier(alphaModThunder);
//		s.setRotationCenter(distance, 0);
//		s.setRotation(-1*rotation);
//		
//		s.attachChild(sys);
//		s.setRotation(rotation);
		
		scene.attachChild(s1);
//		s.attachChild(s1)
		return s1;
		
		
	}
	
	public void decorateBridge(Stick s){
		float length = (float) Math.sqrt((s.p1.centerX - s.p2.centerX)
				* (s.p1.centerX - s.p2.centerX)
				+ (s.p1.centerY - s.p2.centerY)
				* (s.p1.centerY - s.p2.centerY));
		
		float rotation = (float) Math.toDegrees(Math
				.atan2(s.p1.centerY - s.p2.centerY, s.p1.centerX
						- s.p2.centerX));
		int eggCount = (int) Math.floor((length - 130*0.75)/(80*0.75));
		
//		
		float gap = (float) (length - 130*0.75 - eggCount*80*0.75) / 2 ;
		
		
		s.setAlpha(0);
		
		Line l1 = new Line(s.p1.centerX+(s.p2.centerX-s.p1.centerX)/2-length/2, s.p1.centerY+(s.p2.centerY-s.p1.centerY)/2, s.p1.centerX+(s.p2.centerX-s.p1.centerX)/2+length/2, s.p1.centerY+(s.p2.centerY-s.p1.centerY)/2, getVertexBufferObjectManager());
		l1.setAlpha(0);
		scene.attachChild(l1);
		hay h1 = new hay((float) (65*0.75f + gap - 50*0.75), (float) (-45*0.75));
		hay h2 = new hay((float) (length-gap - 65*0.75), (float) (-45*0.75));
		h1.setAlpha(0);
		h2.setAlpha(0);
		s.hayList.add(h1);
		
		l1.attachChild(h2);
		l1.attachChild(h1);
		for(int i = 0 ; i < eggCount ; i ++){
//			haybridge hh = new haybridge(50 + gap + i*80, -45);
//			l1.attachChild(hh);
//			s.hayList.add(hh);
			
			haybridge hh = new haybridge((float) (65 *0.75f + gap+ i*(80*0.75)  ), (float) (-45*0.75));
			hh.setScaleX(1.1f);
			hh.setAlpha(0);
//			egg e  = new egg((float) (65 *0.75f + gap+  + i*80*0.75 +40*0.75f - redeggTextureRegion.getWidth()/2), (float) (-20.5));
//			e.strength = s.strength;
//			e.setScale(0.75f);
			l1.attachChild(hh);
//			s.eggList.add(e);
//			this.eggListMain.add(e);
			s.hayList.add(hh);
//			l1.attachChild(e);
			
		}
		s.hayList.add(h2);
		l1.setRotation(rotation);
		for(AnimatedSprite h : s.hayList){
			h.registerEntityModifier(new AlphaModifier(1, 0, 1));
		}
	}
	
	
	
	public void decorate(Stick s){
		float length = (float) Math.sqrt((s.p1.centerX - s.p2.centerX)
				* (s.p1.centerX - s.p2.centerX)
				+ (s.p1.centerY - s.p2.centerY)
				* (s.p1.centerY - s.p2.centerY));
		
		
		float rotation = (float) Math.toDegrees(Math
				.atan2(s.p1.centerY - s.p2.centerY, s.p1.centerX
						- s.p2.centerX));
//		int eggCount = (int) (((length-150*0.714f))/(80*0.714));
		int eggCount = (int) Math.floor((length - 130*0.75)/(80*0.75));
		
//		float gap = ((int)((length-150*0.714f)%(80*0.714)))/2;
		float gap = (float) (length - 130*0.75 - eggCount*80*0.75) / 2 ;
		
		Line l1 = new Line(s.p1.centerX+(s.p2.centerX-s.p1.centerX)/2-length/2, s.p1.centerY+(s.p2.centerY-s.p1.centerY)/2, s.p1.centerX+(s.p2.centerX-s.p1.centerX)/2+length/2, s.p1.centerY+(s.p2.centerY-s.p1.centerY)/2, getVertexBufferObjectManager());
		l1.setAlpha(0);
		scene.attachChild(l1);
//		decorStick ds = new decorStick(s.p1.centerX+(s.p2.centerX-s.p1.centerX)/2 - stickTextureRegion.getWidth()/2, s.p1.centerY+(s.p2.centerY-s.p1.centerY)/2-stickTextureRegion.getHeight()/2);
//		ds.setScaleX(length/stickTextureRegion.getWidth());
		hay h1 = new hay((float) (65*0.75f + gap - 50*0.75), (float) (-45*0.75));
		hay h2 = new hay((float) (length-gap - 65*0.75), (float) (-45*0.75));
		
		
		s.hayList.add(h1);
		
		
		l1.attachChild(h2);
		l1.attachChild(h1);
		
		for(int i = 0 ; i < eggCount ; i ++){
			hayhole hh = new hayhole((float) (65 *0.75f + gap+ i*(80*0.75)  ), (float) (-45*0.75));
			hh.setScaleX(1.1f);
			egg e  = new egg((float) (65 *0.75f + gap+  + i*80*0.75 +40*0.75f - redeggTextureRegion.getWidth()/2), (float) (-20.5));
			e.strength = s.strength;
			e.setScale(0.75f);
			l1.attachChild(hh);
			s.eggList.add(e);
			this.eggListMain.add(e);
			s.hayList.add(hh);
			l1.attachChild(e);

		}
		s.hayList.add(h2);
		
//		ds.setRotationCenter(stickTextureRegion.getWidth()/2, stickTextureRegion.getHeight()/2);
//		ds.setScaleY(0.4f);
//		ds.setRotation(rotation);
		l1.setRotation(rotation);
//		scene.attachChild(ds);
	}
	
	public void circlep(float x, float y){
		final CircleOutlineParticleEmitter particleEmitter = new CircleOutlineParticleEmitter(CAMERA_WIDTH * 0.5f, CAMERA_HEIGHT * 0.5f + 20, 80);
		final SpriteParticleSystem particleSystem = new SpriteParticleSystem(particleEmitter, 60, 60, 360, this.mParticleTextureRegion, this.getVertexBufferObjectManager());
		particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(1, 0, 0));
		particleSystem.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
		particleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_COLOR, GLES20.GL_ONE));
		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-2, 2, -20, -10));
		particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 360.0f));
		particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(6));

		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 5, 1.0f, 2.0f));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(0, 3, 1, 1, 0, 0.5f, 0, 0));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(4, 6, 1, 1, 0.5f, 1, 0, 1));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 1, 0, 1));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(5, 6, 1, 0));
		
		particleEmitter.setCenter(x, y);
		
		scene.attachChild(particleSystem);

	}
	
	
	
	public void blast(int i , final Point p) {
		// grenade
		float dist = (float) Math.sqrt((p.centerX - player.currentPos.centerX)
				* (p.centerX -  player.currentPos.centerX)
				+ (p.centerY -  player.currentPos.centerY)
				* (p.centerY -  player.currentPos.centerY));
		float duration = dist/ (velocity*1.3f);
		
		if(i == 1){
			bombBlasting = true;
			final AnimatedSprite bomb = new AnimatedSprite(player.currentPos.centerX - grenadeTR.getWidth()/2, player.currentPos.centerY - grenadeTR.getHeight()/2, grenadeTR	, getVertexBufferObjectManager());
			bomb.animate(new long[]{31,31,31,31,31},new int[]{1,2,3,4,5},true);
			bomb.setScale(0.5f);
			scene.attachChild(bomb);
			MoveModifier mm = new MoveModifier(duration , bomb.getX(), p.centerX-bomb.getWidth()/2, bomb.getY(), p.centerY - bomb.getHeight()/2 , EaseCubicInOut.getInstance());
			
			final SpriteParticleSystem sp = bombParticles(bomb.getWidth()/2,bomb.getHeight()/2);
			bomb.attachChild(sp);
			sp.setParticlesSpawnEnabled(true);
			
			mm.addModifierListener(new IModifierListener<IEntity>() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					// TODO Auto-generated method stub
					sp.setParticlesSpawnEnabled(false);
					for(Stick s : p.connectedSticks){
						if(s.strength != 0){
							s.strength -- ;
							exploBurnStick(s, p);
							if(s.strength == 0){
								removedStickCount ++ ;
							}
							player.blackGrenade();
						}
					}
					explosionm.play();
					
					/// explosion effect
					
					final AnimatedSprite explo = new AnimatedSprite(p.centerX-32, p.centerY-32, explosionTextureRegion, getVertexBufferObjectManager());
					explo.animate(30,false);
					explo.setScale(2.2f);
					explo.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE);
					scene.attachChild(explo);
					AlphaModifier am = new AlphaModifier(3, 1, 1);
					am.addModifierListener(new IModifierListener<IEntity>() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
							bombBlasting = false;
							
							runOnUpdateThread(new Runnable() {
								
								@Override
								public void run() {
									scene.detachChild(explo);
								}
							});
						}
					});
					explo.registerEntityModifier(am);
					powerOn = false;
					
					
					powers.get(2).availability --;
					powers.get(2).functioning = false ;
					bomb.setVisible(false);
					
					runOnUpdateThread(new Runnable() {
						
						@Override
						public void run() {
							scene.detachChild(bomb);
							
						}
					});
				}
			});
			SequenceEntityModifier sem = new SequenceEntityModifier(new ScaleModifier(duration/2, 0.5f, 0.75f) , new  ScaleModifier(duration/2, 0.75f, 0.5f) );
			bomb.registerEntityModifier(new ParallelEntityModifier(mm,sem));
			
			
		}
		
		//charges
		if(i == 2){
			for(Stick s : p.connectedSticks){
				if(s.strength != 0){
					
					s.strength = 0 ;
					s.charged = true;
					exploBurnStick(s, p);
					removedStickCount ++ ;
				}
				
			}
			explosionm.play();
			final AnimatedSprite explo = new AnimatedSprite(p.centerX-32, p.centerY-32, explosionTextureRegion, getVertexBufferObjectManager());
			explo.animate(30,false);
			explo.setScale(2.2f);
			explo.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE);
			scene.attachChild(explo);
			AlphaModifier am = new AlphaModifier(3, 1, 1);
			am.addModifierListener(new IModifierListener<IEntity>() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					// TODO Auto-generated method stub
					
					
					runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							scene.detachChild(explo);
						}
					});
				}
			});
			explo.registerEntityModifier(am);
			powerOn = false;
			
		}
		
		
	}
	
	public Stick  getStick(Point ps1 , Point ps2){
		Stick sTmp = null ;
		for (Stick s : StickList) {
			if (s.p1.equals(ps1) && s.p2.equals(ps2)) {
				sTmp = s;
				
			}
			if (s.p1.equals(ps2) && s.p2.equals(ps1)) {
				sTmp = s;
				
			}
		}
		return sTmp;
	}
/////////////////////////////////////////////////////  Stick ///////////////////////////////////////////////
	public class Stick extends Line {

		public Point p1;
		public Point p2;
		public int strength;
		public boolean charged = false;
		
		
		public int ID;
		public ArrayList<AnimatedSprite> hayList = new ArrayList<AnimatedSprite>();
		public ArrayList<egg> eggList = new ArrayList<egg>();
		
		
		@Override
		public void setPosition(float pX1, float pY1, float pX2, float pY2) {
			super.setPosition(pX1, pY1, pX2, pY2);
			this.p1.x = pX1;
			this.p1.y = pY1;
			this.p2.x = pX2;
			this.p2.y = pY2;

		}

		public Stick(float pX1, float pY1, float pX2, float pY2,
				float pLineWidth,
				ILineVertexBufferObject pLineVertexBufferObject) {
			super(pX1, pY1, pX2, pY2, pLineWidth, pLineVertexBufferObject);

		}

		public Stick(float pX1, float pY1, float pX2, float pY2) {
			super(pX1, pY1, pX2, pY2, lineWidth, mEngine.getVertexBufferObjectManager());
			this.p1 = new Point(pX1, pY1);
			this.p2 = new Point(pX2, pY2);
			strength = 1;
			this.setAlpha(0);

		}

		public Stick(Point p1, Point p2) {
			super(p1.centerX, p1.centerY, p2.centerX, p2.centerY, lineWidth,
					mEngine.getVertexBufferObjectManager());
			this.p1 = p1;
			this.p2 = p2;
			strength = 1;
			this.setColor(Color.BLACK);
			this.setAlpha(0);
			this.setCullingEnabled(true);
		}
		
		@Override
		protected void onManagedUpdate(float pSecondsElapsed) {
			
			//to avoid glitches
			if(this.strength == 0 ){
				handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						for(egg e : eggList){
							e.strength =0 ;
						}
						for(int i = 1 ; i < hayList.size() -1 ; i ++){
							hayList.get(i).setVisible(false);
						}
						
						// TODO Auto-generated method stub
						setIgnoreUpdate(true);
					}
				}, 3000);
			}
//			if(this.strength <= 0){
//				this.setColor(Color.PINK);
//			}
			
//			if(this.strength == 1){
//				this.setColor(Color.BLUE);
//			}
//			if(this.strength == 2){
//				this.setColor(Color.BLACK);
//			}
//			if(this.strength == 3){
//				this.setColor(Color.GREEN);
//			}
			try {
				if(currentStick != null){
					if(this.ID == currentStick.ID && onaPoint==false ){



						if (powerOn==false) {
							for (int i = 0; i < eggList.size(); i++) {
								if (eggList.get(i).colided == false
										&& eggList.get(i).strength == 1
										&& eggList.get(i).updating == false) {
									if (eggList.get(i).collidesWith(player.entity)) {
										eggList.get(i).colided = true;
										eggList.get(i).strength = 0;
										eggList.get(i).animate(
												new long[] { 10 },
												new int[] { 5 }, false);

										hayList.get(i + 1)
												.registerEntityModifier(
														new AlphaModifier(1.5f,
																1, 0));
										//								
										greenHayBurn(Level.Stick.this,
												hayList.get(i + 1));
										//				
										handler.postDelayed(new Runnable() {
											
											@Override
											public void run() {
												// TODO Auto-generated method stub
												Level.Stick.this.setIgnoreUpdate(true);
											}
										}, 20000);
									}
								}
								if (eggList.get(i).crashed == false
										&& eggList.get(i).strength == 2) {
									if (eggList.get(i).collidesWith(player.entity)) {
										eggList.get(i).updating = true;
										eggList.get(i).strength = 1;
										eggList.get(i).crashed = true;
										AlphaModifier am = new AlphaModifier(0.8f,
												1, 1);
										final int j = i;
										am.addModifierListener(new IModifierListener<IEntity>() {

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
												eggList.get(j).updating = false;
											}
										});
										eggList.get(i).registerEntityModifier(
												am);
									}
								}
								//					if(eggList.get(i).effected  == false && eggList.get(i).strength == 3 ){
								//						if(eggList.get(i).collidesWith(player)){
								//							eggList.get(i).strength=2;
								//							eggList.get(i).effected=true;
								//						}
								//					}
							}
						}
						//power on true
						else{
						if(powers.get(3).functioning){
							for (final egg e : eggList){
								if(e.collidesWith(player.entity)){
									AlphaModifier am = new AlphaModifier(0.4f, 1, 0.8f);
									e.flamed  = true;
									am.addModifierListener(new IModifierListener<IEntity>() {
										
										@Override
										public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
											// TODO Auto-generated method stub
											
										}
										
										@Override
										public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
											// TODO Auto-generated method stub
//											e.flamed  = true;
										}
									});
//										e.registerEntityModifier(am);
									}
									
								
							}
						}
						}

					}
				}
				
				
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		super.onManagedUpdate(pSecondsElapsed);
		}

	}
	///////////////////////decor stick /////////////////
	
	public class decorStick extends AnimatedSprite{

		public decorStick(float pX, float pY, float pWidth, float pHeight,
				ITiledTextureRegion pTiledTextureRegion,
				ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject) {
			super(pX, pY, pWidth, pHeight, pTiledTextureRegion,
					pTiledSpriteVertexBufferObject);
			// TODO Auto-generated constructor stub
		}
		
		public decorStick(float x,float y){
			
			super(x, y, stickTextureRegion.getWidth(), stickTextureRegion.getHeight(), stickTextureRegion,
					mEngine.getVertexBufferObjectManager());
			this.setCullingEnabled(true);
		}
		
		
	}
	public class hay extends AnimatedSprite{

		public hay(float pX, float pY, float pWidth, float pHeight,
				ITiledTextureRegion pTiledTextureRegion,
				ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject) {
			super(pX, pY, pWidth, pHeight, pTiledTextureRegion,
					pTiledSpriteVertexBufferObject);
			// TODO Auto-generated constructor stub
		}
		
		public hay(float x,float y){
			
			super(x, y, hayTextureRegion.getWidth(), hayTextureRegion.getHeight(), hayTextureRegion,
					mEngine.getVertexBufferObjectManager());
			this.setCullingEnabled(true);
		}
		
		@Override
		public void setAlpha(float pAlpha) {
			// TODO Auto-generated method stub
			super.setAlpha(pAlpha);
			super.setColor(pAlpha, pAlpha, pAlpha);
		}
		
		
	}
	
	public class haybridge extends AnimatedSprite{

		public haybridge(float pX, float pY, float pWidth, float pHeight,
				ITiledTextureRegion pTiledTextureRegion,
				ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject) {
			super(pX, pY, pWidth, pHeight, pTiledTextureRegion,
					pTiledSpriteVertexBufferObject);
			// TODO Auto-generated constructor stub
		}
		
		public haybridge(float x,float y){
			
			super(x, y, haybridgeTextureRegion.getWidth(), haybridgeTextureRegion.getHeight(), haybridgeTextureRegion,
					mEngine.getVertexBufferObjectManager());
			
		}
		@Override
		public void setAlpha(float pAlpha) {
			// TODO Auto-generated method stub
			super.setAlpha(pAlpha);
			super.setColor(pAlpha, pAlpha, pAlpha);
		}
		
	}
	
	
	
	public class hayhole extends AnimatedSprite{

		public hayhole(float pX, float pY, float pWidth, float pHeight,
				ITiledTextureRegion pTiledTextureRegion,
				ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject) {
			super(pX, pY, pWidth, pHeight, pTiledTextureRegion,
					pTiledSpriteVertexBufferObject);
			// TODO Auto-generated constructor stub
		}
		
		public hayhole(float x, float y){
			super(x, y, hayholeTextureRegion.getWidth(), hayholeTextureRegion.getHeight(), hayholeTextureRegion,
					mEngine.getVertexBufferObjectManager());
			
			this.setCullingEnabled(true);
		}
		@Override
		public void setAlpha(float pAlpha) {
			// TODO Auto-generated method stub
			super.setAlpha(pAlpha);
			super.setColor(pAlpha, pAlpha, pAlpha);
		}
	}
	
	
	
	public class collect extends AnimatedSprite{
		public int index = -1;
		public boolean collected = false;
		public SpriteParticleSystem ps ;
		
		@Override
		public void setAlpha(float pAlpha) {
			// TODO Auto-generated method stub
			super.setAlpha(pAlpha);
			super.setColor(pAlpha, pAlpha, pAlpha);
		}
		
		public collect(float pX, float pY, float pWidth, float pHeight,
				ITiledTextureRegion pTiledTextureRegion,
				ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject) {
			super(pX, pY, pWidth, pHeight, pTiledTextureRegion,
					pTiledSpriteVertexBufferObject);
			// TODO Auto-generated constructor stub
		}
		
		public collect (Point p ){
			super(p.centerX-collectableTextureRegion.getWidth()/2, p.centerY-collectableTextureRegion.getHeight()/2, 70, 70, collectableTextureRegion,
					mEngine.getVertexBufferObjectManager());
			this.setScale(1.5f);
			this.setCullingEnabled(true);
			if(p.hasStar){
				index = 0;
				this.setScale(1);
				animate(new long[]{3000,150,150,150},new int[]{5,6,7,8},true);
			}
			if(p.hasThunder){
				index = 1;
				animate(new long[]{80},new int[]{4},false);
			}
			if(p.hasCharges){
				index = 2;
				animate(new long[]{80},new int[]{0},false);
			}
			if(p.hasGrenade){
				index = 3;
				animate(new long[]{80},new int[]{3},false);
			}
			if(p.hasFlame){
				index = 4;
				animate(new long[]{80},new int[]{2},false);
			}
			
			ps = CollectParticles(20, 20);
			this.attachChild(ps);
			ps.setParticlesSpawnEnabled(false);
			
//		////	has bridge havent implemented yet///      ///         ////
			
			
//			if(){
//				index = 5;
//				animate(new long[]{80},new int[]{4},false);
//			}
		}
		
		@Override
		protected void onManagedUpdate(float pSecondsElapsed) {
			
			if(collected == false && playerPlaced){
				if (player.entity.collidesWith(this)) {
					collected = true;
					starcm.play();
//					handler.postDelayed(new Runnable() {
						
//						@Override
//						public void run() {
							AlphaModifier am = new AlphaModifier(0.7f, 1, 0);
							am.addModifierListener(new IModifierListener<IEntity>() {
								
								@Override
								public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
									// TODO Auto-generated method stub
									Level.collect.this.ps.setParticlesSpawnEnabled(true);
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
									// TODO Auto-generated method stub
									handler.postDelayed(new Runnable() {
										
										@Override
										public void run() {
											Level.collect.this.ps.setParticlesSpawnEnabled(false);
											
										}
									}, 500);
									
									
									handler.postDelayed(new Runnable() {
										
										@Override
										public void run() {
											// TODO Auto-generated method stub
											setIgnoreUpdate(true);
										}
									}, 10000);
								}
							});
							
							
							if (index == 0) {
								initStars++;
								
								Level.collect.this.registerEntityModifier(new ParallelEntityModifier(
										new ScaleModifier(0.7f, 1f, 4), am));
							} else {
								powers.get(index - 1).availability++;
								
								Level.collect.this.registerEntityModifier(new ParallelEntityModifier(
										new ScaleModifier(0.7f, 1.5f, 6), am));
								
							}
							
						}
//					}, 400);
					
					
//				}
			}
			
			super.onManagedUpdate(pSecondsElapsed);
		}
		
	}
	
	public class egg extends AnimatedSprite{
		
		public boolean flamed = false;
		public boolean effected = false;
		public int strength = 1;
		public boolean colided = false;
		public boolean crashed = false;
		public boolean updating = false;
		
		
		public egg(float pX, float pY, float pWidth, float pHeight,
				ITiledTextureRegion pTiledTextureRegion,
				ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject) {
			super(pX, pY, pWidth, pHeight, pTiledTextureRegion,
					pTiledSpriteVertexBufferObject);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void setAlpha(float pAlpha) {
			// TODO Auto-generated method stub
			super.setAlpha(pAlpha);
			super.setColor(pAlpha, pAlpha, pAlpha);
		}
		
		public egg(float x, float y){
			super(x, y, redeggTextureRegion.getWidth(), redeggTextureRegion.getHeight(), redeggTextureRegion,
					mEngine.getVertexBufferObjectManager());
			this.setCullingEnabled(true);
		}
		
		@Override
		protected void onManagedUpdate(float pSecondsElapsed) {
			if(strength == 3&& flamed == false){
				animate(new long[]{10}, new int[]{13}, false);
			}
			if(strength == 2&& flamed == false){
				animate(new long[]{10}, new int[]{14}, false);
			}
			if(strength==1 && crashed&& flamed == false){
				animate(new long[]{10}, new int[]{4}, false);
			}
			if(strength==1 && crashed==false&& flamed == false){
				animate(new long[]{10}, new int[]{12}, false);
			}
			if(strength == 0 && flamed == false){
				animate(new long[]{10}, new int[]{5}, false);
			}
			if(flamed == true){
				animate(new long[]{10}, new int[]{8}, false);
			}
			
			super.onManagedUpdate(pSecondsElapsed);
		}
	}
	
	public class decorPoint extends AnimatedSprite{
		
		@Override
		public void setAlpha(float pAlpha) {
			super.setAlpha(pAlpha);
			super.setColor(pAlpha,pAlpha,pAlpha);
		}
		
		public decorPoint(float pX, float pY, float pWidth, float pHeight,
				ITiledTextureRegion pTiledTextureRegion,
				ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject) {
			super(pX, pY, pWidth, pHeight, pTiledTextureRegion,
					pTiledSpriteVertexBufferObject);
			// TODO Auto-generated constructor stub
		}
		
		public decorPoint(float x, float y){
			super(x, y, decopointTextureRegion.getWidth(), decopointTextureRegion.getHeight(), decopointTextureRegion,
					mEngine.getVertexBufferObjectManager());
		
			this.setScale(0.75f);
			this.setCullingEnabled(true);
//			if(xStretched){
//				this.setScaleX(getScaleX()*xScaleFactor);
//			}
//			if(yStretched){
//				this.setScaleY(getScaleY()*yScaleFactor);
//			}
		}
		
	}
	

	@Override
	public EngineOptions onCreateEngineOptions() {
		handler = new Handler();
		handler2 = new Handler();
		display = getWindowManager().getDefaultDisplay();
		actualWidth = display.getWidth();
		actualHeight = display.getHeight();
		
		SharedPreferences asp = getSharedPreferences("AREA", 0);
		currentArea = (int) asp.getInt("CURRENTAREA", 1);
		
		
		
		SharedPreferences sp = getSharedPreferences("LEVEL", 0);
		levelid = sp.getInt("CURRENTLEVEL", 7);
		bgname = bgnames[levelid];
		
		levelName = MainMenu.levelNames[levelid];
		
		SharedPreferences options = getSharedPreferences("OPTIONS", 0);
		soundOn = options.getBoolean("SOUNDS", true);
		musicOn = options.getBoolean("MUSIC",true);
		particlesOn = options.getBoolean("PARTICLES", true);
		
		
		if(levelid%2==0){
			bgMusicName = "levela.ogg" ;
		}
		if(levelid%2==1){
			bgMusicName = "levelb.ogg" ;
		}
		
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
		
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		
		EngineOptions eo = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
				new FillResolutionPolicy(), camera);
		eo.getAudioOptions().setNeedsMusic(true);
		eo.getAudioOptions().setNeedsSound(true);
		
		return eo ;
//		return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
//				new RatioResolutionPolicy(CAMERA_WIDTH,CAMERA_HEIGHT), camera);

	}

//	@Override
//	protected void onCreateResources() {
	public void loadGameResources() throws IllegalStateException, IOException{
		this.backTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		this.backTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.backTextureAtlas, this, bgname, 0, 0);
		
		this.backTextureAtlas.load();
		
		
		
		this.pointTatlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 128, 64,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.playerAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 2048, 1024, TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		
		
		
		///power 2 //
		this.bpowerAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 2048, 1024, TextureOptions.BILINEAR);
		this.bthunderTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(bpowerAtlas, this, "bthunderw.png", 2, 1);
		this.bflameTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(bpowerAtlas, this, "bflamew.png", 2, 1);
		this.bgrenadeTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(bpowerAtlas, this, "bgrenadew.png", 2, 1);
		this.bchargesTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(bpowerAtlas, this, "bchargesw.png", 2, 1);
		this.bbridgeTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(bpowerAtlas, this, "bbridgew.png", 2, 1);
		this.starTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(bpowerAtlas, this, "stars.png", 3, 1);
		
		///power strip
		this.stripTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.bpowerAtlas, this, "strip.png");
		this.buttonbackTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.bpowerAtlas, this, "buttonbox.png");
		
		
		///////// particles ////////
		this.mParticleTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mParticleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mParticleTextureAtlas, this, "particle_point.png", 0, 0);
		this.mParticleTextureAtlas.load();
		
		/////// decorations
		this.decorAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		
		this.decopointTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(decorAtlas, this, "points.png", 4, 4);
//		this.eggTextureRegion = BitmapTextureAtlasTextureRegionFactory
//				.createTiledFromAsset(decorAtlas, this, "eggs.png", 2, 1);
		this.redeggTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(decorAtlas, this, "eggswhite.png", 4, 4);
		this.hayholeTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(decorAtlas, this, "hayholes.png", 2, 2);
		this.hayTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(decorAtlas, this, "hay.png",1,1);
		this.haybridgeTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(decorAtlas, this, "haybridge.png",1,1);
		
		
		
		//ray
		this.rayTextureAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		this.rayTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.rayTextureAtlas, this, "ray.png",
						0, 0);
		this.rayTextureAtlas.load();
		
		///effects /////////////
		this.effectsAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.thundersTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(effectsAtlas, this, "thunders.png", 4, 2);
		this.cloudTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(effectsAtlas, this, "cloud.png", 1, 1);
		this.explosionTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(effectsAtlas, this, "explosion.png", 5, 5);
		
		//collectbables
		this.collectAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.collectableTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(collectAtlas, this, "collect.png", 3, 3);
		try {
			this.collectAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			this.collectAtlas.load();
		} catch (TextureAtlasBuilderException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		//in game menus
		this.menuAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
//		this.tryagainTR = BitmapTextureAtlasTextureRegionFactory
//				.createTiledFromAsset(menuAtlas, this, "tryagain.png", 1, 2);
		this.nextTR = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(menuAtlas, this, "next.png", 1, 2);
//		this.diedTR = BitmapTextureAtlasTextureRegionFactory
//				.createTiledFromAsset(menuAtlas, this, "died.png", 1, 2);
//		this.wonTR = BitmapTextureAtlasTextureRegionFactory
//				.createTiledFromAsset(menuAtlas, this, "won.png", 1, 2);
//		this.replayTR = BitmapTextureAtlasTextureRegionFactory
//				.createTiledFromAsset(menuAtlas, this, "replay.png", 1, 2);
		this.menuquitTR = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(menuAtlas, this, "menuquit.png", 1, 2);
		this.resumeTR = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(menuAtlas, this, "resume.png", 1, 2);
		this.restartTR = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(menuAtlas, this, "restart.png", 1, 2);
		this.mainmenuTR = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(menuAtlas, this, "mainmenu.png", 1, 2);
		
		
		this.menuAtlas2 = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		this.menuTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(menuAtlas2, this, "menu.png");
		this.retryTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(menuAtlas2, this, "retry.png");
		
		this.starsmlTR = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(menuAtlas2, this, "wonstar.png",1,2);
//		this.lostaTR = BitmapTextureAtlasTextureRegionFactory
//				.createFromAsset(menuAtlas2, this, "losta.png");
//		this.wonaTR = BitmapTextureAtlasTextureRegionFactory
//				.createFromAsset(menuAtlas2, this, "wona.png");
		this.levelpauseTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(menuAtlas2, this, "pause.png");
		this.levelrestartTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(menuAtlas2, this, "restarta.png");
		
		this.nextaTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(menuAtlas2, this, "next.png");
		
		
		this.menuAtlas3 = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		this.winBgTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(menuAtlas3, this, "winlostb.png");
		this.lostBgTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(menuAtlas2, this, "winlosta.png");
		this.powerbgTR = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(menuAtlas2, this, "powersbg.png");
		
		//anim bombs
		this.animbombAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.grenadeTR = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(animbombAtlas, this, "animbomb.png", 4,2);
		this.chargeTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(animbombAtlas, this, "animcharge.png");
		try {
			this.animbombAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			this.animbombAtlas.load();
		} catch (TextureAtlasBuilderException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		
		try {
			this.menuAtlas2.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			this.menuAtlas2.load();
		} catch (TextureAtlasBuilderException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			this.menuAtlas3.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			this.menuAtlas3.load();
		} catch (TextureAtlasBuilderException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		
		try {
			this.menuAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			this.menuAtlas.load();
		} catch (TextureAtlasBuilderException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		try {
			this.effectsAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			this.effectsAtlas.load();
		} catch (TextureAtlasBuilderException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		this.pointTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.pointTatlas, this,
						"points6.png", 2, 1);
		this.playerTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(playerAtlas, this, "player.png", 10, 10);

		
		this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, TextureOptions.BILINEAR, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 48);
		this.mFont.load();
		
		try {
			this.bpowerAtlas
			.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
					0, 0, 1));
			this.bpowerAtlas.load();

		} catch (TextureAtlasBuilderException e1) {
			e1.printStackTrace();
		}
		
		
		
		try {
			this.decorAtlas
			.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
					0, 0, 1));
			this.decorAtlas.load();
		} catch (TextureAtlasBuilderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			this.playerAtlas
			.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
					0, 0, 1));
			this.playerAtlas.load();
		} catch (TextureAtlasBuilderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			this.pointTatlas
			.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
					0, 0, 0));
			this.pointTatlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		
		
		loadMusic();
		

		final ITexture strokeFontTexture = new BitmapTextureAtlas(
				this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		this.strFont = new StrokeFont(this.getFontManager(), strokeFontTexture,
				Typeface.create(Typeface.MONOSPACE, Typeface.BOLD), 25, true,
				Color.WHITE, 2, new Color(0.2F, 0.2F, 0.2F));
		this.strFont.load();
		
		final ITexture strokeFontTexture2 = new BitmapTextureAtlas(
				this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		this.strFont2 = new StrokeFont(this.getFontManager(), strokeFontTexture2,
				Typeface.create(Typeface.MONOSPACE, Typeface.BOLD), 48, true,
				Color.WHITE, 2, Color.WHITE);
		this.strFont2.load();
		
		
	}	
	
	
		private void loadMusic() throws IllegalStateException, IOException {
			bgmusic = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this,bgMusicName);
			eatm = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(),this, "eatc.ogg");
			jumpm = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(),this, "jump.ogg");
			thunderm  = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(),this, "thundera.ogg");
			makethunderm = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(),this, "makethunder.ogg");
			hammerm = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(),this, "hammerc.ogg");
			explosionm = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(),this, "explosion.ogg");
			countm = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(),this, "count.ogg");
			angrym = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(),this, "angry.ogg");
			tapm = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(),this, "click.ogg");
			burnm = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(),this, "burn.ogg");
			winm = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(),this, "victory.ogg");
			losem = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(),this, "lose.ogg");
			staram = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(),this, "stara.ogg");
			starbm = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(),this, "starb.ogg");
			starcm = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(),this, "starc.ogg");
			
			if(soundOn == false){
				eatm.stop();
				jumpm.stop();
				thunderm.stop();
				makethunderm.stop();
				hammerm.stop();
				explosionm.stop();
				countm.stop();
				angrym.stop();
				burnm.stop();
				staram.stop();
				starbm.stop();
				starcm.stop();
				tapm.stop();
				
			}
			if(musicOn == false){
				bgmusic.stop();
				losem.stop();
				winm.stop();
			}
	}

		@Override
		public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback)throws Exception {
		
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		MusicFactory.setAssetBasePath("mfx/");
		
		loadItemsTextureAtlas = new BuildableBitmapTextureAtlas(getTextureManager(), 512, 256 , TextureOptions.BILINEAR);
		loadingTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas, this, "loading.png");
		angryTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadItemsTextureAtlas, this, "angry.png");
		loadItemsTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(5, 5, 5));
		loadItemsTextureAtlas.load();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}
		
		
		public void loadGameScene(){
			fpsl = new FPSLogger();
			//// current stick is initialized here
			currentStick = new Stick(-10, -10,-2,-2);
			currentStick.ID = -188;
			/////////////////////////////////////////
//			this.mVibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

			backgroud = new Sprite(CAMERA_WIDTH/2 - backTextureRegion.getWidth()/2, CAMERA_HEIGHT/2 - backTextureRegion.getHeight()/2, backTextureRegion, getVertexBufferObjectManager());
			
			
			
			backgroud.setScale(1.25f);
			
			
			this.mEngine.registerUpdateHandler(fpsl);
//			scene2 = new Scene();
//			scene2.setBackground(new Background(0.97f, 0.9f, 0.93f));
			
			sceneCover = new Rectangle(backgroud.getX(), backgroud.getY(), 640, 1024, getVertexBufferObjectManager());
			sceneCover.setScale(1.25f);
			
			scene = new Scene();
			scene.setBackground(new Background(0.97f, 0.9f, 0.93f));
			scene.attachChild(backgroud);
			scene.setScaleCenter(400, 640);
			
			if(xStretched){
				
				scene.setScaleX(scene.getScaleX()*xScaleFactor);
				backgroud.setScaleX(backgroud.getScaleX()*(1/xScaleFactor));
				sceneCover.setScaleX(sceneCover.getScaleX()*(1/xScaleFactor));
				
			}
			if(yStretched){
				
				
				scene.setScaleY(scene.getScaleY()*yScaleFactor);
				
				backgroud.setScaleY(backgroud.getScaleY()*(1/yScaleFactor));
				sceneCover.setScaleY(sceneCover.getScaleY()*(1/yScaleFactor));
				
			}
			
//			Log.d("ans", "back "+ scene.getScaleCenterX()+"  " + scene.getScaleCenterY() + " scale  "+ scene.getScaleX() + " "+ scene.getScaleY());
			
			
			
			
			thunderPoint = new Point(-100, -100 );
			scene.attachChild(thunderPoint);
			
			///power button strips
			Sprite buttonbck = new Sprite(0, CAMERA_HEIGHT-230, buttonbackTextureRegion, getVertexBufferObjectManager());
//			scene.attachChild(buttonbck);
			
			
			
			
//			tempconnector = new Point(-200,-200);
//			tempconnector.ID = 12361;
//			scene.attachChild(tempconnector);
//			scene.registerTouchArea(tempconnector);
			scene.setTouchAreaBindingOnActionDownEnabled(true);
			scene.setTouchAreaBindingOnActionMoveEnabled(true);
			
			
			scene.setOnSceneTouchListener(new IOnSceneTouchListener() {
			
				
			
				@Override
				public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
					
					if(pSceneTouchEvent.isActionDown() && powers.get(0).functioning){
						
						try {
							thunderPoint.setPosition(pSceneTouchEvent.getX()-thunderPoint.getWidth()/2 ,pSceneTouchEvent.getY()-thunderPoint.getHeight()/2 );
							thunderPoint.setVisible(true);
							
							
							
							for(Stick s : StickList){
								if(thunderPoint.collidesWith(s) && s.strength > 0){
									powers.get(0).functioning = false;
									powerOn = false;
									///this will create an problem if thunder is applied to 2 points at once
									powers.get(0).availability-- ;
									removedStickCount++;
									//todo : when hit thunder what happens
									effectThunder(s);
									player.makethunder();
									
									
									
									
									AlphaModifier am = new AlphaModifier(3, 1, 1);
									am.addModifierListener(new IModifierListener<IEntity>() {
										
										@Override
										public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
											// TODO Auto-generated method stub
											
										}
										
										@Override
										public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
											// TODO Auto-generated method stub
											player.randomAnimation();
										}
									});
									player.registerEntityModifier(am);
									
									break;
								}
							}
							thunderPoint.setPosition(-100, -100);
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(pSceneTouchEvent.isActionDown() && !onaPoint){
					
					}
					
					return true;
				}
			});
			trArray = new TiledTextureRegion[]{bthunderTextureRegion,bchargesTextureRegion,bgrenadeTextureRegion,bflameTextureRegion,bbridgeTextureRegion};
//			trArray = new TiledTextureRegion[]{bflameTextureRegion,bflameTextureRegion,bflameTextureRegion,bflameTextureRegion,bflameTextureRegion};
			
//			SharedPreferences sp = getSharedPreferences("LEVEL", 0);
//			final String name = sp.getString("LEVEL0NAME", "ajh");
			
			SharedPreferences sp = getSharedPreferences("LEVEL", 0);
			int levelIndex = sp.getInt("CURRENTLEVEL", 4) ;
			final String name = sp.getString("LEVEL"+levelIndex+"NAME", "big");
			
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (fileexist == false) {
//						Level.this.showDialog(1);
						loadgame(name);
					}

				}
			});
			
			timerBomb = new Text(CAMERA_WIDTH - 120, 20, strFont, "5.00",mEngine.getVertexBufferObjectManager() );
			timerBomb.setColor(new Color(0.5f, 0, 0));
			timerBomb.setVisible(false);
			scene.attachChild(timerBomb);
			
		
			
			///Win Scene
			
			
//			this.wonScene = new CameraScene(this.camera);
//			Text label = new Text( 400, 640, strFont, "Won!!", new TextOptions(HorizontalAlign.CENTER), mEngine.getVertexBufferObjectManager());
//			label.setColor(Color.BLACK);
//			label.setScale(5);
//			this.wonScene.attachChild(label);
//			/* Makes the paused Game look through. */
//			this.wonScene.setBackgroundEnabled(false);
			
			
			//Lose Scene
//			this.losescene = new CameraScene(this.camera);
//			Text label2 = new Text( 400, 640, strFont, "Lost!!", new TextOptions(HorizontalAlign.CENTER), mEngine.getVertexBufferObjectManager()){
//				@Override
//				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
//						float pTouchAreaLocalX, float pTouchAreaLocalY) {
//					if(pSceneTouchEvent.isActionDown()){
//						mVibrator.vibrate(500);
//						
//						
//					}
//					return true;
//				}
//			};
//			losescene.registerTouchArea(label2);
//			label2.setColor(Color.BLACK);
//			label2.setScale(5);
//			
//			this.losescene.attachChild(label2);
			
			
			/* Makes the paused Game look through. */
//			this.losescene.setBackgroundEnabled(false);
			
			final Text elapsedText = new Text(687, 50, strFont2, "5.0123456789", getVertexBufferObjectManager());
			elapsedText.setVisible(false);
			elapsedText.setColor(Color.WHITE);
			scene.attachChild(elapsedText);
			
			scene.registerUpdateHandler(new IUpdateHandler() {
				
				
				
				private float f;
				private float g;

				@Override
				public void reset() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onUpdate(float pSecondsElapsed) {
					
					
					// TODO Auto-generated method stud
					if(initStickCount - removedStickCount == 0 && onaPoint){
						
					}
					boolean b = false ;
					for(Stick s : StickList){
						if(s.strength > 0){
							b = true;
						}
					}
					if(b == false && onaPoint){
						
						handler.postDelayed(new Runnable() {
							
							@Override
							public void run() {
								fademainscene();
								
								
							}
						}, 3400);
						
						
						handler.postDelayed(new Runnable() {
							
							@Override
							public void run() {
								
								
								
//								loadState(stateList.get(1));
								gamefinished = true;
								
								player.setVisible(false);
								if(lost == false){
									
									if(levelName.equals("1.start")){
										showDialog(4);
									}
									else{
										scene.setChildScene(wonScene, false, true, true);
									}
									
									
									
									
								}
								won = true;
								onaPoint = false;
								
								
								
								
								
								
							}
						}, 3900);
						
//						
					}
					
					
					for(Point p : PointList){
						if(p.charged){
							
							f =  Level.this.mEngine.getSecondsElapsedTotal();
							g = f+5;
							elapsedText.setVisible(true);
							scene.registerUpdateHandler(new TimerHandler(0.05f, true, new ITimerCallback() {
								private String s;

								@Override
								public void onTimePassed(final TimerHandler pTimerHandler) {
									if(g-f>0.12){
										f = Level.this.mEngine.getSecondsElapsedTotal();
										s = String.valueOf(g-f);
										elapsedText.setText(s);
									}
									else{
										elapsedText.setVisible(false);
									}
									
								}
							}));
							
							
						}
					}
					
					
					
				}
			});
			
			
			eatm.setVolume(0.25f);
			eatm.setLooping(true);
			
			hammerm.setLooping(true);
			
			createWonScene();
			createpauseScene();
			createLoseScene();
			
			
			
			
		}
		

		@Override
		public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)throws Exception {
//	@Override
//	protected Scene onCreateScene() {
		SplashScene = new Scene();
		SplashScene.setBackground(new Background(Color.WHITE));
		loading = new Sprite(400 - 90 , 640 + 100, loadingTR, getVertexBufferObjectManager());
		angry = new Sprite(400-90, 640 - 90, angryTR, getVertexBufferObjectManager());
		SplashScene.attachChild(loading);
		SplashScene.attachChild(angry);
		
		pOnCreateSceneCallback.onCreateSceneFinished(SplashScene);

//		return scene;
	}
	
	
		
		
	@Override
		public synchronized void onWindowFocusChanged(boolean pHasWindowFocus) {
			
			super.onWindowFocusChanged(pHasWindowFocus);
			
			if (pHasWindowFocus) {
                if (mIsPausedBefore) {
                	if(bgmusic != null){
            			bgmusic.resume();
            		}
            		
//            		if(eatm != null){
//
//            			if(eatplaingonpause){
//            				eatm.resume();
//            				eatplaingonpause = false;
//            			}
//            		}
//            		if(hammerm != null){
//
//            			if(hammerplayingonpause){
//            				hammerm.resume();
//            				hammerplayingonpause = false;
//            			}
//            		}
                	
                	
                     mIsPausedBefore = false;
                } else {
                     //pause stuff here, except music pausing;
                }
        }
		}

	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
		if(pKeyCode == KeyEvent.KEYCODE_BACK && pEvent.getAction() == KeyEvent.ACTION_DOWN && menuaniminprocess == false) {
			if(gamefinished){
				return false;
			}
			
			
			if (scene != null) {
				if (this.scene.hasChildScene()) {
					/* Remove the menu and reset it. */
					menuaniminprocess = true;

					menubackdarker.registerEntityModifier(new AlphaModifier(
							0.2f, 0.45f, 0));
					resume.registerEntityModifier(new SequenceEntityModifier(
							new ParallelEntityModifier(new AlphaModifier(0.3f,
									1, 0), new MoveXModifier(0.3f, resume
									.getX(), resume.getX() + 800,
									EaseExponentialIn.getInstance()))));
					restart.registerEntityModifier(new SequenceEntityModifier(
							new AlphaModifier(0.1f, 1, 1),
							new ParallelEntityModifier(new AlphaModifier(0.3f,
									1, 0), new MoveXModifier(0.3f, restart
									.getX(), restart.getX() + 800,
									EaseExponentialIn.getInstance()))));
					mainmenu.registerEntityModifier(new SequenceEntityModifier(
							new AlphaModifier(0.2f, 1, 1),
							new ParallelEntityModifier(new AlphaModifier(0.3f,
									1, 0), new MoveXModifier(0.3f, mainmenu
									.getX(), mainmenu.getX() + 800,
									EaseExponentialIn.getInstance()))));
					SequenceEntityModifier seq = new SequenceEntityModifier(
							new AlphaModifier(0.3f, 1, 1),
							new ParallelEntityModifier(new AlphaModifier(0.3f,
									1, 0), new MoveXModifier(0.3f, menuquit
									.getX(), menuquit.getX() + 800,
									EaseExponentialIn.getInstance())));
					seq.addModifierListener(new IModifierListener<IEntity>() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							try {
								scene.clearChildScene();
								bgmusic.play();
								
								if(eatplaingonpause){
									eatm.resume();
									eatplaingonpause = false;
								}
								if(hammerplayingonpause){
									hammerm.resume();
									hammerplayingonpause = false;
								}
								
								menuaniminprocess = false;
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					});
					
					menuquit.registerEntityModifier(seq);
					//				
//					handler.postDelayed(new Runnable() {
//						//					
//						@Override
//						public void run() {
//							try {
//								scene.clearChildScene();
//								bgmusic.play();
//								menuaniminprocess = false;
//							} catch (Exception e) {
//								// TODO: handle exception
//							}
//
//							
//
//						}
//					}, 1100);
					return true;

				} else {
					//				scene.attachChild(menubackdarker);
					//				menubackdarker.setScaleX(1/scene.getScaleX());
					//				menubackdarker.setScaleY(1/scene.getScaleY());
					//				menubackdarker.registerEntityModifier(new AlphaModifier(0.3f,0, 0.25f));
					//				menubackdarker.setAlpha(0.2f);
					/* Attach the menu. */
					menuaniminprocess = true;
					this.scene
							.setChildScene(this.pauseScene, false, true, true);
					bgmusic.pause();
					if(eatm.isPlaying()){
						eatplaingonpause = true;
						eatm.pause();
						
					}
					if(hammerm.isPlaying()){
						hammerplayingonpause = true;
						hammerm.pause();
						
					}
					

					resume.registerEntityModifier(new SequenceEntityModifier(
							new ParallelEntityModifier(new AlphaModifier(0.3f,
									0, 1), new MoveXModifier(0.3f, resume
									.getX(), resume.getX() - 800,
									EaseExponentialOut.getInstance()))));
					restart.registerEntityModifier(new SequenceEntityModifier(
							new AlphaModifier(0.1f, 0, 0),
							new ParallelEntityModifier(new AlphaModifier(0.3f,
									0, 1), new MoveXModifier(0.3f, restart
									.getX(), restart.getX() - 800,
									EaseExponentialOut.getInstance()))));
					mainmenu.registerEntityModifier(new SequenceEntityModifier(
							new AlphaModifier(0.2f, 0, 0),
							new ParallelEntityModifier(new AlphaModifier(0.3f,
									0, 1), new MoveXModifier(0.3f, mainmenu
									.getX(), mainmenu.getX() - 800,
									EaseExponentialOut.getInstance()))));
					SequenceEntityModifier seq2 = new SequenceEntityModifier(
							new AlphaModifier(0.3f, 0, 0),
							new ParallelEntityModifier(new AlphaModifier(0.3f,
									0, 1), new MoveXModifier(0.3f, menuquit
									.getX(), menuquit.getX() - 800,
									EaseExponentialOut.getInstance())));
					seq2.addModifierListener(new IModifierListener<IEntity>() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							menuaniminprocess = false;
						}
					});
					
					menuquit.registerEntityModifier(seq2);
					menubackdarker.registerEntityModifier(new AlphaModifier(
							0.2f, 0, 0.45f));

//					handler.postDelayed(new Runnable() {
//
//						@Override
//						public void run() {
//							menuaniminprocess = false;
//						}
//					}, 1100);
					return true;

				}
			}
			else{
				return false;
			}
			
		} 
		else {
			return false;
//			return super.onKeyDown(pKeyCode, pEvent);
		}
		
	}

//	@Override
//	public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
//		switch(pMenuItem.getID()) {
//			case 0:
//				this.scene.reset();
//
//				/* Remove the menu and reset it. */
//				this.scene.clearChildScene();
//				this.pauseScene.reset();
//				return true;/*\
	
//			case 1:
//				
//			case 3:
//				/* End Activity. */
//				this.finish();
//				return true;
//			default:
//				return false;
//		}
//	}
	public void createpauseScene(){
		this.pauseScene = new Scene();
		menubackdarker = new Rectangle(0, 0, 800, 1280, getVertexBufferObjectManager());
		menubackdarker.setColor(Color.BLACK);
		menubackdarker.setAlpha(0);
		pauseScene.attachChild(menubackdarker);
		
		
		resume = new TiledSprite(400 + 800 - resumeTR.getWidth()/2, 400, resumeTR, getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					menuaniminprocess = true;
					tapm.play();
					this.setCurrentTileIndex(1);
					
					AlphaModifier am = new AlphaModifier(
							0.2f, 0.25f, 0);
					
					
					menubackdarker.registerEntityModifier(am);
					
					
					
					resume.registerEntityModifier(new SequenceEntityModifier(
							new AlphaModifier(0.001f, 1, 1),
							new ParallelEntityModifier(new AlphaModifier(0.3f,
									1, 0), new MoveXModifier(0.3f, resume
									.getX(), resume.getX() + 800,
									EaseExponentialIn.getInstance()))));
					restart.registerEntityModifier(new SequenceEntityModifier(
							new AlphaModifier(0.1f, 1, 1),
							new ParallelEntityModifier(new AlphaModifier(0.3f,
									1, 0), new MoveXModifier(0.3f, restart
									.getX(), restart.getX() + 800,
									EaseExponentialIn.getInstance()))));
					mainmenu.registerEntityModifier(new SequenceEntityModifier(
							new AlphaModifier(0.2f, 1, 1),
							new ParallelEntityModifier(new AlphaModifier(0.3f,
									1, 0), new MoveXModifier(0.3f, mainmenu
									.getX(), mainmenu.getX() + 800,
									EaseExponentialIn.getInstance()))));
					SequenceEntityModifier seq = new SequenceEntityModifier(
							new AlphaModifier(0.3f, 1, 1),
							new ParallelEntityModifier(new AlphaModifier(0.3f,
									1, 0), new MoveXModifier(0.3f, menuquit
									.getX(), menuquit.getX() + 800,
									EaseExponentialIn.getInstance())));
					seq.addModifierListener(new IModifierListener<IEntity>() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
							// TODO Auto-generated method stub
							scene.clearChildScene();
							setCurrentTileIndex(0);
							menuaniminprocess = false;
							bgmusic.play();
							
							if(eatplaingonpause){
								eatm.resume();
								eatplaingonpause = false;
							}
							if(hammerplayingonpause){
								hammerm.resume();
								hammerplayingonpause = false;
							}
						}
					});
					
					menuquit.registerEntityModifier(seq);
					//				
//					handler.postDelayed(new Runnable() {
//						//					
//						@Override
//						public void run() {
//							scene.clearChildScene();
//							setCurrentTileIndex(0);
//							menuaniminprocess = false;
//							bgmusic.play();
//						}
//					}, 1100);
					return true;
				}
				else{
					return false;				}
				
			}
		};
		restart = new TiledSprite(400 + 800- restartTR.getWidth()/2, 500, restartTR, getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					tapm.play();
					this.setCurrentTileIndex(1);
					handler.postDelayed(new Runnable() {

						@Override
						public void run() {

							Intent intent = getIntent();
							finish();
							startActivity(intent);

							setCurrentTileIndex(0);

						}
					}, 100);
				}
				return true;
			}
		};
		mainmenu = new TiledSprite(400 + 800 - mainmenuTR.getWidth()/2, 600, mainmenuTR, getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				
				
				if (pSceneTouchEvent.isActionDown()) {
					tapm.play();
					this.setCurrentTileIndex(1);
					handler.postDelayed(new Runnable() {

						@Override
						public void run() {

							Intent inte = new Intent(Level.this,
									MainMenu2.class);
							finish();
							startActivity(inte);

							setCurrentTileIndex(0);

						}
					}, 100);
				}
				return true;
			}
		};
		menuquit = new TiledSprite(400 + 800- menuquitTR.getWidth()/2, 700, menuquitTR, getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
	
				if (pSceneTouchEvent.isActionDown()) {
					tapm.play();
					this.setCurrentTileIndex(1);
					handler.postDelayed(new Runnable() {

						@Override
						public void run() {
							finish();
							Intent intent = new Intent(Intent.ACTION_MAIN);
							intent.addCategory(Intent.CATEGORY_HOME);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(intent);

							setCurrentTileIndex(0);

						}
					}, 100);
				}
				return true;
			}
		};
		
		resume.setScale(1.4f);
		restart.setScale(1.4f);
		mainmenu.setScale(1.4f);
		menuquit.setScale(1.4f);
		
		
		
		pauseScene.attachChild(resume);
		pauseScene.attachChild(restart);
		pauseScene.attachChild(mainmenu);
		pauseScene.attachChild(menuquit);
		
		pauseScene.registerTouchArea(resume);
		pauseScene.registerTouchArea(restart);
		pauseScene.registerTouchArea(menuquit);
		pauseScene.registerTouchArea(mainmenu);

		pauseScene.setBackgroundEnabled(false);
	}

	public void fademainscene(){
		for(final Point p : PointList){
			p.decorpoint.registerEntityModifier(new AlphaModifier(0.5f, 1, 0));
			p.registerEntityModifier(new AlphaModifier(0.5f, 1, 0));
			p.ray1.clearEntityModifiers();
			p.ray2.clearEntityModifiers();
			p.ray3.clearEntityModifiers();
			p.ray1.registerEntityModifier(new AlphaModifier(0.5f, 1, 0));
			p.ray2.registerEntityModifier(new AlphaModifier(0.5f, 1, 0));
			p.ray3.registerEntityModifier(new AlphaModifier(0.5f, 1, 0));
			
			p.ray1.setVisible(false);
			p.ray2.setVisible(false);
			p.ray3.setVisible(false);
//			runOnUpdateThread(new Runnable() {
//				
//				@Override
//				public void run() {
//					scene.detachChild(p.ray1);
//					scene.detachChild(p.ray2);
//					scene.detachChild(p.ray3);
//					
//				}
//			});
			
		}
		MoveYModifier mm = new MoveYModifier( 0.5f, powerbg.getY(),powerbg.getY() + 400, EaseStrongInOut.getInstance());
		mm.addModifierListener(new IModifierListener<IEntity>() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				for(Point p : PointList){
					p.setVisible(false);
					
				}
				player.setVisible(false);
			}
		});
		
		for(Power p2 : powers){
			p2.registerEntityModifier(new MoveYModifier(0.5f, p2.getY(),p2.getY() + 400, EaseStrongInOut.getInstance()));
			
		}
		powerbg.registerEntityModifier(mm);
		
		for (Stick s : StickList){
			for(egg e : s.eggList){
				e.registerEntityModifier(new AlphaModifier(0.5f, e.getAlpha(), 0));
			}
			for(AnimatedSprite h : s.hayList){
				h.registerEntityModifier(new AlphaModifier(0.5f, h.getAlpha(), 0));
			}
		}
		
		starback.registerEntityModifier(new MoveYModifier(0.5f, starback.getY(),starback.getY() -200 , EaseStrongInOut.getInstance()));
		if(player.isAnimationRunning()){
			player.stopAnimation();
		}

		player.registerEntityModifier(new AlphaModifier(0.5f, 1, 0));
	}
	
	public void createLoseScene(){
		loseScene = new Scene();
		losemenubackdarker = new Rectangle(0, 0, 800, 1280, getVertexBufferObjectManager());
		losemenubackdarker.setColor(Color.BLACK);
		losemenubackdarker.setAlpha(0.7f);
		
		
		loseScene.setBackgroundEnabled(false);
		
		loseScene.setScaleCenter(400, 640);
		
		if(xStretched){

			loseScene.setScaleX(loseScene.getScaleX()*xScaleFactor);
			losemenubackdarker.setScaleX(losemenubackdarker.getScaleX()*(1/xScaleFactor));
			
		}
		if(yStretched){
			loseScene.setScaleY(loseScene.getScaleY()*yScaleFactor);
			losemenubackdarker.setScaleY(losemenubackdarker.getScaleY()*(1/yScaleFactor));
		}
		
		loseScene.attachChild(losemenubackdarker);
		losebg = new Sprite(100, 220, lostBgTR, getVertexBufferObjectManager());
		
		
		retryb = new Sprite(400 - 185, 786, retryTR.deepCopy(), getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					bgmusic.pause();
					tapm.play();
					this.setScale(1.1f);
					
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							Intent intent = getIntent();
							finish();
							startActivity(intent);
							setScale(1f);
						}
					}, 100);
				}
				return true;
			}
		};
		menub = new Sprite(400 + 25 ,786, menuTR.deepCopy(), getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				if (pSceneTouchEvent.isActionDown()) {
					bgmusic.pause();
					tapm.play();
					this.setScale(1.1f);
					handler.postDelayed(new Runnable() {
					
						@Override
						public void run() {
							setScale(1);
							Intent inte = new Intent(Level.this, MainMenu2.class);

							SharedPreferences sp = getSharedPreferences("LEVEL", 0);
							SharedPreferences.Editor editor = sp.edit();
							int nextLevelIndex = sp.getInt("CURRENTLEVEL", 4)  + 1;
							editor.putBoolean("LEVEL"+nextLevelIndex+"UNLOCKED", true);
							editor.commit();

							finish();
							startActivity(inte);
							
						}
					}, 100);
				}
				return true;
			}
		};
		
		losemenubackdarker.setAlpha(0);
		losebg.setAlpha(0);
		menub.setAlpha(0);
		retryb.setAlpha(0);
		
	
		loseScene.attachChild(losebg);
		loseScene.attachChild(menub);
		loseScene.attachChild(retryb);
		
		losebg.registerEntityModifier(new AlphaModifier(0.5f, 0, 1));
		losemenubackdarker.registerEntityModifier(new AlphaModifier(0.5f, 0, 0.2f));
		retryb.registerEntityModifier(new AlphaModifier(0.5f, 0, 1f));
		menub.registerEntityModifier(new AlphaModifier(0.5f, 0, 1f));
		
		loseScene.registerTouchArea(menub);
		loseScene.registerTouchArea(retryb);
	}
	
	
	public void createWonScene(){
		
		
		wonScene = new Scene();
		wonmenubackdarker = new Rectangle(0, 0, 800, 1280, getVertexBufferObjectManager());
		wonmenubackdarker.setColor(Color.BLACK);
		wonmenubackdarker.setAlpha(0.7f);
		
		wonScene.setScaleCenter(400, 640);
		
		if(xStretched){

			wonScene.setScaleX(wonScene.getScaleX()*xScaleFactor);
			wonmenubackdarker.setScaleX(wonmenubackdarker.getScaleX()*(1/xScaleFactor));
			
		}
		if(yStretched){
			wonScene.setScaleY(wonScene.getScaleY()*yScaleFactor);
			wonmenubackdarker.setScaleY(wonmenubackdarker.getScaleY()*(1/yScaleFactor));
		}

		
		
		
		
		wonScene.attachChild(wonmenubackdarker);
		wonScene.setAlpha(0);
		
		
		winbg = new Sprite(100, 150, winBgTR, getVertexBufferObjectManager());
//		wonlable = new Sprite(400 - wonaTR.getWidth()/2, 530, wonaTR, getVertexBufferObjectManager());
		starempty2 = new TiledSprite(317-20, 645, starsmlTR, getVertexBufferObjectManager());
		starempty1 = new TiledSprite(140-20, 645, starsmlTR, getVertexBufferObjectManager());
		starempty3 = new TiledSprite(495-20 , 645, starsmlTR, getVertexBufferObjectManager());
		starempty1.setCurrentTileIndex(0);
		starempty2.setCurrentTileIndex(0);
		starempty3.setCurrentTileIndex(0);
		
		star3 = new TiledSprite(495-20 , 645, starsmlTR, getVertexBufferObjectManager());
		star2 = new TiledSprite(317-20, 645, starsmlTR, getVertexBufferObjectManager());
		star1 = new TiledSprite(140-20, 645, starsmlTR, getVertexBufferObjectManager());
		star1.setCurrentTileIndex(1);
		star2.setCurrentTileIndex(1);
		star3.setCurrentTileIndex(1);
		
		star1.setAlpha(0);
		star2.setAlpha(0);
		star3.setAlpha(0);
		
		star1.setScale(3);
		star2.setScale(3);
		star3.setScale(3);
		
		
		
		
		nexta = new Sprite(482, 855, nextaTR, getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				if (pSceneTouchEvent.isActionDown()) {
					
//					if(levelName.equals("45.1")){
//						
//						// for china
//						
////						String url = "http://www.facebook.com/pages/Eat-em-all/144865022318112";
////						Intent i = new Intent(Intent.ACTION_VIEW);
////						i.setData(Uri.parse(url));
////						startActivity(i);
//						Intent inte = new Intent(Level.this, MainMenu2.class);
//						finish();
//						startActivity(inte);
//						
//						
//					}
					if(levelid == 8 || levelid == 17 || levelid == 26 || levelid == 35 || levelid == 44 || levelid == 53  || levelid == 62 ){
						
						
						SharedPreferences asp = getSharedPreferences("AREA", 0);
						SharedPreferences.Editor spe = asp.edit();
						int nextarea = currentArea +1 ;
						spe.putBoolean("AREA"+nextarea+"STATE", true);
						spe.commit();
						
						
						
						setScale(1.1f);
						tapm.play();
						handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								setScale(1);
								SharedPreferences sp = getSharedPreferences("LEVEL", 0);
								int levelIndex = sp.getInt("CURRENTLEVEL", 4);
								SharedPreferences.Editor editor = sp.edit();
								editor.putInt("CURRENTLEVEL", (levelIndex + 1));
								int nextLevelIndex = levelIndex + 1;
								editor.putBoolean("LEVEL" + nextLevelIndex + "UNLOCKED",
										true);
								editor.putInt("LEVEL"+levelIndex+"STARS" , initStars);
								
								
								editor.commit();
								
								
								wonScene.setVisible(false);
								runOnUiThread(new Runnable() {
									
									@Override
									public void run() {
										Level.this.showDialog(23);
										
									}
								});
							}
						},100);
						
						
						
					} 
					
					else if(levelid == 71){


						SharedPreferences asp = getSharedPreferences("AREA", 0);
						SharedPreferences.Editor spe = asp.edit();
						int nextarea = currentArea +1 ;
						spe.putBoolean("AREA"+nextarea+"STATE", true);
						spe.commit();
						
						setScale(1.1f);
						tapm.play();
						handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								setScale(1);
								SharedPreferences sp = getSharedPreferences("LEVEL", 0);
								int levelIndex = sp.getInt("CURRENTLEVEL", 4);
								SharedPreferences.Editor editor = sp.edit();
								editor.putInt("CURRENTLEVEL", (levelIndex + 1));
								int nextLevelIndex = levelIndex + 1;
								editor.putBoolean("LEVEL" + nextLevelIndex + "UNLOCKED",
										true);
								editor.putInt("LEVEL"+levelIndex+"STARS" , initStars);
								
								
								editor.commit();
								
								
								wonScene.setVisible(false);
								runOnUiThread(new Runnable() {

									@Override
									public void run() {
										Level.this.showDialog(24);

									}
								});
								
							}
						},100);
						
						
						
					} 
					
					
					else{
						setScale(1.1f);
						tapm.play();
						handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								setScale(1);
								SharedPreferences sp = getSharedPreferences("LEVEL", 0);
								int levelIndex = sp.getInt("CURRENTLEVEL", 4);
								SharedPreferences.Editor editor = sp.edit();
								editor.putInt("CURRENTLEVEL", (levelIndex + 1));
								int nextLevelIndex = levelIndex + 1;
								editor.putBoolean("LEVEL" + nextLevelIndex + "UNLOCKED",
										true);
								editor.putInt("LEVEL"+levelIndex+"STARS" , initStars);
								
								
								editor.commit();
								Intent intent = getIntent();
								finish();
								startActivity(intent);
							}
						},100);
					}
					
					
					
					
					
				}
				return true;
			}
		};
		retrya = new Sprite(164, 855, retryTR, getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					
					
					setScale(1.1f);
					tapm.play();
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							setScale(1);
							SharedPreferences sp = getSharedPreferences("LEVEL", 0);
							SharedPreferences.Editor editor = sp.edit();
							
							int levelIndex = sp.getInt("CURRENTLEVEL", 4);
							
							int nextLevelIndex = levelIndex + 1;
							
							editor.putBoolean("LEVEL"+nextLevelIndex+"UNLOCKED", true);
							
							editor.putInt("LEVEL"+levelIndex+"STARS" , initStars);
							
							editor.commit();
							
							
							
							Intent intent = getIntent();
							finish();
							startActivity(intent);
						}
					},100);
					
					
				}
				return true;
			}
		};
		menua = new Sprite(323,855, menuTR, getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				if (pSceneTouchEvent.isActionDown()) {
					
					
					setScale(1.1f);
					tapm.play();
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							setScale(1);
							Intent inte = new Intent(Level.this, MainMenu2.class);

							SharedPreferences sp = getSharedPreferences("LEVEL", 0);
							SharedPreferences.Editor editor = sp.edit();
							
							int levelIndex = sp.getInt("CURRENTLEVEL", 4);
							
							int nextLevelIndex = levelIndex  + 1;
							
							editor.putBoolean("LEVEL"+nextLevelIndex+"UNLOCKED", true);
							
							editor.putInt("LEVEL"+levelIndex+"STARS" , initStars);
							
							editor.commit();

							finish();
							startActivity(inte);
						}
					},100);
					
				}
				return true;
			}
		};
		winbg.setAlpha(0);
		wonmenubackdarker.setAlpha(0);
		starempty1.setAlpha(0);
		starempty2.setAlpha(0);
		starempty3.setAlpha(0);
		retrya.setAlpha(0);
		menua.setAlpha(0);
		nexta.setAlpha(0);
		
		
		wonScene.attachChild(winbg);
//		wonScene.attachChild(wonlable);
		wonScene.attachChild(starempty1);
		wonScene.attachChild(starempty2);
		wonScene.attachChild(starempty3);
		wonScene.attachChild(star1);
		wonScene.attachChild(star2);
		wonScene.attachChild(star3);
		wonScene.attachChild(nexta);
		wonScene.attachChild(retrya);
		wonScene.attachChild(menua);
		
		final SpriteParticleSystem sps1 = CollectParticles2(star1.getWidth()/2, star1.getHeight()/2);
		final SpriteParticleSystem sps2 = CollectParticles2(star1.getWidth()/2, star1.getHeight()/2);
		final SpriteParticleSystem sps3 = CollectParticles2(star1.getWidth()/2, star1.getHeight()/2);
		sps1.setParticlesSpawnEnabled(false);
		sps2.setParticlesSpawnEnabled(false);
		sps3.setParticlesSpawnEnabled(false);
		
		star1.attachChild(sps1);
		star2.attachChild(sps2);
		star3.attachChild(sps3);
		
		
		wonScene.setBackgroundEnabled(false);
		
		final ParallelEntityModifier spm1 = new ParallelEntityModifier(new ScaleModifier(0.4f,3,1,EaseBounceOut.getInstance()),new AlphaModifier(0.4f, 0, 1));
		spm1.addModifierListener(new IModifierListener<IEntity>() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				
				sps1.setParticlesSpawnEnabled(true);
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				sps1.setParticlesSpawnEnabled(false);
				staram.play();
			}
		});
		
		final ParallelEntityModifier spm2 = new ParallelEntityModifier(new ScaleModifier(0.4f,3,1,EaseBounceOut.getInstance()),new AlphaModifier(0.4f, 0, 1));
		spm2.addModifierListener(new IModifierListener<IEntity>() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				sps2.setParticlesSpawnEnabled(true);
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				sps2.setParticlesSpawnEnabled(false);
				starbm.play();
			}
		});
		
		final ParallelEntityModifier spm3 = new ParallelEntityModifier(new ScaleModifier(0.4f,3,1,EaseBounceOut.getInstance()),new AlphaModifier(0.4f, 0, 1));
		spm3.addModifierListener(new IModifierListener<IEntity>() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				sps3.setParticlesSpawnEnabled(true);
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				sps3.setParticlesSpawnEnabled(false);
				starcm.play();
			}
		});
		
		
		
		AlphaModifier sceneam = new AlphaModifier(0.5f, 1, 1);
		sceneam.addModifierListener(new IModifierListener<IEntity>() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				bgmusic.pause();
				winm.play();
				
				winbg.registerEntityModifier(new AlphaModifier(0.5f, 0, 1));
				wonmenubackdarker.registerEntityModifier(new AlphaModifier(0.5f, 0, 0.2f));
				starempty1.registerEntityModifier(new AlphaModifier(0.5f, 0, 1f));
				starempty2.registerEntityModifier(new AlphaModifier(0.5f, 0, 1f));
				starempty3.registerEntityModifier(new AlphaModifier(0.5f, 0, 1f));
				retrya.registerEntityModifier(new AlphaModifier(0.5f, 0, 1f));
				menua.registerEntityModifier(new AlphaModifier(0.5f, 0, 1f));
				nexta.registerEntityModifier(new AlphaModifier(0.5f, 0, 1f));
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				if(initStars == 0){
					wonScene.registerTouchArea(nexta);
					wonScene.registerTouchArea(menua);
					wonScene.registerTouchArea(retrya);
				}
				if(initStars == 1){
					star1.registerEntityModifier(spm1);
					handler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							wonScene.registerTouchArea(nexta);
							wonScene.registerTouchArea(menua);
							wonScene.registerTouchArea(retrya);
							
						}
					}, 400);
				}
				if(initStars == 2){
					star1.registerEntityModifier(spm1);
					star2.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.5f, 0, 0), spm2));
					handler.postDelayed(new Runnable() {

						@Override
						public void run() {
							wonScene.registerTouchArea(nexta);
							wonScene.registerTouchArea(menua);
							wonScene.registerTouchArea(retrya);

						}
					}, 800);

				}if(initStars >= 3){
					star1.registerEntityModifier(spm1);
					star2.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.5f, 0, 0), spm2));
					star3.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(1, 0, 0), spm3));
					handler.postDelayed(new Runnable() {

						@Override
						public void run() {
							wonScene.registerTouchArea(nexta);
							wonScene.registerTouchArea(menua);
							wonScene.registerTouchArea(retrya);

						}
					}, 1200);
				}
			}

		});
		wonScene.registerEntityModifier(sceneam);
	}


//	protected void createpauseScene() {
//		this.pauseScene = new MenuScene(this.camera);
//		
//		//resume
//		final SpriteMenuItem resumeMenuItem = new SpriteMenuItem(0, this.resumeTR, this.getVertexBufferObjectManager());
//		resumeMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
//		this.pauseScene.addMenuItem(resumeMenuItem);
//		
//		//restart
//		final SpriteMenuItem resetMenuItem = new SpriteMenuItem(1, this.restartTR, this.getVertexBufferObjectManager());
//		resetMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
//		this.pauseScene.addMenuItem(resetMenuItem);
//		
//		//main menu
//		final SpriteMenuItem mainMenuItem = new SpriteMenuItem(2, this.menuquitTR, this.getVertexBufferObjectManager());
//		mainMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
//		this.pauseScene.addMenuItem(mainMenuItem);
//		
//		
//		//quit
//		quitMenuItem = new SpriteMenuItem(3, this.menuquitTR, this.getVertexBufferObjectManager());
//		quitMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
//		this.pauseScene.addMenuItem(quitMenuItem);
//		
//		
//		
//		this.pauseScene.buildAnimations();
//
//		this.pauseScene.setBackgroundEnabled(false);
//
//		this.pauseScene.setOnMenuItemClickListener(this);
//	}
	
	
	public void loadgame(final String levelName){


		fileexist = true;
		loadPropertiesfromAsset(levelName);
		
		
		powerbg = new Sprite(0, backgroud.getHeight()-173, powerbgTR, getVertexBufferObjectManager());
		backgroud.attachChild(powerbg);
		
		for (Point p : PointList) {
			
			if(p.hasCharges || p.hasFlame || p.hasGrenade || p.hasStar || p.hasThunder){
				p.collectable = true;
			}
			
			scene.attachChild(p);
			scene.registerTouchArea(p);
			decorPointRays(p);
			
		}
		for (Stick s : StickList) {
			
			scene.attachChild(s);
//			scene.attachChild(stickParticles(s.p1, s.p2));
//			effectsStickList.add(stickParticles(s));
			decorate(s);
					
		}
		
		//// to avoid the unsolved problem faced in loading a state
		Stick avoidUndoProblem = new Stick (10,10,12,-12);
		avoidUndoProblem.strength = 0 ;
		StickList.add(avoidUndoProblem);
		
		for(Point p : PointList){
			decorPoint(p);
			if(p.collectable){
				p.c = new collect(p);
				scene.attachChild(p.c);
				
			}
		}
		
		
		
		initStickCount  = StickList.size();
		
		////adding stars
		initStars = initProps.InitStars;
		
		for(int i = 0 ; i < maxStars ; i ++){
			if(i<initStars){
				starList.add(new Star(i,1));
			}
			else{
				starList.add(new Star(i,0));
			}
		}
		
		starback = new Rectangle(0, 0, 800, 90, getVertexBufferObjectManager());
		starback.setColor(Color.BLACK);
		starback.setAlpha(0.5f);
		scene.attachChild(starback);
		
		for(Star s : starList){
			s.setScale(1f);
			starback.attachChild(s);
			
			
		}
		if(yStretched){
			float h = (1280 - 1280*scene.getScaleY())/ 2;
			starback.setY(starback.getY()-h);
		}
		
		//Initialize player if a must start is given
		if (mustStart()) {
			player = new Player(getmustStart());
			mEngine.getScene().attachChild(player);
			player.currentPos = getmustStart();
			playerPlaced = true;
			onaPoint = true;
		}
		//add pause and restrt buttons
		levelPause = new Sprite(700, 0, levelpauseTR, getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()){
					menuaniminprocess = true;
					this.setScale(1.1f);
					tapm.play();
					handler.postDelayed(new Runnable() {

						@Override
						public void run() {
							scene.setChildScene(pauseScene, false, true, true);
							bgmusic.pause();
							if(hammerm.isPlaying()){
								hammerplayingonpause = true;
								hammerm.pause();
							}
							if(eatm.isPlaying()){
								eatplaingonpause = true;
								eatm.pause();
							}

							resume.registerEntityModifier(new SequenceEntityModifier(new ParallelEntityModifier(new AlphaModifier(0.3f, 0, 1), new MoveXModifier(0.3f,resume.getX(), resume.getX() - 800,EaseExponentialOut.getInstance()))));
							restart.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.1f, 0, 0),new ParallelEntityModifier(new AlphaModifier(0.3f, 0, 1), new MoveXModifier(0.3f,restart.getX(), restart.getX()-800,EaseExponentialOut.getInstance()))));
							mainmenu.registerEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.2f, 0, 0),new ParallelEntityModifier(new AlphaModifier(0.3f, 0, 1), new MoveXModifier(0.3f,mainmenu.getX(), mainmenu.getX() - 800,EaseExponentialOut.getInstance()))));
							
							SequenceEntityModifier seq = new SequenceEntityModifier(new AlphaModifier(0.3f, 0, 0),new ParallelEntityModifier(new AlphaModifier(0.3f, 0, 1), new MoveXModifier(0.3f,menuquit.getX(), menuquit.getX()-800,EaseExponentialOut.getInstance())));
							seq.addModifierListener(new IModifierListener<IEntity>() {
								
								@Override
								public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
									// TODO Auto-generated method stub
									menuaniminprocess = false;
								}
							});
							
							
							menuquit.registerEntityModifier(seq);
							menubackdarker.registerEntityModifier(new AlphaModifier(0.2f,0, 0.45f));
							setScale(1);

						}
					}, 100);

//					handler.postDelayed(new Runnable() {
//
//						@Override
//						public void run() {
//							menuaniminprocess = false;
//						}
//					}, 1100);
					return true;
				}
				else{
					return false;				
					}

			}
		};
		levelrestart = new Sprite(600, 0, levelrestartTR, getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()){
					this.setScale(1.1f);
					tapm.play();
					
					handler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							Intent intent = getIntent();
							finish();
							startActivity(intent);
						}
					}, 100);
				
				}
				return true;
			}
		};
		
		starback.attachChild(levelPause);
		starback.attachChild(levelrestart);
		
		scene.registerTouchArea(levelPause);
		scene.registerTouchArea(levelrestart);
		
		//add power buttons to the scene
		for(int i = 0; i <5 ; i ++){
			Power tmp;
			if(i == 3){
				tmp = new Power(i)
				{
					@Override
					public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX,float pTouchAreaLocalY) {
						if(pSceneTouchEvent.isActionDown() && availability>0 && onaPoint){
							//////
							
							
							if(functioning){
								functioning= false;
								powerOn = false;
								if(!flamePoints.isEmpty()){
									for(Point p : flamePoints){
										p.flamed = false;
										p.flameStart = false;
									}
									flamePoints.clear();
									for(Stick s : flameSticks){
										for(final egg e : s.eggList){
											final egg tmp = new egg(0, 0)	;
											tmp.strength = -5 ;
											tmp.animate(new long[]{20}, new int[]{8},false);
											e.attachChild(tmp);
											AlphaModifier ammm = new AlphaModifier((float) 1.5, 1, 0);
											ammm.addModifierListener(new IModifierListener<IEntity>() {
												
												@Override
												public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
													// TODO Auto-generated method stub
													
												}
												
												@Override
												public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
													// TODO Auto-generated method stub
													runOnUpdateThread(new Runnable() {
														
														@Override
														public void run() {
															// TODO Auto-generated method stub
															e.detachChild(tmp);
														}
													});
												}
											});
											tmp.registerEntityModifier(ammm);
											e.flamed = false;
										}
									}
									flameSticks.clear();
									availability--;
								}
//								this.animate(new long[]{0,100000,0}, new int[]{0,1,2}, true);
								this.setCurrentTileIndex(1);
							}
							else{
								
								
								
								for(Power p : powers){
									
									p.functioning = false;
									
									if(p.availability == 0){
//										p.animate(new long[]{100000,0,0}, new int[]{0,1,2}, true);
										p.setCurrentTileIndex(0);
									}
									else{
//										p.animate(new long[]{0,100000,0}, new int[]{0,1,2}, true);
										p.setCurrentTileIndex(1);
										
									}
								}
								
								functioning= true;
								powerOn = true;
								
								flamePoints.add(player.currentPos);
								player.currentPos.flameStart = true;
								player.currentPos.flamed = false;
								
//								this.animate(new long[]{0,0,10000}, new int[]{0,1,2}, true);
								this.setCurrentTileIndex(2);
								
							}
						}
						
						if(availability== 0 && onaPoint && pSceneTouchEvent.isActionDown() && initStars>=cost && buyable){
							this.availability++;
							initStars -= cost;
						}
						
						
						return true;
						
						
					}
				};
			}
			else{
				tmp = new Power(i);
			}
			
			powers.add(tmp);
			mEngine.getScene().attachChild(tmp);
			tmp.attachChild(tmp.available);
			mEngine.getScene().registerTouchArea(tmp);
			mEngine.getScene().attachChild(tmp.buttonStrip);
			
			if(yStretched){
				float h = (1280 - 1280*scene.getScaleY())/ 2;
				tmp.setY(tmp.getY()+h);
			}
			
			
//			backgroud.attachChild(tmp);
//			backgroud.attachChild(tmp.available);
//			mEngine.getScene().registerTouchArea(tmp);
//			backgroud.attachChild(tmp.buttonStrip);	
		}
		
		for(Power p : powers){
			for(powerStar ps : p.stars){
				p.attachChild(ps);
				ps.setScale(ps.getScaleX()*1.5f);
			}
		}
		
		
		sceneCover.setColor(Color.WHITE);
		scene.attachChild(sceneCover);
		final AlphaModifier scenebackam = new AlphaModifier(1, 1, 0);
		sceneCover.registerEntityModifier(scenebackam);
		
		
		//dialog box
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						
					
						if(levelName.equals("1.start")){
							Level.this.showDialog(2);
						}
						if(levelName.equals("edit3.1")){
							Level.this.showDialog(5);
						}
						if(levelName.equals("2.start5")){
							Level.this.showDialog(7);
						}
						if(levelName.equals("thunder.s")){
							Level.this.showDialog(10);
						}
						if(levelName.equals("3.start")){
							Level.this.showDialog(9);
						}
						
						if(levelName.equals("grenade")){
							Level.this.showDialog(21);
						}
						if(levelName.equals("30.charges")){
							Level.this.showDialog(15);
						}
						if(levelName.equals("35.flame2")){
							Level.this.showDialog(18);
						}
						
						if(levelName.equals("40")){
							Level.this.showDialog(13);
						}
						if(levelName.equals("2")){
							Level.this.showDialog(6);
						}
						
						if(levelName.equals("h3")){
							Level.this.showDialog(25);
						}

					}
				});
				
			}
		}, 4000);
			
		

	
	}
	
	
	

	@Override
	protected Dialog onCreateDialog(int id) {
		
		Context mContext = Level.this;
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
		
		cb.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				dialog.dismiss();
				return false;
			}
		});
		
		
		switch (id) {
		case 1:
			final EditText ipEditText = new EditText(this);

			return new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_info)
			.setTitle("Enter file name")
			.setView(ipEditText)
			.setCancelable(false)

			.setPositiveButton("Open", new OnClickListener() {
				@Override
				public void onClick(final DialogInterface pDialog,
						final int pWhich) {
					fn = ipEditText.getText().toString();
					
					File sdCard = Environment
							.getExternalStorageDirectory();
					File dir = new File(sdCard.getAbsolutePath()
							+ "/dir1/dir2");
					dir.mkdirs();
					file = new File(dir, fn + "-aPoint");

					
					fileexist = true;
					loadProperties(fn);
					for (Point p : PointList) {
						
						if(p.hasCharges || p.hasFlame || p.hasGrenade || p.hasStar || p.hasThunder){
							p.collectable = true;
						}
						
						scene.attachChild(p);
						scene.registerTouchArea(p);
						decorPointRays(p);
						
					}
					for (Stick s : StickList) {
						
						scene.attachChild(s);
//						scene.attachChild(stickParticles(s.p1, s.p2));
//						effectsStickList.add(stickParticles(s));
						decorate(s);
								
					}
					
					//// to avoid the unsolved problem faced in loading a state
					Stick avoidUndoProblem = new Stick (10,10,12,-12);
					avoidUndoProblem.strength = 0 ;
					StickList.add(avoidUndoProblem);
					
					for(Point p : PointList){
						decorPoint(p);
						if(p.collectable){
							p.c = new collect(p);
							scene.attachChild(p.c);
						}
					}
					
					
					
					initStickCount  = StickList.size();
					
					////adding stars
					initStars = initProps.InitStars;
					
					for(int i = 0 ; i < maxStars ; i ++){
						if(i<initStars){
							starList.add(new Star(i,1));
						}
						else{
							starList.add(new Star(i,0));
						}
					}
					for(Star s : starList){
						s.setScale(0.6f);
						scene.attachChild(s);
					}
					
					
					//Initialize player if a must start is given
					if (mustStart()) {
						player = new Player(getmustStart());
						mEngine.getScene().attachChild(player);
						player.currentPos = getmustStart();
						playerPlaced = true;
						onaPoint = true;
					}
					
					
					//add power buttons to the scene
					for(int i = 0; i <5 ; i ++){
						Power tmp;
						if(i == 3){
							tmp = new Power(i)
							{
								@Override
								public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX,float pTouchAreaLocalY) {
									if(pSceneTouchEvent.isActionDown() && availability>0 && onaPoint){
										//////
										
										
										if(functioning){
											functioning= false;
											powerOn = false;
											if(!flamePoints.isEmpty()){
												for(Point p : flamePoints){
													p.flamed = false;
													p.flameStart = false;
												}
												flamePoints.clear();
												for(Stick s : flameSticks){
													for(final egg e : s.eggList){
														final egg tmp = new egg(0, 0)	;
														tmp.strength = -5 ;
														tmp.animate(new long[]{20}, new int[]{8},false);
														e.attachChild(tmp);
														AlphaModifier ammm = new AlphaModifier((float) 1.5, 1, 0);
														ammm.addModifierListener(new IModifierListener<IEntity>() {
															
															@Override
															public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
																// TODO Auto-generated method stub
																
															}
															
															@Override
															public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
																// TODO Auto-generated method stub
																runOnUpdateThread(new Runnable() {
																	
																	@Override
																	public void run() {
																		// TODO Auto-generated method stub
																		e.detachChild(tmp);
																	}
																});
															}
														});
														tmp.registerEntityModifier(ammm);
														e.flamed = false;
													}
												}
												flameSticks.clear();
												availability--;
											}
//											this.animate(new long[]{0,100000,0}, new int[]{0,1,2}, true);
											this.setCurrentTileIndex(1);
										}
										else{
											
											
											
											for(Power p : powers){
												
												p.functioning = false;
												
												if(p.availability == 0){
//													p.animate(new long[]{100000,0,0}, new int[]{0,1,2}, true);
													p.setCurrentTileIndex(0);
												}
												else{
//													p.animate(new long[]{0,100000,0}, new int[]{0,1,2}, true);
													p.setCurrentTileIndex(1);
													
												}
											}
											
											functioning= true;
											powerOn = true;
											
											flamePoints.add(player.currentPos);
											player.currentPos.flameStart = true;
											player.currentPos.flamed = true;
											
//											this.animate(new long[]{0,0,10000}, new int[]{0,1,2}, true);
											this.setCurrentTileIndex(2);
											
										}
									}
									
									if(availability== 0 && onaPoint && pSceneTouchEvent.isActionDown() && initStars>=cost && buyable){
										this.availability++;
										initStars -= cost;
									}
									
									
									return true;
									
									
								}
							};
						}
						else{
							tmp = new Power(i);
						}
						
						powers.add(tmp);
						mEngine.getScene().attachChild(tmp);
						mEngine.getScene().attachChild(tmp.available);
						mEngine.getScene().registerTouchArea(tmp);
						mEngine.getScene().attachChild(tmp.buttonStrip);
					}
					
					for(Power p : powers){
						for(powerStar ps : p.stars){
							scene.attachChild(ps);
						}
					}
					
				
						
					

				}
			})
			.setNegativeButton(android.R.string.cancel,
					new OnClickListener() {
				@Override
				public void onClick(
						final DialogInterface pDialog,
						final int pWhich) {

				}
			}).create();
			
			
			
		case 2:
			
			text.setText("+ Strength = 1 \n+ These eggs are weak. Troopie can eat these eggs and destroy them. Eat all eggs to complete level");
			image.setImageResource(R.drawable.egg);
			
			
			
			ib.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					dialog.dismiss();
					showDialog(3);
					return false;
				}
			});
			
			return dialog;


		
		case 3:

			text.setText("+ Eggs are laid in 'Egg paths'. Troopie can eat eggs while travelling on egg paths. To travel on an egg path, simply tap on a nearby point to Troopie's currruent position");
			image.setImageResource(R.drawable.eat1);
			
			ib.setAlpha(0);
			
			return dialog;
			
			
		/// performance
		case 4:
			
			cb.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					dialog.dismiss();
					scene.setChildScene(wonScene, false, true, true);
					return false;
				}
			});
			
			text.setText("If you are having performance issues or unexpected behaviour in the game ,try turning OFF the 'particles' from OPTIONS sub menu in MAIN MENU");
			image.setImageResource(R.drawable.settings);
			ib.setAlpha(0);
			return dialog;
			
		// star 
		case 5:

			text.setText("+ In each level, try to collect all 3 stars!");
			image.setImageResource(R.drawable.star);

			ib.setAlpha(0);

			return dialog;
			
			// place troopie
		case 6:

			text.setText("+ Tap on a point to place Troopie. Select a suitable position to place Troopie so that he can eat all the eggs!!");
			image.setImageResource(R.drawable.tap1);

			ib.setAlpha(0);

			return dialog;
			
			
			// strong egg
		case 7:

			text.setText("+ Strength = 2 \n+ Troopie cannot eat these eggs at once, because these eggs are strong. So first Troopie will hammer the eggs and make them weak");
			image.setImageResource(R.drawable.stronegg);

			ib.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					dialog.dismiss();
					showDialog(8);
					return false;
				}
			});

			return dialog;
			
		case 8:

			text.setText("+ Troopie will hammer the eggs if they are strong. After they are hammered, Troopie can eat those eggs next time he travels on the same path ");
			image.setImageResource(R.drawable.strongegg2);

			ib.setAlpha(0);

			return dialog;
			
		// poisonus eggs	
		case 9:

			text.setText("+ Strength = 3 \n+ These eggs have a poisonous cover. Troopie can't eat them as they are. So Troopie should use a power either to destroy them or to remove their poisonous cover");
			image.setImageResource(R.drawable.poisonousegg);
			
			ib.setAlpha(0);
			
			return dialog;
			
			
			// thunder
		case 10:

			text.setText("+ Damage = -3 \n+ Thunder can destroy eggs of any type!");
			image.setImageResource(R.drawable.thunder);

			ib.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					dialog.dismiss();
					showDialog(11);
					return false;
				}
			});

			return dialog;
			
		case 11:

			text.setText("+ To activate a power, simply tap on the power buttons. When they are active, a red box will appear around the power");
			image.setImageResource(R.drawable.thunder0);

			ib.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					dialog.dismiss();
					showDialog(12);
					return false;
				}
			});

			return dialog;
			
		case 12:
			text.setText("+ After the Thunder is acvivated, tap on an Egg Path to make thunder and destroy eggs in it");
			image.setImageResource(R.drawable.thunder1);

			ib.setAlpha(0);

			return dialog;
			
			
			
			///bridge
		case 13:

			text.setText("+ Bridge helps to connect between 2 points which are not already connected. Bridges cannot be destroyed by any power");
			image.setImageResource(R.drawable.bridge);

			ib.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					dialog.dismiss();
					showDialog(14);
					return false;
				}
			});

			return dialog;
			
			
		case 14:
			text.setText("+ Activate Bridge and then tap on a point that is not connected with Troopie's current position to build a bridge ");
			image.setImageResource(R.drawable.bridge1);

			ib.setAlpha(0);

			return dialog;
			
			
			
			
			// charges
		case 15:

			text.setText("+ Damage = -3 \n+ Explosives can destroy eggs of any type. Effective at junctions that connect many egg paths. Once placed in a junction, it will destroy all the egg paths connected to the junction");
			image.setImageResource(R.drawable.explosives);

			ib.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					dialog.dismiss();
					showDialog(16);
					return false;
				}
			});

			return dialog;
			
		case 16:

			text.setText("+ To use explosives tap on the icon in 'powers' menu below. Then Troopie will place explosives in his current position");
			image.setImageResource(R.drawable.charge1);

			ib.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					dialog.dismiss();
					showDialog(17);
					return false;
				}
			});

			return dialog;
			
		case 17:
			text.setText("+ Once the explosives are placed Troopie has 5 seconds to go to another point. If Troopie stays, he will die from the explosion!!");
			image.setImageResource(R.drawable.charge2);

			ib.setAlpha(0);

			return dialog;
			

			// flame
		case 18:

			text.setText("+ Damage = -3 \n+ IF you can create a 'flame loop' every egg inside that loop will be burnt");
			image.setImageResource(R.drawable.flame);

			ib.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					dialog.dismiss();
					showDialog(19);
					return false;
				}
			});

			return dialog;

		case 19:

			text.setText("+ First activate flame. Then go from point to point. Troopie will spray some gunpowder while he travels.");
			image.setImageResource(R.drawable.flame1);

			ib.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					dialog.dismiss();
					showDialog(20);
					return false;
				}
			});

			return dialog;

		case 20:
			text.setText("+ Return to the first point that flame was activated and complete the loop. Everything inside the loop will burn!! Remember you can't travel over the eggs that are already Gun Powdered.");
			image.setImageResource(R.drawable.flame2);

			ib.setAlpha(0);

			return dialog;
			
			// grenade
		case 21:

			text.setText("+ Damage = -1 \n+ Reduces egg strength by 1. Means Poisonous eggs will be turned in to Strong eggs, Strong eggs to Weak eggs and Weak eggs will be destroyed");
			image.setImageResource(R.drawable.grenade);

			ib.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					dialog.dismiss();
					showDialog(22);
					return false;
				}
			});

			return dialog;

		case 22:
			text.setText("+ To use grenade, activate it and tap on a point connected to Troopie's currrent position. Effective to be thrown at at junctions!");
			image.setImageResource(R.drawable.grenade1);

			ib.setAlpha(0);

			return dialog;
			
			
		case 23:
			tips.setText("Congratulations!!");
			text.setText("+ You have successfully completed Area "+currentArea+". Area "+(currentArea+1) + " unlocked."  );
			image.setImageResource(R.drawable.newarea);
			cb.setAlpha(0);
			
			
			ib.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					dialog.dismiss();
					
					Intent i = new Intent(Level.this, MainMenu3.class);
					finish();
					startActivity(i);
					
					
					return false;
				}
			});
			
			cb.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					dialog.dismiss();
					
					Intent i = new Intent(Level.this, MainMenu3.class);
					finish();
					startActivity(i);
					
					return false;
				}
			});
			return dialog;
			
		case 24:
			tips.setText("Congratulations!!");
			text.setText("+ You have successfully completed Area "+currentArea+". More levels coming soon! Keep in Touch"  );
			image.setImageResource(R.drawable.newarea);
			cb.setAlpha(0);
			
			
			ib.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					dialog.dismiss();
					
					Intent i = new Intent(Level.this, MainMenu3.class);
					finish();
					startActivity(i);
					
					
					return false;
				}
			});
			
			
			
			cb.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					dialog.dismiss();
					
					Intent i = new Intent(Level.this, MainMenu3.class);
					finish();
					startActivity(i);
					
					return false;
				}
			});
			
			return dialog;
			
		case 25:
			text.setText("+ In some levels you can unlock a bonus power with the collected stas. Be careful to choose the suitable power because you can unlock only one power in a level. \n Different powers cost different amount of stars\n+ Thunder - 1 Star\n+ Explosives - 2 Stars \n+ Grenade - 2 Stars \n+ Bridge - 3 Strars \n+ Flame - 3 Strars \n\nIf the powers can be unlocked it will be indigcated in Yellow Stars");
			image.setImageResource(R.drawable.buyable);

			ib.setAlpha(0);

			return dialog;
			
			

			

		default:
			return null;
		}
	}
	public byte[] readFromAsetFile(String name) throws IOException {
		
		

		InputStream is;
		
		is = getResources().getAssets().open(name);
		
		
		
		byte[] ba = new byte[is.available()];
		
		is.read(ba);
		is.close();
		
		Log.d("ans", ba + "");
		return ba;

	
	}

	public byte[] readFromFile(String name) {
		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File(sdCard.getAbsolutePath() + "/dir1/dir2");
		dir.mkdirs();
		File file = new File(dir, name);

		if (file.exists()) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			byte[] ba = new byte[(int) file.length()];
			try {
				fis.read(ba);
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ba;
		} else {
			Toast.makeText(Level.this, "File not found! try again",
					Toast.LENGTH_LONG).show();
			// runOnUiThread(new Runnable() {
			// @Override
			// public void run() {
			// Level.this.showDialog(1);
			// }
			// });
			return null;
		}
	}
	
	public void loadPropertiesfromAsset(String filename) {
		byte[] aPointL = null;
		byte[] aStickL = null;
		byte[] aInitL = null;
		
		try {
			aPointL = readFromAsetFile(filename + "-Point");
			aStickL = readFromAsetFile(filename + "-Stick");
			aInitL = readFromAsetFile(filename + "-init");
		} catch (IOException e) {
	
			e.printStackTrace();
		}
		
		
		aPointList = (ArrayList<aPoint>) deserializeObject(aPointL);
		aStickList = (ArrayList<aStick>) deserializeObject(aStickL);
		initProps = (InitProperties) deserializeObject(aInitL);
		
		
		powerBuyable = new boolean[]{initProps.ThunderBuyable,initProps.ChargesBuyable,initProps.grenadeBuyable,initProps.FlameBuyable,false};
		initPower = new int[]{initProps.InitThunde,initProps.InitCharges,initProps.InitGrenade,initProps.InitFlame,initProps.InitBridge};
		
		
		
		
		
		for (aPoint p : aPointList) {
			// here ap in Point and p is aPoint
			Point ap = new Point(p.x, p.y);
			ap.ID = p.ID;

			ap.centerX = p.centerX;
			ap.centerY = p.centerY;

			ap.visitable = p.visitable;
			ap.startable = p.startable;
			ap.visited = p.visited;
			ap.mustStart = p.mustStart;

			ap.hasCharges = p.hasCharges;
			ap.hasFlame = p.hasFlame;
			ap.hasGrenade = p.hasGrenade;
			ap.hasStar = p.hasStar;
			ap.hasThunder = p.hasThunder;

			PointList.add(ap);
		}
		for (int i = 0; i < aPointList.size(); i++) {
			for (int j : aPointList.get(i).connectedaPoints) {
				for (Point p : PointList) {
					if (p.ID == j) {
						PointList.get(i).connectedPoints.add(p);
					}
				}
			}
		}

		for (aStick as : aStickList) {
			Point tp1 = null;
			Point tp2 = null;
			for (Point p : PointList) {
				if (p.ID == as.p1ID) {
					tp1 = p;
				}
				if (p.ID == as.p2ID) {
					tp2 = p;
				}
			}
			Stick s = new Stick(tp1, tp2);
			s.ID = as.ID;
			s.strength = as.strength;
			StickList.add(s);
		}

		for (int i = 0; i < aPointList.size(); i++) {
			for (int j : aPointList.get(i).connectedaSticks) {
				for (Stick s : StickList) {
					if (j == s.ID) {
						PointList.get(i).connectedSticks.add(s);
					}
				}
			}
		}


	}
	

	public void loadProperties(String filename) {
		byte[] aPointL = readFromFile(filename + "-Point");
		byte[] aStickL = readFromFile(filename + "-Stick");
		byte[] aInitL = readFromFile(filename + "-init");
		
		aPointList = (ArrayList<aPoint>) deserializeObject(aPointL);
		aStickList = (ArrayList<aStick>) deserializeObject(aStickL);
		initProps = (InitProperties) deserializeObject(aInitL);
		
		
		powerBuyable = new boolean[]{initProps.ThunderBuyable,initProps.ChargesBuyable,initProps.grenadeBuyable,initProps.FlameBuyable,false};
		initPower = new int[]{initProps.InitThunde,initProps.InitCharges,initProps.InitGrenade,initProps.InitFlame,initProps.InitBridge};
		
		
		
		
		
		for (aPoint p : aPointList) {
			// here ap in Point and p is aPoint
			Point ap = new Point(p.x, p.y);
			ap.ID = p.ID;

			ap.centerX = p.centerX;
			ap.centerY = p.centerY;

			ap.visitable = p.visitable;
			ap.startable = p.startable;
			ap.visited = p.visited;
			ap.mustStart = p.mustStart;

			ap.hasCharges = p.hasCharges;
			ap.hasFlame = p.hasFlame;
			ap.hasGrenade = p.hasGrenade;
			ap.hasStar = p.hasStar;
			ap.hasThunder = p.hasThunder;

			PointList.add(ap);
		}
		for (int i = 0; i < aPointList.size(); i++) {
			for (int j : aPointList.get(i).connectedaPoints) {
				for (Point p : PointList) {
					if (p.ID == j) {
						PointList.get(i).connectedPoints.add(p);
					}
				}
			}
		}

		for (aStick as : aStickList) {
			Point tp1 = null;
			Point tp2 = null;
			for (Point p : PointList) {
				if (p.ID == as.p1ID) {
					tp1 = p;
				}
				if (p.ID == as.p2ID) {
					tp2 = p;
				}
			}
			Stick s = new Stick(tp1, tp2);
			s.ID = as.ID;
			s.strength = as.strength;
			StickList.add(s);
		}

		for (int i = 0; i < aPointList.size(); i++) {
			for (int j : aPointList.get(i).connectedaSticks) {
				for (Stick s : StickList) {
					if (j == s.ID) {
						PointList.get(i).connectedSticks.add(s);
					}
				}
			}
		}


	}

	public Object deserializeObject(byte[] b) {
		try {
			ObjectInputStream in = new ObjectInputStream(
					new ByteArrayInputStream(b));
			Object object = in.readObject();
			in.close();

			return object;
		} catch (ClassNotFoundException cnfe) {
			

			return null;
		} catch (IOException ioe) {
			
			;

			return null;
		}
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		mEngine.registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() 
		{
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) 
			{
				mEngine.unregisterUpdateHandler(pTimerHandler);
				try {
					loadGameResources();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				loadGameScene();         
				
				mEngine.setScene(scene);
				bgmusic.play();
				bgmusic.setVolume(0.3f);
				bgmusic.setLooping(true);
				
				
				////////
//				final AlphaModifier scenebackam = new AlphaModifier(1, 1, 0);
//				scenebackam.addModifierListener(new IModifierListener<IEntity>() {
//					
//					@Override
//					public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
//						// TODO Auto-generated method stub
//						
//					}
//					
//					@Override
//					public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
//						
//						bgmusic.play();
//						bgmusic.setLooping(true);
//						// TODO Auto-generated method stub
////						runOnUpdateThread(new Runnable() {
////							
////							@Override
////							public void run() {
////								// TODO Auto-generated method stub
////								scene.detachChild(sceneCover);
////								
////								
////							}
////						});
//					}
//				});
				
				
				
//				MoveXModifier mxm = new MoveXModifier(1.5f, loading.getX(), loading.getX()-300);
//				mxm.addModifierListener(new IModifierListener<IEntity>() {
//					
//					@Override
//					public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
//						// TODO Auto-generated method stub
//						
//					}
//					
//					@Override
//					public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
//						// TODO Auto-generated method stub
//						mEngine.setScene(scene);
//						bgmusic.play();
//						bgmusic.setLooping(true);
//
//					}
//				});
//				loading.registerEntityModifier(mxm);
			}
			

			
		}));
		
		pOnPopulateSceneCallback.onPopulateSceneFinished();
		
	}

	@Override
	public synchronized void onResumeGame() {
		// TODO Auto-generated method stub
		super.onResumeGame();
		
	}

	@Override
	public synchronized void onPauseGame() {
		// TODO Auto-generated method stub
		
		
		super.onPauseGame();
		
		
		
		mIsPausedBefore = true ;
		if(bgmusic != null){
			bgmusic.pause();
		}
		if(eatm != null){
			if(eatm.isPlaying()){
				eatplaingonpause = true;
				eatm.pause();
				
			}
		}
		if(hammerm != null){
			if(hammerm.isPlaying()){
				hammerplayingonpause = true;
				hammerm.pause();
				
			}
		}
		
	}
	
	
	
	

}
