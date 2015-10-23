package com.wangjin.mbilibili.app.model;

/**
 * Created by wangjin on 15/10/20.
 */
public class List {

    private int aid;
    private String title;
    private int play;
    private int video_review;
    private String description;
    private String pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPlay() {
        return play;
    }

    public void setPlay(int play) {
        this.play = play;
    }

    public int getVideo_review() {
        return video_review;
    }

    public void setVideo_review(int video_review) {
        this.video_review = video_review;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
