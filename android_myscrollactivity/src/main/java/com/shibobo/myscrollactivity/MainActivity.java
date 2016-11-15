package com.shibobo.myscrollactivity;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private FloatingActionButton mFloatingActionButton;
    private CollapsingToolbarLayout collapse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolBar= (Toolbar) findViewById(R.id.mToolBar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFloatingActionButton= (FloatingActionButton) findViewById(R.id.mFloatingActionButton);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你好", Toast.LENGTH_SHORT).show();
                Snackbar.make(v,"你好",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
            }
        });
        collapse= (CollapsingToolbarLayout) findViewById(R.id.collapse);
        collapse.setTitle("ScrollActivity");
        collapse.setExpandedTitleColor(Color.WHITE);
        collapse.setCollapsedTitleTextColor(Color.WHITE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                break;
            case R.id.about:
                break;
        }
        return true;
    }
}
