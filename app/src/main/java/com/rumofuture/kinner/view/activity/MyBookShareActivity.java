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
import com.rumofuture.kinner.presenter.MyBookUpdatePresenter;
import com.rumofuture.kinner.view.fragment.MyBookShareFragment;

/**
 * Created by WangZhenqi on 2017/11/3.
 */

public class MyBookShareActivity extends KinnerActivity {

    private static final String EXTRA_BOOK = "com.rumofuture.nemo.view.activity.MyBookShareActivity.book";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_share);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        MyBookShareFragment fragment =
                (MyBookShareFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (null == fragment) {
            fragment = MyBookShareFragment.newInstance((Album) getIntent().getSerializableExtra(EXTRA_BOOK));
            KinnerActivityManager.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.fragment_container);
        }

        MyBookUpdatePresenter presenter = new MyBookUpdatePresenter(
                fragment,
                DataSourceManager.provideBookRepository(MyBookShareActivity.this)
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
        intent.setClass(context, MyBookShareActivity.class);
        intent.putExtra(EXTRA_BOOK, album);
        context.startActivity(intent);
    }
}
