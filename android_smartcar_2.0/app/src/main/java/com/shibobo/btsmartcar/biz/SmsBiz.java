package com.shibobo.btsmartcar.biz;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.telephony.SmsManager;

import com.shibobo.btsmartcar.bean.SendedMsg;
import com.shibobo.btsmartcar.db.SmsProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/1/26.
 */

public class SmsBiz {
    private Context context;
    public SmsBiz(Context context){
        this.context=context;
    }
    public int sendMsg(String number, String msg, PendingIntent sendPi,PendingIntent deliverPi){
        SmsManager smsManager=SmsManager.getDefault();
        ArrayList<String> contents = smsManager.divideMessage(msg);
        for (String content:contents){
            smsManager.sendTextMessage(number,null,content,sendPi,deliverPi);
        }
        return contents.size();
    }
    public int sendMsg(Set<String> numbers, SendedMsg sendedMsg, PendingIntent sendPi, PendingIntent deliverPi){
        save(sendedMsg);
        int result=0;
        for (String number:numbers){
            int count=sendMsg(number,sendedMsg.getMsg(),sendPi,deliverPi);
            result+=count;
        }
        return result;
    }
    private void save(SendedMsg sendedMsg){
        sendedMsg.setDate(new Date());
        ContentValues contentValues=new ContentValues();
        contentValues.put(SendedMsg.COLUME_DATE,sendedMsg.getDate().getTime());
        contentValues.put(SendedMsg.COLUME_FES_NAME,sendedMsg.getFestivalName());
        contentValues.put(SendedMsg.COLUME_MSG,sendedMsg.getMsg());
        contentValues.put(SendedMsg.COLUME_NAMES,sendedMsg.getNames());
        contentValues.put(SendedMsg.COLUME_NUBERS,sendedMsg.getNumbers());
        context.getContentResolver().insert(SmsProvider.URI_SMS_ALL,contentValues);

    }
}
