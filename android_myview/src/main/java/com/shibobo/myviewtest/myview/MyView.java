package com.shibobo.myviewtest.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.util.Measure;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.shibobo.myviewtest.R;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class MyView extends View {
    private int defaultSize;
    public MyView(Context context){
        super(context);
    }
    public MyView(Context context, AttributeSet attrs){
        super(context,attrs);
        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.MyView);
        defaultSize=a.getDimensionPixelSize(R.styleable.MyView_default_size,100);
        a.recycle();
    }
    public int getMySize(int defaultSize,int measureSpec){
        int mySize=defaultSize;

        int mode=MeasureSpec.getMode(measureSpec);
        int size=MeasureSpec.getSize(measureSpec);

        switch (mode){
            case MeasureSpec.UNSPECIFIED:
                mySize=defaultSize;
                break;
            case MeasureSpec.AT_MOST:
                mySize=size;
                break;
            case MeasureSpec.EXACTLY:
                mySize=size;
                break;
        }
        return mySize;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=getMySize(100,widthMeasureSpec);
        int height=getMySize(100,heightMeasureSpec);
        if (width<height){
            height=width;
        }else{
            width=height;
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int r=getMeasuredWidth()/2;//圆的半径
        int centerX=getLeft()+r;
        int centerY=getTop()+r;
        //定义一个画笔
        Paint paint=new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawCircle(centerX,centerX,r,paint);
    }
}
