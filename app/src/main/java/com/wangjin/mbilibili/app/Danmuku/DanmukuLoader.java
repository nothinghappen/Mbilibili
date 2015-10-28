package com.wangjin.mbilibili.app.Danmuku;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wangjin on 15/10/27.
 */
public class DanmukuLoader {

    public static HashMap<Integer,List<DanmukuInfo>> LoadDanmukuInfos(List<DanmukuInfo> danmukuInfos){
        HashMap<Integer,List<DanmukuInfo>> maps = new HashMap<>();
        if (danmukuInfos.isEmpty()){
            Log.d("danmuku","empty");
        }
        for (int i = 0;i<danmukuInfos.size();i++){
            DanmukuInfo d = danmukuInfos.get(i);
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
