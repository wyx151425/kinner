package com.rumofuture.kinner.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.kinner.app.contract.KinnerMainContract;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.source.AlbumDataSource;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/16.
 */

public class KinnerMainPresenter implements KinnerMainContract.Presenter, AlbumDataSource.BookListGetCallback {

    private KinnerMainContract.View mView;
    private AlbumDataSource mBookRepository;

    public KinnerMainPresenter(
            @NonNull KinnerMainContract.View view,
            @NonNull AlbumDataSource bookRepository
    ) {
        mView = view;
        mBookRepository = bookRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getBookList(int pageCode) {
        if (0 == pageCode) {
            mView.showProgressBar(true);
        }
        mBookRepository.getBookListWithAuthor(pageCode, this);
    }

    @Override
    public void onBookListGetSuccess(List<Album> albumList) {
        if (mView.isActive()) {
            mView.showBookListGetSuccess(albumList);
            mView.showProgressBar(false);
        }
    }

    @Override
    public void onBookListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showBookListGetFailed(e);
            mView.showProgressBar(false);
        }
    }
}
