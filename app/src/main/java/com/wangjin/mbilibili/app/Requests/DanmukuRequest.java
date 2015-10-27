package com.wangjin.mbilibili.app.Requests;

/**
 * Created by wangjin on 15/10/26.
 */
public class DanmukuRequest {

    private static String ADRESS = "http://comment.bilibili.cn/";
    private String adress = ADRESS;
    private int cid;

    public void setCid(int cid) {
        this.cid = cid;
        adress = adress+cid+".xml";
    }

    @Override
    public String toString() {
        return adress;
    }
}
