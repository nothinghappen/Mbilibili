package com.wangjin.mbilibili.app.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangjin.mbilibili.R;

/**
 * Created by wangjin on 15/10/22.
 */
public class recommend_item_view extends FrameLayout{
    public TextView title;
    public ImageView cover;

    public recommend_item_view(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.item_recommend_view, this);
        title = (TextView) findViewById(R.id.recommend_title);
        cover = (ImageView) findViewById(R.id.recommend_cover);
    }

}
