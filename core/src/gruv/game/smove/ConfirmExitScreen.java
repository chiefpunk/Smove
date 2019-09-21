package gruv.game.smove;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;

public class ConfirmExitScreen extends MyScreen{
	private Smove game;

	private Rectangle yesBounds;
	private Rectangle noBounds;
	
	private Rectangle goToSiteBounds;
	private Rectangle logoBounds;
	
	private static final float BOUNDS_WIDTH = (Smove.GAME_WIDTH * 0.5f);
	private static final float BOUNDS_HEIGHT = (Smove.GAME_HEIGHT * 0.12f);  
	
	private static final float CONFIRM_EXIT_1_CENTER_X = (Smove.GAME_WIDTH * 0.5f);
	private static final float CONFIRM_EXIT_1_CENTER_Y = (Smove.GAME_HEIGHT * 0.85f);
	
	private static final float CONFIRM_EXIT_2_CENTER_X = CONFIRM_EXIT_1_CENTER_X;
	private static final float CONFIRM_EXIT_2_CENTER_Y = (Smove.GAME_HEIGHT * 0.80f);
		
	private static final float YES_TEXT_CENTER_X = (Smove.GAME_WIDTH * 0.25f);
	private static final float YES_TEXT_CENTER_Y = (Smove.GAME_HEIGHT * 0.69f);

	private static final float NO_TEXT_CENTER_X = (Smove.GAME_WIDTH * 0.75f);
	private static final float NO_TEXT_CENTER_Y = YES_TEXT_CENTER_Y;	
	
	private static final float GO_TO_SITE_CENTER_Y_0 = Smove.GAME_HEIGHT * 0.37f;//0.70f
	private static final float GO_TO_SITE_CENTER_Y_1 = Smove.GAME_HEIGHT * 0.33f;//0.60f
	private static final float GO_TO_SITE_CENTER_Y_2 = Smove.GAME_HEIGHT * 0.29f;//0.50f
	private static final float GO_TO_SITE_CENTER_Y_3 = Smove.GAME_HEIGHT * 0.25f;//0.40f
	private static final float GO_TO_SITE_CENTER_Y_4 = Smove.GAME_HEIGHT * 0.205f;//0.40f
	private static final float GO_TO_SITE_CENTER_Y_5 = Smove.GAME_HEIGHT * 0.165f;//0.40f
	
	private static final float GO_TO_SITE_CENTER_X = CONFIRM_EXIT_1_CENTER_X;
	private static final float GO_TO_SITE_CENTER_Y =  Smove.GAME_HEIGHT * 0.065f;
	    
    private static final float LOGO_CENTER_X = CONFIRM_EXIT_1_CENTER_X;
	private static final float LOGO_CENTER_Y = Smove.GAME_HEIGHT  * 0.47f;
	
	
    private static int NO_PUSHED_BTN = (-1);
	    
    private static int YES_BTN = 0;
    private static int NO_BTN = 1;
    private static int SITE_BTN = 2;			
    
    private int pushedBtn = NO_PUSHED_BTN;

	public ConfirmExitScreen (Smove game) {
		this.game = game;

		Assets.load(game);
				
		yesBounds = new Rectangle(YES_TEXT_CENTER_X - BOUNDS_WIDTH * 0.5f, YES_TEXT_CENTER_Y - BOUNDS_HEIGHT * 0.5f, BOUNDS_WIDTH, BOUNDS_HEIGHT);	
		noBounds =  new Rectangle(NO_TEXT_CENTER_X - BOUNDS_WIDTH * 0.5f, NO_TEXT_CENTER_Y - BOUNDS_HEIGHT * 0.5f, BOUNDS_WIDTH, BOUNDS_HEIGHT);
		goToSiteBounds = new Rectangle(
	        		GO_TO_SITE_CENTER_X - GameScreen.RAMKA_LARGE_WIDTH * 0.5f, 
	        		GO_TO_SITE_CENTER_Y - GameScreen.RAMKA_LARGE_HEIGHT  * 0.5f, 
	        		GameScreen.RAMKA_LARGE_WIDTH, 
	        		GameScreen.RAMKA_LARGE_HEIGHT);  
	    logoBounds = new Rectangle(
	        		LOGO_CENTER_X - Assets.logoRegion.getRegionWidth() * 0.5f, 
	        		LOGO_CENTER_Y - Assets.logoRegion.getRegionHeight()  * 0.5f, 
	        		Assets.logoRegion.getRegionWidth(), 
	        		Assets.logoRegion.getRegionHeight());
	    
	    pushedBtn = NO_PUSHED_BTN;
	    //guiCam.position.set(FollowLine.GAME_WIDTH * 0.5f, FollowLine.GAME_HEIGHT * 0.5f, 0);		
	}

