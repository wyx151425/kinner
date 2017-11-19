package com.rumofuture.kinner.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.contract.KinnerAlbumInfoContract;
import com.rumofuture.kinner.app.widget.OnListScrollListener;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Favorite;
import com.rumofuture.kinner.model.entity.Review;
import com.rumofuture.kinner.model.entity.User;
import com.rumofuture.kinner.model.source.ReviewDataSource;
import com.rumofuture.kinner.view.activity.KinnerBookReviewEditActivity;
import com.rumofuture.kinner.view.adapter.KinnerAuthorAlbumInfoAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/20.
 */

public class KinnerAuthorBookInfoFragment extends Fragment implements KinnerAlbumInfoContract.View {

    private static final String ARG_BOOK = "com.rumofuture.nemo.view.fragment.KinnerBookInfoFragment.book";

    private static final int REQUEST_REVIEW = 501;

    private KinnerAlbumInfoContract.Presenter mPresenter;

    private boolean isOnline = false;
    private boolean isFavorite = false;

    private Album mAlbum;
    private Favorite mFavorite;
    private List<Review> mReviewList;
    private KinnerAuthorAlbumInfoAdapter mBookInfoAdapter;

    private FloatingActionButton mFab;
    private OnListScrollListener mScrollListener;

    public KinnerAuthorBookInfoFragment() {

    }

    public static KinnerAuthorBookInfoFragment newInstance(Album album) {
        Bundle args = new Bundle();
        KinnerAuthorBookInfoFragment fragment = new KinnerAuthorBookInfoFragment();
        args.putSerializable(ARG_BOOK, album);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取Activity启动此Fragment时传递的Book对象
        if (null != getArguments()) {
            mAlbum = (Album) getArguments().getSerializable(ARG_BOOK);
        }

        if (null != BmobUser.getCurrentUser(User.class)) {
            // 如果此应用有用户登录，则将登录标识置为true
            // 并封装待用的Collect对象为漫画册为当前目标漫画册和收藏者为当前用户
            isOnline = true;
            mFavorite = new Favorite(mAlbum, BmobUser.getCurrentUser(User.class));
        } else {
            // 如果此应用没有用户登录，则将登录标识置为false
            isOnline = false;
        }

        mReviewList = new ArrayList<>();
        mBookInfoAdapter = new KinnerAuthorAlbumInfoAdapter(this, mAlbum, mReviewList);
        mScrollListener = new OnListScrollListener(ReviewDataSource.PAGE_LIMIT) {
            @Override
            public void onLoadMore(int pageCode) {
                mPresenter.getBookReviewList(mAlbum, pageCode);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_recycler_view, container, false);

        /* 注释：mFab
         * 初始化收藏按钮，此按钮被点击后，此按钮将被设置为不可点击状态；
         * 如果当前应用有用户登录，且此漫画册已被此用户收藏过，则执行此漫画册收藏对象的删除操作；
         * 如果当前应用有用户登录，且此漫画册未被此用户收藏过，则执行次漫画册收藏对象的保存操作；
         * 如果当前应用没有用户登录，则提示用户登录才能收藏此漫画册；
         */
        mFab = getActivity().findViewById(R.id.fab);
        mFab.setClickable(false);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFab.setClickable(false);
                if (isOnline) {
                    if (isFavorite) {
                        mPresenter.removeBookFromMyFavorite(mFavorite);
                    } else {
                        mPresenter.favoriteBook(mFavorite);
                    }
                } else {
                    Toast.makeText(getActivity(), "登录Nemo 即刻收藏", Toast.LENGTH_LONG).show();
                }
            }
        });  // mFab注释范围结束

        RecyclerView recyclerView =view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mBookInfoAdapter);

        mScrollListener.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(mScrollListener);

        if (isOnline && mAlbum.getAuthor().getObjectId().equals(BmobUser.getCurrentUser(User.class).getObjectId())) {
            // 如果当前应用有用户在线，并且此漫画的作者为当前登录用户
            // 则将mFab置为已收藏状态，并设置此mFab不可点击
            mFab.setImageResource(R.mipmap.ic_star_orange_fab);
            mFab.setClickable(false);
        } else if (isOnline) {
            // 如果当前应用有用户在线，并且此漫画的作者不是当前登录用户，则查询此用户是否收藏过此漫画册
            mPresenter.getFavoriteRelation(mFavorite);
        }

        mScrollListener.init();
        mPresenter.getBookReviewList(mAlbum, 0);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mReviewList.clear();
        isOnline = false;
        isFavorite = false;
    }

    @Override
    public void setPresenter(KinnerAlbumInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showBookFavoriteSuccess(Favorite favorite) {
        mFavorite.setObjectId(favorite.getObjectId());
        isFavorite = true;
        mFab.setImageResource(R.mipmap.ic_star_orange_fab);
        Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_LONG).show();
        mFab.setClickable(true);
    }

    @Override
    public void showBookFavoriteFailed(BmobException e) {
        isFavorite = false;
        Toast.makeText(getActivity(), "收藏失败", Toast.LENGTH_LONG).show();
        mFab.setClickable(true);
    }

    @Override
    public void showFavoriteRemoveSuccess() {
        mFavorite.setObjectId(null);
        isFavorite = false;
        mFab.setImageResource(R.mipmap.ic_star_silver_fab);
        Toast.makeText(getActivity(), "取消收藏", Toast.LENGTH_LONG).show();
        mFab.setClickable(true);
    }

    @Override
    public void showFavoriteRemoveFailed(BmobException e) {
        Toast.makeText(getActivity(), "取消收藏失败", Toast.LENGTH_LONG).show();
        mFab.setClickable(true);
    }

    @Override
    public void showFavoriteGetSuccess(Favorite favorite) {
        mFavorite.setObjectId(favorite.getObjectId());
        isFavorite = true;
        mFab.setImageResource(R.mipmap.ic_star_orange_fab);
        mFab.setClickable(true);
    }

    @Override
    public void showFavoriteGetFailed(BmobException e) {
        mFab.setClickable(true);
    }

    // 更新漫画册收藏者数目信息
    @Override
    public void showBookFavorTotalUpdateSuccess(Album album) {
        mAlbum = album;
    }

    @Override
    public void showReviewListGetSuccess(List<Review> reviewList) {
        mReviewList.addAll(reviewList);
        mBookInfoAdapter.notifyDataSetChanged();
    }

    @Override
    public void showReviewListGetFailed(BmobException e) {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    public void actionEditReview() {
        if (null != BmobUser.getCurrentUser(User.class)) {
            KinnerBookReviewEditActivity.actionStart(this, mAlbum, REQUEST_REVIEW);
        } else {
            Toast.makeText(getActivity(), "登录Nemo 即刻评论", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (REQUEST_REVIEW == requestCode) {
            // 由于此过程Fragment通过自己直接启动了一个新的Activity
            // 所以另一个Activity销毁的时候直接调用了此Fragment的onActivityResult方法
            Review review = (Review) data.getSerializableExtra(KinnerBookReviewEditFragment.EXTRA_REVIEW);
            mReviewList.add(review);
            mBookInfoAdapter.notifyDataSetChanged();
            Toast.makeText(getActivity(), R.string.prompt_review_success, Toast.LENGTH_LONG).show();
        }
    }
}