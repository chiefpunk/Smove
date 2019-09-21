package gruv.game.smove;

import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.I18NBundle;

public class Assets {
    
    public static Texture logoTexture = null;
    public static TextureRegion logoRegion = null;
    public static TextureRegion logoBegunok = null;
    
    public static BitmapFont font = null;
	
	protected static I18NBundle myBundle = null;	
	
	public static TextActors confirmExit1Text = null;
	public static TextActors confirmExit2Text = null;
	public static TextActors yesText = null;
	public static TextActors noText = null;	
	public static TextActors versionText = null;
	public static TextActors creatorText = null;
	public static TextActors musicText = null;
	public static TextActors musicLicenseText = null;
	public static TextActors mailText = null;
	public static TextActors urlText = null;
	public static TextActors siteText = null;
		
	public static TextActors levelText = null; 
	public static TextActors highscoreText = null; 
	public static TextActors gameOverText = null;  	
	public static TextActors swipeText = null;
	//public static TextActors yourTurnText = null;
	//public static TextActors info1Text = null;
	//public static TextActors info2Text = null;
	
	//public static TextActors nextSegmentText = null; 	
	//public static TextActors nextSegmentUnText = null;  	
	
	
	public static TextActors tryAgainText = null;
	public static TextActors newGameText = null;
	public static TextActors resumeGameText = null;
	
	public static TextActors timeText = null;
	//public static TextActors distanceText = null;
	//public static TextActors totDistanceText = null;
		
	public static TextActors [] digitText = null;
	public static TextActors [] digitWhiteText = null;
	
	public static TextActors helpText = null;
	public static TextActors twoDotsText = null;
	public static TextActors bonusesText = null;
		
    public static Texture texture0;
    public static Texture texture1;
    //public static Texture texture2;
    
    //public static Sprite shurikenDarkRegion = null;
    //public static Sprite shurikenLightRegion = null;
    
    public static TextureRegion backgroundRegion = null;
      
    public static TextureRegion myAdRegion;
       
    public static TextureRegion soundOnRegion;
    public static TextureRegion soundOffRegion;  
    public static TextureRegion exitRegion;    
    public static TextureRegion newGameRegion;
    public static TextureRegion helpRegion;
    public static TextureRegion themeRegion;
    //public static TextureRegion unknownRegion;
    
    //public static TextureRegion whiteRegion;
    public static TextureRegion greyRegion;
    public static TextureRegion circleRegion;
    public static Sprite starRegion;
    
    public static TextureRegion volumeBegunokRegion;
    public static TextureRegion volumeLineRegion;
    
    public static NinePatch btnNinePatch;
      
    public static Sound clickSound;   
    public static Sound gameOverSound;
    public static Sound bonusSound;
    public static Sound nextLevelSound;
    public static Sound touchDownSound;
    public static Sound touchUpSound;
    
    public static Music music;
    
    public static boolean languageIsRus = false;    
    
    public static float kHeight = 1f;
        
    public static final float MATRIX_MIN_Y = (Smove.GAME_HEIGHT * 0.26f);//0.88
    public static final float MATRIX_MAX_Y = (Smove.GAME_HEIGHT * 0.80f);//(Puzzle2048.GAME_HEIGHT - MATRIX_MIN_Y);//(Puzzle2048.GAME_HEIGHT * 0.79f);//0.88
   
    public static final float MATRIX_MIN_X = (Smove.GAME_WIDTH * 0.02f);//0.88
    public static final float MATRIX_MAX_X = (Smove.GAME_WIDTH - MATRIX_MIN_X);
    
    public static float matrixMinX = MATRIX_MIN_X;
	public static float matrixMaxX = MATRIX_MAX_X;
	public static float matrixSizeX = (matrixMaxX - matrixMinX);	
    
    public static float matrixMaxY = MATRIX_MAX_Y;
    public static float matrixMinY = MATRIX_MIN_Y;
    public static float matrixSizeY = (matrixMaxY - matrixMinY);
        
