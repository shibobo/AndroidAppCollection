package com.example.videoplayer;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Contacts;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.w3c.dom.Text;

import java.io.Closeable;
import java.util.TreeMap;
import java.util.concurrent.Delayed;

public class MainActivity extends AppCompatActivity {
    private CustomeVideoView videoView;
    private LinearLayout controllerBar_layout;
    private ImageView pause_img;
    private TextView time_current_tv,time_total_tv;
    private SeekBar seekbar_play,seekbar_volume;
    private ImageView fullscreen;
    private RelativeLayout videoLayout;
    private AudioManager mAudioManager;
    private ImageView volume_image;

    public static final int UPDATE_UI=1;
    private int screen_width,screen_height;
    private boolean isFullScreen=false;
    private boolean isAdjust=false;
    private int threshold=54;
    private float mBrightness;

    private ImageView icon_soundOrbright;
    private TextView number;
    private LinearLayout icon_tips;

    private float initX,initY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAudioManager=(AudioManager) getSystemService(AUDIO_SERVICE);
        initUI();
        setPlayerEvent();
        /**
         * 本地视频播放
         */
        String videoName="test.mp4";
        String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+videoName;
        videoView.setVideoPath(path);
        setTitle(videoName);
        videoView.start();
        UIHandler.sendEmptyMessage(UPDATE_UI);
        toastMsg("path:"+path);

        /**
         * 网络视频播放
         */
        //videoView.setVideoURI(Uri.parse(""));
        /**
         * MediaController类用来控制视频播放
         */
//        MediaController mediaController=new MediaController(this);
        /**
         * 设置VideoView和MediaController之间的联系
         */
//        videoView.setMediaController(mediaController);
//        mediaController.setMediaPlayer(videoView);

    }

    /**
     * 格式化时间
     */
    private void updateTextViewWithTimeFormat(TextView textview,int millisecond){
        int second=millisecond/1000;
        int hh=second/3600;
        int mm=second%3600/60;
        int ss=second%60;
        String str="";
        if (hh!=0){
            str=String.format("%02d:%02d:%02d",hh,mm,ss);
        }else{
            str=String.format("%02d:%02d",mm,ss);
        }
        textview.setText(str);
    }
    /**
     * 实时刷新UI界面
     */
    private Handler UIHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==UPDATE_UI){
                int currentPosition=videoView.getCurrentPosition();//当前播放时间
                int totalDuration=videoView.getDuration();//视频总时间
                updateTextViewWithTimeFormat(time_current_tv,currentPosition);
                updateTextViewWithTimeFormat(time_total_tv,totalDuration);
                seekbar_play.setMax(totalDuration);
                seekbar_play.setProgress(currentPosition);
                UIHandler.sendEmptyMessageDelayed(UPDATE_UI,500);
            }

        }
    };
    /**
     * 设置点击事件
     */
    private void setPlayerEvent() {
        pause_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videoView.isPlaying()){
                    pause_img.setImageResource(R.drawable.stop);
                    UIHandler.removeMessages(UPDATE_UI);
                    videoView.pause();
                }else{
                    pause_img.setImageResource(R.drawable.onrun);
                    UIHandler.sendEmptyMessage(UPDATE_UI);
                    videoView.start();
                }
            }
        });
        seekbar_play.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                UIHandler.removeMessages(UPDATE_UI);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress=seekbar_play.getProgress();
                videoView.seekTo(progress);
                UIHandler.sendEmptyMessage(UPDATE_UI);
            }
        });
        seekbar_volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //设置系统音量
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        /**
         * 横竖屏切换
         */
        fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFullScreen){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }else{
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        });
        /**
         * VideoView手势事件
         */
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float x=motionEvent.getX();
                float y=motionEvent.getY();
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        initX=x;//刚开始触碰时候的X坐标
                        initY=y;//刚开始触碰时候的Y坐标
                        //toastMsg("initX="+initX+",initY="+initY);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float deltaX=x-initX;
                        float deltaY=y-initY;
                        float absDeltaX=Math.abs(deltaX);//绝对值
                        float absDeltaY=Math.abs(deltaY);
