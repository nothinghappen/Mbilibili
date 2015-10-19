package com.wangjin.mbilibili.app.Requests;

import android.location.Address;

import com.wangjin.mbilibili.app.Utils.Key;

/**
 * Created by wangjin on 15/10/17.
 */
public class BangumiRequest {

    public static final String bangumiAdress = "http://api.bilibili.cn/bangumi";

    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;
    public static final int SUNDAY = 0;

    public static final int TWO = 2;
    public static final int THREE = 3;

    private int weekday = -1;
    private int type = -1;

    public int getWeekday() {
        return weekday;
    }

    public BangumiRequest setWeekday(int weekday) {
        this.weekday = weekday;
        return this;
    }

    public int getType() {
        return type;
    }

    public BangumiRequest setType(int type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        String Address = bangumiAdress+"?appkey="+ Key.appkey;
        if (weekday != -1)
            Address = Address+"&weekday="+weekday;
        if (type != -1)
            Address = Address+"&btype=" + type;
        return Address;
    }
}
