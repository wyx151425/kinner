package com.rumofuture.kinner.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.kinner.app.contract.KinnerAuthorBlogContract;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Follow;
import com.rumofuture.kinner.model.entity.User;
import com.rumofuture.kinner.model.schema.UserSchema;
import com.rumofuture.kinner.model.source.AlbumDataSource;
import com.rumofuture.kinner.model.source.FollowDataSource;
import com.rumofuture.kinner.model.source.UserDataSource;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/18.
 */

public class KinnerAuthorBlogPresenter implements KinnerAuthorBlogContract.Presenter, UserDataSource.UserInfoUpdateCallback, AlbumDataSource.BookListGetCallback,
        FollowDataSource.FollowSaveCallback, FollowDataSource.FollowDeleteCallback, FollowDataSource.FollowGetCallback {

    private KinnerAuthorBlogContract.View mView;
    private UserDataSource mUserRepository;
    private AlbumDataSource mBookRepository;
    private FollowDataSource mFollowRepository;

    public KinnerAuthorBlogPresenter(
            @NonNull KinnerAuthorBlogContract.View view,
            @NonNull UserDataSource userRepository,
            @NonNull AlbumDataSource bookRepository,
            @NonNull FollowDataSource followRepository
    ) {
        mView = view;
        mUserRepository = userRepository;
        mBookRepository = bookRepository;
        mFollowRepository = followRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getAuthorBookList(User author, int pageCode) {
        mView.showProgressBar(true);
        mBookRepository.getBookListByAuthor(author, pageCode, false, this);
    }

    @Override
    public void followUser(Follow follow) {
        mFollowRepository.saveFollow(follow, this);
    }

    @Override
    public void unfollowUser(Follow follow) {
        mFollowRepository.deleteFollow(follow, this);
    }

    @Override
    public void getFollowRelation(Follow follow) {
        mFollowRepository.getFollow(follow, this);
    }

    @Override
    public void onBookListGetSuccess(List<Album> albumList) {
        if (mView.isActive()) {
            mView.showAuthorBookListGetSuccess(albumList);
            mView.showProgressBar(false);
        }
    }

    @Override
    public void onBookListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showAuthorBookListGetFailed(e);
            mView.showProgressBar(false);
        }
    }

    @Override
    public void onUserInfoUpdateSuccess() {

    }

    @Override
    public void onUserInfoUpdateFailed(BmobException e) {

    }

    @Override
    public void onFollowSaveSuccess(Follow follow) {
        if (mView.isActive()) {
            mView.showUserFollowSuccess(follow);
        }

        User follower = follow.getFollower();
        follower.increment(UserSchema.Table.Cols.FOLLOW_TOTAL);
        mUserRepository.updateUserInfo(follower, this);

//        BmobPushManager pushManager = new BmobPushManager();
//        BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
//        query.addWhereEqualTo("installationId", "C8CDE03327B6A0485A153C838ADD17A5");
//        pushManager.setQuery(query);
//        pushManager.pushMessage("", new PushListener() {
//            @Override
//            public void done(BmobException e) {
//
//            }
//        });
    }

    @Override
    public void onFollowSaveFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showUserFollowFailed(e);
        }
    }

    @Override
    public void onFollowDeleteSuccess(Follow follow) {
        if (mView.isActive()) {
            mView.showUserUnfollowSuccess(follow);
        }

        User follower = BmobUser.getCurrentUser(User.class);
        follower.increment(UserSchema.Table.Cols.FOLLOW_TOTAL, -1);
        mUserRepository.updateUserInfo(follower, this);
    }

    @Override
    public void onFollowDeleteFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showUserUnfollowFailed(e);
        }
    }

    @Override
    public void onFollowGetSuccess(Follow follow) {
        if (mView.isActive()) {
            mView.showFollowGetSuccess(follow);
        }
    }

    @Override
    public void onFollowGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showFollowGetFailed(e);
        }
    }
}
