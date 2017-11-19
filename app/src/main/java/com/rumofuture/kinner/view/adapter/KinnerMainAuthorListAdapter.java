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
 * Created by WangZhenqi on 2017/4/13.
 */

public class KinnerMainAuthorListAdapter extends RecyclerView.Adapter<KinnerMainAuthorListAdapter.ItemViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private Context mContext;
    private List<User> mAuthorList;

    public KinnerMainAuthorListAdapter(List<User> authorList) {
        mAuthorList = authorList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (null == mContext) {
            mContext = parent.getContext();
        }

        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.header_nemo_main_author_list, parent, false);
            return new KinnerMainAuthorListAdapter.ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_nemo_main_author_list, parent, false);
            final KinnerMainAuthorListAdapter.ItemViewHolder holder = new KinnerMainAuthorListAdapter.ItemViewHolder(view);

            holder.mAuthorInfoContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    KinnerAuthorBlogActivity.actionStart(mContext, mAuthorList.get(holder.getAdapterPosition() - 1));
                }
            });

            return holder;
        }
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        if (0 != position) {
            User author = mAuthorList.get(position - 1);
            Glide.with(mContext).load(author.getAvatar().getUrl()).into(holder.mAuthorAvatarView);
            holder.mAuthorNameView.setText(author.getName());
            holder.mAuthorMottoView.setText(author.getMotto());
            holder.mAuthorBookTotalView.setText(String.valueOf(author.getAlbumTotal()));
            holder.mAuthorFollowerTotalView.setText(String.valueOf(author.getFollowerTotal()));
        }
    }

    @Override
    public int getItemCount() {
        return mAuthorList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (0 == position) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
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
            mAuthorAvatarView = (ImageView) itemView.findViewById(R.id.author_avatar_view);
            mAuthorNameView = (TextView) itemView.findViewById(R.id.author_name_view);
            mAuthorMottoView = (TextView) itemView.findViewById(R.id.author_motto_view);
            mAuthorBookTotalView = (TextView) itemView.findViewById(R.id.author_book_total_view);
            mAuthorFollowerTotalView = (TextView) itemView.findViewById(R.id.author_follower_total_view);
        }
    }
}
