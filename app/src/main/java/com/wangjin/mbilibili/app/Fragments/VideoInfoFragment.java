package com.wangjin.mbilibili.app.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.wangjin.mbilibili.R;
import com.wangjin.mbilibili.app.Activities.PlayerActivity;
import com.wangjin.mbilibili.app.Activities.VideoInfoActivity;
import com.wangjin.mbilibili.app.Requests.VideoRequest;
import com.wangjin.mbilibili.app.Requests.ViewRequest;
import com.wangjin.mbilibili.app.Utils.HttpRequestUtils;
import com.wangjin.mbilibili.app.Utils.JsonHandler;
import com.wangjin.mbilibili.app.model.SpInfo;
import com.wangjin.mbilibili.app.model.Spview;
import com.wangjin.mbilibili.app.model.ViewInfo;

import org.json.JSONObject;


public class VideoInfoFragment extends BaseFragment implements VideoInfoActivity.notifyFragmentChangeUI {

    TextView des;

    public static VideoInfoFragment newInstance(String title) {
        VideoInfoFragment fragment = new VideoInfoFragment();
        fragment.title = title;
        return fragment;
    }

    public VideoInfoFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.videoinfo_fragment_layout, container, false);
        des = (TextView) v.findViewById(R.id.sp_description);
        return v;
    }

    @Override
    public void changedesUI() {
        VideoInfoActivity v = (VideoInfoActivity) getActivity();
        if (v != null) {
            des.setText(v.spInfo.getDescription());
        }
    }

    @Override
    public void changebgmUI() {
        final VideoInfoActivity vi = (VideoInfoActivity) getActivity();
        if (vi != null) {
            for (final Spview s : vi.spviewInfo.spviews) {
                Button b = new Button(vi);
                b.setText(String.valueOf(s.getEpisode()));
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vi.httpRequestUtils.getJson(new ViewRequest().setAid(s.getAid()).setPage(1).toString(),
                                new HttpRequestUtils.onResponseFinishedListener() {
                                    @Override
                                    public void onFinish(JSONObject response) {
                                        ViewInfo viewInfo = JsonHandler.handleView(response);
                                        Intent i = new Intent(getActivity(), PlayerActivity.class);
                                        i.putExtra("cid",viewInfo.getCid());
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onError(VolleyError error) {

                                    }
                                });
                    }
                });
                ((GridLayout) getView().findViewById(R.id.bgmbtn_container)).addView(b);
            }
        }
    }
}
