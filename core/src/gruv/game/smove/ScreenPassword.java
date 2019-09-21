package gruv.game.smove;

public class ScreenPassword {
	
	private int screenWidth = 0;
	private int screenHeight = 0;
	 
	private int h18 ;
	private int w18 ;
	 
	private static final int SETKA_X_DIM = 3;
	private static final int SETKA_Y_DIM = 3;
	 
	private int [][] setkaX;
	private int [][] setkaY;
	 
	private static final int PASSWORD_LENGTH = 7;//3;
	private int [] password;
	 
	private int [] inputNumber;
	private int inputNumberIndex;
	 
	 
	public void clearInputNumber(){
		 for(int i = 0 ; i < PASSWORD_LENGTH ; i++){
			 inputNumber[i] = 0 ;
		 }
		 inputNumberIndex = 0 ;
	}
	 
	public void check(int x, int y){
		if(inputNumberIndex < PASSWORD_LENGTH){
			 if(itIsPasswordIndex(x, y, inputNumberIndex)){
				 //if(inputNumberIndex < PASSWORD_LENGTH){
					 inputNumber[inputNumberIndex] = 1;
				// }
				 inputNumberIndex++;			 
			 }else{	
				 if(inputNumberIndex > 0){
					 for(int j = (inputNumberIndex + 1) ; j < PASSWORD_LENGTH ; j++){
						 if(itIsPasswordIndex(x,y, j)){
							 clearInputNumber();
						 }				 
					 }				 
				 }
			 }	
		}
	}
	
	public boolean passwordIsCorrect(){
		for(int i = 0 ; i < PASSWORD_LENGTH ; i++){
			 if(inputNumber[i] == 0 ){
				 return false;
			 }
		}
		return true;
	}

	public ScreenPassword() {	
		setPassword();
	}	

	public ScreenPassword(int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		calcSetka();
		setPassword();
	}
	
	/*
	 * 6 7 8	
	 * 3 4 5
	 * 0 1 2
	 * 
	 * 
	 * 2 5 8	
	 * 1 4 7
	 * 0 3 6
	*/
	private void setPassword(){
		password = new int [PASSWORD_LENGTH];
		/*
		password[0] = 8;
		password[1] = 4;
		password[2] = 0;	
		password[3] = 3;
		password[4] = 6;
		*/
		
		password[0] = 2;
		password[1] = 5;
		password[2] = 8;		
		password[3] = 4;
		password[4] = 0;	
		password[5] = 3;	
		password[6] = 6;
		
		inputNumber = new int [PASSWORD_LENGTH];
	}
	
	public void setScreenSize(int screenWidth, int screenHeight){
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		calcSetka();
	}
	
	private void calcSetka(){
		setkaX = new int [SETKA_Y_DIM][SETKA_X_DIM];
		setkaY = new int [SETKA_Y_DIM][SETKA_X_DIM];
		float h9 = ((float)screenHeight / (float)9);
		float w9 = ((float)screenWidth / (float)9);
		h18 = (int)((float)screenHeight / (float)18);
		w18 = (int)((float)screenWidth / (float)18);
		for(int i = 0 ; i < SETKA_Y_DIM ; i++){
			for(int j = 0 ; j < SETKA_X_DIM ; j++){
				setkaX[i][j] = (int)(((float)1.5 + 3 * i) * w9);
				setkaY[i][j] = (int)(((float)1.5 + 3 * j) * h9);
			}
		}
	}
	
	/*
	 * (-1) - no password
	 * 0 - 2 - password index
	*/
	public boolean itIsPasswordIndex(int x, int y, int paswIndex){
		return (password[paswIndex] == getSetkaIndex(x, y));
	}
	
	/*
	 * (-1) - no setka
	 * 0 - 8 - setka index
	*/
	private int getSetkaIndex(int x, int y){
		for(int i = 0 ; i < SETKA_Y_DIM ; i++){
			for(int j = 0 ; j < SETKA_X_DIM ; j++){
				if( x >= (setkaX[i][j] - w18) && 
					x <= (setkaX[i][j] + w18) &&
					y >= (setkaY[i][j] - h18) && 
					y <= (setkaY[i][j] + h18)){
					return (i * 3 + j);
				}
			}
		}
		return (-1);
	}


}