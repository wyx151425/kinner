package com.rumofuture.kinner.model.source;

import android.support.annotation.Nullable;

import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Style;
import com.rumofuture.kinner.model.entity.User;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/1/29.
 */

public interface AlbumDataSource {

    int PAGE_LIMIT = 32;

    // 保存漫画册的方法
    void saveBook(Album album, BookSaveCallback callback);
    // 删除漫画册的方法
    void deleteBook(Album album, BookDeleteCallback callback);
    // 更新漫画册的方法
    void updateBook(Album album, @Nullable BmobFile newCover, BookUpdateCallback callback);

    // 直接获取漫画册信息和漫画册所属漫画家信息的方法
    void getBookListWithAuthor(int pageCode, BookListGetCallback callback);
    // 根据漫画家获得属于此漫画家的漫画册的方法
    void getBookListByAuthor(User author, int pageCode, boolean self, BookListGetCallback callback);
    // 根据漫画册风格获取指定风格的漫画册
    void getBookListByStyle(String style, int pageCode, BookListGetCallback callback);
    // 获取用户收藏的漫画册
    void getFavoriteBookList(User favor, int pageCode, AlbumDataSource.BookListGetCallback callback);

    void getAuthorBookTotal(User author, boolean self, TotalGetCallback callback);
    void getAlbumBookTotal(Style style, TotalGetCallback callback);
    void getFavoriteBookTotal(User favor, TotalGetCallback callback);

    void searchBook(String keyword, BookListGetCallback callback);

    // 漫画册保存回调接口
    interface BookSaveCallback {
        void onBookSaveSuccess(Album album);
        void onBookSaveFailed(BmobException e);
    }

    // 漫画册删除回调接口
    interface BookDeleteCallback {
        void onBookDeleteSuccess(Album album);
        void onBookDeleteFailed(BmobException e);
    }

    // 漫画册删除回调接口
    interface BookUpdateCallback {
        void onBookUpdateSuccess(Album album);
        void onBookUpdateFailed(BmobException e);
    }

    // 漫画册获取回调接口
    interface BookListGetCallback {
        void onBookListGetSuccess(List<Album> albumList);
        void onBookListGetFailed(BmobException e);
    }

    interface TotalGetCallback {
        void onTotalGetSuccess(Integer total);
        void onTotalGetFailed(BmobException e);
    }
}
