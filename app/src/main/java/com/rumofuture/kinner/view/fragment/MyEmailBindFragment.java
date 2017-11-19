package com.rumofuture.kinner.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.contract.MyEmailBindContract;

import cn.bmob.v3.exception.BmobException;

public class MyEmailBindFragment extends Fragment implements MyEmailBindContract.View {

    private MyEmailBindContract.Presenter mPresenter;

    private KinnerProgressBarFragment mProgressBar;
    private EditText mEmailView;

    public MyEmailBindFragment() {
        
    }

    public static MyEmailBindFragment newInstance() {
        return new MyEmailBindFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mProgressBar = KinnerProgressBarFragment.newInstance(getResources().getString(R.string.prompt_binding));
        View view = inflater.inflate(R.layout.fragment_nemo_email_bind, container, false);

        mEmailView = view.findViewById(R.id.email_view);
        final Button emailBindButton = view.findViewById(R.id.email_bind_button);

        emailBindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.bindEmail(mEmailView.getText().toString().trim());
            }
        });

        return view;
    }

    @Override
    public void setPresenter(MyEmailBindContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgressBar(boolean show) {
        if (show) {
            mProgressBar.show(getFragmentManager(), null);
        } else {
            mProgressBar.dismiss();
        }
    }

    @Override
    public void showEmailError(Integer stringId) {
        if (null == stringId) {
            mEmailView.setError(null);
        } else {
            mEmailView.setError(getString(stringId));
            mEmailView.requestFocus();
        }
    }

    @Override
    public void showEmailBindSuccess(String prompt) {
        Toast.makeText(getActivity(), prompt, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showEmailBindFailed(BmobException e) {
        Toast.makeText(getActivity(), R.string.prompt_email_bind_failed, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
