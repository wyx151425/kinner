package com.rumofuture.kinner.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.KinnerActivity;
import com.rumofuture.kinner.app.manager.DataSourceManager;
import com.rumofuture.kinner.app.manager.KinnerActivityManager;
import com.rumofuture.kinner.presenter.MyInfoUpdatePresenter;
import com.rumofuture.kinner.view.fragment.MyInfoEditFragment;

import java.lang.reflect.Method;

public class MyInfoEditActivity extends KinnerActivity {

    private MyInfoEditFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mFragment = (MyInfoEditFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (mFragment == null) {
            mFragment = new MyInfoEditFragment();
            KinnerActivityManager.addFragmentToActivity(getSupportFragmentManager(), mFragment, R.id.fragment_container);
        }

        MyInfoUpdatePresenter presenter = new MyInfoUpdatePresenter(
                mFragment,
                DataSourceManager.provideUserRepository(MyInfoEditActivity.this)
        );
        mFragment.setPresenter(presenter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_info_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_update_avatar:
                mFragment.updateUserAvatar();
                return true;
            case R.id.action_update_portrait:
                mFragment.updateUserPortrait();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 显示overflow菜单图标
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MyInfoEditActivity.class);
        context.startActivity(intent);
    }
}
