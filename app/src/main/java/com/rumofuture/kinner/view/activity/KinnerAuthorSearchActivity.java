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
import com.rumofuture.kinner.presenter.KinnerAuthorSearchPresenter;
import com.rumofuture.kinner.view.fragment.KinnerAuthorSearchFragment;

public class KinnerAuthorSearchActivity extends KinnerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nemo_fragment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (null != getSupportActionBar()) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        KinnerAuthorSearchFragment fragment = (KinnerAuthorSearchFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (null == fragment) {
            fragment = KinnerAuthorSearchFragment.newInstance();
            KinnerActivityManager.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        }

        KinnerAuthorSearchPresenter presenter = new KinnerAuthorSearchPresenter(
                fragment,
                DataSourceManager.provideUserRepository(KinnerAuthorSearchActivity.this)
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

    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, KinnerAuthorSearchActivity.class);
        context.startActivity(intent);
    }
}
