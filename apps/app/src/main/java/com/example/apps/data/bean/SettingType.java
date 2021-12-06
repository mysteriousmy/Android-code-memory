package com.example.apps.data.bean;


public class SettingType {
    private Integer imgRe;
    private String title;
    public Integer getImgRe() {
        return imgRe;
    }

    public void setImgRe(Integer imgRe) {
        this.imgRe = imgRe;
    }


    public SettingType(Integer imgRe, String title) {
        this.imgRe = imgRe;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
