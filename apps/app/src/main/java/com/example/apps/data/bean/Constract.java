package com.example.apps.data.bean;

public class Constract {
    public static final String REQUEST_URL = "http://192.168.6.48:8080/top/";
    public static final String REQUEST_FILE_AD = "getAdData";
    public static final String REQUEST_FILE_NEWS = "getNewsData";
    public static final String DB_BANE = "news.db";
    public static final String GET_USER_INFO_BY_USERNAME = "select * from userInfo where uname = ?";
    public static final String GET_LOGIN_USER = "select * from userInfo where status = ?";
    public static final String GET_LOGIN_USER_Collect = "select * from newsList where id = ? and userid = ?";
    public static final String GET_ALLCOLLECT_USER = "select * from newsList where userid = ?";
    public static final String INSERT_USER = "insert into userInfo (uname, password, status) values (?, ?, ?)";
    public static final int EDIT_HEAD = 2131230845;
    public static final int EDIT_UNAME = 2131230851;
    public static final int EDIT_NICKNAME = 2131230846;
    public static final int EDIT_SEX = 2131230848;
    public static final int EDIT_SIGN = 2131230849;
}
