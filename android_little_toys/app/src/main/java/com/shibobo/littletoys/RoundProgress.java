package com.shibobo.littletoys;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class RoundProgress extends View{
    private Paint paint;
    private int width;//画布宽度
    private int height;//画布高度
    //自定义属性
    private int ringColor;//底环颜色
    private int ringProgressColor;//进度环颜色
    private int ringWidth;//环宽度
    private int textSize;//字体大小
    private int textColor;//字体颜色
    private int max;//最大进度
    private int progress;//当前进度

    public RoundProgress(Context context) {
        this(context, null);
    }

    public RoundProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint=new Paint();
        //消除锯齿
        paint.setAntiAlias(true);
        //得到自定义属性
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.RoundProgress);
        ringColor=typedArray.getColor(R.styleable.RoundProgress_ringColor, Color.GREEN);
        ringProgressColor=typedArray.getColor(R.styleable.RoundProgress_ringProgressColor,Color.RED);
        ringWidth=(int)typedArray.getDimension(R.styleable.RoundProgress_ringWidth,20);
        textSize=(int)typedArray.getDimension(R.styleable.RoundProgress_textSize,40);
        textColor=typedArray.getColor(R.styleable.RoundProgress_textColor,Color.BLACK);
        max=typedArray.getInteger(R.styleable.RoundProgress_max,100);
        progress=typedArray.getInteger(R.styleable.RoundProgress_progress,60);
        //回收
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width=(int) (0.9*getMeasuredWidth());
//        height=(int) (0.9*getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //圆心坐标
        float circleX=width/2;
        float circleY=circleX;
        float radius=circleX-ringWidth/2;
        //1.绘制圆环
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ringWidth);
        paint.setColor(ringColor);
        canvas.drawCircle(circleX,circleX,radius,paint);
        //2.绘制圆弧
        paint.setColor(ringProgressColor);
        RectF oval=new RectF(ringWidth/2,ringWidth/2,width-ringWidth/2,width-ringWidth/2);
        canvas.drawArc(oval,0,progress*360/max,false,paint);
        //3.绘制文字
        String text=progress*100/max+"%";
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(0);
        //得到绘制字体的大小范围
        Rect rect=new Rect();
        paint.getTextBounds(text,0,text.length(),rect);
        canvas.drawText(text,width/2-rect.width()/2,width/2+rect.height()/2,paint);
    }
    public void changeSize(int value){
        width=value;
        this.invalidate();
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }
}
