package com.wangjin.mbilibili.app.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.VolleyError;
import com.wangjin.mbilibili.R;
import com.wangjin.mbilibili.app.Danmuku.DanmukuInfo;
import com.wangjin.mbilibili.app.Utils.HttpRequestUtils;
import com.wangjin.mbilibili.app.Utils.XMLhandler;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        final HttpRequestUtils httpRequestUtils = HttpRequestUtils.newInstance(this);
        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "request");
                int CID = 4901125;
                httpRequestUtils.getXml("http://www.bilibilijj.com/ashx/Barrage" +
                        ".ashx?f=true&av=&p=&s=xml&cid=" + CID + "&n=" + CID, new HttpRequestUtils.onXMLResponseFinishedListener() {
                    @Override
                    public void onFinish(XmlPullParser response) {
                        java.util.List<DanmukuInfo> danmukuInfos = XMLhandler.handleDanmukuXml(response);
                        for (DanmukuInfo d : danmukuInfos) {
                            Log.d("danmuku", d.getTime() + "   " + d.getText());
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
            }
        });
    }
}
