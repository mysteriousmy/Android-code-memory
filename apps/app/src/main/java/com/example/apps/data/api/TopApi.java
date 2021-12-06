package com.example.apps.data.api;

import com.example.apps.data.bean.Constract;
import com.example.apps.data.bean.DataBean;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface TopApi{
    @GET(Constract.REQUEST_FILE_AD)
    Observable<List<DataBean>> getAdList();
    @GET(Constract.REQUEST_FILE_NEWS)
    Observable<List<DataBean>> getNewList();
}
