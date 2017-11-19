package com.rumofuture.kinner.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.contract.MyBookReviewListContract;
import com.rumofuture.kinner.app.widget.OnListScrollListener;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Review;
import com.rumofuture.kinner.model.source.ReviewDataSource;
import com.rumofuture.kinner.view.adapter.MyAlbumInfoAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/19.
 */

public class MyBookInfoFragment extends Fragment implements MyBookReviewListContract.View {

    private static final String ARG_BOOK = "com.rumofuture.nemo.view.fragment.MyBookInfoFragment.book";

    private MyBookReviewListContract.Presenter mPresenter;

    private Album mAlbum;
    private List<Review> mReviewList;
    private MyAlbumInfoAdapter mReviewListAdapter;
    private OnListScrollListener mScrollListener;

    public MyBookInfoFragment() {

    }

    public static MyBookInfoFragment newInstance(Album album) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK, album);
        MyBookInfoFragment fragment = new MyBookInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mAlbum = (Album) getArguments().getSerializable(ARG_BOOK);
        }
        mReviewList = new ArrayList<>();
        mReviewListAdapter = new MyAlbumInfoAdapter(this, mAlbum, mReviewList);
        mScrollListener = new OnListScrollListener(ReviewDataSource.PAGE_LIMIT) {
            @Override
            public void onLoadMore(int pageCode) {
                mPresenter.getBookReviewList(mAlbum, pageCode);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_recycler_view, container, false);

        RecyclerView reviewListView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        reviewListView.setLayoutManager(layoutManager);
        reviewListView.setAdapter(mReviewListAdapter);

        mScrollListener.setLayoutManager(layoutManager);
        reviewListView.addOnScrollListener(mScrollListener);

        mScrollListener.init();
        mPresenter.getBookReviewList(mAlbum, 0);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mReviewList.clear();
    }

    @Override
    public void setPresenter(MyBookReviewListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgressBar(boolean show) {

    }

    @Override
    public void showReviewDeleteSuccess(Review review) {
        mReviewList.remove(review);
        mReviewListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showReviewDeleteFailed(BmobException e) {
        Toast.makeText(getActivity(), "删除评论失败" + e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showReviewListGetSuccess(List<Review> reviewList) {
        for (Review review : reviewList) {
            mReviewList.add(review);
        }
        mReviewListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showReviewListGetFailed(BmobException e) {
        Toast.makeText(getActivity(), "获取评论失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    public void actionDeleteReview(Review review) {
        mPresenter.deleteReview(review);
    }
}
