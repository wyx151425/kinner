package com.rumofuture.kinner.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.kinner.app.contract.KinnerAlbumSearchContract;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.source.AlbumDataSource;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/18.
 */

public class KinnerBookSearchPresenter implements KinnerAlbumSearchContract.Presenter, AlbumDataSource.BookListGetCallback {

    private KinnerAlbumSearchContract.View mView;
    private AlbumDataSource mBookRepository;

    public KinnerBookSearchPresenter(
            @NonNull KinnerAlbumSearchContract.View view,
            @NonNull AlbumDataSource bookRepository
    ) {
        mView = view;
        mBookRepository = bookRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void searchBook(String keyword) {
        mView.showProgressBar(true);
        mBookRepository.searchBook(keyword, this);
    }

    @Override
    public void onBookListGetSuccess(List<Album> albumList) {
        if (mView.isActive()) {
            mView.showBookSearchSuccess(albumList);
            mView.showProgressBar(false);
        }
    }

    @Override
    public void onBookListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showBookSearchFailed(e);
            mView.showProgressBar(false);
        }
    }
}
