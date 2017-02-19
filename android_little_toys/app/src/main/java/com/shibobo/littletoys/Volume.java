package com.shibobo.littletoys;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.MailTo;
import android.support.annotation.MainThread;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class Volume extends View {
    private Paint mPaint;
    private int index;
    //几种不同的颜色
    private int[] colors=new int[]{
            Color.GREEN,
            Color.BLACK,
            Color.BLUE,
            Color.RED,
            Color.YELLOW,
            Color.WHITE};
    public Volume(Context context) {
        super(context);
    }
    public Volume(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    }
    public Volume(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count=20;
        float evetyWidth=getMeasuredWidth()/count;//每个小矩形的宽度
        float Height=getMeasuredHeight();
        for (int i=0;i<count;i++){
            index=(int) (Math.random()*colors.length);
            Log.d("tips", "index="+index);
            mPaint.setColor(colors[index]);
            double rate=Math.random();//每个矩形高度占总高度的比例
            canvas.drawRect(new RectF(evetyWidth*i,(float)(1-rate)*Height,evetyWidth*(i+1),Height),mPaint);
        }
    }
    public void refresh(){
        this.invalidate();
    }
}
