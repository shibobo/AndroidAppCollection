package com.shibobo.btsmartcar;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.shibobo.btsmartcar.bean.FestivalLab;
import com.shibobo.btsmartcar.bean.Msg;
import com.shibobo.btsmartcar.fragment.FestivalCategoryFragment;

public class ChooseMsgActivity extends AppCompatActivity {
    private ListView mListView;
    private FloatingActionButton mFabToSend;
    private ArrayAdapter<Msg> mAdapter;
    private int mFestivalId;
    private LayoutInflater mInflater;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_msg);
        mContext=ChooseMsgActivity.this;
        mFestivalId=getIntent().getIntExtra(FestivalCategoryFragment.ID_FESTIVAL,-1);
        mInflater=LayoutInflater.from(this);
        setTitle(FestivalLab.getInstance().getFestivalById(mFestivalId).getName());
        initViews();
        initEvent();
    }

    private void initEvent() {
        mFabToSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2017/1/25
                toActivity(mContext,mFestivalId,-1);
            }
        });
    }

    public void initViews(){
        mListView= (ListView) findViewById(R.id.id_lv_msgs);
        mFabToSend= (FloatingActionButton) findViewById(R.id.id_fab_toSend);
        mAdapter=new ArrayAdapter<Msg>(this,-1,FestivalLab.getInstance().getMsgsByFestivalId(mFestivalId)){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                if (convertView==null){
                    convertView=mInflater.inflate(R.layout.item_msg,parent,false);
                }
                final TextView content=(TextView) convertView.findViewById(R.id.id_tv_content);
                Button toSend= (Button) convertView.findViewById(R.id.id_btn_toSend);
                final int msgId=getItem(position).getId();
                content.setText("    "+getItem(position).getContent());
                toSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO: 2017/1/21
                        toActivity(mContext,mFestivalId,msgId);
                    }
                });
                return convertView;
            }
        };
        mListView.setAdapter(mAdapter);
    }
    public void toActivity(Context context,int festivalId, int msgId){
        Intent intent=new Intent(context,SendMsgActivity.class);
        intent.putExtra("FES_ID",festivalId);
        intent.putExtra("MSG_ID",msgId);
        context.startActivity(intent);
    }
}
