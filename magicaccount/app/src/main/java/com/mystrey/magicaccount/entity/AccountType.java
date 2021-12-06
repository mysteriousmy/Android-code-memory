package com.mystrey.magicaccount.entity;

public class AccountType {
    private String tname;

    public Integer getTypeids() {
        return typeids;
    }

    public void setTypeids(Integer typeids) {
        this.typeids = typeids;
    }

    private Integer typeids;
    public String getMoneytitle() {
        return moneytitle;
    }

    public void setMoneytitle(String moneytitle) {
        this.moneytitle = moneytitle;
    }

    private String moneytitle;

    public Integer getIsoutcicle() {
        return isoutcicle;
    }

    public void setIsoutcicle(Integer isoutcicle) {
        this.isoutcicle = isoutcicle;
    }

    private Integer isoutcicle;

    public AccountType(String tname, String moneytitle) {
        this.tname = tname;
        this.moneytitle = moneytitle;
    }
    public AccountType(){

    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }


}
