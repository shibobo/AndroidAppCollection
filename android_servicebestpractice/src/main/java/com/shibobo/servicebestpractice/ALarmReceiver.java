package com.shibobo.servicebestpractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/10/30 0030.
 */

public class ALarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"计时一次",Toast.LENGTH_SHORT).show();
        Intent i=new Intent(context,LongRunningService.class);
        context.startService(i);
    }
}
