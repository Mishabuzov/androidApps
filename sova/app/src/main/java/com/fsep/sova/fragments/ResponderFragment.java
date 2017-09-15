package com.fsep.sova.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fsep.sova.R;
import com.fsep.sova.adapters.UsersAdapter;
import com.fsep.sova.adapters.base.BaseRecyclerViewAdapter;
import com.fsep.sova.fragments.base.BaseRecyclerViewFragment;
import com.fsep.sova.models.ResponseOnTask;
import com.fsep.sova.models.UserInfo;
import com.fsep.sova.network.ServiceHelper;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.actions.ActionGetParticipantsOfTheEvent;
import com.fsep.sova.network.actions.ActionGetResponses;
import com.fsep.sova.network.events.get_participants_of_the_event.GettingParticipantsOfTheEventIsEmptyEvent;
import com.fsep.sova.network.events.get_participants_of_the_event.GettingParticipantsOfTheEventIsSuccessEvent;
import com.fsep.sova.network.events.get_responses.GettingResponsesErrorEvent;
import com.fsep.sova.network.events.get_responses.GettingResponsesIsEmptyEvent;
import com.fsep.sova.network.events.get_responses.GettingResponsesIsSuccessEvent;
import com.fsep.sova.utils.Constants;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResponderFragment extends BaseRecyclerViewFragment {

    private UsersAdapter mAdapter;
    private long mNoteId;
    private boolean mHasPerformer;
    private boolean mIsEvent;

    @OnClick(R.id.back_btn)
    public void onBackBtn() {
        getActivity().finish();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setEnabled(false);
        }
        mAdapter = new UsersAdapter(this, getActivity(), -1);
        mAdapter.setResponderAdapter();
        getRecyclerView().setAdapter(mAdapter);

        Bundle args = getArguments();
        if (args != null) {
            mNoteId = args.getLong(Constants.NOTE_ID);
            mHasPerformer = args.getBoolean(Constants.HAS_PERFORMER);
            mIsEvent = args.getBoolean(Constants.IS_EVENT);
        }
        load();
    }

    private void load() {
        Action action = null;
        if (mIsEvent) {
            action = new ActionGetParticipantsOfTheEvent.Builder(mNoteId).build();
        } else {
            action = new ActionGetResponses.Builder(mNoteId).build();
        }
        ServiceHelper.getInstance().startActionService(getActivity(), action);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GettingParticipantsOfTheEventIsSuccessEvent event) {
        List<UserInfo> participants = event.getParticipantList();
        mAdapter.neededBlockingOnChoosingPerformer();
        mAdapter.setUserInfos(participants);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GettingParticipantsOfTheEventIsEmptyEvent event) {
        mAdapter.showEmptyDataView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessGettingResponses(GettingResponsesIsSuccessEvent event) {
        List<ResponseOnTask> responses = event.getResponses();
        List<UserInfo> responders = new ArrayList<>();
        for (ResponseOnTask response : responses) {
            responders.add(response.getResponder());
        }
        if (mHasPerformer) {
            mAdapter.neededBlockingOnChoosingPerformer();
        }
        mAdapter.setUserInfos(responders);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GettingResponsesIsEmptyEvent event) {
        mAdapter.showEmptyDataView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GettingResponsesErrorEvent event) {
        Toast.makeText(getActivity(), R.string.content_get_users_error, Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    protected BaseRecyclerViewAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onRefresh() {
    }
}
