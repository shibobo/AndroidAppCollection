package com.shibobo.littletoys;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.support.v4.app.AppLaunchChecker;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;

/**
 * Created by Administrator on 2017/2/5.
 */

public class MyAnimation extends Animation {
    private int mCenterWidth,mCenterHeight;
    private Camera mCamera=new Camera();
    private int mRotateY;

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        setDuration(2000);
        setFillAfter(true);
        setInterpolator(new BounceInterpolator());
        mCenterHeight=width/2;
        mCenterHeight=height/2;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final Matrix matrix=t.getMatrix();
        mCamera.save();
        mCamera.rotateY(mRotateY*interpolatedTime);
        mCamera.getMatrix(matrix);
        mCamera.restore();
        matrix.preTranslate(mCenterWidth,mCenterHeight);
        matrix.postTranslate(-mCenterWidth,mCenterHeight);
    }
}
