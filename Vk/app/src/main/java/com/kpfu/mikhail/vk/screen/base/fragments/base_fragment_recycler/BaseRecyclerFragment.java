package com.kpfu.mikhail.vk.screen.base.fragments.base_fragment_recycler;

import android.os.Bundle;
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
import com.kpfu.mikhail.vk.screen.base.activities.base_fragment_activity.BaseFragmentActivity;
import com.kpfu.mikhail.vk.screen.base.fragments.base_fragment.BaseFragment;
import com.kpfu.mikhail.vk.widget.BaseAdapter;
import com.kpfu.mikhail.vk.widget.EmptyRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseRecyclerFragment<T extends BaseAdapter>
        extends BaseFragment implements BaseRecyclerFragmentView {

    @BindView(R.id.main_layout) RelativeLayout mMainLayout;

    @BindView(R.id.recyclerView) EmptyRecyclerView mRecyclerView;

    @BindView(R.id.empty) TextView mEmpty;

    @BindView(R.id.empty_layout) RelativeLayout mEmptyLayout;

    @BindView(R.id.btn_reload) Button mReloadButton;

    private LinearLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_recycler, container, false);
        ButterKnife.bind(this, view);
        getArgs();
        initFragmentElements();
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

    protected abstract void doActions();

    private void initFragmentElements() {
        setupAdapter();
        setupRecyclerView();
    }

    protected abstract T initAdapter();

    private void setupAdapter() {
        T adapter = initAdapter();
        adapter.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(adapter);
    }

    private void setupRecyclerView() {
        mLayoutManager = new LinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setEmptyView(mEmpty);
        mRecyclerView.setBackgroundResource(R.color.vk_white);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }

    public EmptyRecyclerView getRecyclerView() {
        return mRecyclerView;
    }

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

}