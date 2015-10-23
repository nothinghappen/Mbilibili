package com.wangjin.mbilibili.app.model;

/**
 * Created by wangjin on 15/10/20.
 */
public class Spview {

    private String title;
    private String cover;
    private int aid;
    private int click;
    private int episode;

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }
}
