package com.rumofuture.kinner.app.contract;

import com.rumofuture.kinner.app.KinnerView;
import com.rumofuture.kinner.app.KinnerPresenter;
import com.rumofuture.kinner.model.entity.User;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/18.
 */

public interface KinnerAuthorSearchContract {

    interface View extends KinnerView<Presenter> {
        void showProgressBar(boolean show);

        void showAuthorSearchSuccess(List<User> authorList);
        void showAuthorSearchFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends KinnerPresenter {
        void searchAuthor(String keyword);
    }
}
