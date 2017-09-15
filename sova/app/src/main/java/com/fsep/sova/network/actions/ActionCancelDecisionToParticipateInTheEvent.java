package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.network.events.cancel_decision_to_participate_in_the_event.CancelingDecisionToParticipateInTheEventErrorEvent;
import com.fsep.sova.network.events.cancel_decision_to_participate_in_the_event.CancelingDecisionToParticipateInTheEventIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionCancelDecisionToParticipateInTheEvent extends BaseAction<BaseResponseModel> {

    private long mEventId;

    public ActionCancelDecisionToParticipateInTheEvent(long eventId) {
        mEventId = eventId;
    }

    @Override
    protected Response<BaseResponseModel> makeRequest() throws IOException {
        return getRest().cancelDecisionToParticipateInTheEvent(mEventId).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel> response) {
        EventBus.getDefault().post(new CancelingDecisionToParticipateInTheEventIsSuccessEvent());
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new CancelingDecisionToParticipateInTheEventErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mEventId);
    }

    protected ActionCancelDecisionToParticipateInTheEvent(Parcel in) {
        this.mEventId = in.readLong();
    }

    public static final Creator<ActionCancelDecisionToParticipateInTheEvent> CREATOR = new Creator<ActionCancelDecisionToParticipateInTheEvent>() {
        @Override
        public ActionCancelDecisionToParticipateInTheEvent createFromParcel(Parcel source) {
            return new ActionCancelDecisionToParticipateInTheEvent(source);
        }

        @Override
        public ActionCancelDecisionToParticipateInTheEvent[] newArray(int size) {
            return new ActionCancelDecisionToParticipateInTheEvent[size];
        }
    };
}
