package com.shibobo.coolweather.util;

/**
 * Created by Administrator on 2016/11/10 0010.
 */

public interface HttpCallBackListener {
    void onFinish(String response);
    void onError(Exception e);
}
