package com.wangjin.mbilibili.app.Requests;

import com.wangjin.mbilibili.app.Utils.Key;

/**
 * Created by wangjin on 15/10/26.
 */
public class VideoRequest {

    private final static String ADRESS = "http://interface.bilibili.tv/playurl?appkey="+ Key.appkey+"&otype=json&type=FLV/mp4&cid=";
    private String adress = ADRESS;
    private int cid;

    public int getCid() {
        return cid;
    }

    public VideoRequest setCid(int cid) {
        this.cid = cid;
        adress = adress+cid;
        return this;
    }

    @Override
    public String toString() {
        return adress;
    }
}
