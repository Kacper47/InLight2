package com.inlight2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle; // here
import android.provider.Settings;
import android.util.Log;
// import com.reactlibrary.RNDrawOverlayPackage;
import com.facebook.react.ReactActivity;
import org.devio.rn.splashscreen.SplashScreen; // here


public class MainActivity extends ReactActivity {

  @Override
  protected String getMainComponentName() {
    return "InLight2";
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
      SplashScreen.show(this);
      super.onCreate(savedInstanceState);
  }

}
