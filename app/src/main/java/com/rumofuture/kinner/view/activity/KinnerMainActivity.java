package com.rumofuture.kinner.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.KinnerActivity;
import com.rumofuture.kinner.app.manager.DataSourceManager;
import com.rumofuture.kinner.model.entity.User;
import com.rumofuture.kinner.presenter.KinnerMainAlbumPresenter;
import com.rumofuture.kinner.presenter.KinnerMainDiscoverPresenter;
import com.rumofuture.kinner.presenter.KinnerMainPresenter;
import com.rumofuture.kinner.view.fragment.KinnerMainAlbumFragment;
import com.rumofuture.kinner.view.fragment.KinnerMainDiscoverFragment;
import com.rumofuture.kinner.view.fragment.KinnerMainFragment;
import com.rumofuture.kinner.view.fragment.KinnerMainMeFragment;
import com.rumofuture.kinner.view.fragment.KinnerMainWelcomeFragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.bmob.v3.BmobUser;

/**
 * Created by WangZhenqi on 2016/12/24.
 */

public class KinnerMainActivity extends KinnerActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String EXTRA_LOG = "com.rumofuture.nemo.view.activity.KinnerMainActivity.log";

    private KinnerMainFragment mMainFragment;
    private KinnerMainAlbumFragment mAlbumFragment;
    private KinnerMainDiscoverFragment mDiscoverFragment;
    private KinnerMainMeFragment mMeFragment;
    private KinnerMainWelcomeFragment mWelcomeFragment;

    private int navigationIndex = 0;

    private static final int NAVIGATION_MAIN_INDEX = 0;
    private static final int NAVIGATION_ALBUM_INDEX = 1;
    private static final int NAVIGATION_DISCOVER_INDEX = 2;
    private static final int NAVIGATION_ME_INDEX = 3;

    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kinner_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mBottomNavigationView = findViewById(R.id.bottom_navigation_view);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        KinnerMainActivity.BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean isLog = getIntent().getBooleanExtra(EXTRA_LOG, false);
        if (isLog) {
            navigationIndex = 3;
            mBottomNavigationView.setSelectedItemId(R.id.navigation_me);
            showFragment(navigationIndex);
        } else {
            showFragment(navigationIndex);
        }
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        showFragment(navigationIndex);
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_main:
                showFragment(NAVIGATION_MAIN_INDEX);
                return true;
            case R.id.navigation_album:
                showFragment(NAVIGATION_ALBUM_INDEX);
                return true;
            case R.id.navigation_discover:
                showFragment(NAVIGATION_DISCOVER_INDEX);
                return true;
            case R.id.navigation_me:
                showFragment(NAVIGATION_ME_INDEX);
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nemo_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_search_book:
                KinnerBookSearchActivity.actionStart(KinnerMainActivity.this);
                return true;
            case R.id.action_search_author:
                KinnerAuthorSearchActivity.actionStart(KinnerMainActivity.this);
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

    public static void actionStart(Context context, boolean isLogOut) {
        Intent intent = new Intent();
        intent.setClass(context, KinnerMainActivity.class);
        intent.putExtra(EXTRA_LOG, isLogOut);
        context.startActivity(intent);
    }

    public void showFragment(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        navigationIndex = index;
        switch (index) {
            case NAVIGATION_MAIN_INDEX:
                if (null == mMainFragment) {
                    mMainFragment = KinnerMainFragment.newInstance();
                    KinnerMainPresenter presenter = new KinnerMainPresenter(
                            mMainFragment,
                            DataSourceManager.provideBookRepository(KinnerMainActivity.this)
                    );
                    mMainFragment.setPresenter(presenter);
                    transaction.add(R.id.fragment_container, mMainFragment);
                } else {
                    transaction.show(mMainFragment);
                }
                break;
            case NAVIGATION_ALBUM_INDEX:
                if (null == mAlbumFragment) {
                    mAlbumFragment = KinnerMainAlbumFragment.newInstance();
                    KinnerMainAlbumPresenter presenter = new KinnerMainAlbumPresenter(
                            mAlbumFragment,
                            DataSourceManager.provideAlbumRepository(KinnerMainActivity.this)
                    );
                    mAlbumFragment.setPresenter(presenter);
                    transaction.add(R.id.fragment_container, mAlbumFragment);
                } else {
                    transaction.show(mAlbumFragment);
                }
                break;
            case NAVIGATION_DISCOVER_INDEX:
                if (null == mDiscoverFragment) {
                    mDiscoverFragment = KinnerMainDiscoverFragment.newInstance();
                    KinnerMainDiscoverPresenter presenter = new KinnerMainDiscoverPresenter(
                            mDiscoverFragment,
                            DataSourceManager.provideUserRepository(KinnerMainActivity.this)
                    );
                    mDiscoverFragment.setPresenter(presenter);
                    transaction.add(R.id.fragment_container, mDiscoverFragment);
                } else {
                    transaction.show(mDiscoverFragment);
                }
                break;
            case NAVIGATION_ME_INDEX:
                if (null == BmobUser.getCurrentUser(User.class)) {
                    if (null != mMeFragment) {
                        transaction.remove(mMeFragment);
                    }
                    if (null == mWelcomeFragment) {
                        mWelcomeFragment = KinnerMainWelcomeFragment.newInstance();
                        transaction.add(R.id.fragment_container, mWelcomeFragment);
                    } else {
                        transaction.show(mWelcomeFragment);
                    }
                } else {
                    if (null != mWelcomeFragment) {
                        transaction.remove(mWelcomeFragment);
                    }
                    if (null == mMeFragment) {
                        mMeFragment = KinnerMainMeFragment.newInstance();
                        transaction.add(R.id.fragment_container, mMeFragment);
                    } else {
                        transaction.show(mMeFragment);
                    }
                }
                break;
        }
        transaction.commit();
    }

    public void hideFragment(FragmentTransaction transaction) {
        if (null != mMainFragment) {
            transaction.hide(mMainFragment);
        }
        if (null != mAlbumFragment) {
            transaction.hide(mAlbumFragment);
        }
        if (null != mDiscoverFragment) {
            transaction.hide(mDiscoverFragment);
        }
        if (null != mMeFragment) {
            transaction.hide(mMeFragment);
        }
        if (null != mWelcomeFragment) {
            transaction.hide(mWelcomeFragment);
        }
    }

    private static class BottomNavigationViewHelper {
        @SuppressLint("RestrictedApi")
        static void disableShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int index = 0; index < menuView.getChildCount(); index++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(index);
                    item.setShiftingMode(false);
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException | IllegalAccessException ignored) {

            }
        }
    }
}
