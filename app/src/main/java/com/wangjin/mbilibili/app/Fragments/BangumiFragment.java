package com.wangjin.mbilibili.app.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangjin.mbilibili.R;
import com.wangjin.mbilibili.app.Activities.NewBangumiActivity;
import com.wangjin.mbilibili.app.Utils.HttpRequestUtils;
import com.wangjin.mbilibili.app.model.Bangumi;

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

    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private HttpRequestUtils httpRequestUtils;
    private List<Bangumi> bangumis = new ArrayList<>();


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
//        httpRequestUtils = HttpRequestUtils.newInstance(getActivity());
//        mAdapter = new BangumiAdapter(bangumis);
//        mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bangumi, container, false);

        Button b = (Button) v.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),NewBangumiActivity.class));
            }
        });

//        mRecyclerView = (RecyclerView) v.findViewById(R.id.bangumi_recyclerview);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setAdapter(mAdapter);
//        httpRequestUtils.getJson(new BangumiRequest().setType(BangumiRequest.TWO).toString(),
//                new HttpRequestUtils.onResponseFinishedListener() {
//                    @Override
//                    public void onFinish(JSONObject response) {
//                        BangumiInfo bangumiInfo = JsonHandler.handleBangumiInfo(response);
//                        for (Bangumi b : bangumiInfo.bangumis) {
//                            bangumis.add(b);
//                        }
//                        mAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onError(VolleyError error) {
//
//                    }
//                });
        return v;
    }


//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

    }

    private class BangumiAdapter extends RecyclerView.Adapter<BangumiAdapter.ViewHolder>{
        private List<Bangumi> bangumis;

        public BangumiAdapter(List<Bangumi> bangumis){
            this.bangumis = bangumis;
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            private ImageView cover;
            private TextView title;

            public ViewHolder(View itemView) {
                super(itemView);
                cover = (ImageView) itemView.findViewById(R.id.bangumi_cover);
                title = (TextView) itemView.findViewById(R.id.bangumi_title);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.bangumi_item_view,viewGroup,false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.title.setText(bangumis.get(i).getTitle());
            httpRequestUtils.loadImage(bangumis.get(i).getCover(),viewHolder.cover);
        }


        @Override
        public int getItemCount() {
            return bangumis.size();
        }
    }



}
