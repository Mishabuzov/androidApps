package com.fsep.sova.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fsep.sova.Config;

public class EndlessRecyclerScrollListener extends RecyclerView.OnScrollListener {
    /**
     * The minimum amount of items to have below your current scroll position before loading more.
     */
    private static final int VISIBLE_THRESHOLD = 1;
    private static final int OFFSET = 20;

    /**
     * The total number of items in the dataset after the last load
     */
    private int mPreviousTotal = 0;
    /**
     * True if we are still waiting for the last set of data to load.
     */
    private boolean mLoading = true;

    /**
     * True if data is over on server
     */
    private boolean isDataOver;
    private long mLastElementId;

    private LinearLayoutManager mLinearLayoutManager;
    private PaginationLoadable mPaginationLoadable;

    public EndlessRecyclerScrollListener(PaginationLoadable paginationLoadable, LinearLayoutManager linearLayoutManager) {
        this.mPaginationLoadable = paginationLoadable;
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (!isDataOver) {
            int visibleItemCount = recyclerView.getChildCount();
            int totalItemCount = mLinearLayoutManager.getItemCount();
            int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

            if (mLoading) {
                //Attention! Kostyl!
                if(mPreviousTotal - totalItemCount > Config.COUNT_PER_PAGE) {
                    mPreviousTotal = totalItemCount;
                }
                if (totalItemCount - 1 > mPreviousTotal) {
                    mLoading = false;
                    mPreviousTotal = totalItemCount;
                }
            }
            if (!mLoading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
                mPaginationLoadable.onLoadMore(mLastElementId, Config.COUNT_PER_PAGE);
                mLoading = true;
            }
        }
    }

    public void dateIsOver() {
        isDataOver = false;
    }

    public void setLastElementId(long lastElementId) {
        mLastElementId = lastElementId;
    }

    public interface PaginationLoadable {
        void onLoadMore(long lastElementId, int offset);
    }
}