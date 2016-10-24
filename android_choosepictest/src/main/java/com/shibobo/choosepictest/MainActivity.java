package com.shibobo.choosepictest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    //标志
    private static final int CUT_PICTURE=1;
    private static final int SHOW_PICTURE=2;

    private Button take_photo;
    private Button choose_from_album;
    private ImageView picture;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init
        take_photo=(Button) findViewById(R.id.take_pic);
        choose_from_album=(Button) findViewById(R.id.choose_from_album);
        picture=(ImageView) findViewById(R.id.picture);
        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建FIle对象，用于存储拍摄后的照片
                File outputImage=new File(Environment.getExternalStorageDirectory(),
                                          "tpm.jpg");
                try {
                    if (outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                imageUri=Uri.fromFile(outputImage);
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //存储真实大小的图片，大概几兆大小
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,CUT_PICTURE);//启动相机程序
            }
        });
        choose_from_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建file对象
                File outputImage=new File(Environment.getExternalStorageDirectory(),
                                          "output_image.jpg");
                try {
                    if (outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                imageUri=Uri.fromFile(outputImage);
                Toast.makeText(MainActivity.this,imageUri.toString(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent("android.intent.action.PICK");
//                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"images/*");
//                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");//调用系统图库
                //intent.putExtra("crop",true);
                //intent.putExtra("scale",true);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,CUT_PICTURE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CUT_PICTURE:
                if (resultCode==RESULT_OK){
//                    Toast.makeText(this,data.toString(),Toast.LENGTH_SHORT).show();
//                    imageUri=data.getData();
//                    imageUri=data.getData();
//                    String text=data.getData().toString();
//                    Toast.makeText(MainActivity.this,text,Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri,"image/*");
                    intent.putExtra("scale",true);
                    //存储裁剪后的图片，大概几百K
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    Toast.makeText(this,imageUri.toString(),Toast.LENGTH_SHORT).show();
                    startActivityForResult(intent,SHOW_PICTURE);//启动裁剪程序
                }
                break;
            case SHOW_PICTURE:
                if (resultCode==RESULT_OK){
                    try {
                        Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver()
                                       .openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                        Toast.makeText(this,"图片设置成功",Toast.LENGTH_SHORT).show();
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
}
