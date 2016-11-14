package com.shibobo.ownviewtest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class TitleView extends RelativeLayout {
    private Button leftBtn;
    private TextView rightTitle;

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.titlebar,this);
        leftBtn= (Button) findViewById(R.id.left_btn);
        rightTitle= (TextView) findViewById(R.id.right_title);
    }
    public void setLeftButtonListener(OnClickListener listener){
        leftBtn.setOnClickListener(listener);
    }
    public void setTitleText(String txt){
        rightTitle.setText(txt);
    }
}
