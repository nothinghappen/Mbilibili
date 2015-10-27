package com.wangjin.mbilibili.app.Activities;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.wangjin.mbilibili.R;
import com.wangjin.mbilibili.app.Requests.VideoRequest;
import com.wangjin.mbilibili.app.Utils.HttpRequestUtils;
import com.wangjin.mbilibili.app.Utils.JsonHandler;
import com.wangjin.mbilibili.app.Danmuku.*;
import com.wangjin.mbilibili.app.views.MVideoView;

import org.json.JSONObject;

public class PlayerActivity extends ActionBarActivity {

    private HttpRequestUtils httpRequestUtils;
    private MVideoView mVideoView;
    private MediaController mediaController;
    private int cid;
    private DanmukuPlayer mDanmukuPlayer;
    private RelativeLayout rootLayout;
    private static final int SEND = 0;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        rootLayout = (RelativeLayout) findViewById(R.id.root_layout);
        mDanmukuPlayer = new DanmukuPlayer(this,rootLayout,DanmukuLoader.LoadDanmukuInfos());

        httpRequestUtils = HttpRequestUtils.newInstance(this);
        cid = getIntent().getIntExtra("cid", 0);
        mVideoView = (MVideoView) findViewById(R.id.player);
        mDanmukuPlayer.setMVideoView(mVideoView);
        mediaController = new MediaController(this);

        String s = new VideoRequest().setCid(cid).toString();
        httpRequestUtils.getJson(s, new HttpRequestUtils.onResponseFinishedListener() {
            @Override
            public void onFinish(JSONObject response) {
                String url = JsonHandler.handleVideoURL(response);
                System.out.println(url);
                Uri uri = Uri.parse(url);
                mVideoView.setVideoURI(uri);
                mVideoView.setMediaController(mediaController);
                mVideoView.requestFocus();

                new Thread(){
                    @Override
                    public void run() {
                        while (true) {
                            Message m = new Message();
                            handler.sendMessage(m);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();

            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

}
