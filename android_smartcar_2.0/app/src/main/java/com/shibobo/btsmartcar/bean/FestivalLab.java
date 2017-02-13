package com.shibobo.btsmartcar.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/21.
 */

public class FestivalLab {
    public static FestivalLab mInstance;
    private List<Festival> mFestivals=new ArrayList<Festival>();
    private List<Msg> mMsgs=new ArrayList<Msg>();
    private FestivalLab(){
        mFestivals.add(new Festival(1,"春节"));
        mFestivals.add(new Festival(2,"元宵节"));
        mFestivals.add(new Festival(3,"劳动节"));
        mFestivals.add(new Festival(4,"儿童节"));
        mFestivals.add(new Festival(5,"中秋节"));
        mFestivals.add(new Festival(6,"父亲节"));

        mMsgs.add(new Msg(1,1,"鸡年到，喜事到，大喜，小喜都是喜，鸡年到，快乐到，左乐。右乐真是乐。鸡年到，锣鼓敲，鞭炮响。鸡子鸡女一起来报道！"));
        mMsgs.add(new Msg(2,2,"元宵节 月亮，元宵，映衬着你的欢笑，正月十五回荡着你的歌调，新春充盈着你的热闹，此时我心久恋着你的美妙。"));
        mMsgs.add(new Msg(3,3,"劳动节快乐吗?劳动是一种幸福，今天你是不是幸福的要晕?那就趁着晕劲睡觉吧！晚安！"));
        mMsgs.add(new Msg(4,4,"悟空画了一个圈，唐僧安全了；小平画了个圈，深圳富裕了；你画了一个圈，你又尿床了。小朋友，节日快乐！"));
        mMsgs.add(new Msg(5,5,"中秋来临，请允许我寄圆月祝您工作“圆满完成”；托明夜赠您事业“前途光明”；邀中秋喜庆保我们合作娱快！中秋快乐！"));
        mMsgs.add(new Msg(6,6,"爸爸，父亲节给您端杯茶，消暑解渴笑哈哈;父亲节给您擦擦汗，擦去劳累和疲倦;父亲节给您洗洗脚，洗去忧愁和烦恼;父亲节给您捶捶背，健康活百岁！"));
        mMsgs.add(new Msg(7,1,"新年第一个周末，迎新的福气依然满满滴，听说在这天送一条载满友情的短信给最好的朋友，友情将延续一辈子。同时愿你：幸福快乐！富足平安！平安吉祥！"));
    }
    public List<Festival> getFestivals(){
        return new ArrayList<Festival>(mFestivals);
    }
    public Festival getFestivalById(int fesId){
        for (Festival festival:mFestivals){
            if (festival.getId()==fesId){
                return festival;
            }
        }
        return null;
    }
    public List<Msg> getMsgsByFestivalId(int fesId){
        List<Msg> msgs=new ArrayList<>();
        for (Msg msg:mMsgs){
            if (msg.getFestivalId()==fesId){
                msgs.add(msg);
            }
        }
        return msgs;
    }
    public Msg getMsgByMsgId(int id){
        for (Msg msg:mMsgs){
            if (msg.getId()==id){
                return msg;
            }
        }
        return null;
    }
    public static FestivalLab getInstance(){
        if (mInstance==null){
            synchronized (FestivalLab.class){
                if (mInstance==null){
                    mInstance=new FestivalLab();
                }
            }
        }
        return mInstance;
    }
}
