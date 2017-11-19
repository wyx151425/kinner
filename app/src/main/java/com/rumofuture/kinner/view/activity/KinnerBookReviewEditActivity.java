package com.rumofuture.kinner.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.KinnerActivity;
import com.rumofuture.kinner.app.manager.DataSourceManager;
import com.rumofuture.kinner.app.manager.KinnerActivityManager;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.presenter.KinnerReviewEditPresenter;
import com.rumofuture.kinner.view.fragment.KinnerBookReviewEditFragment;

public class KinnerBookReviewEditActivity extends KinnerActivity {

    private static final String EXTRA_BOOK = "com.rumofuture.nemo.view.activity.KinnerBookReviewEditActivity.book";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nemo_review_edit);

        Album album = (Album) getIntent().getSerializableExtra(EXTRA_BOOK);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (null != getSupportActionBar()) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(album.getName());
        }

        KinnerBookReviewEditFragment fragment =
                (KinnerBookReviewEditFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (null == fragment) {
            fragment = KinnerBookReviewEditFragment.newInstance(album);
            KinnerActivityManager.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        }

        KinnerReviewEditPresenter presenter = new KinnerReviewEditPresenter(
                fragment,
                DataSourceManager.provideReviewRepository(KinnerBookReviewEditActivity.this)
        );
        fragment.setPresenter(presenter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void actionStart(Fragment fragment, Album album, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(fragment.getActivity(), KinnerBookReviewEditActivity.class);
        intent.putExtra(EXTRA_BOOK, album);
        fragment.startActivityForResult(intent, requestCode);
    }
}
