package com.wangjin.mbilibili.app.Requests;

import com.wangjin.mbilibili.app.Utils.Key;

/**
 * Created by wangjin on 15/10/26.
 */
public class ViewRequest {

    private static final String ADRESS = "http://api.bilibili.cn/view";
    private String adress = ADRESS + "?appkey="+ Key.appkey;

    private int aid;
    private int page;

    public int getAid() {
        return aid;
    }

    public ViewRequest setAid(int aid) {
        this.aid = aid;
        adress = adress + "&id="+aid;
        return this;
    }

    public int getPage() {
        return page;
    }

    public ViewRequest setPage(int page) {
        this.page = page;
        adress = adress + "&page="+page;
        return this;
    }

    @Override
    public String toString() {
        return adress;
    }
}
