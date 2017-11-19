package com.rumofuture.kinner.model.source;

import com.rumofuture.kinner.model.entity.Style;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/20.
 */

public interface StyleDataSource {

    int PAGE_LIMIT = 8;

    void updateAlbum(Style style, AlbumUpdateCallback callback);
    void getAlbumByStyle(String style, AlbumGetCallback callback);
    void getAlbumList(AlbumListGetCallback callback);

    interface AlbumUpdateCallback {
        void onAlbumUpdateSuccess(Style style);
        void onAlbumUpdateFailed(BmobException e);
    }

    interface AlbumGetCallback {
        void onAlbumGetSuccess(Style style);
        void onAlbumGetFailed(BmobException e);
    }

    interface AlbumListGetCallback {
        void onAlbumListGetSuccess(List<Style> styleList);
        void onAlbumListGetFailed(BmobException e);
    }
}
