package com.shibobo.datastorebestpractise;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show=(TextView) findViewById(R.id.show);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        String name=pref.getString("username","");
        show.setText("欢迎你，"+name);
    }
}
