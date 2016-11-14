package com.shibobo.myviewtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.shibobo.myviewtest.mylistview.MyListView;
import com.shibobo.myviewtest.mylistview.MyListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class MyListViewActivity extends AppCompatActivity {
    private MyListView myListView;
    private MyListViewAdapter myListViewAdapter;
    private List<String> contentList=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylistviewlayout);
        initData();
        myListView=(MyListView) findViewById(R.id.mylistview);
        myListView.setOnDeleteListener(new MyListView.OnDeleteListener() {
            @Override
            public void onDelete(int index) {
                contentList.remove(index);
                myListViewAdapter.notifyDataSetChanged();
            }
        });
        myListViewAdapter=new MyListViewAdapter(this,0,contentList);
        myListView.setAdapter(myListViewAdapter);
    }
    public void initData(){
        for (int i=1;i<=10;i++){
            contentList.add("这是第"+i+"条item");
        }
    }
}
