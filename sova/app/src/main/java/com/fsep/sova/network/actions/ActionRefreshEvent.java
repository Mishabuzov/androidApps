package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Event;
import com.fsep.sova.models.PostEvent;
import com.fsep.sova.network.events.refresh_event.RefreshingEventErrorEvent;
import com.fsep.sova.network.events.refresh_event.RefreshingEventIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionRefreshEvent extends BaseAction<BaseResponseModel<Event>> {

    private long mEventId;
    private PostEvent mEvent;

    public ActionRefreshEvent(long eventId, PostEvent event) {
        mEventId = eventId;
        mEvent = event;
    }

    @Override
    protected Response<BaseResponseModel<Event>> makeRequest() throws IOException {
        return getRest().refreshEvent(mEventId, mEvent).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<Event>> response) {
        EventBus.getDefault().post(new RefreshingEventIsSuccessEvent(response.body().getData()));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new RefreshingEventErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mEventId);
        dest.writeParcelable(this.mEvent, flags);
    }

    protected ActionRefreshEvent(Parcel in) {
        this.mEventId = in.readLong();
        this.mEvent = in.readParcelable(Event.class.getClassLoader());
    }

    public static final Creator<ActionRefreshEvent> CREATOR = new Creator<ActionRefreshEvent>() {
        @Override
        public ActionRefreshEvent createFromParcel(Parcel source) {
            return new ActionRefreshEvent(source);
        }

        @Override
        public ActionRefreshEvent[] newArray(int size) {
            return new ActionRefreshEvent[size];
        }
    };
}
