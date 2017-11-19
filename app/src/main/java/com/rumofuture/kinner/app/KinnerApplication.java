package com.rumofuture.kinner.app;

import android.support.multidex.MultiDexApplication;

import cn.bmob.v3.Bmob;

/**
 * Created by WangZhenqi on 2016/12/24.
 */

public class KinnerApplication extends MultiDexApplication {

    private static final String APPLICATION_ID = "b77129d99ffa977d698830535cb12d7b";

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, APPLICATION_ID);
    }
}
