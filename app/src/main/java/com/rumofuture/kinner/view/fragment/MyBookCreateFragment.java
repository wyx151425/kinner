package com.rumofuture.kinner.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.contract.MyBookCreateContract;
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.model.entity.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

import static android.app.Activity.RESULT_OK;

public class MyBookCreateFragment extends Fragment implements MyBookCreateContract.View {

    public static final String EXTRA_ALBUM = "com.rumofuture.nemo.view.fragment.MyBookCreateFragment.book";

    private MyBookCreateContract.Presenter mPresenter;

    private KinnerProgressBarFragment mProgressBar;

    private ImageView mBookCoverView;
    private EditText mBookNameView;
    private TextView mBookStyleView;
    private EditText mBookIntroductionView;
    private SwitchCompat mBookShowSwitchView;

    private static final int REQUEST_STYLE = 711;

    public MyBookCreateFragment() {

    }

    public static MyBookCreateFragment newInstance() {
        return new MyBookCreateFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!BmobUser.getCurrentUser(User.class).getAuthorize()) {
            showDialog();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mProgressBar = KinnerProgressBarFragment.newInstance(getString(R.string.prompt_creating));
        View view = inflater.inflate(R.layout.fragment_my_book_create, container, false);

        mBookCoverView = view.findViewById(R.id.book_cover_view);
        mBookCoverView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.chooseImage();
            }
        });

        mBookNameView = view.findViewById(R.id.book_name_view);
        mBookIntroductionView = view.findViewById(R.id.book_introduction_view);
        mBookShowSwitchView = view.findViewById(R.id.book_show_switch_view);

        mBookStyleView = view.findViewById(R.id.book_style_view);
        mBookStyleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KinnerBookStylePickerFragment dialog = new KinnerBookStylePickerFragment();
                dialog.setTargetFragment(MyBookCreateFragment.this, REQUEST_STYLE);
                dialog.show(getFragmentManager(), null);
            }
        });

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Album album = new Album();
                album.setName(mBookNameView.getText().toString().trim());
                album.setStyle(mBookStyleView.getText().toString());
                album.setIntroduction(mBookIntroductionView.getText().toString());
                album.setShow(mBookShowSwitchView.isChecked());
                mPresenter.createBook(album);
            }
        });

        return view;
    }

    @Override
    public void setPresenter(MyBookCreateContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showBookCreateSuccess(Album album) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ALBUM, album);
        getActivity().setResult(RESULT_OK, intent);
        getActivity().finish();
    }

    @Override
    public void showBookCreateFailed(BmobException e) {
        Toast.makeText(getActivity(), R.string.prompt_create_failed, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 291 && resultCode == RESULT_OK) {
            mPresenter.submitChoice(requestCode, data);
        }

        if (requestCode == REQUEST_STYLE && resultCode == RESULT_OK) {
            String style = data.getStringExtra(KinnerBookStylePickerFragment.EXTRA_STYLE);
            mBookStyleView.setText(style);
        }
    }

    @Override
    public void showBookInfoError(int stringId) {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.prompt_create_failed)
                .setMessage(getString(stringId))
                .setCancelable(true)
                .setPositiveButton(R.string.prompt_ok, null)
                .show();
    }

    @Override
    public void showBookCoverHasChosen(String imagePath) {
        Glide.with(getActivity()).load(imagePath).into(mBookCoverView);
    }

    @Override
    public void showProgressBar(boolean show) {
        if (show) {
            mProgressBar.show(getFragmentManager(), null);
        } else {
            mProgressBar.dismiss();
        }
    }

    private void showDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.prompt_create_failed)
                .setMessage("请完善个人信息")
                .setCancelable(false)
                .setPositiveButton(R.string.prompt_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                    }
                })
                .show();
    }
}
