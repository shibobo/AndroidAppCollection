package com.shibobo.littletoys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/2/3.
 */

public class FingerMoveActivity extends AppCompatActivity {
    private FingerMoveView btn_move;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fingermove);
        btn_move= (FingerMoveView) findViewById(R.id.btn_move);
    }
}
