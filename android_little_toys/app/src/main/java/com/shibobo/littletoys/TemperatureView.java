package com.shibobo.littletoys;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/11/19 0019.
 */

/**
 * 步骤：
 *  1、整个背景圆（可有可无）
 *  2、进度弧（分为三段，颜色分别为绿黄红）
 *  3、进度弧上的文字（正常，预警，警告）
 *  4、刻度弧（紧靠着进度弧内侧的黑色弧）
 *  5、刻度
 *  6、中间的圆
 *  7、指针
 *  8、当前温度
 */

public class TemperatureView extends View{
    //自定义属性
    private float progressWidth;
    private String tempText;
    private float tempTextSize;

    private Paint outCirclePaint;//绘制外圆的画笔
    private Paint progresspaint;//绘制进度的画笔
    private Paint progressTextpaint;//绘制进度上的文字的画笔
    private Paint scaleArcPaint;//绘制刻度的画笔
    private Paint centerCirclePaint;//绘制中间的圆的画笔
    private Paint centerPointerPaint;//绘制指针的画笔
    private Paint panelTextPaint;//绘制表盘上的文字

    private float mSize;//最终的大小
    private static final int PADDING=40;//进度条的默认宽度
    private static final int PROGRESSTEXTSIZE=30;
    private int mTikeCount=40;//40个刻度包括长短
    private float outCircleRadius;//外圆半径
    private float progressRadius;//进度半径
    private float scaleArcRadius;//刻度半径
    private float centerCircleRadius;//中间的圆半径
    private float centerPointerRadius;//中间指针的半径

    private float currentTemp;

    public TemperatureView(Context context) {
        this(context, null);
    }

