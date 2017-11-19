package com.rumofuture.kinner.model.source;

import android.support.annotation.NonNull;

import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Review;

/**
 * Created by WangZhenqi on 2017/9/12.
 */

public class ReviewRepository implements ReviewDataSource {

    private static ReviewRepository sInstance;

    private final ReviewDataSource mReviewLocalDataSource;
    private final ReviewDataSource mReviewRemoteDataSource;

    public static ReviewRepository getInstance(
            @NonNull ReviewDataSource reviewLocalDataSource,
            @NonNull ReviewDataSource reviewRemoteDataSource
    ) {
        if (null == sInstance) {
            sInstance = new ReviewRepository(reviewLocalDataSource, reviewRemoteDataSource);
        }
        return sInstance;
    }

    private ReviewRepository(
            @NonNull ReviewDataSource reviewLocalDataSource,
            @NonNull ReviewDataSource reviewRemoteDataSource
    ) {
        mReviewLocalDataSource = reviewLocalDataSource;
        mReviewRemoteDataSource = reviewRemoteDataSource;
    }

    @Override
    public void saveReview(Review review, ReviewSaveCallback callback) {
        mReviewRemoteDataSource.saveReview(review, callback);
    }

    @Override
    public void deleteReview(Review review, ReviewDeleteCallback callback) {
        mReviewRemoteDataSource.deleteReview(review, callback);
    }

    @Override
    public void getReviewListByBook(Album album, int pageCode, ReviewListGetCallback callback) {
        mReviewRemoteDataSource.getReviewListByBook(album, pageCode, callback);
    }
}
