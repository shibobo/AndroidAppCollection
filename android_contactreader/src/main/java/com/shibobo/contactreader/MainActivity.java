package com.shibobo.contactreader;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{

    private TextView show;
    private ListView contactView;
    private ArrayAdapter<String> adapter;
    private List<String> contactsList=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactView=(ListView) findViewById(R.id.contact_view);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contactsList);
        contactView.setAdapter(adapter);
        readContacts();
        contactView.setOnItemClickListener(this);
        contactView.setOnItemLongClickListener(this);
        show=(TextView) findViewById(R.id.show);
        show.append("，一共读取到"+adapter.getCount()+"个联系人");
        //Toast.makeText(this,"一共有个"+adapter.getCount()+"联系人",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        Toast.makeText(this,contactsList.get(position),Toast.LENGTH_SHORT).show();
//        String dataget=contactsList.get(position);
        //String numberneed=dataget.substring(dataget.length()-14,dataget.length());
        //Toast.makeText(this,"你将要拨打的号码是:"+numberneed,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,contactsList.get(position),Toast.LENGTH_SHORT).show();
        return true;
    }

    public void readContacts(){
        Cursor cursor=null;
        try {
            //查询联系人数据
            cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    null,
                    null,
                    null);
            while (cursor.moveToNext()){
                //获取联系人姓名
                String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                //获取联系人号码
                String number=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactsList.add("姓名："+name+"\n"+
                                 "号码："+number);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }

    }
}
