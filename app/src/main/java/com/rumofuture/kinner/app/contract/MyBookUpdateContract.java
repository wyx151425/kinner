package com.rumofuture.kinner.app.contract;

import com.rumofuture.kinner.app.KinnerPresenter;
import com.rumofuture.kinner.app.KinnerView;
import com.rumofuture.kinner.model.entity.Album;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/11/3.
 */

public interface MyBookUpdateContract {

    interface View extends KinnerView<Presenter> {
        void showProgressBar(boolean show);

        void showBookUpdateSuccess(Album album);
        void showBookUpdateFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends KinnerPresenter {
        void updateBook(Album album);
    }
}
