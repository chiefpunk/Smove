package gruv.game.smove;

import java.util.Random;

public class MyWorld {
	public interface WorldListener {		
		public void gameOver();
		public void bonus();
		public void nextLevel();
		public void clickSound();		
		public void touchDownSound();
		public void touchUpSound();
	}

	public static final int WORLD_RUNNING_STATE = 0;
	public static final int WORLD_PAUSED_STATE = 1;
	public static final int WORLD_GAME_OVER_STATE = 2;
	
	public static final float WORLD_WIDTH = Smove.GAME_WIDTH;
	public static final float WORLD_HEIGHT = Smove.GAME_HEIGHT;
	
	public static final int NORMAL_MODE = 0;
	public static final int HELP_MODE = 1;
	
	public static final float ALPHA = 0.7f;
	public static int mode = NORMAL_MODE;
	
	public static int state = WORLD_RUNNING_STATE;//WORLD_PAUSED_STATE;//WORLD_READY_STATE;
		
	public static boolean showMyAd = false;
	public static Random random = new Random();
	// --------------------------------------------
	public static boolean itIsDebugMode = false;
	private static ScreenPassword screenPassword = null;
	// --------------------------------------------	
	public static WorldListener listener;
	
	private static final float THEME_TIME1 = 10f;//3f;//10f;
	private static final float THEME_TIME2 = 2f;//1f;//2f;
	private static final float THEME_TIME = THEME_TIME1 + THEME_TIME2;
	private static int themeIndex = 0;
	
	public static float time = 0;
	
	public static final int KLETKAS_COUNT = 3;	
	private static final float KLETKA_X_SIZE = Smove.GAME_WIDTH * 0.15f;
	private static final float KLETKA_X0 = (Smove.GAME_WIDTH  - KLETKA_X_SIZE * (KLETKAS_COUNT - 1)) * 0.5f;
	public static float [] POSITION_X = {
		KLETKA_X0,
		KLETKA_X0 + KLETKA_X_SIZE,
		KLETKA_X0 + KLETKA_X_SIZE * 2,
	};
	
	private static float kletkaYSize = KLETKA_X_SIZE;
	private static float kletkaY0 = (Smove.GAME_HEIGHT  - kletkaYSize * (KLETKAS_COUNT - 1)) * 0.5f;
	public static float [] positionY = new float [KLETKAS_COUNT];
	
	public static int currentBallReel = 1 ;
	public static int currentBallLine = 1 ;
	
	public static int bonusReel = 0 ;
	public static int bonusLine = 0 ;
	
	private static final float CRITICAL_DRAGGED_VALUE = Smove.GAME_WIDTH * 0.05f;
	
	private static float touchedDownX = (-1);
	private static float touchedDownY = (-1);
	private static boolean currentBallDragged = false;
	
	private static final int MAX_BAD_BALLS_COUNT = 100;
	private static final float SMALL_BALLS_DIST = 78;
	
	public static final float BAD_BALL_UNVISIBLE = (-1000) ;
	public static final float BAD_BALL_START_POSITION = (-Smove.GAME_WIDTH * 0.05f) ;
	public static float BAD_BALL_START_COEF = 0.3f;	
	public static float badBallSpeed = Smove.GAME_WIDTH * BAD_BALL_START_COEF;
	public static float addBadBallPeriod = 2f;
	public static float lastAddBadBallTime = 0f;
	private static int lastAddBadBallIndex = (-1);
	
	public static float [] badBallX = new float [MAX_BAD_BALLS_COUNT] ;
	public static float [] badBallY = new float [MAX_BAD_BALLS_COUNT] ;
	public static int [] direction  = new int [MAX_BAD_BALLS_COUNT] ;// 
	/*
	 * 0 -  left -> right
	 * 1 -  right -> left
	 * 2 -  up -> down
	 * 3 -  down -> up
	*/
	public static int badBallsCount = 0 ;
	
	public static int bonusesCount = 0 ;
	
	private static final float SPEED_INCR_COEF = 1.25f;
	public static final int BONUSES_FOR_NEXT_LEVEL_COUNT = 10;
	
