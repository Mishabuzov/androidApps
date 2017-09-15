package com.fsep.sova.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.fsep.sova.R;
import com.fsep.sova.activities.base.BaseActivity;
import com.fsep.sova.adapters.MenuAdapter;
import com.fsep.sova.fragments.NotesFragment;
import com.fsep.sova.fragments.ProfileFragment;
import com.fsep.sova.models.TaskListType;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NotesActivity extends BaseActivity implements MenuAdapter.Callback, ProfileFragment.MenuRefresher {
    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.menu_recycler) RecyclerView mMenuRecycler;
    private android.support.v7.app.ActionBarDrawerToggle mActionBarDrawerToggle;
    private MenuAdapter mMenuAdapter = new MenuAdapter(this, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        ButterKnife.bind(this);
        setupWindow(R.color.transparent_black_color_for_top_window_bar);
        setupMenuRecycler();
        setTaskFragment(TaskListType.FEED);
        mToolbar.setBackgroundResource(R.color.toolbar_color);
        setupActionBar();
        mActionBarDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
    }

    private void setTaskFragment(TaskListType listType) {
        NotesFragment notesFragment = new NotesFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(NotesFragment.LIST_TYPE, listType);
        notesFragment.setArguments(bundle);
        setFragment(notesFragment);
    }

    private void openSearchActivity(){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    private void setupMenuRecycler() {
        mMenuRecycler.setLayoutManager(new LinearLayoutManager(mMenuRecycler.getContext()));
        mMenuRecycler.setAdapter(mMenuAdapter);
    }

    private void setupActionBar(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mActionBarDrawerToggle != null)
            mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onItemSelected(ImageView menuIv, TaskListType listType) {
        switch (listType) {
            case FEED:
            case MY_TASKS:
            case MY_EVENTS:
            case FAVOURITES:
                setTaskFragment(listType);
                break;
            case SEARCH:
                openSearchActivity();
                break;
            case AVATAR:
                setFragment(new ProfileFragment());
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }


    @Override
    public void refreshNavigationView() {
        mMenuAdapter.notifyItemChanged(0);
    }
}
