package com.mystrey.magicaccount.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mystrey.magicaccount.helper.MyDataBaseHelper;
import com.mystrey.magicaccount.myview.MyImageView;
import com.mystrey.magicaccount.adapter.MyPagerApdater;
import com.mystrey.magicaccount.R;
import com.mystrey.magicaccount.entity.Resources;
import com.mystrey.magicaccount.adapter.AddRecordRecyclerViewAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.picker.DatePicker;


public class AccountActivity extends AppCompatActivity {
    private View v1;
    private View v2;
    private ArrayList<View> alist;
    private MyPagerApdater mAdapter;
    private RecyclerView mRecy;
    private RecyclerView mRecy2;
    private EditText inputMoney;
    private Button saveButton;
    private Button getCanlada;
    private String todate[];
    private String nowdate;
    private AddRecordRecyclerViewAdapter myRecyAdapter;
    private AddRecordRecyclerViewAdapter myRecyAdapter2;
    private ImageView huakuai;
    private DisplayMetrics dm;
    private TextView m;
    private Integer widthPixels;
    private ViewPager pager;
    private MyImageView accountimg;
    int isGet = 0;
    int IS_TITLE_OR_NOT =1;
    int MESSAGE = 2;
    private Integer selectid;
    String userid = "0";
    Map<Integer, String> map = new HashMap<Integer, String>();
    private LayoutInflater li;
    List<Map<Integer, String>> list = new ArrayList<>();
    List<Map<Integer, String>> list2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Intent intent=getIntent();
        userid=intent.getStringExtra("userid");
        initList();
        initlist2();
        m = findViewById(R.id.selecttype);
        huakuai = findViewById(R.id.huakuai);
        pager = findViewById(R.id.accountPager);
        saveButton = findViewById(R.id.savabutton);
        inputMoney = findViewById(R.id.inputMoney);
        getCanlada = findViewById(R.id.getCalanda);

        initCreateOnclik();
        //取屏幕像素
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        widthPixels= dm.widthPixels;

