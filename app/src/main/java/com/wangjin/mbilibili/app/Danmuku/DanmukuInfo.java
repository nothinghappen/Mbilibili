package com.wangjin.mbilibili.app.Danmuku;

/**
 * Created by wangjin on 15/10/27.
 */
public class DanmukuInfo {
    private int time;
    private String text;
    private int size;
    private int color;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
