package com.rumofuture.kinner.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.contract.MyFavoriteBookListContract;
import com.rumofuture.kinner.app.widget.OnListScrollListener;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.source.AlbumDataSource;
import com.rumofuture.kinner.view.adapter.MyFavoriteAlbumListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class MyFavoriteBookListFragment extends Fragment implements MyFavoriteBookListContract.View {

    private MyFavoriteBookListContract.Presenter mPresenter;

    private List<Album> mAlbumList;
    private MyFavoriteAlbumListAdapter mBookListAdapter;

    private OnListScrollListener mScrollListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public MyFavoriteBookListFragment() {

    }

    public static MyFavoriteBookListFragment newInstance() {
        return new MyFavoriteBookListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlbumList = new ArrayList<>();
        mBookListAdapter = new MyFavoriteAlbumListAdapter(mAlbumList);
        mScrollListener = new OnListScrollListener(AlbumDataSource.PAGE_LIMIT) {
            @Override
            public void onLoadMore(int pageCode) {
                mPresenter.getMyFavoriteBookList(pageCode);
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
                mPresenter.getMyFavoriteBookList(0);
            }
        });

        RecyclerView bookListView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        bookListView.setLayoutManager(layoutManager);
        bookListView.setAdapter(mBookListAdapter);

        mScrollListener.setLayoutManager(layoutManager);
        bookListView.addOnScrollListener(mScrollListener);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 重新赋值一遍，避免界面销毁时数据仍保存
        mScrollListener.init();
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.getMyFavoriteBookList(0);
    }

    @Override
    public void setPresenter(MyFavoriteBookListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMyFavoriteBookListGetSuccess(List<Album> albumList) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mAlbumList.clear();
            mSwipeRefreshLayout.setRefreshing(false);
        }

        mAlbumList.addAll(albumList);
        mBookListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMyFavoriteBookListGetFailed(BmobException e) {
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
