package com.rumofuture.kinner.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.rumofuture.kinner.R;

public class KinnerProgressBarFragment extends DialogFragment {

    private static final String ARG_PROMPT = "com.rumofuture.nemo.view.fragment.KinnerProgressBarFragment.prompt";

    private String mPrompt;

    public KinnerProgressBarFragment() {

    }

    public static KinnerProgressBarFragment newInstance(String prompt) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROMPT, prompt);
        KinnerProgressBarFragment fragment = new KinnerProgressBarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (null == mPrompt) {
            mPrompt = (String) getArguments().getSerializable(ARG_PROMPT);
        }
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_nemo_progress_bar, null);
        TextView promptView = view.findViewById(R.id.prompt_view);
        promptView.setText(mPrompt);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setCancelable(false)
                .create();
    }

    public void setPrompt(String prompt) {
        mPrompt = prompt;
    }
}
