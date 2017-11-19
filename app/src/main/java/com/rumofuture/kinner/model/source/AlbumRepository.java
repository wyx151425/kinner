package com.rumofuture.kinner.model.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.rumofuture.kinner.model.entity.Style;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.User;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by WangZhenqi on 2017/4/13.
 */

public class AlbumRepository implements AlbumDataSource {

    private static AlbumRepository sInstance;

    private final AlbumDataSource mBookLocalDataSource;
    private final AlbumDataSource mBookRemoteDataSource;

    public static AlbumRepository getInstance(
            @NonNull AlbumDataSource nemoLocalDataSource,
            @NonNull AlbumDataSource nemoRemoteDataSource
    ) {
        if (null == sInstance) {
            sInstance = new AlbumRepository(nemoLocalDataSource, nemoRemoteDataSource);
        }
        return sInstance;
    }

    private AlbumRepository(
            @NonNull AlbumDataSource bookLocalDataSource,
            @NonNull AlbumDataSource bookRemoteDataSource
    ) {
        mBookLocalDataSource = bookLocalDataSource;
        mBookRemoteDataSource = bookRemoteDataSource;
    }

    @Override
    public void saveBook(Album album, BookSaveCallback callback) {
        mBookRemoteDataSource.saveBook(album, callback);
    }

    @Override
    public void deleteBook(Album album, BookDeleteCallback callback) {
        mBookRemoteDataSource.deleteBook(album, callback);
    }

    @Override
    public void updateBook(Album album, @Nullable BmobFile newCover, BookUpdateCallback callback) {
        mBookRemoteDataSource.updateBook(album, newCover, callback);
    }

    @Override
    public void getBookListByAuthor(User author, int pageCode, boolean self, BookListGetCallback callback) {
        mBookRemoteDataSource.getBookListByAuthor(author, pageCode, self, callback);
    }

    @Override
    public void getBookListWithAuthor(int pageCode, BookListGetCallback callback) {
        mBookRemoteDataSource.getBookListWithAuthor(pageCode, callback);
    }

    @Override
    public void getBookListByStyle(String style, int pageCode, BookListGetCallback callback) {
        mBookRemoteDataSource.getBookListByStyle(style, pageCode, callback);
    }

    @Override
    public void getFavoriteBookList(User favor, int pageCode, BookListGetCallback callback) {
        mBookRemoteDataSource.getFavoriteBookList(favor, pageCode, callback);
    }

    @Override
    public void getAuthorBookTotal(User author, boolean self, TotalGetCallback callback) {
        mBookRemoteDataSource.getAuthorBookTotal(author, self, callback);
    }

    @Override
    public void getAlbumBookTotal(Style style, TotalGetCallback callback) {
        mBookRemoteDataSource.getAlbumBookTotal(style, callback);
    }

    @Override
    public void getFavoriteBookTotal(User favor, TotalGetCallback callback) {
        mBookRemoteDataSource.getFavoriteBookTotal(favor, callback);
    }

    @Override
    public void searchBook(String keyword, BookListGetCallback callback) {
        mBookRemoteDataSource.searchBook(keyword, callback);
    }
}
