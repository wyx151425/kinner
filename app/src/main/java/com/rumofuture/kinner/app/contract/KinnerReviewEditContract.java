package com.rumofuture.kinner.app.contract;

import com.rumofuture.kinner.app.KinnerPresenter;
import com.rumofuture.kinner.app.KinnerView;
import com.rumofuture.kinner.model.entity.Review;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/23.
 */

public interface KinnerReviewEditContract {

    interface View extends KinnerView<Presenter> {
        void showProgressBar(boolean show);

        void showReviewSaveSuccess(Review review);
        void showReviewSaveFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends KinnerPresenter {
        void saveReview(Review review);
    }
}
