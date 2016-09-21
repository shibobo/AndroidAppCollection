package com.shibobo.uibestpractise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2016/9/4 0004.
 */
public class MsgAdapter extends ArrayAdapter<Msg> {
    private int resourceId;
    public MsgAdapter(Context context, int textViewResourceId, List<Msg> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Msg msg=getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder=new ViewHolder();
            viewHolder.leftlayout=(LinearLayout) view.findViewById(R.id.left_layout);
            viewHolder.rightlayout=(LinearLayout) view.findViewById(R.id.right_layout);
            viewHolder.leftMsg=(TextView) view.findViewById(R.id.left_msg);
            viewHolder.rightMsg=(TextView) view.findViewById(R.id.right_msg);
            viewHolder.lefttime=(TextView) view.findViewById(R.id.left_time);
            viewHolder.righttime=(TextView) view.findViewById(R.id.right_time);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        if(msg.getType()==Msg.TYPE_RECEIVED){
            viewHolder.leftlayout.setVisibility(View.VISIBLE);
            viewHolder.rightlayout.setVisibility(View.GONE);
            viewHolder.leftMsg.setText(msg.getContent());
            viewHolder.lefttime.setText(msg.getTimeofmsg());
        }else if(msg.getType()==Msg.TYPE_SENT){
            viewHolder.rightlayout.setVisibility(View.VISIBLE);
            viewHolder.leftlayout.setVisibility(View.GONE);
            viewHolder.rightMsg.setText(msg.getContent());
            viewHolder.righttime.setText(msg.getTimeofmsg());
        }
        return view;
    }
    public class ViewHolder{
        LinearLayout leftlayout;
        LinearLayout rightlayout;
        TextView leftMsg;
        TextView lefttime;
        TextView rightMsg;
        TextView righttime;
    }
}
