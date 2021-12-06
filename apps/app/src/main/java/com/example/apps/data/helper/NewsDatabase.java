package com.example.apps.data.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NewsDatabase extends SQLiteOpenHelper {
    public NewsDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String user_db ="create table if not exists userInfo(userid integer primary key AUTOINCREMENT, uname varchar(20), nickname varchar(20), usex integer, password varchar(20), signtext varchar(20), headimg text, status integer)";
        String collect_newslist = "create table if not exists newsList(id integer primary key, userid integer, type integer, newsName varchar(255), img1 text, img2 text, img3 text, newsUrl text)";
        sqLiteDatabase.execSQL(user_db);
        sqLiteDatabase.execSQL(collect_newslist);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
