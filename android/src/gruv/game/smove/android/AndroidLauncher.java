package gruv.game.smove.android;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import gruv.game.smove.Smove;
import gruv.game.smove.Smove.Listener;
import gruv.game.smove.android.R;

public class AndroidLauncher extends AndroidApplication {
	
	//see https://github.com/TheInvader360/tutorial-libgdx-google-ads/commit/0a5ea376d4eb92b8e87c13a03245adb40b53e811
	private static int startCount = 0 ;
	private static final int INTERSTETIAL_PERIOD = 3;
	
	//пїЅпїЅпїЅпїЅпїЅпїЅпїЅ:	
	public AdView adView = null;
	private AdRequest adRequest = null;
	private InterstitialAd interstitial = null;		
	private Listener listener = null;
		
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);	    

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = true;//false;
		config.useCompass = false;
		config.useWakelock = true;
		
		RelativeLayout layout = new RelativeLayout(this);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		getWindow().clearFlags( WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		listener = new Listener(){
			@Override
            public void showAdmobAd() {            
				showAd();
            }
			@Override
            public void hideAdmobAd() {            
				hideAd();
            }
			@Override
			public String getVersion(){
				return getVersionName();
				//return "";
			}
			@Override
			public void initializeAdsAndShow() {
				// TODO Auto-generated method stub				
			}
			@Override
            public void showInterstitialAdmobAd() {            
				showInterstitialAd();
            }
		};
		
		View gameView = initializeForView(new Smove(listener), config);

		setupAdmob();

		layout.addView(gameView);

		RelativeLayout.LayoutParams adParams = 
		new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT, 

		RelativeLayout.LayoutParams.WRAP_CONTENT);

		//adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM); 
		adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP); 

		adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT); 

		layout.addView(adView, adParams);  
		
		setContentView(layout); 
		
		initInterstitial();
		
		getApplicationContext();
	}
	
	
	private String getVersionName(){	
		PackageInfo pInfo;
		 try {			
			pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
			return pInfo.versionName;
		 } catch (NameNotFoundException e) {			
			e.printStackTrace();
			return "no info";
		 } 
	}	
	
	private void setupAdmob(){		
		
        String AdMobPublisherID = this.getContext().getString(R.string.admob_publisher_id);//"ca-app-pub-8769014029420702/1722138123";//getAdMobPublisherID();//getString(R.string.admob_publisher_id);
        adView = new AdView(this);
		adView.setAdUnitId(AdMobPublisherID);
		//adView.setAdSize(AdSize.BANNER);   
		adView.setAdSize(AdSize.SMART_BANNER);    
		//adView.setBackgroundColor(Color.BLACK);
		adView.setBackgroundColor(Color.argb(0, 0, 0, 0)); 
		if(adRequest == null){
        	adRequest = new AdRequest.Builder().build();
        }
        adView.loadAd(adRequest);     
    }	
	
	//-----------------------------------------------------------------
	
	 
	private void requestNewInterstitial() {
		if (interstitial != null) {			
			try { 
				 // создание запроса объявлений
			     AdRequest adRequest = new AdRequest.Builder().build();	
			     
			     // AdListener будет использовать обратные вызовы, указанные ниже.
			     if (!interstitial.isLoaded()) {
			    	 interstitial.loadAd(adRequest);
			     }
			} catch (Exception e) {  
			}
		}
	}
	 
	public void showInterstitialAd(){	
		try { 
			runOnUiThread(new Runnable() {
	 	        @Override
	 	        public void run() {
					 if ((startCount % INTERSTETIAL_PERIOD) == 0 && interstitial != null) {
						 if (interstitial.isLoaded()) {	
							 interstitial.show();
					     }else{
					    	 AdRequest adRequest = new AdRequest.Builder().build();	
					    	 interstitial.loadAd(adRequest);
					    	 //requestNewInterstitial();
					     }
				     }
					 startCount++;
	 	       }
	 	    }); 
		} catch (Exception e) { 
		}
	}
	
	private void initInterstitial(){
		interstitial = new InterstitialAd(this);		
	    interstitial.setAdUnitId(this.getContext().getString(R.string.admob_interstitial_publisher_id));
	    requestNewInterstitial();		    
	    interstitial.setAdListener(new AdListener() {
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });
	}
	//-----------------------------------------------------------------
	/*
    public void startInterstitialAndExit() {
    	runOnUiThread(new Runnable() {
 	        @Override
 	        public void run() {
 	        	if (interstitial != null &&
 	        		interstitial.isLoaded()) {
	 	   	        interstitial.show();	 	   	        
	 	   	    }
 	        	finish();
 	        }
 	    }); 
	}*/
    
    public void showAd(){
    	runOnUiThread(new Runnable() {
 	        @Override
 	        public void run() {
 	        	adView.setVisibility(View.VISIBLE);
 	        }
 	    });    	
    }
    
    public void hideAd(){
    	runOnUiThread(new Runnable() {
 	        @Override
 	        public void run() {
 	        	adView.setVisibility(View.GONE);
 	        }
 	    });    	
    }
    
    public void onResume() {    	
        super.onResume();
        if(adView != null){
        	adView.resume();
        }  
    }
    
    @Override 
    public void onPause() {       
        if(adView != null){
	   		 adView.pause();
	   	}
        super.onPause();
        /*if(MemoryTrainer2.startInterstitial){
        	startInterstitialAndExit();
        }*/
    }    
    
    @Override
    public void onDestroy() {
      if (adView != null) {
          adView.destroy();
      }     
      super.onDestroy();
    }    
}