package com.shibobo.littletoys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2017/2/3.
 */

public class XferModeActivity extends AppCompatActivity {
    private XferModeView xferModeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xfermode);
        xferModeView= (XferModeView) findViewById(R.id.xfermodeview);
    }
}
