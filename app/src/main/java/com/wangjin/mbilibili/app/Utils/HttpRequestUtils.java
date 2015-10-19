package com.wangjin.mbilibili.app.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wangjin.mbilibili.R;

import org.json.JSONObject;

/**
 * Created by wangjin on 15/10/17.
 */
public class HttpRequestUtils {

    private RequestQueue mRequestQueue;
    private Context mContext;
    private MImageCache mImageCache;

    public interface onResponseFinishedListener{
        public void onFinish(JSONObject response);
        public void onError(VolleyError error);
    }

    public interface onImageLoadListener{
        public void onFinish(Bitmap bitmap);
        public void onError(VolleyError error);
    }

    private HttpRequestUtils(RequestQueue r,Context c){
        this.mRequestQueue = r;
        this.mContext = c;
    }

    public static HttpRequestUtils newInstance(Context context){
        return new HttpRequestUtils(Volley.newRequestQueue(context),context);
    }

    //onResponseFinishedListener 中处理返回数据
    public void getJson(String adress, final onResponseFinishedListener listener ){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(adress, null,
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
        mRequestQueue.add(jsonObjectRequest);
    }

    //从指定URL加载图片
    public void loadImage(String url,ImageView imageView){
        mImageCache = MImageCache.getInstance();
        ImageLoader imageLoader = new ImageLoader(mRequestQueue,mImageCache);
        ImageLoader.ImageListener listener = imageLoader.getImageListener(imageView, R.drawable.ic_launcher,R.drawable.ic_launcher);
        imageLoader.get(url,listener);
    }

}
