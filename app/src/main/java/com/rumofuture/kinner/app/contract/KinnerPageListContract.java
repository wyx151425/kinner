package com.rumofuture.kinner.app.contract;

import com.rumofuture.kinner.app.KinnerPresenter;
import com.rumofuture.kinner.app.KinnerView;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Music;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/23.
 */

public interface KinnerPageListContract {

    interface View extends KinnerView<Presenter> {
        void showPageListGetSuccess(List<Music> musicList);
        void showPageListGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends KinnerPresenter {
        void getBookPageList(Album album, int pageCode);
    }
}
