package com.shibobo.littletoys;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class LineChart extends View {
    private Paint mPaint;
    private Path mPath;
    private float circleX;
    private float circleY;
    private float radius;
    private int colors[]={
            Color.RED,
            Color.BLUE,
            Color.GRAY,
            Color.GREEN,
            Color.YELLOW};
    public LineChart(Context context) {
        this(context, null);
    }

    public LineChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPaint=new Paint();
        mPath=new Path();
        circleX=200;
        circleY=200;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mPath.moveTo(0,0);
//        mPath.lineTo(100,200);
//        mPath.lineTo(255,600);
//        mPath.lineTo(605,25);
//        mPath.close();
        radius=(float)(Math.random()*250+50);
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        canvas.drawPath(mPath,mPaint);
        int index=(int)(Math.random()*colors.length);
        mPaint.setColor(colors[index]);
        canvas.drawCircle(circleX,circleY,radius,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        circleX=event.getX();
        circleY=event.getY();
        this.invalidate();
        return true;
    }
}
