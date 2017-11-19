package com.rumofuture.kinner.app.contract;

import com.rumofuture.kinner.app.KinnerView;
import com.rumofuture.kinner.app.KinnerPresenter;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/5/6.
 */

public interface MyPasswordUpdateContract {

    interface View extends KinnerView<Presenter> {
        void showUserPasswordUpdateSuccess();
        void showUserPasswordUpdateFailed(BmobException e);

        void showPasswordError(int stringId);

        boolean isActive();
    }

    interface Presenter extends KinnerPresenter {
        void updatePassword(String oldPassword, String newPassword);
    }
}
