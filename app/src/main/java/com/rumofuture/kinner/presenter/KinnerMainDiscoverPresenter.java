package com.rumofuture.kinner.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.kinner.app.contract.KinnerMainDiscoverContract;
import com.rumofuture.kinner.model.entity.User;
import com.rumofuture.kinner.model.source.UserDataSource;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/15.
 */

public class KinnerMainDiscoverPresenter implements KinnerMainDiscoverContract.Presenter, UserDataSource.UserListGetCallback {

    private KinnerMainDiscoverContract.View mView;
    private UserDataSource mUserRepository;

    public KinnerMainDiscoverPresenter(
            @NonNull KinnerMainDiscoverContract.View view,
            @NonNull UserDataSource userRepository
    ) {
        mView = view;
        mUserRepository = userRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getAuthorList(int pageCode) {
        if (0 == pageCode) {
            mView.showProgressBar(true);
        }
        mUserRepository.getAuthorList(pageCode, this);
    }

    @Override
    public void onUserListGetSuccess(List<User> authorList) {
        if (mView.isActive()) {
            mView.showAuthorListGetSuccess(authorList);
            mView.showProgressBar(false);
        }
    }

    @Override
    public void onUserListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showAuthorListGetFailed(e);
            mView.showProgressBar(false);
        }
    }
}
