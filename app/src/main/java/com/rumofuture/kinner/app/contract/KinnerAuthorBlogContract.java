package com.rumofuture.kinner.app.contract;

import com.rumofuture.kinner.app.KinnerPresenter;
import com.rumofuture.kinner.app.KinnerView;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Follow;
import com.rumofuture.kinner.model.entity.User;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/18.
 */

public interface KinnerAuthorBlogContract {

    interface View extends KinnerView<Presenter> {
        void showProgressBar(boolean show);

        void showAuthorBookListGetSuccess(List<Album> albumList);
        void showAuthorBookListGetFailed(BmobException e);

        void showUserFollowSuccess(Follow follow);
        void showUserFollowFailed(BmobException e);

        void showUserUnfollowSuccess(Follow follow);
        void showUserUnfollowFailed(BmobException e);

        void showFollowGetSuccess(Follow follow);
        void showFollowGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends KinnerPresenter {
        void getAuthorBookList(User author, int pageCode);

        void followUser(Follow follow);
        void unfollowUser(Follow follow);
        void getFollowRelation(Follow follow);
    }
}
