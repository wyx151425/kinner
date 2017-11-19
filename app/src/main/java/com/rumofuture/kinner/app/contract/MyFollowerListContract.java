package com.rumofuture.kinner.app.contract;

import com.rumofuture.kinner.app.KinnerPresenter;
import com.rumofuture.kinner.app.KinnerView;
import com.rumofuture.kinner.model.entity.User;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/8.
 */

public interface MyFollowerListContract {

    interface View extends KinnerView<Presenter> {
        void showMyFollowerListGetSuccess(List<User> followerList);
        void showMyFollowerListGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends KinnerPresenter {
        void getMyFollowerList(int pageCode);
    }
}
