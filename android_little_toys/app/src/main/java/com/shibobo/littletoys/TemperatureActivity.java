package com.shibobo.littletoys;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Administrator on 2016/11/19 0019.
 */

public class TemperatureActivity extends AppCompatActivity {
    private TemperatureView temperatureView;
    private float currentValue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperatureviewlayout);
        temperatureView= (TemperatureView) findViewById(R.id.temperatureView);
        currentValue=22;
        dynamic(currentValue);
        temperatureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentValue=(float) (Math.random()*40);
                if (currentValue>40){
                    currentValue=40;
                }else if(currentValue<0){
                    currentValue=0;
                }
                dynamic(currentValue);
            }
        });
    }
    private void dynamic(final float value){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (float i=0;i<=value;i++){
                    temperatureView.setCurrentTemp(i);
                    try {
                        Thread.sleep(30);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    temperatureView.postInvalidate();
                }
            }
        }).start();
    }
}
