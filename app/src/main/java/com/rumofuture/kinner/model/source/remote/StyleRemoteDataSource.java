package com.rumofuture.kinner.model.source.remote;

import com.rumofuture.kinner.model.entity.Style;
import com.rumofuture.kinner.model.schema.StyleSchema;
import com.rumofuture.kinner.model.source.StyleDataSource;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by WangZhenqi on 2017/9/20.
 */

public class StyleRemoteDataSource implements StyleDataSource {

    private static final StyleRemoteDataSource sInstance = new StyleRemoteDataSource();

    public static StyleRemoteDataSource getInstance() {
        return sInstance;
    }

    private StyleRemoteDataSource() {

    }

    @Override
    public void updateAlbum(final Style style, final AlbumUpdateCallback callback) {
        style.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    callback.onAlbumUpdateSuccess(style);
                } else {
                    callback.onAlbumUpdateFailed(e);
                }
            }
        });
    }

    @Override
    public void getAlbumByStyle(String style, final AlbumGetCallback callback) {
        BmobQuery<Style> query = new BmobQuery<>();
        query.addWhereEqualTo(StyleSchema.Table.Cols.STYLE, style);
        query.findObjects(new FindListener<Style>() {
            @Override
            public void done(List<Style> styleList, BmobException e) {
                if (null == e) {
                    callback.onAlbumGetSuccess(styleList.get(0));
                } else {
                    callback.onAlbumGetFailed(e);
                }
            }
        });
    }

    @Override
    public void getAlbumList(final AlbumListGetCallback callback) {
        BmobQuery<Style> query = new BmobQuery<>();
        query.setLimit(PAGE_LIMIT);
        query.order(StyleSchema.Table.Cols.NUMBER);
        query.findObjects(new FindListener<Style>() {
            @Override
            public void done(List<Style> styleList, BmobException e) {
                if (null == e) {
                    callback.onAlbumListGetSuccess(styleList);
                } else {
                    callback.onAlbumListGetFailed(e);
                }
            }
        });
    }
}
