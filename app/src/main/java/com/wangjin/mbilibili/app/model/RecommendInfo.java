package com.wangjin.mbilibili.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjin on 15/10/18.
 */
public class RecommendInfo {
    private int pages;
    private int num;
    public List<Recommend> recommends = new ArrayList<>();

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
