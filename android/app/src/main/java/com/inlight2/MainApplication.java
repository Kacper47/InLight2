package com.inlight2;

import android.app.Application;
import android.content.Context;
import com.facebook.react.PackageList;
import com.facebook.react.ReactApplication;
import com.toyberman.drawOverlay.RNDrawOverlayPackage;
import org.devio.rn.splashscreen.SplashScreenReactPackage;
import com.github.douglasjunior.reactNativeGetLocation.ReactNativeGetLocationPackage;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.soloader.SoLoader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
// import com.reactlibrary.RNDrawOverlayPackage;

public class MainApplication extends Application implements ReactApplication {

  private final ReactNativeHost mReactNativeHost =
      new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
          return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
          @SuppressWarnings("UnnecessaryLocalVariable")
          List<ReactPackage> packages = new PackageList(this).getPackages();
          packages.add(new FilterPackage());
          // packages.add(new RNDrawOverlayPackage());
          // packages.add(new SplashScreenReactPackage());
          return packages;
        }

        @Override
        protected String getJSMainModuleName() {
          return "index";
        }
      };

  @Override
  public ReactNativeHost getReactNativeHost() {
    return mReactNativeHost;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    SoLoader.init(this, /* native exopackage */ false);
    initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
  }

  /**
   * Loads Flipper in React Native templates. Call this in the onCreate method with something like
   * initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
   *
   * @param context
   * @param reactInstanceManager
   */
  private static void initializeFlipper(
      Context context, ReactInstanceManager reactInstanceManager) {
    if (BuildConfig.DEBUG) {
      try {
        /*
         We use reflection here to pick up the class that initializes Flipper,
        since Flipper library is not available in release mode
        */
        Class<?> aClass = Class.forName("com.inlight2.ReactNativeFlipper");
        aClass
            .getMethod("initializeFlipper", Context.class, ReactInstanceManager.class)
            .invoke(null, context, reactInstanceManager);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }
}


// package com.inlight2;

// import android.app.Application;

// import com.facebook.react.ReactApplication;
// import com.reactnativecommunity.slider.ReactSliderPackage;
// import com.toyberman.drawOverlay.RNDrawOverlayPackage;
// import com.toyberman.drawOverlay.RNDrawOverlayPackage;
// import com.facebook.react.ReactNativeHost;
// import com.facebook.react.ReactPackage;
// import com.facebook.react.shell.MainReactPackage;
// import com.facebook.soloader.SoLoader;

// // react-native-splash-screen >= 0.3.1
// import org.devio.rn.splashscreen.SplashScreenReactPackage;
// import java.util.Arrays;
// import java.util.List;

// public class MainApplication extends Application implements ReactApplication {

//     private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
//         @Override
//         public boolean getUseDeveloperSupport() {
//             return BuildConfig.DEBUG;
//         }

//         @Override
//         protected List<ReactPackage> getPackages() {
//             return Arrays.<ReactPackage>asList(
//                     new FilterPackage(),
//                     new MainReactPackage(),
                      // new VectorIconsPackage(),
//                     new SplashScreenReactPackage(),
//                     new ReactSliderPackage(),
//                     // new RNDrawOverlayPackage(),
//                     // new SplashScreenReactPackage() 
//             );
//         }

//         @Override
//         protected String getJSMainModuleName() {
//             return "index";
//         }
//     };

//     @Override
//     public ReactNativeHost getReactNativeHost() {
//         return mReactNativeHost;
//     }

//     @Override
//     public void onCreate() {
//         super.onCreate();
//         SoLoader.init(this, /* native exopackage */ false);
    
//     }
// }

