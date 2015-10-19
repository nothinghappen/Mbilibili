package com.wangjin.mbilibili.app.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.wangjin.mbilibili.R;
import com.wangjin.mbilibili.app.Fragments.BaseFragment;
import com.wangjin.mbilibili.app.Requests.SpRequest;
import com.wangjin.mbilibili.app.Requests.SpviewRequest;
import com.wangjin.mbilibili.app.Utils.HttpRequestUtils;
import com.wangjin.mbilibili.app.Utils.JsonHandler;
import com.wangjin.mbilibili.app.model.Bangumi;
import com.wangjin.mbilibili.app.model.SpInfo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VideoInfoActivity extends ActionBarActivity implements BaseFragment.OnFragmentInteractionListener{

    private HttpRequestUtils httpRequestUtils;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private PagerAdapter mPagerAdapter;

    private ImageView cover;

    private List<Fragment> fragments = new ArrayList<>();

    private void init(){
        fragments.add(BaseFragment.newInstance("番剧详情"));
        fragments.add(BaseFragment.newInstance("承包商排行"));
        fragments.add(BaseFragment.newInstance("评论"));
        mTabLayout = (TabLayout) findViewById(R.id.vidoe_sliding_tabs);
        mViewPager = (ViewPager) findViewById(R.id.video_pager);
        mPagerAdapter = new MPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        cover = (ImageView) findViewById(R.id.sp_cover);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_info);
        init();
        Bangumi b = (Bangumi) getIntent().getSerializableExtra("bangumi");
        httpRequestUtils = HttpRequestUtils.newInstance(this);
        httpRequestUtils.getJson(new SpRequest().setSpid(b.getSpid()).toString(),
                new HttpRequestUtils.onResponseFinishedListener() {
                    @Override
                    public void onFinish(JSONObject response) {
                        SpInfo spInfo = JsonHandler.handleSpInfo(response);
                        System.out.println(spInfo.getCover());
                        httpRequestUtils.loadImage(spInfo.getCover(),cover);
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });

    }

    private class MPagerAdapter extends FragmentStatePagerAdapter{

        public MPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragments.get(position);
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