    public static float [][] textColor = {
    	{0.20f, 0.20f, 0.00f},
    	{1.00f, 1.00f, 0.75f}
    };
      
	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}
	
	public static void loadOnlyDataForLoadingScreen(){			
		logoTexture = loadTexture("image/logo.png");
        logoRegion = new TextureRegion(logoTexture, 0, 0, 512, 512);  
        logoBegunok = new TextureRegion(logoTexture, 264, 92, 2, 2);       
        
        String language = Locale.getDefault().getLanguage();//ru                 
     	languageIsRus = (language.equals("ru"));
    }
	
	public static void calcKHeight(int width, int height){
		float kScreen = ( (float)height / (float)width);
        float kMyWorld = ( (float)Smove.GAME_HEIGHT / (float)Smove.GAME_WIDTH);
        kHeight = (kMyWorld / kScreen);        
        
        matrixMinX = MATRIX_MIN_X;
        matrixMaxX = MATRIX_MAX_X;
    	matrixSizeX = (matrixMaxX - matrixMinX);
        	
    	matrixMaxY = MATRIX_MAX_Y;
        matrixMinY = MATRIX_MIN_Y;
        matrixSizeY = (matrixMaxY  - matrixMinY);
        float matrixSizeYReal = matrixSizeY / kHeight;
        
        if(matrixSizeX < matrixSizeYReal){   
        	float matrixSizeYNew = matrixSizeX * kHeight;
        	float dy = (matrixSizeY - matrixSizeYNew) * 0.5f;
        	matrixSizeY = matrixSizeYNew;
        	matrixMinY += dy;
        	matrixMaxY -= dy;
        	
        }else{
        	if(matrixSizeX > matrixSizeYReal){     
        		float dx = (matrixSizeX - matrixSizeYReal) * 0.5f;
        		matrixMinX += dx;
        		matrixMaxX -= dx;
        		matrixSizeX = matrixSizeYReal;
            }
        }
	}

	public static void load (Smove game) {
	    
	    texture0 = game.assets.get("image/atlas0.png", Texture.class);
	    texture1 = game.assets.get("image/atlas1.jpg", Texture.class);
	    //texture2 = game.assets.get("image/atlas2.png", Texture.class);
	    
	    //shurikenDarkRegion = new Sprite(texture2, 0, 0, 512, 512); 
	    //shurikenLightRegion = new Sprite(texture2, 512, 0, 512, 512);  
	    
	    backgroundRegion = new TextureRegion(texture1, 0, 0, 576, 1024); 
	    
	    myAdRegion = new TextureRegion(texture0, 4, 366, 720, 98);//
	    exitRegion = new TextureRegion(texture0, 316+1, 4+1, 100-2, 99-2);//   
	    newGameRegion = new TextureRegion(texture0, 4+1, 107+1, 100-2, 99-2);// 
	    helpRegion = new TextureRegion(texture0, 628+1, 4+1, 100-2, 99-2);//
	    themeRegion = new TextureRegion(texture0, 107+1, 4+1, 100-2, 99-2);//
    	soundOnRegion = new TextureRegion(texture0, 628+1, 107+1, 100-2, 99-2);//
    	soundOffRegion = new TextureRegion(texture0, 316+1, 107+1, 100-2, 99-2);//
    	
    	//unknownRegion = new TextureRegion(texture0, 210, 4, 100, 180);//
    	    	
    	//whiteRegion = new TextureRegion(texture0, 9, 368, 10, 10);
    	greyRegion = new TextureRegion(texture1, 36, 6, 10, 10);
    	
    	circleRegion = new TextureRegion(texture0, 473, 211, 100, 100);
    	//starRegion = new Sprite(texture0, 421, 4, 68, 66);
    	starRegion = new Sprite(texture0, 107, 107, 64, 64);
       		
    	volumeBegunokRegion = new TextureRegion(texture0, 946-1, 4-1, 54, 27);//
    	volumeLineRegion = new TextureRegion(texture0, 1010, 4, 10, 320);//
    	       	
    	TextureRegion btnRegion = new TextureRegion(texture0, 4+1, 210+1, 152-2, 152-2);
    	btnNinePatch = new NinePatch(btnRegion, 31, 31, 31, 31);
    	//----------------------------------------------------------------------     
		font = game.assets.get("fonts/font.fnt", BitmapFont.class);
		myBundle =  game.assets.get("bundle/MyBundle", I18NBundle.class);
	    //-------------------------------------------------------------------    	   	    	    
	    //text:		  
	    confirmExit1Text = new TextActors(font,  myBundle.get("confirmExit1"));  
	    confirmExit1Text.setColors(textColor);
    	
	    confirmExit2Text = new TextActors(font,  myBundle.get("confirmExit2"));  
	    confirmExit2Text.setColors(textColor);		    	
    	
    	yesText = new TextActors(font,  myBundle.get("yesString"));
    	yesText.setColors(textColor);
    	
    	noText = new TextActors(font,  myBundle.get("noString"));
    	noText.setColors(textColor);
    	
    	levelText = new TextActors(font,  myBundle.get("level"));
    	levelText.setColors(textColor);  
    	
    	highscoreText = new TextActors(font,  myBundle.get("highscore"));
    	highscoreText.setColors(textColor);      	
    	    	
    	gameOverText = new TextActors(font,  myBundle.get("gameOver"));
    	gameOverText.setColors(textColor);   
    	
    	swipeText = new TextActors(font,  myBundle.get("swipe"));
    	swipeText.setColors(textColor); 
    	/*
    	yourTurnText = new TextActors(font,  myBundle.get("yourTurn"));
    	yourTurnText.setColors(textColor); 
    	
    	info1Text = new TextActors(font,  myBundle.get("info1"));
    	info1Text.setColors(textColor); 
    	
    	info2Text = new TextActors(font,  myBundle.get("info2"));
    	info2Text.setColors(textColor);     	
    	
    	nextSegmentText = new TextActors(font,  myBundle.get("nextSegment"));
    	nextSegmentText.setColors(textColor); 
    	
    	nextSegmentUnText = new TextActors(font,  myBundle.get("nextSegmentUn"));
    	nextSegmentUnText.setColors(textColor); 
    	*/
    	tryAgainText = new TextActors(font,  myBundle.get("tryAgain"));
    	tryAgainText.setColors(textColor);  
    	
    	newGameText = new TextActors(font,  myBundle.get("newGame"));
    	newGameText.setColors(textColor);  
    	
    	resumeGameText = new TextActors(font,  myBundle.get("resumeGame"));
    	resumeGameText.setColors(textColor);  
    	
    	twoDotsText = new TextActors(font, ":");
    	twoDotsText.setColors(textColor);
    	
    	bonusesText = new TextActors(font, myBundle.get("bonuses"));
    	bonusesText.setColors(textColor);
        	
    	helpText = new TextActors(font, myBundle.get("help"));
    	helpText.setColors(textColor);
       	
    	timeText = new TextActors(font, myBundle.get("time"));
    	timeText.setColors(textColor);
    	/*
    	distanceText = new TextActors(font, myBundle.get("distance"));
    	distanceText.setColors(textColor);
    	
    	totDistanceText = new TextActors(font, myBundle.get("totDistance"));
    	totDistanceText.setColors(textColor);
    	*/
    	    	
    	digitText = new TextActors [10];
    	for(int i = 0 ; i < digitText.length; i++){
    		digitText[i] = new TextActors(font, String.valueOf(i));
    		digitText[i].setColors(textColor);  
    	}    	
    	
    	digitWhiteText = new TextActors [10];
    	for(int i = 0 ; i < digitWhiteText.length; i++){
    		digitWhiteText[i] = new TextActors(font, String.valueOf(i));
    		digitWhiteText[i].setColor(0, 0, 0, 1);
    	}    	
    
    	versionText = new TextActors(font,  myBundle.get("version") + Smove.listener.getVersion());
    	versionText.setColors(textColor); 
    	
    	creatorText = new TextActors(font,  myBundle.get("creator"));
    	creatorText.setColors(textColor); 
    	
    	musicText = new TextActors(font,  myBundle.get("music"));
    	musicText.setColors(textColor); 
    	
    	musicLicenseText = new TextActors(font,  myBundle.get("musicLicense"));
    	musicLicenseText.setColors(textColor);     	
    	
    	mailText = new TextActors(font,  myBundle.get("mail"));
    	mailText.setColors(textColor); 
    	
    	urlText = new TextActors(font,  myBundle.get("url"));
    	urlText.setColors(textColor); 
    	
    	siteText = new TextActors(font,  myBundle.get("site"));
    	siteText.setColors(textColor); 
    	// end text
    	//-------------------------------------------------------------------  
		clickSound = game.assets.get("sound/click_sound.mp3", Sound.class);
		gameOverSound = game.assets.get("sound/gameOver.mp3", Sound.class);
		bonusSound = game.assets.get("sound/bonus.mp3", Sound.class);
		nextLevelSound = game.assets.get("sound/nextLevel.mp3", Sound.class);
        touchDownSound = game.assets.get("sound/touchDown.mp3", Sound.class);
        touchUpSound = game.assets.get("sound/touchUp.mp3", Sound.class);
        
        music = game.assets.get("sound/music.mp3", Music.class);
		music.setLooping(true);
		musicIsPlaing = false;
		//initPositionMusic();
		//playMusic();
	}	
	
	private static boolean musicIsPlaing = false;
	
	public static void playMusic() {
		if (MyStorage.soundEnabled){
			if(music != null){
				music.setVolume(MyStorage.volume * 0.25f);
				if (!musicIsPlaing){
					//music.setPosition(180);
					music.play();
					musicIsPlaing = true;
				}
			}
		}		
	}
	
	public static void pauseMusic() {
		if(music != null){
			music.pause();
			musicIsPlaing = false;
		}
	}
	
	public static void stopMusic() {
		if(music != null){
			music.stop();
			musicIsPlaing = false;
		}
	}	
	
	public static void initPositionMusic() {
		if(music != null){
			music.setPosition(0);
		}
	}
	
	public static void musicSetPosition(float value) {
		if(music != null){
			music.setPosition(value);
		}
	}
	
	public static float musicGetPosition() {
		if(music != null){
			return music.getPosition();
		}else{
			return 0;
		}
	}

	public static void playSound (Sound sound) {
		if (MyStorage.soundEnabled){
			sound.play(MyStorage.volume);
		}
	}
}
