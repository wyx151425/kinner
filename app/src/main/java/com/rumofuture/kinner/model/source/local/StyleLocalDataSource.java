package com.rumofuture.kinner.model.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.rumofuture.kinner.model.entity.Style;
import com.rumofuture.kinner.model.source.StyleDataSource;

/**
 * Created by WangZhenqi on 2017/9/20.
 */

public class StyleLocalDataSource implements StyleDataSource {

    private final Context mContext;
    private static StyleLocalDataSource sInstance;

    public static StyleLocalDataSource getInstance(@NonNull Context context) {
        if (null == sInstance) {
            sInstance = new StyleLocalDataSource(context);
        }
        return sInstance;
    }

    private StyleLocalDataSource(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public void updateAlbum(Style style, AlbumUpdateCallback callback) {

    }

    @Override
    public void getAlbumByStyle(String style, AlbumGetCallback callback) {

    }

    @Override
    public void getAlbumList(AlbumListGetCallback callback) {

    }
}
