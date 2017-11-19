package com.rumofuture.kinner.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.kinner.app.contract.MyFollowerListContract;
import com.rumofuture.kinner.model.entity.User;
import com.rumofuture.kinner.model.source.UserDataSource;

import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2017/9/8.
 */

public class MyFollowerListPresenter implements MyFollowerListContract.Presenter, UserDataSource.UserListGetCallback,
        UserDataSource.TotalGetCallback, UserDataSource.UserInfoUpdateCallback {

    private MyFollowerListContract.View mView;
    private UserDataSource mUserRepository;

    public MyFollowerListPresenter(
            @NonNull MyFollowerListContract.View view,
            @NonNull UserDataSource userRepository
    ) {
        mView = view;
        mUserRepository = userRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getMyFollowerList(int pageCode) {
        mUserRepository.getFollowerList(
                BmobUser.getCurrentUser(User.class), pageCode, this
        );
        mUserRepository.getFollowerTotal(
                BmobUser.getCurrentUser(User.class), this
        );
    }

    @Override
    public void onUserListGetSuccess(List<User> userList) {
        if (mView.isActive()) {
            mView.showMyFollowerListGetSuccess(userList);
        }
    }

    @Override
    public void onUserListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showMyFollowerListGetFailed(e);
        }
    }

    @Override
    public void onTotalGetSuccess(Integer total) {
        User currentUser = BmobUser.getCurrentUser(User.class);
        if (!Objects.equals(currentUser.getFollowerTotal(), total)) {
            currentUser.setFollowerTotal(total);
            mUserRepository.updateUserInfo(currentUser, this);
        }
    }

    @Override
    public void onTotalGetFailed(BmobException e) {

    }

    @Override
    public void onUserInfoUpdateSuccess() {

    }

    @Override
    public void onUserInfoUpdateFailed(BmobException e) {

    }
}
