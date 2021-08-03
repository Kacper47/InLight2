 package com.inlight2;
 import android.annotation.TargetApi;
 import android.app.Notification;
 import android.app.PendingIntent;
 import android.app.Service;
 import android.content.Context;
 import android.content.Intent;
 import android.content.res.Resources;
 import android.graphics.Bitmap;
 import android.graphics.BitmapFactory;
 import android.graphics.Color;
 import android.graphics.PixelFormat;
 import android.os.Bundle;
 import android.os.Handler;
 import android.os.IBinder;
 import androidx.core.app.NotificationCompat;
 import android.app.NotificationManager;
 import android.app.NotificationChannel;
 import android.os.Build;
 import android.os.Looper;
 import android.os.SharedMemory;
 import android.util.DisplayMetrics;
 import android.util.Log;
 import android.view.Gravity;
 import android.view.KeyEvent;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.Window;
 import android.view.WindowManager;
 import android.widget.FrameLayout;
 import android.widget.LinearLayout;
 import android.widget.Toast;
 import com.facebook.react.HeadlessJsTaskService;

 import java.util.Timer;
 import java.util.TimerTask;

 import static android.content.ContentValues.TAG;


 public class FilterService extends Service {

     private static final int SERVICE_NOTIFICATION_ID = 12345;
     private static final String CHANNEL_ID = "FILTER";

     private Handler handler = new Handler();
     private Runnable runnableCode = new Runnable() {
         @Override
         public void run() {
             Context context = getApplicationContext();
             Intent myIntent = new Intent(context, FilterEventService.class);
             context.startService(myIntent);
             HeadlessJsTaskService.acquireWakeLockNow(context);
             handler.postDelayed(this, 2000);
         }
     };
     private void createNotificationChannel() {
         // Create the NotificationChannel, but only on API 26+ because
         // the NotificationChannel class is new and not in the support library
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             int importance = NotificationManager.IMPORTANCE_DEFAULT;
             NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Filter in background", importance);
             channel.setDescription("Channel description");
             NotificationManager notificationManager = getSystemService(NotificationManager.class);
             notificationManager.createNotificationChannel(channel);
         }
     }

     @Override
     public IBinder onBind(Intent intent) {
         return null;
     }


    private View fView;
    private View dView;
    boolean isServiceOpen = false;


     @Override
     public void onCreate() {
        super.onCreate();
     }


     @Override
     public void onDestroy() {
         super.onDestroy();
         this.handler.removeCallbacks(this.runnableCode);
         WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

         if (fView != null && isServiceOpen == true) {
          isServiceOpen = false;
          windowManager.removeView(fView);
    
          fView = null;
        }
     }

     @Override
     public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        boolean isAuto = (boolean) extras.get("isAuto");
        String contentTitle = (String) extras.get("contentTitle");
        String contentText = (String) extras.get("contentText");

        int color = Color.argb(0, 0, 0, 0);
        int dim = Color.argb(200, 0, 0, 0);

        
     WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
     DisplayMetrics displayMetrics = new DisplayMetrics();
     windowManager.getDefaultDisplay().getMetrics(displayMetrics);
     int height = displayMetrics.heightPixels;
     int width = displayMetrics.widthPixels;

       WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
               width + 1700,
               height + 700,
               WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
               WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
               PixelFormat.TRANSLUCENT
       );
       layoutParams.gravity = Gravity.CENTER | Gravity.START;
       layoutParams.x = -1;
       layoutParams.y = -1;


        FrameLayout interceptorLayout = new FrameLayout(this) {
            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                        Log.v(TAG, "BACK Button Pressed");
                        return true;
                    }
                }

                return super.dispatchKeyEvent(event);
            }
        };


        final Handler handler = new Handler(Looper.getMainLooper());
        if(!isAuto){
            int alpha = (int) extras.get("alpha");
            int red = (int) extras.get("red");
            int green = (int) extras.get("green");
            int blue = (int) extras.get("blue");
            color = Color.argb(alpha, red, green, blue);
        }
        else {
            int time = (int) extras.get("time");
            int sunrise = (int) extras.get("sunrise");
            int sunset = (int) extras.get("sunset");

            //Sunrise
            if(sunrise-10 < time || time <= sunrise+30){
                color = Color.argb(75, 255, 178, 37);
                handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Day
                    fView.setBackgroundColor(Color.argb(50, 50, 50, 50));
                }
                }, ((sunrise+30) - time) * 60 * 1000);

                handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Sunset
                    fView.setBackgroundColor(Color.argb(50, 50, 50, 50));
                }
                }, ((sunset-10) - time) * 60 * 1000);
                
                handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Night
                    fView.setBackgroundColor(Color.argb(50, 50, 50, 50));
                }
                },  (((24*60 + sunrise-10) - time) * 60 * 1000));
            }
            //Day
            else if(sunrise+30 < time || time <= sunset-10){
                color = Color.argb(75, 255, 178, 37);
                handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Sunset
                    fView.setBackgroundColor(Color.argb(50, 50, 50, 50));
                }
                }, ((sunrise+30) - time) * 60 * 1000);
                handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Night
                    fView.setBackgroundColor(Color.argb(50, 50, 50, 50));
                }
                }, ((sunset-10) - time) * 60 * 1000);
                handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Sunrise
                    fView.setBackgroundColor(Color.argb(50, 50, 50, 50));
                }
                },  ((sunset-10) - time) * 60 * 1020);
            }
            //Sunset
            else if(sunset-10 < time || time <= sunset+30){
                color = Color.argb(75, 255, 178, 37);
                handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Night
                    fView.setBackgroundColor(Color.argb(50, 50, 50, 50));
                }
                }, ((sunrise+30) - time) * 60 * 1000);
                handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Sunrise
                    fView.setBackgroundColor(Color.argb(50, 50, 50, 50));
                }
                }, ((sunset-10) - time) * 60 * 1000);
                handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Day
                    fView.setBackgroundColor(Color.argb(50, 50, 50, 50));
                }
                },  ((sunset-10) - time) * 60 * 1020);            
            }
            //Night
            else if(sunset+30 < time || time <= sunrise-10){
                color = Color.argb(75, 255, 178, 37);
                handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Sunrise
                    fView.setBackgroundColor(Color.argb(50, 50, 50, 50));
                }
                }, ((sunrise+30) - time) * 60 * 1000);
                handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Day
                    fView.setBackgroundColor(Color.argb(50, 50, 50, 50));
                }
                }, ((sunset-10) - time) * 60 * 1000);
                handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Sunset
                    fView.setBackgroundColor(Color.argb(50, 50, 50, 50));
                }
                },  ((sunset-10) - time) * 60 * 1020);
            }   
        }



        

        LayoutInflater inflater = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE));  
        if(inflater != null) {
             if(isServiceOpen == false){
                  fView = inflater.inflate(R.layout.filter_view, interceptorLayout);
                  isServiceOpen = true;
                  windowManager.addView(fView, layoutParams);
             }

              fView.setBackgroundColor(color);
         }



        this.handler.post(this.runnableCode);
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent broadcastIntent = new Intent(this,  NotificationReceiver.class);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setColor(Color.argb(255, 252, 186, 3))
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setOngoing(true)
                .build();
        startForeground(SERVICE_NOTIFICATION_ID, notification);
        return START_STICKY;
     }

 }
