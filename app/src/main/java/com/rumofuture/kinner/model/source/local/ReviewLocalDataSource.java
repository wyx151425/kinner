package com.rumofuture.kinner.model.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Review;
import com.rumofuture.kinner.model.source.ReviewDataSource;

/**
 * Created by WangZhenqi on 2017/9/12.
 */

public class ReviewLocalDataSource implements ReviewDataSource {

    private static ReviewLocalDataSource sInstance;

    private final Context mContext;

    public static ReviewLocalDataSource getInstance(@NonNull Context context) {
        if (null == sInstance) {
            sInstance = new ReviewLocalDataSource(context);
        }
        return sInstance;
    }

    private ReviewLocalDataSource(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public void saveReview(Review review, ReviewSaveCallback callback) {

    }

    @Override
    public void deleteReview(Review review, ReviewDeleteCallback callback) {

    }

    @Override
    public void getReviewListByBook(Album album, int pageCode, ReviewListGetCallback callback) {

    }
}
