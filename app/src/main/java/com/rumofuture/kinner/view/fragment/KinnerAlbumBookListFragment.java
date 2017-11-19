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
import com.rumofuture.kinner.app.contract.KinnerAlbumBookListContract;
import com.rumofuture.kinner.app.widget.OnListScrollListener;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Style;
import com.rumofuture.kinner.model.source.AlbumDataSource;
import com.rumofuture.kinner.view.adapter.KinnerStyleAlbumListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class KinnerAlbumBookListFragment extends Fragment implements KinnerAlbumBookListContract.View {

    private static final String ARG_ALBUM = "com.rumofuture.nemo.view.fragment.KinnerAlbumBookListFragment.album";

    private KinnerAlbumBookListContract.Presenter mPresenter;

    private Style mStyle;
    private List<Album> mAlbumList;
    private KinnerStyleAlbumListAdapter mBookListAdapter;

    private OnListScrollListener mScrollListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public KinnerAlbumBookListFragment() {

    }

    public static KinnerAlbumBookListFragment newInstance(Style style) {
        Bundle args = new Bundle();
        KinnerAlbumBookListFragment fragment = new KinnerAlbumBookListFragment();
        args.putSerializable(ARG_ALBUM, style);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mStyle = (Style) getArguments().getSerializable(ARG_ALBUM);
        }
        mAlbumList = new ArrayList<>();
        mBookListAdapter = new KinnerStyleAlbumListAdapter(mAlbumList);
        mScrollListener = new OnListScrollListener(AlbumDataSource.PAGE_LIMIT) {
            @Override
            public void onLoadMore(int pageCode) {
                mPresenter.getAlbumBookList(mStyle.getStyle(), pageCode);
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
                mScrollListener.init();
                mPresenter.getAlbumBookList(mStyle.getStyle(), 0);
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
        mScrollListener.init();
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.getAlbumBookList(mStyle.getStyle(), 0);
    }

    @Override
    public void setPresenter(KinnerAlbumBookListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showAlbumBooksGetSuccess(List<Album> albumList) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mAlbumList.clear();
            mSwipeRefreshLayout.setRefreshing(false);
        }

        mAlbumList.addAll(albumList);
        mBookListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAlbumBooksGetFailed(BmobException e) {
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
