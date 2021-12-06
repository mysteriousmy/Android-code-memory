package com.example.apps.ui.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apps.ui.activity.CollectActivity;
import com.example.apps.R;
import com.example.apps.data.bean.Constract;
import com.example.apps.data.bean.SettingType;
import com.example.apps.data.helper.NewsDatabase;
import com.example.apps.ui.activity.MainActivity;
import com.example.apps.ui.dashboard.DashboardFragment;

import java.util.List;
@RequiresApi(api = Build.VERSION_CODES.N)
public class SettingRecylerViewAdapter extends RecyclerView.Adapter<SettingRecylerViewAdapter.ViewHolder> {
    private List<SettingType> mSetting;
    private Context context;
    private SQLiteDatabase db;
    private View root;
    public SettingRecylerViewAdapter(Context context,List<SettingType> settinglist) {
        this.mSetting = settinglist;
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public View root;
        TextView SettingsTitle;
        ImageView imgRe;
        public ViewHolder(View view) {
            super(view);
            this.root = view;
            SettingsTitle = view.findViewById(R.id.doing);
            imgRe = view.findViewById(R.id.isoutcircle);
        }
    }
    @NonNull
    @Override
    public SettingRecylerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recy_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull SettingRecylerViewAdapter.ViewHolder holder, int position) {
        SettingType settingType = mSetting.get(position);
        holder.imgRe.setImageResource(settingType.getImgRe());
        holder.SettingsTitle.setText(settingType.getTitle());
        holder.itemView.setOnClickListener(v ->{
            db = new NewsDatabase(context, Constract.DB_BANE, null, 2).getWritableDatabase();
            if(position == 2){
                Cursor cursor = db.rawQuery(Constract.GET_LOGIN_USER, new String[]{"1"});
                if(cursor.getCount() < 1){
                    Toast.makeText(context,"请登陆后再使用此功能！",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(context, CollectActivity.class);
                    context.startActivity(intent);
                }
            }
            if (position == 4) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("status", 0);
                db.update("userInfo",contentValues, "status = ?", new String[]{"1"});
                if(MainActivity.class.isInstance(context)){
                    MainActivity activity = (MainActivity) context;
                    activity.initView();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSetting.size();
    }

}
