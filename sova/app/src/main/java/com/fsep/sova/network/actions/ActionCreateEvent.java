package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Event;
import com.fsep.sova.models.PostEvent;
import com.fsep.sova.network.events.create_new_event.CreatingNewEventIsErrorEvent;
import com.fsep.sova.network.events.create_new_event.CreatingNewEventIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionCreateEvent extends BaseAction<BaseResponseModel<Event>> {

    private PostEvent mEvent;

    public ActionCreateEvent(PostEvent event) {
        mEvent = event;
    }

    @Override
    protected Response<BaseResponseModel<Event>> makeRequest() throws IOException {
        return getRest().createNewEvent(mEvent).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<Event>> response) {
        EventBus.getDefault().post(new CreatingNewEventIsSuccessEvent(response.body().getData()));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new CreatingNewEventIsErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mEvent, flags);
    }

    protected ActionCreateEvent(Parcel in) {
        this.mEvent = in.readParcelable(Event.class.getClassLoader());
    }

    public static final Creator<ActionCreateEvent> CREATOR = new Creator<ActionCreateEvent>() {
        @Override
        public ActionCreateEvent createFromParcel(Parcel source) {
            return new ActionCreateEvent(source);
        }

        @Override
        public ActionCreateEvent[] newArray(int size) {
            return new ActionCreateEvent[size];
        }
    };
}
