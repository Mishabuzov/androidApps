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
import com.fsep.sova.network.actions.ActionFindCity;
import com.fsep.sova.network.events.find_cities.FindingCitiesIsEmptyEvent;
import com.fsep.sova.network.events.find_cities.FindingCitiesIsErrorEvent;
import com.fsep.sova.network.events.find_cities.FindingCitiesIsSuccessEvent;
import com.fsep.sova.utils.Constants;
import com.fsep.sova.utils.EndlessRecyclerScrollListener;
import com.fsep.sova.utils.UiUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CitySearchFragment extends BaseSearchFragment implements EndlessRecyclerScrollListener.PaginationLoadable{

    private CountryCityAdapter mAdapter;
    private long mCountryId;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        disableFabAndRefresh();
        mAdapter = new CountryCityAdapter(getActivity());
        mCountryId = getArguments().getLong(Constants.COUNTRY_ID);
        getRecyclerView().setAdapter(mAdapter);
        if (mCountryId != 0) {
            search("");
        }
        enablePagination(this);
        isPaginationActive = false;
    }

    @Override
    protected void search(String query) {
        Action action = new ActionFindCity.Builder(mCountryId).find(query).build();
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
    public void onSuccessFindingCities(FindingCitiesIsSuccessEvent event) {
        if (getSwipeRefreshLayout() != null && getSwipeRefreshLayout().isRefreshing()) {
            isRefreshingActive = false;
            mAdapter.setCountryCities(event.getCities());
            getSwipeRefreshLayout().setRefreshing(false);
        } else if (isPaginationActive) {
            isPaginationActive = false;
            mAdapter.updateData(event.getCities());
        } else {
            mAdapter.setCountryCities(event.getCities());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEmptyFindingCities(FindingCitiesIsEmptyEvent event) {
        if (mAdapter.getData().isEmpty()) {
            mAdapter.showEmptyDataView();
        } else {
            mAdapter.enablePaginationView(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorFindingCities(FindingCitiesIsErrorEvent event) {
        if (mAdapter.getData().isEmpty()) {
            mAdapter.showEmptyDataView();
        } else {
            mAdapter.enablePaginationView(false);
        }
        UiUtils.showToast(R.string.empty_cities_display);
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
        Action action = new ActionFindCity.Builder(mCountryId).find(mQuery).from(String.valueOf(mAdapter.getLastItemId())).build();
        ServiceHelper.getInstance().startActionService(getActivity(), action);
    }
}
