package com.rumofuture.kinner.app.contract;

import com.rumofuture.kinner.app.KinnerPresenter;
import com.rumofuture.kinner.app.KinnerView;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/5/8.
 */

public interface KinnerPasswordUpdateContract {

    interface View extends KinnerView<Presenter> {
        void showProgressView(boolean show);

        void showUserPasswordModifySuccess();
        void showUserPasswordModifyFailed(BmobException e);

        void showMobilePhoneNumberError(int stringId);
        void showPasswordError(int stringId);

        void showRequestSMSCodeSuccess(Integer smsId);
        void showRequestSMSCodeFailed(BmobException e);

        void showSMSCodeRequestTime(String time);
        void showSMSCodeRequestTimeOut(String message);

        boolean isActive();
    }

    interface Presenter extends KinnerPresenter {
        void requestSMSCode(String mobilePhoneNumber);
        void modifyPassword(String newPassword, String smsCode);
    }
}
