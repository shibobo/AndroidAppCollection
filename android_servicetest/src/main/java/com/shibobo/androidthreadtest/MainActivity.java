package com.shibobo.androidthreadtest;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView show;
    private Button change_text;
    private static final int UPDATE_TEXT=1;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_TEXT:
                    show.setText("nice to meet you");
                    break;
                default:
                    break;
            }
        }
    };
    private MyService.DownLoadBinder downloadBinder;
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder=(MyService.DownLoadBinder) service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Button start_service;
    private Button stop_service;

    private Button bind_service;
    private Button unbind_service;

    private Button start_intentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show=(TextView) findViewById(R.id.show);
        change_text=(Button) findViewById(R.id.change_text);
        start_service=(Button) findViewById(R.id.start_service);
        stop_service=(Button) findViewById(R.id.stop_service);
        bind_service=(Button) findViewById(R.id.bind_service);
        unbind_service=(Button) findViewById(R.id.unbind_service);
        start_intentService=(Button) findViewById(R.id.start_intentService);

        change_text.setOnClickListener(this);
        start_service.setOnClickListener(this);
        stop_service.setOnClickListener(this);
        bind_service.setOnClickListener(this);
        unbind_service.setOnClickListener(this);
        start_intentService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_text:
                //start a new thread
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //the following way can not work
                        // show.setText("nice to meet you");
                        Message message=new Message();
                        message.what=UPDATE_TEXT;
                        handler.sendMessage(message);//send the message object
                    }
                }).start();
                break;
            case R.id.start_service:
                Intent startIntent=new Intent(this,MyService.class);
                startService(startIntent);
                break;
            case R.id.stop_service:
                Intent stopIntent=new Intent(this,MyService.class);
                stopService(stopIntent);
                break;
            case R.id.bind_service:
                Intent bindIntent=new Intent(this,MyService.class);
                bindService(bindIntent,connection,BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                Intent unbindIntent=new Intent(this,MyService.class);
                unbindService(connection);
                break;
            case R.id.start_intentService:
                // print id of main thread
                Log.d("MainActivity","Thread id is "+Thread.currentThread().getId());
                Intent intentService=new Intent(this,MyIntentService.class);
                startService(intentService);
                break;
            default:
                break;
        }
    }
}
