package com.mystrey.magicaccount.entity;

import java.util.Date;
/*
  没时间写的封装
 */
public class Account {
    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getAtype() {
        return Atype;
    }

    public void setAtype(String atype) {
        Atype = atype;
    }

    public String getStype() {
        return Stype;
    }

    public void setStype(String stype) {
        Stype = stype;
    }

    public String getMoneys() {
        return Moneys;
    }

    public void setMoneys(String moneys) {
        Moneys = moneys;
    }

    public String getIsOut() {
        return isOut;
    }

    public void setIsOut(String isOut) {
        this.isOut = isOut;
    }

    public Date getTime() {
        return Time;
    }

    public void setTime(Date time) {
        Time = time;
    }

    private Integer aid;
    private Integer userid;
    private String Atype;
    private String Stype;
    private String Moneys;
    private String isOut;
    private Date Time;

}
