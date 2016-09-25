package com.shibobo.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class NewsAdapter extends ArrayAdapter<News> {

    private int resourceId;

    public NewsAdapter(Context context,int textViewResourceId,List<News> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news=getItem(position);
        View view;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        }else{
            view=convertView;
        }
        TextView newsTitleText=(TextView) view.findViewById(R.id.news_title);
        TextView newsContentText=(TextView) view.findViewById(R.id.news_content);
        newsTitleText.setText(news.getTitle());
        newsContentText.setText(news.getContent());
        return view;
    }
}
