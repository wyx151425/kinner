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
import com.rumofuture.kinner.model.entity.Album;
import com.rumofuture.kinner.view.activity.KinnerBookInfoActivity;

import java.util.List;

/**
 * Created by WangZhenqi on 2017/4/24.
 */

public class MyFavoriteAlbumListAdapter extends RecyclerView.Adapter<MyFavoriteAlbumListAdapter.ItemViewHolder> {

    private List<Album> mAlbumList;
    private Context mContext;

    public MyFavoriteAlbumListAdapter(List<Album> albumList) {
        mAlbumList = albumList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_favorite_book_list, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view);

        holder.mBookInfoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KinnerBookInfoActivity.actionStart(mContext, mAlbumList.get(holder.getAdapterPosition()));
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Album album = mAlbumList.get(position);

        Glide.with(mContext).load(album.getCover().getUrl()).into(holder.mBookCoverView);
        holder.mBookNameView.setText(album.getName());
        holder.mBookIntroductionView.setText(album.getIntroduction());
        holder.mBookStyleView.setText(album.getStyle());
        holder.mBookCollectorCountView.setText(String.valueOf(album.getFavorTotal()));
    }

    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mBookInfoContainer;
        ImageView mBookCoverView;
        TextView mBookNameView;
        TextView mBookIntroductionView;
        TextView mBookStyleView;
        TextView mBookCollectorCountView;

        ItemViewHolder(View itemView) {
            super(itemView);

            mBookInfoContainer = (LinearLayout) itemView;
            mBookCoverView = (ImageView) itemView.findViewById(R.id.book_cover_view);
            mBookNameView = (TextView) itemView.findViewById(R.id.book_name_view);
            mBookIntroductionView = (TextView) itemView.findViewById(R.id.book_introduction_view);
            mBookStyleView = (TextView) itemView.findViewById(R.id.book_style_view);
            mBookCollectorCountView = (TextView) itemView.findViewById(R.id.book_favor_total_view);
        }
    }
}
