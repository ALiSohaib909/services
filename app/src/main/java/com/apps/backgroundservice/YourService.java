package com.apps.backgroundservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

public class YourService extends Service {

    private static final String NOTIF_ID = "1";
    private static final int NOTIF_CHANNEL_ID = 1;
    private final int interval = 5000; // 5 Second

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

         intent = getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
        if (intent != null) {
            // We found the activity now start the activity
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            // Bring user to the market or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + "com.google.android.youtube"));
            startActivity(intent);
        }
        startForegrounds();

        return super.onStartCommand(intent, flags, startId);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startForegrounds() {
       // Handler handler = new Handler();
/*
        Runnable runnable = new Runnable() {
            public void run() {
                //    isForeground();
                Toast.makeText(getApplicationContext(), "C'Mom no hands!", Toast.LENGTH_SHORT).show();
            }
        };
*/
        Timer timerObj = new Timer();
        TimerTask timerTaskObj = new TimerTask() {
            public void run() {
               startForegrounds();
            }
        };
    //    timerObj.schedule(timerTaskObj, 0, 5000);
       new Timer().scheduleAtFixedRate(timerTaskObj, interval, interval);
  //      handler.postAtTime(runnable, System.currentTimeMillis() + interval);
 //       handler.postDelayed(runnable, interval);
       /* Intent notificationIntent = new Intent(this, MainActivity.class);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        startForeground(NOTIF_ID, new NotificationCompat.Builder(this,
                NOTIF_CHANNEL_ID) // don't forget create a notification channel first
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Service is running background")
                .setContentIntent(pendingIntent)
                .build());*/

        NotificationChannel chan = new NotificationChannel(
                NOTIF_ID,
                "My Foreground Service",
                NotificationManager.IMPORTANCE_LOW);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_SECRET);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                this, NOTIF_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("App is running on foreground")
                .setPriority(NotificationManager.IMPORTANCE_LOW)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setChannelId("MyChannelId")
                .build();

        startForeground(1, notification);
        isForeground();

    }
        public void isForeground() {
      /*  ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfo = manager.getRunningTasks(1);
        ComponentName componentInfo = runningTaskInfo.get(0).topActivity;
        return componentInfo.getPackageName().equals(myPackage);*/

           /* Intent i = new Intent(this, MainActivity.class);
            i.setAction(Intent.ACTION_MAIN);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            startActivity(i);*/
         /*   String manufacturer = "samsung";
            if(manufacturer.equalsIgnoreCase(android.os.Build.MANUFACTURER)) {
                //this will open auto start screen where user can enable permission for your app
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.samsung.securitycenter", "com.samsung.permcenter.autostart.AutoStartManagementActivity"));
                startActivity(intent);
            }*/
            Log.d("qwe", "called");
          //  Toast.makeText(getApplicationContext(), "C'Mom no hands!", Toast.LENGTH_SHORT).show();
        }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        startForegrounds();
        //    startForeground(NOTIF_CHANNEL_ID, new Notification());
    }

    class TestAsync extends AsyncTask<Void, Integer, String> {
        String TAG = getClass().getSimpleName();

        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG + " PreExceute","On pre Exceute......");
        }

        protected String doInBackground(Void...arg0) {
            Log.d(TAG + " DoINBackGround", "On doInBackground...");

            for (int i=0; i<10; i++){
                Integer in = new Integer(i);
                publishProgress(i);
            }
            return "You are at PostExecute";
        }

        protected void onProgressUpdate(Integer...a) {
            super.onProgressUpdate(a);
            Log.d(TAG + " onProgressUpdate", "You are in progress update ... " + a[0]);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG + " onPostExecute", "" + result);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
      //  startForegrounds();
      //  startForeground(NOTIF_CHANNEL_ID,new Notification());
    }
}