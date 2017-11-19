package com.rumofuture.kinner.model.source.local;

import android.content.Context;

import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Music;
import com.rumofuture.kinner.model.source.MusicDataSource;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by WangZhenqi on 2017/4/15.
 */

public class MusicLocalDataSource implements MusicDataSource {

    private static MusicLocalDataSource sInstance;
    private Context mContext;

    public static MusicLocalDataSource getInstance(Context context) {
        if (sInstance == null)
            sInstance = new MusicLocalDataSource(context);
        return sInstance;
    }

    private MusicLocalDataSource(Context context) {
        mContext = context;
    }

    @Override
    public void savePage(Music music, PageSaveCallback callback) {

    }

    @Override
    public void deletePage(Music music, PageDeleteCallback callback) {

    }

    @Override
    public void updatePage(Music music, BmobFile newImage, PageUpdateCallback callback) {

    }

    @Override
    public void getPageListByBook(Album album, int pageCode, PageListGetCallback callBack) {

    }

    @Override
    public void getPageTotal(Album album, TotalGetCallback callback) {

    }
}
