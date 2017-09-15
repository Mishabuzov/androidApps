package com.fsep.sova.fragments;

import android.os.Bundle;
import android.view.View;

import com.fsep.sova.Config;
import com.fsep.sova.R;
import com.fsep.sova.network.ServiceHelper;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.actions.ActionFindTasks;
import com.fsep.sova.network.events.find_tasks.FindingTasksErrorEvent;
import com.fsep.sova.network.events.find_tasks.FindingTasksIsEmptyEvent;
import com.fsep.sova.network.events.find_tasks.FindingTasksIsSuccessEvent;
import com.fsep.sova.utils.AndroidUtils;
import com.fsep.sova.utils.UiUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TasksSearchFragment extends NotesFragment {

    private String mQuery;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        disableFabAndRefresh();
//        mAdapter = new TasksAdapter(getActivity());
//        getRecyclerView().setAdapter(mAdapter);
//        search("");
//        enablePagination(this);
        isPaginationActive = false;
    }

    private void disableFabAndRefresh() {
        if (getFab() != null) {
            getFab().setEnabled(false);
            getFab().setVisibility(View.GONE);
        }
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setEnabled(true);
        }
    }

    @Override
    protected void load() {
        Action action = new ActionFindTasks.Builder().from(0).find(mQuery).build();
        ServiceHelper.getInstance().startActionService(getActivity(), action);
    }

    @Override
    public void onRefresh() {
        isRefreshingActive = true;
        load();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessFindingTasks(FindingTasksIsSuccessEvent event) {
        hideProgressBar();
        isPaginationActive = false;
        if (getSwipeRefreshLayout() != null && getSwipeRefreshLayout().isRefreshing()) {
            getSwipeRefreshLayout().setRefreshing(false);
            mAdapter.setData(AndroidUtils.convertTasksIntoNotes(event.getTasks()));
        } else {
            mAdapter.updateData(AndroidUtils.convertTasksIntoNotes(event.getTasks()));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEmptyFindingTasks(FindingTasksIsEmptyEvent event) {
        if (mAdapter.getData().isEmpty()) {
            mAdapter.showEmptyDataView();
        }
        if (isPaginationActive) {
            isPaginationActive = false;
            mAdapter.enablePaginationView(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorFindingTasks(FindingTasksErrorEvent errorEvent) {
        UiUtils.showToast(R.string.empty_tasks_display);
    }

    @Override
    protected void search(String query) {
        mQuery = query;
        showProgressBar();
        load();
    }

    @Override
    public void onLoadMore(long lastElementId, int offset) {
        isPaginationActive = true;
        mAdapter.enablePaginationView(true);
        Action action = new ActionFindTasks.Builder().count(Config.COUNT_PER_PAGE).from((int) mAdapter.getLasElementId()).find(mQuery).build();
        ServiceHelper.getInstance().startActionService(getActivity(), action);
    }
}
