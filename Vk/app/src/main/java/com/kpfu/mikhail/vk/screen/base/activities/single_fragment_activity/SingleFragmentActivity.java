package com.kpfu.mikhail.vk.screen.base.activities.single_fragment_activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kpfu.mikhail.vk.R;
import com.kpfu.mikhail.vk.screen.base.activities.base_fragment_activity.BaseFragmentActivity;
import com.kpfu.mikhail.vk.utils.AndroidUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class SingleFragmentActivity extends BaseFragmentActivity
        implements SingleActivityWithFragmentView {

    @BindView(R.id.toolbar) Toolbar mToolbar;

    @BindView(R.id.appBarLayout) AppBarLayout mAppBarLayout;

    @BindView(R.id.btn_reload) Button mBtnReload;

    @BindView(R.id.main_child) CoordinatorLayout mContentLayout;

    @BindView(R.id.fragment_layout) FrameLayout mFragmentLayout;

    @BindView(R.id.tv_network_error_message) TextView mNetworkErrorTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
        ButterKnife.bind(this);
        installNetworkErrorScreen();
        doCreatingActions();
    }

    private void installNetworkErrorScreen() {
        mContentLayout.addView(getNetworkErrorScreen(), 0);
    }

    protected void doCreatingActions() {
    }

    protected FrameLayout getFragmentLayout() {
        return mFragmentLayout;
    }

    protected Toolbar getToolbar() {
        return mToolbar;
    }

    protected AppBarLayout getAppBarLayout() {
        return mAppBarLayout;
    }

    protected void setToolbarBackArrow() {
        /*View backArrowView = getLayoutInflater().inflate(R.layout.toolbar_back_arrow_upgrade, null);
//        ToolbarUtils.setToolbarBackArrow(toolbarUpgrade, getToolbar(), this::finish);
        backArrowView.setOnClickListener(v -> this.finish());
        ViewGroup mainToolbarView = (ViewGroup) mToolbar.findViewById(R.id.main_toolbar_layout);
        mainToolbarView.addView(backArrowView);
        ToolbarUtils.configToolbarUpgradeView(backArrowView);*/
        AndroidUtils.checkNotNull(getSupportActionBar());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

}
