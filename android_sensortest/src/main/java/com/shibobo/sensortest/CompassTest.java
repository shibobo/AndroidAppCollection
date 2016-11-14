package com.shibobo.sensortest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class CompassTest extends AppCompatActivity {

    private SensorManager sensorManager;
    private TextView showOrientation;
    private ImageView compassImg;
    private ImageView arrowImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass_test);
        showOrientation=(TextView) findViewById(R.id.showOrientation);
        compassImg=(ImageView) findViewById(R.id.compass_img);
        arrowImg=(ImageView) findViewById(R.id.arrow_img);

        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor magneticSensor=sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor accelerometerSensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener,magneticSensor,SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(listener,accelerometerSensor,SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager!=null){
            sensorManager.unregisterListener(listener);
        }
    }
    private SensorEventListener listener=new SensorEventListener() {

        float[] accelerometerValues=new float[3];
        float[] magneticValues=new float[3];
        private float lastRotateDegree;

        @Override
        public void onSensorChanged(SensorEvent event) {

            if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
                accelerometerValues=event.values.clone();
            }else if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
                magneticValues=event.values.clone();
            }

            float[] R=new float[9];
            float[] values=new float[3];
            SensorManager.getRotationMatrix(R,null,accelerometerValues,magneticValues);
            SensorManager.getOrientation(R,values);
            showOrientation.setText("");
            showOrientation.setText("RotZ="+Math.toDegrees(values[0])+"\n"+
                                    "RotX="+Math.toDegrees(values[1])+"\n"+
                                    "RotY="+Math.toDegrees(values[2])+"\n");
            float rotateDegree=-(float) Math.toDegrees(values[0]);
            if (Math.abs(rotateDegree-lastRotateDegree)>1){
                RotateAnimation rotateAnimation=new RotateAnimation(lastRotateDegree,rotateDegree, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                rotateAnimation.setFillAfter(true);
                compassImg.startAnimation(rotateAnimation);
                lastRotateDegree=rotateDegree;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
