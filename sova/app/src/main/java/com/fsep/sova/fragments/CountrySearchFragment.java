package com.fsep.sova.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.fsep.sova.R;
import com.fsep.sova.adapters.CountryCityAdapter;
import com.fsep.sova.adapters.base.BaseRecyclerViewAdapter;
import com.fsep.sova.fragments.base.BaseSearchFragment;
import com.fsep.sova.network.ServiceHelper;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.actions.ActionFindCountry;
import com.fsep.sova.network.events.find_countries.FindingCountryIsEmptyEvent;
import com.fsep.sova.network.events.find_countries.FindingCountryIsErrorEvent;
import com.fsep.sova.network.events.find_countries.FindingCountryIsSuccessEvent;
import com.fsep.sova.utils.EndlessRecyclerScrollListener;
import com.fsep.sova.utils.UiUtils;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CountrySearchFragment extends BaseSearchFragment implements EndlessRecyclerScrollListener.PaginationLoadable{

    private CountryCityAdapter mAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        disableFabAndRefresh();
        mAdapter = new CountryCityAdapter(getActivity());
        getRecyclerView().setAdapter(mAdapter);
        getRecyclerView().addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .colorResId(R.color.recycler_view_divider_bg)
                .sizeResId(R.dimen.recycler_view_divider_height)
                .marginResId(R.dimen.recycler_view_country_city_divider_left_padding,
                        R.dimen.recycler_view_country_city_divider_right_padding)
                .build());
        search("");
        enablePagination(this);
        isPaginationActive = false;
    }

    @Override
    protected void search(String query) {
        Action action = new ActionFindCountry.Builder().find(query).build();
        ServiceHelper.getInstance().startActionService(getActivity(), action);
    }

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
    public void onSuccessFindingCities(FindingCountryIsSuccessEvent event) {
        if (getSwipeRefreshLayout() != null && getSwipeRefreshLayout().isRefreshing()) {
            isRefreshingActive = false;
            mAdapter.setCountryCities(event.getCountryList());
            getSwipeRefreshLayout().setRefreshing(false);
        } else if (isPaginationActive) {
            isPaginationActive = false;
            mAdapter.updateData(event.getCountryList());
        } else {
            mAdapter.setCountryCities(event.getCountryList());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEmptyFindingCities(FindingCountryIsEmptyEvent event) {
        if (mAdapter.getData().isEmpty()) {
            mAdapter.showEmptyDataView();
        } else {
            mAdapter.enablePaginationView(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorFindingCities(FindingCountryIsErrorEvent event) {
        if (mAdapter.getData().isEmpty()) {
            mAdapter.showEmptyDataView();
        } else {
            mAdapter.enablePaginationView(false);
        }
        UiUtils.showToast(R.string.empty_countries_display);
    }

    @Nullable
    @Override
    protected BaseRecyclerViewAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onRefresh() {
        isRefreshingActive = true;
        search(mQuery);
    }

    @Override
    public void onLoadMore(long lastElementId, int offset) {
        mAdapter.enablePaginationView(true);
        isPaginationActive = true;
        Action action = new ActionFindCountry.Builder().find(mQuery).from(String.valueOf(mAdapter.getLastItemId())).build();
        ServiceHelper.getInstance().startActionService(getActivity(), action);
    }
}
