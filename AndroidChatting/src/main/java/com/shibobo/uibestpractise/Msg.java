package com.shibobo.uibestpractise;

/**
 * Created by Administrator on 2016/9/4 0004.
 */
public class Msg {
    public static final int TYPE_RECEIVED=0;
    public static final int TYPE_SENT=1;
    private String content;
    private int type;
    private String timeofmsg;
    public Msg(String content,int type,String timeofmsg){
        this.type=type;
        this.content=content;
        this.timeofmsg=timeofmsg;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public String getTimeofmsg(){
        return timeofmsg;
    }
}
