package com.fsep.sova.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.fsep.sova.Config;
import com.fsep.sova.adapters.InvitationsAdapter;
import com.fsep.sova.adapters.base.BaseRecyclerViewAdapter;
import com.fsep.sova.fragments.base.BaseRecyclerViewFragment;
import com.fsep.sova.local_events.RefreshingCommentsEvent;
import com.fsep.sova.local_events.RefreshingDataEvent;
import com.fsep.sova.models.Note;
import com.fsep.sova.models.ReceivedInvitation;
import com.fsep.sova.network.ServiceHelper;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.actions.ActionGetPerformingTasks;
import com.fsep.sova.network.actions.ActionGetReceivedInvitations;
import com.fsep.sova.network.events.NetworkErrorEvent;
import com.fsep.sova.network.events.add_to_favourites.AddingToFavouritesIsSuccessEvent;
import com.fsep.sova.network.events.delete_from_favourites.DeletingFromFavouritesIsSuccessEvent;
import com.fsep.sova.network.events.delete_invitation.DeletingInvitationIsSuccessEvent;
import com.fsep.sova.network.events.get_received_invitations.GettingReceivedInvitationsIsEmptyEvent;
import com.fsep.sova.network.events.get_received_invitations.GettingReceivedInvitationsIsSuccessEvent;
import com.fsep.sova.network.events.getperformedtasks.GettingPerformingTasksIsEmptyEvent;
import com.fsep.sova.network.events.getperformedtasks.GettingPerformingTasksIsSuccessEvent;
import com.fsep.sova.network.events.put_like_to_note.PuttingLikeIsSuccessEvent;
import com.fsep.sova.network.events.taketask.TakingTaskIsSuccessEvent;
import com.fsep.sova.utils.AndroidUtils;
import com.fsep.sova.utils.EndlessRecyclerScrollListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class InvitationsFragment extends BaseRecyclerViewFragment implements EndlessRecyclerScrollListener.PaginationLoadable {

    private InvitationsAdapter mAdapter;
    private List<ReceivedInvitation> mInvitations;
    private List<Note> mPerformingTasks;
    private boolean isEmptyInviteList = false;
    private boolean isLoadMore = false;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new InvitationsAdapter(getActivity());
        getRecyclerView().setAdapter(mAdapter);
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setEnabled(true);
        }
        showProgressBar();
        load();
        enablePagination(this);
        mAdapter.enablePaginationView(true);
    }

    private void load() {
        Action actionGetInvites = new ActionGetReceivedInvitations.Builder().build();
        ServiceHelper.getInstance().startActionService(getActivity(), actionGetInvites);
    }

    private void loadMore(int count){
        mAdapter.enablePaginationView(true);
        if(mAdapter.isLastInvitation()) {
            Action actionGetInvites = new ActionGetReceivedInvitations.Builder()
                    .from((int)mAdapter.getLastInvitationId())
                    .count(count)
                    .build();
            ServiceHelper.getInstance().startActionService(getActivity(), actionGetInvites);
        } else {
            Action actionGetPerformingTasks = new ActionGetPerformingTasks.Builder()
                    .from((int)mAdapter.getLastTaskId())
                    .count(count)
                    .build();
            ServiceHelper.getInstance().startActionService(getActivity(), actionGetPerformingTasks);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessGettingInvites(GettingReceivedInvitationsIsSuccessEvent event) {
        mInvitations = event.getReceivedInvitations();
        if(isLoadMore) {
            if(mInvitations.size() < Config.COUNT_PER_PAGE) {
                loadMore(Config.COUNT_PER_PAGE - mInvitations.size());
            } else {
                mAdapter.onUpdateData(mInvitations, new ArrayList<Note>());
                isLoadMore = false;
            }
        } else {
            gettingPerformingTasks();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEmptyInvitesList(GettingReceivedInvitationsIsEmptyEvent event) {
        isEmptyInviteList = true;
        gettingPerformingTasks();
    }

    private void gettingPerformingTasks() {
        Action actionGetPerformingTasks = new ActionGetPerformingTasks.Builder().build();
        ServiceHelper.getInstance().startActionService(getActivity(), actionGetPerformingTasks);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessGettingPerformingTasks(GettingPerformingTasksIsSuccessEvent event) {
        hideProgressBar();
        if(getSwipeRefreshLayout() != null && getSwipeRefreshLayout().isRefreshing()) {
            getSwipeRefreshLayout().setRefreshing(false);
        }
        mPerformingTasks = AndroidUtils.convertTasksIntoNotes(event.getTasks());
        if(isLoadMore) {
            mAdapter.onUpdateData(mInvitations, mPerformingTasks);
        } else {
            mAdapter.setInvitations(mInvitations);
            mAdapter.setData(mPerformingTasks);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEmptyGettingPerformingTasks(GettingPerformingTasksIsEmptyEvent event) {
        hideProgressBar();
        if(getSwipeRefreshLayout() != null && getSwipeRefreshLayout().isRefreshing()) {
            getSwipeRefreshLayout().setRefreshing(false);
        }
        if(isLoadMore) {
            isLoadMore = false;
            mAdapter.enablePaginationView(false);
        }
        if(mAdapter.isEmpty()) {
            mAdapter.showEmptyDataView();
        }


    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void refreshCommentsCount(RefreshingCommentsEvent event) {
        mAdapter.refreshCommentsCount(event.getTaskId(), event.getNewCommentsAmount());
        EventBus.getDefault().removeStickyEvent(event);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessTakingTask(TakingTaskIsSuccessEvent event) {
        mAdapter.takeTask(event.getTookTask());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessDeletingInvitation(DeletingInvitationIsSuccessEvent event) {
        mAdapter.deleteInvitation();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessAddedToFavourite(AddingToFavouritesIsSuccessEvent event) {
        mAdapter.updateFavouriteElement(event.getId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessDeletedFromFavourite(DeletingFromFavouritesIsSuccessEvent event) {
        mAdapter.updateFavouriteElement(event.getId());
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void refreshTask(RefreshingDataEvent event) {
        mAdapter.refreshData(event.getNote());
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLikeActionInAdapter(PuttingLikeIsSuccessEvent event) {
        mAdapter.updateLikedElement(event.getNote().getId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkErrorEvent(NetworkErrorEvent event){
        mAdapter.showNetworkErrorView();
    }

    @Nullable
    @Override
    protected BaseRecyclerViewAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onRefresh() {
        isRefreshingActive = true;
        load();
    }

    private int getLastTaskId(){
        return (int)mAdapter.getLasElementId();
    }

    @Override
    public void onLoadMore(long lastElementId, int offset) {
        isLoadMore = true;
        loadMore(offset);
    }
}
