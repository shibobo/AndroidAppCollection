package com.shibobo.coolweather.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shibobo.coolweather.R;
import com.shibobo.coolweather.util.HttpCallBackListener;
import com.shibobo.coolweather.util.HttpUtil;
import com.shibobo.coolweather.util.Utility;

import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016/11/10 0010.
 */

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout weatherInfoLayout;
    private TextView cityNameText;
    private TextView publishText;
    private TextView weatherDespText;
    private TextView temp1Text;
    private TextView temp2Text;
    private TextView currenDateText;
    private Button switchCity;
    private Button refreshWeather;

    /**
     * onCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        //初始化各个控件
        weatherInfoLayout=(LinearLayout) findViewById(R.id.weather_info_layout);
        cityNameText=(TextView) findViewById(R.id.city_name);
        publishText=(TextView) findViewById(R.id.publish_text);
        weatherDespText=(TextView) findViewById(R.id.current_desp);
        temp1Text=(TextView) findViewById(R.id.temp1);
        temp2Text=(TextView) findViewById(R.id.temp2);
        currenDateText=(TextView) findViewById(R.id.current_date);
        switchCity=(Button) findViewById(R.id.switch_city);
        refreshWeather=(Button) findViewById(R.id.refresh_weather);

        String countycode=getIntent().getStringExtra("county_code");
        //Toast.makeText(WeatherActivity.this,countycode,Toast.LENGTH_SHORT).show();
        if (!TextUtils.isEmpty(countycode)){
            //有县级代号就加载天气信息
            publishText.setText("同步中...");
            weatherInfoLayout.setVisibility(View.INVISIBLE);
            cityNameText.setVisibility(View.INVISIBLE);
            queryWeatherCode(countycode);
            //queryWeatherInfo("101090901");
        }else{
            //没有县级代号就直接显示天气(本地已经存有数据)
            showWeather();
        }
        //switchCity.setOnClickListener(this);
        //refreshWeather.setOnClickListener(this);
    }

    /**
     * 判断click对象
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.switch_city:
                Intent intent=new Intent(this,ChooseAreaActivity.class);
                intent.putExtra("from_weather_activity",true);
                startActivity(intent);
                finish();
                break;
            case R.id.refresh_weather:
                publishText.setText("同步中。。。");

                break;
            default:
                break;
        }
    }

    /**
     * 查询县级代号对应的天气代号
     * @param countyCode
     */
    private void queryWeatherCode(String countyCode){
        String address="http://m.weather.com.cn/data5/city"+countyCode+".xml";
//        String address="http://www.weather.com.cn/data/list3/city"+countyCode+".xml";
        //Toast.makeText(WeatherActivity.this,"countyCode:"+address,Toast.LENGTH_SHORT).show();
        queryFromServer(address,"countyCode");
    }

    /**
     * 根据天气代号查询天气信息
     * @param weatherCode
     */
    private void queryWeatherInfo(String weatherCode){
        //中国天气网API接口有问题，换用其他API
//        String address="http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
//        try {
//            weatherCode=URLEncoder.encode(weatherCode,"UTF-8");
//        }catch (UnsupportedEncodingException e){
//            e.printStackTrace();
//        }
//        String address="http://wthrcdn.etouch.cn/weather_mini?citykey="+weatherCode;
        String address="http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
        queryFromServer(address,"weatherCode");
    }
    /**
     * 根据传入的地址和类型查询天气信息或者天气代号
     * @param address
     * @param type
     */
    private void queryFromServer(final String address,final String type){
        HttpUtil.sendHttpRequest(address, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("tips","onFinish得到执行");
                if ("countyCode".equals(type)){
                    if (!TextUtils.isEmpty(response)){
                        String[] array=response.split("\\|");
                        if (array!=null && array.length==2){
                            final String weatherCode=array[1];
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(WeatherActivity.this,weatherCode,Toast.LENGTH_SHORT).show();
                                }
                            });
                            //queryWeatherInfo(weatherCode);
                        }
                    }
                }else if ("weatherCode".equals(type)){
                    Utility.handleWeatherResponse(WeatherActivity.this,response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }
            @Override
            public void onError(Exception e) {
                Log.d("tips","onError得到执行");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishText.setText("同步失败");
                        Toast.makeText(WeatherActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 从sharedpreferences中读取存储的天气信息并显示
     */
    private void showWeather(){
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        cityNameText.setText(prefs.getString("city_name",""));
        temp1Text.setText(prefs.getString("temp1",""));
        temp2Text.setText(prefs.getString("temp2",""));
        weatherDespText.setText(prefs.getString("weather_desp",""));
        publishText.setText("今天"+prefs.getString("publish_time","")+"发布");
        currenDateText.setText(prefs.getString("current_date",""));
        weatherInfoLayout.setVisibility(View.VISIBLE);
        cityNameText.setVisibility(View.VISIBLE);
    }
}
