package com.rumofuture.kinner.app.contract;

import com.rumofuture.kinner.app.KinnerView;
import com.rumofuture.kinner.model.entity.User;
import com.rumofuture.kinner.presenter.KinnerImageUploadPresenter;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/16.
 */

public interface MyInfoUpdateContract {

    interface View extends KinnerView<Presenter> {
        void showProgressBar(boolean show);

        void showUserAvatarUpdateSuccess(BmobFile avatar);
        void showUserAvatarUpdateFailed(BmobException e);

        void showUserPortraitUpdateSuccess(BmobFile portrait);
        void showUserPortraitUpdateFailed(BmobException e);

        void showUserInfoUpdateSuccess();
        void showUserInfoUpdateFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends KinnerImageUploadPresenter {
        void updateUserAvatar();
        void updateUserPortrait();

        void updateUserInfo(User user);
    }
}
