package com.shibobo.littletoys;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class PolygonesVIew extends View {
    private int center;
    private Rect str_rect;
    private String[] str = {"击杀", "生存", "助攻", "物理", "魔法", "防御", "金钱"};

    private float one_radius;//最外层半径
    private float f1,f2,f3,f4,f5,f6,f7;//seekbar传过来的能力值
    private int defaultSize=300;//控件默认大小
    private Paint center_paint;//绘制中心线的画笔
    private Paint str_paint;//绘制字体画笔
    private Paint one_paint;//最外层多边形画笔
    private Paint two_paint;//次外层多边形画笔
    private Paint three_paint;//次内层多边形画笔
    private Paint four_paint;//内层多边形画笔
    private Paint rank_paint;//能力值画笔

    public PolygonesVIew(Context context) {
        super(context);
    }

    public PolygonesVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
        str_rect=new Rect();
        //初始化中心线画笔
        center_paint=new Paint();
        center_paint.setAntiAlias(true);
        center_paint.setColor(Color.WHITE);
        //初始化字体画笔
        str_paint=new Paint();
        str_paint.setAntiAlias(true);
        str_paint.setColor(getResources().getColor(R.color.colorPrimary));
        str_paint.setTextSize(30);
        str_paint.getTextBounds(str[0],0,str[0].length(),str_rect);
        //初始化最外层多边形画笔
        one_paint=new Paint();
        one_paint.setAntiAlias(true);
        one_paint.setColor(getResources().getColor(R.color.one_color));
        one_paint.setStyle(Paint.Style.FILL);
        //初始化次外层多边形画笔
        two_paint=new Paint();
        two_paint.setAntiAlias(true);
        two_paint.setColor(getResources().getColor(R.color.two_color));
        two_paint.setStyle(Paint.Style.FILL);
        //初始化次内层多边形画笔
        three_paint=new Paint();
        three_paint.setAntiAlias(true);
        three_paint.setColor(getResources().getColor(R.color.three_color));
        three_paint.setStyle(Paint.Style.FILL);
        //初始化内层多边形画笔
        four_paint=new Paint();
        four_paint.setAntiAlias(true);
        four_paint.setColor(getResources().getColor(R.color.four_color));
        four_paint.setStyle(Paint.Style.FILL);
        //初始化能力值画笔
        rank_paint=new Paint();
        rank_paint.setAntiAlias(true);
        rank_paint.setColor(getResources().getColor(R.color.rank_color));
        rank_paint.setStyle(Paint.Style.FILL);
    }

    public PolygonesVIew(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wMode=MeasureSpec.getMode(widthMeasureSpec);
        int wSize=MeasureSpec.getSize(widthMeasureSpec);
        int hMode=MeasureSpec.getMode(heightMeasureSpec);
        int hSize=MeasureSpec.getSize(heightMeasureSpec);
        int width,height;
        if (wMode==MeasureSpec.EXACTLY){
            width=wSize;
        }else{
            width=Math.min(wSize,defaultSize);
        }
        if (hMode==MeasureSpec.EXACTLY){
            height=hSize;
        }else{
            height=Math.min(hSize,defaultSize);
        }
        //控件的中心点
        if (width>=height){
            center=height/2;
        }else{
            center=width/2;
        }
        one_radius=center-getPaddingTop()-2*str_rect.height();
//        one_radius=(float)(0.8*center);
        f1=f2=f3=f4=f5=f6=f7=one_radius/4;
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        PaintFont(canvas);
        onePolygones(canvas);
        twoPloygones(canvas);
        threePloygones(canvas);
        fourPloygones(canvas);
        center(canvas);
        PaintRank(canvas);
    }

    /**
     * 绘制文字
     * @param canvas
     */
    public void PaintFont(Canvas canvas){
        canvas.drawText(str[0],
                (float) (center+one_radius*Math.sin(Math.toRadians(360/7*0)))-str_rect.width()/2,
                (float) (getPaddingTop()+str_rect.height()/2+one_radius-one_radius*Math.cos(Math.toRadians(360/7*0))),
                str_paint);
        canvas.drawText(str[1],
                (float) (center+one_radius*Math.sin(Math.toRadians(360/7*1))+str_rect.height()/2),
                (float) (getPaddingTop()-str_rect.height()+center-one_radius*Math.cos(Math.toRadians(360/7*1))),
                str_paint);
        canvas.drawText(str[2],
                (float) (center+one_radius*Math.sin(Math.toRadians(360/7*2))+str_rect.height()/2),
                (float) (getPaddingTop()-str_rect.height()+center-one_radius*Math.cos(Math.toRadians(360/7*2))),
                str_paint);
        canvas.drawText(str[3],
                (float) (center+one_radius*Math.sin(Math.toRadians(360/7*3))-str_rect.height()),
                (float) (getPaddingTop()+str_rect.height()/2+center-one_radius*Math.cos(Math.toRadians(360/7*3))),
                str_paint);
        canvas.drawText(str[4],
                (float) (center+one_radius*Math.sin(Math.toRadians(360/7*4))-str_rect.height()*2),
                (float) (getPaddingTop()+str_rect.height()/3+center-one_radius*Math.cos(Math.toRadians(360/7*4))),
                str_paint);
        canvas.drawText(str[5],
                (float) (center+one_radius*Math.sin(Math.toRadians(360/7*5))-str_rect.height()*3),
                (float) (getPaddingTop()-str_rect.height()+center-one_radius*Math.cos(Math.toRadians(360/7*5))),
                str_paint);
        canvas.drawText(str[6],
                (float) (center+one_radius*Math.sin(Math.toRadians(360/7*6))-str_rect.height()*3),
                (float) (getPaddingTop()-str_rect.height()*2+center-one_radius*Math.cos(Math.toRadians(360/7*6))),
                str_paint);
    }
    /**
     * 绘制最外层多边形
     * @param canvas
     */
    public void onePolygones(Canvas canvas){
        Path path=new Path();
        path.moveTo(center,center-one_radius);
        for (int i=1;i<str.length;i++){
            path.lineTo((float) (center+one_radius*Math.sin(Math.toRadians(360/7*i))),
                       (float) (center-one_radius*Math.cos(Math.toRadians(360/7*i))));
        }
        path.close();
        canvas.drawPath(path,one_paint);
    }

    /**
     * 绘制次外层多边形
     * @param canvas
     */
    public void twoPloygones(Canvas canvas){
        float radius=one_radius*3/4;
        Path path=new Path();
        path.moveTo(center,center-radius);
        for (int i=1;i<str.length;i++){
            path.lineTo((float) (center+radius*Math.sin(Math.toRadians(360/7*i))),
                    (float) (center-radius*Math.cos(Math.toRadians(360/7*i))));
        }
        path.close();
        canvas.drawPath(path,two_paint);
    }

    /**
     * 绘制次内层多边形
     * @param canvas
     */
    public void threePloygones(Canvas canvas){
        float radius=one_radius*2/4;
        Path path=new Path();
        path.moveTo(center,center-radius);
        for (int i=1;i<str.length;i++){
            path.lineTo((float) (center+radius*Math.sin(Math.toRadians(360/7*i))),
                    (float) (center-radius*Math.cos(Math.toRadians(360/7*i))));
        }
        path.close();
        canvas.drawPath(path,three_paint);
    }
    /**
     * 绘制最内层多边形
     * @param canvas
     */
    public void fourPloygones(Canvas canvas){
        float radius=one_radius*1/4;
        Path path=new Path();
        path.moveTo(center,center-radius);
        for (int i=1;i<str.length;i++){
            path.lineTo((float) (center+radius*Math.sin(Math.toRadians(360/7*i))),
                    (float) (center-radius*Math.cos(Math.toRadians(360/7*i))));
        }
        path.close();
        canvas.drawPath(path,four_paint);
    }
    /**
     * 绘制中心线
     * @param canvas
     */
    public void center(Canvas canvas){
        canvas.save();//保存当前状态
        canvas.rotate(0,center,center);
        float startY=getPaddingTop()+2*str_rect.height();
        float endY=center;
        float rotate_angle=(float) (360/7);//每次旋转的角度
        for (int i=0;i<7;i++){
            canvas.drawLine(center,startY,center,endY,center_paint);
            canvas.rotate(rotate_angle,center,center);
        }
        canvas.restore();
    }
    /**
     * 绘制能力值
     * @param canvas
     */
    public void PaintRank(Canvas canvas){
        Path path=new Path();
        path.moveTo(center,center-f1);
        path.lineTo((float)(center+f2*Math.sin(Math.toRadians(360/7))),
                (float)(center-f2*Math.cos(Math.toRadians(360/7))));
        path.lineTo((float)(center+f3*Math.sin(Math.toRadians(360/7*2))),
                (float)(center-f3*Math.cos(Math.toRadians(360/7*2))));
        path.lineTo((float)(center+f4*Math.sin(Math.toRadians(360/7*3))),
                (float)(center-f4*Math.cos(Math.toRadians(360/7*3))));
        path.lineTo((float)(center+f5*Math.sin(Math.toRadians(360/7*4))),
                (float)(center-f5*Math.cos(Math.toRadians(360/7*4))));
        path.lineTo((float)(center+f6*Math.sin(Math.toRadians(360/7*5))),
                (float)(center-f6*Math.cos(Math.toRadians(360/7*5))));
        path.lineTo((float)(center+f7*Math.sin(Math.toRadians(360/7*6))),
                (float)(center-f7*Math.cos(Math.toRadians(360/7*6))));
        path.close();
        canvas.drawPath(path,rank_paint);
    }

    /**
     * setValue1()到setValue7()用来为seekbar设置值
     * @param value
     */
    public void setValue1(double value){
        f1=(float) (one_radius*value/10);
        invalidate();
    }
    public void setValue2(double value){
        f2=(float) (one_radius*value/10);
        invalidate();
    }
    public void setValue3(double value){
        f3=(float) (one_radius*value/10);
        invalidate();
    }
    public void setValue4(double value){
        f4=(float) (one_radius*value/10);
        invalidate();
    }
    public void setValue5(double value){
        f5=(float) (one_radius*value/10);
        invalidate();
    }
    public void setValue6(double value){
        f6=(float) (one_radius*value/10);
        invalidate();
    }
    public void setValue7(double value){
        f7=(float) (one_radius*value)/10;
        invalidate();
    }
}
