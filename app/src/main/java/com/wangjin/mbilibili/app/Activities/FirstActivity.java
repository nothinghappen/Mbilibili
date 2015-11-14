package com.wangjin.mbilibili.app.Activities;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.wangjin.mbilibili.R;
import com.wangjin.mbilibili.app.Fragments.BaseFragment;
import com.wangjin.mbilibili.app.Fragments.MainFragment;

public class FirstActivity extends ActionBarActivity
        implements BaseFragment.OnFragmentInteractionListener{

    private Fragment mainFragment;
    public DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        mainFragment = new MainFragment();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,mainFragment).commit();
    }



}
