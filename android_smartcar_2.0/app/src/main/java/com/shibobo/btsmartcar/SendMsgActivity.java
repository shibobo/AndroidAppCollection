package com.shibobo.btsmartcar;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shibobo.btsmartcar.bean.Festival;
import com.shibobo.btsmartcar.bean.FestivalLab;
import com.shibobo.btsmartcar.bean.Msg;
import com.shibobo.btsmartcar.bean.SendedMsg;
import com.shibobo.btsmartcar.biz.SmsBiz;

import java.util.HashSet;

public class SendMsgActivity extends AppCompatActivity {
    public static final String FES_ID="FES_ID";
    public static final String MSG_ID="MSG_ID";
    public static final int CODE_REQUEST=1;
    private int mFestivalId;
    private int msgId;
    private Festival mFestival;
    private Msg mMsg;
    private EditText mEditText;
    private Button mButtonAdd;
    private FloatingActionButton mFab;
    private View mLayoutLoading;
    private LinearLayout contactLayout;

    private HashSet<String> mContactNames=new HashSet<>();
    private HashSet<String> mContactNums=new HashSet<>();

    private LayoutInflater mInflater;

    public static final String ACTION_SEND_MSG="ACTION_SEND_MSG";
    public static final String ACTION_DElIVER_MSG="ACTION_DELIVER_MSG";
    private PendingIntent mSendPi;
    private PendingIntent mDeliverPi;
    private BroadcastReceiver mSendBroadcastReceiver;
    private BroadcastReceiver mDeliverBroadcastReceiver;

    private SmsBiz mSmsBiz;
    private int mSendCount;
    private int mTotalCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);
        initViews();
        initDatas();
        initEvents();
        initReceiver();
    }

    private void initReceiver() {
        Intent sendIntent=new Intent(ACTION_SEND_MSG);
        mSendPi=PendingIntent.getBroadcast(this,0,sendIntent,0);
        Intent deliverIntent=new Intent(ACTION_DElIVER_MSG);
        mDeliverPi=PendingIntent.getBroadcast(this,0,deliverIntent,0);

        registerReceiver(mSendBroadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mSendCount++;
                if (getResultCode()==RESULT_OK){
                    Log.d("TAG",("短信发送成功"+mSendCount+"/"+mTotalCount));
                }else {
                    Log.d("TAG","短信发送失败");
                }
                Toast.makeText(SendMsgActivity.this,(mSendCount+"/"+mTotalCount+"短信发送成功"), Toast.LENGTH_SHORT).show();
                if (mSendCount==mTotalCount){
                    finish();
                }
            }
        },new IntentFilter(ACTION_SEND_MSG));

        registerReceiver(mDeliverBroadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                    Log.d("TAG","联系人已经收到短信");
            }
        },new IntentFilter(ACTION_DElIVER_MSG));
    }

    private void initEvents() {
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent,CODE_REQUEST);
            }
        });
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mContactNums.size()==0){
                    Toast.makeText(SendMsgActivity.this, "请先选择联系人", Toast.LENGTH_SHORT).show();
                    return;
                }
                String ms= mEditText.getText().toString();
                if (TextUtils.isEmpty(ms)){
                    Toast.makeText(SendMsgActivity.this, "短信不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                mLayoutLoading.setVisibility(View.VISIBLE);
                mFab.setEnabled(false);
                mTotalCount=mSmsBiz.sendMsg(mContactNums,buildSendedMsg(ms),mSendPi,mDeliverPi);
                mSendCount=0;

            }
        });
    }

    private SendedMsg buildSendedMsg(String ms) {
        SendedMsg sendedMsg=new SendedMsg();
        sendedMsg.setMsg(ms);
        sendedMsg.setFestivalName(mFestival.getName());
        String names="";
        for (String name:mContactNames){
            names+=name+":";
        }
        sendedMsg.setNames(names.substring(0,names.length()-1));
        String numbers="";
        for (String number:mContactNums){
            numbers+=number+":";
        }
        sendedMsg.setNumbers(numbers.substring(0,numbers.length()-1));
        return sendedMsg;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CODE_REQUEST){
            if (resultCode==RESULT_OK){
                Uri contactUri=data.getData();
                Cursor cursor=managedQuery(contactUri,null,null,null,null);
                cursor.moveToFirst();
                String contactName=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String number=getContactNumber(cursor);
                if (!TextUtils.isEmpty(number)){
                    mContactNums.add(number);
                    mContactNames.add(contactName);
                    addTag(contactName);
                }
            }
        }
    }

    private void addTag(String contactName) {
        TextView view=(TextView) mInflater.inflate(R.layout.tag,contactLayout,false);
        view.setText(contactName);
        contactLayout.addView(view);
    }

    private String getContactNumber(Cursor cursor) {
        int numberCount=cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
        String number=null;
        if (numberCount>0){
            int contactId=cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phoneCursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+contactId,null,null);
            phoneCursor.moveToFirst();
            number=phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneCursor.close();
            return number;
        }
        cursor.close();
        return number;
    }

    private void initViews() {
        mEditText= (EditText) findViewById(R.id.id_et_content);
        mButtonAdd= (Button) findViewById(R.id.id_btn_add);
        mFab= (FloatingActionButton) findViewById(R.id.id_fab_send);
        mLayoutLoading= findViewById(R.id.id_layout_loading);
        mLayoutLoading.setVisibility(View.GONE);
        mInflater=LayoutInflater.from(this);
        contactLayout= (LinearLayout) findViewById(R.id.contactLayout);
        mSmsBiz=new SmsBiz(this);
    }
    private void initDatas() {
        mFestivalId=getIntent().getIntExtra(FES_ID,-1);
        msgId=getIntent().getIntExtra(MSG_ID,-1);
        mFestival=FestivalLab.getInstance().getFestivalById(mFestivalId);
        setTitle(mFestival.getName());
        if (msgId==-1){
            mEditText.setHint("请输入发送信息");
        }else{
            mMsg=FestivalLab.getInstance().getMsgByMsgId(msgId);
            mEditText.setText(mMsg.getContent().toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mSendBroadcastReceiver);
        unregisterReceiver(mDeliverBroadcastReceiver);
    }
}