	public void update () {
		if(lastTouch == TOUCH_DOWN){	
			pushedBtn = NO_PUSHED_BTN;
			if (boundContainTouchDownPoint(yesBounds)) {
				pushedBtn = YES_BTN;				
				return;
        	}
			if (boundContainTouchDownPoint(noBounds)) {
				pushedBtn = NO_BTN;				
				return;
        	}
			if (boundContainTouchDownPoint(goToSiteBounds)) {
				pushedBtn = SITE_BTN;				
				return;
        	}
		}
		if(lastTouch == TOUCH_UP){			
			if (boundContainTouchPoint(yesBounds)) {
				Assets.playSound(Assets.clickSound);
				Smove.startInterstitial = true;
				clearTouchPoint();
				pushedBtn = NO_PUSHED_BTN;
				MyStorage.save();
				Gdx.app.exit();
				return;
			}		
			if (boundContainTouchPoint(noBounds)) {
				Assets.playSound(Assets.clickSound);	
				clearTouchPoint();
				pushedBtn = NO_PUSHED_BTN;
				game.setScreen(new GameScreen(game), Smove.GAME_SCREEN_ID, Smove.CONFIRM_EXIT_SCREEN_ID);
				return;
			}
			if (boundContainTouchPoint(goToSiteBounds) ||
				boundContainTouchPoint(logoBounds)) {
				Assets.playSound(Assets.clickSound);
				pushedBtn = NO_PUSHED_BTN;
				clearTouchPoint();
				Gdx.net.openURI("http://gruv-apps.com/");
				return;
			}
			pushedBtn = NO_PUSHED_BTN;
		}		
	}

