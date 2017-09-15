package com.fsep.sova.fragments.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fsep.sova.R;
import com.fsep.sova.adapters.base.BaseRecyclerViewAdapter;
import com.fsep.sova.network.events.NetworkErrorEvent;
import com.fsep.sova.utils.EndlessRecyclerScrollListener;
import com.fsep.sova.utils.UiUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

public abstract class BaseRecyclerViewFragment extends BaseLoadableFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindString(R.string.content_error_something_wrong) String mSomethingWrongStringRes;
    @BindString(R.string.content_network_error_message) String mNetworkErrorStringRes;

    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    @Nullable
    @Bind(R.id.btn_floating_action) FloatingActionButton mFab;

    @Nullable
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    protected final int EMPTY_LIST = 0;
    protected final int GET_DATA_ERROR = 1;
    protected final int NETWORK_ERROR = 2;

    protected boolean isProgressBarShowing;
    protected boolean isRefreshingActive;
    protected boolean isPaginationActive;

    @Nullable
    protected abstract BaseRecyclerViewAdapter getAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView(mRecyclerView);
        setupSwipeRefreshLayout(mSwipeRefreshLayout);
    }

    protected void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    }

    protected void setupSwipeRefreshLayout(@Nullable SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setOnRefreshListener(this);
    }

    @NonNull
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public FloatingActionButton getFab() {
        return mFab;
    }

    @Nullable
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    protected void showPaginationBar() {
        isPaginationActive = true;
        getAdapter().enablePaginationView(true);
    }

    protected void cancelPagination() {
        isPaginationActive = false;
        getAdapter().enablePaginationView(false);
    }

    protected void cancelRefreshing() {
        if (isRefreshingActive) {
            isRefreshingActive = false;
            if (mSwipeRefreshLayout != null)
                mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void showProgressBar() {
        getAdapter().showProgressView();
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    protected void hideProgressBar() {
        getAdapter().hideProgressView();
        // TODO: 04.05.16 Setup swipe-to-refresh logic
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setEnabled(true);
    }

    protected void onLoadingError(int type) {
        cancelRefreshing();
        if (loadingDataInProcess) {
            hideProgressBar();
            loadingDataInProcess = false;
            if (getAdapter().getData().size() == 0) {
                if (type == EMPTY_LIST || type == GET_DATA_ERROR)
                    getAdapter().showEmptyDataView();
                else if (type == NETWORK_ERROR) {
                    getAdapter().showNetworkErrorView();
                    return;
                }
            }
        }
        if (type != EMPTY_LIST)
            UiUtils.showToast(type == NETWORK_ERROR ? mNetworkErrorStringRes : mSomethingWrongStringRes);
    }

    protected void resetAllFlags() {
        loadingDataInProcess = false;
        isRefreshingActive = false;
        isProgressBarShowing = false;
        isPaginationActive = false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NetworkErrorEvent event) {
        onLoadingError(NETWORK_ERROR);
    }

    protected void enablePagination(
            @NonNull EndlessRecyclerScrollListener.PaginationLoadable loadable) {
        isPaginationActive = true;
        mRecyclerView.addOnScrollListener(new EndlessRecyclerScrollListener(loadable,
                (LinearLayoutManager) mRecyclerView.getLayoutManager()));
    }
}
