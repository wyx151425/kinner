package com.rumofuture.kinner.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.kinner.app.contract.KinnerAlbumInfoContract;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Favorite;
import com.rumofuture.kinner.model.entity.Review;
import com.rumofuture.kinner.model.entity.User;
import com.rumofuture.kinner.model.schema.AlbumSchema;
import com.rumofuture.kinner.model.schema.UserSchema;
import com.rumofuture.kinner.model.source.AlbumDataSource;
import com.rumofuture.kinner.model.source.FavoriteDataSource;
import com.rumofuture.kinner.model.source.ReviewDataSource;
import com.rumofuture.kinner.model.source.UserDataSource;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/23.
 */

public class KinnerAlbumInfoPresenter implements KinnerAlbumInfoContract.Presenter, UserDataSource.UserInfoUpdateCallback, AlbumDataSource.BookUpdateCallback,
        FavoriteDataSource.FavoriteSaveCallback, FavoriteDataSource.FavoriteDeleteCallback, FavoriteDataSource.FavoriteGetCallback, ReviewDataSource.ReviewListGetCallback {

    private KinnerAlbumInfoContract.View mView;
    private UserDataSource mUserRepository;
    private AlbumDataSource mBookRepository;
    private ReviewDataSource mReviewRepository;
    private FavoriteDataSource mFavoriteRepository;

    public KinnerAlbumInfoPresenter(
            @NonNull KinnerAlbumInfoContract.View view,
            @NonNull UserDataSource userRepository,
            @NonNull AlbumDataSource bookRepository,
            @NonNull ReviewDataSource reviewRepository,
            @NonNull FavoriteDataSource favoriteRepository
    ) {
        mView = view;
        mUserRepository = userRepository;
        mBookRepository = bookRepository;
        mReviewRepository = reviewRepository;
        mFavoriteRepository = favoriteRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void favoriteBook(Favorite favorite) {
        mFavoriteRepository.saveFavorite(favorite, this);
    }

    @Override
    public void removeBookFromMyFavorite(Favorite favorite) {
        mFavoriteRepository.deleteFavorite(favorite, this);
    }

    @Override
    public void getFavoriteRelation(Favorite favorite) {
        mFavoriteRepository.getFavorite(favorite, this);
    }

    @Override
    public void getBookReviewList(Album album, int pageCode) {
        mReviewRepository.getReviewListByBook(album, pageCode, this);
    }

    @Override
    public void onFavoriteSaveSuccess(Favorite favorite) {
        if (mView.isActive()) {
            mView.showBookFavoriteSuccess(favorite);
        }

        User collector = favorite.getFavor();
        collector.increment(UserSchema.Table.Cols.FAVORITE_TOTAL);
        mUserRepository.updateUserInfo(collector, this);

        Album album = favorite.getAlbum();
        album.increment(AlbumSchema.Table.Cols.FAVOR_TOTAL);
        mBookRepository.updateBook(album, null, this);
    }

    @Override
    public void onFavoriteSaveFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showBookFavoriteFailed(e);
        }
    }

    @Override
    public void onFavoriteDeleteSuccess(Favorite favorite) {
        if (mView.isActive()) {
            mView.showFavoriteRemoveSuccess();
        }

        User collector = favorite.getFavor();
        collector.increment(UserSchema.Table.Cols.FAVORITE_TOTAL, -1);
        mUserRepository.updateUserInfo(collector, this);

        Album album = favorite.getAlbum();
        album.increment(AlbumSchema.Table.Cols.FAVOR_TOTAL, -1);
        mBookRepository.updateBook(album, null, this);
    }

    @Override
    public void onFavoriteDeleteFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showFavoriteRemoveFailed(e);
        }
    }

    @Override
    public void onFavoriteGetSuccess(Favorite favorite) {
        if (mView.isActive()) {
            mView.showFavoriteGetSuccess(favorite);
        }
    }

    @Override
    public void onFavoriteGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showFavoriteGetFailed(e);
        }
    }

    @Override
    public void onUserInfoUpdateSuccess() {

    }

    @Override
    public void onUserInfoUpdateFailed(BmobException e) {

    }

    @Override
    public void onBookUpdateSuccess(Album album) {
        if (mView.isActive()) {
            mView.showBookFavorTotalUpdateSuccess(album);
        }
    }

    @Override
    public void onBookUpdateFailed(BmobException e) {

    }

    @Override
    public void onReviewListGetSuccess(List<Review> reviewList) {
        if (mView.isActive()) {
            mView.showReviewListGetSuccess(reviewList);
        }
    }

    @Override
    public void onReviewListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showReviewListGetFailed(e);
        }
    }
}
