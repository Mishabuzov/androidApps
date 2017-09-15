package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Event;
import com.fsep.sova.network.events.get_event_by_id.GettingEventByIdErrorEvent;
import com.fsep.sova.network.events.get_event_by_id.GettingEventByIdIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionGetEventById extends BaseAction<BaseResponseModel<Event>> {

    private long mEventId;

    public ActionGetEventById(long eventId) {
        mEventId = eventId;
    }

    @Override
    protected Response<BaseResponseModel<Event>> makeRequest() throws IOException {
        return getRest().getEventById(mEventId).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<Event>> response) {
        EventBus.getDefault().post(new GettingEventByIdIsSuccessEvent(response.body().getData()));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingEventByIdErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mEventId);
    }

    protected ActionGetEventById(Parcel in) {
        this.mEventId = in.readLong();
    }

    public static final Creator<ActionGetEventById> CREATOR = new Creator<ActionGetEventById>() {
        @Override
        public ActionGetEventById createFromParcel(Parcel source) {
            return new ActionGetEventById(source);
        }

        @Override
        public ActionGetEventById[] newArray(int size) {
            return new ActionGetEventById[size];
        }
    };
}