        li = getLayoutInflater().from(this);
        initViewpager();

    }
    private void initdatabase(ContentValues cv){
        SQLiteDatabase db = new MyDataBaseHelper(this, Resources.DB_NAME,Resources.DB_VERSION).getWritableDatabase();
        db.insert("acrecords",null,cv);
        Toast.makeText(this,"保存成功！",Toast.LENGTH_SHORT).show();
    }
    private void initList(){
        int one=0,two=0,three=0,four=0,five=0;
        SQLiteDatabase db = new MyDataBaseHelper(this,Resources.DB_NAME,Resources.DB_VERSION).getReadableDatabase();
        Cursor cursor = db.query("actype", null, null, null, null, null, null);
        Cursor cursor1 = db.query("actype_detail",null,null,null,null,null,null);
        Cursor cursor2 = db.query("actype_detail",new String[]{"tid"},null,null,null,null,null);


        while(cursor2.moveToNext()){

            if(cursor2.getInt(cursor2.getColumnIndex("tid")) == 1){
                one = one+1;
            }else if(cursor2.getInt(cursor2.getColumnIndex("tid")) == 2){
                two = two+1;
            }else if(cursor2.getInt(cursor2.getColumnIndex("tid")) == 3){
                three = three +1;
            }else if(cursor2.getInt(cursor2.getColumnIndex("tid")) == 4){
                four = four +1;
            }else if(cursor2.getInt(cursor2.getColumnIndex("tid")) == 5){
                five = five+1;
            }
        }
        System.out.println(one);
        while (cursor1.moveToNext()){
            map = new HashMap<Integer, String>();
//            MyImageView myImageView = findViewById(R.id.onepage_ticon);
//            myImageView.setImageURL("http://qianjires.xxoojoke.com/cateic_yinshi.png");
            map.put(IS_TITLE_OR_NOT , "false");
            map.put(MESSAGE , cursor1.getString(cursor1.getColumnIndex("tname"))+"~"+cursor1.getString(cursor1.getColumnIndex("url")));
            list.add(map);
        }
        while (cursor.moveToNext()){
            map = new HashMap<Integer, String>();
            map.put(IS_TITLE_OR_NOT , "true");
            map.put(MESSAGE , cursor.getString(cursor.getColumnIndex("tname")));
            if(cursor.getInt(cursor.getColumnIndex("id")) == 1){
                list.add(0,map);
            }else if(cursor.getInt(cursor.getColumnIndex("id")) == 2){
                list.add(one+1,map);
            }else if(cursor.getInt(cursor.getColumnIndex("id")) == 3){
                list.add(one + two+2,map);
            }else if(cursor.getInt(cursor.getColumnIndex("id")) == 4){
                list.add(one+two+three+3,map);
            }else if(cursor.getInt(cursor.getColumnIndex("id")) == 5){
                list.add(one+two+three+four+4,map);
            }

        }
    }
    public void initlist2(){
        int one=0,two=0,three=0,four=0,five=0;
        SQLiteDatabase db = new MyDataBaseHelper(this,Resources.DB_NAME,Resources.DB_VERSION).getReadableDatabase();
        Cursor cursor = db.query("gettype", null, null, null, null, null, null);
        Cursor cursor1 = db.query("gettype_detail",null,null,null,null,null,null);
        Cursor cursor2 = db.query("gettype_detail",new String[]{"tid"},null,null,null,null,null);
        while(cursor2.moveToNext()){
            if(cursor2.getInt(cursor2.getColumnIndex("tid")) == 1){
                one = one+1;
            }else if(cursor2.getInt(cursor2.getColumnIndex("tid")) == 2){
                two = two+1;
            }else if(cursor2.getInt(cursor2.getColumnIndex("tid")) == 3){
                three = three +1;
            }else if(cursor2.getInt(cursor2.getColumnIndex("tid")) == 4){
                four = four +1;
            }else if(cursor2.getInt(cursor2.getColumnIndex("tid")) == 5){
                five = five+1;
            }
        }
        System.out.println(one);
        while (cursor1.moveToNext()){
            map = new HashMap<Integer, String>();
//            MyImageView myImageView = findViewById(R.id.onepage_ticon);
//            myImageView.setImageURL("http://qianjires.xxoojoke.com/cateic_yinshi.png");
            map.put(IS_TITLE_OR_NOT , "false");
            map.put(MESSAGE , cursor1.getString(cursor1.getColumnIndex("tname"))+"~"+cursor1.getString(cursor1.getColumnIndex("url")));
            list2.add(map);
        }
        while (cursor.moveToNext()){
            map = new HashMap<Integer, String>();
            map.put(IS_TITLE_OR_NOT , "true");
            map.put(MESSAGE , cursor.getString(cursor.getColumnIndex("tname")));
            if(cursor.getInt(cursor.getColumnIndex("id")) == 1){
                list2.add(0,map);
            }else if(cursor.getInt(cursor.getColumnIndex("id")) == 2){
                list2.add(one+1,map);
            }else if(cursor.getInt(cursor.getColumnIndex("id")) == 3){
                list2.add(one + two+2,map);
            }else if(cursor.getInt(cursor.getColumnIndex("id")) == 4){
                list2.add(one+two+three+3,map);
            }else if(cursor.getInt(cursor.getColumnIndex("id")) == 5){
                list2.add(one+two+three+four+4,map);
            }

        }
    }
    private void initViewpager(){
        v1 = li.inflate(R.layout.account_onepager,null);
        v2 = li.inflate(R.layout.account_twopager,null);
        mRecy = v1.findViewById(R.id.onepageRey);
        mRecy2 = v2.findViewById(R.id.twopageRey);
//        mRecy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecy.setLayoutManager(new GridLayoutManager(this, 5, LinearLayoutManager.VERTICAL, false));
        mRecy2.setLayoutManager(new GridLayoutManager(this, 5, LinearLayoutManager.VERTICAL, false));
        myRecyAdapter = new AddRecordRecyclerViewAdapter(this,list,5);
        myRecyAdapter2 = new AddRecordRecyclerViewAdapter(this,list2,5);
        mRecy.setItemAnimator(new DefaultItemAnimator());
        mRecy2.setItemAnimator(new DefaultItemAnimator());
        mRecy.setAdapter(myRecyAdapter);
        mRecy2.setAdapter(myRecyAdapter2);
        alist = new ArrayList<View>();
        alist.add(v1);
        alist.add(v2);
        mAdapter = new MyPagerApdater(alist);
        pager.setAdapter(mAdapter);
    }
    private void initCreateOnclik(){
        findViewById(R.id.outmoney).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(0);
            }
        });
        findViewById(R.id.getmoney).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(1);
            }
        });
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 1){
                    huakuai.setTranslationX((widthPixels-1)/10);
                }else {
                    huakuai.setTranslationX(positionOffsetPixels/10);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if(position == 1){
                    saveButton.setBackgroundColor(Color.parseColor("#67D5B5"));
                    inputMoney.setTextColor(Color.parseColor("#67D5B5"));
                    inputMoney.setHintTextColor(Color.parseColor("#67D5B5"));
                    isGet = 1;
                    m.setText("选择账单类型：");
                }else if(position == 0){
                    saveButton.setBackgroundColor(Color.parseColor("#df405a"));
                    inputMoney.setTextColor(Color.parseColor("#df405a"));
                    inputMoney.setHintTextColor(Color.parseColor("#df405a"));
                    isGet = 0;
                    m.setText("选择账单类型：");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputMoney.getText().equals("") || getCanlada.getText().equals("选择日期") || m.getText().equals("选择账目类型：")){
                    Snackbar snackbar = snackbarCreate("请输入金额、选择日期以及选择账单类型！");
                    snackbar.show();

                }else if (Float.valueOf(String.valueOf(inputMoney.getText())) == 0f){
                    Snackbar snackbar = snackbarCreate("金额不可为0！");
                    snackbar.show();
                }else{
                    ContentValues cv = new ContentValues();
                    String p = (String) getCanlada.getText();
                    String[]c = p.split("–");
                    float cost = Float.valueOf(String.valueOf(inputMoney.getText()));
                    SimpleDateFormat dateFormater = new SimpleDateFormat(
                            Resources.TIME_PATTERN);
                    Calendar cal= Calendar.getInstance();
                    Date date = null;
                    try {
                        date = dateFormater.parse(p);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    cal.setTime(date);
                    cv.put("year",c[0]);
                    cv.put("userid",Integer.valueOf(userid));
                    cv.put("month",c[1]);
                    cv.put("day",c[2]);
                    cv.put("weeks",cal.getTime().getTime());
                    cv.put("cost",cost);
                    cv.put("time",c[0]+"–"+c[1]+"–"+c[2]);
                    cv.put("type",selectid);
                    cv.put("isget",isGet);
                    initdatabase(cv);
                }
            }
        });

        getCanlada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowdate = new SimpleDateFormat(Resources.TIME_PATTERN).format(new Date());
                todate = nowdate.split("–");
