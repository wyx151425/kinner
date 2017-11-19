package com.rumofuture.kinner.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.contract.KinnerMainContract;
import com.rumofuture.kinner.app.widget.OnMainScrollListener;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.source.AlbumDataSource;
import com.rumofuture.kinner.view.adapter.KinnerMainAlbumListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class KinnerMainFragment extends Fragment implements KinnerMainContract.View {

    private KinnerMainContract.Presenter mPresenter;

    private ProgressBar mProgressBar;

    private List<Album> mAlbumList;
    private KinnerMainAlbumListAdapter mBookListAdapter;
    private RecyclerView mBookListView;

    private int mPageCode = 0;
    private boolean mQueryable = true;

    public KinnerMainFragment() {

    }

    public static KinnerMainFragment newInstance() {
        return new KinnerMainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlbumList = new ArrayList<>();
        mBookListAdapter = new KinnerMainAlbumListAdapter(mAlbumList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_main, container, false);

        mProgressBar = view.findViewById(R.id.progress_bar_view);

        mBookListView = view.findViewById(R.id.book_list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mBookListView.setLayoutManager(layoutManager);
        mBookListView.setAdapter(mBookListAdapter);
        mBookListView.addOnScrollListener(new OnMainScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if (mQueryable) {
                    mQueryable = false;
                    mPresenter.getBookList(mPageCode);
                }
            }
        });

        mPresenter.getBookList(mPageCode);

        return view;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        mPageCode = 0;
//        mQueryable = true;
//        mPresenter.getBookList(mPageCode);
//    }

    @Override
    public void setPresenter(KinnerMainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showBookListGetSuccess(List<Album> albumList) {
        // 当pageCode为0时，此时界面可能已刷新，需要将之前的数据全部删除掉，否则会出现重复数据项
        // 当pageCode不为0时，此时界面正在等待获取下一个数据分页，所以将数据项直接追加
        mQueryable = albumList.size() >= AlbumDataSource.PAGE_LIMIT;
        if (0 == mPageCode) {
            mAlbumList.clear();
        }
        mAlbumList.addAll(albumList);
        mBookListAdapter.notifyDataSetChanged();
        mPageCode++;
    }

    @Override
    public void showBookListGetFailed(BmobException e) {
        Toast.makeText(getActivity(), R.string.prompt_load_failed, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showProgressBar(final boolean show) {

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mBookListView.setVisibility(show ? View.GONE : View.VISIBLE);
        mBookListView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mBookListView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressBar.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
}
