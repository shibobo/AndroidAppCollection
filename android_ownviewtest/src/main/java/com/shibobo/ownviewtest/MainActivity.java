package com.shibobo.ownviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.shibobo.ownviewtest.wipetodelete.MyAdapter;
import com.shibobo.ownviewtest.wipetodelete.WipeToDelete;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    private TitleView titleView;

    private WipeToDelete wipe;
    private MyAdapter myAdapter;
    private List<String> contentLists=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wipelayout);
        initContentLists();
        wipe= (WipeToDelete) findViewById(R.id.wipe);
        myAdapter=new MyAdapter(this,0,contentLists);
        wipe.setAdapter(myAdapter);
        wipe.setOnDeleteListener(new WipeToDelete.OnDeleteListener() {
            @Override
            public void onDelete(int index) {
                Log.d("tips", "onDelete: 正在删除 "+index);
                contentLists.remove(index);
                myAdapter.notifyDataSetChanged();
            }
        });
//        titleView= (TitleView) findViewById(R.id.titleBar);
//        titleView.setLeftButtonListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                titleView.setTitleText("点击了返回按钮");
//                Toast.makeText(MainActivity.this, "点击了返回按钮", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });

    }
    //初始化内容
    public void initContentLists(){
        for (int i=0;i<20;i++){
            contentLists.add("内容项: "+i);
        }
    }
}
