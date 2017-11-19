package com.rumofuture.kinner.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.KinnerActivity;
import com.rumofuture.kinner.app.manager.DataSourceManager;
import com.rumofuture.kinner.app.manager.KinnerActivityManager;
import com.rumofuture.kinner.model.entity.Style;
import com.rumofuture.kinner.presenter.KinnerAlbumBookListPresenter;
import com.rumofuture.kinner.view.fragment.KinnerAlbumBookListFragment;

public class KinnerAlbumBookListActivity extends KinnerActivity {

    private static final String EXTRA_ALBUM = "com.rumofuture.nemo.view.activity.KinnerAlbumBookListActivity.album";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nemo_album_book_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Style style = (Style) getIntent().getSerializableExtra(EXTRA_ALBUM);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(style.getStyle());
        }

        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorBlack));
        toolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorWhite));

        ImageView albumCoverView = (ImageView) findViewById(R.id.album_cover_view);
        albumCoverView.setImageResource(style.getImageId());

        KinnerAlbumBookListFragment fragment =
                (KinnerAlbumBookListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (null == fragment) {
            fragment = KinnerAlbumBookListFragment.newInstance(style);
            KinnerActivityManager.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        }

        KinnerAlbumBookListPresenter presenter = new KinnerAlbumBookListPresenter(
                fragment,
                DataSourceManager.provideBookRepository(KinnerAlbumBookListActivity.this),
                DataSourceManager.provideAlbumRepository(KinnerAlbumBookListActivity.this)
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

    public static void actionStart(Context context, Style style) {
        Intent intent = new Intent();
        intent.setClass(context, KinnerAlbumBookListActivity.class);
        intent.putExtra(EXTRA_ALBUM, style);
        context.startActivity(intent);
    }
}
