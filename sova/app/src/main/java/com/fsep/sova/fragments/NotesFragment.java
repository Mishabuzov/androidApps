package com.fsep.sova.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.fsep.sova.Config;
import com.fsep.sova.activities.AddTaskActivity;
import com.fsep.sova.adapters.NotesAdapter;
import com.fsep.sova.adapters.base.BaseRecyclerViewAdapter;
import com.fsep.sova.fragments.base.BaseSearchFragment;
import com.fsep.sova.local_events.RefreshingCommentsEvent;
import com.fsep.sova.local_events.RefreshingDataEvent;
import com.fsep.sova.models.AssignTaskSendingModel;
import com.fsep.sova.models.Note;
import com.fsep.sova.models.TaskListType;
import com.fsep.sova.models.UserInfo;
import com.fsep.sova.network.ServiceHelper;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.actions.ActionAssignTask;
import com.fsep.sova.network.actions.ActionGetEventsWhereIParticipate;
import com.fsep.sova.network.actions.ActionGetFavouriteTasks;
import com.fsep.sova.network.actions.ActionGetFeed;
import com.fsep.sova.network.actions.ActionGetOwnTasks;
import com.fsep.sova.network.events.NetworkErrorEvent;
import com.fsep.sova.network.events.add_to_favourites.AddingToFavouritesIsSuccessEvent;
import com.fsep.sova.network.events.assign_task.AssignTaskErrorEvent;
import com.fsep.sova.network.events.assign_task.AssignTaskIsSuccessEvent;
import com.fsep.sova.network.events.delete_from_favourites.DeletingFromFavouritesIsSuccessEvent;
import com.fsep.sova.network.events.get_events_where_i_participate.GettingEventsWhereIParticipateIsEmptyEvent;
import com.fsep.sova.network.events.get_events_where_i_participate.GettingEventsWhereIParticipateIsSuccessEvent;
import com.fsep.sova.network.events.get_favourite_tasks.GettingFavouriteNotesIsSuccessEvent;
import com.fsep.sova.network.events.get_favourite_tasks.GettingFavouriteTasksIsEmptyEvent;
import com.fsep.sova.network.events.get_feed.GettingFeedIsEmptyEvent;
import com.fsep.sova.network.events.get_feed.GettingFeedIsSuccessEvent;
import com.fsep.sova.network.events.getowntasks.GettingOwnTasksIsEmptyEvent;
import com.fsep.sova.network.events.getowntasks.GettingOwnTasksIsSuccessEvent;
import com.fsep.sova.network.events.put_like_to_note.PuttingLikeIsSuccessEvent;
import com.fsep.sova.utils.AndroidUtils;
import com.fsep.sova.utils.Constants;
import com.fsep.sova.utils.EndlessRecyclerScrollListener;
import com.fsep.sova.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class NotesFragment extends BaseSearchFragment implements NotesAdapter.Callback, EndlessRecyclerScrollListener.PaginationLoadable {

    protected NotesAdapter mAdapter;
    public static final String LIST_TYPE = "LIST_TYPE";
    public static final int REQUEST_CHOOSING_PERFORMER = 1;
    private UserInfo mUserInfo;
    private TaskListType mListType;
    private int mNewAmountOfResponder;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new NotesAdapter(getActivity(), this);
        getRecyclerView().setAdapter(mAdapter);

        getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTaskActivity.class);
                startActivity(intent);
            }
        });
        showProgressBar();
        load();
        enablePagination(this);
