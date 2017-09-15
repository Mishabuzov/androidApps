package com.fsep.sova.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.fsep.sova.R;
import com.fsep.sova.activities.base.BaseActivity;
import com.fsep.sova.fragments.PeopleSearchFragment;
import com.fsep.sova.fragments.TagsSearchFragment;
import com.fsep.sova.fragments.TasksSearchFragment;
import com.fsep.sova.fragments.base.BaseSearchFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity {

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.search_pager) ViewPager mSearchPager;
    @Bind(R.id.search_tabs) TabLayout mSearchTabs;
    private TasksSearchFragment tasksFragment;
    private TagsSearchFragment tagsFragment;
    private PeopleSearchFragment peoplesFragment;
    private SearchAdapter mAdapter;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindow(R.color.black_top_bar_transparent_color);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initToolbar();
        initFragments();
        setupViewPager(mSearchPager);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.search_user, menu);
        MenuItem searchItem = menu.findItem(R.id.search);

        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setMaxWidth(Integer.MAX_VALUE);
        setFragmentSearchListener();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFragmentSearchListener() {
        BaseSearchFragment searchFragment = (BaseSearchFragment) mAdapter.getItem(mSearchPager.getCurrentItem());
        if (mSearchView != null) {
            mSearchView.setOnQueryTextListener(searchFragment.getQueryTextListener());
        }
    }

    private void initFragments() {
        tasksFragment = new TasksSearchFragment();
        tagsFragment = new TagsSearchFragment();
        peoplesFragment = new PeopleSearchFragment();
    }

    private void setupViewPager(ViewPager viewPager) {
        mAdapter = new SearchAdapter(getSupportFragmentManager());
        mAdapter.addFragment(tagsFragment, getString(R.string.tab_title_tags));
        mAdapter.addFragment(peoplesFragment, getString(R.string.tab_title_users));
        mAdapter.addFragment(tasksFragment, getString(R.string.tab_title_tasks));
        viewPager.setAdapter(mAdapter);
        mSearchTabs.setupWithViewPager(mSearchPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setFragmentSearchListener();
            }

            public void onPageSelected(int position) {
            }
        });
    }

    static class SearchAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public SearchAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String fragmentTitle) {
            mFragments.add(fragment);
            mFragmentTitles.add(fragmentTitle);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
