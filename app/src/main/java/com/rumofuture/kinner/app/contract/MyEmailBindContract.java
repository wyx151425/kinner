package com.rumofuture.kinner.app.contract;

import com.rumofuture.kinner.app.KinnerPresenter;
import com.rumofuture.kinner.app.KinnerView;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/11.
 */

public interface MyEmailBindContract {

    interface View extends KinnerView<Presenter> {
        void showProgressBar(boolean show);

        void showEmailError(Integer stringId);

        void showEmailBindSuccess(String prompt);
        void showEmailBindFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends KinnerPresenter {
        void bindEmail(String email);
    }
}
