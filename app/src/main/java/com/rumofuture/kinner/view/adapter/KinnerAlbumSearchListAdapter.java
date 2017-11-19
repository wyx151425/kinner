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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by WangZhenqi on 2017/9/19.
 */

public class KinnerAlbumSearchListAdapter extends RecyclerView.Adapter<KinnerAlbumSearchListAdapter.ItemViewHolder> {

    private Context mContext;
    private List<Album> mAlbumList;

    public KinnerAlbumSearchListAdapter(List<Album> albumList) {
        mAlbumList = albumList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (null == mContext) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_nemo_main_book_list, parent, false);
        final KinnerAlbumSearchListAdapter.ItemViewHolder holder = new KinnerAlbumSearchListAdapter.ItemViewHolder(view);

        holder.mBookInfoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KinnerBookInfoActivity.actionStart(mContext, mAlbumList.get(holder.getAdapterPosition()));
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(KinnerAlbumSearchListAdapter.ItemViewHolder holder, int position) {
        Album album = mAlbumList.get(position);

        Glide.with(mContext).load(album.getAuthor().getAvatar().getUrl()).into(holder.mAuthorAvatarView);
        holder.mAuthorNameView.setText(album.getAuthor().getName());

        Glide.with(mContext).load(album.getCover().getUrl()).into(holder.mBookCoverView);
        holder.mBookNameView.setText(album.getName());
        holder.mBookStyleView.setText(album.getStyle());
        holder.mBookIntroductionView.setText(album.getIntroduction());
        holder.mBookFavorTotalView.setText(String.valueOf(album.getFavorTotal()));
    }

    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mBookInfoContainer;

        CircleImageView mAuthorAvatarView;
        TextView mAuthorNameView;

        ImageView mBookCoverView;
        TextView mBookNameView;
        TextView mBookStyleView;
        TextView mBookIntroductionView;
        TextView mBookFavorTotalView;

        ItemViewHolder(View itemView) {
            super(itemView);

            mBookInfoContainer = (LinearLayout) itemView.findViewById(R.id.book_info_container);

            mAuthorAvatarView = (CircleImageView) itemView.findViewById(R.id.author_avatar_view);
            mAuthorNameView = (TextView) itemView.findViewById(R.id.author_name_view);

            mBookCoverView = (ImageView) itemView.findViewById(R.id.book_cover_view);
            mBookNameView = (TextView) itemView.findViewById(R.id.book_name_view);
            mBookStyleView = (TextView) itemView.findViewById(R.id.book_style_view);
            mBookIntroductionView = (TextView) itemView.findViewById(R.id.book_introduction_view);
            mBookFavorTotalView = (TextView) itemView.findViewById(R.id.book_favor_total_view);
        }
    }
}
