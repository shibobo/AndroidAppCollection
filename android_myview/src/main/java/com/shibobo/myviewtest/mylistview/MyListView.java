package com.shibobo.myviewtest.mylistview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.shibobo.myviewtest.R;

/**
 * Created by Administrator on 2016/11/13 0013.
 */

public class MyListView extends ListView implements View.OnTouchListener,GestureDetector.OnGestureListener{

    private GestureDetector gestureDetector;
    private OnDeleteListener onDeleteListener;
    private Boolean isDeleteShown=false;
    private View deleteButton;
    private int selectedItem;
    private ViewGroup itemLayout;
    public MyListView(Context context, AttributeSet attrs){
        super(context,attrs);
        gestureDetector=new GestureDetector(getContext(),this);
        setOnTouchListener(this);
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isDeleteShown){
            Log.d("tips", "onTouch: 删除按钮出现了");
            itemLayout.removeView(deleteButton);
            deleteButton=null;
            isDeleteShown=false;
            return false;
        }else{
            Log.d("tips", "onTouch: 删除按钮没有出现");
            return gestureDetector.onTouchEvent(event);
//            return false;
        }
    }

    public void setOnDeleteListener(OnDeleteListener l){
        onDeleteListener=l;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if (!isDeleteShown){
            selectedItem=pointToPosition((int) e.getX(),(int) e.getY());
            Log.d("tips", "onDown: 接收到的对象是 "+selectedItem);
        }
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (!isDeleteShown && Math.abs(velocityX)>Math.abs(velocityY)){
            Log.d("tips", "正在向x方向滑动 ");
            deleteButton= LayoutInflater.from(getContext()).inflate(R.layout.delete_button,null);
            deleteButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //click to do sth
                    itemLayout.removeView(deleteButton);
                    deleteButton=null;
                    isDeleteShown=false;
                    onDeleteListener.onDelete(selectedItem);
                }
            });
            itemLayout=(ViewGroup) getChildAt(selectedItem-getFirstVisiblePosition());
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            itemLayout.addView(deleteButton,params);
            isDeleteShown=true;
        }
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
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }
    //定义一个接口
    public interface OnDeleteListener{
        void onDelete(int index);
    }
}
