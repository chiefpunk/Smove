package gruv.game.smove;

import gruv.game.smove.MyWorld.WorldListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;

public class GameScreen extends MyScreen{
	
	public static final float RAMKA_SMALL_WIDTH = 152f;
	public static final float RAMKA_SMALL_HEIGHT = 152f;
	
	public static final float RAMKA_LARGE_WIDTH = 408f;
	public static final float RAMKA_LARGE_HEIGHT = 152f;
	
	public static final float SMALL_SIZE_COEF = 0.96f;//0.98f
   
	private static final float TEXT_CENTER_X = Smove.GAME_WIDTH * 0.5f;
    private static final float TEXT_CENTER_Y_UP = Smove.GAME_HEIGHT * 0.65f;
	private static final float TEXT_CENTER_Y = Smove.GAME_HEIGHT * 0.50f;
	
	private static final float GAME_OVER_CENTER_Y = Smove.GAME_HEIGHT * 0.5f;//0.1125f
	
	private static final float GAME_OVER_CENTER_Y0 = Smove.GAME_HEIGHT * 0.52f;//0.63f
	private static final float GAME_OVER_CENTER_Y1 = Smove.GAME_HEIGHT * 0.48f;//0.59f
		
	private static final float GREY_X = Smove.GAME_WIDTH * 0.5f;
    private static final float GREY_Y = Smove.GAME_HEIGHT * 0.95f;
    private static final float GREY_W = Smove.GAME_WIDTH;
    private static final float GREY_H = Smove.GAME_HEIGHT * 0.1f;

	public static final float BTN_SIZE_X = Smove.GAME_WIDTH * 0.136f;
	public static final float BTN_SIZE_Y = BTN_SIZE_X ;
	
	private static final float EXIT_CENTER_X = Smove.GAME_WIDTH * 0.100f;//0.100f;
	private static final float EXIT_CENTER_Y = Smove.GAME_HEIGHT * 0.055f;	
	
	private static final float SOUND_CENTER_X = Smove.GAME_WIDTH * 0.300f;//0.260f
	private static final float SOUND_CENTER_Y = EXIT_CENTER_Y;
	
	private static final float CHANGE_THEME_CENTER_X = Smove.GAME_WIDTH * 0.5f;//FollowLine.GAME_WIDTH - DELETE_HISTORY_CENTER_X;
	private static final float CHANGE_THEME_CENTER_Y = EXIT_CENTER_Y;
	
	private static final float NEW_GAME_CENTER_X = Smove.GAME_WIDTH - SOUND_CENTER_X;
	private static final float NEW_GAME_CENTER_Y = EXIT_CENTER_Y;
	
	private static final float HELP_CENTER_X = Smove.GAME_WIDTH - EXIT_CENTER_X;
	private static final float HELP_CENTER_Y = EXIT_CENTER_Y;		
	
	private static final float HELP_TEXT_CENTER_X = Smove.GAME_WIDTH * 0.5f;
	private static final float HELP_TEXT_CENTER_Y = Smove.GAME_HEIGHT * 0.25f;//0.32f
	
	private static final float TEXT_X_1 = Smove.GAME_WIDTH * 0.22f;//0.23f
	private static final float TEXT_X_2 = (Smove.GAME_WIDTH * 0.78f);//0.5f
	private static final float TEXT_X_CENTER = (Smove.GAME_WIDTH * 0.5f);//0.5f
	
	private static final float TEXT_Y_1 = Smove.GAME_HEIGHT * 0.88f;//EXIT_CENTER_Y;
	private static final float TEXT_Y_2 = Smove.GAME_HEIGHT * 0.85f;
	//---------------------------------------------------------------------
	private static final float AD_CENTER_Y = Smove.GAME_HEIGHT  * 0.9617f;//0.966f
	
