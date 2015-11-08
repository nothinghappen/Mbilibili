package com.wangjin.mbilibili.app.Utils;

import android.view.View;

import com.wangjin.mbilibili.app.model.Bangumi;
import com.wangjin.mbilibili.app.model.BangumiInfo;
import com.wangjin.mbilibili.app.model.Banner;
import com.wangjin.mbilibili.app.model.List;
import com.wangjin.mbilibili.app.model.ListInfo;
import com.wangjin.mbilibili.app.model.Recommend;
import com.wangjin.mbilibili.app.model.RecommendBangumi;
import com.wangjin.mbilibili.app.model.RecommendInfo;
import com.wangjin.mbilibili.app.model.SpInfo;
import com.wangjin.mbilibili.app.model.Spview;
import com.wangjin.mbilibili.app.model.SpviewInfo;
import com.wangjin.mbilibili.app.model.ViewInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wangjin on 15/10/17.
 */
public class JsonHandler {

    public static BangumiInfo handleBangumiInfo(JSONObject bangumi) {
        BangumiInfo bangumiInfo = new BangumiInfo();
        bangumiInfo.bangumis = new ArrayList<>();
        try {
            bangumiInfo.num = bangumi.getInt("results");
            JSONArray js = bangumi.getJSONArray("list");
            if (js != null && js.length() > 0) {
                for (int i = 0; i < js.length(); i++) {
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

    public static RecommendInfo handleRecommendInfo(JSONObject recommend) {
        RecommendInfo recommendInfo = new RecommendInfo();
        try {
            recommendInfo.setPages(recommend.getInt("pages"));
            recommendInfo.setNum(recommend.getInt("num"));
            JSONArray jsonArray = recommend.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
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
            spInfo.setTitle(sp.getString("title"));
            spInfo.setIsBangumi_end(sp.getInt("isbangumi_end"));
            spInfo.setCount(sp.getInt("count"));
            spInfo.setDescription(sp.getString("description"));
            spInfo.setCover(sp.getString("cover"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return spInfo;
    }

    public static SpviewInfo handleSpviewInfo(JSONObject spv) {
        SpviewInfo spviewInfo = new SpviewInfo();
        try {
            spviewInfo.setResult(spv.getInt("results"));
            JSONArray jsonArray = spv.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                Spview s = new Spview();
                JSONObject j = jsonArray.getJSONObject(i);
                s.setTitle(j.getString("title"));
                s.setCover(j.getString("cover"));
                s.setAid(j.getInt("aid"));
                s.setClick(j.getInt("click"));
                s.setEpisode(j.getInt("episode"));
                spviewInfo.spviews.add(s);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return spviewInfo;
    }

    public static ListInfo handleListInfo(JSONObject l) {
        ListInfo listInfo = new ListInfo();
        try {
            listInfo.setPages(l.getInt("pages"));
            listInfo.setResults(l.getInt("results"));
            JSONObject list = l.getJSONObject("list");
            for (int i = 0; i < list.length() - 1; i++) {
                JSONObject j = list.getJSONObject(String.valueOf(i));
                List li = new List();
                li.setAid(j.getInt("aid"));
                li.setDescription(j.getString("description"));
                li.setPlay(j.getInt("play"));
                li.setTitle(j.getString("title"));
                li.setVideo_review(j.getInt("video_review"));
                li.setPic(j.getString("pic"));
                listInfo.lists.add(li);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listInfo;
    }

    public static java.util.List<RecommendBangumi> handleRecommendBangumi(JSONObject res) {
        ArrayList<RecommendBangumi> recommendBangumis = new ArrayList<>();
        try {
            JSONArray bangumis = res.getJSONArray("list");
            for (int i = 0; i < bangumis.length(); i++) {
                try {
                    JSONObject bangumi = bangumis.getJSONObject(i);
                    RecommendBangumi rb = new RecommendBangumi();
                    rb.setTitle(bangumi.getString("title"));
                    rb.setSpid(bangumi.getInt("spid"));
                    rb.setCover(bangumi.getString("imageurl"));
                    rb.setImg_heigth(bangumi.getInt("height"));
                    rb.setImg_width(bangumi.getInt("width"));
                    recommendBangumis.add(rb);
                } catch (JSONException e) {

                }
            }
        } catch (JSONException e) {
            return null;
        }

        return recommendBangumis;
    }

    public static ViewInfo handleView(JSONObject res) {
        ViewInfo viewInfo = new ViewInfo();
        try {
            viewInfo.setCid(res.getInt("cid"));
            viewInfo.setCreate_at(res.getString("created_at"));
            viewInfo.setDescription(res.getString("description"));
            viewInfo.setPages(res.getInt("pages"));
            viewInfo.setPic(res.getString("pic"));
            viewInfo.setPlay(res.getInt("play"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return viewInfo;
    }

    public static String handleVideoURL(JSONObject res) {
        String url = "";
        try {
            JSONArray durl = res.getJSONArray("durl");
            JSONObject j = durl.getJSONObject(0);
            url = j.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static ArrayList<Banner> handleBannerJson(JSONObject jsonObject) {
        ArrayList<Banner> banners = new ArrayList<>();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    Banner banner = new Banner();
                    JSONObject b = jsonArray.getJSONObject(i);
                    int spid = b.getInt("spid");
                    String title = b.getString("title");
                    String imageurl = b.getString("imageurl");
                    int width = b.getInt("width");
                    int height = b.getInt("height");
                    banner.setHeight(height);
                    banner.setWidth(width);
                    banner.setSpid(spid);
                    banner.setImageurl(imageurl);
                    banner.setTitle(title);
                    banners.add(banner);
                } catch (JSONException e) {

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return banners;
    }


}
