package com.rumofuture.kinner.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rumofuture.kinner.R;
import com.rumofuture.kinner.model.entity.Music;

import java.util.List;

/**
 * Created by WangZhenqi on 2017/4/23.
 */

public class KinnerAlbumMusicListAdapter extends RecyclerView.Adapter<KinnerAlbumMusicListAdapter.ItemViewHolder> {

    private Context mContext;
    private List<Music> mMusicList;

    public KinnerAlbumMusicListAdapter(List<Music> musicList) {
        mMusicList = musicList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null)
            mContext = parent.getContext();

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_book_page_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Glide.with(mContext).load(mMusicList.get(position).getMusic().getUrl()).into(holder.mPageImageView);
    }

    @Override
    public int getItemCount() {
        return mMusicList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView mPageImageView;

        ItemViewHolder(View itemView) {
            super(itemView);

            mPageImageView = (ImageView) itemView.findViewById(R.id.page_image);
        }
    }
}
