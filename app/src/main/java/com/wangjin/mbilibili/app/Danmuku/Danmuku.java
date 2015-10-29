package com.wangjin.mbilibili.app.Danmuku;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import com.wangjin.mbilibili.R;

/**
 * Created by wangjin on 15/10/26.
 */
public class Danmuku extends TextView {

    public static final int MIN_SIZE = 20;
    public int trackNum;
    private PositionChangeListner positionChangeListner;

    public Danmuku(Context context,String text,int size,int color) {
        super(context);
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


    public void setPositionChangeListner(PositionChangeListner positionChangeListner) {
        this.positionChangeListner = positionChangeListner;
    }

    interface PositionChangeListner{
        void PositionCallBack();
    }

    public void listenStart(){
        new Thread(){
            @Override
            public void run() {
                positionChangeListner.PositionCallBack();
            }
        }.start();
    }

}
