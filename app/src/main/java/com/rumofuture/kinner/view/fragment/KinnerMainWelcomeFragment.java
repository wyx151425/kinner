package com.rumofuture.kinner.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.view.activity.KinnerLogInActivity;
import com.rumofuture.kinner.view.activity.KinnerSignUpActivity;

public class KinnerMainWelcomeFragment extends Fragment {

    public KinnerMainWelcomeFragment() {

    }

    public static KinnerMainWelcomeFragment newInstance() {
        return new KinnerMainWelcomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_main_welcome, container, false);

        Button logInButton = view.findViewById(R.id.log_in_button);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KinnerLogInActivity.actionStart(getActivity());
            }
        });

        Button signUpButton = view.findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KinnerSignUpActivity.actionStart(getActivity());
            }
        });

        return view;
    }
}
