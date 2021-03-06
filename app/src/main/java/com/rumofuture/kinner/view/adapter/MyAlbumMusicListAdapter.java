package com.rumofuture.kinner.view.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rumofuture.kinner.R;
import com.rumofuture.kinner.model.entity.Music;
import com.rumofuture.kinner.view.fragment.MyBookPageListFragment;

import java.util.List;

/**
 * Created by WangZhenqi on 2017/4/15.
 */

public class MyAlbumMusicListAdapter extends RecyclerView.Adapter<MyAlbumMusicListAdapter.ItemViewHolder> {

    private Context mContext;
    private List<Music> mMusicList;
    private MyBookPageListFragment mFragment;

    public MyAlbumMusicListAdapter(List<Music> musicList, MyBookPageListFragment fragment) {
        mMusicList = musicList;
        mFragment = fragment;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_book_page_list, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view);

        holder.mPageImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setTitle("选项");
                dialog.setMessage("请选择您要进行的操作");
                dialog.setCancelable(true);
                dialog.setNegativeButton(R.string.prompt_update, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mFragment.actionUpdatePage(mMusicList.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                    }
                });
                dialog.setPositiveButton(R.string.prompt_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mFragment.actionDeletePage(mMusicList.get(holder.getAdapterPosition()));
                    }
                });
                dialog.show();
                return false;
            }
        });

        return holder;
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
            mPageImageView = (ImageView) itemView;
        }
    }
}
