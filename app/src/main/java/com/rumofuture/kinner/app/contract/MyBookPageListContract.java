package com.rumofuture.kinner.app.contract;

import com.rumofuture.kinner.app.KinnerView;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Music;
import com.rumofuture.kinner.presenter.KinnerImageUploadPresenter;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/15.
 */

public interface MyBookPageListContract {

    interface View extends KinnerView<Presenter> {
        void showProgressBar(boolean show, int stringId);

        void showPageSaveSuccess(Music music);
        void showPageSaveFailed(BmobException e);

        void showPageDeleteSuccess(Music music);
        void showPageDeleteFailed(BmobException e);

        void showPageUpdateSuccess(Music music);
        void showPageUpdateFailed(BmobException e);

        void showPageListGetSuccess(List<Music> musicList);
        void showPageListGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends KinnerImageUploadPresenter {
        void uploadPage(Music music);
        void deletePage(Music music);
        void updatePage(Music music);
        void getBookPageList(Album album, int pageCode);
    }
}
