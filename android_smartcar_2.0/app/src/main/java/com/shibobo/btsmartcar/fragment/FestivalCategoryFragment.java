package com.shibobo.btsmartcar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.shibobo.btsmartcar.ChooseMsgActivity;
import com.shibobo.btsmartcar.R;
import com.shibobo.btsmartcar.bean.Festival;
import com.shibobo.btsmartcar.bean.FestivalLab;

/**
 * Created by Administrator on 2017/1/21.
 */

public class FestivalCategoryFragment extends Fragment {
    public static final String ID_FESTIVAL="festival_id";
    private GridView mGridView;
    private ArrayAdapter<Festival> mAdapter;
    private LayoutInflater mInflater;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_festival_category,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mInflater=LayoutInflater.from(getActivity());
        mGridView=(GridView) view.findViewById(R.id.id_gv_festival_category);
        mGridView.setAdapter(mAdapter=new ArrayAdapter<Festival>(getActivity(),-1, FestivalLab.getInstance().getFestivals()){
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView==null){
                    convertView=mInflater.inflate(R.layout.item_festival,parent,false);
                }
                TextView tv=(TextView) convertView.findViewById(R.id.id_tv_festival_name);
                tv.setText(getItem(position).getName());
                return convertView;
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO: 2017/1/21
                Intent intent=new Intent(getActivity(), ChooseMsgActivity.class);
                intent.putExtra(ID_FESTIVAL,mAdapter.getItem(i).getId());
                startActivity(intent);
            }
        });
    }
}
