package com.rumofuture.kinner.app.contract;

import com.rumofuture.kinner.app.KinnerView;
import com.rumofuture.kinner.app.KinnerPresenter;
import com.rumofuture.kinner.model.entity.Style;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/20.
 */

public interface KinnerMainStyleContract {

    interface View extends KinnerView<Presenter> {
        void showAlbumListGetSuccess(List<Style> styleList);
        void showAlbumListGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends KinnerPresenter {
        void getAlbumList();
    }
}
