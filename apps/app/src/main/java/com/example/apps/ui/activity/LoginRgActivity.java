package com.example.apps.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apps.R;
import com.example.apps.data.bean.Constract;
import com.example.apps.data.helper.BaseChecker;
import com.example.apps.data.helper.NewsDatabase;
import com.google.android.material.snackbar.Snackbar;

public class LoginRgActivity extends AppCompatActivity {
    private TextView title;
    private TextView toLogin;
    private TextView toRegister;
    private View login_view;
    private View register_view;
    private Button login_btn;
    private Button reg_btn;
    private ImageView see_btn1;
    private ImageView see_btn2;
    private ImageView see_btn3;
    private Integer flag1 = 0;
    private Integer flag2 = 0;
    private Integer flag3 = 0;
    private EditText login_username;
    private EditText login_password;
    private EditText register_username;
    private EditText register_password;
    private EditText register_repassword;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_rg);
        db = new NewsDatabase(this, Constract.DB_BANE, null, 2).getReadableDatabase();
        initView();
        initListener();
    }
    public void initView(){
        title = findViewById(R.id.doing);
        title.setText("登录/注册账户");
        login_view = findViewById(R.id.login_section);
        register_view = findViewById(R.id.register_section);
        toLogin = findViewById(R.id.login_a);
        toRegister = findViewById(R.id.reg_a);
        login_btn = findViewById(R.id.login_btn);
        reg_btn = findViewById(R.id.reg_btn);
        see_btn1 = findViewById(R.id.see_btn1);
        see_btn2 = findViewById(R.id.see_btn2);
        see_btn3 = findViewById(R.id.see_btn3);
        login_username = findViewById(R.id.login_username);
        login_password = findViewById(R.id.login_password);
        register_username = findViewById(R.id.register_username);
        register_password = findViewById(R.id.register_password);
        register_repassword = findViewById(R.id.register_repassword);
    }
    public void initListener(){
        toLogin.setOnClickListener(v -> {
            register_view.setVisibility(View.GONE);
            login_view.setVisibility(View.VISIBLE);
        });
        toRegister.setOnClickListener(v -> {
            login_view.setVisibility(View.GONE);
            register_view.setVisibility(View.VISIBLE);
        });
        login_btn.setOnClickListener(v -> {
            try{
                BaseChecker.Checker(login_username.getText().toString().trim().length() != 0
                        && login_password.getText().toString().length() != 0, "输入不可有空！");
                BaseChecker.Checker(login_password.getText().toString().length() > 8, "密码必须大于8位！");
                Cursor cursor = db.rawQuery(Constract.GET_USER_INFO_BY_USERNAME, new String[]{login_username.getText().toString()});
                BaseChecker.Checker(cursor.getCount() == 1, "不存在的用户！");
                String passwd = "";
                while (cursor.moveToNext()){
                    passwd = cursor.getString(cursor.getColumnIndex("password"));
                }
                BaseChecker.Checker(passwd.equals(login_password.getText().toString()),"密码错误！");
                ContentValues cv = new ContentValues();
                ContentValues cv2 = new ContentValues();
                cv.put("status", 1);
                cv2.put("status", 0);
                db.update("userInfo",cv2,"status = ?", new String[]{String.valueOf(1)});
                db.update("userInfo",cv,"uname = ?", new String[]{login_username.getText().toString()});
                finish();
            }catch (Exception e){
                showSnackBar(login_view,e.getMessage());
            }
        });
        reg_btn.setOnClickListener(v -> {
            try{
                BaseChecker.Checker(register_username.getText().toString().trim().length() != 0
                        && register_password.getText().toString().trim().length() != 0
                        && register_repassword.getText().toString().trim().length() != 0, "输入不可有空！");
                BaseChecker.Checker(register_password.getText().toString().trim().length() > 8
                        && register_repassword.getText().toString().trim().length() > 8, "密码必须大于8位！");
                BaseChecker.Checker(register_password.getText().toString().equals(register_repassword.getText().toString()),"两次输入的密码不一致！");
                Cursor cursor = db.rawQuery(Constract.GET_USER_INFO_BY_USERNAME, new String[]{register_username.getText().toString()});
                BaseChecker.Checker(cursor.getCount() < 1, "已存在的用户名，请起别的！");
                ContentValues values = new ContentValues();
                values.put("uname", register_username.getText().toString());
                values.put("password", register_password.getText().toString());
                values.put("status", 0);
                values.put("usex", 2);
                db.insert("userInfo", null, values);
                showSnackBar(register_view,"注册成功！请登录！");
            }catch (Exception e){
                showSnackBar(register_view,e.getMessage());
            }
        });
        see_btn1.setOnClickListener(v -> {
            if(flag1 == 0){
                checkSee(see_btn1,login_password,flag1);
                flag1 = 1;
            }else {
                checkSee(see_btn1,login_password,flag1);
                flag1 = 0;
            }
        });
        see_btn2.setOnClickListener(v -> {
            if(flag2 == 0){
                checkSee(see_btn2,register_password,flag2);
                flag2 = 1;
            }else {
                checkSee(see_btn2,register_password,flag2);
                flag2 = 0;
            }
        });
        see_btn3.setOnClickListener(v -> {
            if(flag3 == 0){
                checkSee(see_btn3,register_repassword,flag3);
                flag3 = 1;
            }else {
                checkSee(see_btn3,register_repassword,flag3);
                flag3 = 0;
            }
        });
    }
    public void checkSee(ImageView btn,EditText edit, Integer flags){
        if(flags == 0){
            edit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            btn.setBackground(getResources().getDrawable(R.drawable.cansee));
        }else {
            edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
            btn.setBackground(getResources().getDrawable(R.drawable.notsee));
        }
    }
    public void showSnackBar(View view, String message){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setAction("知道了", v -> {}).show();
    }
}