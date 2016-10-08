package com.shibobo.datastoretest;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText edit;
    private TextView msg;

    private Button save_data;
    private Button getSave_data;
    private TextView getsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit=(EditText) findViewById(R.id.edit);
        msg=(TextView) findViewById(R.id.msg);
        String msgget=load();
        if (!TextUtils.isEmpty(msgget)){
            msg.setText(msgget);
            Toast.makeText(this,"success",Toast.LENGTH_SHORT).show();
        }
        //
        save_data=(Button) findViewById(R.id.savesharedpreferences);
        save_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
                editor.putString("name","shibobo");
                editor.putInt("age",23);
                editor.putBoolean("boy",true);
                editor.commit();
            }
        });
        //
        getsp=(TextView) findViewById(R.id.getsp);
        getSave_data=(Button) findViewById(R.id.getsharedpreferences);
        getSave_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
                String name=pref.getString("name","");
                int age=pref.getInt("age",0);
                Boolean boy=pref.getBoolean("boy",false);
                Log.d("name","name is "+name);
                Log.d("age","age is "+age);
                Log.d("boy","gender is boy "+boy);
                getsp.setText("Name is: "+name+"\nAge is: "+age+"\nGender is boy? "+boy);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText=edit.getText().toString();
//        save
        save(inputText);
    }
    public void save(String inputText){
        FileOutputStream out=null;
        BufferedWriter writer=null;
        try {
            out=openFileOutput("data", Context.MODE_PRIVATE);
            writer=new BufferedWriter(new OutputStreamWriter(out));
//            writer.write(inputText);
            writer.append(inputText);

        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(writer!=null){
                    writer.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }

        }
    }
    public String load(){
        FileInputStream in=null;
        BufferedReader reader=null;
        StringBuilder content=new StringBuilder();
        try {
            in=openFileInput("data");
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while((line=reader.readLine())!=null){
                content.append(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(reader!=null){
                    reader.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }

        }
        return content.toString();
    }
}
