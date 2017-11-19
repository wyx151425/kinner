package com.rumofuture.kinner.model.source;

import android.support.annotation.NonNull;

import com.rumofuture.kinner.model.entity.Style;

/**
 * Created by WangZhenqi on 2017/9/20.
 */

public class StyleRepository implements StyleDataSource {

    private static StyleRepository sInstance;
    private final StyleDataSource mAlbumLocalDataSource;
    private final StyleDataSource mAlbumRemoteDataSource;

    public static StyleRepository getInstance(
            @NonNull StyleDataSource albumLocalDataSource,
            @NonNull StyleDataSource albumRemoteDataSource
    ) {
        if (null == sInstance) {
            sInstance = new StyleRepository(albumLocalDataSource, albumRemoteDataSource);
        }
        return sInstance;
    }

    private StyleRepository(
            @NonNull StyleDataSource albumLocalDataSource,
            @NonNull StyleDataSource albumRemoteDataSource
    ) {
        mAlbumLocalDataSource = albumLocalDataSource;
        mAlbumRemoteDataSource = albumRemoteDataSource;
    }

    @Override
    public void updateAlbum(Style style, AlbumUpdateCallback callback) {
        mAlbumRemoteDataSource.updateAlbum(style, callback);
    }

    @Override
    public void getAlbumByStyle(String style, AlbumGetCallback callback) {
        mAlbumRemoteDataSource.getAlbumByStyle(style, callback);
    }

    @Override
    public void getAlbumList(AlbumListGetCallback callback) {
        mAlbumRemoteDataSource.getAlbumList(callback);
    }
}
