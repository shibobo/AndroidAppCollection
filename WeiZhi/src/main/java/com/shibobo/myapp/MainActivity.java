package com.shibobo.myapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private List<News> newsList=new ArrayList<News>();
    //private TextView mTitle;
    //private TextView mContent;
    private ListView newsListView;
    //private ProgressDialog pd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化数据
        initData();
        //加载进度条
        //pd.show();
        NewsAdapter newsAdapter=new NewsAdapter(MainActivity.this,R.layout.news_item,newsList);
        newsListView=(ListView) findViewById(R.id.news_main_title);
        newsListView.setAdapter(newsAdapter);
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //mTitle=(TextView) findViewById(R.id.news_title);
                //mContent=(TextView) findViewById(R.id.news_content);
                String title=newsList.get(position).getTitle();
                String content=newsList.get(position).getContent();
                //Toast.makeText(MainActivity.this,title+","+content,Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this,"id:"+position,Toast.LENGTH_SHORT).show();
                actionStart(MainActivity.this,title,content);
            }
        });
    }
    public void initData(){
        // TODO: 2016/9/22 0022
        // 此处的数据暂时只能通过这种方式来添加，
        // 等到学完了xml解析技术之后就可以从网上
        // 读取并解析xml文件，并动态添加，可以实现
        // 诸如微博等功能，敬请期待
//        News news1=new News("sbb","a boy");
//        newsList.add(news1);
//        News news2=new News("wsh","also a boy");
//        newsList.add(news2);
//        News news3=new News("sff","a girl");
//        newsList.add(news3);
//        News news4=new News("sbb1","a boy");
//        newsList.add(news4);
//        News news5=new News("wsh1","also a boykdfjjjjjjjjadjkfj dkjfkadjfkdaljf dakjfkdlajf 我我看大家疯狂的大家疯狂点击发");
//        newsList.add(news5);
//        News news6=new News("sff1","a girl");
//        newsList.add(news6);
//        String titles="me";
//        String contents="you";
//        for (int i=0;i<=3;i++){
//            newsList.add(new News(titles,contents));
//        }
        LoadJsonData("news.json");
    }
    public static void actionStart(Context context, String newsTitle, String newsContent){
        Intent intent=new Intent(context,NewsContentActivity.class);
        intent.putExtra("newsTitle",newsTitle);
        intent.putExtra("newsContent",newsContent);
        context.startActivity(intent);
    }
    public void LoadJsonData(String filename){
        //Log.d("load","loading");
        //读取本地文件
        StringBuilder stringBuilder=new StringBuilder();
        try {
            //一定要添加assets文件
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(getAssets().open(filename),"UTF-8"));
            String line;
            while((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            bufferedReader.close();
            String dataGet=stringBuilder.toString();
//            parseJsonWithJsonObject(dataGet);
            parseJsonWithGson(dataGet);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    //利用jsonobject来进行解析
    public void parseJsonWithJsonObject(String jsonData){
        try{
            //JSONObject jsonObject=new JSONObject(jsonData);
            Log.d("json",jsonData);
            JSONArray jsonArray=new JSONArray(jsonData);
            for (int i=0;i<=jsonData.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                //Log.d("json",jsonObject);
                String id=jsonObject.getString("id");
                String title=jsonObject.getString("title");
                String content=jsonObject.getString("content");
                newsList.add(new News(title,content));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    //利用Gson来进行解析
    public void parseJsonWithGson(String jsonData){
        Gson gson=new Gson();
        List<News> list=gson.fromJson(jsonData,new TypeToken<List<News>>(){}.getType());
        for (News news:list){
            //String id=news.getId();
            String title=news.getTitle();
            String content=news.getContent();
            newsList.add(new News(title,content));
        }

    }

}
