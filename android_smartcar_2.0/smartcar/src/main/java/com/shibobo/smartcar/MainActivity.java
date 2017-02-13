package com.shibobo.smartcar;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button SearchDevices;
    private TextView ShowConnStatus;

    private Button GoForward;
    private Button GoBack;
    private Button TurnLeft;
    private Button TurnRight;
    private Button StopCar;
//    private Button AutoRun;

    private RadioButton slow_speed;
    private RadioButton high_speed;


    private ArrayList<BluetoothDevice> devicelist;
    private Set<BluetoothDevice> paireddevices;
    private BluetoothAdapter adapter;

    private Context mContext;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private ArrayAdapter arrayAdapter;

    private int currentMode=0;
    private static final int Car_Mode_1=0;
    private static final int Car_Mode_2=1;
    private static final int Car_Mode_None=2;
    private static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String TAG = "BluetoothTest";

    private BluetoothSocket bluetoothSocket=null;
    private InputStream inStream;
    private OutputStream outStream;

    //menu
    private Menu menu;



    /**
     * 创建
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        //监听点击事件
        SearchDevices.setOnClickListener(this);
//        AutoRun.setOnClickListener(this);

        GoForward.setOnClickListener(this);
        GoBack.setOnClickListener(this);
        TurnLeft.setOnClickListener(this);
        TurnRight.setOnClickListener(this);
        StopCar.setOnClickListener(this);
        //监听触摸事件
        //GoForward.setOnTouchListener(this);
        //GoBack.setOnTouchListener(this);
        //TurnLeft.setOnTouchListener(this);
        //TurnRight.setOnTouchListener(this);
        setCarMode(Car_Mode_None);
        slow_speed.setOnClickListener(this);
        high_speed.setOnClickListener(this);
    }
    /**
     * 初始化界面中的各个控件
     */
    public void init(){
        SearchDevices=(Button) findViewById(R.id.SearchDevices);
        ShowConnStatus=(TextView) findViewById(R.id.ShowConnStatus);
//        SpeedSet=(TextView) findViewById(R.id.SpeedSet);
//
//        Speed=(SeekBar) findViewById(R.id.Speed);

        GoForward=(Button) findViewById(R.id.GoForward);
        TurnLeft=(Button) findViewById(R.id.TurnLeft);
        TurnRight=(Button) findViewById(R.id.TurnRight);
//        AutoRun=(Button) findViewById(R.id.AutoRun);
        GoBack=(Button) findViewById(R.id.GoBack);
        StopCar=(Button) findViewById(R.id.StopCar);

        slow_speed=(RadioButton) findViewById(R.id.slow_speed);
        high_speed=(RadioButton) findViewById(R.id.high_speed);

        mContext=MainActivity.this;
    }
    /**
     * 利用Toast弹出提示信息
     * @param msg
     */
    public void Toast_Msg(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }
    /**
     * 监听各个按钮的点击事件并作出响应
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //搜索蓝牙设备并进行连接
            case R.id.SearchDevices:
                //Toast.makeText(this,"正在查找蓝牙设备",Toast.LENGTH_SHORT).show();
                //开始查找蓝牙设备
                //得到BluetoothAdapter对象
                adapter=BluetoothAdapter.getDefaultAdapter();
                //判断本机是否具有蓝牙适配器
                if (adapter!=null){
                    //Toast_Msg("本机支持蓝牙功能");
                    //检测蓝牙是否已经打开
                    if (!adapter.isEnabled()){
                        //创建intent用于启动蓝牙适配器
                        Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivity(intent);
                    }
                    //建立蓝牙连接
                    //和选中的设备建立连接
                    setBlueToothConn();
                }else{
                    Toast_Msg("该设备不支持蓝牙");
                    finish();
                }
                break;
            case R.id.slow_speed:
                slow_speed.setChecked(true);
                high_speed.setChecked(false);
                break;
            case R.id.high_speed:
                slow_speed.setChecked(false);
                high_speed.setChecked(true);
                break;
            //小车停止，发送E字符
//            case R.id.AutoRun:
                //自动循迹
//                StopSmartCar();
//                Toast.makeText(mContext, "自动循迹模式", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.GoForward:
                if (slow_speed.isChecked()){
                    SendDataToBTModule("a");
                    Toast_Msg("低速");
                }else{
                    SendDataToBTModule("A");
                    Toast_Msg("高速");
                }
                break;
            case R.id.GoBack:
                if (slow_speed.isChecked()){
                    SendDataToBTModule("d");
                }else{
                    SendDataToBTModule("D");
                }
                break;
            case R.id.TurnLeft:
                if (slow_speed.isChecked()){
                    SendDataToBTModule("c");
                }else{
                    SendDataToBTModule("C");
                }
                break;
            case R.id.TurnRight:
                if (slow_speed.isChecked()){
                    SendDataToBTModule("b");
                }else{
                    SendDataToBTModule("B");
                }
                break;
            case R.id.StopCar:
                SendDataToBTModule("S");
                break;
            default:
                break;

        }
    }
    /**
     * 监听按键的按下与放开事件
     * @param v
     * @param event
     * @return
     */
    /*@Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            //小车前进，发送A字符
            case R.id.GoForward:
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        SendDataToBTModule("A");
                        break;
                    case MotionEvent.ACTION_UP:
                        StopSmartCar();
                        break;
                }
                break;
            //小车后退，发送D字符
            case R.id.GoBack:
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        SendDataToBTModule("D");
                        break;
                    case MotionEvent.ACTION_UP:
                        StopSmartCar();
                        break;
                }
                break;
            //小车左转弯，发送C字符
            case R.id.TurnLeft:
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        SendDataToBTModule("C");
                        break;
                    case MotionEvent.ACTION_UP:
                        StopSmartCar();
                        break;
                }
                break;
            //小车右转弯，发送B字符
            case R.id.TurnRight:
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        SendDataToBTModule("B");
                        break;
                    case MotionEvent.ACTION_UP:
                        StopSmartCar();
                        break;
                }
                break;
        }
        return false;
    }*/
    /**
     *设置蓝牙socket连接
     */
    public void setBlueToothConn(){
        //得到已经配对成功的蓝牙适配器对象
        if (adapter.isEnabled()){
            paireddevices=adapter.getBondedDevices();
            devicelist=new ArrayList();
            for (BluetoothDevice bt:paireddevices){
                devicelist.add(bt);
            }
            arrayAdapter=new ArrayAdapter(mContext,android.R.layout.simple_list_item_1,devicelist);
            //以对话框的形式显示
            LayoutInflater layoutInflater=LayoutInflater.from(mContext);
            View layout=layoutInflater.inflate(R.layout.mybtview,null);
            final ListView btdeviceslist=(ListView) layout.findViewById(R.id.btdeviceslist);
            btdeviceslist.setAdapter(arrayAdapter);

            builder=new AlertDialog.Builder(mContext);
            builder.setView(layout);
            alertDialog=builder.create();
            alertDialog.show();
            btdeviceslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //选中的设备
                    BluetoothDevice btDeviceChosed=devicelist.get(position);
                    String name=btDeviceChosed.getName();
                    String address=btDeviceChosed.getAddress();
                    //Toast_Msg("name:"+name);
                    //利用socket和选中的设备建立连接
                    try {
                        bluetoothSocket = btDeviceChosed.createRfcommSocketToServiceRecord(myUUID);
                        Log.d(TAG,"和"+name+"的socket已经成功打通");
                    }catch (IOException e){
                        Log.d(TAG,"错误:",e);
                    }
                    adapter.cancelDiscovery();
                    try{
                        if (!bluetoothSocket.isConnected()){
                            bluetoothSocket.connect();
                        }
                        //SearchDevices.setEnabled(false);
                        Log.d(TAG,"连接设备成功");
                        alertDialog.dismiss();
                        //Toast_Msg("已经成功连接上设备:"+name);
                        ShowConnStatus.setText("已经连接上设备:"+name);
                        setCarMode(Car_Mode_1);
                    }catch (IOException e){
                        Log.d(TAG,"连接设备失败");
                        alertDialog.dismiss();
                        //Toast_Msg("连接设备失败,请重试");
                        ShowConnStatus.setText("连接设备失败,请重试");
                        try{
                            bluetoothSocket.close();
                        }catch (IOException e1){
                            Log.d(TAG,"close socket failed",e1);
                        }
                    }
                }
            });
        }
    }
    /**
     * 设置小车工作模式:0代表手动模式，1代表全自动模式
     */
    public void setCarMode(int mode){
        if (mode==0){
            GoForward.setEnabled(true);
            GoBack.setEnabled(true);
            TurnLeft.setEnabled(true);
            TurnRight.setEnabled(true);
            StopCar.setEnabled(true);
//            AutoRun.setEnabled(false);
//            Speed.setEnabled(true);
            MenuItem menuItem=menu.findItem(R.id.menu_mode);
            menuItem.setTitle("切换到自动模式");
            currentMode=0;
        }
        if(mode==1){
            GoForward.setEnabled(false);
            GoBack.setEnabled(false);
            TurnLeft.setEnabled(false);
            TurnRight.setEnabled(false);
//            AutoRun.setEnabled(true);
            StopCar.setEnabled(false);
            MenuItem menuItem=menu.findItem(R.id.menu_mode);
            menuItem.setTitle("切换到手动模式");
            currentMode=1;
        }
        if(mode==2){
            GoForward.setEnabled(false);
            GoBack.setEnabled(false);
            TurnLeft.setEnabled(false);
            TurnRight.setEnabled(false);
//            AutoRun.setEnabled(false);
            StopCar.setEnabled(false);
            currentMode=2;
        }
    }
    /**
     * 发送数据到蓝牙模块
     * @param data
     * @return
     */
    public boolean SendDataToBTModule(String data){
        if(bluetoothSocket.isConnected()){
            String message=data;
            byte[] msgBuffer=message.getBytes();
            try {
                outStream=bluetoothSocket.getOutputStream();
                outStream.write(msgBuffer);
            }catch (IOException e){
                Log.d(TAG,"outputstream create failed",e);
            }
        }
        return true;
    }
    /**
     * 软件菜单配置
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu=menu;
        getMenuInflater().inflate(R.menu.nav,menu);
        return true;
    }
    /**
     * 软件菜单点击事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.menu_mode:
                switch (currentMode){
                    case 0:
//                        setCarMode(Car_Mode_2);
                        //item.setTitle("切换到自动模式");
//                        Toast_Msg("切换到自动模式");
                        break;
                    case 1:
//                        setCarMode(Car_Mode_1);
                        //item.setTitle("切换到手动模式");
//                        Toast_Msg("切换到手动模式");
                        break;
                    case 2:
                        //nothing to do
                        break;
                }
                break;
            case R.id.menu_btclose:
                //关闭蓝牙
                if (adapter!=null){
                    if (adapter.isEnabled()){
                        adapter.disable();
                        Toast_Msg("蓝牙已经关闭");
                        ShowConnStatus.setText("蓝牙已经关闭");
                    }else{
                        Toast_Msg("蓝牙已经关闭");
                        ShowConnStatus.setText("蓝牙已经关闭");
                    }
                    setCarMode(Car_Mode_None);
                }
                break;
            case R.id.menu_about:
                //关于软件
                aboutTheApp();
                break;
            case R.id.menu_exit:
                //结束程序
                my_finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * 关于软件
     */
    public void aboutTheApp(){
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        View layout=layoutInflater.inflate(R.layout.about,null);
        AlertDialog.Builder mbuilder=new AlertDialog.Builder(mContext);
        mbuilder.setView(layout);
        mbuilder.setCancelable(false);
        final AlertDialog dialog=mbuilder.create();
        Button btn_know=(Button) layout.findViewById(R.id.know);
        btn_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    /**
     * 退出程序
     */
    public void my_finish(){
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        builder.setCancelable(true);
        builder.setTitle("退出");
        builder.setMessage("确定要退出本程序吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show().show();
    }

    @Override
    protected void onDestroy() {
        if (bluetoothSocket!=null){
            try{
                bluetoothSocket.close();
            }catch (IOException e){
                Log.d(TAG,"close socket failed",e);
            }
        }
        super.onDestroy();
    }
}
