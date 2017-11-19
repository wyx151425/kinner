package com.rumofuture.kinner.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.KinnerActivity;

public class KinnerAccountSecurityActivity extends KinnerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nemo_account_security);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (null != getSupportActionBar()) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView emailBindView = findViewById(R.id.email_bind_view);
        emailBindView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyEmailBindActivity.actionStart(KinnerAccountSecurityActivity.this);
            }
        });

        TextView passwordUpdateView = findViewById(R.id.password_update_view);
        passwordUpdateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPasswordUpdateActivity.actionStart(KinnerAccountSecurityActivity.this);
            }
        });
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
        intent.setClass(context, KinnerAccountSecurityActivity.class);
        context.startActivity(intent);
    }
}
