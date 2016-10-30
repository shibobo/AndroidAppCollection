package com.shibobo.servicebestpractice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Date;

/**
 * Created by Administrator on 2016/10/30 0030.
 */

public class LongRunningService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("LongRunningService ","executed in "+new Date().toString());
            }
        }).start();
        AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour=1000;
        long triggleAtTime= SystemClock.elapsedRealtime()+anHour;
        Intent i=new Intent(this,ALarmReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,i,0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggleAtTime,pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
