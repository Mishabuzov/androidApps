package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.network.events.delete_event.DeletingEventIsErrorEvent;
import com.fsep.sova.network.events.delete_event.DeletingEventIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionDeleteEvent extends BaseAction<BaseResponseModel> {

    private long mEventId;

    public ActionDeleteEvent(long eventId) {
        mEventId = eventId;
    }

    @Override
    protected Response<BaseResponseModel> makeRequest() throws IOException {
        return getRest().deleteEvent(mEventId).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel> response) {
        EventBus.getDefault().post(new DeletingEventIsSuccessEvent());
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new DeletingEventIsErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mEventId);
    }

    protected ActionDeleteEvent(Parcel in) {
        this.mEventId = in.readLong();
    }

    public static final Creator<ActionDeleteEvent> CREATOR = new Creator<ActionDeleteEvent>() {
        @Override
        public ActionDeleteEvent createFromParcel(Parcel source) {
            return new ActionDeleteEvent(source);
        }

        @Override
        public ActionDeleteEvent[] newArray(int size) {
            return new ActionDeleteEvent[size];
        }
    };
}
