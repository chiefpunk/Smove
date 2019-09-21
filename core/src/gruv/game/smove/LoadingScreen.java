package gruv.game.smove;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.I18NBundle;

public class LoadingScreen extends MyScreen {
		
	private Smove game = null;
	private OrthographicCamera guiCam;
    private MySpriteBatch batcher;    
    private float progress = 0 ;
    
    private static final float BEGUNOK_Y = (Smove.GAME_HEIGHT * 0.8f);
    private static final float BEGUNOK_Y_SIZE = (Smove.GAME_HEIGHT * 0.005f);
	   
    public LoadingScreen(Smove game) {    	
    	this.game = game;
    
    	game.assets = new AssetManager();
    	
    	loadData();    	
    	
        guiCam = new OrthographicCamera(Smove.GAME_WIDTH, Smove.GAME_HEIGHT);
		guiCam.position.set(Smove.GAME_WIDTH / 2, Smove.GAME_HEIGHT / 2, 0);
		batcher = new MySpriteBatch(); 
    }    
    
    private void loadData(){    	
    	TextureParameter param = new TextureParameter();
    	param.minFilter = TextureFilter.Linear;//.MipMapLinearNearest;//Linear;    	
    	//param.minFilter = TextureFilter.MipMapLinearNearest;//Linear;    	
    	param.magFilter = TextureFilter.Linear;
    	param.genMipMaps = true;    	
    	
    	BitmapFontLoader.BitmapFontParameter bfParam = new BitmapFontLoader.BitmapFontParameter();
    	bfParam.minFilter = TextureFilter.Linear;//.MipMapLinearNearest;//Linear;    	
    	//bfParam.minFilter = TextureFilter.MipMapLinearNearest;//Linear;    	
    	bfParam.magFilter = TextureFilter.Linear;
    	bfParam.genMipMaps = true;   
    	
    	//game.assets.load("image/bg.jpg", Texture.class, param); 
    	game.assets.load("image/atlas0.png", Texture.class, param);
    	game.assets.load("image/atlas1.jpg", Texture.class, param);
    	//game.assets.load("image/atlas2.png", Texture.class, param);
    	//game.assets.load("image/atlas3.png", Texture.class, param);
    	game.assets.load("fonts/font.fnt", BitmapFont.class, bfParam);
    	//game.assets.load("fonts/font2.fnt", BitmapFont.class, bfParam);
    	
    	game.assets.load("bundle/MyBundle",  I18NBundle.class);
    	    	
    	game.assets.load("sound/music.mp3", Music.class);
    	game.assets.load("sound/click_sound.mp3", Sound.class);  
    	game.assets.load("sound/gameOver.mp3", Sound.class); 
    	game.assets.load("sound/bonus.mp3", Sound.class); 
    	game.assets.load("sound/nextLevel.mp3", Sound.class); 
    	game.assets.load("sound/touchDown.mp3", Sound.class); 
    	game.assets.load("sound/touchUp.mp3", Sound.class);
    }
 
    @Override
	public void render (float delta) {
		update(delta);
		draw();
		//update(delta);
	}
    
    public void update(float deltaTime) {
    	if(game.assets.update()){
    		game.setScreen(new GameScreen(game),  Smove.GAME_SCREEN_ID, Smove.LOADING_SCREEN_ID);
    	} 
        progress = game.assets.getProgress();//... left to the reader ...    	
    } 
      
    private void renderLogo() {
      	batcher.drawCenterWithKHeight(Assets.logoRegion, Smove.GAME_WIDTH * 0.5f, Smove.GAME_HEIGHT * 0.5f);
    }
    
    public void draw() {
    	GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);
		
		batcher.begin();
		
		batcher.enableBlending();
        renderLogo();
       
        batcher.draw(Assets.logoBegunok, 0, BEGUNOK_Y, ((float)Smove.GAME_WIDTH * progress), BEGUNOK_Y_SIZE);       
        
        batcher.end();
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

    /*
	@Override
	public void resize(int width, int height) {
	}*/

	@Override
	public void show() {		
	}

	@Override
	public void hide() {
	}
}