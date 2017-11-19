package com.rumofuture.kinner.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rumofuture.kinner.R;
import com.rumofuture.kinner.model.entity.User;

import java.util.List;

/**
 * Created by Administrator on 2017/9/12.
 */

public class MyFollowerListAdapter extends RecyclerView.Adapter<MyFollowerListAdapter.ItemViewHolder> {

    private List<User> mUserList;
    private Context mContext;

    public MyFollowerListAdapter(List<User> userList) {
        mUserList = userList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null)
            mContext = parent.getContext();

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_follow_author_list, parent, false);

        final MyFollowerListAdapter.ItemViewHolder holder = new MyFollowerListAdapter.ItemViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        User user = mUserList.get(position);
        Glide.with(mContext).load(user.getAvatar().getUrl()).into(holder.mUserAvatarView);
        holder.mUserNameView.setText(user.getName());
        holder.mUserMottoView.setText(user.getMotto());
        holder.mUserBookCountView.setText(String.valueOf(user.getAlbumTotal()));
        holder.mUserFollowerCountView.setText(String.valueOf(user.getFollowerTotal()));
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mUserInfoContainer;
        ImageView mUserAvatarView;
        TextView mUserNameView;
        TextView mUserMottoView;
        TextView mUserBookCountView;
        TextView mUserFollowerCountView;

        ItemViewHolder(View itemView) {
            super(itemView);

            mUserInfoContainer = (LinearLayout) itemView;
            mUserAvatarView = (ImageView) itemView.findViewById(R.id.author_avatar_view);
            mUserNameView = (TextView) itemView.findViewById(R.id.author_name_view);
            mUserMottoView = (TextView) itemView.findViewById(R.id.author_motto_view);
            mUserBookCountView = (TextView) itemView.findViewById(R.id.author_book_total_view);
            mUserFollowerCountView = (TextView) itemView.findViewById(R.id.author_follower_total_view);
        }
    }
}
