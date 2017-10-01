package com.kpfu.mikhail.vk.screen.base.fragments.base_fragment_recycler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kpfu.mikhail.vk.R;
import com.kpfu.mikhail.vk.screen.base.activities.base_fragment_activity.BaseFragmentActivity;
import com.kpfu.mikhail.vk.screen.base.fragments.base_fragment.BaseFragment;
import com.kpfu.mikhail.vk.widget.BaseAdapter;
import com.kpfu.mikhail.vk.widget.EmptyRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseRecyclerFragment<T extends BaseAdapter>
        extends BaseFragment implements BaseRecyclerFragmentView {

    @BindView(R.id.main_layout) RelativeLayout mMainLayout;

    @BindView(R.id.recyclerView) EmptyRecyclerView mRecyclerView;

    @BindView(R.id.empty) TextView mEmpty;

    private LinearLayoutManager mLayoutManager;

    private T mAdapter;

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
        mAdapter = initAdapter();
        mAdapter.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setupRecyclerView() {
        mLayoutManager = new LinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setEmptyView(mEmpty);
        mRecyclerView.setBackgroundResource(R.color.vk_white);
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

}