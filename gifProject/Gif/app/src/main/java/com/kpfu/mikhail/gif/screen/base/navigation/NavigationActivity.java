package com.kpfu.mikhail.gif.screen.base.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.kpfu.mikhail.gif.R;
import com.kpfu.mikhail.gif.content.MenuItem;
import com.kpfu.mikhail.gif.screen.auth.AuthActivity;
import com.kpfu.mikhail.gif.screen.base.feed.BaseRecyclerGifFragment;
import com.kpfu.mikhail.gif.screen.favorites.FavoritesFragment;
import com.kpfu.mikhail.gif.screen.feed.FeedFragment;
import com.kpfu.mikhail.gif.utils.PreferenceUtils;
import com.kpfu.mikhail.gif.widget.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kpfu.mikhail.gif.content.MenuItem.FEED;

public class NavigationActivity extends AppCompatActivity implements
        NavigationView,
        MenuAdapter.MenuCallback,
        BaseRecyclerGifFragment.BaseCallback {

    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;

    @BindView(R.id.menu_recycler) EmptyRecyclerView mMenuRecycler;

    private MenuAdapter mMenuAdapter;

    private NavigationPresenter mNavigationPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        initMenuActivityElements();
        mNavigationPresenter.showFeed();
    }

    private void initMenuActivityElements() {
        mNavigationPresenter = new NavigationPresenter(this);
        setupMenuAdapter();
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
    public void showFeed() {
        switchScreen(FEED);
    }

    private void setupMenuAdapter() {
        List<MenuItem> menuItemList = new ArrayList<>();
        if (PreferenceUtils.isCurrentUserAuthorized()) {
            menuItemList.addAll(MenuItem.getItemsForAuthorizedUser());
        } else {
            menuItemList.addAll(MenuItem.getItemsForNotAuthorizedUser());
        }
        mMenuAdapter = new MenuAdapter(menuItemList, this);
        mMenuAdapter.attachToRecyclerView(mMenuRecycler);
        setupMenuRecycler();
    }

    private void setupMenuRecycler() {
        mMenuRecycler.setLayoutManager(new LinearLayoutManager(mMenuRecycler.getContext()));
        mMenuRecycler.setAdapter(mMenuAdapter);
    }

    private void setFragment(@NonNull Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (fm.findFragmentById(R.id.fragment_layout) != null) {
            ft.replace(R.id.fragment_layout, fragment, "fragment");
            ft.commit();
        } else {
            ft.add(R.id.fragment_layout, fragment, "fragment");
            ft.commit();
        }
    }

    @Override
    public void switchScreen(MenuItem item) {
        switch (item) {
            case FEED:
                setFragment(new FeedFragment());
                break;
            case SIGN_IN:
                startActivity(new Intent(this, AuthActivity.class));
                break;
            case FAVORITES:
                setFragment(new FavoritesFragment());
                break;
            case SIGN_OUT:
                PreferenceUtils.clearPreference();
                startActivity(new Intent(this, AuthActivity.class));
                this.finish();
                break;
        }
        mNavigationPresenter.closeNavigation();
    }

    @Override
    @NonNull
    public DrawerLayout getDrawer() {
        return mDrawerLayout;
    }

    @Override
    public void closeDrawer() {
        mNavigationPresenter.closeNavigation();
    }

    @Override
    public void openDrawer() {
        mNavigationPresenter.openNavigation();
    }
}
