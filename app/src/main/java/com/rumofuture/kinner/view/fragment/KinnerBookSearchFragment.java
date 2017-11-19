package com.rumofuture.kinner.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.contract.KinnerAlbumSearchContract;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.view.adapter.KinnerAlbumSearchListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class KinnerBookSearchFragment extends Fragment implements KinnerAlbumSearchContract.View {

    private KinnerAlbumSearchContract.Presenter mPresenter;
    private KinnerProgressBarFragment mProgressBar;

    private List<Album> mAlbumList;
    private KinnerAlbumSearchListAdapter mBookListAdapter;

    public KinnerBookSearchFragment() {

    }

    public static KinnerBookSearchFragment newInstance() {
        return new KinnerBookSearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mAlbumList = new ArrayList<>();
        mBookListAdapter = new KinnerAlbumSearchListAdapter(mAlbumList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mProgressBar = KinnerProgressBarFragment.newInstance(getResources().getString(R.string.prompt_loading));

        View view = inflater.inflate(R.layout.fragment_nemo_recycler_view, container, false);

        RecyclerView bookListView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        bookListView.setLayoutManager(layoutManager);
        bookListView.setAdapter(mBookListAdapter);

        return view;
    }

    @Override
    public void setPresenter(KinnerAlbumSearchContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgressBar(boolean show) {
        if (show) {
            mProgressBar.show(getFragmentManager(), null);
        } else {
            mProgressBar.dismiss();
        }
    }

    @Override
    public void showBookSearchSuccess(List<Album> albumList) {
        mAlbumList.clear();
        mAlbumList.addAll(albumList);
        mBookListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showBookSearchFailed(BmobException e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_nemo_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String keyword) {
                mPresenter.searchBook(keyword);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String keyword) {
                return false;
            }
        });
    }
}
