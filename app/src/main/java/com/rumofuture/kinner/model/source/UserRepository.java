package com.rumofuture.kinner.model.source;

import android.support.annotation.NonNull;

import com.rumofuture.kinner.model.entity.User;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by WangZhenqi on 2017/4/16.
 */

public class UserRepository implements UserDataSource {

    private static UserRepository sInstance;
    private final UserDataSource mUserLocalDataSource;
    private final UserDataSource mUserRemoteDataSource;

    public static UserRepository getInstance(
            @NonNull UserDataSource userLocalDataSource,
            @NonNull UserDataSource userRemoteDataSource
    ) {
        if (null == sInstance) {
            sInstance = new UserRepository(userLocalDataSource, userRemoteDataSource);
        }
        return sInstance;
    }

    private UserRepository(
            @NonNull UserDataSource userLocalDataSource,
            @NonNull UserDataSource userRemoteDataSource
    ) {
        mUserLocalDataSource = userLocalDataSource;
        mUserRemoteDataSource = userRemoteDataSource;
    }

    @Override
    public void logIn(User user, UserLogInCallback callback) {
        mUserRemoteDataSource.logIn(user, callback);
    }

    @Override
    public void signUp(User user, UserSignUpCallback callback) {
        mUserRemoteDataSource.signUp(user, callback);
    }

    @Override
    public void updateUserAvatar(BmobFile newAvatar, UserImageUpdateCallback callback) {
        mUserRemoteDataSource.updateUserAvatar(newAvatar, callback);
    }

    @Override
    public void updateUserPortrait(BmobFile newPortrait, UserImageUpdateCallback callback) {
        mUserRemoteDataSource.updateUserPortrait(newPortrait, callback);
    }

    @Override
    public void updateUserInfo(User user, UserInfoUpdateCallback callback) {
        mUserRemoteDataSource.updateUserInfo(user, callback);
    }

    @Override
    public void getAuthorList(int pageCode, UserListGetCallback callback) {
        mUserRemoteDataSource.getAuthorList(pageCode, callback);
    }

    @Override
    public void getFollowAuthorList(User follower, int pageCode, UserListGetCallback callback) {
        mUserRemoteDataSource.getFollowAuthorList(follower, pageCode, callback);
    }

    @Override
    public void getFollowerList(User author, int pageCode, UserListGetCallback callback) {
        mUserRemoteDataSource.getFollowerList(author, pageCode, callback);
    }

    @Override
    public void getFollowAuthorTotal(User follower, TotalGetCallback callback) {
        mUserRemoteDataSource.getFollowAuthorTotal(follower, callback);
    }

    @Override
    public void getFollowerTotal(User author, TotalGetCallback callback) {
        mUserRemoteDataSource.getFollowerTotal(author, callback);
    }

    @Override
    public void searchAuthor(String keyword, UserListGetCallback callback) {
        mUserRemoteDataSource.searchAuthor(keyword, callback);
    }
}
