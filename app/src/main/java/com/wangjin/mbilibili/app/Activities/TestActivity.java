package com.wangjin.mbilibili.app.Activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangjin.mbilibili.R;
import com.wangjin.mbilibili.app.model.List;

public class TestActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    Toolbar mToolbar;
    String[] s = new String[1000];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        for (int i = 0; i < s.length; i++) {
            s[i] = "caonima";
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.test_rec);
        mToolbar = (Toolbar) findViewById(R.id.test_toolbar);
        setSupportActionBar(mToolbar);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new BangumiAdapter());
    }

    private class BangumiAdapter extends RecyclerView.Adapter<BangumiAdapter.ViewHolder> {

        public BangumiAdapter() {

        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView t;

            public ViewHolder(TextView itemView) {
                super(itemView);
                t = itemView;
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            TextView t = new TextView(TestActivity.this);
            ViewHolder vh = new ViewHolder(t);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            TextView t = viewHolder.t;
            t.setText(s[i]);
        }


        @Override
        public int getItemCount() {
            return s.length;
        }
    }


}
