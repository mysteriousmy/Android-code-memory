package com.example.apps.ui.home;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.apps.R;
import com.example.apps.data.api.ApiService;
import com.example.apps.data.bean.DataBean;
import com.example.apps.data.service.RetrofitClient;
import com.example.apps.ui.adapter.NewsListRecyViewAdapter;
import com.example.apps.ui.adapter.PisscoImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
@RequiresApi(api = Build.VERSION_CODES.N)
@SuppressLint("CheckResult")
public class HomeViewModel extends ViewModel {
    private Banner banner;
    private RecyclerView newsList;
    private NewsListRecyViewAdapter newsListRecyViewAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RetrofitClient retrofitClient;
    public HomeViewModel(View root) {
        banner = root.findViewById(R.id.mybanner);
        swipeRefreshLayout = root.findViewById(R.id.fresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.blue);
        banner.setImageLoader(new PisscoImageLoader());
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setBannerAnimation(Transformer.Default);
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        newsList = root.findViewById(R.id.newsList);
        newsList.setLayoutManager(new LinearLayoutManager(root.getContext(),LinearLayoutManager.VERTICAL,false));
        initObserver(root);
        retrofitClient = new RetrofitClient(ApiService.class);
        swipeRefreshLayout.setOnRefreshListener(()-> {
            ObserverNewsList(root, retrofitClient);
            ObserverBanner(root, retrofitClient);
        });
    }
    public void initObserver(View root){
        ObserverBanner(root,new RetrofitClient(ApiService.class));
        ObserverNewsList(root, new RetrofitClient(ApiService.class));
    }
    public void ObserverNewsList(View root, RetrofitClient retrofitClient){
        DataBean end = new DataBean();
        retrofitClient.getService().getNewList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataBeans -> {
                    end.setType(3);
                    dataBeans.add(end);
                    newsListRecyViewAdapter = new NewsListRecyViewAdapter(dataBeans);
                    newsList.setItemAnimator(new DefaultItemAnimator());
                    newsList.setAdapter(newsListRecyViewAdapter);
                    newsListRecyViewAdapter.notifyDataSetChanged();
                }, error ->{
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(root.getContext(),"提示:网络出错了",Toast.LENGTH_SHORT).show();
                    Log.e("错误",error.getMessage());
                }, ()-> {
                    swipeRefreshLayout.setRefreshing(false);
                });
    }
    public void ObserverBanner(View root, RetrofitClient retrofitClient){
        List imgs = new ArrayList();
        List title = new ArrayList();
        retrofitClient.getService().getAdList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataBeans -> {
                    dataBeans.stream().forEach(x -> {
                        imgs.add(x.getImg1());
                        title.add(x.getNewsName());
                    });
                    banner.setImages(imgs);
                    banner.setBannerTitles(title);
                    banner.start();
                }, error -> {
                    Toast.makeText(root.getContext(),"提示:网络出错了",Toast.LENGTH_SHORT).show();
                    Log.e("错误",error.getMessage());
                });
    }

}