package com.shibobo.notificationmanagertest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button send_notice;
    private Button cancel_notice;
    NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        send_notice=(Button) findViewById(R.id.send_notice);
        cancel_notice=(Button) findViewById(R.id.cancel_notice);
        send_notice.setOnClickListener(this);
        cancel_notice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_notice:
                manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                Notification notification=new Notification(R.mipmap.ic_launcher,"ticker here",System.currentTimeMillis());
//                notification.setLatestEventInfo();
                Intent intent=new Intent(this,SecActivity.class);
                PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationCompat.Builder builder=new NotificationCompat.Builder(MainActivity.this);
                builder.setTicker("this is a notification");
                builder.setContentTitle("notification");
                builder.setContentText("中国足球进世界杯了！");
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setWhen(System.currentTimeMillis());
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                long[] vib=new long[]{0,1000,1000,1000};
                builder.setVibrate(vib);
                builder.setLights(Color.GREEN,3000,3000);

                Notification notification=builder.build();
                notification.defaults=Notification.DEFAULT_ALL;
                manager.notify(11,notification);
                break;
            case R.id.cancel_notice:
                manager.cancel(11);
                break;
            default:
                break;
        }

    }
}
