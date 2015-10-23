package com.wangjin.mbilibili.app.Requests;

/**
 * Created by wangjin on 15/10/20.
 */
import com.wangjin.mbilibili.app.Utils.Key;

public class ListRequest {

    private final static String ADRESS = "http://api.bilibili.cn/list";

    private int tid = -1;
    private int page = -1;
    private int pagesize = -1;
    private String order;

    private String adress = ADRESS+"?appkey="+Key.appkey;

    public int getTid() {
        return tid;
    }

    public ListRequest setTid(int tid) {
        this.tid = tid;
        adress = adress+"&tid="+tid;
        return this;
    }

    public int getPage() {
        return page;
    }

    public ListRequest setPage(int page) {
        this.page = page;
        adress = adress+"&page="+page;
        return this;
    }

    public int getPagesize() {
        return pagesize;
    }

    public ListRequest setPagesize(int pagesiz) {
        this.pagesize = pagesiz;
        adress = adress+"&pagesize="+pagesize;
        return this;
    }

    public String getOrder() {
        return order;
    }

    public ListRequest setOrder(String order) {
        this.order = order;
        adress=adress+"&order="+order;
        return this;
    }

    @Override
    public String toString() {
        return adress;
    }
}
