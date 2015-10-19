package com.wangjin.mbilibili.app.Requests;

/**
 * Created by wangjin on 15/10/18.
 */
public class RecommendRequest {

    private static final String ADRESS = "http://api.bilibili.cn/recommend";
    //分类id,new排序为必填 其他为可选
    private int tid = -1;
    //结果分页选择
    private int page = -1;
    //单页返回记录条数，最大100，默认30
    private int pagesize = -1;
    //排序方式
    private String order;

    private boolean flag = false;

//    default 按新投稿排序
//        new 按新评论排序
//     review 按评论数从高至低排序
//        hot 按点击从高至低排序
//      damku 按弹幕数从高至低排序
//    comment 按推荐数从高至低排序
//    promote 按宣传数排序（硬币）

    public int getTid() {
        return tid;
    }

    public RecommendRequest setTid(int tid) {
        this.tid = tid;
        flag = true;
        return this;
    }

    public int getPage() {
        return page;
    }

    public RecommendRequest setPage(int page) {
        this.page = page;
        flag = true;
        return this;
    }

    public int getPagesize() {
        return pagesize;
    }

    public RecommendRequest setPagesize(int pagesize) {
        this.pagesize = pagesize;
        flag = true;
        return this;
    }

    public String getOrder() {
        return order;
    }

    public RecommendRequest setOrder(String order) {
        this.order = order;
        flag = true;
        return this;
    }

    @Override
    public String toString() {
        String address = ADRESS;
        if (tid != -1){
            if (flag) {
                address = address + "?tid=" + tid;
                flag = false;
            }
            else
                address = address + "&tid="+tid;
        }
        if (page != -1){
            if (flag) {
                address = address + "?page=" + page;
                flag = false;
            }
            else
                address = address + "&page="+page;
        }
        if (pagesize != -1){
            if (flag) {
                address = address + "?pagesize=" + pagesize;
                flag = false;
            }
            else
                address = address + "&pagesize="+pagesize;
        }
        if (order != null){
            if (flag) {
                address = address + "?order=" + order;
                flag = false;
            }
            else
                address = address + "&order="+order;
        }
        return address;
    }
}
