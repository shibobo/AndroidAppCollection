package com.shibobo.littletoys;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/2/6.
 */

public class MyCounterActivity extends AppCompatActivity {
    private TextView myCounter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counter);
        myCounter= (TextView) findViewById(R.id.myCounter);
        myCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMyCounter();
            }
        });
    }
    private void startMyCounter(){
        ValueAnimator valueAnimator=ValueAnimator.ofInt(0,100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                myCounter.setText("$:"+(Integer)(animation.getAnimatedValue()));
            }
        });
        valueAnimator.setDuration(3000);
        valueAnimator.start();
    }
}
