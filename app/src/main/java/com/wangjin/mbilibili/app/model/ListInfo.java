package com.wangjin.mbilibili.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjin on 15/10/20.
 */
public class ListInfo {

    private int pages;
    private int results;
    public List<com.wangjin.mbilibili.app.model.List> lists = new ArrayList<>();

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }
}
