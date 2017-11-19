package com.rumofuture.kinner.model.source.remote;

import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Music;
import com.rumofuture.kinner.model.schema.MusicSchema;
import com.rumofuture.kinner.model.source.MusicDataSource;

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
 * Created by WangZhenqi on 2017/4/15.
 */

public class MusicRemoteDataSource implements MusicDataSource {

    private static final int PAGE_LIMIT = 64;

    private static final MusicRemoteDataSource sInstance = new MusicRemoteDataSource();

    public static MusicRemoteDataSource getInstance() {
        return sInstance;
    }

    private MusicRemoteDataSource() {

    }

    @Override
    public void savePage(final Music music, final PageSaveCallback callback) {
        final BmobFile image = music.getMusic();
        image.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    music.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (null == e) {
                                music.setObjectId(objectId);
                                callback.onPageSaveSuccess(music);
                            } else {
                                callback.onPageSaveFailed(e);
                                image.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                            }
                        }
                    });
                } else {
                    callback.onPageSaveFailed(e);
                }
            }
        });
    }

    @Override
    public void deletePage(final Music music, final PageDeleteCallback callback) {
        final BmobFile image = music.getMusic();
        music.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    callback.onPageDeleteSuccess(music);
                    image.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });
                } else {
                    callback.onPageDeleteFailed(e);
                }
            }
        });
    }

    @Override
    public void updatePage(final Music music, final BmobFile newImage, final PageUpdateCallback callback) {
        final BmobFile oldImage = music.getMusic();
        newImage.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    music.setMusic(newImage);
                    music.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                callback.onPageUpdateSuccess(music);
                                oldImage.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                            } else {
                                callback.onPageUpdateFailed(e);
                            }
                        }
                    });
                } else {
                    callback.onPageUpdateFailed(e);
                }
            }
        });
    }

    @Override
    public void getPageListByBook(Album album, int pageCode, final PageListGetCallback callBack) {
        BmobQuery<Music> query = new BmobQuery<>();
        query.addWhereEqualTo(MusicSchema.Table.Cols.ALBUM, album);
        query.setLimit(PAGE_LIMIT);
        query.setSkip(pageCode * PAGE_LIMIT);
        query.order(MusicSchema.Table.Cols.CREATE_TIME);
        query.findObjects(new FindListener<Music>() {
            @Override
            public void done(List<Music> musicList, BmobException e) {
                if (e == null) {
                    callBack.onPageListGetSuccess(musicList);
                } else {
                    callBack.onPageListGetFailed(e);
                }
            }
        });
    }

    @Override
    public void getPageTotal(Album album, final TotalGetCallback callback) {
        BmobQuery<Music> query = new BmobQuery<>();
        query.addWhereEqualTo(MusicSchema.Table.Cols.ALBUM, album);
        query.count(Music.class, new CountListener() {
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
}
