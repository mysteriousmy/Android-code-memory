package com.mystrey.magicaccount.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    public MyDataBaseHelper(Context context, String dbname, int version) {
        super(context, dbname, null, version);
    }

    /**
     * actype  记账的 大类
     * actype_detail  记账的 小类
     * acrecords   具体的目录
     *
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 在此处准备数据库的初始化创建
        String tb_accountType = "create table if not exists actype (id integer, tname varchar(20) not null)";
        String tb_accountType2 = "create table if not exists gettype (id integer, tname varchar(20) not null)";
        String tb_accountTypeDetail = "create table if not exists actype_detail (id integer primary key, tname varchar(20) not null, tid integer not null, url varchar(500))";
        String tb_accountTypeDetail2 = "create table if not exists gettype_detail (id integer primary key, tname varchar(20) not null, tid integer not null,  url varchar(500))";
        String tb_records =  "create table if not exists acrecords (id integer primary key, userid integer, year integer, month  integer, day integer , weeks long, cost float, time text , type integer , isget integer)";
        String tb_userinfo = "create table if not exists userinfo(userid integer primary key, uname varchar(16), password varchar(16), status text)";


        sqLiteDatabase.execSQL(tb_accountType);
        sqLiteDatabase.execSQL(tb_accountType2);
        sqLiteDatabase.execSQL(tb_accountTypeDetail);
        sqLiteDatabase.execSQL(tb_accountTypeDetail2);
        sqLiteDatabase.execSQL(tb_records);
        sqLiteDatabase.execSQL(tb_userinfo);

        String[] types = {"食品酒水","购物消费","行车交通","休闲娱乐","其他"};
        String[][] son = {{"早餐","中餐","晚餐","零食"},{"衣裤鞋帽","洗护用品","厨房用品"},{"打车","交通充值","加油"},{"看电影","KTV","出门游玩"},{"坏账烂账","钱丢了"}};
        String[][] url = {{"http://qianjires.xxoojoke.com/ic_cate2_zaocan.png","http://qianjires.xxoojoke.com/ic_cate2_wucan.png","http://qianjires.xxoojoke.com/ic_cate2_wancan.png","http://qianjires.xxoojoke.com/cateic_lingshi.png"},
                {"http://qianjires.xxoojoke.com/cateic_yifu.png","http://qianjires.xxoojoke.com/cateic_meizhuang.png","http://qianjires.xxoojoke.com/cateic_riyongpin.png"},
                {"http://qianjires.xxoojoke.com/ic_cate2_dache.png","http://qianjires.xxoojoke.com/cateic_gongzi.png","http://qianjires.xxoojoke.com/cateic_jiayou.png"},
                {"http://qianjires.xxoojoke.com/cateic_yule.png","http://qianjires.xxoojoke.com/cateic_yule.png","http://qianjires.xxoojoke.com/cateic_lvxing.png"},
                {"http://qianjires.xxoojoke.com/cateic_other.png","http://qianjires.xxoojoke.com/cateic_other.png"}};
        String[] types2 = {"工作单位","外快","二手收益","转账收益","其他"};
        String[][] son2 = {{"工资","奖金","提成","网店收益"},{"接单","搬砖","其它兼职"},{"闲鱼","转转","其它二手市场"},{"抢红包","零花钱","压岁钱"},{"公积金","退款","股票","理财收益"}};
        String[][] url2 = {{"http://qianjires.xxoojoke.com/cateic_gongzi.png","http://qianjires.xxoojoke.com/cateic_gongzi.png","http://qianjires.xxoojoke.com/ic_cate2_ticheng.png","http://qianjires.xxoojoke.com/cateic_gongzi.png"},
                {"http://qianjires.xxoojoke.com/cateic_waikuai.png","http://qianjires.xxoojoke.com/cateic_waikuai.png","http://qianjires.xxoojoke.com/cateic_waikuai.png"},
                {"http://qianjires.xxoojoke.com/ic_cate2_ticheng.png","http://qianjires.xxoojoke.com/ic_cate2_ticheng.png","http://qianjires.xxoojoke.com/ic_cate2_ticheng.png"},
                {"http://qianjires.xxoojoke.com/ic_cate2_shouhongbao.png","http://qianjires.xxoojoke.com/ic_cate2_linghuaqian.png","http://qianjires.xxoojoke.com/cateic_hongbao.png"},
                {"http://qianjires.xxoojoke.com/ic_cate2_gongjijin.png","http://qianjires.xxoojoke.com/ic_cate2_tuikuan.png","http://qianjires.xxoojoke.com/cateic_gupiao.png","http://qianjires.xxoojoke.com/ic_cate2_licai.png"}};
        int i = 0;
        int m = 0;
        for (String every: types) {
            String sql = "insert into actype('id','tname') values(?,?)";
            sqLiteDatabase.execSQL(sql, new Object[]{i+1,every});

            for(String inner : son[i]){
                String sqln = "insert into actype_detail('tname','tid','url') values(?,?,?)";
                sqLiteDatabase.execSQL(sqln, new Object[]{inner, i+1,url[i]});
            }
            for (String urls : url[i]){
                m++;
                String sqls1 = "update actype_detail set url=? where id=?";
                sqLiteDatabase.execSQL(sqls1,new Object[]{urls,m});
            }
            i++;
        }
        int j = 0;
        int b = 0;
        for(String every : types2){
            String sql = "insert into gettype('id','tname') values(?,?)";
            sqLiteDatabase.execSQL(sql,new Object[]{j+1,every});

            for(String inner : son2[j]){
                String sqln = "insert into gettype_detail('tname','tid','url') values(?,?,?)";
                sqLiteDatabase.execSQL(sqln,new Object[]{inner,j+1,url2[j]});
            }
            for (String urls : url2[j]){
                b++;
                String sqls2 = "update gettype_detail set url=? where id=?";
                sqLiteDatabase.execSQL(sqls2,new Object[]{urls,b});
            }
            j++;
        }

        Log.d("数据库准备状态：","插入完成++++++++++");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql1 = "drop table if exists actype";
        String sql2 = "drop table if exists actype_detail";
        String sql3 = "drop table if exists acrecords";

        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.execSQL(sql2);
        sqLiteDatabase.execSQL(sql3);

        onCreate(sqLiteDatabase);

    }
}
