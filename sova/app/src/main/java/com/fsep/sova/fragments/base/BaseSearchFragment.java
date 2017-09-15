package com.fsep.sova.fragments.base;

import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;

import com.fsep.sova.adapters.base.BaseRecyclerViewAdapter;

public abstract class BaseSearchFragment extends BaseRecyclerViewFragment implements SearchView.OnQueryTextListener {

    protected String mQuery;

    @Nullable
    @Override
    protected abstract BaseRecyclerViewAdapter getAdapter();

    @Override
    public abstract void onRefresh();

    protected abstract void search(String query);

    @Override
    public boolean onQueryTextSubmit(String query) {
        mQuery = query;
        search(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mQuery = newText;
        search(newText);
        return false;
    }

    public SearchView.OnQueryTextListener getQueryTextListener() {
        return this;
    }
}
