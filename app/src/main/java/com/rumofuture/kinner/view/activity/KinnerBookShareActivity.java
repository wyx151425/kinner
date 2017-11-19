package com.rumofuture.kinner.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.KinnerActivity;
import com.rumofuture.kinner.app.manager.KinnerActivityManager;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.view.fragment.KinnerBookShareFragment;

/**
 * Created by WangZhenqi on 2017/10/13.
 */

public class KinnerBookShareActivity extends KinnerActivity {

    public static final String EXTRA_BOOK = "com.rumofuture.nemo.view.activity.KinnerBookShareActivity.url";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nemo_fragment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        KinnerBookShareFragment fragment =
                (KinnerBookShareFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (null == fragment) {
            fragment = KinnerBookShareFragment.newInstance((Album) getIntent().getSerializableExtra(EXTRA_BOOK));
            KinnerActivityManager.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        }
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
        intent.setClass(context, KinnerBookShareActivity.class);
        intent.putExtra(EXTRA_BOOK, album);
        context.startActivity(intent);
    }
}
