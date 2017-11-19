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
import com.rumofuture.kinner.presenter.MyBookPageListPresenter;
import com.rumofuture.kinner.view.fragment.MyBookPageListFragment;

public class MyBookPageListActivity extends KinnerActivity {

    private static final String EXTRA_BOOK = "com.rumofuture.wzq.nemo.view.activity.MyBookPageListActivity.book";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_page_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Album album = (Album) getIntent().getSerializableExtra(EXTRA_BOOK);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(album.getName());
        }

        MyBookPageListFragment fragment = (MyBookPageListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = MyBookPageListFragment.newInstance(album);
            KinnerActivityManager.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.fragment_container);
        }

        // Create the presenter
        MyBookPageListPresenter presenter = new MyBookPageListPresenter(
                fragment,
                DataSourceManager.provideBookRepository(MyBookPageListActivity.this),
                DataSourceManager.providePageRepository(MyBookPageListActivity.this)
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
        intent.setClass(context, MyBookPageListActivity.class);
        intent.putExtra(EXTRA_BOOK, album);
        context.startActivity(intent);
    }
}
