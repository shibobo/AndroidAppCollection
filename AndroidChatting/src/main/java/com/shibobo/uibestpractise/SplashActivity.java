package com.shibobo.uibestpractise;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGHT=3000;
    //private ImageView splashimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //设置背景图片
        //splashimg=(ImageView) findViewById(R.id.splashimg);
        //splashimg.setImageResource(R.drawable.splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent newintent=new Intent(SplashActivity.this,MainActivity.class);
                SplashActivity.this.startActivity(newintent);
                SplashActivity.this.finish();
            }
        },SPLASH_DISPLAY_LENGHT);
    }
}
