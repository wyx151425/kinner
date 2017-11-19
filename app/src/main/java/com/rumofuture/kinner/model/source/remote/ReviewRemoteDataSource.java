package com.rumofuture.kinner.model.source.remote;

import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Review;
import com.rumofuture.kinner.model.schema.ReviewSchema;
import com.rumofuture.kinner.model.source.ReviewDataSource;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by WangZhenqi on 2017/9/12.
 */

public class ReviewRemoteDataSource implements ReviewDataSource {

    private static final int PAGE_LIMIT = 32;

    public static final ReviewRemoteDataSource sInstance = new ReviewRemoteDataSource();

    public static ReviewRemoteDataSource getInstance() {
        return sInstance;
    }

    private ReviewRemoteDataSource() {

    }

    @Override
    public void saveReview(final Review review, final ReviewSaveCallback callback) {
        review.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (null == e) {
                    review.setObjectId(objectId);
                    callback.onReviewSaveSuccess(review);
                } else {
                    callback.onReviewSaveFailed(e);
                }
            }
        });
    }

    @Override
    public void deleteReview(final Review review, final ReviewDeleteCallback callback) {
        review.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    callback.onReviewDeleteSuccess(review);
                } else {
                    callback.onReviewDeleteFailed(e);
                }
            }
        });
    }

    @Override
    public void getReviewListByBook(Album album, int pageCode, final ReviewListGetCallback callback) {
        BmobQuery<Review> query = new BmobQuery<>();
        query.addWhereEqualTo(ReviewSchema.Table.Cols.ALBUM, album);
        query.include(ReviewSchema.Table.Cols.REVIEWER);
        query.setLimit(PAGE_LIMIT);
        query.setSkip(pageCode * PAGE_LIMIT);
        query.findObjects(new FindListener<Review>() {
            @Override
            public void done(List<Review> reviewList, BmobException e) {
                if (null == e) {
                    callback.onReviewListGetSuccess(reviewList);
                } else {
                    callback.onReviewListGetFailed(e);
                }
            }
        });
    }
}
