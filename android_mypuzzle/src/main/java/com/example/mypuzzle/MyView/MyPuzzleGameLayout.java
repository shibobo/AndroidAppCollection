package com.example.mypuzzle.MyView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mypuzzle.Bean.ImagePiece;
import com.example.mypuzzle.R;
import com.example.mypuzzle.Utils.ImageSplitter;

import java.sql.Time;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2017/2/10.
 */

public class MyPuzzleGameLayout extends RelativeLayout implements View.OnClickListener {
    private int mColumn=2;//默认为3x3
    private int mWidth;//容器的宽度
    private int mPadding;//外边距
    private ImageView[] mPuzzleGameItems;//小图片集合
    private int mItemWidth;//每个小图片的宽度
    private int mMargin=3;//内边距
    private Bitmap mBitmap;//整个大图片来源
    private List<ImagePiece> mItemBitmaps;//ImagePiece容器
    private boolean once;//第一次加载
    private Context mContext=getContext();
    private boolean isTimerEnabled=false;//不计时模式
    private int mTime;
    public void setTimerEnabled(boolean isTimerEnabled){
        this.isTimerEnabled=isTimerEnabled;
    }
    private int mLevel=1;//从第一关开始
    private boolean isGameSuccess;//通关成功
    private boolean isGameOver;//时间结束导致游戏失败
    //判断游戏成功的接口
    public interface MyPuzzleGameListener{
        void nextLevel(int nextLevelNum);
        void timeChanged(int currentTime);
        void gameOver();
    }
    private MyPuzzleGameListener mListener;//接口变量
    //设置接口回调
    public void setMyPuzzleGameListener(MyPuzzleGameListener mListener){
        this.mListener=mListener;
    }
    private static final int TIME_CHANGED=0;//时间改变
    private static final int NEXT_LEVEL=1;//下一关
//    private static final int GAME_OVER=2;//游戏结束
    //UI操作
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case TIME_CHANGED:
                    if (isGameSuccess || isGameOver || isPause){
                        return;
                    }
                    if (mListener!=null){
                        mListener.timeChanged(mTime);
                        if (mTime==0){
                            isGameOver=true;
                            mListener.gameOver();
                            return;
                        }
                    }
                    mTime--;
                    mHandler.sendEmptyMessageDelayed(TIME_CHANGED,1000);
                    break;
                case NEXT_LEVEL:
                    mLevel=mLevel+1;
                    if (mListener!=null){
                        mListener.nextLevel(mLevel);
                    }else {
                        nextLevel();
                    }
                    break;
            }
        }
    };
    public MyPuzzleGameLayout(Context context) {
        this(context,null);
    }

    public MyPuzzleGameLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyPuzzleGameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //3dp转换为3px
        mMargin=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,mMargin,getResources().getDisplayMetrics());
        //取得四个边距之中最小的
        mPadding=min(getPaddingLeft(),getPaddingRight(),getPaddingTop(),getPaddingBottom());
    }

    /**
     * 获取多个参数的最小值
     * @param params
     * @return
     */
    private int min(int...params) {
        int min=params[0];
        for (int param:params){
            if (param<min){
                min=param;
            }
        }
        return min;
    }

    /**
     * 设置容器始终为正方形
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取拼图游戏布局的边长
        mWidth=Math.min(getMeasuredWidth(),getMeasuredHeight());
        if (!once){
            //进行切图，以及排序
            initBitmap();
            //设置imageViiew的宽高
            initItems();
            //判断是否开启计时
            checkTimerEnabled();
            //判断是否游戏成功
            checkSuccess();
            once=true;
        }
        setMeasuredDimension(mWidth,mWidth);
    }

    private void checkTimerEnabled() {
        if (isTimerEnabled){
         //根据当前等级设置时间
            countTimeBaseLevel();
            mHandler.sendEmptyMessage(TIME_CHANGED);
        }
    }

    private void countTimeBaseLevel() {
        mTime=(int) Math.pow(2,mLevel)*30;
    }

    private void initBitmap(){
        if (mBitmap==null){
            //设置拼图所使用的的图片
            mBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.my);
        }
        //将图片切成mColumn*mColumn个小图片
        mItemBitmaps= ImageSplitter.split(mBitmap,mColumn);
        Collections.sort(mItemBitmaps, new Comparator<ImagePiece>() {
            @Override
            public int compare(ImagePiece imagePiece, ImagePiece t1) {
                return Math.random()>0.5?1:-1;
            }
        });
    }
    private void initItems(){
        //获得item的宽和高
        int childWidth=(mWidth-mPadding*2-mMargin*(mColumn-1))/mColumn;
        mItemWidth=childWidth;
        mPuzzleGameItems=new ImageView[mColumn*mColumn];
        //放置item
        for (int i=0;i<mPuzzleGameItems.length;i++){
            ImageView item=new ImageView(getContext());
            item.setOnClickListener(this);
            item.setImageBitmap(mItemBitmaps.get(i).bitmap);
            mPuzzleGameItems[i]=item;
            item.setId(i+1);//图片编号
            item.setTag(i+"_"+mItemBitmaps.get(i).index);//图片编号以及正确的序号
            RelativeLayout.LayoutParams layoutParams=new LayoutParams(mItemWidth,mItemWidth);
            //设置横向边距、不是最后一列
            if ((i+1)%mColumn!=0){
                layoutParams.rightMargin=mMargin;
            }
            //不是第一列
            if (i%mColumn!=0){
                layoutParams.addRule(RelativeLayout.RIGHT_OF,mPuzzleGameItems[i-1].getId());
            }
            //如果不是第一行，设置纵向边距
            if ((i+1)>mColumn){
                layoutParams.topMargin=mMargin;
                layoutParams.addRule(RelativeLayout.BELOW,mPuzzleGameItems[i-mColumn].getId());
            }
            addView(item,layoutParams);
        }
    }
    private ImageView mFirst;
    private ImageView mSecond;
    private boolean isAniming;//动画运行标志位
    private RelativeLayout mAnimLayout;
    /**
     * 点击item的事件
     */
    @Override
    public void onClick(View view) {
//        Toast.makeText(mContext,view.getId()+" "+view.getTag(), Toast.LENGTH_SHORT).show();
        if (isAniming){
            return;
        }
        //如果两次点击同一个
        if (mFirst==view){
            mFirst.setColorFilter(null);
            mFirst=null;
            return;
        }
        //点击第一个view
        if (mFirst==null){
            mFirst=(ImageView) view;
            mFirst.setColorFilter(Color.parseColor("#55ff0000"));
        //点击第二个view
        }else{
            mSecond=(ImageView) view;
            exchangeView();
        }

    }

    /**
     * 交换两个item
     */
    private void exchangeView(){
        mFirst.setColorFilter(null);
        setupAnimLayout();
        //添加FirstView
        ImageView first=new ImageView(mContext);
        first.setImageBitmap(mItemBitmaps.get(getImageIndexByTag((String) mFirst.getTag())).bitmap);
        LayoutParams lp=new LayoutParams(mItemWidth,mItemWidth);
        lp.leftMargin=mFirst.getLeft()-mPadding;
        lp.topMargin=mFirst.getTop()-mPadding;
        first.setLayoutParams(lp);
        mAnimLayout.addView(first);
        //添加secondView
        ImageView second=new ImageView(mContext);
        second.setImageBitmap(mItemBitmaps.get(getImageIndexByTag((String) mSecond.getTag())).bitmap);
        LayoutParams lp2=new LayoutParams(mItemWidth,mItemWidth);
        lp2.leftMargin=mSecond.getLeft()-mPadding;
        lp2.topMargin=mSecond.getTop()-mPadding;
        second.setLayoutParams(lp2);
        mAnimLayout.addView(second);
        //设置第一个动画
        TranslateAnimation animFirst=new TranslateAnimation(0,mSecond.getLeft()-mFirst.getLeft(),0,mSecond.getTop()-mFirst.getTop());
        animFirst.setDuration(300);
        animFirst.setFillAfter(true);
        first.startAnimation(animFirst);
        //设置第二个动画
        TranslateAnimation animSecond=new TranslateAnimation(0,mFirst.getLeft()-mSecond.getLeft(),0,mFirst.getTop()-mSecond.getTop());
        animSecond.setDuration(300);
        animSecond.setFillAfter(true);
        second.startAnimation(animSecond);

        animFirst.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAniming=true;
                mFirst.setVisibility(INVISIBLE);
                mSecond.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                String firstTag=(String) mFirst.getTag();
                String secondTag=(String) mSecond.getTag();
                String[] firstParams=firstTag.split("_");
                String[] secondParams=secondTag.split("_");
                mFirst.setImageBitmap(mItemBitmaps.get(Integer.parseInt(secondParams[0])).bitmap);
                mSecond.setImageBitmap(mItemBitmaps.get(Integer.parseInt(firstParams[0])).bitmap);
                mFirst.setTag(secondTag);
                mSecond.setTag(firstTag);
                mFirst.setVisibility(VISIBLE);
                mSecond.setVisibility(VISIBLE);
                mFirst=mSecond=null;
                mAnimLayout.removeAllViews();
                checkSuccess();//判断游戏是否成功
                isAniming=false;
//                Toast.makeText(mContext, "交换完成", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 设置动画层
     */
    private void setupAnimLayout(){
        if (mAnimLayout==null){
            mAnimLayout=new RelativeLayout(mContext);
            addView(mAnimLayout);
//            Toast.makeText(mContext, "mAnimLayout添加完毕", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断游戏是否成功
     */
    private void checkSuccess(){
        boolean isSuccess=true;
        for (int i=0;i<mPuzzleGameItems.length;i++) {
            ImageView first = mPuzzleGameItems[i];
            Log.d("main", getIndexByTag((String) first.getTag()) + "");
            if (getIndexByTag((String) first.getTag()) != i) {
                isSuccess = false;
            }
        }
        if (isSuccess){
//            Toast.makeText(mContext, "Success, Next Level!", Toast.LENGTH_SHORT).show();
            isSuccess=true;
            mHandler.removeMessages(TIME_CHANGED);
            mHandler.sendEmptyMessage(NEXT_LEVEL);
        }
    }
    public void nextLevel(){
        this.removeAllViews();
        mAnimLayout=null;
        mColumn++;
        isGameSuccess=false;
        checkTimerEnabled();
        initBitmap();
        initItems();
//        Toast.makeText(mContext, mItemBitmaps.get(10).toString(), Toast.LENGTH_SHORT).show();
    }
    /**
     * 获得图片的真实索引
     */
    private int getIndexByTag(String tag){
        String[] split=tag.split("_");
        return Integer.parseInt(split[1]);
    }
    private int getImageIndexByTag(String tag){
        String[] split=tag.split("_");
        return Integer.parseInt(split[0]);
    }
    /**
     * 重新开始本关
     */
    public void restartGame(){
        isGameOver=false;
        mColumn--;
        nextLevel();
    }
    private boolean isPause;//暂停游戏
    public void pause(){
        isPause=true;
        mHandler.removeMessages(TIME_CHANGED);
    }
    public void resume(){
        if (isPause){
            isPause=false;
            mHandler.sendEmptyMessage(TIME_CHANGED);
        }
    }
}
