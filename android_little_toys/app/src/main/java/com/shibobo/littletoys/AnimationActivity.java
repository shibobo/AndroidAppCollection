package com.shibobo.littletoys;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/2/5.
 */

public class AnimationActivity extends AppCompatActivity {
    private Button alpha_an,rotate_an,translate_an;
    private Button btn_object_animation;
    private Button my_animation;
    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animationlayout);
        alpha_an= (Button) findViewById(R.id.alpha_an);
        rotate_an= (Button) findViewById(R.id.rotate_an);
        translate_an= (Button) findViewById(R.id.translate_an);
        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1);
        alphaAnimation.setDuration(1000);
        alpha_an.setAnimation(alphaAnimation);

        RotateAnimation rotateAnimation=new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(1000);
        rotate_an.setAnimation(rotateAnimation);

        TranslateAnimation translateAnimation=new TranslateAnimation(0,2,0,2);
        translateAnimation.setDuration(1000);
        translate_an.setAnimation(translateAnimation);
        translate_an.startAnimation(rotateAnimation);


        /**
         * 使用属性动画
         */
        btn_object_animation= (Button) findViewById(R.id.btn_object_animation);
        btn_object_animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AnimationActivity.this, "我是属性动画", Toast.LENGTH_SHORT).show();
            }
        });
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(btn_object_animation,"translationX",300);
        objectAnimator.setDuration(3000);
        objectAnimator.start();
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Toast.makeText(AnimationActivity.this, "动画开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Toast.makeText(AnimationActivity.this, "动画结束", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Toast.makeText(AnimationActivity.this, "动画取消", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Toast.makeText(AnimationActivity.this, "动画重复", Toast.LENGTH_SHORT).show();
            }
        });
        /**
         * 自定义动画
         */
        my_animation= (Button) findViewById(R.id.my_animation);
        MyAnimation myAnimation=new MyAnimation();
        my_animation.startAnimation(myAnimation);
        /**
         * 自定义线图动画
         */
        imageView= (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate();
            }
        });
    }
    private void animate(){
        Drawable myDrawable=imageView.getDrawable();
        if (myDrawable instanceof Animatable){
            ((Animatable)myDrawable).start();
        }
    }
}
