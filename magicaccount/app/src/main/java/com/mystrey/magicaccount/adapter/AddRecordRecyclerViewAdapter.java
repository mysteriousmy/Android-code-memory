package com.mystrey.magicaccount.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mystrey.magicaccount.myview.MyImageView;
import com.mystrey.magicaccount.R;

import java.util.List;
import java.util.Map;

public class AddRecordRecyclerViewAdapter extends RecyclerView.Adapter<AddRecordRecyclerViewAdapter.ViewHolder>{
    private Context mContext;
    private LayoutInflater mInflater;
    private static final int VIEW_TYPE_TITLE= 0;
    private static final int VIEW_TYPE_ITEM = 1;
    int IS_TITLE_OR_NOT =1;
    int MESSAGE = 2;
    int ColumnNum;
    private OnItemClickListener mOnItemClickListener;
    List<Map<Integer, String>> mData;


    public AddRecordRecyclerViewAdapter(Context context , List<Map<Integer, String>> mData , int ColumnNum) {
        this.mContext=context;
        this.ColumnNum=ColumnNum;
        this.mData= mData;
    }

    @Override
    public AddRecordRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder  holder = null;
        mInflater = LayoutInflater.from(mContext);

        //判断viewtype类型返回不同Viewholder
        switch (viewType) {
            case VIEW_TYPE_TITLE:
                holder = new HolderOne(mInflater.inflate(R.layout.account_pager_title, parent, false));
                break;
            case VIEW_TYPE_ITEM:
                holder = new HolderTwo(mInflater.inflate(R.layout.account_onepager_icon, parent,false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if("true".equals(mData.get(position).get(IS_TITLE_OR_NOT))){
            holder.mTitle.setText(mData.get(position).get(MESSAGE));
        }else {
            String get = mData.get(position).get(MESSAGE);
            String[] gets = get.split("~");
            holder.mItem.setText(gets[0]);
            holder.mImageView.setImageURL(gets[1]);
        }

    }

    //判断RecyclerView的子项样式，返回一个int值表示
    @Override
    public int getItemViewType(int position) {
        if ("true".equals(mData.get(position).get(IS_TITLE_OR_NOT))) {
            return VIEW_TYPE_TITLE;
        }
        return VIEW_TYPE_ITEM;
    }

    //判断是否是title，如果是，title占满一行的所有子项，则是ColumnNum个，如果是item，占满一个子项
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        //如果是title就占据2个单元格(重点)
        GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if("false".equals(mData.get(position).get(IS_TITLE_OR_NOT))){
                    return 1;
                }else {
                    return ColumnNum;
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position , List<Object> payloads) {
        if(payloads.isEmpty()){
            onBindViewHolder(holder,position);
        } else {
            onBindViewHolder(holder,position);
        }
    }

    //对于不同布局的子项，需要对它进行初始化
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public TextView mItem;
        public MyImageView mImageView;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
    public class HolderOne extends ViewHolder {

        public HolderOne(View viewHolder) {
            super(viewHolder);
            mTitle= (TextView) viewHolder.findViewById(R.id.pagertitle);
        }
    }

    public class HolderTwo extends ViewHolder{

        public HolderTwo(final View viewHolder) {
            super(viewHolder);
            mItem =(TextView)viewHolder.findViewById(R.id.onepage_tname);
            mImageView =(MyImageView)viewHolder.findViewById(R.id.onepage_ticon);
        }
    }
    public interface OnItemClickListener{
        void onClick( int position);
        void onLongClick( int position);
    }

}