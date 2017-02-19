package com.shibobo.littletoys;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/2/6.
 */

public class SpecialMenuActivity extends AppCompatActivity {
    private ImageView open_menu,one,two,three,four;
    private boolean isOpen=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.special_menu);
        open_menu= (ImageView) findViewById(R.id.open_menu);
        one= (ImageView) findViewById(R.id.one);
        two= (ImageView) findViewById(R.id.two);
        three= (ImageView) findViewById(R.id.three);
        four= (ImageView) findViewById(R.id.four);
        open_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen){
                    closeAnim();
                }else{
                    startAnim();
                }
            }
        });
    }
    private void startAnim(){
        ObjectAnimator animator_open=ObjectAnimator.ofFloat(open_menu,"alpha",1f,0.5f);
        ObjectAnimator animator_one=ObjectAnimator.ofFloat(one,"translationY",0,-200f);
        ObjectAnimator animator_two=ObjectAnimator.ofFloat(two,"translationX",0,200f);
        ObjectAnimator animator_three=ObjectAnimator.ofFloat(three,"translationY",0,200f);
        ObjectAnimator animator_four=ObjectAnimator.ofFloat(four,"translationX",0,-200f);
        AnimatorSet set=new AnimatorSet();
        set.setDuration(500);
        set.setInterpolator(new DecelerateInterpolator());
        set.playTogether(animator_open,
                         animator_one,
                         animator_two,
                         animator_three,
                         animator_four);
        set.start();
        isOpen=true;
        Toast.makeText(this, "灵动菜单已打开", Toast.LENGTH_SHORT).show();
    }
    private void closeAnim(){
        ObjectAnimator animator_close=ObjectAnimator.ofFloat(open_menu,"alpha",0.5f,1f);
        ObjectAnimator animator_one=ObjectAnimator.ofFloat(one,"translationY",0);
        ObjectAnimator animator_two=ObjectAnimator.ofFloat(two,"translationX",0);
        ObjectAnimator animator_three=ObjectAnimator.ofFloat(three,"translationY",0);
        ObjectAnimator animator_four=ObjectAnimator.ofFloat(four,"translationX",0);
        AnimatorSet set=new AnimatorSet();
        set.setDuration(500);
        set.setInterpolator(new DecelerateInterpolator());
        set.playTogether(animator_close,
                animator_one,
                animator_two,
                animator_three,
                animator_four);
        set.start();
        isOpen=false;
        Toast.makeText(this, "灵动菜单已关闭", Toast.LENGTH_SHORT).show();
    }
}
