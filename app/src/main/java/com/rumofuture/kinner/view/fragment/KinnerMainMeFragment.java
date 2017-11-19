package com.rumofuture.kinner.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rumofuture.kinner.R;
import com.rumofuture.kinner.app.manager.DataSourceManager;
import com.rumofuture.kinner.model.entity.User;
import com.rumofuture.kinner.presenter.KinnerMainMePresenter;
import com.rumofuture.kinner.view.activity.MyBlogActivity;
import com.rumofuture.kinner.view.activity.MyBookListActivity;
import com.rumofuture.kinner.view.activity.MyFavoriteBookListActivity;
import com.rumofuture.kinner.view.activity.MyFollowAuthorListActivity;
import com.rumofuture.kinner.view.activity.MyFollowerListActivity;
import com.rumofuture.kinner.view.activity.MySettingListActivity;

import cn.bmob.v3.BmobUser;

public class KinnerMainMeFragment extends Fragment {

    private ImageView mMyAvatarView;
    private TextView mMyNameView;
    private TextView mMyMottoView;
    private TextView mMyFollowAuthorTotalView;
    private TextView mMyFollowerTotalView;
    private TextView mMyFavoriteBookView;

    private KinnerMainMePresenter mPresenter;

    public KinnerMainMeFragment() {

    }

    public static KinnerMainMeFragment newInstance() {
        return new KinnerMainMeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new KinnerMainMePresenter(DataSourceManager.provideUserRepository(getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_main_me, container, false);

        LinearLayout myInfoContainer = view.findViewById(R.id.my_info_container);
        myInfoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyBlogActivity.actionStart(getActivity());
            }
        });

        mMyAvatarView = view.findViewById(R.id.my_avatar_view);
        mMyNameView = view.findViewById(R.id.my_name_view);
        mMyMottoView = view.findViewById(R.id.my_motto_view);
        mMyFollowAuthorTotalView = view.findViewById(R.id.my_follow_author_total_view);
        mMyFollowerTotalView = view.findViewById(R.id.my_follower_total_view);
        mMyFavoriteBookView = view.findViewById(R.id.my_favorite_book_total_view);

        TextView myBookListView = view.findViewById(R.id.my_book_list_view);
        myBookListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyBookListActivity.actionStart(getActivity());
            }
        });

        TextView myFollowAuthorListView = view.findViewById(R.id.my_follow_author_list_view);
        myFollowAuthorListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFollowAuthorListActivity.actionStart(getActivity());
            }
        });

        TextView myFollowerListView = view.findViewById(R.id.my_follower_list_view);
        myFollowerListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFollowerListActivity.actionStart(getActivity());
            }
        });

        TextView myFavoriteBookListView = view.findViewById(R.id.recycler_view);
        myFavoriteBookListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFavoriteBookListActivity.actionStart(getActivity());
            }
        });

        TextView mySettingListView = view.findViewById(R.id.my_setting_list_view);
        mySettingListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySettingListActivity.actionStart(getActivity());
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        User myself = BmobUser.getCurrentUser(User.class);
        if (!myself.getAuthorize() && null != myself.getProfile() && !myself.getProfile().equals("")
                && null != myself.getAvatar() && null != myself.getPortrait()) {
            mPresenter.getAuthorization();
        }

        if (null != myself.getAvatar()) {
            Glide.with(getActivity()).load(myself.getAvatar().getUrl()).into(mMyAvatarView);
        }
        mMyNameView.setText(myself.getName());
        mMyMottoView.setText(myself.getMotto());
        mMyFollowAuthorTotalView.setText(String.valueOf(myself.getFollowTotal()));
        mMyFollowerTotalView.setText(String.valueOf(myself.getFollowerTotal()));
        mMyFavoriteBookView.setText(String.valueOf(myself.getFavoriteTotal()));
    }
}
