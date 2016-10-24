package com.shibobo.smstest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private TextView from;
    private TextView content;
    private IntentFilter intentFilter;
    private MessageReceiver messageReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        from=(TextView) findViewById(R.id.from);
        content=(TextView) findViewById(R.id.content);
        intentFilter=new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.setPriority(100);
        messageReceiver=new MessageReceiver();
        registerReceiver(messageReceiver,intentFilter);
    }
    class MessageReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle=intent.getExtras();
            //提取短信消息
            Object[] pdus=(Object[]) bundle.get("pdus");
            SmsMessage[] messages=new SmsMessage[pdus.length];
            for (int i=0;i<messages.length;i++){
                messages[i]=SmsMessage.createFromPdu((byte[]) pdus[i]);
            }
            //获取发送方号码
            String address=messages[0].getOriginatingAddress();
            String fullMessage="";
            for(SmsMessage message:messages){
                //获取短信内容
                fullMessage+=message.getMessageBody();
            }
            from.setText(address);
            content.setText(fullMessage);
            abortBroadcast();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messageReceiver);
    }
}
