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
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.util.Xml;

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

    public interface onXMLResponseFinishedListener{
        public void onFinish(XmlPullParser response);
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

    public void getXml(String adress,final onXMLResponseFinishedListener listener){
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
        mRequestQueue.add(xmlRequest);
    }


    //从指定URL加载图片
    public void loadImage(String url,ImageView imageView){
        mImageCache = MImageCache.getInstance();
        ImageLoader imageLoader = new ImageLoader(mRequestQueue,mImageCache);
        ImageLoader.ImageListener listener = imageLoader.getImageListener(imageView, R.drawable.ic_launcher,R.drawable.ic_launcher);
        imageLoader.get(url,listener);
    }

}
