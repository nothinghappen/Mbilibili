package com.wangjin.mbilibili.app.Danmuku;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by wangjin on 15/11/10.
 */
public class Danmuku extends TextView implements Observer{

    public static final int DEFAULT_SIZE = 20;
    public ValueAnimator animator;

    public void setAnimator(ValueAnimator animator) {
        this.animator = animator;
    }

    public Danmuku(Context context) {
        super(context);
    }

    public void init(String text, int size, int color){
        String c = "#"+Integer.toHexString(color);
        setText(text);
        try {
            setTextColor(Color.parseColor(c));
        }catch (Exception e){
            System.out.println(e);
            setTextColor(Color.parseColor("#000000"));
        }
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
    }

    public void init(String text,int color){
        init(text,DEFAULT_SIZE,color);
    }

    @Override
    public void update(Observable observable, Object data) {
        String command = (String) data;
        switch (command){
            case "pause":
                animator.pause();
                break;
            case "resume":
                animator.resume();
                break;
            case "cancel":
                animator.cancel();
                break;
        }

    }
}
