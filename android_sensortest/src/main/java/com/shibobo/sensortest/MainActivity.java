package com.shibobo.sensortest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView light_level;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        light_level=(TextView) findViewById(R.id.light_level);
        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //光照传感器
        Sensor sensorLight=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(listenerLight,sensorLight,SensorManager.SENSOR_DELAY_NORMAL);
        //加速度传感器
        Sensor sensorAcc=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listenerAcc,sensorAcc,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager!=null){
            sensorManager.unregisterListener(listenerLight);
            sensorManager.unregisterListener(listenerAcc);
        }
    }
    private SensorEventListener listenerLight=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float value=event.values[0];
            light_level.setText("当前光照强度为: "+value+" lx");
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    private SensorEventListener listenerAcc=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float xValue=Math.abs(event.values[0]);
            float yValue=Math.abs(event.values[1]);
            float zValue=Math.abs(event.values[2]);
            if (xValue>15 || yValue>15 || zValue>15){
                Toast.makeText(MainActivity.this,"你摇动了手机",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
