package com.rumofuture.kinner.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.kinner.app.contract.MyBookUpdateContract;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.source.AlbumDataSource;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/11/3.
 */

public class MyBookUpdatePresenter implements MyBookUpdateContract.Presenter, AlbumDataSource.BookUpdateCallback {

    private MyBookUpdateContract.View mView;
    private AlbumDataSource mBookRepository;

    public MyBookUpdatePresenter(
            @NonNull MyBookUpdateContract.View view,
            @NonNull AlbumDataSource bookRepository
    ) {
        mView = view;
        mBookRepository = bookRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void updateBook(Album album) {
        mView.showProgressBar(true);
        mBookRepository.updateBook(album, null, this);
    }

    @Override
    public void onBookUpdateSuccess(Album album) {
        if (mView.isActive()) {
            mView.showProgressBar(false);
            mView.showBookUpdateSuccess(album);
        }
    }

    @Override
    public void onBookUpdateFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showProgressBar(false);
            mView.showBookUpdateFailed(e);
        }
    }
}
