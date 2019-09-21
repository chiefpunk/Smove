package gruv.game.smove;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
    public final Vector2 position;
    public final Rectangle bounds;
    public final Circle boundsCircle;
    
    public GameObject(float x, float y, float width, float height) {
        this.position = new Vector2(x,y);
        this.bounds = new Rectangle(x-width/2, y-height/2, width, height);
        this.boundsCircle = new Circle(x, y, (width + height) / 4);
    }
    
    public void setXY(float x, float y){
   	    this.position.x = x;
   	    this.position.y = y;
   	  
   	    //this.bounds.lowerLeft.x = (x - this.bounds.width / 2);
   	    //this.bounds.lowerLeft.y = (y - this.bounds.height / 2);
   	    this.bounds.setCenter(x, y);
   	 
   	    //this.boundsCircle.center.x = x;
   	    //this.boundsCircle.center.y = y;
   	    this.boundsCircle.setPosition(x, y);
    }
    
    public void setXYWH(float x, float y, float width, float height){
   	    this.position.x = x;
   	    this.position.y = y;
   	  
   	    //this.bounds.lowerLeft.x = (x - this.bounds.width / 2);
   	    //this.bounds.lowerLeft.y = (y - this.bounds.height / 2);
   	    this.bounds.setCenter(x, y);
   	    
   	    this.bounds.width = width;
   	    this.bounds.height = height;
   	 
   	    //this.boundsCircle.center.x = x;
   	    //this.boundsCircle.center.y = y;
   	    this.boundsCircle.setPosition(x, y);
   	    
   	    this.boundsCircle.radius = (width + height) / 4;
    }
    
    public void addXY(float addx, float addy){
   	    this.position.x += addx;
   	    this.position.y += addy;
   	  
   	    //this.bounds.lowerLeft.x += addx ;
   	    //this.bounds.lowerLeft.y += addy ;
   	    this.bounds.setCenter(this.bounds.getX() + addx, 
   	    		this.bounds.getY() + addy);
   	    
   	    //this.boundsCircle.center.x += addx;
   	    //this.boundsCircle.center.y += addy;
   	    this.boundsCircle.setPosition(
   	    		this.boundsCircle.x + addx, 
   	    		this.boundsCircle.y + addy);
    }
}
