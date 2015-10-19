package com.wangjin.mbilibili.app.Activities;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.wangjin.mbilibili.R;
import com.wangjin.mbilibili.app.Fragments.BangumiFragment;
import com.wangjin.mbilibili.app.Fragments.BaseFragment;
import com.wangjin.mbilibili.app.Fragments.NavigationDrawerFragment;
import com.wangjin.mbilibili.app.Requests.RecommendRequest;
import com.wangjin.mbilibili.app.Utils.HttpRequestUtils;
import com.wangjin.mbilibili.app.Utils.JsonHandler;
import com.wangjin.mbilibili.app.model.Recommend;
import com.wangjin.mbilibili.app.model.RecommendInfo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks ,BaseFragment.OnFragmentInteractionListener{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ViewPager mViewPager;
    private PagerAdapter mAdapter;
    private TabLayout mTabLayout;
    private HttpRequestUtils httpRequestUtils;

    private List<Fragment> fragments;

    private void init(){
        fragments = new ArrayList<>();
        fragments.add(BangumiFragment.newInstance("番剧"));
        fragments.add(BaseFragment.newInstance("推荐"));
        fragments.add(BaseFragment.newInstance("分区"));
        fragments.add(BaseFragment.newInstance("关注"));
        fragments.add(BaseFragment.newInstance("发现"));

        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        httpRequestUtils = HttpRequestUtils.newInstance(this);
        init();
//        String request = new RecommendRequest().setPagesize(100).setPage(3).toString();
//        System.out.println(request);
//        httpRequestUtils.getJson(request, new HttpRequestUtils.onResponseFinishedListener() {
//            @Override
//            public void onFinish(JSONObject response) {
//                RecommendInfo recommendInfo = JsonHandler.handleRecommendInfo(response);
//                System.out.println(recommendInfo.getNum());
//                for (Recommend r : recommendInfo.recommends){
//                    System.out.println(r.getTypeid() + "  " + r.getTypename());
//                }
//            }
//
//            @Override
//            public void onError(VolleyError error) {
//
//            }
//        });
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
//        switch (position){
//            case 0:
//                nowFragment = PlaceholderFragment.newInstance(1);
//                break;
//            case 1:
//                nowFragment = PlaceholderFragment.newInstance(2);
//                break;
//            case 2:
//                nowFragment = PlaceholderFragment.newInstance(3);
//                break;
//            default:
//                nowFragment = PlaceholderFragment.newInstance(1);
//        }
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.container, nowFragment)
//                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

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
