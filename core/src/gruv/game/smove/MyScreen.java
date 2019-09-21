package gruv.game.smove;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MyScreen implements Screen{
	
	public Smove game = null;
	public static Camera guiCam = null;
	public MySpriteBatch batcher = null;
	
	public Vector3 touchDownPoint = null;
	public Vector3 touchUpPoint = null;
	public Vector3 touchDraggedPoint = null;	
	
	public static final int NO_TOUCH = 0 ;
	public static final int TOUCH_DOWN = 1 ;
	public static final int TOUCH_DRAGGED = 2 ;
	public static final int TOUCH_UP = 3 ;
	
	public int lastTouch = NO_TOUCH;
	
	public MyScreen() {	 
		
		guiCam = new OrthographicCamera(Smove.GAME_WIDTH, Smove.GAME_HEIGHT);
	 	guiCam.position.set(Smove.GAME_WIDTH * 0.5f, Smove.GAME_HEIGHT * 0.5f, 0);	 	
	 	
    	touchDownPoint = new Vector3(-1, -1, 0);
    	touchUpPoint = new Vector3(-1, -1, 0);
    	touchDraggedPoint = new Vector3(-1, -1, 0);
 		
    	batcher = new MySpriteBatch();
    	lastTouch = NO_TOUCH;
	}
	
	public void clearTouchPoint(){
		touchDownPoint.x = (-1);
		touchDownPoint.y = (-1);
		lastTouch = NO_TOUCH;
	}
	
	public float getYCorrection(){
		return (guiCam.position.y - Smove.GAME_HEIGHT * 0.5f);
	}
	
	public boolean boundContainTouchPoint(Rectangle rect){
		return (rect.contains(touchDownPoint.x, touchDownPoint.y - getYCorrection()) &&
    			rect.contains(touchUpPoint.x, touchUpPoint.y - getYCorrection()));
    }
	
	public boolean boundContainTouchDownPoint(Rectangle rect){
		return rect.contains(touchDownPoint.x, touchDownPoint.y - getYCorrection()) ;
	}
	
	public boolean boundContainTouchUpPoint(Rectangle rect){
		return rect.contains(touchUpPoint.x, touchUpPoint.y - getYCorrection()) ;
	}
	
	public boolean boundContainTouchDraggedPoint(Rectangle rect){
		return rect.contains(touchDraggedPoint.x, touchDraggedPoint.y - getYCorrection()) ;
	}	        
	 
	@Override
	public void render(float delta) {
	}

	@Override
	public void resize(int width, int height) {
		Assets.calcKHeight(width, height);
		//int r = 0 ;
		//int rr = r ;
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
	
	public boolean keyDown(int keycode) {
		return false;
	}
	
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(pointer == 0){// one touch mode (not multitouch!)
			touchUpPoint.x = (-1);
			touchUpPoint.y = (-1);
			touchDownPoint.x = screenX;
			touchDownPoint.y = screenY;
			guiCam.unproject(touchDownPoint);
			lastTouch = TOUCH_DOWN;	
		}
		return false;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(pointer == 0){// one touch mode (not multitouch!)
			touchUpPoint.x = screenX;
			touchUpPoint.y = screenY;		
			guiCam.unproject(touchUpPoint);
			lastTouch = TOUCH_UP;	
		}
		return false;
	}


	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(pointer == 0){// one touch mode (not multitouch!)
			touchDraggedPoint.x = screenX;
			touchDraggedPoint.y = screenY;		
			guiCam.unproject(touchDraggedPoint);
			lastTouch = TOUCH_DRAGGED;	
		}
		return false;
	}
}