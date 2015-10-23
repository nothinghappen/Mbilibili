package com.wangjin.mbilibili.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjin on 15/10/20.
 */
public class SpviewInfo {

    int result;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<Spview> spviews = new ArrayList<>();

}
