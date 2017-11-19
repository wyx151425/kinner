package com.rumofuture.kinner.model.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.rumofuture.kinner.model.entity.Follow;
import com.rumofuture.kinner.model.source.FollowDataSource;

/**
 * Created by WangZhenqi on 2017/9/12.
 */

public class FollowLocalDataSource implements FollowDataSource {

    private static FollowLocalDataSource sInstance;
    private final Context mContext;

    public static FollowLocalDataSource getInstance(@NonNull Context context) {
        if (null == sInstance) {
            sInstance = new FollowLocalDataSource(context);
        }
        return sInstance;
    }

    private FollowLocalDataSource(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public void saveFollow(Follow follow, FollowSaveCallback callback) {

    }

    @Override
    public void deleteFollow(Follow follow, FollowDeleteCallback callback) {

    }

    @Override
    public void getFollow(Follow follow, FollowGetCallback callback) {

    }
}
