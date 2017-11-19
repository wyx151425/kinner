package com.rumofuture.kinner.app.contract;

import com.rumofuture.kinner.app.KinnerPresenter;
import com.rumofuture.kinner.app.KinnerView;
import com.rumofuture.kinner.model.entity.User;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/15.
 */

public interface KinnerMainDiscoverContract {

    interface View extends KinnerView<Presenter> {
        void showProgressBar(boolean show);

        void showAuthorListGetSuccess(List<User> authorList);
        void showAuthorListGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends KinnerPresenter {
        void getAuthorList(int pageCode);
    }
}
