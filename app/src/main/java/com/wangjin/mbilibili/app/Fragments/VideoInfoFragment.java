package com.wangjin.mbilibili.app.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.wangjin.mbilibili.R;
import com.wangjin.mbilibili.app.Activities.VideoInfoActivity;
import com.wangjin.mbilibili.app.model.SpInfo;
import com.wangjin.mbilibili.app.model.Spview;


public class VideoInfoFragment extends BaseFragment implements VideoInfoActivity.notifyFragmentChangeUI{

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
        VideoInfoActivity v = (VideoInfoActivity)getActivity();
        des.setText(v.spInfo.getDescription());
    }

    @Override
    public void changebgmUI() {
        VideoInfoActivity v = (VideoInfoActivity)getActivity();
        for (Spview s : v.spviewInfo.spviews) {
            Button b = new Button(v);
            b.setText(String.valueOf(s.getEpisode()));
            ((GridLayout) getView().findViewById(R.id.bgmbtn_container)).addView(b);
        }
    }
}
