package com.rumofuture.kinner.model.source.local;

import android.content.Context;
import android.support.annotation.Nullable;

import com.rumofuture.kinner.model.entity.Style;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.User;
import com.rumofuture.kinner.model.source.AlbumDataSource;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by WangZhenqi on 2017/4/13.
 */

public class AlbumLocalDataSource implements AlbumDataSource {

    private static AlbumLocalDataSource sInstance;

    private Context mContext;

    public static AlbumLocalDataSource getInstance(Context context) {
        if (sInstance == null)
            sInstance = new AlbumLocalDataSource(context);
        return sInstance;
    }

    private AlbumLocalDataSource(Context context) {
        mContext = context;
    }

    @Override
    public void saveBook(Album album, BookSaveCallback callback) {

    }

    @Override
    public void deleteBook(Album album, BookDeleteCallback callback) {

    }

    @Override
    public void updateBook(Album album, @Nullable BmobFile newCover, BookUpdateCallback callback) {

    }

    @Override
    public void getBookListByAuthor(User author, int pageCode, boolean self, BookListGetCallback callback) {

    }

    @Override
    public void getBookListWithAuthor(int pageCode, BookListGetCallback callback) {

    }

    @Override
    public void getBookListByStyle(String style, int pageCode, BookListGetCallback callback) {

    }

    @Override
    public void getFavoriteBookList(User favor, int pageCode, BookListGetCallback callback) {

    }

    @Override
    public void getAuthorBookTotal(User author, boolean self, TotalGetCallback callback) {

    }

    @Override
    public void getAlbumBookTotal(Style style, TotalGetCallback callback) {

    }

    @Override
    public void getFavoriteBookTotal(User favor, TotalGetCallback callback) {

    }

    @Override
    public void searchBook(String keyword, BookListGetCallback callback) {

    }
}
