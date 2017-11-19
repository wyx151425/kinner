package com.rumofuture.kinner.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.contract.MyFollowerListContract;
import com.rumofuture.kinner.app.widget.OnListScrollListener;
import com.rumofuture.kinner.model.entity.User;
import com.rumofuture.kinner.model.source.UserDataSource;
import com.rumofuture.kinner.view.adapter.MyFollowerListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class MyFollowerListFragment extends Fragment implements MyFollowerListContract.View {

    private MyFollowerListContract.Presenter mPresenter;

    private List<User> mFollowerList;
    private MyFollowerListAdapter mFollowerListAdapter;

    private OnListScrollListener mScrollListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public MyFollowerListFragment() {

    }

    public static MyFollowerListFragment newInstance() {
        return new MyFollowerListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFollowerList = new ArrayList<>();
        mFollowerListAdapter = new MyFollowerListAdapter(mFollowerList);
        mScrollListener = new OnListScrollListener(UserDataSource.PAGE_LIMIT) {
            @Override
            public void onLoadMore(int pageCode) {
                mPresenter.getMyFollowerList(pageCode);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_swipe_refresh, container, false);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getMyFollowerList(0);
            }
        });

        RecyclerView followerListView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        followerListView.setLayoutManager(layoutManager);
        followerListView.setAdapter(mFollowerListAdapter);

        mScrollListener.setLayoutManager(layoutManager);
        followerListView.addOnScrollListener(mScrollListener);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mScrollListener.init();
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.getMyFollowerList(0);
    }

    @Override
    public void setPresenter(MyFollowerListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMyFollowerListGetSuccess(List<User> followerList) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mFollowerList.clear();
            mSwipeRefreshLayout.setRefreshing(false);
        }

        mFollowerList.addAll(followerList);
        mFollowerListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMyFollowerListGetFailed(BmobException e) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
