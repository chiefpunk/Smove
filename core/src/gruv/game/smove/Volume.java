package gruv.game.smove;

import com.badlogic.gdx.math.Rectangle;

public class Volume {
	
	private float xCenter;
	private float ycenter;
	
	private float height = 0 ;	
	
	private static float ballMinY = 0;
	private static float ballMaxY = 0;
	
	private static float ballVolumeCenterX = 0;	
	private static float ballVolumeCenterY = 0;	
	
	private GameObject ballVolume = null;
	private boolean ballVolumeIsTouched = false;
	    
    public Volume(float xCenter, float yDown) { 
    	height = Assets.volumeLineRegion.getRegionHeight();
    	this.xCenter = xCenter;
    	this.ycenter = yDown + height * 0.5f;
    	
    	ballMinY = yDown;
    	ballMaxY = yDown + height;
    	
    	ballVolumeCenterX = xCenter;
    	calcballVolumeCenterY();
        ballVolume = new GameObject(ballVolumeCenterX, ballVolumeCenterY, Assets.volumeBegunokRegion.getRegionWidth() * 2, Assets.volumeBegunokRegion.getRegionHeight() * 2);
        calcballVolumeShowValueY();  
        ballVolumeIsTouched = false;
    }
    
    private void ballVolumeSetY(float y){
    	if(y < ballMinY){
    		y = ballMinY;
    	}
    	if(y > ballMaxY){
    		y = ballMaxY;
    	}    	
    	ballVolume.setXY(ballVolumeCenterX, y);
    	calcballVolumeShowValueY();
    }
    
    private void calcballVolumeCenterY(){
    	ballVolumeCenterY = ballMinY + MyStorage.volumeValueY * (ballMaxY - ballMinY) / 100;
    }
    
    private void calcballVolumeShowValueY(){
    	MyStorage.volumeValueY = (int)((ballVolume.position.y - ballMinY) * 100f / (ballMaxY - ballMinY));
    	if(MyStorage.volumeValueY < 0){
    		MyStorage.volumeValueY = 0;
    	}
    	if(MyStorage.volumeValueY > 100){
    		MyStorage.volumeValueY = 100;
    	} 	
    }
    
    public void draw(MySpriteBatch batcher){
    	batcher.setColor(LevelParams.themeColor[0] * 0.195f, LevelParams.themeColor[1] * 0.195f, LevelParams.themeColor[2] * 0.195f, 1);
    	
    	batcher.drawCenter(Assets.volumeLineRegion, xCenter, ycenter /*+ GameScreen.yCamPositionDelta*/);
		batcher.drawCenter(Assets.volumeBegunokRegion, xCenter, ballVolume.position.y /*+ GameScreen.yCamPositionDelta*/);
		//batcher.setColor(1, 1, 1, 1);
	}
    
    private boolean boundContainTouchDownPoint( float touchDownPointX, 
									    		float touchDownPointY,									    		
									    		Rectangle rect){
		return rect.contains(touchDownPointX, touchDownPointY /*- GameScreen.yCamPositionDelta*/) ;
	}
    
    /*
	if return true, then call clearTouchPoint();
    */
    public boolean touch(int lastTouch,    					
			    		float touchDownPointX, 
			    		float touchDownPointY, 
			    		float touchDraggedPointY){
    	if (lastTouch == MyScreen.TOUCH_DOWN && 
			boundContainTouchDownPoint(touchDownPointX, touchDownPointY, ballVolume.bounds)) {
			Assets.playSound(Assets.clickSound);
			ballVolumeSetY(touchDownPointY /*- GameScreen.yCamPositionDelta*/);	 
        	ballVolumeIsTouched = true;
        	//clearTouchPoint();
        	return true;
		}
    	if(lastTouch == MyScreen.TOUCH_DRAGGED && ballVolumeIsTouched){   	
        	ballVolumeSetY(touchDraggedPointY /*- GameScreen.yCamPositionDelta*/);
        	//return false;
        	return true;
        }
    	if (lastTouch == MyScreen.TOUCH_UP) {
			ballVolumeIsTouched = false;
			//clearTouchPoint();
			MyStorage.calcVolume();
			stopScreen();
        	return true;
		}    	  
    	return false;
    }
    
    private void stopScreen(){
   	    MyStorage.save();// для сохранения состояния      
   	    Assets.pauseMusic();
   	    Assets.playMusic();
    }

    public void startScreen(){
	   	//MyStorage.load();// для восстановления состояния 	 
	   	ballVolumeIsTouched  = false;
	   	calcballVolumeCenterY();
    }
}
