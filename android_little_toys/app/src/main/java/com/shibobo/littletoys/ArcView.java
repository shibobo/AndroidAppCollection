package com.shibobo.littletoys;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class ArcView extends View{

    private Paint mPaint1;//空心画笔
    private Paint mPaint2;//实心心画笔
    private Paint mPaint3;//字体画笔
    private float mSweepValue=60;
    private Rect str_rect;

    public ArcView(Context context,AttributeSet attrs) {
        super(context,attrs);
        mPaint1=new Paint();
        mPaint2=new Paint();
        mPaint3=new Paint();
        str_rect=new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint1.setColor(Color.BLACK);
        mPaint1.setStyle(Paint.Style.STROKE);//设置为空心画笔
        mPaint1.setStrokeWidth(5);//画笔宽度
        mPaint1.setAntiAlias(true);//消除锯齿
        canvas.drawRoundRect(new RectF((float)(getMeasuredWidth()*0.1),
                                       (float)(getMeasuredHeight()*0.1),
                                       (float)(getMeasuredWidth()*0.9),
                                       (float)(getMeasuredWidth()*0.9)),10,10,mPaint1);
//        canvas.drawRoundRect(new RectF(0,0,getMeasuredWidth(),getMeasuredHeight()),20,20,mPaint);
//        canvas.drawRect(10,10,180,180,mPaint);
        float cX=getMeasuredWidth()/2;
        float cY=getMeasuredHeight()/2;
        float radius=(float) getMeasuredWidth()/4;
        //Log.d("tips", "notice:"+getWidth());
        //Log.d("tips", "notice:"+getMeasuredWidth());
        mPaint2.setColor(Color.GREEN);
        mPaint2.setStyle(Paint.Style.FILL);
        mPaint2.setAntiAlias(true);
        canvas.drawCircle(cX,cY,radius,mPaint2);

        mPaint1.setColor(Color.RED);
        mPaint1.setStrokeWidth(20);
        canvas.drawArc(new RectF((float)(getMeasuredWidth()*0.2),
                       (float)(getMeasuredHeight()*0.2),
                       (float)(getMeasuredWidth()*0.8),
                       (float)(getMeasuredWidth()*0.8)),0,mSweepValue,false,mPaint1);
        mPaint3.setAntiAlias(true);
        mPaint3.setColor(Color.BLUE);
        mPaint3.setTextSize(40);
        String text="当前值为: "+mSweepValue;
        mPaint3.getTextBounds(text,0,text.length(),str_rect);
        canvas.drawText(text,cX-str_rect.width()/2,cY,mPaint3);
    }
    public void setSweepValue(float sweepValue){
        mSweepValue=sweepValue;
        this.invalidate();
    }
    public void refresh(){
        mSweepValue=(float)(360*Math.random());
        this.invalidate();
    }

}
