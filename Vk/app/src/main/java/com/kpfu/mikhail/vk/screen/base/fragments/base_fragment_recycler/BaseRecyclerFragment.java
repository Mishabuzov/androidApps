package com.kpfu.mikhail.vk.screen.base.fragments.base_fragment_recycler;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kpfu.mikhail.vk.R;
import com.kpfu.mikhail.vk.screen.base.BaseAdapter;
import com.kpfu.mikhail.vk.screen.base.BasePresenter;
import com.kpfu.mikhail.vk.screen.base.activities.base_fragment_activity.BaseFragmentActivity;
import com.kpfu.mikhail.vk.screen.base.fragments.base_fragment.BaseFragment;
import com.kpfu.mikhail.vk.utils.Function;
import com.kpfu.mikhail.vk.widget.EmptyRecyclerView;
import com.kpfu.mikhail.vk.widget.EndlessRecyclerScrollListener;
import com.kpfu.mikhail.vk.widget.EndlessRecyclerScrollListener.PaginationLoadable;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseRecyclerFragment
        <Adapter extends BaseAdapter,
                Data extends Parcelable,
                V extends BaseRecyclerFragmentView<Data>,
                P extends BasePresenter<V, Data>>

        extends BaseFragment<Data, V, P> implements BaseRecyclerFragmentView<Data>,
        PaginationLoadable, BaseAdapter.FooterReloadCallback {

    @BindView(R.id.main_layout) RelativeLayout mMainLayout;

    @BindView(R.id.recyclerView) EmptyRecyclerView mRecyclerView;

    @BindView(R.id.empty) TextView mEmpty;

    @BindView(R.id.empty_layout) RelativeLayout mEmptyLayout;

    @BindView(R.id.btn_reload) Button mReloadButton;

    private LinearLayoutManager mLayoutManager;

    private Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_recycler, container, false);
        ButterKnife.bind(this, view);
        getArgs();
        setupRecyclerView();
        getDataAndShow();
        doActions();
        return view;
    }

    @Override
    public void configToolbarBehavior(int recyclerItemsSize) {
        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ((BaseFragmentActivity) getActivity()).setToolbarBehavior(mLayoutManager, recyclerItemsSize);
            }
        });
    }

    protected abstract void getArgs();

    protected void doActions() {
    }

    protected abstract Adapter initAdapter();

    private void setupRecyclerView() {
        mAdapter = initAdapter();
        mAdapter.attachToRecyclerView(mRecyclerView);
        mLayoutManager = new LinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setEmptyView(mEmpty);
        mRecyclerView.setBackgroundResource(R.color.vk_white);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }

//    public EmptyRecyclerView getRecyclerView() {
//        return mRecyclerView;
//    }

    @Override
    public void showScreenAndHideLoading() {
        showScreen();
        hideLoading();
    }

    @Override
    public void hideScreenAndShowLoading() {
        hideScreen();
        showLoading();
    }

    @Override
    public void showScreen() {
        mMainLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideScreen() {
        mMainLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showEmptyView() {
        mReloadButton.setOnClickListener((v) -> getPresenter().connectData());
        mEmptyLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        if (mEmptyLayout.getVisibility() == View.VISIBLE) {
            mEmptyLayout.setVisibility(View.GONE);
        }
    }

    protected void enablePagination(@NonNull PaginationLoadable loadable) {
        mRecyclerView.addOnScrollListener(new EndlessRecyclerScrollListener(loadable,
                (LinearLayoutManager) mRecyclerView.getLayoutManager()));
    }

    @Override
    public void onLoadMore() {
        getPresenter().connectData();
    }

    @Override
    public Function getFooterReloadFunction() {
        return getPresenter()::connectData;
    }

    public void showReloadFooterInterface() {
        mAdapter.showReloadFooterInterface();
    }

}