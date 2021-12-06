package com.example.apps.ui.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.apps.R;
import com.example.apps.data.bean.DataBean;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class NewsListRecyViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DataBean> dataBeans;
    public NewsListRecyViewAdapter(List<DataBean> dataBeans) {
        this.dataBeans = dataBeans;
    }

    static class NewsListViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle;
        TextView news_url;
        ImageView img;
        public NewsListViewHolder(View view) {
            super(view);
            newsTitle = view.findViewById(R.id.news_title);
            news_url = view.findViewById(R.id.news_info);
            img = view.findViewById(R.id.news_img);
        }
    }

    static class NewsListViewHolder2 extends RecyclerView.ViewHolder {
        TextView newsTitle2;
        TextView news_url;
        ImageView img1;
        ImageView img2;
        ImageView img3;
        public NewsListViewHolder2(View view) {
            super(view);
            newsTitle2 = view.findViewById(R.id.news_title);
            img1 = view.findViewById(R.id.news_img1);
            img2 = view.findViewById(R.id.news_img2);
            img3 = view.findViewById(R.id.news_img3);
            news_url = view.findViewById(R.id.news_info);
        }
    }
    static class NewsListViewHolder3 extends RecyclerView.ViewHolder {
        TextView end;
        public NewsListViewHolder3(View view) {
            super(view);
            end = view.findViewById(R.id.endlist);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_list_type1, parent, false);
                return new NewsListViewHolder(view);
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_list_type2, parent, false);
                return new NewsListViewHolder2(view);
            case 4:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_end, parent, false);
                return new NewsListViewHolder3(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DataBean dataBean = dataBeans.get(position);
        if (position != dataBeans.size() + 1){
            if (holder instanceof NewsListViewHolder) {
                Picassoset(dataBean.getImg1(),((NewsListViewHolder) holder).img);
                ((NewsListViewHolder) holder).newsTitle.setText(dataBean.getNewsName());
                ((NewsListViewHolder) holder).news_url.setText(dataBean.getNewsUrl() + "，" + ("".equals(dataBean.getImg1()) ? "null" : dataBean.getImg1()) + "，" + ("".equals(dataBean.getImg2()) ? "null" : dataBean.getImg2()) + "，" + ("".equals(dataBean.getImg3()) ? "null" : dataBean.getImg3()) + "，" + dataBean.getType().toString()+ "，" + dataBean.getId().toString());
            }
            if (holder instanceof NewsListViewHolder2) {
                Picassoset(dataBean.getImg1(),((NewsListViewHolder2) holder).img1);
                Picassoset(dataBean.getImg2(),((NewsListViewHolder2) holder).img2);
                Picassoset(dataBean.getImg3(),((NewsListViewHolder2) holder).img3);
                ((NewsListViewHolder2) holder).newsTitle2.setText(dataBean.getNewsName());
                ((NewsListViewHolder2) holder).news_url.setText(dataBean.getNewsUrl() + "，" + ("".equals(dataBean.getImg1()) ? "null" : dataBean.getImg1()) + "，" + ("".equals(dataBean.getImg2()) ? "null" : dataBean.getImg2()) + "，" + ("".equals(dataBean.getImg3()) ? "null" : dataBean.getImg3()) + "，" + dataBean.getType().toString()+ "，" + dataBean.getId().toString());
            }
        }
    }

    public void Picassoset(String url, ImageView view){
        if(url.trim().length() == 0){
            Picasso.get()
                    .load(R.drawable.eer)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(view);
        }else {
            Picasso.get()
                    .load(url)
                    .error(R.drawable.eer)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(view);
        }
    }
    @Override
    public int getItemViewType(int position) {
        switch (dataBeans.get(position).getType()){
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

}
