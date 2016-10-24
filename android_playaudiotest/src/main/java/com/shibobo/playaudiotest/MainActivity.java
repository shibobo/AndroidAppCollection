package com.shibobo.playaudiotest;

import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button start;
    private Button pause;
    private Button stop;
    private Button playvideo;
    private Button pausevideo;
    private Button replayvideo;

    private VideoView video_show;
    private MediaPlayer mediaPlayer=new MediaPlayer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        start=(Button) findViewById(R.id.start);
        pause=(Button) findViewById(R.id.pause);
        stop=(Button) findViewById(R.id.stop);

        playvideo=(Button) findViewById(R.id.playvideo);
        pausevideo=(Button) findViewById(R.id.pausevideo);
        replayvideo=(Button) findViewById(R.id.replayvideo);

        start.setOnClickListener(new MyOnClickListener());
        pause.setOnClickListener(new MyOnClickListener());
        stop.setOnClickListener(new MyOnClickListener());

        video_show=(VideoView) findViewById(R.id.video_show);

        playvideo.setOnClickListener(new MyOnClickListener());
        pausevideo.setOnClickListener(new MyOnClickListener());
        replayvideo.setOnClickListener(new MyOnClickListener());
        initMediaPlayer();
        initVideoPath();
    }
    public void initMediaPlayer(){
        try {
            File file=new File(Environment.getExternalStorageDirectory(),"music.mp3");
            Log.d("音乐路径:",file.getPath().toString());
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void initVideoPath(){
        File file=new File(Environment.getExternalStorageDirectory(),"show.mp4");
        Log.d("视频路径:",file.getPath().toString());
        video_show.setVideoPath(file.getPath());
    }
    class MyOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                //audio
                case R.id.start:
                    if (!mediaPlayer.isPlaying()){
                        mediaPlayer.start();
                    }
                    break;
                case R.id.pause:
                    if (mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                    }
                    break;
                case R.id.stop:
                    if (mediaPlayer.isPlaying()){
                        mediaPlayer.reset();
                        initMediaPlayer();
                    }
                    break;
                //video
                case R.id.playvideo:
                    if (!video_show.isPlaying()){
                        video_show.start();
                    }
                    break;
                case R.id.pausevideo:
                    if (video_show.isPlaying()){
                        video_show.pause();
                    }
                    break;
                case R.id.replayvideo:
                    if (video_show.isPlaying()){
                        video_show.resume();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //audio
        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        //video
        if (video_show!=null){
            video_show.suspend();
        }

    }
}