    public TemperatureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TemperatureView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //获取自定义属性
        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.TemperatureView);
        progressWidth=ta.getDimension(R.styleable.TemperatureView_progressWidth,PADDING);
        tempText=ta.getString(R.styleable.TemperatureView_tempText);
        tempTextSize=ta.getDimensionPixelSize(R.styleable.TemperatureView_tempTextSize,15);
        ta.recycle();
        initPaint();
    }

    private void initPaint() {
        outCirclePaint=new Paint();
        outCirclePaint.setAntiAlias(true);
        outCirclePaint.setStyle(Paint.Style.FILL);
        outCirclePaint.setStrokeWidth(5);
        //添加一个渐变的背景
//        LinearGradient mLinearGradient=new LinearGradient(0,0,50,0,Color.RED,Color.GREEN, Shader.TileMode.CLAMP);
//        outCirclePaint.setShader(mLinearGradient);
        outCirclePaint.setColor(getResources().getColor(R.color.temperatureBackground));

        progresspaint=new Paint();
        progresspaint.setAntiAlias(true);
        progresspaint.setStyle(Paint.Style.STROKE);
        progresspaint.setStrokeWidth(progressWidth);
        progresspaint.setStrokeCap(Paint.Cap.ROUND);
        progresspaint.setStrokeJoin(Paint.Join.ROUND);

        progressTextpaint=new Paint();
        progressTextpaint.setAntiAlias(true);
        progressTextpaint.setStyle(Paint.Style.FILL);
        progressTextpaint.setColor(Color.BLACK);
        progressTextpaint.setTypeface(Typeface.DEFAULT);

        scaleArcPaint=new Paint();
        scaleArcPaint.setAntiAlias(true);
        scaleArcPaint.setStyle(Paint.Style.STROKE);
        scaleArcPaint.setStrokeWidth(2);
        scaleArcPaint.setColor(Color.BLACK);
        scaleArcPaint.setTextSize(30);

        centerCirclePaint=new Paint();
        centerCirclePaint.setAntiAlias(true);
        centerCirclePaint.setStyle(Paint.Style.FILL);
        centerCirclePaint.setColor(getResources().getColor(R.color.center_color));

        centerPointerPaint=new Paint();
        centerPointerPaint.setAntiAlias(true);
        centerPointerPaint.setStyle(Paint.Style.FILL);
        centerPointerPaint.setColor(getResources().getColor(R.color.center_pointer_color));

        panelTextPaint=new Paint();
        panelTextPaint.setAntiAlias(true);
        panelTextPaint.setStyle(Paint.Style.FILL);
        panelTextPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int realWidth=MeasureSpec.getSize(widthMeasureSpec);
        int realHeigth=MeasureSpec.getSize(heightMeasureSpec);
        //绘制成为正方形边框
        mSize=Math.min(realWidth,realHeigth);
        setMeasuredDimension((int)mSize,(int)mSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画布平移，方便确定坐标
        canvas.translate(mSize/2,mSize/2);
        //绘制外圆
        drawOutCircle(canvas);
        //画刻度
        drawProgress(canvas);
        //画进度上的文字
        drawProgressText(canvas);
        //画出表盘
        drawPanel(canvas);
    }

    /**
     * 画出外圆
     * @param canvas
     */
    private void drawOutCircle(Canvas canvas) {
        outCircleRadius=(float) (mSize*0.45);
        canvas.drawCircle(0,0,outCircleRadius,outCirclePaint);
        canvas.save();
    }

    /**
     * 画出进度
     * @param canvas
     */
    private void drawProgress(Canvas canvas){
        float progressRadius=(float) (mSize*0.45-10);
        canvas.save();
        RectF rectF=new RectF(-progressRadius,-progressRadius,progressRadius,progressRadius);
        //设置为圆角
        progresspaint.setStrokeCap(Paint.Cap.ROUND);
        progresspaint.setColor(Color.GREEN);
        canvas.drawArc(rectF,150,120,false,progresspaint);

        progresspaint.setStrokeCap(Paint.Cap.ROUND);
        progresspaint.setColor(Color.RED);
        canvas.drawArc(rectF,330,60,false,progresspaint);

        progresspaint.setStrokeCap(Paint.Cap.BUTT);
        progresspaint.setColor(Color.YELLOW);
        canvas.drawArc(rectF,270,60,false,progresspaint);

        canvas.restore();
    }

    /**
     * 画出进度上的文字
     * @param canvas
     */
    private void drawProgressText(Canvas canvas){
        canvas.save();
        String normal="正常";
        String warn="预警";
        String danger="警告";
        //旋转画布绘制文字
        canvas.rotate(-60,0,0);
        progressTextpaint.setTextSize(PROGRESSTEXTSIZE);
        progressRadius=outCircleRadius-PADDING/2;
        canvas.drawText(normal,0,-progressRadius,progressTextpaint);
        canvas.rotate(60,0,0);//canvas回到初始位置

        canvas.rotate(30,0,0);
        canvas.drawText(warn,0,-progressRadius,progressTextpaint);
        canvas.rotate(-30,0,0);//canvas回到初始位置

        canvas.rotate(90,0,0);
        canvas.drawText(danger,0,-progressRadius,progressTextpaint);
        canvas.rotate(-90,0,0);//canvas回到初始位置
        canvas.restore();
    }

    /**
     * 画出表盘
     * @param canvas
     */
    private void drawPanel(Canvas canvas) {
        //画出刻度弧
        drawScaleArc(canvas);
        //画出中间的圆
        drawCenterCircle(canvas);
        //绘制指针
        drawCenterPointer(canvas);
        //绘制文字
        drawPanelText(canvas);
    }

    /**
     * 画出刻度弧
     * @param canvas
     */
    private void drawScaleArc(Canvas canvas){
        scaleArcRadius=outCircleRadius-PADDING;
        canvas.save();
        //画出弧
        RectF rectF=new RectF(-scaleArcRadius,-scaleArcRadius,scaleArcRadius,scaleArcRadius);
        canvas.drawArc(rectF,150,240,false,scaleArcPaint);
        //要旋转的角度
        float mAngle=240f/mTikeCount;
        canvas.rotate(-120,0,0);
        for (int i=0;i<=mTikeCount;i++){
            float shortLength=(float)(0.1*scaleArcRadius);
            float longLength=(float)(0.2*scaleArcRadius);
            if (i%5==0){
                canvas.drawLine(0,-scaleArcRadius,0,-scaleArcRadius+longLength,scaleArcPaint);
                canvas.rotate(mAngle,0,0);
                canvas.drawText(i+"",-shortLength,(float) (-scaleArcRadius+longLength*1.5),scaleArcPaint);
            }else{
                canvas.drawLine(0,-scaleArcRadius,0,-scaleArcRadius+shortLength,scaleArcPaint);
                canvas.rotate(mAngle,0,0);
            }
        }
        canvas.rotate(120,0,0);//回到原来的位置
        canvas.restore();
    }

    /**
     * 画出中间的圆
     * @param canvas
     */
    private void drawCenterCircle(Canvas canvas){
        canvas.save();
        centerCircleRadius=(float) (0.2*outCircleRadius);
        canvas.drawCircle(0,0,centerCircleRadius,centerCirclePaint);
        canvas.restore();
    }

    /**
     * 绘制中心的指针
     */
    private void drawCenterPointer(Canvas canvas){
        centerPointerRadius=(float) (0.5*centerCircleRadius);
        RectF rectF=new RectF(-centerPointerRadius,-centerPointerRadius,centerPointerRadius,centerCircleRadius);
        //RectF rectF=new RectF(-100,-100,100,100);
        canvas.save();
        canvas.rotate(-120,0,0);
        float rAngle=currentTemp*6.0f;
        canvas.rotate(rAngle,0,0);
        Path mPath=new Path();
        mPath.moveTo(centerPointerRadius,0);
        mPath.addArc(rectF,0,180);
        mPath.lineTo(0,-centerPointerRadius*6);
        mPath.lineTo(centerPointerRadius,0);
//        mPath.lineTo();
        mPath.close();
        canvas.drawPath(mPath,centerPointerPaint);
        canvas.rotate(120-rAngle,0,0);
        canvas.restore();
    }

    /**
     * 绘制文字
     * @param canvas
     */
    private void drawPanelText(Canvas canvas){
        canvas.save();
        String text=tempText;
        float length=panelTextPaint.measureText(text);
        panelTextPaint.setTextSize(tempTextSize);
        canvas.drawText(text,-length/2,scaleArcRadius/2,panelTextPaint);

        String temp=currentTemp+"℃";
        float tempLength=panelTextPaint.measureText(temp);
        panelTextPaint.setTextSize(tempTextSize+5);
        canvas.drawText(temp,-tempLength/2,scaleArcRadius*2/3,panelTextPaint);
        canvas.restore();
    }
    //一些setter和getter

    public void setCurrentTemp(float currentTemp) {
        this.currentTemp = currentTemp;
    }

    public float getCurrentTemp() {
        return currentTemp;
    }

    public void setProgressWidth(float progressWidth) {
        this.progressWidth = progressWidth;
    }

    public float getProgressWidth() {
        return progressWidth;
    }

    public void setTempText(String tempText) {
        this.tempText = tempText;
    }

    public String getTempText() {
        return tempText;
    }

    public void setTempTextSize(float tempTextSize) {
        this.tempTextSize = tempTextSize;
    }

    public float getTempTextSize() {
        return tempTextSize;
    }
}
