package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Event;
import com.fsep.sova.models.PostOnParticipantEvent;
import com.fsep.sova.network.events.take_participation_in_the_event.TakingParticipationInTheEventErrorEvent;
import com.fsep.sova.network.events.take_participation_in_the_event.TakingParticipationInTheEventIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionTakeParticipationInTheEvent extends BaseAction<BaseResponseModel<Event>> {

    private PostOnParticipantEvent mEvent;

    public ActionTakeParticipationInTheEvent(PostOnParticipantEvent event) {
        mEvent = event;
    }

    @Override
    protected Response<BaseResponseModel<Event>> makeRequest() throws IOException {
        return getRest().takeParticipationInTheEvent(mEvent).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<Event>> response) {
        EventBus.getDefault().post(new TakingParticipationInTheEventIsSuccessEvent(response.body().getData()));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new TakingParticipationInTheEventErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mEvent, flags);
    }

    protected ActionTakeParticipationInTheEvent(Parcel in) {
        this.mEvent = in.readParcelable(Event.class.getClassLoader());
    }

    public static final Creator<ActionTakeParticipationInTheEvent> CREATOR = new Creator<ActionTakeParticipationInTheEvent>() {
        @Override
        public ActionTakeParticipationInTheEvent createFromParcel(Parcel source) {
            return new ActionTakeParticipationInTheEvent(source);
        }

        @Override
        public ActionTakeParticipationInTheEvent[] newArray(int size) {
            return new ActionTakeParticipationInTheEvent[size];
        }
    };
}
