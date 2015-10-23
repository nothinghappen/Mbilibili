package com.wangjin.mbilibili.app.Fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.wangjin.mbilibili.R;
import com.wangjin.mbilibili.app.Activities.FirstActivity;
import com.wangjin.mbilibili.app.Requests.RecommendRequest;
import com.wangjin.mbilibili.app.Utils.HttpRequestUtils;
import com.wangjin.mbilibili.app.Utils.JsonHandler;
import com.wangjin.mbilibili.app.model.Recommend;
import com.wangjin.mbilibili.app.model.RecommendInfo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends BaseFragment {

    private ViewPager mViewPager;
    private PagerAdapter mAdapter;
    private TabLayout mTabLayout;
    private Toolbar toolbar;

    private List<Fragment> fragments;

    private void init(){
        fragments = new ArrayList<>();
        fragments.add(BangumiFragment.newInstance("番剧"));
        fragments.add(BaseFragment.newInstance("推荐"));
        fragments.add(BaseFragment.newInstance("分区"));
        fragments.add(BaseFragment.newInstance("关注"));
        fragments.add(BaseFragment.newInstance("发现"));

    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        toolbar = (Toolbar) v.findViewById(R.id.first_toolbar);
        ((FirstActivity)getActivity()).setSupportActionBar(toolbar);
        mTabLayout = (TabLayout) v.findViewById(R.id.sliding_tabs);
        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        mAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        return v;
    }

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return ((BaseFragment)fragments.get(position)).title;
        }
    }
}
