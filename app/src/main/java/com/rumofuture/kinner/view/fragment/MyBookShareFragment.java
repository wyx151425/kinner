package com.rumofuture.kinner.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.contract.MyBookUpdateContract;
import com.rumofuture.kinner.model.entity.Album;

import cn.bmob.v3.exception.BmobException;

public class MyBookShareFragment extends Fragment implements MyBookUpdateContract.View {

    private static final String ARG_BOOK = "com.rumofuture.nemo.view.fragment.MyBookShareFragment.book";

    private static final String DIALOG_URL = "DialogURL";
    private static final int REQUEST_URL = 808;

    private MyBookUpdateContract.Presenter mPresenter;

    private Album mAlbum;
    private WebView mWebView;
    private String mNewURL;
    private String mOldURL;

    private KinnerProgressBarFragment mProgressBar;

    public MyBookShareFragment() {

    }

    public static MyBookShareFragment newInstance(Album album) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK, album);
        MyBookShareFragment fragment = new MyBookShareFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mAlbum = (Album) getArguments().getSerializable(ARG_BOOK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mProgressBar = KinnerProgressBarFragment.newInstance(getString(R.string.prompt_updating));

        View view = inflater.inflate(R.layout.fragment_my_book_share, container, false);
        mWebView = view.findViewById(R.id.web_view);
        if (null != mAlbum.getUrl()) {
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.setWebViewClient(new KinnerBookShareFragment.NemoWebViewClient());
            mWebView.setSaveEnabled(false);
            mWebView.loadUrl(mAlbum.getUrl());
        }

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyBookURLEditFragment URLEditDialog = MyBookURLEditFragment.newInstance();
                URLEditDialog.setTargetFragment(MyBookShareFragment.this, REQUEST_URL);
                URLEditDialog.show(getFragmentManager(), DIALOG_URL);
            }
        });

        return view;
    }

    @Override
    public void setPresenter(MyBookUpdateContract.Presenter presenter) {
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
    public void showBookUpdateSuccess(Album album) {
        mWebView.loadUrl(mNewURL);
        Toast.makeText(getActivity(), R.string.prompt_update_success, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showBookUpdateFailed(BmobException e) {
        mAlbum.setUrl(mOldURL);
        Toast.makeText(getActivity(), R.string.prompt_update_failed, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (REQUEST_URL == requestCode) {
            mNewURL = data.getStringExtra(MyBookURLEditFragment.EXTRA_URL);
            mOldURL = mAlbum.getUrl();
            mAlbum.setUrl(mNewURL);
            mPresenter.updateBook(mAlbum);
        }
    }
}
