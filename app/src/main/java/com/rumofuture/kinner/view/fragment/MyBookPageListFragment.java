package com.rumofuture.kinner.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.contract.MyBookPageListContract;
import com.rumofuture.kinner.app.widget.OnListScrollListener;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Music;
import com.rumofuture.kinner.model.source.MusicDataSource;
import com.rumofuture.kinner.view.adapter.MyAlbumMusicListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

import static android.app.Activity.RESULT_OK;

public class MyBookPageListFragment extends Fragment implements MyBookPageListContract.View {

    private static final String ARG_PARAM = "com.rumofuture.wzq.nemo.view.fragment.MyBookPageListFragment.book";

    private KinnerProgressBarFragment mProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private MyBookPageListContract.Presenter mPresenter;

    private Album mAlbum;
    private Music mMusic;
    private int mIndexOfPageToUpdate = -1;
    private List<Music> mMusicList;
    private MyAlbumMusicListAdapter mPageListAdapter;

    private OnListScrollListener mScrollListener;

    public MyBookPageListFragment() {

    }

    public static MyBookPageListFragment newInstance(@NonNull Album album) {
        MyBookPageListFragment fragment = new MyBookPageListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM, album);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlbum = (Album) getArguments().getSerializable(ARG_PARAM);
        mMusic = new Music();
        mMusic.setAlbum(mAlbum);

        mProgressBar = KinnerProgressBarFragment.newInstance(getString(R.string.prompt_loading));

        mMusicList = new ArrayList<>();
        mPageListAdapter = new MyAlbumMusicListAdapter(mMusicList, this);
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
                mScrollListener.init();
                mPresenter.getBookPageList(mAlbum, 0);
            }
        });

        RecyclerView pageListView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        pageListView.setLayoutManager(layoutManager);
        pageListView.setAdapter(mPageListAdapter);

        mScrollListener.setLayoutManager(layoutManager);
        pageListView.addOnScrollListener(mScrollListener);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.uploadPage(mMusic);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mSwipeRefreshLayout.setRefreshing(true);
        mScrollListener.init();
        mPresenter.getBookPageList(mAlbum, 0);
    }

    @Override
    public void setPresenter(MyBookPageListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showPageSaveSuccess(Music music) {
        Toast.makeText(getActivity(), R.string.prompt_save_success, Toast.LENGTH_LONG).show();
        mMusicList.add(music);
        mPageListAdapter.notifyDataSetChanged();
        mMusic = new Music();
        mMusic.setAlbum(mAlbum);
    }

    @Override
    public void showPageSaveFailed(BmobException e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showPageDeleteSuccess(Music music) {
        Toast.makeText(getActivity(), R.string.prompt_delete_success, Toast.LENGTH_LONG).show();
        mMusicList.remove(music);
        mPageListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showPageDeleteFailed(BmobException e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showPageUpdateSuccess(Music music) {
        Toast.makeText(getActivity(), R.string.prompt_update_success, Toast.LENGTH_LONG).show();
        mMusicList.set(mIndexOfPageToUpdate, music);
        mPageListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showPageUpdateFailed(BmobException e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
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
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showProgressBar(boolean show, int stringId) {
        if (show) {
            mProgressBar.setPrompt(getString(stringId));
            mProgressBar.show(getFragmentManager(), null);
        } else {
            mProgressBar.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode && 291 == requestCode) {
            mPresenter.submitChoice(requestCode, data);
        }
    }

    public void actionDeletePage(Music music) {
        mPresenter.deletePage(music);
    }

    public void actionUpdatePage(Music music, int index) {
        mPresenter.updatePage(music);
        mIndexOfPageToUpdate = index;
    }
}
