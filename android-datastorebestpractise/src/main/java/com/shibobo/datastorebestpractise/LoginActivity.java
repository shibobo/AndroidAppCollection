package com.shibobo.datastorebestpractise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/9/25 0025.
 */
public class LoginActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


    private EditText username;
    private EditText userpwd;
    private CheckBox rempass;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        username=(EditText) findViewById(R.id.uname);
        userpwd=(EditText) findViewById(R.id.upwd);
        rempass=(CheckBox) findViewById(R.id.remember_pass);
        send=(Button) findViewById(R.id.login);
        boolean isRemember=pref.getBoolean("remember_pass",false);
        if(isRemember){
            String uname=pref.getString("username","");
            String upass=pref.getString("userpass","");
            Toast.makeText(this,"你的个人信息已经通过SharedPreferences保存",Toast.LENGTH_SHORT).show();
            username.setText(uname);
            userpwd.setText(upass);
            rempass.setChecked(true);
        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account=username.getText().toString();
                String password=userpwd.getText().toString();
                //check
                if (account.equals("admin") && password.equals("admin")){
                    editor=pref.edit();
                    if(rempass.isChecked()){
                        editor.putBoolean("remember_pass",true);
                        editor.putString("username",account);
                        editor.putString("userpass",password);
                    }else{
                        editor.clear();
                    }
                    editor.commit();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,"account or password is invalid",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
