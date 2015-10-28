package com.wangjin.mbilibili.app.Danmuku;

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
}
