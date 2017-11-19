package com.rumofuture.kinner.view.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.rumofuture.kinner.R;

public class MyBookURLEditFragment extends DialogFragment {

    public static final String EXTRA_URL = "com.rumofuture.nemo.view.fragment.MyBookURLEditFragment.URL";

    public MyBookURLEditFragment() {

    }

    public static MyBookURLEditFragment newInstance() {
        return new MyBookURLEditFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my_book_url_edit, null);
        final EditText bookURLView = view.findViewById(R.id.book_url_view);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.action_update_url)
                .setPositiveButton(R.string.prompt_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK, bookURLView.getText().toString().trim());
                    }
                })
                .setNegativeButton(R.string.prompt_cancel, null)
                .create();
    }

    private void sendResult(int resultCode, String URL) {
        if (null == getTargetFragment()) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_URL, URL);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
