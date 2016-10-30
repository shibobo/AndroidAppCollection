package com.shibobo.servicebestpractice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button startAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toast.makeText(MainActivity.this,"hello",Toast.LENGTH_SHORT).show();
//        Intent intent=new Intent(MainActivity.this,LongRunningService.class);
//        startService(intent);
        startAlarm=(Button) findViewById(R.id.startAlarm);
        startAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"hello",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,LongRunningService.class);
                startService(intent);
            }
        });

    }
}
