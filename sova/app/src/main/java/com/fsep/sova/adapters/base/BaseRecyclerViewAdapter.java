package com.fsep.sova.adapters.base;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import com.fsep.sova.R;
import com.fsep.sova.utils.logger.Logger;

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected static final int TYPE_ITEM_VIEW = 0;
    protected static final int TYPE_EMPTY_VIEW = 1;
    protected static final int TYPE_NETWORK_ERROR_VIEW = 2;
    protected static final int TYPE_PROGRESS_VIEW = 3;
    protected static final int TYPE_FOOTER_PAGINATION_PROGRESS = 4;

    protected boolean isDataEmpty;
    protected boolean isNetworkError;
    protected boolean isProgressView;
    protected boolean isPaginationInProgress;

    protected RecyclerView recyclerView;
    protected LoadData loadData;

    @NonNull
    public abstract List getData();

    public BaseRecyclerViewAdapter(@Nullable LoadData loadData) {
        this.loadData = loadData;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        setSpanSizeLookupIfNeeded();
    }

    private void setSpanSizeLookupIfNeeded() {
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position == 0) {
                        return ((GridLayoutManager) layoutManager).getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (isDataEmpty || isNetworkError || isProgressView)
            return 1;
        return getDefaultItemsCount() +
                (isPaginationInProgress ? 1 : 0);
    }

    protected int getDefaultItemsCount() {
        return getData().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isDataEmpty)
            return TYPE_EMPTY_VIEW;
        else if (isNetworkError) {
            return TYPE_NETWORK_ERROR_VIEW;
        } else if (isProgressView)
            return TYPE_PROGRESS_VIEW;
        else if (position == getItemCount() - 1 && isPaginationInProgress)
            return TYPE_FOOTER_PAGINATION_PROGRESS;

        return TYPE_ITEM_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_EMPTY_VIEW:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_empty_item, parent, false);
                view.setLayoutParams(new RecyclerView.LayoutParams(
                        recyclerView.getWidth(), recyclerView.getHeight()));
                return new EmptyViewHolder(view);
            case TYPE_NETWORK_ERROR_VIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.network_error_item, parent, false);
                view.setLayoutParams(new RecyclerView.LayoutParams(
                        recyclerView.getWidth(), recyclerView.getHeight()));
                return new NetworkErrorViewHolder(view);
            case TYPE_PROGRESS_VIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_bar, parent, false);
                view.setLayoutParams(new RecyclerView.LayoutParams(
                        recyclerView.getWidth(), recyclerView.getHeight()));
                return new ProgressViewHolder(view);
            case TYPE_FOOTER_PAGINATION_PROGRESS:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_progress_bar, parent, false);
                return new ProgressBarFooterViewHolder(view);
            default:
                return onCreateDefaultViewHolder(parent, viewType);
        }
    }

    protected abstract RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case TYPE_EMPTY_VIEW:
                EmptyViewHolder emptyViewHolder = (EmptyViewHolder) holder;
                emptyViewHolder.textViewNoData.setText(defineTextForEmptyDataMessage());
                break;
            case TYPE_NETWORK_ERROR_VIEW:
                NetworkErrorViewHolder networkErrorViewHolder = (NetworkErrorViewHolder) holder;
                networkErrorViewHolder.btnReload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (loadData != null)
                            loadData.load();
                    }
                });
                networkErrorViewHolder.textViewNetworkError.setText(R.string.content_network_error_message);
                break;
            default:
                onBindDefaultViewHolder(holder, holder.getLayoutPosition());
        }
    }

    @NonNull
    protected abstract String defineTextForEmptyDataMessage();

    protected abstract void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, int position);

    @MainThread
    public void showEmptyDataView() {
        isDataEmpty = true;
        isNetworkError = false;
        isProgressView = false;
        isPaginationInProgress = false;
        notifyDataSetChanged();
    }

    @MainThread
    public void showNetworkErrorView() {
        isDataEmpty = false;
        isNetworkError = true;
        isProgressView = false;
        isPaginationInProgress = false;
        notifyDataSetChanged();
    }

    @MainThread
    public void removeStateViews() {
        if (isDataEmpty || isNetworkError || isProgressView) {
            isDataEmpty = false;
            isNetworkError = false;
            isProgressView = false;
            isPaginationInProgress = false;
            notifyDataSetChanged();
        }
    }

    @MainThread
    public void showProgressView() {
        isProgressView = true;
        isDataEmpty = false;
        isNetworkError = false;
        isPaginationInProgress = false;
        notifyDataSetChanged();
    }

    @MainThread
    public void hideProgressView() {
        if (isProgressView) {
            isProgressView = false;
            notifyDataSetChanged();
        }
    }

    public void enablePaginationView(boolean enable) {
        if (isPaginationInProgress == enable) {
            return;
        }
        isPaginationInProgress = enable;
        Logger.d("Layout manager items count: " + recyclerView.getLayoutManager().getItemCount());
        Logger.d("Adapter items count: " + recyclerView.getLayoutManager().getItemCount());
        if (enable) {
            notifyItemInserted(getItemCount());
        } else {
            notifyItemRemoved(getItemCount());
        }
    }

    protected boolean isProgressShowing() {
        return isProgressView;
    }

    protected void onNewDataAppeared() {
        showProgressView();
        isDataEmpty = getData().size() == 0;
        isNetworkError = false;
        isProgressView = false;
        isPaginationInProgress = false;
        notifyDataSetChanged();
    }


    protected class EmptyViewHolder extends RecyclerView.ViewHolder {
        public final TextView textViewNoData;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            textViewNoData = (TextView) itemView.findViewById(R.id.tv_empty_list_message);
        }
    }

    protected class NetworkErrorViewHolder extends RecyclerView.ViewHolder {
        public final TextView textViewNetworkError;
        public final Button btnReload;

        public NetworkErrorViewHolder(View itemView) {
            super(itemView);
            textViewNetworkError = (TextView) itemView.findViewById(R.id.tv_network_error_message);
            btnReload = (Button) itemView.findViewById(R.id.btn_reload);
        }
    }

    protected class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }

    protected class ProgressBarFooterViewHolder extends RecyclerView.ViewHolder {
        public ProgressBarFooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface LoadData {
        void load();
    }
}


