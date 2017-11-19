package com.rumofuture.kinner.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.KinnerActivity;
import com.rumofuture.kinner.app.manager.DataSourceManager;
import com.rumofuture.kinner.app.manager.KinnerActivityManager;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.presenter.KinnerAlbumInfoPresenter;
import com.rumofuture.kinner.view.fragment.KinnerBookInfoFragment;

public class KinnerBookInfoActivity extends KinnerActivity {

    private static final String EXTRA_BOOK = "com.rumofuture.nemo.view.activity.KinnerBookInfoActivity.BookObject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nemo_book_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Album album = (Album) getIntent().getSerializableExtra(EXTRA_BOOK);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(album.getName());
        }

        KinnerBookInfoFragment fragment =
                (KinnerBookInfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = KinnerBookInfoFragment.newInstance(album);
            KinnerActivityManager.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        }

        KinnerAlbumInfoPresenter presenter = new KinnerAlbumInfoPresenter(
                fragment,
                DataSourceManager.provideUserRepository(KinnerBookInfoActivity.this),
                DataSourceManager.provideBookRepository(KinnerBookInfoActivity.this),
                DataSourceManager.provideReviewRepository(KinnerBookInfoActivity.this),
                DataSourceManager.provideFavoriteRepository(KinnerBookInfoActivity.this)
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

    public static void actionStart(Context context, Album album) {
        Intent intent = new Intent();
        intent.setClass(context, KinnerBookInfoActivity.class);
        intent.putExtra(EXTRA_BOOK, album);
        context.startActivity(intent);
    }
}
