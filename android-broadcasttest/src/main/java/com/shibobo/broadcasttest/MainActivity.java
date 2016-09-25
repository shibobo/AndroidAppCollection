package com.shibobo.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    private TextView msg;
    private Button send;
    private Button send1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        msg=(TextView) findViewById(R.id.internetmsg);
        send=(Button) findViewById(R.id.sendstdbroadcast);
        send1=(Button) findViewById(R.id.sendordbroadcast);
        intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver=new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent("com.shibobo.broadcasttest.MY_BROADCAST");
                Intent intent=new Intent("com.shibobo.broadcasttest.MY_BROADCAST");
                sendBroadcast(intent);
            }
        });
        send1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent("com.shibobo.broadcasttest.MY_BROADCAST");
                sendOrderedBroadcast(intent1,null);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    class NetworkChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            //msg.setText("网络状态发生了变化");
            //Toast.makeText(context,"network changed",Toast.LENGTH_SHORT).show();
            ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if (networkInfo!=null && networkInfo.isAvailable()){
                msg.setText("已经连上网了:"+networkInfo.getTypeName().toString());
            }else{
                msg.setText("不好意思，没网了");
            }
        }
    }
}
