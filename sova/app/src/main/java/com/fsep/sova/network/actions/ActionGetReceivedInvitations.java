package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.ReceivedInvitation;
import com.fsep.sova.network.events.get_received_invitations.GettingReceivedInvitationsErrorEvent;
import com.fsep.sova.network.events.get_received_invitations.GettingReceivedInvitationsIsEmptyEvent;
import com.fsep.sova.network.events.get_received_invitations.GettingReceivedInvitationsIsSuccessEvent;
import com.fsep.sova.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class ActionGetReceivedInvitations extends BaseAction<BaseResponseModel<List<ReceivedInvitation>>> {
    private int mCount;
    private int mFrom;

    public ActionGetReceivedInvitations(Builder builder) {
        mCount = builder.count;
        mFrom = builder.from;
    }

    protected ActionGetReceivedInvitations(Parcel in) {
        mCount = in.readInt();
        mFrom = in.readInt();
    }

    @Override
    protected Response<BaseResponseModel<List<ReceivedInvitation>>> makeRequest() throws IOException {
        return getRest().getReceivedInvitations(createQueryMap()).execute();
    }

    private Map<String, Object> createQueryMap() {
        Map<String, Object> options = new HashMap<>();
        if (mCount != 0) {
            options.put("count", mCount);
        } else {
            options.put("count", Constants.DEFAULT_COUNT_VALUE);
        }
        if (mFrom != 0) {
            options.put("from", mFrom);
        }
        return options;
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<List<ReceivedInvitation>>> response) {
        List<ReceivedInvitation> invitations = response.body().getData();
        if (invitations.size() > 0) {
            EventBus.getDefault().post(new GettingReceivedInvitationsIsSuccessEvent(invitations));
        } else {
            EventBus.getDefault().post(new GettingReceivedInvitationsIsEmptyEvent());
        }
        EventBus.getDefault().post(new GettingReceivedInvitationsIsSuccessEvent(response.body().getData()));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingReceivedInvitationsErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActionGetReceivedInvitations> CREATOR = new Creator<ActionGetReceivedInvitations>() {
        @Override
        public ActionGetReceivedInvitations createFromParcel(Parcel source) {
            return new ActionGetReceivedInvitations(source);
        }

        @Override
        public ActionGetReceivedInvitations[] newArray(int size) {
            return new ActionGetReceivedInvitations[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mCount);
        dest.writeInt(mFrom);
    }

    public static class Builder {
        private int count;
        private int from;

        public Builder() {
        }

        public Builder count(int val) {
            count = val;
            return this;
        }

        public Builder from(int val) {
            from = val;
            return this;
        }

        public ActionGetReceivedInvitations build() {
            return new ActionGetReceivedInvitations(this);
        }
    }
}
