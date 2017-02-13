package com.example.mypuzzle.Utils;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.mypuzzle.Bean.ImagePiece;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/10.
 * 将图片切成nxn的小图片
 */

public class ImageSplitter {
    /**
     * 传入bitmap，将图片切成piece x piece个小方块
     * @param bitmap
     * @param piece
     * @return List<ImagePiece>
     */
    public static List<ImagePiece> split(Bitmap bitmap,int piece){
        List<ImagePiece> pieces=new ArrayList<ImagePiece>(piece*piece);
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();
        Log.d("main","width="+width+", height="+height);
        int pieceWidth=Math.min(width,height)/piece;
        for (int i=0;i<piece;i++){
            for (int j=0;j<piece;j++){
                ImagePiece imagePiece=new ImagePiece();
                imagePiece.setIndex(j+i*piece);
                int xValue=j*pieceWidth;
                int yValue=i*pieceWidth;
                imagePiece.setBitmap(Bitmap.createBitmap(bitmap,xValue,yValue,pieceWidth,pieceWidth));
                pieces.add(imagePiece);
            }
        }
        return pieces;
    }
}
