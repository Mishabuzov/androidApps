package com.kpfu.mikhail.vk.screen.base;

import android.support.annotation.CallSuper;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kpfu.mikhail.vk.R;
import com.kpfu.mikhail.vk.utils.Function;
import com.kpfu.mikhail.vk.utils.logger.Logger;
import com.kpfu.mikhail.vk.widget.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<ViewHolder>
        implements NetworkErrorViewHolder.NetworkHolderCallback {

    private final List<T> mItems = new ArrayList<>();

    private AdapterCallback mReloadCallback;

    @Nullable
    private OnItemClickListener<T> mOnItemClickListener;

    private final View.OnClickListener mInternalListener = (view) -> {
        if (mOnItemClickListener != null) {
            int position = (int) view.getTag();
            T item = mItems.get(position);
            mOnItemClickListener.onItemClick(item);
        }
    };

    private EmptyRecyclerView mRecyclerView;

    public BaseAdapter(@NonNull List<T> items) {
        mItems.addAll(items);
    }

    public BaseAdapter(@NonNull List<T> items,
                       @NonNull AdapterCallback callback) {
        mItems.addAll(items);
        mReloadCallback = callback;
    }

    public void attachToRecyclerView(@NonNull EmptyRecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.setAdapter(this);
        refreshRecycler();
    }

    public final void add(@NonNull T value) {
        mItems.add(value);
        refreshRecycler();
    }

    public final void addAll(@NonNull List<T> values) {
        /*int positionStart = 0;
        if (!mItems.isEmpty()) {
            positionStart = mItems.size() - 1;
        }*/
        mItems.addAll(values);
//        notifyItemRangeInserted(positionStart, values.size());
        refreshRecycler();
    }

    public final void changeDataSet(@NonNull List<T> values) {
        clearData();
        mItems.addAll(values);
        refreshRecycler();
    }

    public void clearData() {
        mItems.clear();
    }

    public final void clear() {
        clearData();
        refreshRecycler();
    }

    public void refreshRecycler() {
        notifyDataSetChanged();
        if (mRecyclerView != null) {
            mRecyclerView.checkIfEmpty();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_FOOTER_PAGINATION_PROGRESS:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.footer_progress_bar, parent, false);
                return new PaginationViewHolder(view);
            case TYPE_NETWORK_ERROR_VIEW:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_network_error, parent, false);
                return new NetworkErrorViewHolder(view, mReloadCallback.getReloadFunction(), this);
            default:
                return onCreateDefaultViewHolder(parent, viewType);
        }
    }

    protected abstract ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int viewType);

    @CallSuper
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(mInternalListener);
        if (holder.getItemViewType() == TYPE_NETWORK_ERROR_VIEW) {
            NetworkErrorViewHolder h = ((NetworkErrorViewHolder) holder);
            h.bind();
        }
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener<T> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    public T getItem(int position) {
        return mItems.get(position);
    }

    public List<T> getItems() {
        return mItems;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public interface OnItemClickListener<T> {

        void onItemClick(@NonNull T item);

    }

    protected boolean mIsPaginationInProgress;

    protected boolean mIsNetworkError;

//    protected boolean mIsSwipeRefreshing;

    protected static final int TYPE_ITEM_VIEW = 0;

    protected static final int TYPE_FOOTER_PAGINATION_PROGRESS = 1;

    protected static final int TYPE_NETWORK_ERROR_VIEW = 2;

    /*public void setSwipeRefreshing(boolean isEnable){
        mIsSwipeRefreshing = isEnable;
        if(!isEnable){
            mReloadCallback.hideSwipeRefresh();
        }
    }*/

    @MainThread
    public void showNetworkErrorView(boolean isEnable) {
        if (mIsNetworkError == isEnable) {
            return;
        }/* else if(mIsSwipeRefreshing){
            setSwipeRefreshing(false);
            return;
        }*/
        if (mIsPaginationInProgress) {
            enablePaginationView(false);
        }
        mIsNetworkError = isEnable;
        configExtraItem(isEnable);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1 && mIsPaginationInProgress) {
            return TYPE_FOOTER_PAGINATION_PROGRESS;
        } else if (position == getItemCount() - 1 && mIsNetworkError) {
            return TYPE_NETWORK_ERROR_VIEW;
        }
        return TYPE_ITEM_VIEW;
    }

    private void configExtraItem(boolean isEnable) {
        if (isEnable) {
            notifyItemInserted(getItemCount());
        } else {
            notifyItemRemoved(getItemCount());
        }
    }

    public void enablePaginationView(boolean isEnable) {
       /* if(mIsSwipeRefreshing){
            setSwipeRefreshing(false);
        }*/
        if (mIsPaginationInProgress == isEnable) {
            return;
        }
        if (mIsNetworkError) {
            showNetworkErrorView(false);
        }
        mIsPaginationInProgress = isEnable;
        Logger.d("Layout manager items count: " + mRecyclerView.getLayoutManager().getItemCount());
        Logger.d("Adapter items count: " + mRecyclerView.getLayoutManager().getItemCount());
        configExtraItem(isEnable);
    }

    private class PaginationViewHolder extends ViewHolder {
        private PaginationViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface AdapterCallback {
        Function getReloadFunction();
//        void hideSwipeRefresh();
    }

}
