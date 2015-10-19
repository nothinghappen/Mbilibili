package com.wangjin.mbilibili.app.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wangjin on 15/10/17.
 */
public class Bangumi implements Comparable,Serializable{
    static Date d = new Date();
    static Calendar cal = Calendar.getInstance();
    static int day_of_week;

    public Bangumi(){
        cal.setTime(d);
        day_of_week = cal.get(Calendar.DAY_OF_WEEK)-1;
    }

    //标题
    private String title;
    //封面地址
    private String cover;
    //集数
    private int bgmcount;
    //周信息
    private int weekday;
    //最后更新时间 UNIX Timestamp
    private int lastUpdate;
    //最后更新时间
    private String lastUpdate_at;
    //是否最近有更新
    private boolean isNew;

    //以下意义不明  —_—#
    private int arealimit;
    private int spid;//专题id
    private int click;
    private int video_view;
    private int attention;
    private int areaid;  //地区id？
    private int typeid;  //类别id？
    private int priority; //优先级？
    private String area; //地区
    private int seasonid; //季

    public int getArealimit() {
        return arealimit;
    }

    public void setArealimit(int arealimit) {
        this.arealimit = arealimit;
    }

    public int getSpid() {
        return spid;
    }

    public void setSpid(int spid) {
        this.spid = spid;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public int getVideo_view() {
        return video_view;
    }

    public void setVideo_view(int video_view) {
        this.video_view = video_view;
    }

    public int getAttention() {
        return attention;
    }

    public void setAttention(int attention) {
        this.attention = attention;
    }

    public int getAreaid() {
        return areaid;
    }

    public void setAreaid(int areaid) {
        this.areaid = areaid;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getSeasonid() {
        return seasonid;
    }

    public void setSeasonid(int seasonid) {
        this.seasonid = seasonid;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBgmcount() {
        return bgmcount;
    }

    public void setBgmcount(int bgmcount) {
        this.bgmcount = bgmcount;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public String getLastUpdate_at() {
        return lastUpdate_at;
    }

    public void setLastUpdate_at(String lastUpdate_at) {
        this.lastUpdate_at = lastUpdate_at;
    }

    public int getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(int lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    @Override
    public int compareTo(Object o) {
        Bangumi b = (Bangumi) o;
//        int x = day_of_week - this.weekday;
//        int y = day_of_week - b.getWeekday();
//
//        if(x >=0 && y<0){
//            return -1;
//        }
//        if(y>= 0 && x <0){
//            return 1;
//        }else {
//            return x-y;
//        }
        return b.getLastUpdate() - this.getLastUpdate();
    }
}
