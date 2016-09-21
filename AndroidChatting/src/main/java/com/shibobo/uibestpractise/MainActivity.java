package com.shibobo.uibestpractise;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ListView msgListView;
    private EditText inputtext;
    private Button send;
    private MsgAdapter msgAdapter;
    private List<Msg> msgList=new ArrayList<Msg>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化消息
        initMsg();
        msgAdapter=new MsgAdapter(MainActivity.this,R.layout.msg_item,msgList);
        inputtext=(EditText) findViewById(R.id.inputtext);
        send=(Button) findViewById(R.id.send);
        send.setText("TA说:");
        msgListView=(ListView) findViewById(R.id.msg_list_view);
        msgListView.setAdapter(msgAdapter);
        send.setOnClickListener(this);
        msgListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
//                Toast.makeText(MainActivity.this,"位置为："+position,Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(false);
                builder.setTitle("删除");
                builder.setMessage("你要删除此消息吗?");
                //点击确认
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        msgList.remove(position);
                        msgAdapter.notifyDataSetChanged();//有新消息时候刷新listview
                        dialog.dismiss();
                    }
                });
                //点击取消
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //nothing to do
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        //发送消息
//        Toast.makeText(this,"type to send a message",Toast.LENGTH_SHORT).show();
        String content=inputtext.getText().toString();
//        Toast.makeText(this,"你发送的消息是:"+content,Toast.LENGTH_SHORT).show();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currenttime=simpleDateFormat.format(new java.util.Date());
        if(!"".equals(content)){
            if(send.getText().toString()=="TA说:"){
                send.setText("我说:");
                Msg msg=new Msg(content,Msg.TYPE_RECEIVED,currenttime);
                msgList.add(msg);
                msgAdapter.notifyDataSetChanged();//有新消息时候刷新listview
                msgListView.setSelection(msgList.size());
                inputtext.setText("");

            }else{
                send.setText("TA说:");
                Msg msg=new Msg(content,Msg.TYPE_SENT,currenttime);
                msgList.add(msg);
                msgAdapter.notifyDataSetChanged();//有新消息时候刷新listview
                msgListView.setSelection(msgList.size());
                inputtext.setText("");

            }
        }
    }

    private void initMsg(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currenttime=simpleDateFormat.format(new java.util.Date());
        Msg msg1=new Msg("hello,baby",Msg.TYPE_RECEIVED,currenttime);
        msgList.add(msg1);
        Msg msg2=new Msg("hi,what is up?",Msg.TYPE_SENT,currenttime);
        msgList.add(msg2);
        Msg msg3=new Msg("老样子，你怎么样",Msg.TYPE_RECEIVED,currenttime);
        msgList.add(msg3);
        Msg msg4=new Msg("我也是老样子，和之前一样。",Msg.TYPE_SENT,currenttime);
        msgList.add(msg4);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        MenuItem dark_item=menu.getItem(2);
        dark_item.setTitle("夜间模式");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:

                break;
            case R.id.clear:
                int size= msgList.size();
                for (int i=0;i<size;i++){
                    msgList.remove(0);
                }
                msgAdapter.notifyDataSetChanged();//有新消息时候刷新listview
                Toast.makeText(this,"聊天记录已清空",Toast.LENGTH_SHORT).show();
                break;
            case R.id.darkMode:
                MenuItem darkMode=item;
                LinearLayout chatbg=(LinearLayout) findViewById(R.id.chatbg);
                if(darkMode.getTitle()=="夜间模式"){
                    chatbg.setBackgroundResource(R.color.darkMode);
                    darkMode.setTitle("日间模式");
                }else {
                    chatbg.setBackgroundResource(R.color.lightMode);
                    darkMode.setTitle("夜间模式");
                }
                break;
            case R.id.finish:
                final AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(false);
                builder.setTitle("退出");
                builder.setMessage("你确定退出?");
//                builder.create().show();
                //yes
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
                        finish();
                    }
                });
                //no
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                break;
            default:
                break;
        }
        return true;
    }
}
