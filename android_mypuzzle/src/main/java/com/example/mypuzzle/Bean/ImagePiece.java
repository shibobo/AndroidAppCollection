package com.example.mypuzzle.Bean;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/2/10.
 */

public class ImagePiece {
    public int index=0;
    public Bitmap bitmap=null;
    public ImagePiece(){

    }
//    public ImagePiece(int index, Bitmap bitmap) {
//        this.index = index;
//        this.bitmap = bitmap;
//    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index){
        this.index=index;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    @Override
    public String toString() {
        return "ImagePiece{" +
                "index=" + index +
                ", bitmap=" + bitmap +
                '}';
    }
}
