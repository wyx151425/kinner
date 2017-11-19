package com.rumofuture.kinner.model.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.rumofuture.kinner.model.entity.Favorite;
import com.rumofuture.kinner.model.source.FavoriteDataSource;

/**
 * Created by WangZhenqi on 2017/9/12.
 */

public class FavoriteLocalDataSource implements FavoriteDataSource {

    private static FavoriteLocalDataSource sInstance;
    private final Context mContext;

    public static FavoriteLocalDataSource getInstance(@NonNull Context context) {
        if (null == sInstance) {
            sInstance = new FavoriteLocalDataSource(context);
        }
        return sInstance;
    }

    private FavoriteLocalDataSource(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public void saveFavorite(Favorite favorite, FavoriteSaveCallback callback) {

    }

    @Override
    public void deleteFavorite(Favorite favorite, FavoriteDeleteCallback callback) {

    }

    @Override
    public void getFavorite(Favorite favorite, FavoriteGetCallback callback) {

    }
}
