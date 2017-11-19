package com.rumofuture.kinner.app.contract;

import com.rumofuture.kinner.app.KinnerView;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.presenter.KinnerImageUploadPresenter;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/13.
 */

public interface MyBookCreateContract {

    interface View extends KinnerView<Presenter> {
        void showBookInfoError(int stringId);
        void showBookCoverHasChosen(String imagePath);

        void showProgressBar(boolean show);

        void showBookCreateSuccess(Album album);
        void showBookCreateFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends KinnerImageUploadPresenter {
        void createBook(Album album);
    }
}
