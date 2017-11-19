package com.rumofuture.kinner.app.contract;

import com.rumofuture.kinner.app.KinnerView;
import com.rumofuture.kinner.app.KinnerPresenter;
import com.rumofuture.kinner.model.entity.Album;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/15.
 */

public interface MyBookListContract {

    interface View extends KinnerView<Presenter> {
        void showProgressBar(boolean show);

        void showBookListGetSuccess(List<Album> albumList);
        void showBookListGetFailed(BmobException e);

        void showBookDeleteSuccess(Album album);
        void showBookDeleteFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends KinnerPresenter {
        void deleteBook(Album album);
        void updateMyBookTotalOnCreate();
        void updateMyBookTotalOnDelete();
        void getMyBookList(int pageCode);
    }
}
