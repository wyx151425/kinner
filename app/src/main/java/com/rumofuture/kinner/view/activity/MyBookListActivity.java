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
import com.rumofuture.kinner.presenter.MyBookListPresenter;
import com.rumofuture.kinner.view.fragment.MyBookListFragment;

public class MyBookListActivity extends KinnerActivity {

    private MyBookListFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mFragment =
                (MyBookListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (mFragment == null) {
            mFragment = MyBookListFragment.newInstance();
            KinnerActivityManager.addFragmentToActivity(getSupportFragmentManager(),
                    mFragment, R.id.fragment_container);
        }

        // 创建Presenter
        MyBookListPresenter presenter = new MyBookListPresenter(
                mFragment,
                DataSourceManager.provideUserRepository(MyBookListActivity.this),
                DataSourceManager.provideBookRepository(MyBookListActivity.this)
        );

        mFragment.setPresenter(presenter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MyBookListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFragment.onActivityResult(requestCode, resultCode, data);
    }
}
