package gruv.game.smove.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import gruv.game.smove.Smove;
import gruv.game.smove.Smove.Listener;

public class DesktopLauncher {
	
	private static Listener listener = null;
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Title";	
		float scale = 0.7f;
		/*
		config.height = (int)(FireBall2.GAME_HEIGHT * scale);
		//config.width = (int)(Labyrinth.GAME_WIDTH * scale);
		config.width = (int)(900 * scale);
		*/
		config.height = (int)(1024 * 1 * scale);//ipad
		config.width = (int)(768 * scale);//ipad
		
		//config.height = (int)(TicTacToe2.GAME_HEIGHT * scale);
		//config.width = (int)(TicTacToe2.GAME_WIDTH * scale);
		
		listener = new Listener(){
			@Override
            public void showAdmobAd() {            
				//TODO: showAd();
            }
			@Override
            public void hideAdmobAd() {            
				//TODO: hideAd();
            }
			@Override
			public String getVersion(){
				//TODO
				return "";
			}
			@Override
			public void initializeAdsAndShow() {
				// TODO Auto-generated method stub				
			}
			@Override
            public void showInterstitialAdmobAd() {            
				//TODO: showInterstitialAdmobAd();
            }
		};
		
		System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
		new LwjglApplication(new Smove(listener), config);
	}
}
