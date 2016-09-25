package com.shibobo.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LocalBroadcast extends AppCompatActivity {
    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_broadcast);
        //获取实例
        localBroadcastManager=LocalBroadcastManager.getInstance(this);
        Button button=(Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("com.shibobo.broadcasttest.BEST_BROADCAST");
                localBroadcastManager.sendBroadcast(intent);
            }
        });
        intentFilter=new IntentFilter();
        intentFilter.addAction("com.shibobo.broadcasttest.BEST_BROADCAST");
        localReceiver=new LocalReceiver();
        //注册本地广播
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    //define an inner class
    class LocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"i received the broadcast",Toast.LENGTH_SHORT).show();
        }
    }
}
