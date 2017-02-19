package com.shibobo.littletoys;

import android.content.Context;
import android.drm.DrmStore;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/2/3.
 */

public class FingerMoveView extends Button {
    private int initX,initY;
    public FingerMoveView(Context context) {
        super(context);
    }

    public FingerMoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FingerMoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rawX=(int)event.getRawX();
        int rawY=(int)event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                initX=rawX;
                initY=rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX=rawX-initX;
                int offsetY=rawY-initY;
                int l=getLeft()+offsetX;
                int t=getTop()+offsetY;
                int r=getRight()+offsetX;
                int b=getBottom()+offsetY;
                int screen_width=getResources().getDisplayMetrics().widthPixels;
                int screen_height=getResources().getDisplayMetrics().heightPixels;
                if (r>=screen_width){
                    r=screen_width;
                }
                if (b>=screen_height){
                    b=screen_height;
                }
                layout(l,t,r,b);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
