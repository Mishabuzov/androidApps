package com.kpfu.mikhail.vk.screen.base.activities.base_fragment_activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.LayoutParams;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kpfu.mikhail.vk.R;
import com.kpfu.mikhail.vk.screen.base.activities.base_activity.BaseActivity;
import com.kpfu.mikhail.vk.utils.Function;

import static android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS;
import static android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL;
import static com.kpfu.mikhail.vk.utils.Constants.NONE_SCROLLING;

public abstract class BaseFragmentActivity extends BaseActivity
        implements BaseActivityWithFragmentView {

    private BaseActivityWithFragmentPresenter mFragmentPresenter;

    private RelativeLayout mNetworkErrorLayout;

    private TextView mNetworkErrorTv;

    private Button mButtonReload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentPresenter = new BaseActivityWithFragmentPresenter(this);
        if (savedInstanceState == null) {
            Fragment fragment = getFragment();
            fragment.setArguments(getFragmentArguments());
            setNewInstanceOfFragment(fragment);
        }
        setSupportActionBar(getToolbar());
        initNetworkErrorScreen();
    }

    protected abstract Fragment getFragment();

    protected abstract Bundle getFragmentArguments();

    protected abstract Toolbar getToolbar();

    protected abstract AppBarLayout getAppBarLayout();

    protected abstract FrameLayout getFragmentLayout();

    @Override
    public void setNewInstanceOfFragment(@NonNull Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (fm.findFragmentById(R.id.fragment_layout) != null) {
            ft.replace(R.id.fragment_layout, fragment, FRAGMENT_TAG);
            ft.commit();
        } else {
            ft.add(R.id.fragment_layout, fragment, FRAGMENT_TAG);
            ft.commit();
        }
    }

    @Override
    public void setToolbarBehavior(int scrollHeight,
                                   int scrollPaddingTop,
                                   int scrollPaddingBottom,
                                   int childHeight) {
        mFragmentPresenter.defineToolbarScrollingState(scrollHeight,
                scrollPaddingTop, scrollPaddingBottom, childHeight);
    }

    @Override
    public void setToolbarBehavior(LinearLayoutManager layoutManager, int size) {
        mFragmentPresenter.defineToolbarScrollingState(layoutManager.findLastCompletelyVisibleItemPosition(), size);
    }

    @Override
    public void turnOffToolbarScrolling() {
        //turn off scrolling
        setToolbarScrollingState(NONE_SCROLLING, null);
    }

    @Override
    public void turnOnToolbarScrolling() {
        //turn on scrolling
        setToolbarScrollingState(SCROLL_FLAG_SCROLL | SCROLL_FLAG_ENTER_ALWAYS,
                new AppBarLayout.Behavior());
    }

    private void setToolbarScrollingState(int scrollFlags, AppBarLayout.Behavior behavior) {
        LayoutParams toolbarLayoutParams = (LayoutParams) getToolbar().getLayoutParams();
        toolbarLayoutParams.setScrollFlags(scrollFlags);
        getToolbar().setLayoutParams(toolbarLayoutParams);

        CoordinatorLayout.LayoutParams appBarLayoutParams =
                (CoordinatorLayout.LayoutParams) getAppBarLayout().getLayoutParams();
        appBarLayoutParams.setBehavior(behavior);
        getAppBarLayout().setLayoutParams(appBarLayoutParams);
    }

    @Override
    public void setToolbarTitle(String title) {
        if (title != null && !title.isEmpty()) {
            getToolbar().setTitle(title);
        }
    }

    @Override
    public void setToolbarTitle(@StringRes int title) {
        if (title != 0) {
            getToolbar().setTitle(title);
        }
    }

    private void initNetworkErrorScreen() {
        mNetworkErrorLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.network_error_item, null);
        mNetworkErrorTv = (TextView) mNetworkErrorLayout.findViewById(R.id.tv_network_error_message);
        mButtonReload = (Button) mNetworkErrorLayout.findViewById(R.id.btn_reload);
    }

    protected RelativeLayout getNetworkErrorScreen() {
        return mNetworkErrorLayout;
    }

    @Override
    public void setErrorScreenWithReloadButton(Function reloadFunction,
                                               @StringRes int errorText) {
        mNetworkErrorTv.setText(errorText);
        getFragmentLayout().setVisibility(View.GONE);
        mNetworkErrorLayout.setVisibility(View.VISIBLE);
        mButtonReload.setOnClickListener(v -> {
            reloadFunction.action();
            getFragmentLayout().setVisibility(View.VISIBLE);
            mNetworkErrorLayout.setVisibility(View.GONE);
        });
    }

}
