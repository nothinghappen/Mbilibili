package com.wangjin.mbilibili.app.Danmuku;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

import com.wangjin.mbilibili.app.views.MVideoView;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Random;

/**
 * Created by wangjin on 15/11/10.
 */
public class DanmukuPlayer extends Observable{

    private MVideoView mVideoView;
    private DanmukuManager danmukuManager;
    private DisplayMetrics metrics;
    int rowCount;
    private HashMap<Integer, List<DanmukuInfo>> danmukuInfos;
    private PositionListenThread listenThread;
    boolean play_flag = true;

    public DanmukuPlayer(Context context,ViewGroup rootView){
        danmukuManager = new DanmukuManager(context,rootView);
        metrics = new DisplayMetrics();
        ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(metrics);
        rowCount = (int) (metrics.heightPixels / (Danmuku.DEFAULT_SIZE * metrics.density));
        listenThread = new PositionListenThread();
    }

    public void setDanmukuInfos(HashMap<Integer, List<DanmukuInfo>> danmukuInfos) {
        this.danmukuInfos = danmukuInfos;
    }


    public void setmVideoView(MVideoView mVideoView) {
        this.mVideoView = mVideoView;
        mVideoView.setStateChangeListner(new MVideoView.StateChangeListner() {
            @Override
            public void onStart() {
                play_flag = true;
                setChanged();
                notifyObservers("resume");
            }

            @Override
            public void onPause() {
                play_flag = false;
                setChanged();
                notifyObservers("pause");
            }
        });
        listenThread.start();
    }

    public void send(DanmukuInfo info){
        Random random = new Random();
        int row = Math.abs(random.nextInt() % rowCount);
        float y = row * Danmuku.DEFAULT_SIZE * metrics.density;
        final Danmuku danmuku = danmukuManager.getDanmuku();
        danmuku.setY(y);
        danmuku.setX(metrics.widthPixels + 100);
        danmuku.init(info.getText(),info.getColor());
        danmuku.post(new Runnable() {
            @Override
            public void run() {
                final ObjectAnimator animator = ObjectAnimator
                        .ofFloat(danmuku, "translationX", metrics.widthPixels + 100, -danmuku.getWidth())
                        .setDuration(8000);
                animator.setInterpolator(new LinearInterpolator());
                danmuku.setAnimator(animator);
                addObserver(danmuku);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        deleteObserver(danmuku);
                        danmukuManager.recycleDanmuku(danmuku);
                    }
                });
                animator.start();
            }
        });
    }

    private class PositionListenThread extends Thread{
        public boolean flag = true;
        @Override
        public void run() {
            while (flag){
                if (mVideoView != null && danmukuInfos != null) {
                    int position = mVideoView.getCurrentPosition();
                    PositionChangeCallBack(position);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void PositionChangeCallBack(int currentPosition) {
        int time = currentPosition / 1000;
        if (play_flag) {
            List<DanmukuInfo> l = danmukuInfos.get(Integer.valueOf(time));
            if (l != null) {
                for (final DanmukuInfo d : l) {
                    mVideoView.post(new Runnable() {
                        @Override
                        public void run() {
                            send(d);
                        }
                    });
                }
            }
        }
    }

    public void destory(){
        listenThread.flag = false;
    }


}
