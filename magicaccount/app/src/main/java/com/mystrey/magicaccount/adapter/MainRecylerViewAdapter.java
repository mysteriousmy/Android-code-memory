package com.mystrey.magicaccount.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mystrey.magicaccount.entity.AccountType;
import com.mystrey.magicaccount.R;

import java.util.List;

public class MainRecylerViewAdapter extends RecyclerView.Adapter<MainRecylerViewAdapter.ViewHolder> {

    private List<AccountType> mAccountList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView Accounttype;
        TextView AccountMoney;
        TextView idss;
        ImageView xiaoyuandian;


        public ViewHolder(View view) {
            super(view);
            Accounttype = (TextView) view.findViewById(R.id.doing);
            AccountMoney = (TextView) view.findViewById(R.id.expense);
            xiaoyuandian = (ImageView) view.findViewById(R.id.isoutcircle);
            idss = (TextView)view.findViewById(R.id.typeids);
        }
    }

    public MainRecylerViewAdapter(List<AccountType> mAccountList) {
        this.mAccountList = mAccountList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recy_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AccountType Account = mAccountList.get(position);
        holder.Accounttype.setText(Account.getTname());
        holder.idss.setText(String.valueOf(Account.getTypeids()));
        if(Account.getIsoutcicle() == 1){
            holder.AccountMoney.setText("￥+"+Account.getMoneytitle());
            holder.AccountMoney.setTextColor(Color.parseColor("#75D701"));
            holder.xiaoyuandian.setBackgroundResource(R.mipmap.green);
        }else if (Account.getIsoutcicle() == 0){
            holder.AccountMoney.setTextColor(Color.parseColor("#E71D36"));
            holder.AccountMoney.setText("￥-"+Account.getMoneytitle());
            holder.xiaoyuandian.setBackgroundResource(R.mipmap.red);
        }
    }

    @Override
    public int getItemCount() {
        return mAccountList.size();
    }
}