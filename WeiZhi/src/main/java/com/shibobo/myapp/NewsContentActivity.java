package com.shibobo.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class NewsContentActivity extends AppCompatActivity {
    // TODO: 2016/9/22 0022
    private TextView mTitle;
    private TextView mContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.news_content);
        String nTitle=getIntent().getStringExtra("newsTitle");
        String nContent=getIntent().getStringExtra("newsContent");
        mTitle=(TextView) findViewById(R.id.ntitle);
        mContent=(TextView) findViewById(R.id.ncontent);
        mTitle.setText(nTitle);
        mContent.setText(nContent);
    }

}
