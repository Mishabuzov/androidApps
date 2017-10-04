package com.kpfu.mikhail.vk.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import static com.kpfu.mikhail.vk.utils.Constants.COUNT_PER_PAGE;

public class EndlessRecyclerScrollListener extends RecyclerView.OnScrollListener {
    /**
     * The minimum amount of items to have below your current scroll position before loading more.
     */
    private static final int VISIBLE_THRESHOLD = 1;

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
                if (mPreviousTotal - totalItemCount > COUNT_PER_PAGE) {
                    mPreviousTotal = totalItemCount;
                }
                if (totalItemCount - 1 > mPreviousTotal) {
                    mLoading = false;
                    mPreviousTotal = totalItemCount;
                }
            }
            if (!mLoading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
                mPaginationLoadable.onLoadMore();
                mLoading = true;
            }
        }
    }

    public void dateIsOver() {
        isDataOver = false;
    }

   /* public void setLastElementId(long lastElementId) {
        mLastElementId = lastElementId;
    }*/

    public interface PaginationLoadable {
        void onLoadMore();
    }
}