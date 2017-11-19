package com.rumofuture.kinner.model.source;

import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Music;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/1/29.
 */

public interface MusicDataSource {

    int PAGE_LIMIT = 64;

    void savePage(Music music, PageSaveCallback callback);
    void deletePage(Music music, PageDeleteCallback callback);
    void updatePage(Music music, BmobFile newImage, final PageUpdateCallback callback);
    void getPageListByBook(Album album, int pageCode, PageListGetCallback callBack);
    void getPageTotal(Album album, TotalGetCallback callback);

    interface PageSaveCallback {
        void onPageSaveSuccess(Music music);
        void onPageSaveFailed(BmobException e);
    }

    interface PageDeleteCallback {
        void onPageDeleteSuccess(Music music);
        void onPageDeleteFailed(BmobException e);
    }

    interface PageUpdateCallback {
        void onPageUpdateSuccess(Music music);
        void onPageUpdateFailed(BmobException e);
    }

    interface PageListGetCallback {
        void onPageListGetSuccess(List<Music> musicList);
        void onPageListGetFailed(BmobException e);
    }

    interface TotalGetCallback {
        void onTotalGetSuccess(Integer total);
        void onTotalGetFailed(BmobException e);
    }
}
