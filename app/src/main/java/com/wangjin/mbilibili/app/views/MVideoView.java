package com.wangjin.mbilibili.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

import com.wangjin.mbilibili.app.Danmuku.DanmukuPlayer;

/**
 * Created by wangjin on 15/10/27.
 */
public class MVideoView extends VideoView {

    DanmukuPlayer mDanmukuPlayer;

    public interface StateChangeListner{
        public void onStart();
        public void onPause();
    }

    StateChangeListner listner;

    public void setDanmukuPlayer(DanmukuPlayer mDanmukuPlayer) {
        this.mDanmukuPlayer = mDanmukuPlayer;
    }

    public void setStateChangeListner(StateChangeListner listner){
        this.listner = listner;
    }

    public MVideoView(Context context) {
        super(context);
    }

    public MVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void start() {
        super.start();
        if (listner != null){
            listner.onStart();
        }
    }

    @Override
    public void pause() {
        super.pause();
        if (listner != null){
            listner.onPause();
        }
    }


}
