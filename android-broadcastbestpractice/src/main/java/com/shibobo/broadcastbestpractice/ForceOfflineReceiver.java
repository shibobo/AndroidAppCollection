package com.shibobo.broadcastbestpractice;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/9/25 0025.
 */
public class ForceOfflineReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        //Log.d("msg","程序执行到这里");
        AlertDialog.Builder dialogbuilder=new AlertDialog.Builder(context);
        dialogbuilder.setTitle("请下线");
        dialogbuilder.setMessage("你被迫下线！");
        dialogbuilder.setCancelable(false);
        dialogbuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(context,"me",Toast.LENGTH_SHORT).show();
                ActivityCollector.finishAll();
                Intent intent=new Intent(context,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        AlertDialog alertdialog=dialogbuilder.create();
        alertdialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertdialog.show();
    }
}
