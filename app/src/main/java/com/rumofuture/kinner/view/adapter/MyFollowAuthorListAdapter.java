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
import com.rumofuture.kinner.view.activity.KinnerAuthorBlogActivity;

import java.util.List;

/**
 * Created by WangZhenqi on 2017/4/24.
 */

public class MyFollowAuthorListAdapter extends RecyclerView.Adapter<MyFollowAuthorListAdapter.ItemViewHolder> {

    private List<User> mAuthorList;
    private Context mContext;

    public MyFollowAuthorListAdapter(List<User> authorList) {
        mAuthorList = authorList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null)
            mContext = parent.getContext();

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_follow_author_list, parent, false);

        final ItemViewHolder holder = new ItemViewHolder(view);

        holder.mUserInfoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                KinnerAuthorBlogActivity.actionStart(mContext, mAuthorList.get(position));
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        User author = mAuthorList.get(position);
        Glide.with(mContext).load(author.getAvatar().getUrl()).into(holder.mUserAvatarView);
        holder.mUserNameView.setText(author.getName());
        holder.mUserMottoView.setText(author.getMotto());
        holder.mUserBookCountView.setText(String.valueOf(author.getAlbumTotal()));
        holder.mUserFollowerCountView.setText(String.valueOf(author.getFollowerTotal()));
    }

    @Override
    public int getItemCount() {
        return mAuthorList.size();
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
