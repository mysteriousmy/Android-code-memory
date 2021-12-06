package com.mystrey.magicaccount.entity;
/*
  没时间写的封装
 */
public class Userinfo {
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTouxi() {
        return touxi;
    }

    public void setTouxi(String touxi) {
        this.touxi = touxi;
    }

    private Integer userid;
    private String uname;
    private String password;
    private String touxi;

    public String getMoney() {
        return Money;
    }

    public void setMoney(String money) {
        Money = money;
    }

    private String Money;

    public String getRetime() {
        return retime;
    }

    public void setRetime(String retime) {
        this.retime = retime;
    }

    private String retime;
}
