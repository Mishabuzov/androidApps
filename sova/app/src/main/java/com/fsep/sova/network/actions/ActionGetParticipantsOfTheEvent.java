package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.UserInfo;
import com.fsep.sova.network.events.get_participants_of_the_event.GettingParticipantsOfTheEventIsEmptyEvent;
import com.fsep.sova.network.events.get_participants_of_the_event.GettingParticipantsOfTheEventIsErrorEvent;
import com.fsep.sova.network.events.get_participants_of_the_event.GettingParticipantsOfTheEventIsSuccessEvent;
import com.fsep.sova.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class ActionGetParticipantsOfTheEvent extends BaseAction<BaseResponseModel<List<UserInfo>>> {

    private long mEventId;
    private int mCount;
    private int mFrom;

    public ActionGetParticipantsOfTheEvent(Builder builder) {
        mEventId = builder.eventId;
        mCount = builder.count;
        mFrom = builder.from;
    }

    @Override
    protected Response<BaseResponseModel<List<UserInfo>>> makeRequest() throws IOException {
        return getRest().getParticipantsOfTheEvent(mEventId, createQueryMap()).execute();
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
    protected void onResponseSuccess(Response<BaseResponseModel<List<UserInfo>>> response) {
        List<UserInfo> participantList = response.body().getData();
        if (participantList.isEmpty()) {
            EventBus.getDefault().post(new GettingParticipantsOfTheEventIsEmptyEvent());
        } else {
            EventBus.getDefault().post(new GettingParticipantsOfTheEventIsSuccessEvent(participantList));
        }
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingParticipantsOfTheEventIsErrorEvent(response.code(), response.message()));
    }

    public static class Builder {
        private long eventId;
        private int count;
        private int from;

        public Builder(long eventId) {
            this.eventId = eventId;
        }

        public Builder count(int val) {
            count = val;
            return this;
        }

        public Builder from(int val) {
            from = val;
            return this;
        }

        public ActionGetParticipantsOfTheEvent build() {
            return new ActionGetParticipantsOfTheEvent(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mEventId);
        dest.writeInt(this.mCount);
        dest.writeInt(this.mFrom);
    }

    protected ActionGetParticipantsOfTheEvent(Parcel in) {
        this.mEventId = in.readLong();
        this.mCount = in.readInt();
        this.mFrom = in.readInt();
    }

    public static final Creator<ActionGetParticipantsOfTheEvent> CREATOR = new Creator<ActionGetParticipantsOfTheEvent>() {
        @Override
        public ActionGetParticipantsOfTheEvent createFromParcel(Parcel source) {
            return new ActionGetParticipantsOfTheEvent(source);
        }

        @Override
        public ActionGetParticipantsOfTheEvent[] newArray(int size) {
            return new ActionGetParticipantsOfTheEvent[size];
        }
    };
}
