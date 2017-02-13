package com.example.videoplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.VideoView;

/**
 * Created by Administrator on 2017/2/5.
 */

public class CustomeVideoView extends VideoView {
    int defaultWidth=1920;
    int defaultHeight=1080;
    public CustomeVideoView(Context context) {
        super(context);
    }

    public CustomeVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomeVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=getDefaultSize(defaultHeight,widthMeasureSpec);
        int height=getDefaultSize(defaultHeight,heightMeasureSpec);
        Log.d("Main","w="+width+",h="+height);
        setMeasuredDimension(width,height);
    }
}