//                        toastMsg("initX="+initX+",initY="+initY);
//                        toastMsg("w="+screen_width+",h="+screen_height);
//                        toastMsg("deltaX="+deltaX+",deltaY="+deltaY);
                        if (absDeltaX>threshold && absDeltaY>threshold){
                            if (absDeltaX<absDeltaY){
                                isAdjust=true;
                            }else{
                                isAdjust=false;
                            }
                        }else if(absDeltaX<threshold && absDeltaY>threshold){
                            isAdjust=true;
                        }else if(absDeltaX>threshold && absDeltaY<threshold){
                            isAdjust=false;
                        }
                        //toastMsg("手势是否合法："+isAdjust);
                        if (isAdjust){
                            icon_tips.setVisibility(View.VISIBLE);
                            if (x<screen_width/2){
                                if (deltaY>0){
                                    //toastMsg("降低亮度"+deltaY);
                                }else{
                                    //toastMsg("增加亮度"+deltaY);
                                }
                                changeBrightness(-deltaY);
                            }else{
                                if (deltaY>0){
                                    //toastMsg("降低音量"+deltaY);
                                }else{
                                    //toastMsg("增加音量"+deltaY);
                                }
                                changeVolume(-deltaY);
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        icon_tips.setVisibility(View.GONE);
                        break;
                }
                return true;
            }
        });
    }
    private void changeVolume(float delta){
        float max=mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float current=mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//        toastMsg("max="+max+",current="+current);
        float index=(float) (delta/screen_height*max);//0-15
        current+=index;
        if (current>15){
            current=15;
        }
        if (current<0){
            current=0;
        }
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,(int)current,0);
        icon_soundOrbright.setImageResource(R.drawable.sound);
        number.setText("音量:"+(current/max*100)+"%");
        seekbar_volume.setProgress((int)(current/max*100));
    }
    private void changeBrightness(float delta){
        WindowManager.LayoutParams attributes=getWindow().getAttributes();
        mBrightness=attributes.screenBrightness;
        float index=delta/screen_height/2;
        mBrightness+=index;
        if (mBrightness>1.0f){
            mBrightness=1.0f;
        }
        if (mBrightness<0.01f){
            mBrightness=0.01f;
        }
        attributes.screenBrightness=mBrightness;
        icon_soundOrbright.setImageResource(R.drawable.bright);
        number.setText("亮度:"+mBrightness/1.0f*100+"%");
        getWindow().setAttributes(attributes);
    }
    private void setVideoViewScale(int width,int height){
        ViewGroup.LayoutParams layoutParams=videoView.getLayoutParams();
        layoutParams.width=width;
        layoutParams.height=height;
        videoView.setLayoutParams(layoutParams);
        ViewGroup.LayoutParams layoutParams1=videoLayout.getLayoutParams();
        layoutParams1.width=width;
        layoutParams1.height=height;
        videoLayout.setLayoutParams(layoutParams1);
    }

    /**
     * 屏幕方向改变自动调用
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            volume_image.setVisibility(View.VISIBLE);
            seekbar_volume.setVisibility(View.VISIBLE);
            isFullScreen=true;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT,PiexlUtils.dip2px(this,240));
            volume_image.setVisibility(View.GONE);
            seekbar_volume.setVisibility(View.GONE);
            isFullScreen=false;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        videoView= (CustomeVideoView) findViewById(R.id.videoView);
        controllerBar_layout= (LinearLayout) findViewById(R.id.controllerBar_layout);
        pause_img= (ImageView) findViewById(R.id.pause_img);
        time_current_tv= (TextView) findViewById(R.id.time_current_tv);
        time_total_tv= (TextView) findViewById(R.id.time_total_tv);
        seekbar_play= (SeekBar) findViewById(R.id.seekbar_play);
        seekbar_volume= (SeekBar) findViewById(R.id.seekbar_volume);
        fullscreen= (ImageView) findViewById(R.id.fullscreen);
        screen_width=getResources().getDisplayMetrics().widthPixels;
        screen_height=getResources().getDisplayMetrics().heightPixels;
        videoLayout= (RelativeLayout) findViewById(R.id.videoLayout);
        //获取系统最大音量,当前音量
        int streamMaxVolume=mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int streamVolume=mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekbar_volume.setMax(streamMaxVolume);
        seekbar_volume.setProgress(streamVolume);
        volume_image= (ImageView) findViewById(R.id.volume_image);

        icon_soundOrbright= (ImageView) findViewById(R.id.icon_soundOrbright);
        number= (TextView) findViewById(R.id.number);
        icon_tips= (LinearLayout) findViewById(R.id.icon_tips);
    }

    @Override
    protected void onPause() {
        super.onPause();
        UIHandler.removeMessages(UPDATE_UI);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UIHandler.removeMessages(UPDATE_UI);
    }
    public void toastMsg(String msg){
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
    }
}
