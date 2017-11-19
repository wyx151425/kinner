package com.rumofuture.kinner.app.contract;

import com.rumofuture.kinner.app.KinnerView;
import com.rumofuture.kinner.app.KinnerPresenter;
import com.rumofuture.kinner.model.entity.User;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/5/6.
 */

public interface KinnerSignUpContract {

    interface View extends KinnerView<Presenter> {
        void showProgressBar(boolean show);

        void showNameError(int stringId);
        void showMobilePhoneNumberError(int stringId);
        void showPasswordError(int stringId);

        void showRequestSMSCodeSuccess(Integer smsId);
        void showRequestSMSCodeFailed(BmobException e);

        void showSMSCodeRequestTime(String time);
        void showSMSCodeRequestTimeOut(String message);

        void showSignUpSuccess(User user);
        void showSignUpFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends KinnerPresenter {
        void requestSMSCode(String mobilePhoneNumber);
        void signUp(String name, String mobilePhoneNumber, String password, String smsCode);
    }
}