	public static boolean showSwipeInfo = true;
	
	// --------------------------------------------
	
	public MyWorld(WorldListener listener) {
		MyWorld.listener = listener;					
		screenPassword = new ScreenPassword();
		screenPassword.setScreenSize((int)WORLD_WIDTH, (int)WORLD_HEIGHT);		
		//init();
	}
		
	private static void calcShowMyAd(){ 
		showMyAd = (random.nextInt(10) == 0); 
	}	
	
	private static void updateBadBalls(float deltaTime){
		for(int i = 0 ; i < badBallsCount; i++){
    		if( badBallX[i] > BAD_BALL_UNVISIBLE &&
				badBallY[i] > BAD_BALL_UNVISIBLE){
    			if(direction[i] == 0){
    				badBallX[i] += (badBallSpeed * deltaTime);
    			}
    			if(direction[i] == 1){
    				badBallX[i] -= (badBallSpeed * deltaTime);
    			}
    			if(direction[i] == 2){
    				badBallY[i] += (badBallSpeed * deltaTime);
    			}
    			if(direction[i] == 3){
    				badBallY[i] -= (badBallSpeed * deltaTime);
    			}
    		}	
    	}
	}
	
	private static boolean ballsTouched(){
		for(int i = 0 ; i < badBallsCount; i++){
    		if( badBallX[i] > BAD_BALL_UNVISIBLE &&
				badBallY[i] > BAD_BALL_UNVISIBLE){
    			float dx = Math.abs(badBallX[i] - POSITION_X[currentBallReel]);
    			float dy = Math.abs(badBallY[i] - positionY[currentBallLine]);
    			if( dx < SMALL_BALLS_DIST && 
					dy < SMALL_BALLS_DIST * Assets.kHeight){
    				return true;
    			}
    		}	
    	}
		return false;
	}
	
	private static void checkTouchBalls(){
		if(ballsTouched()){
			lose();
		}
	}	
	
	private static void checkTouchBonus(){
		if(currentBallReel == bonusReel && currentBallLine == bonusLine){
			addBonus();
			bonusesCount++ ;
			if(bonusesCount % BONUSES_FOR_NEXT_LEVEL_COUNT == 0){
				listener.nextLevel();
				LevelParams.level++;				
				if(LevelParams.level > 2){
					badBallSpeed *= SPEED_INCR_COEF;
					addBadBallPeriod = Smove.GAME_WIDTH / badBallSpeed;	
				}			
			}else{
				listener.bonus();
			}
		}
	}
	
	public static void updateRunningState(float deltaTime) {	
		time += deltaTime;	
		if(!showSwipeInfo){
			lastAddBadBallTime += deltaTime;
		}
		updateBadBalls(deltaTime);
		calcThemeColor();
		checkTouchBalls();
		checkTouchBonus();
		if(lastAddBadBallTime >= addBadBallPeriod){
			lastAddBadBallTime -= addBadBallPeriod;
			addRandomBadBall(false);
			if(LevelParams.level == 1){
				addRandomBadBall(false);
			}
			if(LevelParams.level > 1){
				addRandomBadBall(true);
			}
		}
	}
	
	public static void calcThemeColor(){
		float t = (time % THEME_TIME);
		themeIndex = (int)(time / THEME_TIME);
		themeIndex = (themeIndex % LevelParams.LIGHT_THEME_COLOR.length);
		float k = 0;
		if(t < THEME_TIME1){
			k = 0 ;
		}else{
			k = (t - THEME_TIME1) / THEME_TIME2;
		}
		int indexFrom = themeIndex ;
		int indexTo = (themeIndex + 1);
		if(indexTo >= LevelParams.LIGHT_THEME_COLOR.length){
			indexTo = 0;
		}
		
		for(int i = 0 ; i < 3 ; i++){			
			if(LevelParams.theme == 0){
				LevelParams.themeColor[i] = LevelParams.LIGHT_THEME_COLOR[indexFrom][i] * (1 - k) + 
						LevelParams.LIGHT_THEME_COLOR[indexTo][i] * k;
			}else{
				LevelParams.themeColor[i] = LevelParams.DARK_THEME_COLOR[indexFrom][i] * (1 - k) + 
						LevelParams.DARK_THEME_COLOR[indexTo][i] * k;
			}
			
		}
	}	
	
