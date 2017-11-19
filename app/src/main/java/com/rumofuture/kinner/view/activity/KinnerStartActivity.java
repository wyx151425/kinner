package com.rumofuture.kinner.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.KinnerActivity;

/**
 * Created by WangZhenqi on 2017/9/21.
 */

public class KinnerStartActivity extends KinnerActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nemo_start);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                KinnerMainActivity.actionStart(KinnerStartActivity.this, false);
                finish();
            }
        }, 1250);
    }
}
