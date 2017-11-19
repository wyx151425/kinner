package com.rumofuture.kinner.model.source.remote;

import android.support.annotation.Nullable;

import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Style;
import com.rumofuture.kinner.model.entity.Favorite;
import com.rumofuture.kinner.model.entity.User;
import com.rumofuture.kinner.model.schema.AlbumSchema;
import com.rumofuture.kinner.model.schema.FavoriteSchema;
import com.rumofuture.kinner.model.source.AlbumDataSource;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by WangZhenqi on 2017/4/13.
 */

public class AlbumRemoteDataSource implements AlbumDataSource {

    private static final AlbumRemoteDataSource sInstance = new AlbumRemoteDataSource();

    public static AlbumRemoteDataSource getInstance() {
        return sInstance;
    }

    private AlbumRemoteDataSource() {

    }

    @Override
    public void saveBook(final Album album, final BookSaveCallback callback) {
        final BmobFile cover = album.getCover();
        // 用于上传封面图片的方法
        cover.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    // 图片上传成功后，封面对象自动获取Bmob云中地址
                    // 将图片赋值给漫画册对象并进行上传即可
                    album.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (null == e) {
                                album.setObjectId(objectId);
                                callback.onBookSaveSuccess(album);
                            } else {
                                callback.onBookSaveFailed(e);
                                cover.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                            }
                        }
                    });
                } else {
                    callback.onBookSaveFailed(e);
                }
            }
        });
    }

    @Override
    public void deleteBook(final Album album, final BookDeleteCallback callback) {
        final BmobFile cover = album.getCover();
        album.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    callback.onBookDeleteSuccess(album);
                    cover.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });
                } else {
                    callback.onBookDeleteFailed(e);
                }
            }
        });
    }

    @Override
    public void updateBook(final Album album, @Nullable final BmobFile newCover, final BookUpdateCallback callback) {
        if (null != newCover) {
            newCover.upload(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (null == e) {
                        BmobFile oldCover = album.getCover();
                        oldCover.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {

                            }
                        });
                        album.setCover(newCover);
                    } else {
                        callback.onBookUpdateFailed(e);
                    }
                }
            });
        }

        album.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    callback.onBookUpdateSuccess(album);
                } else {
                    callback.onBookUpdateFailed(e);
                }
            }
        });
    }

    @Override
    public void getBookListWithAuthor(int pageCode, final BookListGetCallback callback) {
        BmobQuery<Album> query = new BmobQuery<>();
        query.addWhereEqualTo(AlbumSchema.Table.Cols.APPROVE, true);
        query.addWhereEqualTo(AlbumSchema.Table.Cols.SHOW, true);
        query.include(AlbumSchema.Table.Cols.AUTHOR);
        query.setLimit(PAGE_LIMIT);
        query.setSkip(pageCode * PAGE_LIMIT);
        query.findObjects(new FindListener<Album>() {
            @Override
            public void done(List<Album> albumList, BmobException e) {
                if (null == e) {
                    callback.onBookListGetSuccess(albumList);
                } else {
                    callback.onBookListGetFailed(e);
                }
            }
        });
    }

    @Override
    public void getBookListByAuthor(User author, int pageCode, boolean self, final BookListGetCallback callback) {
        BmobQuery<Album> query = new BmobQuery<>();
        query.addWhereEqualTo(AlbumSchema.Table.Cols.AUTHOR, author);
        if (!self) {
            query.addWhereEqualTo(AlbumSchema.Table.Cols.APPROVE, true);
            query.addWhereEqualTo(AlbumSchema.Table.Cols.SHOW, true);
        }
        query.setLimit(PAGE_LIMIT);
        query.setSkip(pageCode * PAGE_LIMIT);
        query.findObjects(new FindListener<Album>() {
            @Override
            public void done(List<Album> albumList, BmobException e) {
                if (null == e) {
                    callback.onBookListGetSuccess(albumList);
                } else {
                    callback.onBookListGetFailed(e);
                }
            }
        });
    }

    @Override
    public void getBookListByStyle(String style, int pageCode, final BookListGetCallback callback) {
        BmobQuery<Album> query = new BmobQuery<>();
        query.addWhereEqualTo(AlbumSchema.Table.Cols.STYLE, style);
        query.addWhereEqualTo(AlbumSchema.Table.Cols.APPROVE, true);
        query.addWhereEqualTo(AlbumSchema.Table.Cols.SHOW, true);
        query.include(AlbumSchema.Table.Cols.AUTHOR);
        query.setLimit(PAGE_LIMIT);
        query.setSkip(pageCode * PAGE_LIMIT);
        query.findObjects(new FindListener<Album>() {
            @Override
            public void done(List<Album> albumList, BmobException e) {
                if (null == e) {
                    callback.onBookListGetSuccess(albumList);
                } else {
                    callback.onBookListGetFailed(e);
                }
            }
        });
    }

    @Override
    public void getFavoriteBookList(User favor, int pageCode, final BookListGetCallback callback) {
        BmobQuery<Favorite> query = new BmobQuery<>();
        query.addWhereEqualTo(FavoriteSchema.Table.Cols.FAVOR, favor);
        query.include(FavoriteSchema.Table.Cols.ALBUM + "." + AlbumSchema.Table.Cols.AUTHOR);
        query.setLimit(PAGE_LIMIT);
        query.setSkip(pageCode * PAGE_LIMIT);
        query.order(FavoriteSchema.Table.Cols.CREATE_TIME);
        query.findObjects(new FindListener<Favorite>() {
            @Override
            public void done(List<Favorite> favoriteList, BmobException e) {
                if (null == e) {
                    List<Album> albumList = new ArrayList<>();
                    for (Favorite favorite : favoriteList) {
                        albumList.add(favorite.getAlbum());
                    }
                    callback.onBookListGetSuccess(albumList);
                } else {
                    callback.onBookListGetFailed(e);
                }
            }
        });
    }

    @Override
    public void getAuthorBookTotal(User author, boolean self, final TotalGetCallback callback) {
        BmobQuery<Album> query = new BmobQuery<>();
        query.addWhereEqualTo(AlbumSchema.Table.Cols.AUTHOR, author);
        if (!self) {
            query.addWhereEqualTo(AlbumSchema.Table.Cols.APPROVE, true);
            query.addWhereEqualTo(AlbumSchema.Table.Cols.SHOW, true);
        }
        query.count(Album.class, new CountListener() {
            @Override
            public void done(Integer total, BmobException e) {
                if (null == e) {
                    callback.onTotalGetSuccess(total);
                } else {
                    callback.onTotalGetFailed(e);
                }
            }
        });
    }

    @Override
    public void getAlbumBookTotal(Style style, final TotalGetCallback callback) {
        BmobQuery<Album> query = new BmobQuery<>();
        query.addWhereEqualTo(AlbumSchema.Table.Cols.STYLE, style.getStyle());
        query.addWhereEqualTo(AlbumSchema.Table.Cols.APPROVE, true);
        query.addWhereEqualTo(AlbumSchema.Table.Cols.SHOW, true);
        query.count(Album.class, new CountListener() {
            @Override
            public void done(Integer total, BmobException e) {
                if (null == e) {
                    callback.onTotalGetSuccess(total);
                } else {
                    callback.onTotalGetFailed(e);
                }
            }
        });
    }

    @Override
    public void getFavoriteBookTotal(User favor, final TotalGetCallback callback) {
        BmobQuery<Favorite> query = new BmobQuery<>();
        query.addWhereEqualTo(FavoriteSchema.Table.Cols.FAVOR, favor);
        query.count(Favorite.class, new CountListener() {
            @Override
            public void done(Integer total, BmobException e) {
                if (null == e) {
                    callback.onTotalGetSuccess(total);
                } else {
                    callback.onTotalGetFailed(e);
                }
            }
        });
    }

    @Override
    public void searchBook(String keyword, final BookListGetCallback callback) {
        BmobQuery<Album> query = new BmobQuery<>();
        query.addWhereEqualTo(AlbumSchema.Table.Cols.NAME, keyword);
        query.addWhereEqualTo(AlbumSchema.Table.Cols.APPROVE, true);
        query.addWhereEqualTo(AlbumSchema.Table.Cols.SHOW, true);
        query.include(AlbumSchema.Table.Cols.AUTHOR);
        query.findObjects(new FindListener<Album>() {
            @Override
            public void done(List<Album> albumList, BmobException e) {
                if (null == e) {
                    callback.onBookListGetSuccess(albumList);
                } else {
                    callback.onBookListGetFailed(e);
                }
            }
        });
    }
}
