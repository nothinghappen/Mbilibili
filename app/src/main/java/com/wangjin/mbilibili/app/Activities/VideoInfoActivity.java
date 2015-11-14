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
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.wangjin.mbilibili.R;
import com.wangjin.mbilibili.app.Fragments.BaseFragment;
import com.wangjin.mbilibili.app.Fragments.VideoInfoFragment;
import com.wangjin.mbilibili.app.Requests.SpRequest;
import com.wangjin.mbilibili.app.Requests.SpviewRequest;
import com.wangjin.mbilibili.app.Utils.HttpRequestUtils;
import com.wangjin.mbilibili.app.Utils.JsonHandler;
import com.wangjin.mbilibili.app.model.Bangumi;
import com.wangjin.mbilibili.app.model.SpInfo;
import com.wangjin.mbilibili.app.model.Spview;
import com.wangjin.mbilibili.app.model.SpviewInfo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VideoInfoActivity extends ActionBarActivity implements BaseFragment.OnFragmentInteractionListener{

    public HttpRequestUtils httpRequestUtils;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private PagerAdapter mPagerAdapter;

    private ImageView cover;
    private TextView title;
    private TextView click;
    private TextView isbangumi_end;
    public SpInfo spInfo;
    public SpviewInfo spviewInfo;

    private List<Fragment> fragments = new ArrayList<>();

    private void init(){
        fragments.add(VideoInfoFragment.newInstance("番剧详情"));
        fragments.add(BaseFragment.newInstance("评论"));
        mTabLayout = (TabLayout) findViewById(R.id.vidoe_sliding_tabs);
        mViewPager = (ViewPager) findViewById(R.id.video_pager);
        mPagerAdapter = new MPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        cover = (ImageView) findViewById(R.id.sp_cover);
        title = (TextView) findViewById(R.id.sp_title);
        click = (TextView) findViewById(R.id.click);
        isbangumi_end = (TextView) findViewById(R.id.isbangumi_end);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_info);
        init();
        int spid = getIntent().getIntExtra("spid",0);
        httpRequestUtils = HttpRequestUtils.getInstance();
        httpRequestUtils.getJson(new SpRequest().setSpid(spid).toString(),
                new HttpRequestUtils.onResponseFinishedListener() {
                    @Override
                    public void onFinish(JSONObject response) {
                        spInfo = JsonHandler.handleSpInfo(response);
                        title.setText(spInfo.getTitle());
                        if(spInfo.getIsBangumi_end() == 0){
                            isbangumi_end.setText("连载中");
                        }else {
                            isbangumi_end.setText("已完结");
                        }
                        ((VideoInfoFragment) fragments.get(0)).changedesUI();
                        httpRequestUtils.loadImage(spInfo.getCover(), cover);
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });

        httpRequestUtils.getJson(new SpviewRequest().setSpid(spid).setBangumi(1).toString(),
                new HttpRequestUtils.onResponseFinishedListener() {
            @Override
            public void onFinish(JSONObject response) {
                spviewInfo = JsonHandler.handleSpviewInfo(response);
                int clicks = 0;
                for (Spview s : spviewInfo.spviews){
                    clicks = clicks + s.getClick();
                }
                click.setText("播放 " + String.valueOf(clicks));
                ((VideoInfoFragment) fragments.get(0)).changebgmUI();
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

    public interface notifyFragmentChangeUI{
        void changedesUI();
        void changebgmUI();
    }

}