	public static void touchDown(float x, float y) {
		touchedDownX = x;
		touchedDownY = y;
		currentBallDragged = false;
		mode = NORMAL_MODE;
		listener.touchDownSound();
	}

	public static void touchDragged(float x, float y) {
		if(touchedDownX < 0 || touchedDownY < 0){
			touchedDownX = x;
			touchedDownY = y;
			currentBallDragged = false;
		}else{
			if(!currentBallDragged){
				float dx = x - touchedDownX;
				float dy = y - touchedDownY;
				float dxAbs = Math.abs(dx);
				float dyAbs = Math.abs(dy);
				if(dxAbs > dyAbs){
					if(dxAbs > CRITICAL_DRAGGED_VALUE){
						if(dx > 0){
							if(currentBallReel < (KLETKAS_COUNT - 1)){
								currentBallReel++;
								currentBallDragged = true;
								listener.touchUpSound();
								showSwipeInfo = false;
							}
						}else{//dx < 0
							if(currentBallReel > 0){
								currentBallReel--;
								currentBallDragged = true;
								listener.touchUpSound();
								showSwipeInfo = false;
							}
						}
					}
				}else{//(dxAbs <= dyAbs)
					if(dyAbs > CRITICAL_DRAGGED_VALUE){
						if(dy > 0){
							if(currentBallLine < (KLETKAS_COUNT - 1)){
								currentBallLine++;
								currentBallDragged = true;
								listener.touchUpSound();
								showSwipeInfo = false;
							}
						}else{//dy < 0
							if(currentBallLine > 0){
								currentBallLine--;
								currentBallDragged = true;
								listener.touchUpSound();
								showSwipeInfo = false;
							}
						}
					}
				}				
			}
		}
		screenPassword.check((int)x, (int)y);
	}

	public static void touchUp(float x, float y) {		
		touchedDownX = (-1);
		touchedDownY = (-1);
		currentBallDragged = false;
		if(screenPassword.passwordIsCorrect()){
			  itIsDebugMode = true;
		}
		screenPassword.clearInputNumber();
	}
	
	private static void lose(){
		calcShowMyAd();		
		Assets.stopMusic();
		MyStorage.saveMusicPosition();
		listener.gameOver();		
		state = WORLD_GAME_OVER_STATE;
		MyStorage.addScore((int)time);
	}
	
	private static int getFreeBadBallIndex(){
		for(int i = 0 ; i < badBallsCount; i++){
    		if( badBallX[i] > BAD_BALL_UNVISIBLE &&
				badBallY[i] > BAD_BALL_UNVISIBLE){    
    			if(direction[i] == 0){
    				if(badBallX[i] > (Smove.GAME_WIDTH - BAD_BALL_START_POSITION)){
    					return i;
    				}
    			}
    			if(direction[i] == 1){
    				if(badBallX[i] < BAD_BALL_START_POSITION){
    					return i;
    				}
    			}
    			if(direction[i] == 2){
    				if(badBallY[i] > (Smove.GAME_WIDTH - BAD_BALL_START_POSITION)){
    					return i;
    				}
    			}
    			if(direction[i] == 3){
    				if(badBallY[i] < BAD_BALL_START_POSITION){
    					return i;
    				}
    			}
    		}    		
		}
		return badBallsCount;
	}
	
