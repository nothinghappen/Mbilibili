package com.wangjin.mbilibili.app.Danmuku;

import android.content.Context;
import android.view.ViewGroup;

import java.util.LinkedList;

/**
 * Created by wangjin on 15/11/10.
 */
public class DanmukuManager {

    private int max_danmuku = 10;
    private Context mContext;
    private ViewGroup mRootView;
    private LinkedList<Danmuku> danmukuPool = new LinkedList<>();

    public DanmukuManager(Context context,ViewGroup rootView){
        mContext = context;
        mRootView = rootView;
    }

    public void setMax_danmuku(int max_danmuku) {
        this.max_danmuku = max_danmuku;
    }

    public Danmuku getDanmuku(){
        if(danmukuPool.isEmpty()){
            Danmuku danmuku = new Danmuku(mContext);
            mRootView.addView(danmuku);
            return danmuku;
        }else {
            Danmuku danmuku = danmukuPool.getFirst();
            danmukuPool.removeFirst();
            return danmuku;
        }
    }

    public void recycleDanmuku(Danmuku danmuku){

        if (danmukuPool.size() >= max_danmuku){
            mRootView.removeView(danmuku);
        }else {
            danmukuPool.addLast(danmuku);
        }
    }

}
