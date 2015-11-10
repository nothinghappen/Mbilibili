package com.wangjin.mbilibili.app.Utils;

import com.wangjin.mbilibili.app.Danmuku.DanmukuInfo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjin on 15/10/27.
 */
public class XMLhandler {

    public static List<DanmukuInfo> handleDanmukuXml(XmlPullParser res){
        List<DanmukuInfo> danmukuInfos = new ArrayList<>();
        try {
            int eventType = res.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if (res.getName().equals("d")) {
                            DanmukuInfo d = new DanmukuInfo();
                            String[] params = res.getAttributeValue(0).split(",");
                            int time = Double.valueOf(params[0]).intValue();
                            int size = Integer.valueOf(params[2]);
                            int color = Integer.valueOf(params[3]);
                            String text = "...";
                            if (res.next() == XmlPullParser.TEXT)
                                text = res.getText();
                            d.setSize(size);
                            d.setText(text);
                            d.setTime(time);
                            d.setColor(color);
                            danmukuInfos.add(d);
                        }
                        break;
                }
                eventType = res.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return danmukuInfos;
    }


}
