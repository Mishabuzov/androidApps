package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Respond;
import com.fsep.sova.models.ResponseOnTask;
import com.fsep.sova.network.events.add_response.AddingResponseIsSuccessEvent;
import com.fsep.sova.network.events.add_to_favourites.AddingToFavouritesErrorEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionAddResponse extends BaseAction<BaseResponseModel<ResponseOnTask>> {
    private Respond mRespond;

    public ActionAddResponse(Respond respond) {
        mRespond = respond;
    }

    protected ActionAddResponse(Parcel in) {
        mRespond = in.readParcelable(Respond.class.getClassLoader());
    }

    @Override
    protected Response<BaseResponseModel<ResponseOnTask>> makeRequest() throws IOException {
        return getRest().addResponse(mRespond).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<ResponseOnTask>> response) {
        //   PrefUtils.saveResponseId(App.context, response.body().getData().getId());
        EventBus.getDefault().post(new AddingResponseIsSuccessEvent(response.body().getData()));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new AddingToFavouritesErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActionAddResponse> CREATOR = new Creator<ActionAddResponse>() {
        @Override
        public ActionAddResponse createFromParcel(Parcel source) {
            return new ActionAddResponse(source);
        }

        @Override
        public ActionAddResponse[] newArray(int size) {
            return new ActionAddResponse[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mRespond, flags);
    }
}
