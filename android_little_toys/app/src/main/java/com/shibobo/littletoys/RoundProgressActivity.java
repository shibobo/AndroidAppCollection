package com.shibobo.littletoys;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class RoundProgressActivity extends AppCompatActivity {
    private SeekBar changeSize;
    private RoundProgress round;
    private Button redraw;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roundprogresslayout);
        changeSize= (SeekBar) findViewById(R.id.changeSize);
        round= (RoundProgress) findViewById(R.id.round);
        changeSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                round.changeSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        dynamicDraw(round.getProgress());//已加载就进行重绘
        redraw= (Button) findViewById(R.id.redraw);
        redraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamicDraw(round.getProgress());
            }
        });
        round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamicDraw(round.getProgress());//单击绘制
            }
        });
    }
    public void dynamicDraw(final int value){
        //当前进度条动态显示
        new Thread(new Runnable() {
            @Override
            public void run() {
                round.setMax(100);
                round.setProgress(0);
                for (int i=0;i<value;i++){
                    round.setProgress(round.getProgress()+1);
                    SystemClock.sleep(30);//30ms绘制
                    round.postInvalidate();
                }
            }
        }).start();
    }
}
