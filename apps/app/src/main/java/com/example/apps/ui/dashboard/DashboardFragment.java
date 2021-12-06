package com.example.apps.ui.dashboard;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apps.ui.activity.LoginRgActivity;
import com.example.apps.R;
import com.example.apps.ui.activity.UserInfoActivity;
import com.example.apps.data.bean.Constract;
import com.example.apps.data.helper.NewsDatabase;
import com.example.apps.ui.adapter.SettingRecylerViewAdapter;
import com.example.apps.data.bean.SettingType;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    private RecyclerView settings;
    private SQLiteDatabase db;
    private RelativeLayout user_main;
    private Button lgbtn;
    private TextView username;
    private TextView usersign;
    private ImageView head;
    private TextView usernickname;
    private View view;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        db = new NewsDatabase(root.getContext(), Constract.DB_BANE, null, 2).getReadableDatabase();
        initView(root);
        initData(root);
        return root;
    }
    public void initView(View root){
        user_main = root.findViewById(R.id.user_info_head);
        lgbtn = root.findViewById(R.id.login_reg_btn);
        username = root.findViewById(R.id.user_name);
        usersign = root.findViewById(R.id.user_sign);
        head = root.findViewById(R.id.user_head);
        usernickname = root.findViewById(R.id.user_nickname);
        view = root.findViewById(R.id.user_info_head);
        view.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), UserInfoActivity.class);
            startActivity(intent);
        });
    }
    public void initData(View root){
        Cursor cursor = db.rawQuery(Constract.GET_LOGIN_USER,new String[]{"1"});
        System.out.println(cursor);
        if(cursor.getCount() == 0){
            user_main.setVisibility(View.GONE);
            lgbtn.setVisibility(View.VISIBLE);
        }else {
            cursor.moveToFirst();
            username.setText(cursor.getString(cursor.getColumnIndex("uname")));
            String nickname = cursor.getString(cursor.getColumnIndex("nickname"));
            String sign = cursor.getString(cursor.getColumnIndex("signtext"));
            String headpath = cursor.getString(cursor.getColumnIndex("headimg"));
            usersign.setText(sign == null ? "这个人很懒，没有个性签名！" : sign);
            usernickname.setText(nickname == null ? "昵称：莫得" : "昵称" + nickname);
            if(headpath != null){
                Picasso.get().load(new File(headpath)).transform(new CircleTransForm()).memoryPolicy(MemoryPolicy.NO_CACHE).error(R.drawable.ic_baseline_account_circle_24).into(head);
            }
            user_main.setVisibility(View.VISIBLE);
            lgbtn.setVisibility(View.GONE);
        }
        lgbtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginRgActivity.class);
            startActivity(intent);
        });
        settings = root.findViewById(R.id.settingsView);
        settings.setLayoutManager(new LinearLayoutManager(root.getContext(),LinearLayoutManager.VERTICAL,false));
        List<SettingType> settinglist = new ArrayList<>();
        settinglist.add(new SettingType(R.drawable.ic_baseline_add_circle_outline_24,"我的关注"));
        settinglist.add(new SettingType(R.drawable.ic_baseline_notifications_active_24,"我的消息"));
        settinglist.add(new SettingType(R.drawable.ic_baseline_star_24, "我的收藏"));
        settinglist.add(new SettingType(R.drawable.ic_baseline_settings_24,"系统设置"));
        if(cursor.getCount() != 0){
            settinglist.add(new SettingType(R.drawable.ic_baseline_exit_to_app_24,"退出系统"));
        }
        SettingRecylerViewAdapter settingRecylerViewAdapter = new SettingRecylerViewAdapter(getContext(),settinglist);
        settings.setItemAnimator(new DefaultItemAnimator());
        settings.setAdapter(settingRecylerViewAdapter);
        cursor.close();
        db.close();
    }

}