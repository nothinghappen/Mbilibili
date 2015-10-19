package com.wangjin.mbilibili.app.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangjin.mbilibili.R;

/**
 * Created by wangjin on 15/10/17.
 */
public class BangumiItemView extends FrameLayout{

    Context mContext;
    public ImageView mImageview;
    TextView title;
    TextView bgmcount;

    public BangumiItemView(Context context) {
        super(context);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.bangumi_item_view,this);
        mImageview = (ImageView) findViewById(R.id.bangumi_cover);
        title = (TextView) findViewById(R.id.bangumi_title);
        bgmcount = (TextView) findViewById(R.id.bgmcount);
    }

    public void setmImageview(Bitmap bitmap) {
        mImageview.setImageBitmap(bitmap);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setBgmcount(String bgmcount){
        this.bgmcount.setText(bgmcount);
    }
}
