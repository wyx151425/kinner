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
import com.rumofuture.kinner.model.entity.User;
import com.rumofuture.kinner.view.activity.KinnerAuthorBookInfoActivity;

import java.util.List;

/**
 * Created by WangZhenqi on 2017/4/18.
 */

public class KinnerAuthorBlogListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_AUTHOR = 0;
    private static final int TYPE_BOOK = 1;

    private Context mContext;
    private User mAuthor;
    private List<Album> mAlbumList;

    public KinnerAuthorBlogListAdapter(User author, List<Album> albumList) {
        mAuthor = author;
        mAlbumList = albumList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (null == mContext) {
            mContext = parent.getContext();
        }

        if (TYPE_AUTHOR == viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_nemo_author_info, parent, false);
            return new AuthorViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_nemo_author_book_list, parent, false);
            final BookViewHolder holder = new BookViewHolder(view);

            holder.mBookInfoContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Album album = mAlbumList.get(holder.getAdapterPosition() - 1);
                    album.setAuthor(mAuthor);
                    KinnerAuthorBookInfoActivity.actionStart(mContext, album);
                }
            });

            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (0 == position) {
            AuthorViewHolder authorViewHolder = (AuthorViewHolder) holder;
            authorViewHolder.mAuthorProfileView.setText(mAuthor.getProfile());
        } else {
            Album album = mAlbumList.get(position - 1);
            BookViewHolder bookViewHolder = (BookViewHolder) holder;
            Glide.with(mContext).load(album.getCover().getUrl()).into(bookViewHolder.mBookCoverView);
            bookViewHolder.mBookNameView.setText(album.getName());
            bookViewHolder.mBookStyleView.setText(album.getStyle());
            bookViewHolder.mBookIntroductionView.setText(album.getIntroduction());
            bookViewHolder.mBookFavorTotalView.setText(String.valueOf(album.getFavorTotal()));
        }
    }

    @Override
    public int getItemCount() {
        return mAlbumList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (0 == position) {
            return TYPE_AUTHOR;
        } else {
            return TYPE_BOOK;
        }
    }

    private static class AuthorViewHolder extends RecyclerView.ViewHolder {
        TextView mAuthorProfileView;
        AuthorViewHolder(View itemView) {
            super(itemView);
            mAuthorProfileView = (TextView) itemView.findViewById(R.id.author_profile_view);
        }
    }

    private static class BookViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mBookInfoContainer;
        ImageView mBookCoverView;
        TextView mBookNameView;
        TextView mBookStyleView;
        TextView mBookIntroductionView;
        TextView mBookFavorTotalView;

        BookViewHolder(View itemView) {
            super(itemView);

            mBookInfoContainer = (LinearLayout) itemView;
            mBookCoverView = (ImageView) itemView.findViewById(R.id.book_cover_view);
            mBookNameView = (TextView) itemView.findViewById(R.id.book_name_view);
            mBookStyleView = (TextView) itemView.findViewById(R.id.book_style_view);
            mBookIntroductionView = (TextView) itemView.findViewById(R.id.book_introduction_view);
            mBookFavorTotalView = (TextView) itemView.findViewById(R.id.book_favor_total_view);
        }
    }
}
