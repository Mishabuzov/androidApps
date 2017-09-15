package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.SentInvitation;
import com.fsep.sova.network.events.get_sent_invitations.GettingSentInvitationsErrorEvent;
import com.fsep.sova.network.events.get_sent_invitations.GettingSentInvitationsIsEmptyEvent;
import com.fsep.sova.network.events.get_sent_invitations.GettingSentInvitationsIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class ActionGetSentInvitations extends BaseAction<BaseResponseModel<List<SentInvitation>>> {

    private long mTaskId;

    public ActionGetSentInvitations(long taskId) {
        mTaskId = taskId;
    }

    protected ActionGetSentInvitations(Parcel in) {
        mTaskId = in.readLong();
    }

    @Override
    protected Response<BaseResponseModel<List<SentInvitation>>> makeRequest() throws IOException {
        return getRest().getSentInvitations(mTaskId).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<List<SentInvitation>>> response) {
        List<SentInvitation> sentInvitations = response.body().getData();
        if (sentInvitations.size() > 0) {
            EventBus.getDefault().post(new GettingSentInvitationsIsSuccessEvent(sentInvitations));
        } else {
            EventBus.getDefault().post(new GettingSentInvitationsIsEmptyEvent());
        }
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingSentInvitationsErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mTaskId);
    }

    public static final Creator<ActionGetSentInvitations> CREATOR = new Creator<ActionGetSentInvitations>() {
        @Override
        public ActionGetSentInvitations createFromParcel(Parcel source) {
            return new ActionGetSentInvitations(source);
        }

        @Override
        public ActionGetSentInvitations[] newArray(int size) {
            return new ActionGetSentInvitations[size];
        }
    };
}
