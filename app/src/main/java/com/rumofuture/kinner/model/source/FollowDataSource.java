package com.rumofuture.kinner.model.source;

import com.rumofuture.kinner.model.entity.Follow;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/11.
 */

public interface FollowDataSource {

    void saveFollow(Follow follow, FollowSaveCallback callback);
    void deleteFollow(Follow follow, FollowDeleteCallback callback);
    void getFollow(Follow follow, FollowGetCallback callback);

    interface FollowSaveCallback {
        void onFollowSaveSuccess(Follow follow);
        void onFollowSaveFailed(BmobException e);
    }

    interface FollowDeleteCallback {
        void onFollowDeleteSuccess(Follow follow);
        void onFollowDeleteFailed(BmobException e);
    }

    interface FollowGetCallback {
        void onFollowGetSuccess(Follow follow);
        void onFollowGetFailed(BmobException e);
    }
}
