package com.example.apps.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.apps.R;
import com.example.apps.data.bean.Constract;
import com.example.apps.data.bean.DataBean;
import com.example.apps.data.helper.NewsDatabase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NewsActivity extends AppCompatActivity {
    private WebView webView;
    private ImageButton backbtn, addfacbtn;
    private ProgressBar progressBar;
    private String url;
    private SQLiteDatabase db;
    private DataBean dataBean;
    private Integer flag = 0;
    private Integer flag2 = 0;
    private Integer userid = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Intent intent = getIntent();
        dataBean = (DataBean) intent.getSerializableExtra("data_info");
        url = dataBean.getNewsUrl();
        db = new NewsDatabase(this, Constract.DB_BANE, null, 2).getWritableDatabase();
        initView();
        Cursor cursor = db.rawQuery(Constract.GET_LOGIN_USER, new String[]{"1"});
        if (cursor.getCount() < 1) {
            flag = 0;
        } else {
            flag = 1;
            if(cursor.moveToFirst()){
                userid = cursor.getInt(cursor.getColumnIndex("userid"));
            }
            Cursor cursor1 = db.rawQuery(Constract.GET_LOGIN_USER_Collect,new String[]{dataBean.getId().toString(), userid.toString()});
            System.out.println(cursor1.getCount());
            if(cursor1.getCount() >= 1){
                flag2 = 1;
                addfacbtn.setBackground(getResources().getDrawable(R.drawable.ic_baseline_star_25));
            }
        }
        initListener();
        initWebView();
    }

    public void initView() {
        webView = findViewById(R.id.webView);
        backbtn = findViewById(R.id.back_to_home);
        addfacbtn = findViewById(R.id.acc_fac);
        addfacbtn.setVisibility(View.VISIBLE);
        progressBar = findViewById(R.id.progressBar);
    }

    public void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });
    }

    public void initListener() {
        backbtn.setOnClickListener(v -> finish());
        addfacbtn.setOnClickListener(v -> {
            if (flag == 0) {
                Toast.makeText(this, "请登录后再使用收藏功能！", Toast.LENGTH_SHORT).show();
            } else {
                if (flag2 == 0) {
                    ContentValues values = new ContentValues();
                    values.put("id", dataBean.getId());
                    values.put("userid", userid);
                    values.put("type", dataBean.getType());
                    values.put("newsName", dataBean.getNewsName());
                    values.put("img1", dataBean.getImg1());
                    values.put("img2", dataBean.getImg2());
                    values.put("img3", dataBean.getImg3());
                    values.put("newsUrl", dataBean.getNewsUrl());
                    db.insert("newsList", null, values);
                    Toast.makeText(this, "收藏成功！", Toast.LENGTH_SHORT).show();
                    addfacbtn.setBackground(getResources().getDrawable(R.drawable.ic_baseline_star_25));
                    flag2 = 1;
                } else {
                    db.delete("newsList", "id = ? and userid = ?", new String[]{dataBean.getId().toString(),userid.toString()});
                    addfacbtn.setBackground(getResources().getDrawable(R.drawable.ic_baseline_star_border_24));
                    Toast.makeText(this, "取消收藏成功！", Toast.LENGTH_SHORT).show();
                    flag2 = 0;
                }
            }
        });
    }
}