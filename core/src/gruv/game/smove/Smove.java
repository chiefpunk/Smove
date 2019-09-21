package gruv.game.smove;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.FPSLogger;

public class Smove extends Game implements InputProcessor{	

	FPSLogger fps;
	
	public AssetManager assets = null;//https://github.com/libgdx/libgdx/wiki/Managing-your-assets
	
	public static int currentScreenID = 0 ;
	public static int previousScreenID = 0 ;
	
	public static final int LOADING_SCREEN_ID = 0;
	public static final int GAME_SCREEN_ID = 1;
	public static final int CONFIRM_EXIT_SCREEN_ID = 2;
	public static final int CONFIRM_SAVE_SCREEN_ID = 3;
	public static final int CONFIRM_LOAD_SCREEN_ID = 4;
	    
    public static final float GAME_WIDTH = 720;//320;
	public static final float GAME_HEIGHT = 1280;//480;
    
    public static boolean startInterstitial = false; 
    
    public static final float TEXT_SCALE_LICENSE = 0.50f;
    public static final float TEXT_SCALE_0 = 0.62f;
    public static final float TEXT_SCALE_1 = 0.71f;//
    public static final float TEXT_SCALE_2 = 1.0f;//
    
    public static final float TEXT_SCALE_SIZE = 0.35f;//1.15f;//
    
    public static final float TEXT_SCALE_VALUE = 0.92f;
     
    public interface Listener {
    	public void showAdmobAd();
		public void hideAdmobAd();	
		public void initializeAdsAndShow();//only for iOS
		public String getVersion();
		public void showInterstitialAdmobAd();
	}
    
    public static Listener listener = null;
    
    public Smove(){    	
    }
    
    public Smove(Listener listener){
    	Smove.listener = listener;
    }    
    
	@Override
	public void create () {
		 startInterstitial = false;
		 
		 Gdx.input.setInputProcessor(this);
		 Gdx.input.setCatchBackKey(true);	
		
		 MyStorage.load();		
	
		 Assets.loadOnlyDataForLoadingScreen();
		 setScreen(new LoadingScreen(this), LOADING_SCREEN_ID, LOADING_SCREEN_ID);
		
		 fps = new FPSLogger();
		 
		 listener.initializeAdsAndShow();//only for iOS
	}	
	
	@Override
	public void render() {
		super.render();
		fps.log();
	}

	/** {@link Game#dispose()} only calls {@link Screen#hide()} so you need to override {@link Game#dispose()} in order to call
	 * {@link Screen#dispose()} on each of your screens which still need to dispose of their resources. SuperJumper doesn't
	 * actually have such resources so this is only to complete the example. */
	@Override
	public void dispose () {
		super.dispose();
		getScreen().dispose();
	}
	
	public void setScreen (Screen screen, int currentScreenID_, int previousScreenID_) {
		this.setScreen(screen);
		currentScreenID = currentScreenID_;
	    previousScreenID = previousScreenID_;
	}

	@Override
	public boolean keyDown(int keycode) {
		return ((MyScreen)this.getScreen()).keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return ((MyScreen)this.getScreen()).touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return ((MyScreen)this.getScreen()).touchUp(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return ((MyScreen)this.getScreen()).touchDragged(screenX, screenY, pointer);
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
