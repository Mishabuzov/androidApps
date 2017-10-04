package com.kpfu.mikhail.vk.widget;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kpfu.mikhail.vk.R;
import com.kpfu.mikhail.vk.utils.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    private final List<T> mItems = new ArrayList<>();

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

    public void attachToRecyclerView(@NonNull EmptyRecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.setAdapter(this);
        refreshRecycler();
    }

    public final void add(@NonNull T value) {
        mItems.add(value);
        refreshRecycler();
    }

    public final void add(@NonNull List<T> values) {
        int positionStart = 0;
        if (!mItems.isEmpty()) {
            positionStart = mItems.size() - 1;
        }
        mItems.addAll(values);
//        notifyItemRangeInserted(positionStart, values.size());
        refreshRecycler();
    }

    public final void changeDataSet(@NonNull List<T> values) {
        mItems.clear();
        mItems.addAll(values);
        refreshRecycler();
    }

    public final void clear() {
        mItems.clear();
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
            case TYPE_PROGRESS_VIEW:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_bar, parent, false);
                view.setLayoutParams(new RecyclerView.LayoutParams(
                        mRecyclerView.getWidth(), mRecyclerView.getHeight()));
                return new ProgressViewHolder(view);
            case TYPE_FOOTER_PAGINATION_PROGRESS:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_progress_bar, parent, false);
                return new ProgressBarFooterViewHolder(view);
            default:
                return onCreateDefaultViewHolder(parent, viewType);
        }
    }

    protected abstract RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int viewType);

    @CallSuper
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(mInternalListener);
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

    public boolean isDataEmpty() {
        return mItems.isEmpty();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public interface OnItemClickListener<T> {

        void onItemClick(@NonNull T item);

    }

    protected boolean isProgressView;

    protected boolean isPaginationInProgress;

    protected static final int TYPE_ITEM_VIEW = 0;

    protected static final int TYPE_PROGRESS_VIEW = 1;

    protected static final int TYPE_FOOTER_PAGINATION_PROGRESS = 2;

    @Override
    public int getItemViewType(int position) {
        if (isProgressView) {
            return TYPE_PROGRESS_VIEW;
        } else if (position == getItemCount() - 1 && isPaginationInProgress) {
            return TYPE_FOOTER_PAGINATION_PROGRESS;
        }
        return TYPE_ITEM_VIEW;
    }

    public void enablePaginationView(boolean enable) {
        if (isPaginationInProgress == enable) {
            return;
        }
        isPaginationInProgress = enable;
        Logger.d("Layout manager items count: " + mRecyclerView.getLayoutManager().getItemCount());
        Logger.d("Adapter items count: " + mRecyclerView.getLayoutManager().getItemCount());
        if (enable) {
            notifyItemInserted(getItemCount());
        } else {
            notifyItemRemoved(getItemCount());
        }
    }

    private class ProgressViewHolder extends ViewHolder {
        private ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class ProgressBarFooterViewHolder extends ViewHolder {
        private ProgressBarFooterViewHolder(View itemView) {
            super(itemView);
        }
    }

  /*  @Override
    public void onViewAttachedToWindow(VH holder) {
        super.onViewAttachedToWindow(holder);
    }*/

}
