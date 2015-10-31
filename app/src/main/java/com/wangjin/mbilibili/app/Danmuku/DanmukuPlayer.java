package com.wangjin.mbilibili.app.Danmuku;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;


import com.wangjin.mbilibili.app.views.MVideoView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Created by wangjin on 15/10/26.
 */
public class DanmukuPlayer {

    private ViewGroup rootView;
    private Context mContext;
    private DanmukuManager mDanmukuManager = new DanmukuManager();
    private int screenHeight;
    private int screenWidth;
    private int rowCount;
    private float dpValue;
    private List<ObjectAnimator> animatorList = new ArrayList<>();
    private HashMap<Integer, List<DanmukuInfo>> danmukuInfos;
    private Random random = new Random();
    private MVideoView mVideoView;
    private boolean play_flag = true;
    private HandlerThread MVideoViewPositionListener;
    private Handler handler;
    private int old_time = 0;

    Runnable ListenMVideoViewPosition = new Runnable() {
        @Override
        public void run() {
            int position = mVideoView.getCurrentPosition();
            PositionChangeCallBack(position);
            handler.postDelayed(ListenMVideoViewPosition,1000);
        }
    };

    public void PositionChangeCallBack(int currentPosition) {
        int time = currentPosition / 1000;
        if (time != old_time) {
            Log.d("danmuku", String.valueOf(time));
            List<DanmukuInfo> l = danmukuInfos.get(Integer.valueOf(time));
            if (l != null) {
                for (final DanmukuInfo d : l) {
                    rootView.post(new Runnable() {
                        @Override
                        public void run() {//暂且只支持一个尺寸！
                            send(d.getText(), 20, d.getColor());
                        }
                    });
                }
            }
            old_time = time;
        }
    }


    public DanmukuPlayer(Context context, ViewGroup rootView, HashMap<Integer, List<DanmukuInfo>> danmukuInfos) {
        this.mContext = context;
        this.rootView = rootView;
        this.danmukuInfos = danmukuInfos;
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenHeight = metric.heightPixels;
        screenWidth = metric.widthPixels;
        dpValue = metric.density;
        rowCount = (int) (screenHeight / (Danmuku.MIN_SIZE * dpValue));
        MVideoViewPositionListener = new HandlerThread("PositionListener");
        MVideoViewPositionListener.start();
        handler = new Handler(MVideoViewPositionListener.getLooper());
    }

    public void send(String text, int size, int color) {
        int row = Math.abs(random.nextInt() % rowCount);
        float y = row * Danmuku.MIN_SIZE * dpValue;
        final Danmuku danmuku = mDanmukuManager.getDanmuku(text, size, color);
        danmuku.setY(y);
        danmuku.setX(screenWidth + 100);
        danmuku.post(new Runnable() {
            @Override
            public void run() {
                final ObjectAnimator animator = ObjectAnimator
                        .ofFloat(danmuku, "translationX", screenWidth + 100, -danmuku.getWidth())
                        .setDuration(8000);
                animator.setInterpolator(new LinearInterpolator());
                animatorList.add(animator);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animatorList.remove(animator);
                        mDanmukuManager.recycleDanmuku(danmuku);
                    }
                });
                animator.start();
            }
        });
    }

    public void setMVideoView(MVideoView mVideoView) {
        this.mVideoView = mVideoView;
        mVideoView.setStateChangeListner(new MVideoView.StateChangeListner() {
            @Override
            public void onStart() {
                restart();
                if (play_flag) {
                    play_flag = false;
                    handler.post(ListenMVideoViewPosition);
                }
            }

            @Override
            public void onPause() {
                pause();
            }
        });
    }


    public void pause() {
        play_flag = false;
        for (ObjectAnimator a : animatorList) {
            a.pause();
        }
    }

    public void restart() {
        play_flag = true;
        for (ObjectAnimator a : animatorList) {
            a.resume();
        }
    }

    private class DanmukuManager {

        private int max_Danmuku = 10;
        private ArrayList<Danmuku> perpared = new ArrayList<>();


        public synchronized void recycleDanmuku(Danmuku danmuku) {
            if (perpared.size() >= 10)
                rootView.removeView(danmuku);
            else
                perpared.add(danmuku);
        }

        public synchronized Danmuku getDanmuku(String text, int size, int color) {
            if (perpared.isEmpty()) {
                Danmuku danmuku = new Danmuku(mContext, text, size, color);
                rootView.addView(danmuku);
                return danmuku;
            } else {
                Danmuku danmuku = perpared.get(0);
                perpared.remove(0);
                danmuku.setTextSize(size);
                danmuku.setText(text);
                return danmuku;
            }
        }

        public int getMax_Danmuku() {
            return max_Danmuku;
        }

        public void setMax_Danmuku(int max_Danmuku) {
            this.max_Danmuku = max_Danmuku;
        }
    }

    public void destroy() {
        handler.removeCallbacks(ListenMVideoViewPosition);
        MVideoViewPositionListener.quitSafely();
    }


}
