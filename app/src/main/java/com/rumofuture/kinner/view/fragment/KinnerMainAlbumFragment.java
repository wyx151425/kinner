package com.rumofuture.kinner.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.contract.KinnerMainStyleContract;
import com.rumofuture.kinner.model.entity.Style;
import com.rumofuture.kinner.model.source.StyleDataSource;
import com.rumofuture.kinner.view.adapter.KinnerMainStyleListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class KinnerMainAlbumFragment extends Fragment implements KinnerMainStyleContract.View {

    private KinnerMainStyleContract.Presenter mPresenter;

    private List<Style> mStyleList;
    private KinnerMainStyleListAdapter mAlbumListAdapter;

    public KinnerMainAlbumFragment() {

    }

    public static KinnerMainAlbumFragment newInstance() {
        return new KinnerMainAlbumFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStyleList = new ArrayList<>();
        for (int index = 0; index < StyleDataSource.PAGE_LIMIT; index++) {
            Style style = new Style();
            style.setAlbumTotal(0);
            mStyleList.add(style);
        }
        mAlbumListAdapter = new KinnerMainStyleListAdapter(mStyleList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_main_album, container, false);

        RecyclerView albumListView = view.findViewById(R.id.album_list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        albumListView.setLayoutManager(layoutManager);
        albumListView.setAdapter(mAlbumListAdapter);

        mPresenter.getAlbumList();

        return view;
    }

    @Override
    public void setPresenter(KinnerMainStyleContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showAlbumListGetSuccess(List<Style> styleList) {
        mStyleList.clear();
        mStyleList.addAll(styleList);
        mAlbumListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAlbumListGetFailed(BmobException e) {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
