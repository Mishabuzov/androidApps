package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.network.events.get_connection.GettingConnectionErrorEvent;
import com.fsep.sova.network.events.get_connection.GettingConnectionIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionGetConnection extends BaseAction<BaseResponseModel<String>> {

    public ActionGetConnection() {
    }

    protected ActionGetConnection(Parcel in) {
    }

    @Override
    protected Response<BaseResponseModel<String>> makeRequest() throws IOException {
        return getRest().getConnectionAddress().execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<String>> response) {
        EventBus.getDefault().post(new GettingConnectionIsSuccessEvent(response.body().getData()));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingConnectionErrorEvent(response.code(), response.message()));
    }

    public static final Creator<ActionGetConnection> CREATOR = new Creator<ActionGetConnection>() {
        @Override
        public ActionGetConnection createFromParcel(Parcel source) {
            return new ActionGetConnection(source);
        }

        @Override
        public ActionGetConnection[] newArray(int size) {
            return new ActionGetConnection[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