	private static final float MY_AD_CENTER_X = Smove.GAME_WIDTH  * 0.5f;//GameScreen.ZigZag2.GAME_WIDTH  * 0.5f;
	private static final float MY_AD_CENTER_Y = AD_CENTER_Y;
	//---------------------------------------------------------------------   	
    public MyWorld world;
    private WorldListener worldListener;
    private WorldRenderer renderer; 
     
    private Rectangle newPlayBounds;
    private Rectangle resumeBounds;     
      
    private Rectangle exitBounds;
    private Rectangle newGameBounds;
    private Rectangle helpBounds;    
    private Rectangle changeThemeBounds;
    private Rectangle soundBounds;
    
    private Rectangle myAdBounds;
    
    private static int NO_PUSHED_BTN = (-1);
    
    private static int EXIT_BTN = 0;
    private static int NEW_GAME_BTN = 1;
    private static int HELP_BTN = 2;    
    private static int CHANGE_THEME_BTN = 3;
    private static int SOUND_BTN = 4;     
    private static int RESUME_BTN = 5;
    private static int START_NEW_GAME_BTN = 6;			
    
    private int pushedBtn = NO_PUSHED_BTN;
    
    private boolean adIsShowing = false;
    
    private Volume volume = null;
    private boolean drawVolume = false;
    
    private static final float INIT_STATE_TIMEOUT = 0.1f;//0.2f;
    private static float updateNextLevelTimeOut = -10f;
    
    public GameScreen(Smove game) {
    	this.game = game;
    	
    	Assets.load(game);
      
        worldListener = new WorldListener() {
            @Override
            public void gameOver() {            
                Assets.playSound(Assets.gameOverSound);
            } 
            @Override
            public void bonus() {            
                Assets.playSound(Assets.bonusSound);
            }
            @Override
            public void nextLevel() {            
                Assets.playSound(Assets.nextLevelSound);
            }
            @Override
            public void clickSound(){            
                Assets.playSound(Assets.clickSound);
            }
            
            @Override
            public void touchDownSound(){            
                Assets.playSound(Assets.touchDownSound);
            }
            @Override
            public void touchUpSound(){            
                Assets.playSound(Assets.touchUpSound);
            }
        };
                    
        world = new MyWorld(worldListener);
        renderer = new WorldRenderer(/*shapeRenderer, */batcher, world);     	
        
        exitBounds = new Rectangle(EXIT_CENTER_X - BTN_SIZE_X / 2, EXIT_CENTER_Y - BTN_SIZE_Y / 2, BTN_SIZE_X, BTN_SIZE_Y);
        newGameBounds = new Rectangle(NEW_GAME_CENTER_X - BTN_SIZE_X / 2, NEW_GAME_CENTER_Y - BTN_SIZE_Y / 2, BTN_SIZE_X, BTN_SIZE_Y);
        helpBounds = new Rectangle(HELP_CENTER_X - BTN_SIZE_X / 2, HELP_CENTER_Y - BTN_SIZE_Y / 2, BTN_SIZE_X, BTN_SIZE_Y);
        
        changeThemeBounds = new Rectangle(CHANGE_THEME_CENTER_X - BTN_SIZE_X / 2, CHANGE_THEME_CENTER_Y - BTN_SIZE_Y / 2, BTN_SIZE_X, BTN_SIZE_Y);
        soundBounds = new Rectangle(SOUND_CENTER_X - BTN_SIZE_X / 2, SOUND_CENTER_Y - BTN_SIZE_Y / 2, BTN_SIZE_X, BTN_SIZE_Y);
                         
        float h = (TEXT_CENTER_Y_UP - TEXT_CENTER_Y);// * 2f;
        resumeBounds = new Rectangle(TEXT_CENTER_X - Smove.GAME_WIDTH * 0.5f, TEXT_CENTER_Y_UP - h / 2, Smove.GAME_WIDTH, h);
        newPlayBounds = new Rectangle(TEXT_CENTER_X - Smove.GAME_WIDTH * 0.5f, TEXT_CENTER_Y - h / 2, Smove.GAME_WIDTH, h);
     
        myAdBounds = new Rectangle(MY_AD_CENTER_X - Smove.GAME_WIDTH * 0.5f, MY_AD_CENTER_Y - BTN_SIZE_X * 0.5f, Smove.GAME_WIDTH, BTN_SIZE_X);
        
        pushedBtn = NO_PUSHED_BTN;
        
        volume = new Volume(SOUND_CENTER_X, SOUND_CENTER_Y + Assets.soundOffRegion.getRegionHeight() * 0.5f);
        drawVolume = false;
        initGameState();
    }    
  
