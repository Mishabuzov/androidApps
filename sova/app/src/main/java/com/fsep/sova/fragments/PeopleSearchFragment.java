package com.fsep.sova.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.fsep.sova.R;
import com.fsep.sova.adapters.PeopleSearchAdapter;
import com.fsep.sova.adapters.base.BaseRecyclerViewAdapter;
import com.fsep.sova.fragments.base.BaseSearchFragment;
import com.fsep.sova.network.ServiceHelper;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.actions.ActionFindUsers;
import com.fsep.sova.network.events.find_users.FindingUsersErrorEvent;
import com.fsep.sova.network.events.find_users.FindingUsersIsEmptyEvent;
import com.fsep.sova.network.events.find_users.FindingUsersIsSuccessEvent;
import com.fsep.sova.utils.UiUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class PeopleSearchFragment extends BaseSearchFragment {

    private PeopleSearchAdapter mSearchAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchAdapter = new PeopleSearchAdapter();
        disableFabAndRefresh();
        getRecyclerView().setAdapter(mSearchAdapter);
        search("");
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
    public void onEvent(FindingUsersIsSuccessEvent event) {
        mSearchAdapter.setPeople(event.getUsers());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FindingUsersIsEmptyEvent event) {
        mSearchAdapter.showEmptyDataView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FindingUsersErrorEvent event) {
        UiUtils.showToast(R.string.content_get_users_error);
    }

    @Nullable
    @Override
    protected BaseRecyclerViewAdapter getAdapter() {
        return mSearchAdapter;
    }

    @Override
    public void onRefresh() {
    }

    @Override
    protected void search(String query) {
        Action searchAction = new ActionFindUsers.Builder()
                .find(query).build();
        ServiceHelper.getInstance().startActionService(getActivity(), searchAction);
    }
}
