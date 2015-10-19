package com.wangjin.mbilibili.app.Utils;

import com.wangjin.mbilibili.app.model.Bangumi;
import com.wangjin.mbilibili.app.model.BangumiInfo;
import com.wangjin.mbilibili.app.model.Recommend;
import com.wangjin.mbilibili.app.model.RecommendInfo;
import com.wangjin.mbilibili.app.model.SpInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wangjin on 15/10/17.
 */
public class JsonHandler {

    public static BangumiInfo handleBangumiInfo(JSONObject bangumi){
        BangumiInfo bangumiInfo= new BangumiInfo();
        bangumiInfo.bangumis = new ArrayList<>();
        try {
            bangumiInfo.num = bangumi.getInt("results");
            JSONArray js = bangumi.getJSONArray("list");
            if(js != null && js.length() > 0){
                for (int i = 0;i<js.length();i++){
                    JSONObject j = js.getJSONObject(i);
                    Bangumi b = new Bangumi();
                    b.setTitle(j.getString("title"));
                    b.setCover(j.getString("cover"));
                    b.setBgmcount(j.getInt("bgmcount"));
                    b.setLastUpdate(j.getInt("lastupdate"));
                    b.setLastUpdate_at(j.getString("lastupdate_at"));
                    b.setIsNew(j.getBoolean("new"));
                    b.setWeekday(j.getInt("weekday"));
                    b.setArea(j.getString("area"));
                    b.setAreaid(j.getInt("areaid"));
                    b.setArealimit(j.getInt("arealimit"));
                    b.setClick(j.getInt("click"));
                    b.setPriority(j.getInt("priority"));
                    b.setTypeid(j.getInt("typeid"));
                    b.setSeasonid(j.getInt("season_id"));
                    b.setAttention(j.getInt("attention"));
                    b.setSpid(j.getInt("spid"));
                    b.setVideo_view(j.getInt("video_view"));
                    bangumiInfo.bangumis.add(b);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bangumiInfo;
    }

    public static RecommendInfo handleRecommendInfo(JSONObject recommend){
        RecommendInfo recommendInfo = new RecommendInfo();
        try {
            recommendInfo.setPages(recommend.getInt("pages"));
            recommendInfo.setNum(recommend.getInt("num"));
            JSONArray jsonArray = recommend.getJSONArray("list");
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject j = jsonArray.getJSONObject(i);
                Recommend r = new Recommend();
                r.setTypeid(j.getInt("typeid"));
                r.setTypename(j.getString("typename"));
                recommendInfo.recommends.add(r);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recommendInfo;
    }

    public static SpInfo handleSpInfo(JSONObject sp) {
        SpInfo spInfo = new SpInfo();
        try {
            spInfo.setCover(sp.getString("cover"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return spInfo;
    }


}
