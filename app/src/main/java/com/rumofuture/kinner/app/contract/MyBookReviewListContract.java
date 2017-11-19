package com.rumofuture.kinner.app.contract;

import com.rumofuture.kinner.app.KinnerView;
import com.rumofuture.kinner.app.KinnerPresenter;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Review;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/19.
 */

public interface MyBookReviewListContract {

    interface View extends KinnerView<Presenter> {
        void showProgressBar(boolean show);

        void showReviewDeleteSuccess(Review review);
        void showReviewDeleteFailed(BmobException e);

        void showReviewListGetSuccess(List<Review> reviewList);
        void showReviewListGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends KinnerPresenter {
        void deleteReview(Review review);
        void getBookReviewList(Album album, int pageCode);
    }
}
