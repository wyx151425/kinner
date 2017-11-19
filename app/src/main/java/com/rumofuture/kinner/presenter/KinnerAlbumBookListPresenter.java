package com.rumofuture.kinner.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.kinner.app.contract.KinnerAlbumBookListContract;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Style;
import com.rumofuture.kinner.model.source.StyleDataSource;
import com.rumofuture.kinner.model.source.AlbumDataSource;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/27.
 */

public class KinnerAlbumBookListPresenter implements KinnerAlbumBookListContract.Presenter, AlbumDataSource.BookListGetCallback,
        AlbumDataSource.TotalGetCallback, StyleDataSource.AlbumUpdateCallback, StyleDataSource.AlbumGetCallback {

    private KinnerAlbumBookListContract.View mView;
    private AlbumDataSource mBookRepository;
    private StyleDataSource mAlbumRepository;

    private Style mStyle;

    public KinnerAlbumBookListPresenter(
            @NonNull KinnerAlbumBookListContract.View view,
            @NonNull AlbumDataSource bookRepository,
            @NonNull StyleDataSource albumRepository
    ) {
        mView = view;
        mBookRepository = bookRepository;
        mAlbumRepository = albumRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getAlbumBookList(String style, int pageCode) {
        mBookRepository.getBookListByStyle(style, pageCode, this);
        mAlbumRepository.getAlbumByStyle(style, this);
    }

    @Override
    public void onBookListGetSuccess(List<Album> albumList) {
        if (mView.isActive()) {
            mView.showAlbumBooksGetSuccess(albumList);
        }
    }

    @Override
    public void onBookListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showAlbumBooksGetFailed(e);
        }
    }

    @Override
    public void onAlbumGetSuccess(Style style) {
        mStyle = style;
        mBookRepository.getAlbumBookTotal(style, this);
    }

    @Override
    public void onAlbumGetFailed(BmobException e) {

    }

    @Override
    public void onTotalGetSuccess(Integer total) {
        mStyle.setAlbumTotal(total);
        mAlbumRepository.updateAlbum(mStyle, this);
    }

    @Override
    public void onTotalGetFailed(BmobException e) {

    }

    @Override
    public void onAlbumUpdateSuccess(Style style) {

    }

    @Override
    public void onAlbumUpdateFailed(BmobException e) {

    }
}
