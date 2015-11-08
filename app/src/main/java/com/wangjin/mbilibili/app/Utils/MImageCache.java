package com.wangjin.mbilibili.app.Utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by wangjin on 15/10/17.
 */
public class MImageCache implements ImageLoader.ImageCache{

    private static MImageCache mImageCache;

    private LruCache<String,Bitmap> mCache;

    public static MImageCache getInstance(){
        if (mImageCache == null){
            mImageCache = new MImageCache();
            return mImageCache;
        }else {
            return mImageCache;
        }
    }

    private MImageCache(){
        mCache = new LruCache<String,Bitmap>(20);
    }

    @Override
    public Bitmap getBitmap(String url) {
        return mCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url,bitmap);
    }
}
