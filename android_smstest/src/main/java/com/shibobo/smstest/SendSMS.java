package com.shibobo.smstest;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendSMS extends AppCompatActivity implements View.OnClickListener,TextWatcher{
    private EditText to;
    private EditText content;
    private Button sendsms;
    private IntentFilter sendFilter;
    private SendStatusReceiver sendStatusReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        init();
        to.addTextChangedListener(this);
        content.addTextChangedListener(this);
        sendsms.setOnClickListener(this);

    }
    public void init(){
        to=(EditText) findViewById(R.id.to);
        content=(EditText) findViewById(R.id.content);
        sendsms=(Button) findViewById(R.id.sendsms);
        sendsms.setEnabled(false);
        sendFilter=new IntentFilter();
        sendFilter.addAction("SENT_SMS_ACTION");
        sendStatusReceiver=new SendStatusReceiver();
        registerReceiver(sendStatusReceiver,sendFilter);
    }
    public void cleanInput(){
        to.setText("");
        content.setText("");
        sendsms.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        //Log.d("tips","the button is clicked, can not use");
        SmsManager smsManager=SmsManager.getDefault();
        Intent sentIntent=new Intent("SENT_SMS_ACTION");
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,sentIntent,0);
        smsManager.sendTextMessage(to.getText().toString(),null,content.getText().toString(),pendingIntent,null);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //判断输入是否为空
        if ((to.length()==0)||(content.length()==0)){
            sendsms.setEnabled(false);
        }else{
            sendsms.setEnabled(true);
        }

    }

    //inner class
    class SendStatusReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(getResultCode()==RESULT_OK){
                //
                //Log.d("tips","send ok");
                Toast.makeText(SendSMS.this,"短信发送成功",Toast.LENGTH_SHORT).show();
                cleanInput();
            }else{
                //Log.d("tips","send failed");
                Toast.makeText(SendSMS.this,"短信发送失败，请重试",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(sendStatusReceiver);
    }
}
