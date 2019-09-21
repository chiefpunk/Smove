package gruv.game.smove;

//import org.robovm.apple.coregraphics.CGRect;
//import org.robovm.apple.coregraphics.CGSize;
import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.coregraphics.CGSize;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSBundle;
import org.robovm.apple.foundation.NSDictionary;
import org.robovm.apple.foundation.NSString;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIScreen;
import org.robovm.pods.google.mobileads.GADAdSize;
import org.robovm.pods.google.mobileads.GADBannerView;
import org.robovm.pods.google.mobileads.GADBannerViewDelegateAdapter;
import org.robovm.pods.google.mobileads.GADInterstitial;
import org.robovm.pods.google.mobileads.GADInterstitialDelegateAdapter;
import org.robovm.pods.google.mobileads.GADRequest;
import org.robovm.pods.google.mobileads.GADRequestError;





//import org.robovm.apple.uikit.UIScreen;
/*
import org.robovm.bindings.admob.GADAdSize;
import org.robovm.bindings.admob.GADBannerView;
import org.robovm.bindings.admob.GADBannerViewDelegateAdapter;
import org.robovm.bindings.admob.GADInterstitial;
import org.robovm.bindings.admob.GADInterstitialDelegateAdapter;
import org.robovm.bindings.admob.GADRequest;
import org.robovm.bindings.admob.GADRequestError;
*/
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication.Delegate;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;

import gruv.game.smove.Smove;
import gruv.game.smove.Smove.Listener;

public class IOSLauncher extends Delegate  implements Listener {		 	
	
	private GADBannerView adview;
    private GADInterstitial adviewInterstitial;
    private boolean adsInitialized = false;
    private IOSApplication iosApplication;
	
	private static int startCount = 0 ;
	private static final int INTERSTETIAL_PERIOD = 3 ;	

    @Override
    protected IOSApplication createApplication() {
        final IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        config.orientationLandscape = false;
        config.orientationPortrait = true;
      		
        iosApplication = new IOSApplication(new Smove(this), config);
        
        //initializeAds() ;
        //showAds(true) ;
        
        //org.robovm.bindings.admob.GADAdSize gas = GADAdSizeManager.smartBannerPortrait();
        //adview = new GADBannerView(GADAdSizeManager.smartBannerPortrait());
        
        return iosApplication;
    }

    public static void main(String[] argv) {
    	
    	NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();    
    }

    public void initializeAdsAndShow() {//only for iOS
    	initialize();
    	showAds(true);
    }

    private void initialize() {
    	
        if (!adsInitialized) {
            adsInitialized = true;
            
            adview = new GADBannerView(GADAdSize.SmartBannerPortrait());
            adview.setAdUnitID("ca-app-pub-0000000000000000/0000000000"); 
			
            adview.setRootViewController(iosApplication.getUIViewController());
            
            iosApplication.getUIViewController().getView().addSubview(adview);            
            
            
            final GADRequest request = new GADRequest();

            adview.setDelegate(new GADBannerViewDelegateAdapter() {
                @Override
                public void didReceiveAd(GADBannerView view) {
                    super.didReceiveAd(view);
                    //log.debug("didReceiveAd");
                }

                @Override
                public void didFailToReceiveAd(GADBannerView view, GADRequestError error) {
                    super.didFailToReceiveAd(view, error);
                    //log.debug("didFailToReceiveAd:" + error);
                }
            });

            adview.loadRequest(request);    
            
            intializeInterstitial(); 
        }
        
    }

    public void intializeInterstitial () {
		
        adviewInterstitial = new GADInterstitial("ca-app-pub-0000000000000000/0000000000");
       

        adviewInterstitial.setDelegate(new GADInterstitialDelegateAdapter() {
            @Override
            public void didReceiveAd (GADInterstitial ad) {
            }
            
            @Override
            public void didDismissScreen(GADInterstitial ad) {
                 intializeInterstitial ();
            }

            @Override
            public void didFailToReceiveAd (GADInterstitial ad, GADRequestError error) {
            }
        });

        adviewInterstitial.loadRequest(new GADRequest());
        
    }
    
    public void showAds(boolean show) {
    	
        initialize();

        final CGSize screenSize = UIScreen.getMainScreen().getBounds().getSize();
        double screenWidth = screenSize.getWidth();
        //double screenHeight = screenSize.getHeight();

        final CGSize adSize = adview.getBounds().getSize();
        double adWidth = adSize.getWidth();
        double adHeight = adSize.getHeight();

        float bannerWidth = (float) screenWidth;
        float bannerHeight = (float) (bannerWidth / adWidth * adHeight);

        if(show) {
            adview.setFrame(new CGRect((screenWidth / 2) - adWidth / 2, 0, bannerWidth, bannerHeight));//TOP
            //adview.setFrame(new CGRect((screenWidth / 2) - adWidth / 2, screenHeight  - bannerHeight, bannerWidth, bannerHeight));//BOTTOM
        } else {
            adview.setFrame(new CGRect(0, -bannerHeight, bannerWidth, bannerHeight));
        }
        
    }

	@Override
	public void showAdmobAd() {
		showAds(true);
	}

	@Override
	public void hideAdmobAd() {
		showAds(false);
	}

	@Override
	public String getVersion() {
		NSDictionary<?, ?> infoDictionary = NSBundle.getMainBundle().getInfoDictionary();
		String version = infoDictionary.get(new NSString("CFBundleShortVersionString")).toString();
		return version;		
		//return null;
	}
	
	@Override
	public void showInterstitialAdmobAd() {	
		
		initialize();
		if ((startCount % INTERSTETIAL_PERIOD) == 0 && adviewInterstitial != null) {     
	        if(adviewInterstitial.isReady()){
	            adviewInterstitial.present(iosApplication.getUIViewController());
	        }else{
	        	intializeInterstitial () ;
	        }
		}
		startCount++;
		
	}      
}

