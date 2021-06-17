package com.inlight2;

import android.content.Intent;
import android.app.Service;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.WindowManager;
import android.view.Window;
import android.view.View;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.IllegalViewOperationException;


public class FilterModule extends ReactContextBaseJavaModule {
    private static ReactApplicationContext reactContext;

    public FilterModule(ReactApplicationContext reactContext) {
        super(reactContext); //required by React Native
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "Filter";
    }

    @ReactMethod
    public void startAutoService(String contentTitle, String contentText, boolean isAuto, int time, int sunrise, int sunset){
      Intent filterIntent = new Intent(this.reactContext,  FilterService.class);
      
      filterIntent.putExtra("contentTitle", contentTitle);
      filterIntent.putExtra("contentText", contentText);
      filterIntent.putExtra("isAuto", isAuto);
      filterIntent.putExtra("time", time);
      filterIntent.putExtra("sunrise", sunrise);
      filterIntent.putExtra("sunset", sunset);
      
      this.reactContext.startService(filterIntent);
    }


    @ReactMethod
    public void startFilterService(String contentTitle, 
                                  String contentText, boolean isAuto, 
                                  int alpha, int red, int green, int blue){
      Intent filterIntent = new Intent(this.reactContext,  FilterService.class);
      
      filterIntent.putExtra("contentTitle", contentTitle);
      filterIntent.putExtra("contentText", contentText);
      filterIntent.putExtra("isAuto", isAuto);
      filterIntent.putExtra("alpha", alpha);
      filterIntent.putExtra("red", red);
      filterIntent.putExtra("green", green);
      filterIntent.putExtra("blue", blue);
      
      this.reactContext.startService(filterIntent);
    }

    @ReactMethod
    public void stopService() {
        this.reactContext.stopService(new Intent(this.reactContext, FilterService.class));
    }
}