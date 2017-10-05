package com.kpfu.mikhail.vk.screen.base.activities.navigation_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.kpfu.mikhail.vk.R;
import com.kpfu.mikhail.vk.content.MenuItem;
import com.kpfu.mikhail.vk.screen.base.activities.base_fragment_activity.BaseFragmentActivity;
import com.kpfu.mikhail.vk.screen.base.activities.navigation_activity.MenuAdapter.ClickCallback;
import com.kpfu.mikhail.vk.screen.login.LoginActivity;
import com.kpfu.mikhail.vk.utils.AndroidUtils;
import com.kpfu.mikhail.vk.widget.EmptyRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class NavigationActivity extends BaseFragmentActivity
        implements ClickCallback, NavigationView {

    @BindView(R.id.toolbar) Toolbar mToolbar;

    @BindView(R.id.appBarLayout) AppBarLayout mAppBarLayout;

    @BindView(R.id.fragment_layout) FrameLayout mFragmentLayout;

    @BindView(R.id.main_child) CoordinatorLayout mContentLayout;

    @BindView(R.id.menu_recycler) EmptyRecyclerView mMenuRecycler;

    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;

    private MenuAdapter mMenuAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        initNavigationElements();
        doCreatingActions();
    }

    private void initNavigationElements() {
        installNetworkErrorScreen();
        setupMenuAdapter();
    }

    private void installNetworkErrorScreen() {
        RelativeLayout networkErrorLayout = getNetworkErrorScreen();
        networkErrorLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT));
        mContentLayout.addView(getNetworkErrorScreen(), 0);
    }

    @Override
    public void onMenuItemChosen(MenuItem item) {
        switch (item) {
            case LOG_OUT:
                AndroidUtils.logOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
        }
    }

    private void setupMenuAdapter() {
        mMenuAdapter = new MenuAdapter(MenuItem.getmMenuItems(), this);
        mMenuAdapter.attachToRecyclerView(mMenuRecycler);
        mMenuRecycler.setLayoutManager(new LinearLayoutManager(mMenuRecycler.getContext()));
    }

    protected void doCreatingActions() {
    }

    @Override
    public void openNavigationMenu() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void closeNavigationMenu() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected AppBarLayout getAppBarLayout() {
        return mAppBarLayout;
    }

    @Override
    protected FrameLayout getFragmentLayout() {
        return mFragmentLayout;
    }
}
