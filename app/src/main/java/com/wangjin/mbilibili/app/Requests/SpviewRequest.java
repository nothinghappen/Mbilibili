package com.wangjin.mbilibili.app.Requests;

/**
 * Created by wangjin on 15/10/19.
 */
public class SpviewRequest {

    private static final String ADRESS = "http://api.bilibili.cn/spview";

    //专题SPID 必填
    private int spid;
    //分季id
    private int season_id = -1;
    //设置为1时只返回番剧类视频 设置为0时只返回普通视频 必填
    private int bangumi;

    public int getSpid() {
        return spid;
    }

    public SpviewRequest setSpid(int spid) {
        this.spid = spid;
        return this;
    }

    public int getSeason_id() {
        return season_id;
    }

    public SpviewRequest setSeason_id(int season_id) {
        this.season_id = season_id;
        return this;
    }

    public int getBangumi() {
        return bangumi;
    }

    public SpviewRequest setBangumi(int bangumi) {
        this.bangumi = bangumi;
        return this;
    }

    @Override
    public String toString() {
       String adress = ADRESS;
        adress = adress+"?spid="+spid+"&bangumi="+bangumi;
        if (season_id != -1){
            adress = adress+"&season_id="+season_id;
        }
        return adress;
    }
}