	public void update(float deltaTime) {
	    if(deltaTime > 0.1f){
	        deltaTime = 0.1f;
	    }	
	    switch(MyWorld.state) {	
		    case MyWorld.WORLD_RUNNING_STATE :
		        updateRunning(deltaTime);
		        break;
		    case MyWorld.WORLD_GAME_OVER_STATE :
		    	updateGameOver(deltaTime);
		        break;		         
		    case MyWorld.WORLD_PAUSED_STATE :
		        updatePaused();
		        break;
		}	
	}
	
	private void updateGameOver(float deltaTime) {		
		if(lastTouch == TOUCH_DOWN){				
			if (MyWorld.showMyAd && boundContainTouchDownPoint(myAdBounds)) {
				Assets.playSound(Assets.clickSound);				
				clearTouchPoint();
				Gdx.net.openURI("http://gruv-apps.com/");
				initGameState();
				return;
			}else{
				Assets.playSound(Assets.clickSound);
				clearTouchPoint();
				/*
				tryIncreaseLevel();
				initGameState();
				*/
				updateNextLevelTimeOut = -1f;
				Smove.listener.showInterstitialAdmobAd();
			}
		}
	}	
	
	private static void tryIncreaseLevel() {
		LevelParams.level++;
	}
	
	private void updatePaused() {	
		if(lastTouch == TOUCH_DOWN){	
			pushedBtn = NO_PUSHED_BTN;
			if (boundContainTouchDownPoint(resumeBounds)) {
				pushedBtn = RESUME_BTN;				
				return;
        	}
			if (boundContainTouchDownPoint(newPlayBounds)) {
				pushedBtn = START_NEW_GAME_BTN;				
				return;
        	}
			if (boundContainTouchDownPoint(soundBounds)) {
				pushedBtn = SOUND_BTN;				
				return;
        	}
		}
		if(lastTouch == TOUCH_UP){			
			if (boundContainTouchPoint(resumeBounds)) {
				Assets.playSound(Assets.clickSound);				
				clearTouchPoint();				
		        MyWorld.state = MyWorld.WORLD_RUNNING_STATE;
		        pushedBtn = NO_PUSHED_BTN;
		        Assets.playMusic();
				return;
			}
			if (boundContainTouchPoint(newPlayBounds)) {
				Assets.playSound(Assets.clickSound);				
				clearTouchPoint();			
				initGameState();				
				MyWorld.state = MyWorld.WORLD_RUNNING_STATE;//MyWorld.WORLD_WAIT_START_STATE;
		        pushedBtn = NO_PUSHED_BTN;
				return;
			}	
			if (boundContainTouchPoint(soundBounds)) {
				Assets.playSound(Assets.clickSound);				
				clearTouchPoint();				
				MyStorage.soundEnabled = !MyStorage.soundEnabled;		
				pushedBtn = NO_PUSHED_BTN;
	        	return;
			}
			pushedBtn = NO_PUSHED_BTN;
		}    
	} 

