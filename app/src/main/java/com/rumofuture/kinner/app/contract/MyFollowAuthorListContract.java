package com.rumofuture.kinner.app.contract;

import com.rumofuture.kinner.app.KinnerPresenter;
import com.rumofuture.kinner.app.KinnerView;
import com.rumofuture.kinner.model.entity.User;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/24.
 */

public interface MyFollowAuthorListContract {

    interface View extends KinnerView<Presenter> {
        void showFollowUserListGetSuccess(List<User> authorList);
        void showFollowUserListGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends KinnerPresenter {
        void getMyFollowAuthorList(int pageCode);
    }
}
