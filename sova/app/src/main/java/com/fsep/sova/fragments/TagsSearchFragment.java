package com.fsep.sova.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.fsep.sova.R;
import com.fsep.sova.adapters.TagsAdapter;
import com.fsep.sova.adapters.base.BaseRecyclerViewAdapter;
import com.fsep.sova.fragments.base.BaseSearchFragment;
import com.fsep.sova.network.ServiceHelper;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.actions.ActionFindTags;
import com.fsep.sova.network.events.find_tags.FindingTagsErrorEvent;
import com.fsep.sova.network.events.find_tags.FindingTagsIsEmptyEvent;
import com.fsep.sova.network.events.find_tags.FindingTagsIsSuccessEvent;
import com.fsep.sova.utils.EndlessRecyclerScrollListener;
import com.fsep.sova.utils.UiUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TagsSearchFragment extends BaseSearchFragment implements EndlessRecyclerScrollListener.PaginationLoadable {

    private TagsAdapter mTagsAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        disableFabAndRefresh();
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setEnabled(true);
        }
        mTagsAdapter = new TagsAdapter();
        getRecyclerView().setAdapter(mTagsAdapter);
        search("");
        enablePagination(this);
        isPaginationActive = false;
    }

   /* private void initToolbar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }*/

    private void disableFabAndRefresh() {
        if (getFab() != null) {
            getFab().setEnabled(false);
            getFab().setVisibility(View.GONE);
        }
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setEnabled(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessFindingTags(FindingTagsIsSuccessEvent event) {
        if (getSwipeRefreshLayout() != null && getSwipeRefreshLayout().isRefreshing()) {
            isRefreshingActive = false;
            mTagsAdapter.setTags(event.getFoundedTags());
            getSwipeRefreshLayout().setRefreshing(false);
        } else if (isPaginationActive) {
            isPaginationActive = false;
            mTagsAdapter.updateData(event.getFoundedTags());
        } else {
            mTagsAdapter.setTags(event.getFoundedTags());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEmptyFindingTags(FindingTagsIsEmptyEvent event) {
        if (mTagsAdapter.getData().isEmpty()) {
            mTagsAdapter.showEmptyDataView();
        } else {
            mTagsAdapter.enablePaginationView(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorFindingTags(FindingTagsErrorEvent event) {
        if (mTagsAdapter.getData().isEmpty()) {
            mTagsAdapter.showEmptyDataView();
        } else {
            mTagsAdapter.enablePaginationView(false);
        }
        UiUtils.showToast(R.string.empty_tags_display);
    }

    @Nullable
    @Override
    protected BaseRecyclerViewAdapter getAdapter() {
        return mTagsAdapter;
    }

    @Override
    public void onRefresh() {
        isRefreshingActive = true;
        search(mQuery);
    }

    @Override
    protected void search(String query) {
        Action action = new ActionFindTags.Builder().find(query).build();
        ServiceHelper.getInstance().startActionService(getActivity(), action);
    }

    @Override
    public void onLoadMore(long lastElementId, int offset) {
        mTagsAdapter.enablePaginationView(true);
        isPaginationActive = true;
        Action action = new ActionFindTags.Builder().find(mQuery).from(mTagsAdapter.getLastItemId()).build();
        ServiceHelper.getInstance().startActionService(getActivity(), action);
    }
}
