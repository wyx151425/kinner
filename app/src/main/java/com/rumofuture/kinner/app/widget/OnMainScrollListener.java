package com.rumofuture.kinner.app.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by WangZhenqi on 2017/9/15.
 */

public abstract class OnMainScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager mLinearLayoutManager;

    protected OnMainScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if ((mLinearLayoutManager.getItemCount() - recyclerView.getChildCount())
                <= mLinearLayoutManager.findFirstVisibleItemPosition()) {
            onLoadMore();
        }
    }

    public abstract void onLoadMore();
}
