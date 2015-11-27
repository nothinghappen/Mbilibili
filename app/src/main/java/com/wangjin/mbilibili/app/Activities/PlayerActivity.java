package com.wangjin.mbilibili.app.Activities;

import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.wangjin.mbilibili.R;
import com.wangjin.mbilibili.app.Requests.VideoRequest;
import com.wangjin.mbilibili.app.Utils.HttpRequestUtils;
import com.wangjin.mbilibili.app.Utils.JsonHandler;
import com.wangjin.mbilibili.app.Danmuku.*;
import com.wangjin.mbilibili.app.Utils.XMLhandler;
import com.wangjin.mbilibili.app.views.MVideoView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;
import java.util.List;

public class PlayerActivity extends ActionBarActivity {

    private HttpRequestUtils httpRequestUtils;
    private MVideoView mVideoView;
    private MediaController mediaController;
    private int cid;
    private DanmukuPlayer mDanmukuPlayer;
    private RelativeLayout rootLayout;
    private String url;
    private List<DanmukuInfo> danmukuInfos;

    private TextView urlloding;
    private TextView danmukuloding;

    static Handler handler = new Handler();

    Runnable toPlay = new Runnable() {
        @Override
        public void run() {
            danmukuloding.setText("");
            urlloding.setText("");
            HashMap<Integer, List<DanmukuInfo>> map = DanmukuLoader.LoadDanmukuInfos(danmukuInfos);
            Uri uri = Uri.parse(url);
            mDanmukuPlayer = new DanmukuPlayer(PlayerActivity.this,rootLayout);
            mDanmukuPlayer.setmVideoView(mVideoView);
            mDanmukuPlayer.setDanmukuInfos(map);
            mVideoView.setVideoURI(uri);
            mVideoView.setMediaController(mediaController);
            mVideoView.requestFocus();
            mVideoView.start();
        }
    };

    Runnable urlLoadComplete = new Runnable() {
        @Override
        public void run() {
            urlloding.setText("视频地址加载完成！");
            danmukuloding.setText("全舰弹幕装填中.......>_<");
            danmukuloding.setTextColor(Color.parseColor("#ffffff"));
        }
    };

    Runnable danmukuLoadComplete = new Runnable() {
        @Override
        public void run() {
            danmukuloding.setText("弹幕装填完成，准备发射！");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        rootLayout = (RelativeLayout) findViewById(R.id.root_layout);
        urlloding = (TextView) findViewById(R.id.urlloding);
        urlloding.setText("视频地址加载中.......>_<");
        urlloding.setTextColor(Color.parseColor("#ffffff"));
        danmukuloding = (TextView) findViewById(R.id.danmukuloding);
        httpRequestUtils = HttpRequestUtils.getInstance();
        cid = getIntent().getIntExtra("cid", 0);
        mVideoView = (MVideoView) findViewById(R.id.player);
        mediaController = new MediaController(this);

        Log.d("danmuku", "urlloding");
        loadUrl();
    }


    public void loadDanmuku(){
        Log.d("danmuku", "loding");
//        String url = "http://comment.bilibili.cn/"+cid+".xml";
        String url = "http://www.bilibilijj.com/ashx/Barrage" +
                ".ashx?f=true&av=&p=&s=xml&cid=" + cid + "&n=" + cid;
        httpRequestUtils.getXml(url, new HttpRequestUtils.onXMLResponseFinishedListener() {
            @Override
            public void onFinish(XmlPullParser response) {
                danmukuInfos = XMLhandler.handleDanmukuXml(response);
                if (danmukuInfos.isEmpty()) {
                    loadDanmuku();
                } else {
                    Message m1 = Message.obtain(handler,danmukuLoadComplete);
                    Message m2 = Message.obtain(handler,toPlay);
                    handler.sendMessage(m1);
                    handler.sendMessageDelayed(m2, 500);
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.d("danmuku", "danmuku error");
                loadDanmuku();
            }
        });
    }

    public void loadUrl(){
        String s = new VideoRequest().setCid(cid).toString();
        httpRequestUtils.getJson(s, new HttpRequestUtils.onResponseFinishedListener() {
            @Override
            public void onFinish(JSONObject response) {
                url = JsonHandler.handleVideoURL(response);
                Message m = Message.obtain(handler,urlLoadComplete);
                handler.sendMessage(m);
                loadDanmuku();
            }

            @Override
            public void onError(VolleyError error) {
                Log.d("danmuku","url error");
                loadUrl();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDanmukuPlayer.destory();
    }

}