//        mAdapter.enablePaginationView(true);
    }

    protected void load() {
        mListType = (TaskListType) getArguments().get(LIST_TYPE);
        Action action;
        switch (mListType) {
            case FEED:
                action = new ActionGetFeed();
                break;
            case FAVOURITES:
                action = new ActionGetFavouriteTasks();
                break;
            case MY_TASKS:
                action = new ActionGetOwnTasks.Builder(true).build();
                break;
            case MY_EVENTS:
                action = new ActionGetEventsWhereIParticipate.Builder().build();
                break;
            default:
                action = new ActionGetFeed();
        }
        ServiceHelper.getInstance().startActionService(getActivity(), action);
    }

    protected void loadMore(int from, int count) {
        mAdapter.enablePaginationView(true);
        mListType = (TaskListType) getArguments().get(LIST_TYPE);
        Action action;
        switch (mListType) {
            case FAVOURITES:
                ActionGetFavouriteTasks.Builder builder = new ActionGetFavouriteTasks.Builder()
                        .count(count)
                        .from(from);
                action = new ActionGetFavouriteTasks(builder);
                break;
            case MY_TASKS:
                ActionGetOwnTasks.Builder builder1 = new ActionGetOwnTasks.Builder(true)
                        .count(count)
                        .from(from);
                action = new ActionGetOwnTasks(builder1);
                break;
            case MY_EVENTS:
                ActionGetEventsWhereIParticipate.Builder builder2 = new ActionGetEventsWhereIParticipate.Builder()
                        .count(count)
                        .from(from);
                action = new ActionGetEventsWhereIParticipate(builder2);
                break;
            default:
                ActionGetFeed.Builder builder3 = new ActionGetFeed.Builder()
                        .count(count)
                        .from(from);
                action = new ActionGetFeed(builder3);
        }
        ServiceHelper.getInstance().startActionService(getActivity(), action);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CHOOSING_PERFORMER:
                    mUserInfo = data.getParcelableExtra(Constants.EXTRA_USER);
                    mNewAmountOfResponder = data.getIntExtra(Constants.AMOUNT_OF_RESPONDERS, 0);
                    Action action = new ActionAssignTask(mUserInfo.getId(), new AssignTaskSendingModel(mAdapter.getTaskId()));
                    ServiceHelper.getInstance().startActionService(getActivity(), action);
                    break;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessAssigningOnTask(AssignTaskIsSuccessEvent event) {
        mAdapter.refreshingPerformingTask(mUserInfo, mNewAmountOfResponder);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorAssigningOnTask(AssignTaskErrorEvent event) {
        UiUtils.showToast("Не удалось назначить выбранного исполнителя");
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void refreshTask(RefreshingDataEvent event) {
        if (event.isDeletingFromFavouritesEvent()) {
            checkIsFavouritesList();
            mAdapter.updateFavouriteElement(event.getNote().getId());
        } else {
            mAdapter.refreshData(event.getNote());
        }
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GettingEventsWhereIParticipateIsSuccessEvent event) {
        loadingEnd();
        updateData(AndroidUtils.convertEventsIntoNotes(event.getEvents()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GettingEventsWhereIParticipateIsEmptyEvent event) {
        loadingEnd();
        if (mAdapter.getData().isEmpty()) {
            mAdapter.showEmptyDataView();
        } else {
            mAdapter.enablePaginationView(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadingFeedSuccessEvent(GettingFeedIsSuccessEvent event) {
        loadingEnd();
        updateData(event.getNotes());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEmptyFeedEvent(GettingFeedIsEmptyEvent event) {
        loadingEnd();
        if (mAdapter.getData().isEmpty()) {
            mAdapter.showEmptyDataView();
        } else {
            mAdapter.enablePaginationView(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadingOwnTasksSuccessEvent(GettingOwnTasksIsSuccessEvent event) {
        loadingEnd();
        updateData(AndroidUtils.convertTasksIntoNotes(event.getTasks()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadingOwnTasksEmptyEvent(GettingOwnTasksIsEmptyEvent event) {
        loadingEnd();
        if(mAdapter.getData().isEmpty()) {
            mAdapter.showEmptyDataView();
        } else {
            mAdapter.enablePaginationView(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadingFavouritesTasksSuccessEvent(GettingFavouriteNotesIsSuccessEvent event) {
        loadingEnd();
        updateData(event.getNotes());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadingFavouritesTasksEmptyEvent(GettingFavouriteTasksIsEmptyEvent event) {
        loadingEnd();
        if(mAdapter.getData().isEmpty()) {
            mAdapter.showEmptyDataView();
        } else {
            mAdapter.enablePaginationView(false);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void refreshCommentsCount(RefreshingCommentsEvent event) {
        mAdapter.refreshCommentsCount(event.getTaskId(), event.getNewCommentsAmount());
        EventBus.getDefault().removeStickyEvent(event);
    }

    private void checkIsFavouritesList() {
        if (mListType == TaskListType.FAVOURITES) {
            mAdapter.setAdapterForFavourites();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessAddedToFavourite(AddingToFavouritesIsSuccessEvent event) {
        mAdapter.updateFavouriteElement(event.getId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessDeletedFromFavourite(DeletingFromFavouritesIsSuccessEvent event) {
        checkIsFavouritesList();
        mAdapter.showEmptyDataView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLikeActionInAdapter(PuttingLikeIsSuccessEvent event) {
        mAdapter.updateLikedElement(event.getNote().getId());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkErrorEvent(NetworkErrorEvent event){
        mAdapter.showNetworkErrorView();
    }


    @Override
    public void onRefresh() {
        isRefreshingActive = true;
        loadMore(0, Config.COUNT_PER_PAGE);
    }

    @Override
    public void onChoosingPerformer(Intent intent) {
        startActivityForResult(intent, NotesFragment.REQUEST_CHOOSING_PERFORMER);
    }

    protected void search(String query) {
    }

    protected void loadingEnd(){
        hideProgressBar();
        if (getSwipeRefreshLayout() != null && getSwipeRefreshLayout().isRefreshing()) {
            getSwipeRefreshLayout().setRefreshing(false);
        }
    }

    protected void updateData(List<Note> notes) {
        if(isRefreshingActive) {
            mAdapter.setData(notes);
            isRefreshingActive = false;
        } else {
            mAdapter.updateData(notes);
        }
    }

    private int getLastElementId(){
        return (int)mAdapter.getLasElementId();
    }

    @Nullable
    @Override
    protected BaseRecyclerViewAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onLoadMore(long lastElementId, int offset) {
        loadMore(getLastElementId(), offset);
    }
}
