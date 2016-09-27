package com.shibobo.broadcastbestpractice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/9/25 0025.
 */
public class LoginActivity extends BaseActivity {

    private EditText username;
    private EditText userpwd;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username=(EditText) findViewById(R.id.uname);
        userpwd=(EditText) findViewById(R.id.upwd);
        send=(Button) findViewById(R.id.login);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account=username.getText().toString();
                String password=userpwd.getText().toString();
                //check
                if (account.equals("admin") && password.equals("admin")){
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
