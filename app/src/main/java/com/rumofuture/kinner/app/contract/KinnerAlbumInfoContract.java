package com.rumofuture.kinner.app.contract;

import com.rumofuture.kinner.app.KinnerView;
import com.rumofuture.kinner.app.KinnerPresenter;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Favorite;
import com.rumofuture.kinner.model.entity.Review;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/23.
 */

public interface KinnerAlbumInfoContract {

    interface View extends KinnerView<Presenter> {
        void showBookFavoriteSuccess(Favorite favorite);
        void showBookFavoriteFailed(BmobException e);

        void showFavoriteRemoveSuccess();
        void showFavoriteRemoveFailed(BmobException e);

        void showFavoriteGetSuccess(Favorite favorite);
        void showFavoriteGetFailed(BmobException e);

        void showBookFavorTotalUpdateSuccess(Album album);

        void showReviewListGetSuccess(List<Review> reviewList);
        void showReviewListGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends KinnerPresenter {
        void favoriteBook(Favorite favorite);
        void removeBookFromMyFavorite(Favorite favorite);
        void getFavoriteRelation(Favorite favorite);
        void getBookReviewList(Album album, int pageCode);
    }
}
