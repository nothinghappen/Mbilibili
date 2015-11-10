package com.wangjin.mbilibili.app.Activities;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.wangjin.mbilibili.R;
import com.wangjin.mbilibili.app.Fragments.BangumiFragment;
import com.wangjin.mbilibili.app.Fragments.BaseFragment;
import com.wangjin.mbilibili.app.Fragments.MainFragment;
import com.wangjin.mbilibili.app.Requests.RecommendRequest;
import com.wangjin.mbilibili.app.Utils.HttpRequestUtils;
import com.wangjin.mbilibili.app.Utils.JsonHandler;
import com.wangjin.mbilibili.app.model.Recommend;
import com.wangjin.mbilibili.app.model.RecommendInfo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends ActionBarActivity
        implements BaseFragment.OnFragmentInteractionListener{

    private Fragment mainFragment;
    public DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        HttpRequestUtils.newInstance(this);
        mainFragment = new MainFragment();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,mainFragment).commit();
    }



}
