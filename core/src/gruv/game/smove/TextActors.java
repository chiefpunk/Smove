package gruv.game.smove;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class TextActors extends Image {
	
	TextActor [] textActor;
	TextActor textActorDark;	
	
    public TextActors(BitmapFont bitmapFont, String text){
    	textActor = new TextActor[LevelParams.THEME_DIM];
    	for(int i = 0 ; i < LevelParams.THEME_DIM; i++){
    		textActor[i] = new TextActor(bitmapFont, text);
    	}
    }
       
    public float getWidth(){
    	return textActor[LevelParams.theme].getWidth();    	  		
    }
    
    public float getHeight(){
    	return textActor[LevelParams.theme].getHeight();  
    }
    
   
    public void setColors(float [][] color) {
    	for(int i = 0 ; i < LevelParams.THEME_DIM; i++){
    		textActor[i].setColor(color[i][0], color[i][1], color[i][2], 1); 
    	}
	}

    
    public void draw(Batch batch, float scale, float centerX, float centerY){  
    	draw(batch, 1f, scale, centerX, centerY);
    }
    
    public void draw(Batch batch, float scale, float centerX, float centerY, boolean recalcMatrix){  
    	draw(batch, 1f, scale, centerX, centerY, recalcMatrix);
    }
       
    public void draw(Batch batch, float parentAlpha, float scale, float centerX, float centerY){   
    	textActor[LevelParams.theme].draw(batch, parentAlpha, scale, centerX, centerY);
    }
    
    public void draw(Batch batch, float parentAlpha, float scale, float centerX, float centerY, boolean recalcMatrix){     	
    	textActor[LevelParams.theme].draw(batch, parentAlpha, scale, centerX, centerY, recalcMatrix);
    }
    
    public void updateText(final String text) {
    	for(int i = 0 ; i < LevelParams.THEME_DIM; i++){
    		textActor[i].updateText(text); 
    	}
    }
    
    public void setAlpha(float a) {
    	textActor[LevelParams.theme].setAlpha(a);
    }
    
    public void draw2(Batch batch, float scaleX, float scaleY, float centerX, float centerY){  
    	textActor[LevelParams.theme].draw2(batch, scaleX, scaleY, centerX, centerY);
    }
    
    public void draw2(Batch batch, float scaleX, float scaleY, float centerX, float centerY, boolean recalcMatrix){  
    	textActor[LevelParams.theme].draw2(batch, scaleX, scaleY, centerX, centerY, recalcMatrix);
    }
}