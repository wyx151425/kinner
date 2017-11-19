package com.rumofuture.kinner.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.kinner.app.contract.MyFavoriteBookListContract;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.User;
import com.rumofuture.kinner.model.source.AlbumDataSource;
import com.rumofuture.kinner.model.source.UserDataSource;

import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/24.
 */

public class MyFavoriteBookListPresenter implements MyFavoriteBookListContract.Presenter, UserDataSource.UserInfoUpdateCallback,
        AlbumDataSource.BookListGetCallback, AlbumDataSource.TotalGetCallback {

    private MyFavoriteBookListContract.View mView;
    private UserDataSource mUserRepository;
    private AlbumDataSource mBookRepository;

    public MyFavoriteBookListPresenter(
            @NonNull MyFavoriteBookListContract.View view,
            @NonNull UserDataSource userRepository,
            @NonNull AlbumDataSource bookRepository
    ) {
        mView = view;
        mUserRepository = userRepository;
        mBookRepository = bookRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getMyFavoriteBookList(int pageCode) {
        mBookRepository.getFavoriteBookList(
                BmobUser.getCurrentUser(User.class), pageCode, this
        );
        mBookRepository.getFavoriteBookTotal(
                BmobUser.getCurrentUser(User.class), this
        );
    }

    @Override
    public void onBookListGetSuccess(List<Album> albumList) {
        if (mView.isActive()) {
            mView.showMyFavoriteBookListGetSuccess(albumList);
        }
    }

    @Override
    public void onBookListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showMyFavoriteBookListGetFailed(e);
        }
    }

    @Override
    public void onTotalGetSuccess(Integer total) {
        User currentUser = BmobUser.getCurrentUser(User.class);
        if (!Objects.equals(currentUser.getFavoriteTotal(), total)) {
            currentUser.setFavoriteTotal(total);
            mUserRepository.updateUserInfo(currentUser, this);
        }
    }

    @Override
    public void onTotalGetFailed(BmobException e) {

    }

    @Override
    public void onUserInfoUpdateSuccess() {

    }

    @Override
    public void onUserInfoUpdateFailed(BmobException e) {

    }
}
