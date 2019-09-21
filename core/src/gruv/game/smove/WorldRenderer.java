package gruv.game.smove;

public class WorldRenderer  {  
    
    private MySpriteBatch batcher;
     
    private static final float FINGER_SIZE = 0.85f;
    private static final float BONUS_ROTATE_SPEED = 50f;
             
    public WorldRenderer(MySpriteBatch batcher, MyWorld world) {
        //this.world = world;  
    	this.batcher = batcher;        
    }  
    
    private void drawSetka(){    	
    	if(LevelParams.theme == 0){
    		batcher.setColor(0.2f,0.2f,0.2f,1);
    	}else{
    		batcher.setColor(0.8f,0.8f,0.8f,1);
    	}
    	for(int i = 0 ; i < MyWorld.KLETKAS_COUNT; i++){
    		for(int j = 0 ; j < MyWorld.KLETKAS_COUNT; j++){
    			float x = MyWorld.POSITION_X[j];
    			float y = MyWorld.positionY[i];    			
    			batcher.drawCenterWithKHeightResize(Assets.circleRegion, x, y, FINGER_SIZE * 0.25f);	
        	}
    	}  
    	batcher.setColor(1,1,1,1);
    }
    
    private void drawMyBall(){
    	batcher.setColor(1.0f, 0.35f, 0.35f, 1);    	
		float x = MyWorld.POSITION_X[MyWorld.currentBallReel];
		float y = MyWorld.positionY[MyWorld.currentBallLine];
		batcher.drawCenterWithKHeightResize(Assets.circleRegion, x, y, FINGER_SIZE);	
		batcher.setColor(1, 1, 1, 1);
    }
    
    private void drawBadBalls(){
    	if(LevelParams.theme == 0){
    		batcher.setColor(0.1f, 0.1f, 0.1f, 1);
    	}else{
    		batcher.setColor(0.9f, 0.9f, 0.9f, 1);
    	}
    	for(int i = 0 ; i < MyWorld.badBallsCount; i++){
    		if( MyWorld.badBallX[i] > MyWorld.BAD_BALL_UNVISIBLE &&
				MyWorld.badBallY[i] > MyWorld.BAD_BALL_UNVISIBLE){
    			batcher.drawCenterWithKHeightResize(
    					Assets.circleRegion, 
    					MyWorld.badBallX[i], 
    					MyWorld.badBallY[i],
    					FINGER_SIZE);
    		}	
    	}
		batcher.setColor(1, 1, 1, 1);
    }
    
    private void drawBonus(){
    	if(MyWorld.bonusesCount % MyWorld.BONUSES_FOR_NEXT_LEVEL_COUNT != 9){
    		if(LevelParams.theme == 0){
    			batcher.setColor(1.0f, 1.0f, 0.0f, 1);
        	}else{
        		batcher.setColor(1.0f, 1.0f, 0.4f, 1);
        	}    		
    	}else{
    		batcher.setColor(1.0f, 0.35f, 0.35f, 1);
    	}  
    	float x = MyWorld.POSITION_X[MyWorld.bonusReel];
		float y = MyWorld.positionY[MyWorld.bonusLine];
		float angle = MyWorld.time * BONUS_ROTATE_SPEED;
		batcher.drawCenterWithKHeight(Assets.starRegion, x, y, angle, FINGER_SIZE);	    		
		batcher.setColor(1, 1, 1, 1);
    }
    
    public void renderWorld() { 
    	drawSetka();
    	drawMyBall();  
    	drawBadBalls();
    	drawBonus();    		
    }    
}