package com.rumofuture.kinner.app.contract;

import com.rumofuture.kinner.app.KinnerView;
import com.rumofuture.kinner.app.KinnerPresenter;
import com.rumofuture.kinner.model.entity.Album;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/27.
 */

public interface KinnerAlbumBookListContract {

    interface View extends KinnerView<Presenter> {
        void showAlbumBooksGetSuccess(List<Album> albums);
        void showAlbumBooksGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends KinnerPresenter {
        void getAlbumBookList(String style, int pageCode);
    }
}
