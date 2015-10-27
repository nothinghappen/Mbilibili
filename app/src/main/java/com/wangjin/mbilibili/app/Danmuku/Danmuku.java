package com.wangjin.mbilibili.app.Danmuku;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by wangjin on 15/10/26.
 */
public class Danmuku extends TextView {

    public static final int SIZE_SMALL = 25;
    public static final int SIZE_MEDIUM = 35;
    public static final int SIZE_LARGE = 50;

    public Danmuku(Context context,String text,int size) {
        super(context);
        setText(text);
        setTextSize(TypedValue.COMPLEX_UNIT_DIP,size);
    }
}
