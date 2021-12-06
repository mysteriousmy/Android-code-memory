package com.mystrey.magicaccount.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mystrey.magicaccount.helper.CodeUtils;
import com.mystrey.magicaccount.helper.MyDataBaseHelper;
import com.mystrey.magicaccount.adapter.MyPagerApdater;
import com.mystrey.magicaccount.R;
import com.mystrey.magicaccount.entity.Resources;
import com.mystrey.magicaccount.adapter.MainRecylerViewAdapter;
import com.mystrey.magicaccount.entity.AccountType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.qqtheme.framework.picker.DatePicker;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ViewPager vpager_one;
    private ArrayList<View> aList;
    private MyPagerApdater mAdapter;
    private View v1;
    private View v2;
    private View v3;
    private String codeStr;
    private CodeUtils codeUtils;
    private ImageView codeimg;
    private TextView toolbar_canlda;
    private String nowdate;
    private String[] todate;
    private SQLiteDatabase helper;
    String uname="0";
    String userid="0";
    private NavigationView nv;
    private View menuheader;
    private ImageView headertouxi;
    private TextView headertitle;
    private TextView header_yue;
    private TextView header_shouru;
    private TextView header_zhichu;
    private TextView notfound;
    private ConstraintLayout header_account;
    private RecyclerView accountrecy;
    private TextView accounttitles;
    private TextView accountyuers;
    private EditText unameEdit;
    private EditText passwordEdit;
    private EditText codeEdit;
    private EditText reunameEdit;
    private EditText repasswordEdit;
    private EditText recovryEdit;
    private Integer tendcytype = 0;
    LayoutInflater li;
    private List<AccountType> mlsit = new ArrayList<AccountType>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //调用状态栏透明
        setStatusBarFullTransparent();
        setFitSystemWindow(false);
        statusCheck();
//      viewpager页面设置
        vpager_one = (ViewPager) findViewById(R.id.vpager_one);
        li = getLayoutInflater().from(this);
        v1 = li.inflate(R.layout.app_bar_main,null);
        initViewPagerAll();
        //加载view
        initview();
        initCreateOnclik();
        uiUpdate();
        nowdate = new SimpleDateFormat(Resources.TIME_PATTERN).format(new Date());
        todate = nowdate.split("–");
        toolbar_canlda.setText(todate[0]+"–"+todate[1]+"–"+todate[2]);
        getAccount(tendcytype);
        aList = new ArrayList<View>();
        setViewPager(v1);
        if(uname.equals("0") || userid.equals("0")){
            setViewPager(v2);
            setViewPager(v3);
        }
        mAdapter = new MyPagerApdater(aList);
        vpager_one.setAdapter(mAdapter);