	public void draw () {
		GL20 gl = Gdx.gl;
		gl.glClearColor(0, 0, 0, 1);
		//gl.glClearColor(GameScreen.BG_COLOR[0], GameScreen.BG_COLOR[1], GameScreen.BG_COLOR[2], 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);
		batcher.begin();
		
		guiCam.position.set(Smove.GAME_WIDTH * 0.5f, Smove.GAME_HEIGHT * 0.5f, 0);

		batcher.setColor(LevelParams.themeColor[0], LevelParams.themeColor[1], LevelParams.themeColor[2], 1);
		batcher.draw(Assets.backgroundRegion, 0, 0, Smove.GAME_WIDTH, Smove.GAME_HEIGHT);		
		batcher.setColor(1, 1, 1, 1);
		
		batcher.enableBlending();		
		
		batcher.drawCenterWithKHeightResize(Assets.logoRegion, LOGO_CENTER_X, LOGO_CENTER_Y, 0.4f);		
		
		Assets.versionText.draw(batcher, Smove.TEXT_SCALE_1, GO_TO_SITE_CENTER_X, GO_TO_SITE_CENTER_Y_0, false);
		Assets.creatorText.draw(batcher, Smove.TEXT_SCALE_1, GO_TO_SITE_CENTER_X, GO_TO_SITE_CENTER_Y_1, false);
		Assets.mailText.draw(batcher, Smove.TEXT_SCALE_1, GO_TO_SITE_CENTER_X, GO_TO_SITE_CENTER_Y_2, false);
		Assets.urlText.draw(batcher, Smove.TEXT_SCALE_1, GO_TO_SITE_CENTER_X, GO_TO_SITE_CENTER_Y_3, false);
		Assets.musicText.draw(batcher, Smove.TEXT_SCALE_1, GO_TO_SITE_CENTER_X, GO_TO_SITE_CENTER_Y_4, false);
		Assets.musicLicenseText.draw(batcher, Smove.TEXT_SCALE_LICENSE, GO_TO_SITE_CENTER_X, GO_TO_SITE_CENTER_Y_5, false);

		batcher.setColor(LevelParams.themeColor[0], LevelParams.themeColor[1], LevelParams.themeColor[2], 1);
					
		if(pushedBtn == SITE_BTN){	
			Assets.btnNinePatch.draw(batcher, 
					GO_TO_SITE_CENTER_X - GameScreen.RAMKA_LARGE_WIDTH * GameScreen.SMALL_SIZE_COEF * 0.5f, 
					GO_TO_SITE_CENTER_Y - GameScreen.RAMKA_LARGE_HEIGHT * GameScreen.SMALL_SIZE_COEF * 0.5f, 
					GameScreen.RAMKA_LARGE_WIDTH * GameScreen.SMALL_SIZE_COEF, 
					GameScreen.RAMKA_LARGE_HEIGHT * GameScreen.SMALL_SIZE_COEF) ;
			Assets.siteText.draw(batcher, Smove.TEXT_SCALE_2 * GameScreen.SMALL_SIZE_COEF, GO_TO_SITE_CENTER_X, GO_TO_SITE_CENTER_Y, true);	
			
		}else{
			Assets.btnNinePatch.draw(batcher, 
					GO_TO_SITE_CENTER_X - GameScreen.RAMKA_LARGE_WIDTH * 0.5f, 
					GO_TO_SITE_CENTER_Y - GameScreen.RAMKA_LARGE_HEIGHT * 0.5f, 
					GameScreen.RAMKA_LARGE_WIDTH, 
					GameScreen.RAMKA_LARGE_HEIGHT) ;
			Assets.siteText.draw(batcher, Smove.TEXT_SCALE_2, GO_TO_SITE_CENTER_X, GO_TO_SITE_CENTER_Y, true);	
		}
		
				
				

		if(pushedBtn == YES_BTN){	
			Assets.btnNinePatch.draw(batcher, 
					YES_TEXT_CENTER_X - GameScreen.RAMKA_SMALL_WIDTH * GameScreen.SMALL_SIZE_COEF * 0.5f, 
					YES_TEXT_CENTER_Y - GameScreen.RAMKA_SMALL_HEIGHT * Assets.kHeight * GameScreen.SMALL_SIZE_COEF * 0.5f, 
					GameScreen.RAMKA_SMALL_WIDTH * GameScreen.SMALL_SIZE_COEF, 
					GameScreen.RAMKA_SMALL_HEIGHT * Assets.kHeight * GameScreen.SMALL_SIZE_COEF) ;
			Assets.yesText.draw(batcher, Smove.TEXT_SCALE_2 * GameScreen.SMALL_SIZE_COEF, YES_TEXT_CENTER_X, YES_TEXT_CENTER_Y, true);	
			
		}else{
			Assets.btnNinePatch.draw(batcher, 
					YES_TEXT_CENTER_X - GameScreen.RAMKA_SMALL_WIDTH * 0.5f, 
					YES_TEXT_CENTER_Y - GameScreen.RAMKA_SMALL_HEIGHT * Assets.kHeight * 0.5f, 
					GameScreen.RAMKA_SMALL_WIDTH, 
					GameScreen.RAMKA_SMALL_HEIGHT * Assets.kHeight) ;
			Assets.yesText.draw(batcher, Smove.TEXT_SCALE_2, YES_TEXT_CENTER_X, YES_TEXT_CENTER_Y, true);	
		}
		
		
		if(pushedBtn == NO_BTN){	
			Assets.btnNinePatch.draw(batcher, 
					NO_TEXT_CENTER_X - GameScreen.RAMKA_SMALL_WIDTH * GameScreen.SMALL_SIZE_COEF * 0.5f, 
					NO_TEXT_CENTER_Y - GameScreen.RAMKA_SMALL_HEIGHT * Assets.kHeight * GameScreen.SMALL_SIZE_COEF * 0.5f, 
					GameScreen.RAMKA_SMALL_WIDTH * GameScreen.SMALL_SIZE_COEF, 
					GameScreen.RAMKA_SMALL_HEIGHT * Assets.kHeight * GameScreen.SMALL_SIZE_COEF) ;
			Assets.noText.draw(batcher, Smove.TEXT_SCALE_2 * GameScreen.SMALL_SIZE_COEF, NO_TEXT_CENTER_X, NO_TEXT_CENTER_Y, true);		
		
		}else{
			Assets.btnNinePatch.draw(batcher, 
					NO_TEXT_CENTER_X - GameScreen.RAMKA_SMALL_WIDTH * 0.5f, 
					NO_TEXT_CENTER_Y - GameScreen.RAMKA_SMALL_HEIGHT * Assets.kHeight * 0.5f, 
					GameScreen.RAMKA_SMALL_WIDTH, 
					GameScreen.RAMKA_SMALL_HEIGHT * Assets.kHeight) ;
			Assets.noText.draw(batcher, Smove.TEXT_SCALE_2, NO_TEXT_CENTER_X, NO_TEXT_CENTER_Y, true);		
		}
		batcher.setColor(1, 1, 1, 1);
			
				
		Assets.confirmExit1Text.draw(batcher, Smove.TEXT_SCALE_2, CONFIRM_EXIT_1_CENTER_X, CONFIRM_EXIT_1_CENTER_Y, false);
		Assets.confirmExit2Text.draw(batcher, Smove.TEXT_SCALE_2, CONFIRM_EXIT_2_CENTER_X, CONFIRM_EXIT_2_CENTER_Y, false);
				
		
		batcher.end();		
	}
	/*
	@Override
    public void resume() {
		guiCam.position.set(FollowLine.GAME_WIDTH * 0.5f, FollowLine.GAME_HEIGHT * 0.5f, 0);		 
    } */ 

	@Override
	public void render (float delta) {
		update();
		draw();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.BACK){
			game.setScreen(new GameScreen(game), Smove.GAME_SCREEN_ID, Smove.CONFIRM_EXIT_SCREEN_ID);
    		return true;
		}
		return false;
	}
}
