package com.shibobo.myviewtest.circleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.View;
import android.widget.Toast;

import com.shibobo.myviewtest.R;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class CircleView extends View implements View.OnClickListener {
    private int mBorderColor;
    private float mBorderWidth;

    private Paint mPaint;
    private float radius;//表盘半径
    private float largeLength;//长针的长度
    private float shortLength;//短针的长度
    public CircleView(Context context){
        super(context);
        init();
    }
    public CircleView(Context context, AttributeSet attrs){
        super(context,attrs);
        init();
        TypedArray typedArray=context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleView,0,0);
        try {
            mBorderColor=typedArray.getColor(R.styleable.CircleView_border_color, Color.GREEN);
            mBorderWidth=typedArray.getDimension(R.styleable.CircleView_border_width,2);
        }finally {
            typedArray.recycle();
        }
    }
    public CircleView(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        init();
    }
    public void init(){
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setColor(mBorderColor);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画出黑色背景
        canvas.drawColor(Color.BLACK);
        //画出有圆角的矩形,
        mPaint.setColor(Color.WHITE);
        //上下左右各自留0.1倍的width作为margin
        RectF rectF=new RectF();
        rectF.left=(float) 0.1*getWidth();
        rectF.top=(float) 0.1*getHeight();
        rectF.right=(float) 0.9*getWidth();
        rectF.bottom=(float) 0.9*getHeight();
        canvas.drawRoundRect(rectF,30,30,mPaint);
        //画出圆形
        mPaint.setColor(mBorderColor);
        if (getWidth()>=getHeight()){
            radius=(float) 0.3*getHeight();
        }else{
            radius=(float) 0.3*getWidth();
        }
        float center_x=getWidth()/2;
        float center_y=getHeight()/2;
        canvas.drawCircle(center_x,center_y,radius,mPaint);
        float start_x,start_y;
        float end_x,end_y;
        largeLength=(float) 0.2*radius;
        shortLength=(float) 0.1*radius;
        for (int i=0;i<60;i++){
            start_x=radius*(float) Math.cos(Math.PI/180*6*i);
            start_y=radius*(float) Math.sin(Math.PI/180*6*i);
            if (i%5==0){
                end_x=start_x+largeLength*(float) Math.cos(Math.PI/180*6*i);
                end_y=start_y+largeLength*(float) Math.sin(Math.PI/180*6*i);
            }else{
                end_x=start_x+shortLength*(float) Math.cos(Math.PI/180*6*i);
                end_y=start_y+shortLength*(float) Math.sin(Math.PI/180*6*i);
            }
            start_x+=center_x;
            start_y+=center_y;
            end_x+=center_x;
            end_y+=center_y;
            canvas.drawLine(start_x,start_y,end_x,end_y,mPaint);
        }
        canvas.drawCircle(center_x,center_y,(float) 0.1*radius,mPaint);
        canvas.drawLine(center_x,
                center_y,
                (float) (center_x+radius*Math.cos(Math.PI*45/180)),
                (float)(center_y-radius*Math.sin(Math.PI*45/180)),
                mPaint);
    }

    @Override
    public void onClick(View v) {

    }
}
