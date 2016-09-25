package com.shibobo.myapp;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class News {
    private String title;
    private String content;
    public News(String title,String content){
        this.title=title;
        this.content=content;
    }
//    public void setTitle(String title){
//        this.title=title;
//    }
    public String getTitle(){
        return title;
    }
//    public void setContent(String content){
//        this.content=content;
//    }
    public String getContent(){
        return content;
    }
}
