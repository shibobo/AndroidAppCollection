package com.shibobo.coolweather.activity;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.shibobo.coolweather.R;
import com.shibobo.coolweather.util.HttpCallBackListener;
import com.shibobo.coolweather.util.HttpUtil;

import junit.framework.Test;

/**
 * Created by Administrator on 2016/11/11 0011.
 */

public class TestActivity extends AppCompatActivity {
    private Button test_site;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        test_site=(Button) findViewById(R.id.test_site);
        test_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final String address="http://m.blog.csdn.net/article/details?id=53083013";

                //失效网址
                //final String address="http://www.weather.com.cn/data/list3/city090101.xml";
                //final String address="http://www.weather.com.cn/data/cityinfo/101090101.html";
                //失效网址

                //final String address="http://www.baidu.com";
                final String address="http://www.weather.com.cn/data/list3/city.xml";
                HttpUtil.sendHttpRequest(address, new HttpCallBackListener() {
                    @Override
                    public void onFinish(String response) {
                        Log.d("tips","连接上了"+address);
                        Looper.prepare();
                        Toast.makeText(TestActivity.this,"连接上了"+address,Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                    @Override
                    public void onError(Exception e) {
                        Log.d("tips","没连上"+address);
                        Looper.prepare();
                        Toast.makeText(TestActivity.this,"没连上"+address,Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
            }
        });
    }
}
