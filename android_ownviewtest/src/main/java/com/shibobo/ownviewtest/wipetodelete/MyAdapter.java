package com.shibobo.ownviewtest.wipetodelete;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shibobo.ownviewtest.R;

import java.util.List;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class MyAdapter extends ArrayAdapter<String> {
    public MyAdapter(Context context, int textViewResourceId,List<String> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView==null){
            view= LayoutInflater.from(getContext()).inflate(R.layout.list_view_item,null);
        }else{
            view=convertView;
        }
        TextView t=(TextView) view.findViewById(R.id.content_tv);
        t.setText(getItem(position).toString());
        return view;
    }
}
