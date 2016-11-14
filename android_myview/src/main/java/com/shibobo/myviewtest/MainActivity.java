package com.shibobo.myviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.shibobo.myviewtest.mylistview.MyListView;
import com.shibobo.myviewtest.mylistview.MyListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MyListView myListView;
    private MyListViewAdapter myListViewAdapter;
    private List<String> contentList=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circleview);
        //initData();
        //myListView=(MyListView) findViewById(R.id.my_list_view);
        //myListViewAdapter=new MyListViewAdapter(this,0,contentList);
        //myListView.setAdapter(myListViewAdapter);
    }
//    public void initData(){
//        for (int i=1;i<=10;i++){
//            contentList.add("这是第"+i+"条item");
//        }
//    }
}
