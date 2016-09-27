package com.shibobo.broadcastbestpractice;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

/**
 * 这是一个强制下线的项目。类似于qq多地登录之后强制下线
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button force_offline=(Button) findViewById(R.id.force_offline);
        force_offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"you",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent("com.shibobo.broadcastbestpractice.FORCE_OFFLINE");
                sendBroadcast(intent);
            }
        });
        Button test=(Button) findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogbuilder=new AlertDialog.Builder(MainActivity.this);
                dialogbuilder.setTitle("Warning");
                dialogbuilder.setMessage("you are forced to offline");
                dialogbuilder.setCancelable(true);
//                dialogbuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(context,"me",Toast.LENGTH_SHORT).show();
//                ActivityCollector.finishAll();
//                Intent intent=new Intent(context,LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//                    }
//                });
                AlertDialog alertdialog=dialogbuilder.create();
                alertdialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                alertdialog.show();
            }
        });
    }
}
