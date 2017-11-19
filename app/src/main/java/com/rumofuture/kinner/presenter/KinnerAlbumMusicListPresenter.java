package com.rumofuture.kinner.presenter;

import com.rumofuture.kinner.app.contract.KinnerPageListContract;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Music;
import com.rumofuture.kinner.model.source.MusicDataSource;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/23.
 */

public class KinnerAlbumMusicListPresenter implements KinnerPageListContract.Presenter, MusicDataSource.PageListGetCallback {

    private KinnerPageListContract.View mView;
    private MusicDataSource mPageRepository;

    public KinnerAlbumMusicListPresenter(KinnerPageListContract.View view, MusicDataSource pageRepository) {
        mView = view;
        mPageRepository = pageRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getBookPageList(Album album, int pageCode) {
        mPageRepository.getPageListByBook(album, pageCode, this);
    }

    @Override
    public void onPageListGetSuccess(List<Music> musicList) {
        if (mView.isActive()) {
            mView.showPageListGetSuccess(musicList);
        }
    }

    @Override
    public void onPageListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showPageListGetFailed(e);
        }
    }
}
