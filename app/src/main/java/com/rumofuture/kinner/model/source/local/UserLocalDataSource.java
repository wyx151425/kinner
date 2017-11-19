package com.rumofuture.kinner.model.source.local;

import android.content.Context;

import com.rumofuture.kinner.model.entity.User;
import com.rumofuture.kinner.model.source.UserDataSource;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by WangZhenqi on 2017/4/16.
 */

public class UserLocalDataSource implements UserDataSource {

    private Context mContext;
    private static UserLocalDataSource sInstance;

    public static UserLocalDataSource getInstance(Context context) {
        if (sInstance == null)
            sInstance = new UserLocalDataSource(context);
        return sInstance;
    }

    private UserLocalDataSource(Context context) {
        mContext = context;
    }

    @Override
    public void logIn(User user, UserLogInCallback callback) {

    }

    @Override
    public void signUp(User user, UserSignUpCallback callback) {

    }

    @Override
    public void updateUserAvatar(BmobFile newAvatar, UserImageUpdateCallback callback) {

    }

    @Override
    public void updateUserPortrait(BmobFile newPortrait, UserImageUpdateCallback callback) {

    }

    @Override
    public void updateUserInfo(User user, UserInfoUpdateCallback callback) {

    }

    @Override
    public void getAuthorList(int pageCode, UserListGetCallback callback) {

    }

    @Override
    public void getFollowAuthorList(User follower, int pageCode, UserListGetCallback callback) {

    }

    @Override
    public void getFollowerList(User author, int pageCode, UserListGetCallback callback) {

    }

    @Override
    public void getFollowAuthorTotal(User follower, TotalGetCallback callback) {

    }

    @Override
    public void getFollowerTotal(User author, TotalGetCallback callback) {

    }

    @Override
    public void searchAuthor(String keyword, UserListGetCallback callback) {

    }
}
