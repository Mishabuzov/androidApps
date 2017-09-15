package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Event;
import com.fsep.sova.network.events.get_events_where_i_participate.GettingEventsWhereIParticipateErrorEvent;
import com.fsep.sova.network.events.get_events_where_i_participate.GettingEventsWhereIParticipateIsEmptyEvent;
import com.fsep.sova.network.events.get_events_where_i_participate.GettingEventsWhereIParticipateIsSuccessEvent;
import com.fsep.sova.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class ActionGetEventsWhereIParticipate extends BaseAction<BaseResponseModel<List<Event>>> {

    private int mCount;
    private int mFrom;

    public ActionGetEventsWhereIParticipate(Builder builder) {
        mCount = builder.count;
        mFrom = builder.from;
    }

    @Override
    protected Response<BaseResponseModel<List<Event>>> makeRequest() throws IOException {
        return getRest().getEventsWhereIParticipate(createQueryMap()).execute();
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
    protected void onResponseSuccess(Response<BaseResponseModel<List<Event>>> response) {
        List<Event> myEvents = response.body().getData();
        if (myEvents.isEmpty()) {
            EventBus.getDefault().post(new GettingEventsWhereIParticipateIsEmptyEvent());
        } else {
            EventBus.getDefault().post(new GettingEventsWhereIParticipateIsSuccessEvent(myEvents));
        }
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingEventsWhereIParticipateErrorEvent(response.code(), response.message()));
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

        public ActionGetEventsWhereIParticipate build() {
            return new ActionGetEventsWhereIParticipate(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mCount);
        dest.writeInt(this.mFrom);
    }

    protected ActionGetEventsWhereIParticipate(Parcel in) {
        this.mCount = in.readInt();
        this.mFrom = in.readInt();
    }

    public static final Creator<ActionGetEventsWhereIParticipate> CREATOR = new Creator<ActionGetEventsWhereIParticipate>() {
        @Override
        public ActionGetEventsWhereIParticipate createFromParcel(Parcel source) {
            return new ActionGetEventsWhereIParticipate(source);
        }

        @Override
        public ActionGetEventsWhereIParticipate[] newArray(int size) {
            return new ActionGetEventsWhereIParticipate[size];
        }
    };
}
