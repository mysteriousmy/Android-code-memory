package com.example.apps.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.apps.R;
import com.example.apps.data.bean.Constract;
import com.example.apps.data.bean.DataBean;
import com.example.apps.data.helper.NewsDatabase;
import com.example.apps.ui.adapter.NewsListRecyViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CollectActivity extends AppCompatActivity {
    private RecyclerView newsList;
    private SQLiteDatabase db;
    private Integer userid;
    private TextView title;
    private NewsListRecyViewAdapter newsListRecyViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        initView();
        initData();
    }
    public void initView(){
        newsList = findViewById(R.id.collect);
        title = findViewById(R.id.doing);
        title.setText("我的收藏");
    }
    public void initData(){
        newsList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        db = new NewsDatabase(this, Constract.DB_BANE, null, 2).getWritableDatabase();
        Cursor user = db.rawQuery(Constract.GET_LOGIN_USER, new String[]{"1"});
        while (user.moveToNext()){
            userid = user.getInt(user.getColumnIndex("userid"));
        }
        Cursor cursor = db.rawQuery(Constract.GET_ALLCOLLECT_USER,new String[]{String.valueOf(userid)});
        if(cursor.getCount() > 0){
            List<DataBean> dataBeans = new ArrayList<>();
            while (cursor.moveToNext()){
                dataBeans.add(parse(cursor));
            }
            newsListRecyViewAdapter = new NewsListRecyViewAdapter(dataBeans);
            newsList.setItemAnimator(new DefaultItemAnimator());
            newsList.setAdapter(newsListRecyViewAdapter);
            newsListRecyViewAdapter.notifyDataSetChanged();
        }
    }
    public DataBean parse(Cursor cursor){
        DataBean dataBean = new DataBean();
        dataBean.setId(cursor.getInt(cursor.getColumnIndex("id")));
        dataBean.setNewsName(cursor.getString(cursor.getColumnIndex("newsName")));
        dataBean.setType(cursor.getInt(cursor.getColumnIndex("type")));
        dataBean.setImg1(cursor.getString(cursor.getColumnIndex("img1")));
        dataBean.setImg2(cursor.getString(cursor.getColumnIndex("img2")));
        dataBean.setImg3(cursor.getString(cursor.getColumnIndex("img3")));
        dataBean.setNewsUrl(cursor.getString(cursor.getColumnIndex("newsUrl")));
        return dataBean;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    public void startNews(View view){
        TextView news_url = view.findViewById(R.id.news_info);
        TextView news_name = view.findViewById(R.id.news_title);
        StringTokenizer st = new StringTokenizer(news_url.getText().toString(), "，");
        DataBean dataBean = new DataBean();
        dataBean.setNewsName(news_name.getText().toString());
        dataBean.setNewsUrl(st.nextToken());
        dataBean.setImg1(st.nextToken());
        dataBean.setImg2(st.nextToken());
        dataBean.setImg3(st.nextToken());
        dataBean.setType((Integer.valueOf(st.nextToken())));
        dataBean.setId((Integer.valueOf(st.nextToken())));
        Intent intent = new Intent(CollectActivity.this, NewsActivity.class);
        intent.putExtra("data_info",dataBean);
        startActivity(intent);
    }
}