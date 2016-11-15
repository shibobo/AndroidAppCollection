package com.shibobo.myslidingmenutest;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mtoolbar;
    private FloatingActionButton mfloatingbutton;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    //标题
    private List<String> titleLists;
    //对应的view
    private View view1,view2,view3,view4;
    //view集合
    private List<View> viewLists;
    private LayoutInflater inflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtoolbar= (Toolbar) findViewById(R.id.mtoolbar);
        mfloatingbutton= (FloatingActionButton) findViewById(R.id.mfloatingbutton);
        setSupportActionBar(mtoolbar);
        mfloatingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"hello boy",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
            }
        });
        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,mtoolbar, R.string.open, R.string.close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navigationView= (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
//                switch (item.getItemId()){
//                    case R.id.nav_camera:
//                        Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.nav_gallery:
//                        break;
//                    case R.id.nav_slideshow:
//                        break;
//                    case R.id.nav_manage:
//                        break;
//                    case R.id.nav_share:
//                        break;
//                    case R.id.nav_send:
//                        break;
//
//                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        tabLayout= (TabLayout) findViewById(R.id.tablayout);
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        titleLists=new ArrayList<String>();
        viewLists=new ArrayList<View>();
        inflater=LayoutInflater.from(this);
        view1=inflater.inflate(R.layout.tab_special,null);
        view2=inflater.inflate(R.layout.tab_music,null);
        view3=inflater.inflate(R.layout.tab_channel,null);
        view4=inflater.inflate(R.layout.tab_rank,null);

        viewLists.add(view1);
        viewLists.add(view2);
        viewLists.add(view3);
        viewLists.add(view4);

        titleLists.add("个性推荐");
        titleLists.add("歌单");
        titleLists.add("主播电台");
        titleLists.add("排行榜");

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(titleLists.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titleLists.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(titleLists.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(titleLists.get(3)));

        MyPagerAdapter myPagerAdapter=new MyPagerAdapter(viewLists,titleLists);
        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(myPagerAdapter);
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about:
                Toast.makeText(this, "关于我们", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    class MyPagerAdapter extends PagerAdapter{

        private List<View> mViewLists;
        private List<String> mTitleLists;

        public MyPagerAdapter(List<View> mViewLists,List<String> mTitleLists){
            this.mViewLists=mViewLists;
            this.mTitleLists=mTitleLists;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public int getCount() {
            return mViewLists.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewLists.get(position),0);
            return mViewLists.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewLists.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleLists.get(position);
        }
    }
}
