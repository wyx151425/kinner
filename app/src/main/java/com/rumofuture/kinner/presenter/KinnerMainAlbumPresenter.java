package com.rumofuture.kinner.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.kinner.app.contract.KinnerMainStyleContract;
import com.rumofuture.kinner.model.entity.Style;
import com.rumofuture.kinner.model.source.StyleDataSource;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/20.
 */

public class KinnerMainAlbumPresenter implements KinnerMainStyleContract.Presenter, StyleDataSource.AlbumListGetCallback {

    private KinnerMainStyleContract.View mView;
    private StyleDataSource mAlbumRepository;

    public KinnerMainAlbumPresenter(
            @NonNull KinnerMainStyleContract.View view,
            @NonNull StyleDataSource albumRepository
    ) {
        mView = view;
        mAlbumRepository = albumRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getAlbumList() {
        mAlbumRepository.getAlbumList(this);
    }

    @Override
    public void onAlbumListGetSuccess(List<Style> styleList) {
        if (mView.isActive()) {
            mView.showAlbumListGetSuccess(styleList);
        }
    }

    @Override
    public void onAlbumListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showAlbumListGetFailed(e);
        }
    }
}