//        toolbar设置

    }

    //初始化view组件
    public void initview(){
         Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);

         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
         drawer.addDrawerListener(toggle);
         toggle.syncState();
         nv = (NavigationView) findViewById(R.id.nav_view);
         nv.setNavigationItemSelectedListener(this);
         toolbar_canlda = findViewById(R.id.toolbar_canlda);
         menuheader = nv.inflateHeaderView(R.layout.nav_header_main);
         header_account = v1.findViewById(R.id.header_account);
         header_yue = header_account.findViewById(R.id.bentianyue);
         header_shouru = header_account.findViewById(R.id.bentianshouru);
         header_zhichu = header_account.findViewById(R.id.bentianzhichu);
         notfound = header_account.findViewById(R.id.notfound);
         accountrecy = header_account.findViewById(R.id.AccountrecyclerView);
         accounttitles = header_account.findViewById(R.id.Accounttitle);
         accountyuers = header_account.findViewById(R.id.accountyuers);
    }

    //初始化监听事件
    public void initViewpagerOnclick(){
        if(uname.equals("0") || userid.equals("0")){

            v2.findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String back = LoginCheck();
                    Log.d("2",String.valueOf(back));
                    if(back.equals("0")){
                        Snackbar snackbar = snackbarCreate("用户名密码和验证码不可为空！");
                        snackbar.show();
                    }else if(back.equals("2")){
                        Snackbar snackbar = snackbarCreate("验证码错误！");
                        snackbar.show();
                    }else if(back.equals("3")){
                        Snackbar snackbar = snackbarCreate("用户名存在错误或密码错误！");
                        snackbar.show();
                    }else if(back.equals("1")){
                        EditText unameEdit = v2.findViewById(R.id.unameEdit);
                        Snackbar snackbar = snackbarCreate("登录成功！");
                        snackbar.show();
                        SQLiteDatabase db = new MyDataBaseHelper(MainActivity.this,Resources.DB_NAME,Resources.DB_VERSION).getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        cv.put("status","1");
                        db.update("userinfo",cv,"uname=?",new String[]{unameEdit.getText().toString()});
                        statusCheck();
                        uiUpdate();
                        vpager_one.setCurrentItem(0);
                        delPage(1);
                        delPage(1);
                        getAccount(tendcytype);
                    }
                }
            });
            v3.findViewById(R.id.registsButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String back = registCheck();

                    if(back.equals("0")){
                        Snackbar snackbar = snackbarCreate("用户名密码不可为空！");
                        snackbar.show();
                    }else if(back.equals("2")){
                        Snackbar snackbar = snackbarCreate("两次密码不一致！");
                        snackbar.show();
                    }else if(back.equals("3")){
                        Snackbar snackbar = snackbarCreate("该用户已存在！");
                        snackbar.show();
                    }else if(back.equals("1")){
                        ContentValues cv = new ContentValues();
                        EditText reunameEdit = v3.findViewById(R.id.reunameEdit);
                        EditText repasswordEdit = v3.findViewById(R.id.repasswordEdit);
                        String reuname = reunameEdit.getText().toString();
                        String repassword = repasswordEdit.getText().toString();
                        cv.put("uname",reuname);
                        cv.put("password",repassword);
                        cv.put("status","0");
                        initdatabase(cv);
                        vpager_one.setCurrentItem(1);

                    }
                }
            });

            v2.findViewById(R.id.backhome).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vpager_one.setCurrentItem(0);
                }
            });
            v3.findViewById(R.id.rebackhome).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vpager_one.setCurrentItem(0);
                }
            });
            v2.findViewById(R.id.registButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vpager_one.setCurrentItem(2);
                }
            });
            v3.findViewById(R.id.loginsButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vpager_one.setCurrentItem(1);
                }
            });
        }
    }
    public void initViewPagerAll(){
        if(uname.equals("0") || userid.equals("0")){
            v2 = li.inflate(R.layout.login_main,null);
            v3 = li.inflate(R.layout.regist_main,null);
                unameEdit = v2.findViewById(R.id.unameEdit);
                passwordEdit = v2.findViewById(R.id.passwordEdit);
                codeEdit = v2.findViewById(R.id.codeEdit);
                reunameEdit = v3.findViewById(R.id.reunameEdit);
                repasswordEdit = v3.findViewById(R.id.repasswordEdit);
                recovryEdit = v3.findViewById(R.id.recovryPassword);
                codeimg = v2.findViewById(R.id.codeimg);
                initcode();
                codeimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initcode();
                    }
                });

        }
    }
    public boolean checkstatus(){
        if(uname.equals("0") || userid.equals("0")){
            return false;
        }else {
            return true;
        }
    }
    public void initCreateOnclik(){
        v1.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean back = checkstatus();
                if(back == false){
                    Snackbar snackbar = snackbarCreate("需要登录账户才可使用！");
                    snackbar.show();
                }else {

                    startActivitys(MainActivity.this,AccountActivity.class);
                }
            }
        });
        initViewpagerOnclick();
        toolbar_canlda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowdate = new SimpleDateFormat(Resources.TIME_PATTERN).format(new Date());
                todate = nowdate.split("–");
                String selectday = toolbar_canlda.getText().toString();
                String[] toselectyear = selectday.split("–");
                DatePicker piker1 = new DatePicker(MainActivity.this);
                piker1.setDateRangeStart(2010,1,1);
                piker1.setDateRangeEnd(Integer.parseInt(todate[0]),Integer.parseInt(todate[1]),Integer.parseInt(todate[2]));
                piker1.setSelectedItem(Integer.valueOf(toselectyear[0]),Integer.valueOf(toselectyear[1]),Integer.valueOf(toselectyear[2]));
                piker1.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        toolbar_canlda.setText(year+"–"+month+"–"+day);
                        getAccount(tendcytype);
                    }
                });
                piker1.show();
            }
        });
        vpager_one.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    toolbar_canlda.setVisibility(View.VISIBLE);
                }else if(position == 1){
                    toolbar_canlda.setVisibility(View.INVISIBLE);
                }else if(position == 2){
                    toolbar_canlda.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public void startActivitys(Context context,Class c){
        Intent intent = new Intent(context,c);
        intent.putExtra("userid",userid);
        startActivity(intent);
    }
    //加载验证码
    public void initcode(){
        codeUtils = new CodeUtils();
        codeUtils = CodeUtils.getInstance();
        Bitmap bitmap = codeUtils.createBitmap();
        codeimg.setImageBitmap(bitmap);
    }

    //Snackbar创建
    public Snackbar snackbarCreate(String text){
        Snackbar snackbar = Snackbar.make(vpager_one,text,Snackbar.LENGTH_SHORT);
        snackbar.setAction("知道了", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        snackbar.getView().setBackgroundColor(Color.parseColor("#00b9f1"));
        snackbar.setActionTextColor(Color.parseColor("#f0f5f9"));
        return snackbar;
    }
    public void statusCheck(){
        SQLiteDatabase db = new MyDataBaseHelper(MainActivity.this,Resources.DB_NAME,Resources.DB_VERSION).getReadableDatabase();
        String sql = "select * from userinfo where status=?";
        Cursor cursor = db.rawQuery(sql,new String[]{"1"});
        if(cursor != null){
            while (cursor.moveToNext()){
                uname = cursor.getString(cursor.getColumnIndex("uname"));
                userid = String.valueOf(cursor.getInt(cursor.getColumnIndex("userid")));
            }
        }else{
            uname = "0";
            userid = "0";
        }
    }
    public void uiUpdate(){
        headertouxi = menuheader.findViewById(R.id.headertouxi);
        headertitle = menuheader.findViewById(R.id.headeruname);
        System.out.println(uname);
        System.out.print(userid);
        if(uname.equals("0") || userid.equals("0")){
            headertouxi.setBackgroundResource(R.mipmap.ic_launcher_round);
            headertitle.setText("游客");
            nv.getMenu().getItem(2).setTitle("登录");
            nv.getMenu().getItem(2).setIcon(R.drawable.login);
        }else {
            headertouxi.setBackgroundResource(R.mipmap.user);
            headertitle.setText(uname);
            nv.getMenu().getItem(2).setTitle("退出登录");
            nv.getMenu().getItem(2).setIcon(R.drawable.out);
        }
    }
    public String LoginCheck(){

        String uname = unameEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        String code = codeEdit.getText().toString();
        String codes = codeUtils.getCode();
        System.out.println(codes);
        SQLiteDatabase db = new MyDataBaseHelper(this,Resources.DB_NAME,Resources.DB_VERSION).getReadableDatabase();
        if(uname.equals("") || password.equals("") || code.equals("")){
            return "0";
        }
        if(!codes.equalsIgnoreCase(code)) {
            return "2";
        }
        String isuname = "";
        String ispassword = "";
        String sql = "select * from userinfo where uname=?";
        Cursor cursor = db.rawQuery(sql,new String[]{uname});

        String lll = String.valueOf(cursor.getCount());
        if (lll.equals("0")){
            return "3";
        }

        while (cursor.moveToNext()){
            try{
                isuname = cursor.getString(cursor.getColumnIndex("uname"));
                ispassword = cursor.getString(cursor.getColumnIndex("password"));
            }catch (Exception e){
                isuname = "";
                ispassword = "";
            }
            if(!isuname.equals(uname)){
                return "3";
            }else if(!ispassword.equals(password)){
                return "3";
            }
        }
        return "1";

    }
    public String registCheck(){

        String reuname = reunameEdit.getText().toString();
        String repassword = repasswordEdit.getText().toString();
        String recovrypass = recovryEdit.getText().toString();
        String isused ="";
        SQLiteDatabase db = new MyDataBaseHelper(this,Resources.DB_NAME,Resources.DB_VERSION).getReadableDatabase();
        Cursor cursor = db.query("userinfo",null,null,null,null,null,null,null);
        while(cursor.moveToNext()){
            try{
                isused = cursor.getString(cursor.getColumnIndex("uname"));
            }catch (Exception e){
                isused = "";
            }
            if(isused.equals(reuname)){
                return "3";
            }

        }
        if(reuname.equals("") || repassword.equals("") || recovrypass.equals("")){
            return "0";
        }else if(!repassword.equals(recovrypass)){
            return "2";
        }else{
            return "1";
        }

    }

    public void setViewPager(View v){
        aList.add(v);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
    public String[] getmonthAcount(){
        float zhichu = 0;
        float zhichu2 = 0;
        float shouru = 0;
        float shouru2 = 0;
        String sql = "select * from acrecords where month=? and userid=?";
        String day = toolbar_canlda.getText().toString();
        String[] ddd = day.split("–");
        Cursor cursor = refreshdatabase(userid,ddd[1],sql);
        while(cursor.moveToNext()){
            Integer isgets = cursor.getInt(cursor.getColumnIndex("isget"));
            if (isgets == 0){
                zhichu = cursor.getFloat(cursor.getColumnIndex("cost"));
                zhichu2 = zhichu2 + zhichu;
            }else{
                shouru = cursor.getFloat(cursor.getColumnIndex("cost"));
                shouru2 = shouru2 + shouru;
            }
        }
        String[] math = new String[4];
        math[0] = String.valueOf(shouru2-zhichu2);
        math[1] = String.valueOf(shouru2);
        math[2] = String.valueOf(zhichu2);

        return math;
    }
    public String[] getzhouAcount(){
        float zhichu = 0;
        float zhichu2 = 0;
        float shouru = 0;
        float shouru2 = 0;
        Date date = null;
        String sql = "select * from acrecords where userid = ? and weeks > ? and weeks < ?";

        SimpleDateFormat dateFormater = new SimpleDateFormat(
                Resources.TIME_PATTERN);
        Calendar cal = Calendar.getInstance();
        try {
            date = dateFormater.parse(toolbar_canlda.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, 1);
        cal.getTime();
        Long start_time = cal.getTime().getTime();

        cal.set(Calendar.DAY_OF_WEEK,
                cal.getActualMaximum(Calendar.DAY_OF_WEEK));
        Long end_time = cal.getTime().getTime();

        Cursor cursor = refreshdatabase2(userid,String.valueOf(start_time),String.valueOf(end_time),sql);
        while(cursor.moveToNext()){
            Integer isgets = cursor.getInt(cursor.getColumnIndex("isget"));
            if (isgets == 0){
                zhichu = cursor.getFloat(cursor.getColumnIndex("cost"));
                zhichu2 = zhichu2 + zhichu;
            }else{
                shouru = cursor.getFloat(cursor.getColumnIndex("cost"));
                shouru2 = shouru2 + shouru;
            }
        }
        String[] math = new String[4];
        math[0] = String.valueOf(shouru2-zhichu2);
        math[1] = String.valueOf(shouru2);
        math[2] = String.valueOf(zhichu2);

        return math;
    }
    public String[] getYearAcount(){
        float zhichu = 0;
        float zhichu2 = 0;
        float shouru = 0;
        float shouru2 = 0;
        String sql = "select * from acrecords where year=? and userid=?";
        String day = toolbar_canlda.getText().toString();
        String[] ddd = day.split("–");
        Cursor cursor = refreshdatabase(userid,ddd[0],sql);
        while(cursor.moveToNext()){
            Integer isgets = cursor.getInt(cursor.getColumnIndex("isget"));
            if (isgets == 0){
                zhichu = cursor.getFloat(cursor.getColumnIndex("cost"));
                zhichu2 = zhichu2 + zhichu;
            }else{
                shouru = cursor.getFloat(cursor.getColumnIndex("cost"));
                shouru2 = shouru2 + shouru;
            }
        }
        String[] math = new String[4];
        math[0] = String.valueOf(shouru2-zhichu2);
        math[1] = String.valueOf(shouru2);
        math[2] = String.valueOf(zhichu2);

        return math;
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            vpager_one.setCurrentItem(0);
        } else if (id == R.id.nav_tendcy) {
            boolean back = checkstatus();
            if(back == false ){
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("请登录软件查看分析！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create().show();
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("账本粗略统计")
                        .setMessage("本月余额：￥"+getmonthAcount()[0]+"\n"+"本月收入：￥"+getmonthAcount()[1]+"\n"+"本月支出：￥"+getmonthAcount()[2]+"\n"+"本年余额：￥"+getYearAcount()[0]+"\n"+"本年收入：￥"+getYearAcount()[1]+"\n"+"本年支出：￥"+getYearAcount()[2]+"\n"+"本周余额：￥"+getzhouAcount()[0] +"\n"+"本周收入：￥"+getzhouAcount()[1] +"\n"+"本周支出：￥"+getzhouAcount()[2])
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create().show();
            }

        } else if (id == R.id.nav_setting) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("时间仓促，尚未开发！")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.create().show();
        } else if (id == R.id.nav_login) {
            String title = nv.getMenu().getItem(2).getTitle().toString();
            if(title.equals("登录")){
                vpager_one.setCurrentItem(1);
            }else if(title.equals("退出登录")){
                SQLiteDatabase db = new MyDataBaseHelper(MainActivity.this,Resources.DB_NAME,Resources.DB_VERSION).getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("status","0");
                db.update("userinfo",cv,"uname=?",new String[]{uname});
                uname = "0";
                userid = "0";
                getAccount(tendcytype);
                statusCheck();
                uiUpdate();
                initViewPagerAll();
                initViewpagerOnclick();
                setViewPager(v2);
                setViewPager(v3);
                mAdapter.notifyDataSetChanged();
                vpager_one.setAdapter(mAdapter);
            }

        } else if (id == R.id.nav_about) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("关于软件")
                    .setMessage("谢昕成 制作")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.create().show();
        }else if(id == R.id.nav_tendcy0){
            tendcytype = 0;
            getAccount(tendcytype);
        }else if(id == R.id.nav_tendcy1){
            tendcytype = 1;
            getAccount(tendcytype);
        }else if(id == R.id.nav_tendcy2){
            tendcytype = 2;
            getAccount(tendcytype);
        }else if(id == R.id.nav_tendcy3){
            tendcytype = 3;
            getAccount(tendcytype);
        }
//        DrawerLayout constraintLayout = (DrawerLayout) findViewById(R.id.drawer_layout1);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

// 全透明状态栏
    protected void setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

// 半透明状态栏
    protected void setHalfTransparent() {

        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

//  设置filesystemwindow贴合
    private View contentViewGroup;

    protected void setFitSystemWindow(boolean fitSystemWindow) {
        if (contentViewGroup == null) {
            contentViewGroup = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        }
        contentViewGroup.setFitsSystemWindows(fitSystemWindow);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder key = new AlertDialog.Builder(this)
                    .setTitle("提示：")
                    .setMessage("确定要退出软件吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            key.show();
        }
        return false;
    }
    public String getType(String type,Integer isget){
        String sql="";
        String tp="";
        SQLiteDatabase db = new MyDataBaseHelper(MainActivity.this,Resources.DB_NAME,Resources.DB_VERSION).getReadableDatabase();
        if(isget == 0){
            sql = "select tname from actype_detail where id=?";
        }else{
            sql = "select tname from gettype_detail where id=?";
        }
        Cursor cursor = db.rawQuery(sql,new String[]{type});
        while(cursor.moveToNext()){
            tp = cursor.getString(cursor.getColumnIndex("tname"));
        }
        return tp;
    }
    public void getAccount(Integer tendcytype){
        if (tendcytype == 0){
            float zhichu = 0;
            float zhichu2 = 0;
            float shouru = 0;
            float shouru2 = 0;
            String days = toolbar_canlda.getText().toString();
            mlsit.clear();
            String sql = "select * from acrecords where time=? and userid=?";
            Cursor cursor = refreshdatabase(userid,days,sql);
            while (cursor.moveToNext()){
                Integer isgets = cursor.getInt(cursor.getColumnIndex("isget"));
                String type = String.valueOf(cursor.getInt(cursor.getColumnIndex("type")));
                String tname = getType(type,isgets);
                if (isgets == 0){
                    zhichu = cursor.getFloat(cursor.getColumnIndex("cost"));
                    zhichu2 = zhichu2 + zhichu;
                    initList(tname,String.valueOf(zhichu),isgets,cursor.getInt(cursor.getColumnIndex("id")));
                }else{
                    shouru = cursor.getFloat(cursor.getColumnIndex("cost"));
                    shouru2 = shouru2 + shouru;
                    initList(tname,String.valueOf(shouru),isgets,cursor.getInt(cursor.getColumnIndex("id")));
                }

            }
            accountyuers.setText("本天余额");
            header_zhichu.setText("本天支出￥"+String.valueOf(zhichu2));
            header_shouru.setText("本天收入￥"+String.valueOf(shouru2));
            header_yue.setText("￥"+String.valueOf(shouru2-zhichu2));
            String[] mmdd =days.split("–");
            accounttitles.setText(mmdd[1]+"月"+mmdd[2]+"日");
            accountrecy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            MainRecylerViewAdapter mainAdapter = new MainRecylerViewAdapter(mlsit);
            accountrecy.setItemAnimator(new DefaultItemAnimator());
            accountrecy.setAdapter(mainAdapter);

            if(cursor.getCount() == 0){
                notfound.setVisibility(View.VISIBLE);
                accountrecy.setVisibility(View.INVISIBLE);
                accounttitles.setVisibility(View.INVISIBLE);

            }else {
                notfound.setVisibility(View.INVISIBLE);
                accountrecy.setVisibility(View.VISIBLE);
                accounttitles.setVisibility(View.VISIBLE);
            }
        }else if(tendcytype == 1){
            float zhichu = 0;
            float zhichu2 = 0;
            float shouru = 0;
            float shouru2 = 0;
            String days = toolbar_canlda.getText().toString();
            Date date = null;
            String sql = "select * from acrecords where userid = ? and weeks > ? and weeks < ?";
            mlsit.clear();
            SimpleDateFormat dateFormater = new SimpleDateFormat(
                    Resources.TIME_PATTERN);
            Calendar cal = Calendar.getInstance();
            try {
                date = dateFormater.parse(days);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cal.setTime(date);
            int week = cal.get(Calendar.WEEK_OF_MONTH);
            cal.set(Calendar.DAY_OF_WEEK, 1);
            cal.getTime();
            Long start_time = cal.getTime().getTime();

            cal.set(Calendar.DAY_OF_WEEK,
                    cal.getActualMaximum(Calendar.DAY_OF_WEEK));
            Long end_time = cal.getTime().getTime();

            Cursor cursor = refreshdatabase2(userid,String.valueOf(start_time),String.valueOf(end_time),sql);
            while(cursor.moveToNext()){
                Integer isgets = cursor.getInt(cursor.getColumnIndex("isget"));
                String type = String.valueOf(cursor.getInt(cursor.getColumnIndex("type")));
                String tname = getType(type,isgets);
                if (isgets == 0){
                    zhichu = cursor.getFloat(cursor.getColumnIndex("cost"));
                    zhichu2 = zhichu2 + zhichu;
                    initList(cursor.getInt(cursor.getColumnIndex("month"))+"月"+cursor.getInt(cursor.getColumnIndex("day"))+"日"+" "+tname,String.valueOf(zhichu),isgets,cursor.getInt(cursor.getColumnIndex("id")));
                }else{
                    shouru = cursor.getFloat(cursor.getColumnIndex("cost"));
                    shouru2 = shouru2 + shouru;
                    initList(cursor.getInt(cursor.getColumnIndex("month"))+"月"+cursor.getInt(cursor.getColumnIndex("day"))+"日"+" "+tname,String.valueOf(shouru),isgets,cursor.getInt(cursor.getColumnIndex("id")));
                }
            }
            accountyuers.setText("本周余额");
            String[] mmdd =days.split("–");
            accounttitles.setText(mmdd[0]+"年"+mmdd[1]+"月"+"第"+week+"周");
            header_zhichu.setText("本周支出￥"+String.valueOf(zhichu2));
            header_shouru.setText("本周收入￥"+String.valueOf(shouru2));
            header_yue.setText("￥"+String.valueOf(shouru2-zhichu2));
            accountrecy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            MainRecylerViewAdapter mainAdapter = new MainRecylerViewAdapter(mlsit);
            accountrecy.setItemAnimator(new DefaultItemAnimator());
            accountrecy.setAdapter(mainAdapter);
            if(cursor.getCount() == 0){
                notfound.setVisibility(View.VISIBLE);
                accountrecy.setVisibility(View.INVISIBLE);
                accounttitles.setVisibility(View.INVISIBLE);

            }else {
                notfound.setVisibility(View.INVISIBLE);
                accountrecy.setVisibility(View.VISIBLE);
                accounttitles.setVisibility(View.VISIBLE);
            }
        }else if(tendcytype == 2){
            float zhichu = 0;
            float zhichu2 = 0;
            float shouru = 0;
            float shouru2 = 0;
            String sql = "select * from acrecords where month=? and userid=?";
            String day = toolbar_canlda.getText().toString();
            mlsit.clear();
            String[] ddd = day.split("–");
            Cursor cursor = refreshdatabase(userid,ddd[1],sql);
            while(cursor.moveToNext()){
                Integer isgets = cursor.getInt(cursor.getColumnIndex("isget"));
                String type = String.valueOf(cursor.getInt(cursor.getColumnIndex("type")));
                String tname = getType(type,isgets);
                if (isgets == 0){
                    zhichu = cursor.getFloat(cursor.getColumnIndex("cost"));
                    zhichu2 = zhichu2 + zhichu;
                    initList(cursor.getInt(cursor.getColumnIndex("month"))+"月"+cursor.getInt(cursor.getColumnIndex("day"))+"日"+" "+tname,String.valueOf(zhichu),isgets,cursor.getInt(cursor.getColumnIndex("id")));
                }else{
                    shouru = cursor.getFloat(cursor.getColumnIndex("cost"));
                    shouru2 = shouru2 + shouru;
                    initList(cursor.getInt(cursor.getColumnIndex("month"))+"月"+cursor.getInt(cursor.getColumnIndex("day"))+"日"+" "+tname,String.valueOf(shouru),isgets,cursor.getInt(cursor.getColumnIndex("id")));
                }

            }
            accountyuers.setText("本月余额");
            String[] mmdd =day.split("–");
            accounttitles.setText(mmdd[0]+"年"+mmdd[1]+"月");
            header_zhichu.setText("本月支出￥"+String.valueOf(zhichu2));
            header_shouru.setText("本月收入￥"+String.valueOf(shouru2));
            header_yue.setText("￥"+String.valueOf(shouru2-zhichu2));
            accountrecy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            MainRecylerViewAdapter mainAdapter = new MainRecylerViewAdapter(mlsit);
            accountrecy.setItemAnimator(new DefaultItemAnimator());
            accountrecy.setAdapter(mainAdapter);
            if(cursor.getCount() == 0){
                notfound.setVisibility(View.VISIBLE);
                accountrecy.setVisibility(View.INVISIBLE);
                accounttitles.setVisibility(View.INVISIBLE);

            }else {
                notfound.setVisibility(View.INVISIBLE);
                accountrecy.setVisibility(View.VISIBLE);
                accounttitles.setVisibility(View.VISIBLE);
            }
        }else if(tendcytype == 3){
            float zhichu = 0;
            float zhichu2 = 0;
            float shouru = 0;
            float shouru2 = 0;
            mlsit.clear();
            String sql = "select * from acrecords where year=? and userid=?";
            String day = toolbar_canlda.getText().toString();
            String[] ddd = day.split("–");
            Cursor cursor = refreshdatabase(userid,ddd[0],sql);
            while(cursor.moveToNext()){
                Integer isgets = cursor.getInt(cursor.getColumnIndex("isget"));
                String type = String.valueOf(cursor.getInt(cursor.getColumnIndex("type")));
                String tname = getType(type,isgets);
                if (isgets == 0){
                    zhichu = cursor.getFloat(cursor.getColumnIndex("cost"));
                    zhichu2 = zhichu2 + zhichu;
                    initList(cursor.getInt(cursor.getColumnIndex("month"))+"月"+cursor.getInt(cursor.getColumnIndex("day"))+"日"+" "+tname,String.valueOf(zhichu),isgets,cursor.getInt(cursor.getColumnIndex("id")));
                }else{
                    shouru = cursor.getFloat(cursor.getColumnIndex("cost"));
                    shouru2 = shouru2 + shouru;
                    initList(cursor.getInt(cursor.getColumnIndex("month"))+"月"+cursor.getInt(cursor.getColumnIndex("day"))+"日"+" "+tname,String.valueOf(shouru),isgets,cursor.getInt(cursor.getColumnIndex("id")));
                }
            }
            accountyuers.setText("本年余额");
            String[] mmdd =day.split("–");
            accounttitles.setText(mmdd[0]+"年");
            header_zhichu.setText("本年支出￥"+String.valueOf(zhichu2));
            header_shouru.setText("本年收入￥"+String.valueOf(shouru2));
            header_yue.setText("￥"+String.valueOf(shouru2-zhichu2));
            accountrecy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            MainRecylerViewAdapter mainAdapter = new MainRecylerViewAdapter(mlsit);
            accountrecy.setItemAnimator(new DefaultItemAnimator());
            accountrecy.setAdapter(mainAdapter);
            if(cursor.getCount() == 0){
                notfound.setVisibility(View.VISIBLE);
                accountrecy.setVisibility(View.INVISIBLE);
                accounttitles.setVisibility(View.INVISIBLE);

            }else {
                notfound.setVisibility(View.INVISIBLE);
                accountrecy.setVisibility(View.VISIBLE);
                accounttitles.setVisibility(View.VISIBLE);
            }
        }

    }
    public Cursor refreshdatabase(String userid,String time,String sql){
        helper = new MyDataBaseHelper(this,Resources.DB_NAME,Resources.DB_VERSION).getReadableDatabase();
        Cursor cursor = helper.rawQuery(sql,new String[]{time,userid});
        return cursor;
    }
    public Cursor refreshdatabase2(String userid,String weeks_start,String weeks_end,String sql){
        helper = new MyDataBaseHelper(this,Resources.DB_NAME,Resources.DB_VERSION).getReadableDatabase();
        Cursor cursor = helper.rawQuery(sql,new String[]{userid,weeks_start,weeks_end});
        return cursor;
    }
    private void initdatabase(ContentValues cv){
        SQLiteDatabase db = new MyDataBaseHelper(this, Resources.DB_NAME,Resources.DB_VERSION).getWritableDatabase();
        db.insert("userinfo",null,cv);
        Snackbar snackbar = snackbarCreate("注册成功！");
        snackbar.show();
    }
    public void delPage(int position){
        aList.remove(position);
        vpager_one.removeView(v2);
        vpager_one.removeView(v3);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        getAccount(tendcytype);
        super.onResume();
    }
    public void initList(String tname,String moneytitle,Integer isoutcircle,Integer idss){
        AccountType accountType = new AccountType();
        accountType.setTname(tname);
        accountType.setMoneytitle(moneytitle);
        accountType.setIsoutcicle(isoutcircle);
        accountType.setTypeids(idss);
        mlsit.add(accountType);
    }
    public void getAccountss(View v){
        TextView one = v.findViewById(R.id.doing);
        TextView two = v.findViewById(R.id.expense);
        final TextView three = v.findViewById(R.id.typeids);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("账单详情")
                .setMessage("账单类型："+one.getText().toString() + "\n" + "账单花费："+two.getText().toString() +"\n"+"账单日期："+accounttitles.getText().toString())
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getAccount(tendcytype);
                    }
                })
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase db = new MyDataBaseHelper(MainActivity.this,Resources.DB_NAME,Resources.DB_VERSION).getWritableDatabase();
                        db.delete("acrecords", "id = ?", new String[] {three.getText().toString()});
                        Toast.makeText(MainActivity.this,"删除成功！",Toast.LENGTH_SHORT).show();
                        getAccount(tendcytype);
                    }
                });
        builder.show();
    }
}
