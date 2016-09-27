package com.shibobo.broadcastbestpractice;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/25 0025.
 */
public class ActivityCollector {
    //activity 泛型
    public static List<Activity> activityList=new ArrayList<Activity>();
    //添加
    public static void addActivity(Activity activity){
        activityList.add(activity);
    }
    //删除
    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }
    //全部删除
    public static void finishAll(){
        for (Activity activity:activityList){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
