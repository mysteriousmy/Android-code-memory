package com.example.apps.data.service;

import com.example.apps.data.api.ApiService;
import com.example.apps.data.bean.Constract;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private Retrofit retrofit;
    private String BASE_URL = Constract.REQUEST_URL;
    private ApiService service;
    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    public RetrofitClient(Class<ApiService> service){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        this.service = retrofit.create(service);
    }
    public ApiService getService(){
        if (service != null){
            return service;
        }
        return null;
    }
}
