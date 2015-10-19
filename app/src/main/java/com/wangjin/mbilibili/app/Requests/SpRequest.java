package com.wangjin.mbilibili.app.Requests;

/**
 * Created by wangjin on 15/10/19.
 */
public class SpRequest {

    private static final String ADRESS = "http://api.bilibili.cn/sp";

    //必填，专题id
    private int spid;

    public int getSpid() {
        return spid;
    }

    public SpRequest setSpid(int spid) {
        this.spid = spid;
        return this;
    }

    @Override
    public String toString() {
        return ADRESS+"?spid="+spid;
    }
}
