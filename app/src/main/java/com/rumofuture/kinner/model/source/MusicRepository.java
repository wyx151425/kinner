package com.rumofuture.kinner.model.source;

import android.support.annotation.NonNull;

import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Music;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by WangZhenqi on 2017/4/15.
 */

public class MusicRepository implements MusicDataSource {

    private static MusicRepository sInstance;
    private final MusicDataSource mPageLocalDataSource;
    private final MusicDataSource mPageRemoteDataSource;

    public static MusicRepository getInstance(
            @NonNull MusicDataSource pageLocalDataSource,
            @NonNull MusicDataSource pageRemoteDataSource
    ) {
        if (null == sInstance) {
            sInstance = new MusicRepository(pageLocalDataSource, pageRemoteDataSource);
        }
        return sInstance;
    }

    private MusicRepository(
            @NonNull MusicDataSource pageLocalDataSource,
            @NonNull MusicDataSource pageRemoteDataSource
    ) {
        mPageLocalDataSource = pageLocalDataSource;
        mPageRemoteDataSource = pageRemoteDataSource;
    }

    @Override
    public void savePage(Music music, PageSaveCallback callback) {
        mPageRemoteDataSource.savePage(music, callback);
    }

    @Override
    public void deletePage(Music music, PageDeleteCallback callback) {
        mPageRemoteDataSource.deletePage(music, callback);
    }

    @Override
    public void updatePage(Music music, BmobFile newImage, PageUpdateCallback callback) {
        mPageRemoteDataSource.updatePage(music, newImage, callback);
    }

    @Override
    public void getPageListByBook(Album album, int pageCode, PageListGetCallback callBack) {
        mPageRemoteDataSource.getPageListByBook(album, pageCode, callBack);
    }

    @Override
    public void getPageTotal(Album album, TotalGetCallback callback) {
        mPageRemoteDataSource.getPageTotal(album, callback);
    }
}
