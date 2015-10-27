package com.wangjin.mbilibili.app.Danmuku;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;


import com.wangjin.mbilibili.app.views.MVideoView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by wangjin on 15/10/26.
 */
public class DanmukuPlayer {

    private ViewGroup rootView;
    Context mContext;
    private DanmukuManager mDanmukuManager = new DanmukuManager();
    int screenHeight;
    int screenWidth;
    private List<ObjectAnimator> animatorList = new ArrayList<>();
    private HashMap<Integer,List<DanmukuInfo>> danmukuInfos;
    Random random = new Random();
    private MVideoView mVideoView;
    private Thread listenerThread;
    private int old_time = 0;

    public void setMVideoView(MVideoView mVideoView) {
        this.mVideoView = mVideoView;
        mVideoView.setStateChangeListner(new MVideoView.StateChangeListner() {
            @Override
            public void onStart() {
                restart();
                listenerThread = ListenMVideoViewPosition();
                listenerThread.start();
            }

            @Override
            public void onPause() {
                pause();
            }
        });
    }

    public void PositionChangeCallBack(int currentPosition){
        int time = currentPosition/1000;
        if (time != old_time) {
            Log.d("Danmuku", "time: " + time);
            List<DanmukuInfo> l = danmukuInfos.get(Integer.valueOf(time));
            if (l != null) {
                for (final DanmukuInfo d : l) {
                    rootView.post(new Runnable() {
                        @Override
                        public void run() {
                            send(d.getText(), d.getSize());
                        }
                    });
                }
            }
            old_time = time;
        }
    }

    public Thread ListenMVideoViewPosition(){
        Thread listenerThread = new Thread(){
            @Override
            public void run() {
                while (true) {
                    int position = mVideoView.getCurrentPosition();
                    PositionChangeCallBack(position);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        return listenerThread;
    }

    public DanmukuPlayer(Context context,ViewGroup rootView,HashMap<Integer,List<DanmukuInfo>> danmukuInfos){
        this.mContext = context;
        this.rootView = rootView;
        this.danmukuInfos = danmukuInfos;
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenHeight = metric.heightPixels;
        screenWidth = metric.widthPixels;
    }

    public void pause(){
        for (ObjectAnimator a : animatorList){
            a.pause();
        }
    }

    public void restart(){
        for (ObjectAnimator a : animatorList){
            a.resume();
        }
    }

    public void send(String text,int size){
        int y = Math.abs(random.nextInt() % screenHeight);
        final Danmuku danmuku = mDanmukuManager.getDanmuku(text,size);
        danmuku.setY(y);
        danmuku.setX(screenWidth + 100);
        danmuku.post(new Runnable() {
            @Override
            public void run() {
                final ObjectAnimator animator = ObjectAnimator
                        .ofFloat(danmuku, "translationX", screenWidth + 100, -danmuku.getWidth())
                        .setDuration(8000);
                animatorList.add(animator);
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animatorList.remove(animator);
                        mDanmukuManager.recycleDanmuku(danmuku);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animator.start();
            }
        });

    }

    private class DanmukuManager {

        private int max_Danmuku = 10;
        private ArrayList<Danmuku> perpared = new ArrayList<>();


        public synchronized void recycleDanmuku(Danmuku danmuku){
            if (perpared.size() >= 10)
                rootView.removeView(danmuku);
            else
                perpared.add(danmuku);
        }

        public synchronized Danmuku getDanmuku(String text,int size){
            if (perpared.isEmpty()){
                Danmuku danmuku = new Danmuku(mContext,text,size);
                rootView.addView(danmuku);
                return danmuku;
            }else {
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


}
