package com.shibobo.ownviewtest.wipetodelete;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.shibobo.ownviewtest.R;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class WipeToDelete extends ListView implements View.OnTouchListener,GestureDetector.OnGestureListener{
    //手势动作探测器
    private GestureDetector gestureDetector;
    //删除按钮
    private View deleteBtn;
    //列表项布局
    private ViewGroup itemLayout;
    //选中的item
    private int selectedItem;
    //判断删除按钮是否显示
    private boolean isDeleteShown=false;

    private OnDeleteListener listener;

    public WipeToDelete(Context context, AttributeSet attrs){
        super(context, attrs);
        gestureDetector=new GestureDetector(context,this);
        setOnTouchListener(this);
    }
    public interface OnDeleteListener{
        void onDelete(int index);
    }
    public void setOnDeleteListener(OnDeleteListener l){
        listener=l;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isDeleteShown) {
            Log.d("tips", "删除按钮出现了");
            hideDelete();
            return false;
        }else{
            Log.d("tips", "删除按钮没出现");
            return gestureDetector.onTouchEvent(event);
        }
    }
    public void hideDelete(){
        itemLayout.removeView(deleteBtn);
        deleteBtn=null;
        isDeleteShown=false;
    }
    @Override
    public boolean onDown(MotionEvent e) {
        if (!isDeleteShown){
            selectedItem=pointToPosition((int)e.getX(),(int)e.getY());
            Log.d("tips", "选中 "+selectedItem);
        }
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //Log.d("tips", "正在滑动");
        if (!isDeleteShown && Math.abs(velocityX)>Math.abs(velocityY)){
            Log.d("tips", "正在x方向滑动");
            deleteBtn= LayoutInflater.from(getContext()).inflate(R.layout.deletebtn,null);
            deleteBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemLayout.removeView(deleteBtn);
                    deleteBtn=null;
                    isDeleteShown=false;
                    listener.onDelete(selectedItem);
                }
            });
            itemLayout=(ViewGroup) getChildAt(selectedItem-getFirstVisiblePosition());
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            itemLayout.addView(deleteBtn,params);
            isDeleteShown=true;
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public void onLongPress(MotionEvent e) {

    }
}