	private void updateRunning(float deltaTime) {
		if(drawVolume && volume.touch(lastTouch, touchDownPoint.x, touchDownPoint.y, touchDraggedPoint.y)){			
			if(lastTouch == TOUCH_UP){
				drawVolume = false;
				pushedBtn = NO_PUSHED_BTN;
			}	
			clearTouchPoint();
			return;
		}
		
		if(lastTouch == TOUCH_DOWN){
			pushedBtn = NO_PUSHED_BTN;
        	if (boundContainTouchDownPoint(exitBounds)) {
				pushedBtn = EXIT_BTN;				
				return;
        	}
        	if (boundContainTouchDownPoint(newGameBounds)) {
				pushedBtn = NEW_GAME_BTN;				
				return;
        	}
        	if (boundContainTouchDownPoint(helpBounds)) {
				pushedBtn = HELP_BTN;				
				return;
        	}
        	if (boundContainTouchDownPoint(changeThemeBounds)) {
				pushedBtn = CHANGE_THEME_BTN;				
				return;
        	}
        	if (boundContainTouchDownPoint(soundBounds)) {
				pushedBtn = SOUND_BTN;				
				return;
        	}
        	MyWorld.touchDown(touchDownPoint.x, touchDownPoint.y);
        	clearTouchPoint();
        	//return;        	
		}
		
		if(lastTouch == TOUCH_DRAGGED){
			MyWorld.touchDragged(touchDraggedPoint.x, touchDraggedPoint.y);
			clearTouchPoint();
		}		
		
		if(lastTouch == TOUCH_UP){
			if (boundContainTouchUpPoint(exitBounds) &&
					pushedBtn == EXIT_BTN) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new ConfirmExitScreen(game), Smove.CONFIRM_EXIT_SCREEN_ID, Smove.GAME_SCREEN_ID);
	    		clearTouchPoint();		
	    		pushedBtn = NO_PUSHED_BTN;
				return;
			}
			if(boundContainTouchUpPoint(newGameBounds) &&
					pushedBtn == NEW_GAME_BTN) {
				Assets.playSound(Assets.clickSound);
		        MyWorld.state = MyWorld.WORLD_PAUSED_STATE;
		        Assets.pauseMusic();
		        pushedBtn = NO_PUSHED_BTN;
        		return;
            }
			if (boundContainTouchUpPoint(helpBounds) &&
					pushedBtn == HELP_BTN) {
				Assets.playSound(Assets.clickSound);
				
				if(MyWorld.mode == MyWorld.NORMAL_MODE){
					MyWorld.mode = MyWorld.HELP_MODE;	
				}else{
					if(MyWorld.mode == MyWorld.HELP_MODE){
						MyWorld.mode = MyWorld.NORMAL_MODE;	
						MyWorld.touchUp(touchUpPoint.x, touchUpPoint.y);
					}
				}
				clearTouchPoint();	
				pushedBtn = NO_PUSHED_BTN;
				return;
			}
			if (boundContainTouchUpPoint(changeThemeBounds) &&
					pushedBtn == CHANGE_THEME_BTN) {	
				Assets.playSound(Assets.clickSound);
				LevelParams.theme++;
				if(LevelParams.theme >= LevelParams.THEME_DIM){
					LevelParams.theme = 0;
				}
				MyWorld.calcThemeColor();
				clearTouchPoint();
				pushedBtn = NO_PUSHED_BTN;
				return;
			}
			if (boundContainTouchUpPoint(soundBounds) &&
					pushedBtn == SOUND_BTN) {
				Assets.playSound(Assets.clickSound);
				drawVolume = true;
				volume.startScreen();	
				clearTouchPoint();		
				pushedBtn = NO_PUSHED_BTN;
				return;
			}
			MyWorld.mode = MyWorld.NORMAL_MODE;
			MyWorld.touchUp(touchUpPoint.x, touchUpPoint.y);
			clearTouchPoint();		
			pushedBtn = NO_PUSHED_BTN;
		}				
		MyWorld.updateRunningState(deltaTime/*, guiCam.position.y*/);	 
	}	
	
	private void presentRunning() {	
	   
	    if(MyWorld.mode != MyWorld.HELP_MODE && MyWorld.showSwipeInfo){
	    	Assets.swipeText.draw(batcher, Smove.TEXT_SCALE_1,  HELP_TEXT_CENTER_X, HELP_TEXT_CENTER_Y, true);
		}
    	batcher.setColor(LevelParams.themeColor[0], LevelParams.themeColor[1], LevelParams.themeColor[2], 1);
 		 
		
		if(pushedBtn == EXIT_BTN){	
			 batcher.drawCenterWithKHeightResize(Assets.exitRegion, EXIT_CENTER_X, EXIT_CENTER_Y /*+ yCamPositionDelta*/, SMALL_SIZE_COEF);
		}else{
			 batcher.drawCenterWithKHeight(Assets.exitRegion, EXIT_CENTER_X, EXIT_CENTER_Y /*+ yCamPositionDelta*/);
		}
		if(pushedBtn == NEW_GAME_BTN){	
			 batcher.drawCenterWithKHeightResize(Assets.newGameRegion, NEW_GAME_CENTER_X, NEW_GAME_CENTER_Y /*+ yCamPositionDelta*/, SMALL_SIZE_COEF);
		}else{
			 batcher.drawCenterWithKHeight(Assets.newGameRegion, NEW_GAME_CENTER_X, NEW_GAME_CENTER_Y /*+ yCamPositionDelta*/);
		}	   
		if(pushedBtn == HELP_BTN){	
			 batcher.drawCenterWithKHeightResize(Assets.helpRegion, HELP_CENTER_X, HELP_CENTER_Y /*+ yCamPositionDelta*/, SMALL_SIZE_COEF);
		}else{
			 batcher.drawCenterWithKHeight(Assets.helpRegion, HELP_CENTER_X, HELP_CENTER_Y /*+ yCamPositionDelta*/);
		}
		if(pushedBtn == CHANGE_THEME_BTN){	
			 batcher.drawCenterWithKHeightResize(Assets.themeRegion, CHANGE_THEME_CENTER_X, CHANGE_THEME_CENTER_Y /*+ yCamPositionDelta*/, SMALL_SIZE_COEF);
		}else{
			 batcher.drawCenterWithKHeight(Assets.themeRegion, CHANGE_THEME_CENTER_X, CHANGE_THEME_CENTER_Y /*+ yCamPositionDelta*/);
		}				
	    presentSound(); 
	    batcher.setColor(1, 1, 1, 1);	    
	    if(MyWorld.mode == MyWorld.HELP_MODE){
	   	     Assets.helpText.draw(batcher, Smove.TEXT_SCALE_1, HELP_TEXT_CENTER_X, HELP_TEXT_CENTER_Y /*+ yCamPositionDelta*/, true);		
	   	}
	}
	
	private void presentSound(){
		if(pushedBtn == SOUND_BTN){	
			if(MyStorage.soundEnabled){	    	
			  	batcher.drawCenterWithKHeightResize(Assets.soundOnRegion, SOUND_CENTER_X, SOUND_CENTER_Y /*+ yCamPositionDelta*/, SMALL_SIZE_COEF);	      
			}else{
				batcher.drawCenterWithKHeightResize(Assets.soundOffRegion, SOUND_CENTER_X, SOUND_CENTER_Y /*+ yCamPositionDelta*/, SMALL_SIZE_COEF);      
			} 
		}else{
			if(MyStorage.soundEnabled){	    	
			  	batcher.drawCenterWithKHeight(Assets.soundOnRegion, SOUND_CENTER_X, SOUND_CENTER_Y /*+ yCamPositionDelta*/);	      
			}else{
				batcher.drawCenterWithKHeight(Assets.soundOffRegion, SOUND_CENTER_X, SOUND_CENTER_Y /*+ yCamPositionDelta*/);	      
			}
		}
	}
	
	private void presentPaused() {  	
		if(pushedBtn == RESUME_BTN){	
			Assets.btnNinePatch.draw(batcher, 
					TEXT_CENTER_X - RAMKA_LARGE_WIDTH * SMALL_SIZE_COEF * 0.5f, 
					TEXT_CENTER_Y_UP - RAMKA_LARGE_HEIGHT * SMALL_SIZE_COEF * 0.5f /*+ yCamPositionDelta*/, 
	       			RAMKA_LARGE_WIDTH * SMALL_SIZE_COEF, 
	       			RAMKA_LARGE_HEIGHT * SMALL_SIZE_COEF) ;
			Assets.resumeGameText.draw(batcher, Smove.TEXT_SCALE_1 * SMALL_SIZE_COEF, TEXT_CENTER_X , TEXT_CENTER_Y_UP /*+ yCamPositionDelta*/, true);
		}else{
			Assets.btnNinePatch.draw(batcher, 
					TEXT_CENTER_X - RAMKA_LARGE_WIDTH * 0.5f, 
					TEXT_CENTER_Y_UP - RAMKA_LARGE_HEIGHT * 0.5f /*+ yCamPositionDelta*/, 
	       			RAMKA_LARGE_WIDTH, 
	       			RAMKA_LARGE_HEIGHT) ;
			Assets.resumeGameText.draw(batcher, Smove.TEXT_SCALE_1, TEXT_CENTER_X , TEXT_CENTER_Y_UP /*+ yCamPositionDelta*/, true);
		}
		
		if(pushedBtn == START_NEW_GAME_BTN){	
			Assets.btnNinePatch.draw(batcher, 
					TEXT_CENTER_X - RAMKA_LARGE_WIDTH * SMALL_SIZE_COEF * 0.5f, 
					TEXT_CENTER_Y - RAMKA_LARGE_HEIGHT * SMALL_SIZE_COEF * 0.5f /*+ yCamPositionDelta*/, 
	       			RAMKA_LARGE_WIDTH * SMALL_SIZE_COEF, 
	       			RAMKA_LARGE_HEIGHT * SMALL_SIZE_COEF) ;
			Assets.newGameText.draw(batcher, Smove.TEXT_SCALE_1 * SMALL_SIZE_COEF, TEXT_CENTER_X , TEXT_CENTER_Y /*+ yCamPositionDelta*/, true);
		}else{
			Assets.btnNinePatch.draw(batcher, 
					TEXT_CENTER_X - RAMKA_LARGE_WIDTH * 0.5f, 
					TEXT_CENTER_Y - RAMKA_LARGE_HEIGHT * 0.5f /*+ yCamPositionDelta*/, 
	       			RAMKA_LARGE_WIDTH, 
	       			RAMKA_LARGE_HEIGHT) ;
			Assets.newGameText.draw(batcher, Smove.TEXT_SCALE_1, TEXT_CENTER_X , TEXT_CENTER_Y /*+ yCamPositionDelta*/, true);
		}
	}

	private void presentGameOver() {		
		Assets.btnNinePatch.draw(batcher, 
				TEXT_CENTER_X - RAMKA_LARGE_WIDTH * SMALL_SIZE_COEF * 0.5f, 
				GAME_OVER_CENTER_Y - RAMKA_LARGE_HEIGHT * SMALL_SIZE_COEF * 0.5f /*+ yCamPositionDelta*/, 
				RAMKA_LARGE_WIDTH * SMALL_SIZE_COEF, 
				RAMKA_LARGE_HEIGHT * SMALL_SIZE_COEF) ;
		
			Assets.gameOverText.draw(batcher, Smove.TEXT_SCALE_1, TEXT_CENTER_X , GAME_OVER_CENTER_Y0 /*+ yCamPositionDelta*/, true);
		Assets.tryAgainText.draw(batcher, Smove.TEXT_SCALE_1, TEXT_CENTER_X , GAME_OVER_CENTER_Y1 /*+ yCamPositionDelta*/, true);
		    	
    	presentMyAd();
	}		

	private void initGameState(){
        MyWorld.updateGameFinishState();
	}
        
    private void hideAd(){
		if(adIsShowing){	
			if(Smove.listener != null){
				Smove.listener.hideAdmobAd();	
			}
			adIsShowing = false;
		}
	}
	
	private void showAd(){
		if(!adIsShowing){
			if(Smove.listener != null){
				Smove.listener.showAdmobAd();
			}
			adIsShowing = true;
		}
	}
		
	private void presentMyAd(){
    	if(MyWorld.showMyAd){
			hideAd();
			batcher.drawCenter(Assets.myAdRegion, MY_AD_CENTER_X, MY_AD_CENTER_Y /*+ yCamPositionDelta*/);    
		}
    }

    @Override
    public void pause() {  	
    	stopScreen();      
    }
    
    @Override
    public void resume() {
    	startScreen();       
    }    
    
    @Override
    public void hide() { 
    	stopScreen();      
    }
    
    @Override
    public void show () {
    	startScreen();       
    }
    
    private void stopScreen(){
    	 MyStorage.saveWorld();
    	 MyStorage.save();
         showAd();
         Assets.pauseMusic();
    }

    private void startScreen(){
    	MyStorage.load();
    	MyStorage.loadWorld(world);
    	guiCam.position.set(Smove.GAME_WIDTH * 0.5f, Smove.GAME_HEIGHT * 0.5f/* - MyWorld.time * LevelParams.camSpeed*/, 0);
    	if(MyStorage.weHaveDataForLevel()){
	    	if( MyWorld.state == MyWorld.WORLD_GAME_OVER_STATE){
	    		initGameState();            
	    	}else{
	    		//Assets.initPositionMusic();
	    		Assets.pauseMusic();
	    		Assets.playMusic();
	    	}
    	}else{
    		initGameState();
    	}
    	showAd();   
    	drawVolume = false;
    } 
        	
    public void draw () {
		GL20 gl = Gdx.gl;
		gl.glClearColor(0, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gl.glDisable(GL20.GL_DEPTH_TEST); 
		gl.glEnable(GL20.GL_BLEND);
		
		gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);		
		
		guiCam.update();		
			
		batcher.setProjectionMatrix(guiCam.combined);
		
		batcher.begin();
		
		//batcher.disableBlending();
		batcher.setColor(LevelParams.themeColor[0], LevelParams.themeColor[1], LevelParams.themeColor[2], 1);
		batcher.draw(Assets.backgroundRegion, 0, 0, Smove.GAME_WIDTH, Smove.GAME_HEIGHT);
		batcher.setColor(1, 1, 1, 1);		
		
		batcher.enableBlending();
		
		renderer.renderWorld();
		
	    switch(MyWorld.state) {
		    case MyWorld.WORLD_GAME_OVER_STATE :
		    	presentGameOver();
		        break;		     		   
	        case MyWorld.WORLD_RUNNING_STATE:
	        	showAd();
		        presentRunning();	        
		        break;			  	        
	        case MyWorld.WORLD_PAUSED_STATE:
	        	showAd();
		        presentPaused();
		        break;
		}	  
		
	    presentTablo();
	    
	    if(!MyWorld.showMyAd){
		    batcher.setColor(LevelParams.themeColor[0], LevelParams.themeColor[1], LevelParams.themeColor[2], 1);
			batcher.drawCenter(Assets.greyRegion, GREY_X, GREY_Y /*+ yCamPositionDelta*/, GREY_W, GREY_H);
		    batcher.setColor(1, 1, 1, 1);
	    }
	    
	    if(drawVolume){
	    	volume.draw(batcher);
	    }
	    //fpsCounter.logFrame();		
		
		batcher.end();
	}
    	
	private void presentTablo(){	
		if(MyWorld.state == MyWorld.WORLD_GAME_OVER_STATE){
			Assets.highscoreText.draw(batcher, Smove.TEXT_SCALE_0, TEXT_X_1, TEXT_Y_1 /*+ yCamPositionDelta*/, true);	
		}else{
			Assets.levelText.draw(batcher, Smove.TEXT_SCALE_0, TEXT_X_1, TEXT_Y_1 /*+ yCamPositionDelta*/, true);	
		}
		Assets.timeText.draw(batcher, Smove.TEXT_SCALE_0, TEXT_X_CENTER, TEXT_Y_1 /*+ yCamPositionDelta*/, true);	
		
		Assets.bonusesText.draw(batcher, Smove.TEXT_SCALE_0, TEXT_X_2, TEXT_Y_1 /*+ yCamPositionDelta*/, true);			
				
		if(MyWorld.state == MyWorld.WORLD_GAME_OVER_STATE){
			batcher.drawDigitsCenter(TEXT_X_1, TEXT_Y_2 /*+ yCamPositionDelta*/,
					Smove.TEXT_SCALE_1, 
					MyStorage.highscores);	
		}else{
			batcher.drawDigitsCenter(TEXT_X_1, TEXT_Y_2 /*+ yCamPositionDelta*/,
					Smove.TEXT_SCALE_1, 
					(LevelParams.level + 1));	
		}
		
		batcher.drawTimeCenter(TEXT_X_CENTER, TEXT_Y_2, 
				Smove.TEXT_SCALE_1,
				(int)MyWorld.time);
		
		batcher.drawDigitsCenter(TEXT_X_2, TEXT_Y_2/* + yCamPositionDelta*/,
				Smove.TEXT_SCALE_1, 
				MyWorld.bonusesCount);			
	}
	
	/*
	private void drawTime(int sec, float x){
		if(sec < 0){
			sec = 0;
		}		
		int hour = (sec / 3600);
		sec = (sec % 3600);
		int min = (sec / 60);
		sec = (sec % 60);		
		
		batcher.drawDigitsTabloCenter(x - TIME_DX2, TEXT_Y_2 + yCamPositionDelta,
				FollowLine.TEXT_SCALE_1, 
				hour, 2);	
		Assets.twoDotsText.draw(batcher, FollowLine.TEXT_SCALE_1, x - TIME_DX1, TEXT_Y_2 + yCamPositionDelta, true);
		batcher.drawDigitsTabloCenter(x, TEXT_Y_2 + yCamPositionDelta,
				FollowLine.TEXT_SCALE_1, 
				min, 2);	
		Assets.twoDotsText.draw(batcher, FollowLine.TEXT_SCALE_1, x + TIME_DX1, TEXT_Y_2 + yCamPositionDelta, true);
		batcher.drawDigitsTabloCenter(x + TIME_DX2, TEXT_Y_2 + yCamPositionDelta,
				FollowLine.TEXT_SCALE_1, 
				sec, 2);
	}
	*/
	
    @Override
    public void dispose() {   
    }

	@Override
	public void render(float delta) {		
		if(updateNextLevelTimeOut > -9){
			if(updateNextLevelTimeOut < 0){
				updateNextLevelTimeOut = 0.01f;
				draw();
				return;
			}else{
				updateNextLevelTimeOut += delta;
				if(updateNextLevelTimeOut < INIT_STATE_TIMEOUT){
					draw();
					return;
				}else{
					tryIncreaseLevel();
					initGameState();
					updateNextLevelTimeOut = -10;
				}				
			}
		}
		update(delta);
		draw();	
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.BACK){
			game.setScreen(new ConfirmExitScreen(game), Smove.CONFIRM_EXIT_SCREEN_ID, Smove.GAME_SCREEN_ID);
    		return true;
		}
		return false;
	}
}
