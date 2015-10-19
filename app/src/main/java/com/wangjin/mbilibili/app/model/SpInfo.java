package com.wangjin.mbilibili.app.model;


/**
 * Created by wangjin on 15/10/19.
 */
public class SpInfo {

    private String title;
    private int pubdate; //UNIX Timestamp
    private String create_at;
    private int lastupdate; //UNIX Timestamp
    private String lastupdate_at;
    private String cover;
    private int isBangumi; //是否新番 1 = 二次元新番 2 ＝ 三次元新番
    private String bangumi_date; //开播日期
    private int isBangumi_end;//是否完结
    private String description;
    private int view;//点击次数
    private int count;//视频数

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPubdate() {
        return pubdate;
    }

    public void setPubdate(int pubdate) {
        this.pubdate = pubdate;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public int getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(int lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getLastupdate_at() {
        return lastupdate_at;
    }

    public void setLastupdate_at(String lastupdate_at) {
        this.lastupdate_at = lastupdate_at;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getIsBangumi() {
        return isBangumi;
    }

    public void setIsBangumi(int isBangumi) {
        this.isBangumi = isBangumi;
    }

    public String getBangumi_date() {
        return bangumi_date;
    }

    public void setBangumi_date(String bangumi_date) {
        this.bangumi_date = bangumi_date;
    }

    public int getIsBangumi_end() {
        return isBangumi_end;
    }

    public void setIsBangumi_end(int isBangumi_end) {
        this.isBangumi_end = isBangumi_end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
