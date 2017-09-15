package com.fsep.sova.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.fsep.sova.R;
import com.fsep.sova.activities.base.BaseActivity;
import com.fsep.sova.fragments.CountrySearchFragment;
import com.fsep.sova.fragments.base.BaseSearchFragment;
import com.fsep.sova.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CountrySearchActivity extends BaseActivity {

    @Bind(R.id.toolbar) Toolbar mToolbar;
    private BaseSearchFragment mCountryFragment = new CountrySearchFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindow(R.color.black_top_bar_transparent_color);
        setContentView(R.layout.activity_single_search);
        ButterKnife.bind(this);
        initFragment();
        initToolbar();
    }

    private void initFragment() {
        mCountryFragment.setArguments(getIntent().getExtras());
        FragmentManager manager = getSupportFragmentManager();
        if (manager.findFragmentById(R.id.search_fragment) == null) {
            manager.beginTransaction()
                    .add(R.id.search_fragment, mCountryFragment, Constants.FRAGMENT_TAG)
                    .commit();
        } else {
            manager.beginTransaction()
                    .replace(R.id.search_fragment, mCountryFragment, Constants.FRAGMENT_TAG)
                    .commit();
        }
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

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(mCountryFragment.getQueryTextListener());
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
