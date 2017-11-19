package com.rumofuture.kinner.presenter;

import com.rumofuture.kinner.app.contract.KinnerReviewEditContract;
import com.rumofuture.kinner.model.entity.Review;
import com.rumofuture.kinner.model.source.ReviewDataSource;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/23.
 */

public class KinnerReviewEditPresenter implements KinnerReviewEditContract.Presenter, ReviewDataSource.ReviewSaveCallback {

    private KinnerReviewEditContract.View mView;
    private ReviewDataSource mReviewRepository;

    public KinnerReviewEditPresenter(
            KinnerReviewEditContract.View view,
            ReviewDataSource reviewRepository
    ) {
        mView = view;
        mReviewRepository = reviewRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void saveReview(Review review) {
        mReviewRepository.saveReview(review, this);
    }

    @Override
    public void onReviewSaveSuccess(Review review) {
        if (mView.isActive()) {
            mView.showReviewSaveSuccess(review);
        }
    }

    @Override
    public void onReviewSaveFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showReviewSaveFailed(e);
        }
    }
}
