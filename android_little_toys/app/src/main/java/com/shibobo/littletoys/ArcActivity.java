package com.shibobo.littletoys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class ArcActivity extends AppCompatActivity {
    private ArcView mArcView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arcviewlayout);
        mArcView= (ArcView) findViewById(R.id.arcview);
        mArcView.setSweepValue(120);
        mArcView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArcView.refresh();
            }
        });
    }
}
