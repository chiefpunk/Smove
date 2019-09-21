package gruv.game.smove;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MySpriteBatch extends SpriteBatch{
	
	private static int []digits = new int [10];// пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ 10 пїЅпїЅпїЅпїЅ пїЅ пїЅпїЅпїЅпїЅпїЅ (4пїЅ294пїЅ967пїЅ296)  

	private static final int VERTEX_SIZE = 2 + 1 + 2;// Sprite.VERTEX_SIZE;//пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅ пїЅпїЅпїЅпїЅпїЅпїЅ Sprite
	private static final int SPRITE_SIZE = 4 * VERTEX_SIZE;// Sprite.SPRITE_SIZE;//пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅ пїЅпїЅпїЅпїЅпїЅпїЅ Sprite
	
	private static float[] vertices = new float[SPRITE_SIZE];
	    
	public MySpriteBatch() {
	}	
	
	public void drawCenterWithKHeight(TextureRegion tr, float xCenter, float yCenter){
		drawCenter(tr, xCenter, yCenter, tr.getRegionWidth(), tr.getRegionHeight() * Assets.kHeight);
    }
	
	public void drawCenterWithKHeightResize(TextureRegion tr, float xCenter, float yCenter, float sizeCoef){
		drawCenter(tr, xCenter, yCenter, tr.getRegionWidth() * sizeCoef, tr.getRegionHeight() * Assets.kHeight * sizeCoef);
    }
	
	public void drawCenterWithKHeight(TextureRegion tr, float xCenter, float yCenter, float angle){
		drawCenter(tr, xCenter, yCenter, angle, 
				tr.getRegionWidth(), tr.getRegionHeight() * Assets.kHeight);
    }
	
	public void drawCenterWithKHeight(TextureRegion tr, float xCenter, float yCenter, float angle, float sizeCoef){
		drawCenter(tr, xCenter, yCenter, angle, 
				tr.getRegionWidth() * sizeCoef, tr.getRegionHeight() * sizeCoef * Assets.kHeight);
    }
	
	public void drawCenterWithKHeight(Sprite spr, float xCenter, float yCenter, float angle){		
		draw(spr.getTexture(), getVerticesWithKHeight(spr, 
				xCenter - spr.getWidth() * 0.5f,
				yCenter - spr.getHeight() * 0.5f,
				1, 1, angle), 0, SPRITE_SIZE);		
    }
	
	public void drawCenterWithKHeight(Sprite spr, float xCenter, float yCenter, float angle, float sizeCoef){
		draw(spr.getTexture(), getVerticesWithKHeight(spr, 
				xCenter - spr.getWidth() * 0.5f,
				yCenter - spr.getHeight() * 0.5f,
				sizeCoef, sizeCoef, angle), 0, SPRITE_SIZE);		
    }
	//------------------------------------------------------------------------------------------------	
	private float[] getVerticesWithKHeight (Sprite sprite, 
			float x, float y,
			float scaleX, float scaleY, 
			float rotation) {		
		
		System.arraycopy(sprite.getVertices(), 0, vertices, 0, SPRITE_SIZE);
					
		float localX = -sprite.getOriginX();
		float localY = -sprite.getOriginY();
		float localX2 = localX + sprite.getWidth();
		float localY2 = localY + sprite.getHeight();
		float worldOriginX = x - localX;
		float worldOriginY = y - localY;
		if (scaleX != 1 || scaleY != 1) {
			localX *= scaleX;
			localY *= scaleY;
			localX2 *= scaleX;
			localY2 *= scaleY;
		}
		if (rotation != 0) {
			final float cos = MathUtils.cosDeg(rotation);
			final float sin = MathUtils.sinDeg(rotation);
			final float localXCos = localX * cos;
			final float localXSin = localX * sin;
			final float localYCos = localY * cos;
			final float localYSin = localY * sin;
			final float localX2Cos = localX2 * cos;
			final float localX2Sin = localX2 * sin;
			final float localY2Cos = localY2 * cos;
			final float localY2Sin = localY2 * sin;

			final float x1 = localXCos - localYSin + worldOriginX;
			final float y1 = (localYCos + localXSin) * Assets.kHeight + worldOriginY;
			vertices[X1] = x1;
			vertices[Y1] = y1;

			final float x2 = localXCos - localY2Sin + worldOriginX;
			final float y2 = (localY2Cos + localXSin) * Assets.kHeight + worldOriginY;
			vertices[X2] = x2;
			vertices[Y2] = y2;

			final float x3 = localX2Cos - localY2Sin + worldOriginX;
			final float y3 = (localY2Cos + localX2Sin) * Assets.kHeight + worldOriginY;
			vertices[X3] = x3;
			vertices[Y3] = y3;

			vertices[X4] = x1 + (x3 - x2);
			vertices[Y4] = y3 - (y2 - y1);
		} else {
			final float x1 = localX + worldOriginX;
			final float y1 = localY * Assets.kHeight + worldOriginY;
			final float x2 = localX2 + worldOriginX;
			final float y2 = localY2 * Assets.kHeight + worldOriginY;

			vertices[X1] = x1;
			vertices[Y1] = y1;			

			vertices[X2] = x1;
			vertices[Y2] = y2;

			vertices[X3] = x2;
			vertices[Y3] = y2;

			vertices[X4] = x2;
			vertices[Y4] = y1;
		}
		
		vertices[C1] = getPackedColor();
		vertices[C2] = getPackedColor();
		vertices[C3] = getPackedColor();
		vertices[C4] = getPackedColor();
		
		return vertices;
	}
	//------------------------------------------------------------------------------------------------	
	public void drawCenter(TextureRegion tr, float xCenter, float yCenter){
    	draw(tr, xCenter - tr.getRegionWidth() * 0.5f, yCenter - tr.getRegionHeight() * 0.5f);
    }
	
	public void drawCenter(TextureRegion tr, float xCenter, float yCenter, float w, float h){
    	draw(tr, xCenter - w * 0.5f, yCenter - h * 0.5f, w, h);
    }
	
	public void drawCenter(TextureRegion tr, float xCenter, float yCenter, float angle){
		drawCenter(tr, xCenter, yCenter, angle, 
				tr.getRegionWidth(), tr.getRegionHeight());
    }	
	
	public void drawCenter(TextureRegion tr, float xCenter, float yCenter, float angle, float w, float h){
		draw (tr, xCenter - w * 0.5f, yCenter - h * 0.5f, 
				//0, 0, w, h,
				w * 0.5f, h * 0.5f, w, h,
				1f, 1f, angle);
    }
	//------------------------------------------------------------------------------------------------	
	public void drawLine(float depth, float x1, float y1, float x2, float y2, TextureRegion region) {
    	Vector2 v1 = new Vector2(x1, y1);
    	Vector2 v2 = new Vector2(x2, y2);
    	Vector2 v = v1.sub(v2);
    	float centerX = (x1 + x2) * 0.5f;
    	float centerY = (y1 + y2) * 0.5f;
    	
    	drawCenter(region, centerX, centerY, v.angle(), v.len(), depth);
    }
	//------------------------------------------------------------------------------------------------	
	public void drawDigitsTabloCenter(float xCenter, float yCenter, float scale, int n, int length){// n - number for draw
		drawDigitsTabloCenter(xCenter, yCenter, 1f, scale, n, length);
    }
	
	public void drawDigitsTabloCenter(float xCenter, float yCenter, float alpha, float scale, int n, int length){// n - number for draw
    	//int length = 0 ;
    	for(int i = 0 ;i < 10 ; i++){
    		/*if(n > 0){
    			length++;
    		}*/
    		digits[i] = (n % 10);//цифры в обратном порядке
    		n /= 10;
    	}
    	if(length == 0){// for n = 0
    		length = 1;
    	}
    	
    	float w = (scale * Assets.digitText[digits[0]].getWidth());
    	
      	xCenter -= ((length - 1) * w / 2);
      	for(int i = 0; i < length; i++){      		
      		Assets.digitText[digits[length - i - 1]].draw((Batch)this, alpha, scale, xCenter, yCenter);      
      		xCenter += w;
      	}
    }
	
	public void drawDigitsCenter(float xCenter, float yCenter, float scale, int n){// n - number for draw
		drawDigitsCenter(xCenter, yCenter, 1f, scale, n);
    }
	
	public void drawDigitsCenter(float xCenter, float yCenter, float alpha, float scale, int n){// n - number for draw
    	int length = 0 ;
    	for(int i = 0 ;i < 10 ; i++){
    		if(n > 0){
    			length++;
    		}
    		digits[i] = (n % 10);//пїЅпїЅпїЅпїЅпїЅ пїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ
    		n /= 10;
    	}
    	if(length == 0){// for n = 0
    		length = 1;
    	}
    	
    	float w = (scale * Assets.digitText[digits[0]].getWidth());
    	
      	xCenter -= ((length - 1) * w / 2);
      	for(int i = 0; i < length; i++){      		
      		Assets.digitText[digits[length - i - 1]].draw((Batch)this, alpha, scale, xCenter, yCenter);      
      		xCenter += w;
      	}
    }
	
	private static final float DELTA_X_DIGIT1 = (Smove.GAME_WIDTH * 0.030f);//0.015f
    private static final float DELTA_X_DIGIT2 = (Smove.GAME_WIDTH * 0.060f);//0.030f
   
    public void drawDigitsCenter(float xCenter, float yCenter, float alpha, float scale, int n, int len){// n - number for draw
    	for(int i = 0 ;i < 10 ; i++){
    		digits[i] = (n % 10);//цифры в обратном порядке
    		n /= 10;
    	}
    	    	
    	float w = (scale * Assets.digitText[digits[0]].getWidth());
    	
      	xCenter -= ((len - 1) * w / 2);
      	for(int i = 0; i < len; i++){      		
      		Assets.digitText[digits[len - i - 1]].draw((Batch)this, alpha, scale, xCenter, yCenter);      
      		xCenter += w;
      	}
    }
    
	public void drawTimeCenter(float xCenter, float yCenter, float scale, int time){// time - time for draw
		int min = (time / 60);
		int hour = (min / 60);
		min = (min % 60);
		int sec = (time % 60);    		
		
		drawDigitsCenter(xCenter - DELTA_X_DIGIT2, yCenter, 1f, Smove.TEXT_SCALE_1, hour, 2); 
		Assets.twoDotsText.draw(this, Smove.TEXT_SCALE_1, xCenter - DELTA_X_DIGIT1, yCenter);
		drawDigitsCenter(xCenter, yCenter, 1f, Smove.TEXT_SCALE_1, min, 2);
		Assets.twoDotsText.draw(this, Smove.TEXT_SCALE_1, xCenter + DELTA_X_DIGIT1, yCenter);
		drawDigitsCenter(xCenter + DELTA_X_DIGIT2, yCenter, 1f, Smove.TEXT_SCALE_1, sec, 2);  
    }
	//------------------------------------------------------------------------------------------------	
	public void drawDigitsWhiteCenter(float xCenter, float yCenter, float scale, int n){// n - number for draw
		drawDigitsWhiteCenter(xCenter, yCenter, 1f, scale, n);
    }
	
	public void drawDigitsWhiteCenter(float xCenter, float yCenter, float alpha, float scale, int n){// n - number for draw
    	int length = 0 ;
    	for(int i = 0 ;i < 10 ; i++){
    		if(n > 0){
    			length++;
    		}
    		digits[i] = (n % 10);//пїЅпїЅпїЅпїЅпїЅ пїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ
    		n /= 10;
    	}
    	if(length == 0){// for n = 0
    		length = 1;
    	}
    	
    	float w = (scale * Assets.digitWhiteText[digits[0]].getWidth());
    	
      	xCenter -= ((length - 1) * w / 2);
      	for(int i = 0; i < length; i++){      		
      		Assets.digitWhiteText[digits[length - i - 1]].draw((Batch)this, alpha, scale, xCenter, yCenter);      
      		xCenter += w;
      	}
    }
}