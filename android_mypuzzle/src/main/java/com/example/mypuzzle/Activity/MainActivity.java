package com.example.mypuzzle.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypuzzle.MyView.MyPuzzleGameLayout;
import com.example.mypuzzle.R;

public class MainActivity extends AppCompatActivity {
    private MyPuzzleGameLayout mypuzzle_main;
    private Context mContext=MainActivity.this;
    private TextView tv_level,tv_time;
    private Button btn_originImg,btn_stop;
    private boolean isPause=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        mypuzzle_main= (MyPuzzleGameLayout) findViewById(R.id.mypuzzle_main);
        mypuzzle_main.setTimerEnabled(true);
        mypuzzle_main.setMyPuzzleGameListener(new MyPuzzleGameLayout.MyPuzzleGameListener() {
            @Override
            public void nextLevel(final int nextLevelNum) {
                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                builder.setCancelable(false);
                builder.setTitle("Game Info");
                builder.setMessage("恭喜你进入下一关");
                builder.setPositiveButton("立即进入下一关", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                        mypuzzle_main.nextLevel();
                        tv_level.setText("第"+nextLevelNum+"关");
                    }
                });
                builder.show();

            }

            @Override
            public void timeChanged(int currentTime) {
                tv_time.setText("剩余时间:"+currentTime+"s");
            }

            @Override
            public void gameOver() {
                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                builder.setCancelable(false);
                builder.setTitle("Game Info");
                builder.setMessage("很遗憾，游戏失败！");
                builder.setPositiveButton("重试", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                        mypuzzle_main.restartGame();
                    }
                });
                builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.show();
            }
        });
        btn_originImg= (Button) findViewById(R.id.btn_originalImg);
        btn_originImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View layout= LayoutInflater.from(mContext).inflate(R.layout.origin_img,null);
                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                builder.setCancelable(true);
                builder.setView(layout);
                ImageView originImg= (ImageView) layout.findViewById(R.id.originImg);
                final AlertDialog dialog=builder.create();
                originImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        tv_level= (TextView) findViewById(R.id.tv_level);
        tv_time= (TextView) findViewById(R.id.tv_time);
        btn_stop= (Button) findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mypuzzle_main.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mypuzzle_main.resume();
    }
}
