package com.wangjin.mbilibili.app.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.wangjin.mbilibili.R;
import com.wangjin.mbilibili.app.Activities.NewBangumiActivity;
import com.wangjin.mbilibili.app.Requests.ListRequest;
import com.wangjin.mbilibili.app.Utils.HttpRequestUtils;
import com.wangjin.mbilibili.app.Utils.JsonHandler;
import com.wangjin.mbilibili.app.model.ListInfo;

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

    private RecyclerView mRecyclerView;
    private RecyclerViewHeader header;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private HttpRequestUtils httpRequestUtils;
    private List<com.wangjin.mbilibili.app.model.List> lists = new ArrayList<>();
    private View v;



    public static BangumiFragment newInstance(String s) {
        BangumiFragment fragment = new BangumiFragment();
        fragment.title = s;
        return fragment;
    }

    public BangumiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("BangumiFragment","onDestroy");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("BangumiFragment", "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("BangumiFragment", "onDetach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("BangumiFragment", "onDestroyView");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BangumiFragment", "onCreate");
        httpRequestUtils = HttpRequestUtils.newInstance(getActivity());
        mAdapter = new BangumiAdapter(lists);
        header = RecyclerViewHeader.fromXml(getActivity(),R.layout.recyclerview_header_layout);
        String request = new ListRequest().setPagesize(50).setOrder("hot").setTid(33).toString();
        httpRequestUtils.getJson(request, new HttpRequestUtils.onResponseFinishedListener() {
            @Override
            public void onFinish(JSONObject response) {
                ListInfo listInfo = JsonHandler.handleListInfo(response);
                for (com.wangjin.mbilibili.app.model.List l : listInfo.lists) {
                    lists.add(l);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        if (this.v != null){
            return this.v;
        }
        View v = inflater.inflate(R.layout.fragment_bangumi, container, false);
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.bangumi_recyclerview);
        mRecyclerView.setLayoutManager(mLayoutManager);
        header.attachTo(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        Button b = (Button) v.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NewBangumiActivity.class));
            }
        });

        this.v = v;
        return v;
    }

    private class BangumiAdapter extends RecyclerView.Adapter<BangumiAdapter.ViewHolder> {
        private List<com.wangjin.mbilibili.app.model.List> lists;

        public BangumiAdapter(List<com.wangjin.mbilibili.app.model.List> lists) {
            this.lists = lists;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView cover;
            private TextView title;

            public ViewHolder(View itemView) {
                super(itemView);
                cover = (ImageView) itemView.findViewById(R.id.recommend_cover);
                title = (TextView) itemView.findViewById(R.id.recommend_title);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.item_recommend_view, viewGroup, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.title.setText(lists.get(i).getTitle());
            httpRequestUtils.loadImage(lists.get(i).getPic(), viewHolder.cover);
        }


        @Override
        public int getItemCount() {
            return lists.size();
        }
    }


}
