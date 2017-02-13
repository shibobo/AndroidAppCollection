package com.shibobo.btsmartcar.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shibobo.btsmartcar.bean.SendedMsg;

/**
 * Created by Administrator on 2017/1/28.
 */

public class SmsDbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="sms.db";
    private static final int DB_VERSION=1;
    private SmsDbOpenHelper(Context context) {
        super(context.getApplicationContext(), DB_NAME, null, DB_VERSION);
    }
    private static SmsDbOpenHelper mHelper;
    public static SmsDbOpenHelper getInstance(Context context){
        if (mHelper==null){
            synchronized (SmsDbOpenHelper.class){
                if (mHelper==null){
                    mHelper=new SmsDbOpenHelper(context);
                }
            }
        }
        return mHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table"+ SendedMsg.TABLE_NAME+"("+
                "_id integer primary key autoincrement,"
                +SendedMsg.COLUME_DATE+" integer,"
                +SendedMsg.COLUME_FES_NAME+" text,"
                +SendedMsg.COLUME_MSG+" text,"
                +SendedMsg.COLUME_NAMES+" text,"
                +SendedMsg.COLUME_NUBERS+" text,"
                +")";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
