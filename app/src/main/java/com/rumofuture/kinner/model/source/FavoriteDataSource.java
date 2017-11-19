package com.rumofuture.kinner.model.source;

import com.rumofuture.kinner.model.entity.Favorite;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/11.
 */

public interface FavoriteDataSource {

    void saveFavorite(Favorite favorite, FavoriteSaveCallback callback);
    void deleteFavorite(Favorite favorite, FavoriteDeleteCallback callback);
    void getFavorite(Favorite favorite, FavoriteGetCallback callback);

    interface FavoriteSaveCallback {
        void onFavoriteSaveSuccess(Favorite favorite);
        void onFavoriteSaveFailed(BmobException e);
    }

    interface FavoriteDeleteCallback {
        void onFavoriteDeleteSuccess(Favorite favorite);
        void onFavoriteDeleteFailed(BmobException e);
    }

    interface FavoriteGetCallback {
        void onFavoriteGetSuccess(Favorite favorite);
        void onFavoriteGetFailed(BmobException e);
    }
}
