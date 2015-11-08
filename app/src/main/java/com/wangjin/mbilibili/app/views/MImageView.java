package com.wangjin.mbilibili.app.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by wangjin on 15/11/8.
 */
public class MImageView extends ImageView {

    public MImageView(Context context) {
        super(context);
    }

    public MImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        this.setAlpha(0f);
        super.setImageBitmap(bm);
        ObjectAnimator.ofFloat(this,"Alpha",0f,1f).setDuration(2000).start();
    }
}
