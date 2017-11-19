package com.rumofuture.kinner.app.contract;

import com.rumofuture.kinner.app.KinnerPresenter;
import com.rumofuture.kinner.app.KinnerView;
import com.rumofuture.kinner.model.entity.User;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/16.
 */

public interface KinnerLogInContract {

    interface View extends KinnerView<Presenter> {
        void showProgressBar(boolean show);

        void showMobilePhoneNumberError(Integer stringId);
        void showPasswordError(Integer stringId);

        void showLogInSuccess(User user);
        void showLogInFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends KinnerPresenter {
        void logIn(String mobilePhoneNumber, String password);
    }
}
