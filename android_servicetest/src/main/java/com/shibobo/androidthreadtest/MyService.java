package com.shibobo.androidthreadtest;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/10/30 0030.
 */

public class MyService extends Service {

    private static final String Service_Tag="MyService";
    private DownLoadBinder downloadBinder=new DownLoadBinder();
    class DownLoadBinder extends Binder{
        public void startDownload(){
            Log.d(Service_Tag,"startDownload");
        }
        public int getProgress(){
            Log.d(Service_Tag,"getProgress");
            return 0;
        }
    }
    NotificationManager manager;
    @Override
    public IBinder onBind(Intent intent) {
        return downloadBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Service_Tag,"onCreate Service");
        Toast.makeText(this,"onCreate Service",Toast.LENGTH_SHORT).show();
        manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent=new Intent(this,DisplayActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,
                                                              0,
                                                              intent,
                                                              PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setTicker("通知");
        builder.setContentTitle("你有一个通知");
        builder.setContentText("中国足球出线啦!");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);

        Notification notification=builder.build();
        startForeground(1,notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(Service_Tag,"onStartCommand Service");
        Toast.makeText(this,"onStartCommand Service",Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(Service_Tag,"onDestroy Service");
        Toast.makeText(this,"onDestroy Service",Toast.LENGTH_SHORT).show();
    }
}
