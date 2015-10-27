package com.wangjin.mbilibili.app.Danmuku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wangjin on 15/10/27.
 */
public class DanmukuLoader {

    public static HashMap<Integer,List<DanmukuInfo>> LoadDanmukuInfos(){
        HashMap<Integer,List<DanmukuInfo>> maps = new HashMap<>();
        DanmukuInfo[] danmukuInfos = new DanmukuInfo[1000];
        for (int i = 0;i<danmukuInfos.length;i++){
            DanmukuInfo d = new DanmukuInfo();
            d.setSize(Danmuku.SIZE_MEDIUM);
            d.setTime(i / 10 + 1);
            d.setText("233333333");
            danmukuInfos[i] = d;
        }
        for (int i = 0;i<danmukuInfos.length;i++){
            DanmukuInfo d = danmukuInfos[i];
            List<DanmukuInfo> l = maps.get(Integer.valueOf(d.getTime()));
            if(l == null){
                l = new ArrayList<>();
                l.add(d);
                maps.put(d.getTime(),l);
            }else {
                l.add(d);
            }
        }
        return maps;
    }

}
