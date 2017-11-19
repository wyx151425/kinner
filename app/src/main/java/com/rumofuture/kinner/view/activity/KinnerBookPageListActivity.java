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
import com.rumofuture.kinner.presenter.KinnerAlbumMusicListPresenter;
import com.rumofuture.kinner.view.fragment.KinnerBookPageListFragment;

public class KinnerBookPageListActivity extends KinnerActivity {

    private static final String EXTRA_BOOK_OBJECT = "com.rumofuture.nemo.view.activity.KinnerBookPageListActivity.BookObject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nemo_fragment);

        Album album = (Album) getIntent().getSerializableExtra(EXTRA_BOOK_OBJECT);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(album.getName());
        }

        KinnerBookPageListFragment fragment = (KinnerBookPageListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = KinnerBookPageListFragment.newInstance(album);
            KinnerActivityManager.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.fragment_container);
        }

        // Create the presenter
        KinnerAlbumMusicListPresenter presenter = new KinnerAlbumMusicListPresenter(
                fragment,
                DataSourceManager.providePageRepository(KinnerBookPageListActivity.this)
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
        intent.setClass(context, KinnerBookPageListActivity.class);
        intent.putExtra(EXTRA_BOOK_OBJECT, album);
        context.startActivity(intent);
    }
}
