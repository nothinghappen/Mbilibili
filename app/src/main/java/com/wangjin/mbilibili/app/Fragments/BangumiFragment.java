package com.wangjin.mbilibili.app.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.wangjin.mbilibili.R;
import com.wangjin.mbilibili.app.Activities.NewBangumiActivity;
import com.wangjin.mbilibili.app.Activities.VideoInfoActivity;
import com.wangjin.mbilibili.app.Requests.ListRequest;
import com.wangjin.mbilibili.app.Utils.HttpRequestUtils;
import com.wangjin.mbilibili.app.Utils.JsonHandler;
import com.wangjin.mbilibili.app.model.ListInfo;
import com.wangjin.mbilibili.app.model.RecommendBangumi;
import com.wangjin.mbilibili.app.views.UnScrollableGridView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BangumiFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BangumiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BangumiFragment extends BaseFragment {

    private int dpi;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private HttpRequestUtils httpRequestUtils;
    private List<RecommendBangumi> lists = new ArrayList<>();
    private List<com.wangjin.mbilibili.app.model.List> hot_bangumis = new ArrayList<>();


    public static BangumiFragment newInstance(String s) {
        BangumiFragment fragment = new BangumiFragment();
        fragment.title = s;
        return fragment;
    }

    public BangumiFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        dpi = metric.densityDpi;

        httpRequestUtils = HttpRequestUtils.newInstance(getActivity());
        mAdapter = new BangumiAdapter(lists,hot_bangumis);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_bangumi, container, false);
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.bangumi_recyclerview);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        initData();
        return v;
    }

    private class BangumiAdapter extends RecyclerView.Adapter {
        static final int HEADER = 0;
        static final int ITEM = 1;
        static final int MAIN_HEADER = 2;
        static final int HOT_ITEM = 3;

        private List<RecommendBangumi> lists;
        private List<com.wangjin.mbilibili.app.model.List> hot_bangumis;

        public BangumiAdapter(List<RecommendBangumi> lists,List<com.wangjin.mbilibili.app.model.List> hot_bangumis) {
            this.lists = lists;
            this.hot_bangumis = hot_bangumis;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == HEADER) {
                View header = LayoutInflater.from(getActivity()).inflate(R.layout.recyclerview_header_layout, parent, false);
                ((StaggeredGridLayoutManager.LayoutParams) header.getLayoutParams()).setFullSpan(true);
                HeaderViewHodler hvh = new HeaderViewHodler(header);
                return hvh;
            } else if (viewType == ITEM) {
                View item = LayoutInflater.from(getActivity()).inflate(R.layout.item_recommend_view, parent, false);
                ItemViewHodler ivh = new ItemViewHodler(item);
                return ivh;
            } else if (viewType == MAIN_HEADER){
                View v = LayoutInflater.from(getActivity()).inflate(R.layout.bangumi_header_layout,parent,false);
                ((StaggeredGridLayoutManager.LayoutParams) v.getLayoutParams()).setFullSpan(true);
                MainHeaderHolder mhvh = new MainHeaderHolder(v);
                return mhvh;
            }else if (viewType == HOT_ITEM){
                View v = LayoutInflater.from(getActivity()).inflate(R.layout.item_hot_bangumi,parent,false);
                HotBangumiViewHolder hbvh = new HotBangumiViewHolder(v);
                return hbvh;
            }else {
                return null;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (holder != null) {
                if (holder instanceof MainHeaderHolder){
                    ((MainHeaderHolder) holder).btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getActivity(),NewBangumiActivity.class));
                        }
                    });
                }
                if (holder instanceof HeaderViewHodler){
                    if (position == 1){
                        HeaderViewHodler headerViewHodler = (HeaderViewHodler) holder;
                        headerViewHodler.title.setText("本区热门");
                    }else {
                        HeaderViewHodler headerViewHodler = (HeaderViewHodler) holder;
                        headerViewHodler.title.setText("推荐番剧");
                    }
                }
                if (holder instanceof ItemViewHodler) {
                    ItemViewHodler ivh = (ItemViewHodler) holder;
                    ivh.title.setText(lists.get(position - 7).getTitle());
                    ivh.cover.setMinimumWidth(lists.get(position - 7).getImg_width());
                    ivh.cover.setMinimumHeight(lists.get(position - 7).getImg_heigth() * (dpi / 320));
                    httpRequestUtils.loadImage(lists.get(position - 7).getCover(), ivh.cover);
                    ((ItemViewHodler) holder).mItemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getActivity(),VideoInfoActivity.class);
                            i.putExtra("spid",lists.get(position-7).getSpid());
                            startActivity(i);
                        }
                    });
                }
                if (holder instanceof HotBangumiViewHolder){
                    HotBangumiViewHolder hotBangumiViewHolder = (HotBangumiViewHolder) holder;
                    hotBangumiViewHolder.title.setText(hot_bangumis.get(position-2).getTitle());
                    httpRequestUtils.loadImage(hot_bangumis.get(position-2).getPic(),hotBangumiViewHolder.cover);
                }
            }
        }

        @Override
        public int getItemCount() {
            return hot_bangumis.isEmpty()?2:lists.size()+hot_bangumis.size()+3;
        }

        private class ItemViewHodler extends RecyclerView.ViewHolder {
            private TextView title;
            private ImageView cover;
            public View mItemView;
            public ItemViewHodler(View itemView) {
                super(itemView);
                mItemView = itemView;
                title = (TextView) itemView.findViewById(R.id.recommend_title);
                cover = (ImageView) itemView.findViewById(R.id.recommend_cover);
            }
        }

        private class HotBangumiViewHolder extends RecyclerView.ViewHolder{
            TextView title;
            ImageView cover;
            public HotBangumiViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.hot_bangumi_title);
                cover = (ImageView) itemView.findViewById(R.id.hot_bangumi_cover);
            }
        }

        private class HeaderViewHodler extends RecyclerView.ViewHolder {
            TextView title;
            public HeaderViewHodler(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.header_title);
            }
        }

        private class MainHeaderHolder extends RecyclerView.ViewHolder{
            private Button btn;
            public MainHeaderHolder(View itemView) {
                super(itemView);
                btn = (Button) itemView.findViewById(R.id.NewBangumi);
            }
        }

        @Override
        public int getItemViewType(int position) {
            switch (position){
                case 0:
                    return MAIN_HEADER;
                case 2:
                case 3:
                case 4:
                case 5:
                    return HOT_ITEM;
                case 1:
                    return HEADER;
                case 6:
                    return HEADER;
                default:
                    return ITEM;
            }
        }
    }

    private void initData(){
        if (lists.isEmpty()) {
            String request = "http://app.bilibili.com/bangumi/operation_module?_device=android&_hwid=ccbb856c97ccb8d2&appkey=c1b107428d337928&build=406001&channel=baidu&module=bangumi&platform=android&screen=xhdpi&test=0&ts=1445825927000&sign=9a1c61ecd5d46c1343ac0f1428ca8cc8";
            httpRequestUtils.getJson(request, new HttpRequestUtils.onResponseFinishedListener() {
                @Override
                public void onFinish(JSONObject response) {
                    List<RecommendBangumi> recommendBangumis = JsonHandler.handleRecommendBangumi(response);
                    lists.addAll(recommendBangumis);
                    if (!hot_bangumis.isEmpty())
                        mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        }
        if (hot_bangumis.isEmpty()) {
            String Get = new ListRequest().setOrder("hot").setPagesize(4).setTid(33).toString();
            httpRequestUtils.getJson(Get, new HttpRequestUtils.onResponseFinishedListener() {
                @Override
                public void onFinish(JSONObject response) {
                    ListInfo listInfo = JsonHandler.handleListInfo(response);
                    hot_bangumis.addAll(listInfo.lists);
                    if (!lists.isEmpty())
                        mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        }
    }


}
