package com.wangjin.mbilibili.app.Activities;

import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private static final int SEND = 0;
    private String url;
    private List<DanmukuInfo> danmukuInfos;

    private TextView urlloding;
    private TextView danmukuloding;

    private static final int URL_LOAD_COMPLETE = 0;
    private static final int DANMUKU_LOAD_COMPLETE = 1;
    private static final int READEY_TO_PLAY = 2;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case READEY_TO_PLAY:
                    danmukuloding.setText("");
                    urlloding.setText("");
                    HashMap<Integer, List<DanmukuInfo>> map = DanmukuLoader.LoadDanmukuInfos(danmukuInfos);
                    Uri uri = Uri.parse(url);
                    mDanmukuPlayer = new DanmukuPlayer(PlayerActivity.this, rootLayout, map);
                    mDanmukuPlayer.setMVideoView(mVideoView);
                    mVideoView.setVideoURI(uri);
                    mVideoView.setMediaController(mediaController);
                    mVideoView.requestFocus();
                    mVideoView.start();
                    break;
                case URL_LOAD_COMPLETE:
                    urlloding.setText("视频地址加载完成！");
                    danmukuloding.setText("全舰弹幕装填中.......>_<");
                    danmukuloding.setTextColor(Color.parseColor("#ffffff"));
                    //Toast.makeText(PlayerActivity.this,"视频地址加载完成！",Toast.LENGTH_LONG).show();
                    break;
                case DANMUKU_LOAD_COMPLETE:
                    danmukuloding.setText("弹幕装填完成，准备发射！");
                    //Toast.makeText(PlayerActivity.this,"弹幕加载完成！",Toast.LENGTH_LONG).show();
                    break;
            }
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
        httpRequestUtils = HttpRequestUtils.newInstance(this);
        cid = getIntent().getIntExtra("cid", 0);
        mVideoView = (MVideoView) findViewById(R.id.player);
        mediaController = new MediaController(this);

        Log.d("danmuku", "urlloding");
        loadUrl();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDanmukuPlayer!=null){
            if (mDanmukuPlayer.listenerThread != null){
                mDanmukuPlayer.listenerThread.destroy();
            }
        }
    }

    public void loadDanmuku(){
        Log.d("danmuku","loding");
        httpRequestUtils.getXml("http://www.bilibilijj.com/ashx/Barrage" +
                ".ashx?f=true&av=&p=&s=xml&cid=" + cid + "&n=" + cid, new HttpRequestUtils.onXMLResponseFinishedListener() {
            @Override
            public void onFinish(XmlPullParser response) {
                danmukuInfos = XMLhandler.handleDanmukuXml(response);
                if (danmukuInfos.isEmpty()) {
                    loadDanmuku();
                } else {
                    Message m1 = new Message();
                    m1.what = DANMUKU_LOAD_COMPLETE;
                    Message m2 = new Message();
                    m2.what = READEY_TO_PLAY;
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
                Message m = new Message();
                m.what = URL_LOAD_COMPLETE;
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

}
