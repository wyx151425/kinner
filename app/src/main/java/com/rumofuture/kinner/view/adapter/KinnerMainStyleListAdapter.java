package com.rumofuture.kinner.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rumofuture.kinner.R;
import com.rumofuture.kinner.model.entity.Style;
import com.rumofuture.kinner.view.activity.KinnerAlbumBookListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZhenqi on 2017/4/13.
 */

public class KinnerMainStyleListAdapter extends RecyclerView.Adapter<KinnerMainStyleListAdapter.AlbumViewHolder> {

    private List<Style> mStyleList;
    private List<Style> mServerStyleList;
    private Context mContext;

    public KinnerMainStyleListAdapter(List<Style> serverStyleList) {
        mServerStyleList = serverStyleList;
        mStyleList = new ArrayList<>();
        mStyleList.add(new Style(R.drawable.classical, "古典", "Classical Style"));
        mStyleList.add(new Style(R.drawable.burningblood, "热血", "Burning Blood Style"));
        mStyleList.add(new Style(R.drawable.aesthetic, "唯美", "Aesthetic Style"));
        mStyleList.add(new Style(R.drawable.purefresh, "清新", "Pure & Fresh Style"));
        mStyleList.add(new Style(R.drawable.inference, "推理", "Inference Style"));
        mStyleList.add(new Style(R.drawable.hilarious, "爆笑", "Hilarious Style"));
        mStyleList.add(new Style(R.drawable.cliffhang, "悬疑", "Cliffhang Style"));
        mStyleList.add(new Style(R.drawable.lovely, "萌系", "Lovely Style"));
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (null == mContext) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_nemo_main_album_list, parent, false);
        final AlbumViewHolder holder = new AlbumViewHolder(view);
        holder.mAlbumGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KinnerAlbumBookListActivity.actionStart(mContext, mStyleList.get(holder.getAdapterPosition()));
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        holder.mAlbumCoverView.setImageResource(mStyleList.get(position).getImageId());
        holder.mAlbumStyleView.setText(mStyleList.get(position).getStyle());
        holder.mAlbumNoteView.setText(mStyleList.get(position).getNote());
        holder.mAlbumBookTotalView.setText(String.valueOf(mServerStyleList.get(position).getAlbumTotal()));
    }

    @Override
    public int getItemCount() {
        return mStyleList.size();
    }

    static class AlbumViewHolder extends RecyclerView.ViewHolder {

        ImageView mAlbumCoverView;
        TextView mAlbumNoteView;
        TextView mAlbumStyleView;
        TextView mAlbumBookTotalView;
        Button mAlbumGoButton;

        AlbumViewHolder(View itemView) {
            super(itemView);

            mAlbumCoverView = (ImageView) itemView.findViewById(R.id.album_cover_view);
            mAlbumStyleView = (TextView) itemView.findViewById(R.id.album_style_view);
            mAlbumNoteView = (TextView) itemView.findViewById(R.id.album_note_view);
            mAlbumBookTotalView = (TextView) itemView.findViewById(R.id.album_book_total_view);
            mAlbumGoButton = (Button) itemView.findViewById(R.id.album_go_button);
        }
    }
}
