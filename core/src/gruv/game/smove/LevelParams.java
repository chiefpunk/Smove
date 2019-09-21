package gruv.game.smove;

public class LevelParams {	
	//public static final int LEVELS_COUNT = 1;//30;//50;	
	
	public static final int THEME_DIM = 2;
	
	public static final float [][] LIGHT_THEME_COLOR = {    	
    	{ 0.5f, 0.5f, 1.0f},
    	{ 0.5f, 1.0f, 0.5f},
    	
    	{ 0.5f, 1.0f, 1.0f},
    	{ 1.0f, 0.5f, 1.0f},
    	{ 1.0f, 1.0f, 0.5f},
	};
	
	public static final float [][] DARK_THEME_COLOR = {    	
    	{ 0.0f, 0.0f, 0.7f},
    	{ 0.0f, 0.5f, 0.0f},
    	
    	{ 0.0f, 0.5f, 0.5f},
    	{ 0.5f, 0.0f, 0.5f},
    	{ 0.5f, 0.5f, 0.0f},
    };
	
	public static float [] themeColor = { 1.0f, 1.0f, 1.0f};//new float [3];	
	
	public static int level = 0;	
	public static int theme = 0;//0-2	
			
	public LevelParams(){	
	}		
}
