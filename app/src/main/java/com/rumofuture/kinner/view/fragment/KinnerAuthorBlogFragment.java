package com.rumofuture.kinner.view.fragment;

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
import com.rumofuture.kinner.app.widget.OnListScrollListener;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.Follow;
import com.rumofuture.kinner.model.entity.User;
import com.rumofuture.kinner.app.contract.KinnerAuthorBlogContract;
import com.rumofuture.kinner.model.source.AlbumDataSource;
import com.rumofuture.kinner.view.adapter.KinnerAuthorBlogListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

public class KinnerAuthorBlogFragment extends Fragment implements KinnerAuthorBlogContract.View {

    private static final String ARG_AUTHOR = "com.rumofuture.nemo.view.fragment.KinnerAuthorBlogFragment.author";

    private KinnerAuthorBlogContract.Presenter mPresenter;

    private boolean isOnline = false;
    private boolean isFollow = false;

    private User mAuthor;
    private Follow mFollow;
    private List<Album> mAlbumList;
    private KinnerAuthorBlogListAdapter mBookListAdapter;

    private FloatingActionButton mFab;
    private OnListScrollListener mScrollListener;

    public KinnerAuthorBlogFragment() {

    }

    public static KinnerAuthorBlogFragment newInstance(User author) {
        Bundle args = new Bundle();
        KinnerAuthorBlogFragment fragment = new KinnerAuthorBlogFragment();
        args.putSerializable(ARG_AUTHOR, author);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mAuthor = (User) getArguments().getSerializable(ARG_AUTHOR);
        }

        if (null != BmobUser.getCurrentUser(User.class)) {
            mFollow = new Follow(mAuthor, BmobUser.getCurrentUser(User.class));
            isOnline = true;
        } else {
            isOnline = false;
        }

        mAlbumList = new ArrayList<>();
        mBookListAdapter = new KinnerAuthorBlogListAdapter(mAuthor, mAlbumList);
        mScrollListener = new OnListScrollListener(AlbumDataSource.PAGE_LIMIT) {
            @Override
            public void onLoadMore(int pageCode) {
                mPresenter.getAuthorBookList(mAuthor, pageCode);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_recycler_view, container, false);

        mFab = getActivity().findViewById(R.id.fab);
        mFab.setClickable(false);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFab.setClickable(false);
                if (isOnline) {
                    if (isFollow) {
                        mPresenter.unfollowUser(mFollow);
                    } else {
                        mPresenter.followUser(mFollow);
                    }
                } else {
                    Toast.makeText(getActivity(), "登录Nemo 即刻关注", Toast.LENGTH_LONG).show();
                }
            }
        });

        RecyclerView bookListView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        bookListView.setLayoutManager(layoutManager);
        bookListView.setAdapter(mBookListAdapter);

        mScrollListener.setLayoutManager(layoutManager);
        bookListView.addOnScrollListener(mScrollListener);

        if (isOnline && mAuthor.getObjectId().equals(BmobUser.getCurrentUser(User.class).getObjectId())) {
            mFab.setImageResource(R.mipmap.ic_favorite_red_fab);
            mFab.setClickable(false);
        } else if (isOnline) {
            mPresenter.getFollowRelation(mFollow);
        }

        mScrollListener.init();
        mPresenter.getAuthorBookList(mAuthor, 0);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAlbumList.clear();
        isOnline = false;
        isFollow = false;
    }

    @Override
    public void setPresenter(KinnerAuthorBlogContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgressBar(boolean show) {

    }

    @Override
    public void showAuthorBookListGetSuccess(List<Album> albumList) {
        mAlbumList.addAll(albumList);
        mBookListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAuthorBookListGetFailed(BmobException e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showUserFollowSuccess(Follow follow) {
        mFollow.setObjectId(follow.getObjectId());
        isFollow = true;
        mFab.setImageResource(R.mipmap.ic_favorite_red_fab);
        Toast.makeText(getActivity(), "关注成功", Toast.LENGTH_LONG).show();
        mFab.setClickable(true);
    }

    @Override
    public void showUserFollowFailed(BmobException e) {
        Toast.makeText(getActivity(), "关注失败", Toast.LENGTH_LONG).show();
        mFab.setClickable(true);
    }

    @Override
    public void showUserUnfollowSuccess(Follow follow) {
        follow.setObjectId(null);
        mFollow = follow;
        isFollow = false;
        mFab.setImageResource(R.mipmap.ic_favorite_silver_fab);
        Toast.makeText(getActivity(), "取消关注", Toast.LENGTH_LONG).show();
        mFab.setClickable(true);
    }

    @Override
    public void showUserUnfollowFailed(BmobException e) {
        Toast.makeText(getActivity(), "取消关注失败", Toast.LENGTH_LONG).show();
        mFab.setClickable(true);
    }

    @Override
    public void showFollowGetSuccess(Follow follow) {
        mFollow.setObjectId(follow.getObjectId());
        isFollow = true;
        mFab.setImageResource(R.mipmap.ic_favorite_red_fab);
        mFab.setClickable(true);
    }

    @Override
    public void showFollowGetFailed(BmobException e) {
        mFab.setClickable(true);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
