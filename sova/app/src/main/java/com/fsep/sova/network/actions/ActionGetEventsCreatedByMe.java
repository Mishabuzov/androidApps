package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Event;
import com.fsep.sova.network.events.get_events_created_by_me.GettingEventsCreatedByMeErrorEvent;
import com.fsep.sova.network.events.get_events_created_by_me.GettingEventsCreatedByMeIsEmptyEvent;
import com.fsep.sova.network.events.get_events_created_by_me.GettingEventsCreatedByMeIsSuccessEvent;
import com.fsep.sova.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class ActionGetEventsCreatedByMe extends BaseAction<BaseResponseModel<List<Event>>> {

    private boolean mPublished;
    private int mCount;
    private int mFrom;

    public ActionGetEventsCreatedByMe(Builder builder) {
        mPublished = builder.published;
        mCount = builder.count;
        mFrom = builder.from;
    }

    @Override
    protected Response<BaseResponseModel<List<Event>>> makeRequest() throws IOException {
        return getRest().getEventsCreatedByMe(createQueryMap()).execute();
    }

    private Map<String, Object> createQueryMap() {
        Map<String, Object> options = new HashMap<>();
        options.put("published", mPublished);
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
        List<Event> eventList = response.body().getData();
        if (eventList.isEmpty()) {
            EventBus.getDefault().post(new GettingEventsCreatedByMeIsEmptyEvent());
        } else {
            EventBus.getDefault().post(new GettingEventsCreatedByMeIsSuccessEvent(eventList));
        }
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingEventsCreatedByMeErrorEvent(response.code(), response.message()));
    }

    public static class Builder {
        private boolean published;
        private int count;
        private int from;

        public Builder(boolean published) {
            this.published = published;
        }

        public Builder count(int val) {
            count = val;
            return this;
        }

        public Builder from(int val) {
            from = val;
            return this;
        }

        public ActionGetEventsCreatedByMe build() {
            return new ActionGetEventsCreatedByMe(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.mPublished ? (byte) 1 : (byte) 0);
        dest.writeInt(this.mCount);
        dest.writeInt(this.mFrom);
    }

    protected ActionGetEventsCreatedByMe(Parcel in) {
        this.mPublished = in.readByte() != 0;
        this.mCount = in.readInt();
        this.mFrom = in.readInt();
    }

    public static final Creator<ActionGetEventsCreatedByMe> CREATOR = new Creator<ActionGetEventsCreatedByMe>() {
        @Override
        public ActionGetEventsCreatedByMe createFromParcel(Parcel source) {
            return new ActionGetEventsCreatedByMe(source);
        }

        @Override
        public ActionGetEventsCreatedByMe[] newArray(int size) {
            return new ActionGetEventsCreatedByMe[size];
        }
    };
}
