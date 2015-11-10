package com.wangjin.mbilibili.app.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wangjin.mbilibili.R;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

/**
 * Created by wangjin on 15/10/17.
 */
public class HttpRequestUtils {

    private RequestQueue mRequestQueue;
    private MImageCache mImageCache;

    private static volatile HttpRequestUtils httpRequestUtils;

    private HttpRequestUtils(RequestQueue r) {
        this.mRequestQueue = r;
    }

    public static HttpRequestUtils newInstance(Context context) {
        if (httpRequestUtils == null) {
            synchronized (HttpRequestUtils.class) {
                if (httpRequestUtils == null) {
                    httpRequestUtils = new HttpRequestUtils(
                            Volley.newRequestQueue(context.getApplicationContext(), 10 * 1024 * 1024));
                }
            }
        }
        return httpRequestUtils;
    }

    public interface onResponseFinishedListener {
        public void onFinish(JSONObject response);

        public void onError(VolleyError error);
    }

    public interface onXMLResponseFinishedListener {
        public void onFinish(XmlPullParser response);

        public void onError(VolleyError error);
    }

    public interface onImageLoadListener {
        public void onFinish(Bitmap bitmap);

        public void onError(VolleyError error);
    }

    //onResponseFinishedListener 中处理返回数据
    public void getJson(String adress, final onResponseFinishedListener listener) {
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(adress, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onFinish(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error);
            }
        });
        jsonObjectRequest.setShouldCache(false);
        mRequestQueue.add(jsonObjectRequest);
    }

    public void getXml(String adress, final onXMLResponseFinishedListener listener) {
        XMLRequest xmlRequest = new XMLRequest(adress, new Response.Listener<XmlPullParser>() {
            @Override
            public void onResponse(XmlPullParser response) {
                listener.onFinish(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error);
            }
        });
        xmlRequest.setShouldCache(false);
        mRequestQueue.add(xmlRequest);
    }


    //从指定URL加载图片
    public void loadImage(String url, ImageView imageView) {
        mImageCache = MImageCache.getInstance();
        ImageLoader imageLoader = new ImageLoader(mRequestQueue, mImageCache);
        ImageLoader.ImageListener listener = imageLoader.getImageListener(imageView, R.drawable.white_blank, R.drawable.little_tv);
        imageLoader.get(url, listener);
    }

}
