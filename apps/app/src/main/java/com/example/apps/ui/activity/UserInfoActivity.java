package com.example.apps.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apps.R;
import com.example.apps.data.bean.Constract;
import com.example.apps.data.helper.BaseChecker;
import com.example.apps.data.helper.FileHelper;
import com.example.apps.data.helper.NewsDatabase;
import com.example.apps.ui.dashboard.CircleTransForm;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

public class UserInfoActivity extends AppCompatActivity {
    private ImageView headimg;
    private TextView text_uname;
    private TextView text_nickname;
    private TextView text_sex;
    private TextView text_sign;
    private TextView title;
    private SQLiteDatabase db;
    private View dialog;
    private EditText editcontent;
    private String uname,nickname,sign;
    private Integer sex,userid;
    private Button edit_ok,edit_cancel;
    private Integer flag;
    public static final int RC_CHOOSE_PHOTO = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        db = new NewsDatabase(this, Constract.DB_BANE, null, 2).getWritableDatabase();
        initData();
        initListener();
    }
    public void initView(){
        headimg = findViewById(R.id.head_img);
        text_uname = findViewById(R.id.text_uname);
        text_nickname = findViewById(R.id.text_nickname);
        text_sex = findViewById(R.id.text_sex);
        text_sign = findViewById(R.id.text_sign);
        title = findViewById(R.id.doing);
        dialog = findViewById(R.id.dialog_edit_view);
        editcontent = findViewById(R.id.edit_content);
        edit_ok = findViewById(R.id.ok_button);
        edit_cancel = findViewById(R.id.ecancel_button);
        title.setText("个人信息");
    }
    public void initData(){
        Cursor cursor = db.rawQuery(Constract.GET_LOGIN_USER, new String[]{"1"});
        cursor.moveToFirst();
        uname = cursor.getString(cursor.getColumnIndex("uname"));
        userid = cursor.getInt(cursor.getColumnIndex("userid"));
        text_uname.setText(uname);
        nickname = cursor.getString(cursor.getColumnIndex("nickname"));
        sign = cursor.getString(cursor.getColumnIndex("signtext"));
        sex = cursor.getInt(cursor.getColumnIndex("usex"));
        text_nickname.setText(nickname == null ? "无" : nickname);
        text_sex.setText(sex == 0 ? "女" : "男");
        text_sign.setText(sign == null ? "无" : sign);
        String imgpath = cursor.getString(cursor.getColumnIndex("headimg"));
        if(imgpath != null){
            Picasso.get()
                    .load(new File(imgpath))
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .error(R.drawable.ic_baseline_account_circle_24)
                    .transform(new CircleTransForm())
                    .into(headimg);
        }
    }
    public void initListener(){
        edit_cancel.setOnClickListener(v -> {
            dialog.setVisibility(View.GONE);
        });

        edit_ok.setOnClickListener(v -> {
            try {
                BaseChecker.Checker(editcontent.getText().toString().trim().length() != 0, "不可输入为空值！");
                switch (flag){
                    case 1:
                        Cursor cursor = db.rawQuery(Constract.GET_USER_INFO_BY_USERNAME, new String[]{editcontent.getText().toString()});
                        BaseChecker.Checker(cursor.getCount() == 0, "该用户名已经存在！");
                        updateInfo("uname", editcontent.getText().toString());
                        Toast.makeText(this,"修改用户名成功！",Toast.LENGTH_SHORT).show();
                        initData();
                        dialog.setVisibility(View.GONE);
                        break;
                    case 2: ;
                        updateInfo("nickname", editcontent.getText().toString());
                        Toast.makeText(this,"修改昵称成功！",Toast.LENGTH_SHORT).show();
                        initData();
                        dialog.setVisibility(View.GONE);
                        break;
                    case 3:
                        BaseChecker.Checker("男".equals(editcontent.getText().toString()) || "女".equals(editcontent.getText().toString()), "性别只能输入男或者女");
                        Integer sex = ("男".equals(editcontent.getText().toString()) ? 1 : 0);
                        updateInfo("usex", sex);
                        Toast.makeText(this,"修改性别成功！",Toast.LENGTH_SHORT).show();
                        initData();
                        dialog.setVisibility(View.GONE);
                        break;
                    case 4:
                        updateInfo("signtext", editcontent.getText().toString());
                        Toast.makeText(this,"修改个性签名成功！",Toast.LENGTH_SHORT).show();
                        initData();
                        dialog.setVisibility(View.GONE);
                        break;
                }
            }catch (Exception e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }

        });
    }
    public void editAny(View view){

        System.out.println(view.getId());
        switch (view.getId()){
            case Constract.EDIT_HEAD:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RC_CHOOSE_PHOTO);
                } else {
                    choosePhoto();
                }
                break;
            case Constract.EDIT_UNAME:
                editcontent.setText(uname);
                dialog.setVisibility(View.VISIBLE);
                flag = 1;
                break;
            case Constract.EDIT_NICKNAME:
                editcontent.setText(nickname == null ? "无" : nickname);
                dialog.setVisibility(View.VISIBLE);
                flag = 2;
                break;
            case Constract.EDIT_SEX:
                editcontent.setText(sex == 0 ? "女" : "男");
                dialog.setVisibility(View.VISIBLE);
                flag = 3;
                break;
            case Constract.EDIT_SIGN:
                editcontent.setText(sign == null ? "无" : sign);
                dialog.setVisibility(View.VISIBLE);
                flag = 4;
                break;
        }
    }
    public void updateInfo(String key, String value){
        ContentValues contentValues = new ContentValues();
        contentValues.put(key, value);
        db.update("userInfo", contentValues, "userid = ?", new String[]{String.valueOf(userid)});
    }
    public void updateInfo(String key, Integer value){
        ContentValues contentValues = new ContentValues();
        contentValues.put(key, value);
        db.update("userInfo", contentValues, "userid = ?", new String[]{String.valueOf(userid)});
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RC_CHOOSE_PHOTO:
                choosePhoto();
                break;
        }
    }
    private void choosePhoto() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, RC_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_CHOOSE_PHOTO:
                Uri uri = data.getData();
                System.out.println(uri);
                String filePath = FileHelper.getFilePathByUri(this,uri);
                updateInfo("headimg", filePath);
                if (!TextUtils.isEmpty(filePath)) {
                    Picasso.get()
                            .load(uri)
                            .error(R.drawable.ic_baseline_account_circle_24)
                            .transform(new CircleTransForm())
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .into(headimg);
                }
                Toast.makeText(this,"修改头像成功！",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}