//                DateTimePicker picker = new DateTimePicker(AccountActivity.this, DateTimePicker.YEAR_MONTH_DAY);//24小时值
                DatePicker piker1 = new DatePicker(AccountActivity.this);
                piker1.setDateRangeStart(2010,1,1);
                piker1.setDateRangeEnd(Integer.parseInt(todate[0]),Integer.parseInt(todate[1]),Integer.parseInt(todate[2]));
                if(getCanlada.getText().toString().equals("选择日期")){
                    piker1.setSelectedItem(Integer.parseInt(todate[0]),Integer.parseInt(todate[1]),Integer.parseInt(todate[2]));
                }else {
                    String now = getCanlada.getText().toString();
                    String[] select = now.split("–");
                    piker1.setSelectedItem(Integer.parseInt(select[0]),Integer.parseInt(select[1]),Integer.parseInt(select[2]));
                }

//                picker.setDateRangeStart(2010, 1, 1);//日期起点
//                picker.setDateRangeEnd(2018, 12,30);//日期终点
//                picker.setTimeRangeStart(0, 0);//时间范围起点
//                picker.setTimeRangeEnd(23, 59);//时间范围终点
//                picker.setCycleDisable(false);
                piker1.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        getCanlada.setText(year+"–"+month+"–"+day);
                    }
                });
                piker1.show();
//                picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
//                    @Override
//                    public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
//                        //year:年，month:月，day:日，hour:时，minute:分
//                        Toast.makeText(getApplicationContext(), year + "-" + month + "-" + day + " "
//                                + hour + ":" + minute, Toast.LENGTH_LONG).show();
//                        getCanlada.setText(year+"-"+month+"-"+day+" "+hour+":"+minute);
//                    }
//                });
//                picker.show();
            }
        });
    }
//    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
//        private int space;
//
//        public SpaceItemDecoration(int space) {
//            this.space = space;
//        }
//
//        @Override
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            //分别设置item的间距
//            if (parent.getChildViewHolder(view).getItemViewType() == 0) {
//                outRect.bottom = 0;
//                outRect.top = space / 2;
//            } else {
//                outRect.bottom = space;
//                outRect.top = space;
//            }
//            outRect.right = space;
//            outRect.left = space;
//
//        }
//    }

    public void getAccountType(View v){
        TextView c = v.findViewById(R.id.onepage_tname);
        m.setText("选择账单类型："+c.getText());
        SQLiteDatabase db = new MyDataBaseHelper(this, Resources.DB_NAME,Resources.DB_VERSION).getReadableDatabase();
        if(isGet == 1){
            String sql = "select id from gettype_detail where tname=?";
            Cursor cursor =db.rawQuery(sql,new String[]{String.valueOf(c.getText())});
            while (cursor.moveToNext()){
                selectid = cursor.getInt(cursor.getColumnIndex("id"));
            }
        }else if(isGet == 0){
            String sql = "select id from actype_detail where tname=?";
            Cursor cursor =db.rawQuery(sql,new String[]{String.valueOf(c.getText())});
            while (cursor.moveToNext()){
                selectid = cursor.getInt(cursor.getColumnIndex("id"));
            }
        }
    }
    public Snackbar snackbarCreate(String text){
        Snackbar snackbar = Snackbar.make(pager,text,Snackbar.LENGTH_SHORT);
        snackbar.setAction("知道了", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        snackbar.getView().setBackgroundColor(Color.parseColor("#00b9f1"));
        snackbar.setActionTextColor(Color.parseColor("#f0f5f9"));
        return snackbar;
    }
}
