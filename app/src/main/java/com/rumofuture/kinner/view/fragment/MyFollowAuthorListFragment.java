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
import com.rumofuture.kinner.app.contract.MyFollowAuthorListContract;
import com.rumofuture.kinner.app.widget.OnListScrollListener;
import com.rumofuture.kinner.model.entity.User;
import com.rumofuture.kinner.model.source.UserDataSource;
import com.rumofuture.kinner.view.adapter.MyFollowAuthorListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class MyFollowAuthorListFragment extends Fragment implements MyFollowAuthorListContract.View {

    private MyFollowAuthorListContract.Presenter mPresenter;

    private OnListScrollListener mScrollListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private List<User> mAuthorList;
    private MyFollowAuthorListAdapter mAuthorListAdapter;

    public MyFollowAuthorListFragment() {
    }

    public static MyFollowAuthorListFragment newInstance() {
        return new MyFollowAuthorListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuthorList = new ArrayList<>();
        mAuthorListAdapter = new MyFollowAuthorListAdapter(mAuthorList);
        mScrollListener = new OnListScrollListener(UserDataSource.PAGE_LIMIT) {
            @Override
            public void onLoadMore(int pageCode) {
                mPresenter.getMyFollowAuthorList(pageCode);
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
                mPresenter.getMyFollowAuthorList(0);
            }
        });

        RecyclerView authorListView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        authorListView.setLayoutManager(layoutManager);
        authorListView.setAdapter(mAuthorListAdapter);

        mScrollListener.setLayoutManager(layoutManager);
        authorListView.addOnScrollListener(mScrollListener);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mScrollListener.init();
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.getMyFollowAuthorList(0);
    }

    @Override
    public void setPresenter(MyFollowAuthorListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showFollowUserListGetSuccess(List<User> authorList) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mAuthorList.clear();
            mSwipeRefreshLayout.setRefreshing(false);
        }

        mAuthorList.addAll(authorList);
        mAuthorListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFollowUserListGetFailed(BmobException e) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        Toast.makeText(getActivity(), R.string.prompt_load_failed, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
