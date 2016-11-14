package com.shibobo.myviewtest.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.shibobo.myviewtest.R;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class CustomTitleView extends View {
    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;
    private Paint mPaint;
    private Rect mBounds;
    public CustomTitleView(Context context){
        this(context,null,0);
    }
    public CustomTitleView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }
    public CustomTitleView(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        //获得自定义属性
        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView,defStyle,0);
        int n=a.getIndexCount();
        for (int i=0;i<n;i++){
            int attr=a.getIndex(i);
            switch (attr){
                case R.styleable.CustomTitleView_titleText:
                    mTitleText=a.getString(attr);
                    break;
                case R.styleable.CustomTitleView_titleTextColor:
                    mTitleTextColor=a.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CustomTitleView_titleTextSize:
                    mTitleTextSize=a.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle();
        mPaint=new Paint();
        mPaint.setTextSize(mTitleTextSize);
        mBounds=new Rect();
        mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBounds);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitleText=randomText();
                postInvalidate();
            }
        });
    }
    public String randomText(){
        Random random=new Random();
        Set<Integer> set=new HashSet<Integer>();
        while (set.size()<4){
            int randomInt=random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb=new StringBuffer();
        for (Integer i:set){
            sb.append(""+i);
        }
        return sb.toString();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);

        mPaint.setColor(Color.GREEN);
        canvas.drawText(mTitleText,getWidth()/2-mBounds.width()/2,getHeight()/2+mBounds.height()/2,mPaint);
    }
}
