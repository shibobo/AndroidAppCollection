package com.shibobo.littletoys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class VolumeActivity extends AppCompatActivity {
    private Volume mVolume;
    private Button refresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volumelayout);
        mVolume= (Volume) findViewById(R.id.mVolume);
        refresh= (Button) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVolume.refresh();
            }
        });
        mVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVolume.refresh();
            }
        });
    }
}
