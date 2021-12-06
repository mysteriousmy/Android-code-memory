package com.example.apps.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.apps.R;
import com.example.apps.data.bean.Constract;
import com.example.apps.data.bean.DataBean;
import com.example.apps.data.helper.NewsDatabase;
import com.example.apps.ui.adapter.HomePagerAdapter;
import com.example.apps.ui.dashboard.DashboardFragment;
import com.example.apps.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity{
    private ViewPager viewPager;
    private BottomNavigationView navView;
    private TextView title;
    private SQLiteDatabase db;
    private HomeFragment homeFragment;
    private DashboardFragment dashboardFragment;
    private Integer crr = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeStatusBarTransparent(this);
        setFitSystemWindow(false);
        initView();
        initListener();
        db = new NewsDatabase(this, Constract.DB_BANE, null, 2).getWritableDatabase();
    }


    private void initListener() {
        navView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            switch (itemId){
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0,true);
                    title.setText("首页");
                    crr = 0;
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1,true);
                    title.setText("个人中心");
                    crr = 1;
                    return true;
                default:
                    break;
            }
            return false;
        });
        navView.setSelectedItemId(R.id.navigation_home);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        navView.setSelectedItemId(R.id.navigation_home);
                        title.setText("首页");
                        crr = 0;
                        break;
                    case 1:
                        navView.setSelectedItemId(R.id.navigation_dashboard);
                        title.setText("个人中心");
                        crr = 1;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void initView(){
        navView = findViewById(R.id.nav_view);
        title = findViewById(R.id.titles);
        viewPager = findViewById(R.id.main_pager);
        List<Fragment> fragments = new ArrayList<>();
        homeFragment = new HomeFragment();
        dashboardFragment = new DashboardFragment();
        fragments.add(homeFragment);
        fragments.add(dashboardFragment);
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager(),0, fragments);
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
        System.out.println(crr);
        if(crr == 0){
            viewPager.setCurrentItem(0);
            navView.setSelectedItemId(R.id.navigation_home);
        }else {
            viewPager.setCurrentItem(1);
            navView.setSelectedItemId(R.id.navigation_dashboard);
        }

    }

    public static void makeStatusBarTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int option = window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    private View contentViewGroup;

    protected void setFitSystemWindow(boolean fitSystemWindow) {
        if (contentViewGroup == null) {
            contentViewGroup = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        }
        contentViewGroup.setFitsSystemWindows(fitSystemWindow);
    }
    public void startNews(View view){
        TextView news_url = view.findViewById(R.id.news_info);
        TextView news_name = view.findViewById(R.id.news_title);
        StringTokenizer st = new StringTokenizer(news_url.getText().toString(), "，");
        System.out.println(news_url.getText());
        DataBean dataBean = new DataBean();
        dataBean.setNewsName(news_name.getText().toString());
        dataBean.setNewsUrl(st.nextToken());
        dataBean.setImg1(st.nextToken());
        dataBean.setImg2(st.nextToken());
        dataBean.setImg3(st.nextToken());
        dataBean.setType((Integer.valueOf(st.nextToken())));
        dataBean.setId((Integer.valueOf(st.nextToken())));
        Intent intent = new Intent(MainActivity.this, NewsActivity.class);
        intent.putExtra("data_info",dataBean);
        startActivity(intent);
    }
}