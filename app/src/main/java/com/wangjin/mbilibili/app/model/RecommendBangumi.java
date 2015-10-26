package com.wangjin.mbilibili.app.model;

/**
 * Created by wangjin on 15/10/24.
 */
public class RecommendBangumi {
    private String title;
    private int spid;
    private String cover;
    private int img_width;
    private int img_heigth;

    public int getImg_heigth() {
        return img_heigth;
    }

    public void setImg_heigth(int img_heigth) {
        this.img_heigth = img_heigth;
    }

    public int getImg_width() {
        return img_width;
    }

    public void setImg_width(int img_width) {
        this.img_width = img_width;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSpid() {
        return spid;
    }

    public void setSpid(int spid) {
        this.spid = spid;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
