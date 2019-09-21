package gruv.game.smove;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class MyStorage {	
	
    public static boolean soundEnabled = true;   
    
    public static int volumeValueY = 50;
    public static float volume = 0.5f;
    //public static float musicPosition = 0;
     
    private static final int DEFAULT_STATE = MyWorld.WORLD_RUNNING_STATE;      
    
    private static final String preferencesStr = "MyPreferencesSmove";
	private static Preferences prefs = null;
	
	//public static int [] highscores = new int[LevelParams.LEVELS_COUNT];
	public static int  highscores = 0;
		
	public static void calcVolume(){
		volume = ((float)MyStorage.volumeValueY / 100f);
		if(volume < 0){
			volume = 0;
		}
		if(volume > 1){
			volume = 1;
		}
		soundEnabled = (MyStorage.volumeValueY > 0);
	}
		
	public static void load(){
		try {			
			if(prefs == null){
				prefs = Gdx.app.getPreferences(preferencesStr);
			}
			//clearAll();//delete it!!!
			
			soundEnabled = prefs.getBoolean("soen", true);				
			volumeValueY = prefs.getInteger("vovX", 50);			
			calcVolume();	
			//musicPosition = prefs.getFloat("mp", 0);
			Assets.musicSetPosition(prefs.getFloat("mp", 0));
			/*
			for (int i = 0; i < highscores.length; i++) {
	        	 highscores[i] = prefs.getInteger(new StringBuffer("hs").append(String.valueOf(i)).toString(), 0);
	        }*/
		 	highscores = prefs.getInteger(new StringBuffer("hs").toString(), 0);		       
			
			Smove.currentScreenID = prefs.getInteger("csID", Smove.LOADING_SCREEN_ID);	        
			Smove.previousScreenID = prefs.getInteger("psID", Smove.LOADING_SCREEN_ID);   		  
			
			LevelParams.level = prefs.getInteger("lev", 0);   
						
			LevelParams.theme = prefs.getInteger("theme", 0); 			
			
		} catch (Throwable e) {
			// :( It's ok we have defaults
		}
	}
	
	private static String getBaseString(){		
		return "";//(new StringBuffer(String.valueOf(LevelParams.level)).append("_").toString());  		
	}
	
	public static boolean weHaveDataForLevel(){
	    	String baseString = getBaseString();
	    	return (prefs.contains(new StringBuffer("st").append(baseString).toString()));
	}
	
	public static boolean loadWorld(MyWorld world) {
		if(prefs == null){
			prefs = Gdx.app.getPreferences(preferencesStr);
		}			
			
		String baseString = getBaseString();
			
		MyWorld.time = prefs.getFloat(
        		(new StringBuffer("ti").append(baseString).toString()), 0);   
		MyWorld.calcThemeColor();
		
    	MyWorld.state = prefs.getInteger(
        		(new StringBuffer("st").append(baseString).toString()), DEFAULT_STATE);
    	MyWorld.bonusesCount = prefs.getInteger(
        		(new StringBuffer("bc").append(baseString).toString()), 0);
    	
    	MyWorld.currentBallReel = prefs.getInteger(
        		(new StringBuffer("cbr").append(baseString).toString()), 0); 
    	MyWorld.currentBallLine = prefs.getInteger(
        		(new StringBuffer("cbl").append(baseString).toString()), 0); 
    	MyWorld.bonusReel = prefs.getInteger(
        		(new StringBuffer("br").append(baseString).toString()), 0); 
    	MyWorld.bonusLine = prefs.getInteger(
        		(new StringBuffer("bl").append(baseString).toString()), 0);
    	
    	
    	MyWorld.badBallSpeed = prefs.getFloat(
    			(new StringBuffer("bbs").append(baseString).toString()), 
    			Smove.GAME_WIDTH * MyWorld.BAD_BALL_START_COEF);    	
    	MyWorld.addBadBallPeriod = Smove.GAME_WIDTH / MyWorld.badBallSpeed;	
    	
    	
    	MyWorld.badBallsCount = prefs.getInteger(
        		(new StringBuffer("bbc").append(baseString).toString()), 0);

    	String str;
    	for(int i = 0 ; i < MyWorld.badBallsCount; i++){
    		str = (new StringBuffer("d").append(baseString).append(String.valueOf(i)).toString());
    		if(prefs.contains(str)){
    			MyWorld.direction[i] = prefs.getInteger(str, 0);
    		}
    		
    		str = (new StringBuffer("x").append(baseString).append(String.valueOf(i)).toString());
    		if(prefs.contains(str)){
    			MyWorld.badBallX[i] = prefs.getFloat(str, 0);
    		}
    		
    		str = (new StringBuffer("y").append(baseString).append(String.valueOf(i)).toString());
    		if(prefs.contains(str)){
    			MyWorld.badBallY[i] = prefs.getFloat(str, 0);
    		}				
		}
    	
		return true;
	}
	
	private static void saveHighscores(){
    	try {			
			/*for (int i = 0; i <  highscores.length; i++) {
				prefs.putInteger(new StringBuffer("hs").append(String.valueOf(i)).toString(), highscores[i]);
			}*/	
			prefs.putInteger(new StringBuffer("hs").toString(), highscores);
			
		} catch (Throwable e) {			
		}
    }
	
	public static void saveMusicPosition(){
		try {
			if(prefs == null){
				prefs = Gdx.app.getPreferences(preferencesStr);
			}			
			prefs.putFloat("mp", Assets.musicGetPosition());	
		}catch (Throwable e) {			
		}
	}
		
	public static void save(){
		try {
			if(prefs == null){
				prefs = Gdx.app.getPreferences(preferencesStr);
			}
			
			saveHighscores();
			
			prefs.putBoolean("soen", soundEnabled);
			prefs.putInteger("vovX", volumeValueY);	
			
			//musicPosition = Assets.musicGetPosition();
			prefs.putFloat("mp", Assets.musicGetPosition());	
			
			prefs.putInteger("csID", Smove.currentScreenID);	
			prefs.putInteger("psID", Smove.previousScreenID);	
			
			prefs.putInteger("lev", LevelParams.level);
			//prefs.putLong("hal", LyHash(String.valueOf(LevelParams.level)));
						
			prefs.putInteger("theme", LevelParams.theme);
			prefs.flush();
		} catch (Throwable e) {			
		}
	}
		
	public static void clearAll(){
		try {
			if(prefs == null){
				prefs = Gdx.app.getPreferences(preferencesStr);
			}
			prefs.clear();
			prefs.flush();
		} catch (Throwable e) {			
		}
	}	
	
	public static void saveWorld() {
		if(prefs == null){
			prefs = Gdx.app.getPreferences(preferencesStr);
		}
		
		String baseString = getBaseString();	
			
		prefs.putFloat((new StringBuffer("ti").append(baseString).toString()), MyWorld.time); 	
		prefs.putInteger((new StringBuffer("st").append(baseString).toString()), MyWorld.state);  
		prefs.putInteger((new StringBuffer("bc").append(baseString).toString()), MyWorld.bonusesCount);  
		
		prefs.putInteger((new StringBuffer("cbr").append(baseString).toString()), MyWorld.currentBallReel); 	
		prefs.putInteger((new StringBuffer("cbl").append(baseString).toString()), MyWorld.currentBallLine); 	
		prefs.putInteger((new StringBuffer("br").append(baseString).toString()), MyWorld.bonusReel); 	
		prefs.putInteger((new StringBuffer("bl").append(baseString).toString()), MyWorld.bonusLine); 	
		
		prefs.putFloat((new StringBuffer("bbs").append(baseString).toString()), MyWorld.badBallSpeed); 	
						
		prefs.putInteger((new StringBuffer("bbc").append(baseString).toString()), MyWorld.badBallsCount); 	
    	for(int i = 0 ; i < MyWorld.badBallsCount; i++){
			prefs.putInteger((new StringBuffer("d").append(baseString).append(String.valueOf(i)).toString()), MyWorld.direction[i]); 
			prefs.putFloat((new StringBuffer("x").append(baseString).append(String.valueOf(i)).toString()), MyWorld.badBallX[i]); 
			prefs.putFloat((new StringBuffer("y").append(baseString).append(String.valueOf(i)).toString()), MyWorld.badBallY[i]); 			
		}
	
    	prefs.flush();
	}
	
	public static void addScore(int score) {    	
		if(score > highscores){
    		highscores = score;
    	}
    }   
}
