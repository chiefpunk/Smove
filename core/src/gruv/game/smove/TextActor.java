package gruv.game.smove;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
//import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
//import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

public class TextActor extends Image {
	
	private Matrix4 matrix = new Matrix4();
	private Matrix4 matrix0 = new Matrix4();
	
	private boolean drawDone = false;
	
	private BitmapFontCache bitmapFontCache = null;
	//private TextBounds textBounds = null;
	private GlyphLayout glyphLayout = null;
	private String text = null;
	private float r = 0 ;
	private float g = 0 ;
	private float b = 0 ;
	private float a = 0 ;
	
    public TextActor(BitmapFont bitmapFont, String text){
    	bitmapFontCache = new BitmapFontCache(bitmapFont);
    	this.text = text;
    	bitmapFontCache.setText(this.text, 0, 0);
    	matrix0.idt();
    	drawDone = false;
    }
    
    public float getWidth(){
    	if (glyphLayout != null){
    		return glyphLayout.width;
    	}else{
    		return 0;
    	}
    }
    
    public float getHeight(){
    	if (glyphLayout != null){
    		return glyphLayout.height;
    	}else{
    		return 0;
    	}
    }
   
    public void setColor(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        bitmapFontCache.setColor(this.r, this.g, this.b, this.a);
    }
    
    public void setAlpha(float a) {
        this.a = a;
        bitmapFontCache.setColor(this.r, this.g, this.b, this.a);
    }
    
    public void draw(Batch batch, float scale, float centerX, float centerY){  
    	draw(batch, 1f, scale, centerX, centerY);
    }
    
    public void draw(Batch batch, float scale, float centerX, float centerY, boolean recalcMatrix){  
    	draw(batch, 1f, scale, centerX, centerY, recalcMatrix);
    }
       
    public void draw(Batch batch, float parentAlpha, float scale, float centerX, float centerY){    	
    	glyphLayout = bitmapFontCache.setText(this.text, 0, 0, 0, Align.center, false);
    	//textBounds = bitmapFontCache.setMultiLineText(this.text, 0, 0, 0, HAlignment.CENTER);
    	
    	bitmapFontCache.setColor(this.r, this.g, this.b, this.a * parentAlpha);
    	matrix.idt();
    	
        matrix.translate(centerX, centerY + glyphLayout.height * scale * 0.5f, 0);  
        matrix.scale(scale, scale, 1);       
        batch.setTransformMatrix(matrix);  
        bitmapFontCache.draw(batch);
        
        
        batch.setTransformMatrix(matrix0);
        
    }
    
    public void draw(Batch batch, float parentAlpha, float scale, float centerX, float centerY, boolean recalcMatrix){     	
    	if(drawDone && !recalcMatrix){
    		 batch.setTransformMatrix(matrix); 
    		 bitmapFontCache.draw(batch);
    		 batch.setTransformMatrix(matrix0);
    	}else{
    		draw(batch, parentAlpha, scale, centerX, centerY);    		
    	}
    }
    
    
    
    
    
    
    
    public void draw2(Batch batch, float scaleX, float scaleY, float centerX, float centerY){  
    	draw2(batch, 1f, scaleX, scaleY, centerX, centerY);
    }
    
    public void draw2(Batch batch, float scaleX, float scaleY, float centerX, float centerY, boolean recalcMatrix){  
    	draw2(batch, 1f, scaleX, scaleY, centerX, centerY, recalcMatrix);
    }
       
    public void draw2(Batch batch, float parentAlpha, float scaleX, float scaleY, float centerX, float centerY){    	
    	glyphLayout = bitmapFontCache.setText(this.text, 0, 0, 0, Align.center, false);
    	
    	//textBounds = bitmapFontCache.setMultiLineText(this.text, 0, 0, 0, HAlignment.CENTER);
    	bitmapFontCache.setColor(this.r, this.g, this.b, this.a * parentAlpha);
    	matrix.idt();
    	
        //matrix.translate(centerX, centerY + textBounds.height * scaleY * 0.5f, 0);        
        matrix.translate(centerX, centerY + glyphLayout.height * scaleY * 0.5f, 0);  
        matrix.scale(scaleX, scaleY, 1);       
        batch.setTransformMatrix(matrix);  
        bitmapFontCache.draw(batch);
        
        
        batch.setTransformMatrix(matrix0);
        drawDone = true;    	
    }
    
    public void draw2(Batch batch, float parentAlpha, float scaleX, float scaleY, float centerX, float centerY, boolean recalcMatrix){     	
    	if(drawDone && !recalcMatrix){
    		 //bitmapFontCache.setColor(this.r, this.g, this.b, this.a * parentAlpha);
    		 batch.setTransformMatrix(matrix); 
    		 bitmapFontCache.draw(batch);
    		 batch.setTransformMatrix(matrix0);
    	}else{
    		draw2(batch, parentAlpha, scaleX, scaleY, centerX, centerY);    		
    	}
    }
    
    
    public void updateText(final String text) {
        this.text = text;
    }
}