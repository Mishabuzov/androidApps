package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.network.events.decline_response.DeclineResponseErrorEvent;
import com.fsep.sova.network.events.decline_response.DeclineResponseIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionDeclineResponse extends BaseAction<BaseResponseModel> {

    private long mResponseId;

    public ActionDeclineResponse(long responseId) {
        this.mResponseId = responseId;
    }

    protected ActionDeclineResponse(Parcel in) {
        mResponseId = in.readLong();
    }

    @Override
    protected Response<BaseResponseModel> makeRequest() throws IOException {
        return getRest().declineResponse(mResponseId).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel> response) {
        EventBus.getDefault().post(new DeclineResponseIsSuccessEvent());
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new DeclineResponseErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActionDeclineResponse> CREATOR = new Creator<ActionDeclineResponse>() {
        @Override
        public ActionDeclineResponse createFromParcel(Parcel source) {
            return new ActionDeclineResponse(source);
        }

        @Override
        public ActionDeclineResponse[] newArray(int size) {
            return new ActionDeclineResponse[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mResponseId);
    }
}