	private static void addRandomBadBall(int index, boolean setPreviousDirection){
		if(index < MAX_BAD_BALLS_COUNT){	
			if(setPreviousDirection && lastAddBadBallIndex >= 0){
				direction[index] =  direction[lastAddBadBallIndex];
			}else{
				direction[index] = random.nextInt(4);
			}			
			
			if(direction[index] == 0){
				badBallX[index] = BAD_BALL_START_POSITION;
				int ind = random.nextInt(3);
				if(lastAddBadBallIndex >= 0){
					while(direction[index] == direction[lastAddBadBallIndex] &&
							positionY[ind] == badBallY[lastAddBadBallIndex]){
						 ind = random.nextInt(3);
					};
				}
				badBallY[index] = positionY[ind];
			}
			if(direction[index] == 1){
				badBallX[index] = Smove.GAME_WIDTH - BAD_BALL_START_POSITION;
				int ind = random.nextInt(3);
				if(lastAddBadBallIndex >= 0){
					while(direction[index] == direction[lastAddBadBallIndex] &&
							positionY[ind] == badBallY[lastAddBadBallIndex]){
						 ind = random.nextInt(3);
					};
				}
				badBallY[index] = positionY[ind];
			}
			if(direction[index] == 2){
				badBallY[index] = BAD_BALL_START_POSITION;
				int ind = random.nextInt(3);
				if(lastAddBadBallIndex >= 0){
					while(direction[index] == direction[lastAddBadBallIndex] &&
							POSITION_X[ind] == badBallX[lastAddBadBallIndex]){
						 ind = random.nextInt(3);
					};
				}
				badBallX[index] = POSITION_X[ind];
			}
			if(direction[index] == 3){
				badBallY[index] = (Smove.GAME_HEIGHT - BAD_BALL_START_POSITION);
				int ind = random.nextInt(3);
				if(lastAddBadBallIndex >= 0){
					while(direction[index] == direction[lastAddBadBallIndex] &&
							POSITION_X[ind] == badBallX[lastAddBadBallIndex]){
						 ind = random.nextInt(3);
					};
				}
				badBallX[index] = POSITION_X[ind];
			}		
		}		
	}
	
	private static void addRandomBadBall(boolean setPreviousDirection){		
		int index = getFreeBadBallIndex();			
		if(index == badBallsCount){
			badBallsCount++;
		}		
		addRandomBadBall(index, setPreviousDirection);	
		lastAddBadBallIndex = index;
	}
	
	private static void addBonus(){
		if(bonusesCount == 0 && 
			currentBallReel == 1 &&
			currentBallLine == 1){
			bonusReel = 0;
			bonusLine = 0;
		}else{
			do{
				bonusReel = random.nextInt(KLETKAS_COUNT);
				bonusLine = random.nextInt(KLETKAS_COUNT);
			}while(currentBallReel == bonusReel && currentBallLine == bonusLine);
		}
	}
	
	public static void init() {	
		showMyAd = false;
		itIsDebugMode = false;
		
		touchedDownX = (-1);
		touchedDownY = (-1);
		currentBallDragged = false;
		
		kletkaYSize = KLETKA_X_SIZE * Assets.kHeight;
		kletkaY0 = (Smove.GAME_HEIGHT  - kletkaYSize * (KLETKAS_COUNT - 1)) * 0.5f;
		positionY[0] = kletkaY0;
		positionY[1] = kletkaY0 + kletkaYSize;
		positionY[2] = kletkaY0 + kletkaYSize * 2;
		
		currentBallReel = 1 ;
		currentBallLine = 1 ;
		LevelParams.level = 0 ;
		
		bonusesCount = 0 ;
		addBonus();
		
		lastAddBadBallIndex = (-1);
		
		//Assets.stopMusic();		
		Assets.initPositionMusic();
		Assets.pauseMusic();
		Assets.playMusic();
		
		badBallsCount = 0 ;
		//addRandomBadBall(false);
		
		badBallSpeed = Smove.GAME_WIDTH * BAD_BALL_START_COEF;
		addBadBallPeriod = Smove.GAME_WIDTH / badBallSpeed;			
		
		time = 0;
		lastAddBadBallTime = 0f;
		calcThemeColor();
		
		state = WORLD_RUNNING_STATE;
		mode = NORMAL_MODE;	
		
		showSwipeInfo = true;
	}
	
	public static void updateGameFinishState() {	
		state = WORLD_RUNNING_STATE;
		init();
	}
}