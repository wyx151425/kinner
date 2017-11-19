package com.rumofuture.kinner.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.contract.KinnerReviewEditContract;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Review;
import com.rumofuture.kinner.model.entity.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

import static android.app.Activity.RESULT_OK;

public class KinnerBookReviewEditFragment extends Fragment implements KinnerReviewEditContract.View {

    public static final String EXTRA_REVIEW = "com.rumofuture.nemo.view.fragment.KinnerBookReviewEditFragment.review";
    private static final String ARG_PARAM = "com.rumofuture.nemo.view.fragment.KinnerBookReviewEditFragment.Album";

    private KinnerReviewEditContract.Presenter mPresenter;

    private Album mAlbum;
    private Review mReview;

    public KinnerBookReviewEditFragment() {

    }

    public static KinnerBookReviewEditFragment newInstance(Album album) {
        Bundle args = new Bundle();
        KinnerBookReviewEditFragment fragment = new KinnerBookReviewEditFragment();
        args.putSerializable(ARG_PARAM, album);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getActivity()) {
            mAlbum = (Album) getArguments().getSerializable(ARG_PARAM);
        }
        mReview = new Review(mAlbum, BmobUser.getCurrentUser(User.class));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_review_edit, container, false);

        final EditText reviewContentView = view.findViewById(R.id.review_content_view);
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reviewContentView.getText() == null || reviewContentView.getText().toString().equals("")) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("评论失败")
                            .setMessage("请编辑评论内容")
                            .setCancelable(true)
                            .setPositiveButton("确定", null)
                            .show();
                } else {
                    mReview.setContent(reviewContentView.getText().toString());
                    mPresenter.saveReview(mReview);
                }
            }
        });

        return view;
    }

    @Override
    public void setPresenter(KinnerReviewEditContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgressBar(boolean show) {

    }

    @Override
    public void showReviewSaveSuccess(Review review) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_REVIEW, review);
        getActivity().setResult(RESULT_OK, intent);
        getActivity().finish();
    }

    @Override
    public void showReviewSaveFailed(BmobException e) {
        Toast.makeText(getActivity(), "评论失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
