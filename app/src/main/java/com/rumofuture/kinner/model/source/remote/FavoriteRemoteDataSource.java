package com.rumofuture.kinner.model.source.remote;

import com.rumofuture.kinner.model.entity.Favorite;
import com.rumofuture.kinner.model.schema.FavoriteSchema;
import com.rumofuture.kinner.model.source.FavoriteDataSource;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by WangZhenqi on 2017/9/12.
 */

public class FavoriteRemoteDataSource implements FavoriteDataSource {

    private static final FavoriteRemoteDataSource sInstance = new FavoriteRemoteDataSource();

    public static FavoriteRemoteDataSource getInstance() {
        return sInstance;
    }

    private FavoriteRemoteDataSource() {
    }

    @Override
    public void saveFavorite(final Favorite favorite, final FavoriteSaveCallback callback) {
        favorite.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (null == e) {
                    favorite.setObjectId(objectId);
                    callback.onFavoriteSaveSuccess(favorite);
                } else {
                    callback.onFavoriteSaveFailed(e);
                }
            }
        });
    }

    @Override
    public void deleteFavorite(final Favorite favorite, final FavoriteDeleteCallback callback) {
        favorite.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    callback.onFavoriteDeleteSuccess(favorite);
                } else {
                    callback.onFavoriteDeleteFailed(e);
                }
            }
        });
    }

    @Override
    public void getFavorite(Favorite favorite, final FavoriteGetCallback callback) {
        BmobQuery<Favorite> query = new BmobQuery<>();
        query.addWhereEqualTo(FavoriteSchema.Table.Cols.BOOK, favorite.getAlbum());
        query.addWhereEqualTo(FavoriteSchema.Table.Cols.FAVOR, favorite.getFavor());
        query.include(FavoriteSchema.Table.Cols.BOOK);
        query.include(FavoriteSchema.Table.Cols.FAVOR);
        query.findObjects(new FindListener<Favorite>() {
            @Override
            public void done(List<Favorite> favoriteList, BmobException e) {
                if (null == e) {
                    callback.onFavoriteGetSuccess(favoriteList.get(0));
                } else {
                    callback.onFavoriteGetFailed(e);
                }
            }
        });
    }
}
