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
 * Created by WangZhenqi on 2017/9/19.
 */

public class KinnerAuthorSearchListAdapter extends RecyclerView.Adapter<KinnerAuthorSearchListAdapter.ItemViewHolder> {

    private Context mContext;
    private List<User> mAuthorList;

    public KinnerAuthorSearchListAdapter(List<User> authorList) {
        mAuthorList = authorList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (null == mContext) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_nemo_main_author_list, parent, false);
        final KinnerAuthorSearchListAdapter.ItemViewHolder holder = new KinnerAuthorSearchListAdapter.ItemViewHolder(view);

        holder.mAuthorInfoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KinnerAuthorBlogActivity.actionStart(mContext, mAuthorList.get(holder.getAdapterPosition()));
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        User author = mAuthorList.get(position);
        Glide.with(mContext).load(author.getAvatar().getUrl()).into(holder.mAuthorAvatarView);
        holder.mAuthorNameView.setText(author.getName());
        holder.mAuthorMottoView.setText(author.getMotto());
        holder.mAuthorBookTotalView.setText(String.valueOf(author.getAlbumTotal()));
        holder.mAuthorFollowerTotalView.setText(String.valueOf(author.getFollowerTotal()));
    }

    @Override
    public int getItemCount() {
        return mAuthorList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mAuthorInfoContainer;
        ImageView mAuthorAvatarView;
        TextView mAuthorNameView;
        TextView mAuthorMottoView;
        TextView mAuthorBookTotalView;
        TextView mAuthorFollowerTotalView;

        ItemViewHolder(View itemView) {
            super(itemView);

            mAuthorInfoContainer = (LinearLayout) itemView;
            mAuthorAvatarView = itemView.findViewById(R.id.author_avatar_view);
            mAuthorNameView = itemView.findViewById(R.id.author_name_view);
            mAuthorMottoView = itemView.findViewById(R.id.author_motto_view);
            mAuthorBookTotalView = itemView.findViewById(R.id.author_book_total_view);
            mAuthorFollowerTotalView = itemView.findViewById(R.id.author_follower_total_view);
        }
    }
}
