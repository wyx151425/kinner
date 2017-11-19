package com.rumofuture.kinner.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rumofuture.kinner.app.manager.KinnerActivityManager;

/**
 * Created by WangZhenqi on 2017/2/12.
 */

public class KinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KinnerActivityManager.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KinnerActivityManager.removeActivity(this);
    }
}
