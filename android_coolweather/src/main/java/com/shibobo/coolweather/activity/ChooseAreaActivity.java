package com.shibobo.coolweather.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shibobo.coolweather.R;
import com.shibobo.coolweather.model.City;
import com.shibobo.coolweather.model.County;
import com.shibobo.coolweather.model.Province;
import com.shibobo.coolweather.util.CoolWeatherDB;
import com.shibobo.coolweather.util.HttpCallBackListener;
import com.shibobo.coolweather.util.HttpUtil;
import com.shibobo.coolweather.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class ChooseAreaActivity extends AppCompatActivity {
    private static final int LEVEL_PROVINCE=0;
    private static final int LEVEL_CiTY=1;
    private static final int LEVEL_COUNTY=2;
    private TextView title_text;
    private ListView list_view;
    private ArrayAdapter<String> adapter;
    private List<String> dataList=new ArrayList<>();

    private ProgressDialog progressDialog;
    private CoolWeatherDB coolWeatherDB;
    private Province selectedProvince;
    private City selectedCity;

    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;
    private int currentLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_area);
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        Boolean is_city_selected=prefs.getBoolean("city_selected",false);
        Toast.makeText(this,"city_selected:"+is_city_selected,Toast.LENGTH_SHORT).show();
        if (prefs.getBoolean("city_selected",false)){//本地有天气信息时不用再查询，直接显示
            Toast.makeText(this,"city_selected true",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,WeatherActivity.class);
            startActivity(intent);
            finish();
            return;
        }else{//本地没有数据
            Toast.makeText(this,"city_selected false",Toast.LENGTH_SHORT).show();
        }
        title_text=(TextView) findViewById(R.id.title_text);
        list_view=(ListView) findViewById(R.id.list_view);
//        dataList.add("shibobo");
//        dataList.add("石博博");
//        dataList.add("Bojack Horseman");
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataList);
        list_view.setAdapter(adapter);
        coolWeatherDB=CoolWeatherDB.getInstance(this);
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(ChooseAreaActivity.this,"the index is "+position,Toast.LENGTH_SHORT).show();
                if (currentLevel==LEVEL_PROVINCE){
                    selectedProvince=provinceList.get(position);
                    queryCities();
                }else if (currentLevel==LEVEL_CiTY){
                    selectedCity=cityList.get(position);
                    queryCounties();
                }else if (currentLevel==LEVEL_COUNTY){
                    String countyCode=countyList.get(position).getCountyCode();
                    String countyName=countyList.get(position).getCountyName();
                    Intent intent=new Intent(ChooseAreaActivity.this,WeatherActivity.class);
                    intent.putExtra("county_code",countyCode);
                    intent.putExtra("county_name",countyName);
                    startActivity(intent);
                    finish();
                }
            }
        });
        queryProvinces();
    }

    /**
     * 查询省级数据
     */
    private void queryProvinces(){
        provinceList=coolWeatherDB.loadProvinces();
        if (provinceList.size()>0){
            dataList.clear();
            for (Province province:provinceList){
                dataList.add("NAME:"+province.getProvinceName()+" CODE:"+province.getProvinceCode());
            }
            adapter.notifyDataSetChanged();
            list_view.setSelection(0);
            title_text.setText("中国");
            currentLevel=LEVEL_PROVINCE;
        }else{
            Toast.makeText(ChooseAreaActivity.this,"数据库里面没有数据，尝试从网络加载",Toast.LENGTH_SHORT).show();
            queryFromServer(null,"province");
        }

    }

    /**
     * 查询市级数据
     */
    private void queryCities(){
        cityList=coolWeatherDB.loadCities(selectedProvince.getId());
        if (cityList.size()>0){
            dataList.clear();
            for (City city:cityList){
                dataList.add("NAME:"+city.getCityName()+" CODE:"+city.getCityCode());
            }
            adapter.notifyDataSetChanged();
            list_view.setSelection(0);
            title_text.setText(selectedProvince.getProvinceName());
            currentLevel=LEVEL_CiTY;
        }else{
            queryFromServer(selectedProvince.getProvinceCode(),"city");
        }

    }

    /**
     * 查询县级数据
     */
    private void queryCounties(){
        countyList=coolWeatherDB.loadCounties(selectedCity.getId());
        if (countyList.size()>0){
            dataList.clear();
            for (County county:countyList){
                dataList.add("NAME:"+county.getCountyName()+" CODE:"+county.getCountyCode());
            }
            adapter.notifyDataSetChanged();
            list_view.setSelection(0);
            title_text.setText(selectedCity.getCityName());
            currentLevel=LEVEL_COUNTY;
        }else{
            queryFromServer(selectedCity.getCityCode(),"county");
        }
    }

    /**
     * 从服务器获得数据
     * @param code
     * @param type
     */
    private void queryFromServer(final String code,final String type){
        String address;
        if (!TextUtils.isEmpty(code)){
            address="http://www.weather.com.cn/data/list3/city"+code+".xml";
        }else{
            address="http://www.weather.com.cn/data/list3/city.xml";
        }
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {
                boolean result=false;
                if ("province".equals(type)){
                    result=Utility.handleProvincesResponse(coolWeatherDB,response);
                }else if ("city".equals(type)){
                    result=Utility.handleCitiesResponse(coolWeatherDB,response,selectedProvince.getId());
                }else if ("county".equals(type)){
                    result=Utility.handleCountiesResponse(coolWeatherDB,response,selectedCity.getId());
                }
                if (result){
                    //runOnUiThread回到主线程处理
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)){
                                queryProvinces();
                            }else if ("city".equals(type)){
                                queryCities();
                            }else if ("county".equals(type)){
                                queryCounties();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseAreaActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog(){
        if (progressDialog==null){
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog(){
        if (progressDialog!=null){
            progressDialog.dismiss();
        }
    }

    /**
     * 返回上级或者直接退出
     */
    @Override
    public void onBackPressed() {
        if (currentLevel==LEVEL_COUNTY){
            queryCities();
        }else if (currentLevel==LEVEL_CiTY){
            queryProvinces();
        }else{
            finish();
        }
    }
}
