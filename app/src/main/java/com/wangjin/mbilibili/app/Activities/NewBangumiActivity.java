package com.wangjin.mbilibili.app.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.wangjin.mbilibili.R;
import com.wangjin.mbilibili.app.Requests.BangumiRequest;
import com.wangjin.mbilibili.app.Utils.HttpRequestUtils;
import com.wangjin.mbilibili.app.Utils.JsonHandler;
import com.wangjin.mbilibili.app.model.Bangumi;
import com.wangjin.mbilibili.app.model.BangumiInfo;
import com.wangjin.mbilibili.app.views.BangumiItemView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class NewBangumiActivity extends BaseActivity implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {

    private ActionBar actionBar;

    HttpRequestUtils httpRequestUtils;
    ListView listView;
    Adapter adapter;
    List<Bangumi> bangumis = new ArrayList<>();
    String[] week={"日","一","二","三","四","五","六"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnScrollListener(this);
        adapter = new Adapter(this,0,bangumis);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        httpRequestUtils = HttpRequestUtils.newInstance(this);
        httpRequestUtils.getJson(new BangumiRequest().setType(BangumiRequest.TWO).toString(),
                new HttpRequestUtils.onResponseFinishedListener() {
                    @Override
                    public void onFinish(JSONObject response) {
                        BangumiInfo bangumiInfo = JsonHandler.handleBangumiInfo(response);
                        for (Bangumi b : bangumiInfo.bangumis){
                            bangumis.add(b);
                        }
                        Collections.sort(bangumis);
//                        int day=-1;
//                        for (int i = 0;i < bangumis.size();i++){
//                            if(day != bangumis.get(i).getWeekday()){
//                                day = bangumis.get(i).getWeekday();
//                                Bangumi b = new Bangumi();
//                                b.setWeekday(day-7);
//                                bangumis.add(i,b);
//                            }
//                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        switch (i){
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE://滚动停止

                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时

                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时

                break;
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int firstItemPosition, int i1, int i2) {
        if(firstItemPosition>0){
            actionBar.hide();
        }else {
            actionBar.show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this,VideoInfoActivity.class);
        intent.putExtra("bangumi",bangumis.get(i));
        startActivity(intent);
    }


    class Adapter extends ArrayAdapter<Bangumi>{

        public Adapter(Context context, int resource, List<Bangumi> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int weekDay = bangumis.get(position).getWeekday();
            if (weekDay >= 0) {
                View bangumiItemView = LayoutInflater.from(NewBangumiActivity.this)
                        .inflate(R.layout.bangumi_item_view, parent, false);
                TextView title = (TextView) bangumiItemView.findViewById(R.id.bangumi_title);
                TextView bgmcount = (TextView) bangumiItemView.findViewById(R.id.bgmcount);
                ImageView cover = (ImageView) bangumiItemView.findViewById(R.id.bangumi_cover);
                title.setText(bangumis.get(position).getTitle());
                if (bangumis.get(position).isNew()){
                    bgmcount.setText("第"+bangumis.get(position).getBgmcount()+ "话  new!");
                }else {
                    bgmcount.setText("第"+bangumis.get(position).getBgmcount()+ "话");
                }
                httpRequestUtils.loadImage(bangumis.get(position).getCover(), cover);
                return bangumiItemView;
            }else {
                View v = LayoutInflater.from(NewBangumiActivity.this).inflate(R.layout.bangumi_header,null);
                TextView t = (TextView) v.findViewById(R.id.header);
                t.setText("星期"+week[weekDay+7]);
                return v;
            }

        }
    }
}
