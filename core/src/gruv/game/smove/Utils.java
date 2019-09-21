package gruv.game.smove;

import java.util.Random;

public class Utils {
    private static Random random = new Random();	

	public Utils() {			
	}
	
	public static void generateShuffle(int [] data) {
		fill(data);
		shuffle(data);
	}
	
	public static void fill(int [] data) {
		for (int i = 0 ; i < data.length ; i++ ){
			data[i] = i ;
		}	
	}

	public static void shuffle(int [] data) {		
		for (int i = data.length; i > 1; i--) {
			int i1 = i - 1;				
			int i2 = random.nextInt(i);//iRand(0, (i - 1)); 
			int tmp = data[i2];
			data[i2] = data[i1];
			data[i1] = tmp;
		}
	}
	
	/*
	 * return rand in [0.0, 1.0)
	 * */
	public static float rand(){
		return random.nextFloat();
	}
	
	/*
	 * return rand in [min, max)
	 * */
	public static int rand(int min, int max){
		return (min + random.nextInt(max - min)); 		
	}	
}