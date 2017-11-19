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
import com.rumofuture.kinner.app.contract.KinnerPageListContract;
import com.rumofuture.kinner.app.widget.OnListScrollListener;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Music;
import com.rumofuture.kinner.model.source.MusicDataSource;
import com.rumofuture.kinner.view.adapter.KinnerAlbumMusicListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class KinnerBookPageListFragment extends Fragment implements KinnerPageListContract.View {

    private static final String ARG_BOOK = "com.rumofuture.nemo.view.fragment.KinnerBookPageListFragment.book";

    private KinnerPageListContract.Presenter mPresenter;

    private Album mAlbum;
    private List<Music> mMusicList;
    private KinnerAlbumMusicListAdapter mPageListAdapter;

    private OnListScrollListener mScrollListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public KinnerBookPageListFragment() {

    }

    public static KinnerBookPageListFragment newInstance(Album album) {
        Bundle args = new Bundle();
        KinnerBookPageListFragment fragment = new KinnerBookPageListFragment();
        args.putSerializable(ARG_BOOK, album);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mAlbum = (Album) getArguments().getSerializable(ARG_BOOK);
        }
        mMusicList = new ArrayList<>();
        mPageListAdapter = new KinnerAlbumMusicListAdapter(mMusicList);
        mScrollListener = new OnListScrollListener(MusicDataSource.PAGE_LIMIT) {
            @Override
            public void onLoadMore(int pageCode) {
                mPresenter.getBookPageList(mAlbum, pageCode);
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
                mPresenter.getBookPageList(mAlbum, 0);
            }
        });

        RecyclerView pageListView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        pageListView.setLayoutManager(layoutManager);
        pageListView.setAdapter(mPageListAdapter);

        mScrollListener.setLayoutManager(layoutManager);
        pageListView.addOnScrollListener(mScrollListener);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mScrollListener.init();
        mPresenter.getBookPageList(mAlbum, 0);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void setPresenter(KinnerPageListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showPageListGetSuccess(List<Music> musicList) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mMusicList.clear();
            mSwipeRefreshLayout.setRefreshing(false);
        }

        mMusicList.addAll(musicList);
        mPageListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showPageListGetFailed(BmobException e) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        Toast.makeText(getActivity(), R.string.prompt_load_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
