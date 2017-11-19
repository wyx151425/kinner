package com.rumofuture.kinner.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.kinner.app.contract.KinnerAuthorSearchContract;
import com.rumofuture.kinner.model.entity.User;
import com.rumofuture.kinner.model.source.UserDataSource;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/19.
 */

public class KinnerAuthorSearchPresenter implements KinnerAuthorSearchContract.Presenter, UserDataSource.UserListGetCallback {

    private KinnerAuthorSearchContract.View mView;
    private UserDataSource mUserRepository;

    public KinnerAuthorSearchPresenter(
            @NonNull KinnerAuthorSearchContract.View view,
            @NonNull UserDataSource userRepository
    ) {
        mView = view;
        mUserRepository = userRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void searchAuthor(String keyword) {
        mView.showProgressBar(true);
        mUserRepository.searchAuthor(keyword, this);
    }

    @Override
    public void onUserListGetSuccess(List<User> userList) {
        if (mView.isActive()) {
            mView.showAuthorSearchSuccess(userList);
            mView.showProgressBar(false);
        }
    }

    @Override
    public void onUserListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showAuthorSearchFailed(e);
            mView.showProgressBar(false);
        }
    }
}
