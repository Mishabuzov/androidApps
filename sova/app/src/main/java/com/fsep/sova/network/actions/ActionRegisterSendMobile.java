package com.fsep.sova.network.actions;

import android.os.Parcel;
import android.os.Parcelable;

import com.fsep.sova.network.events.registration.registration_send_mobile.RegisterSendingMobileErrorEvent;
import com.fsep.sova.network.events.registration.registration_send_mobile.RegisterSendingMobileIsSuccessEvent;
import com.fsep.sova.utils.PrefUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionRegisterSendMobile extends BaseAction {

    private String mMobileForConfirm;

    public ActionRegisterSendMobile(String mobileForConfirm) {
        mMobileForConfirm = mobileForConfirm;
    }

    public ActionRegisterSendMobile(Parcel in) {
        mMobileForConfirm = in.readString();
    }

    @Override
    protected Response makeRequest() throws IOException {
        return getRest().confirmPhone(mMobileForConfirm).execute();
    }

    @Override
    protected void onResponseSuccess(Response response) {
        PrefUtils.saveTempAuthToken(context, response.headers().get("tempauth-token"));
        EventBus.getDefault().post(new RegisterSendingMobileIsSuccessEvent());
    }

    @Override
    protected void onHttpError(Response response) {
        EventBus.getDefault().post(new RegisterSendingMobileErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<ActionRegisterSendMobile> CREATOR = new Parcelable.Creator<ActionRegisterSendMobile>() {

        @Override
        public ActionRegisterSendMobile createFromParcel(Parcel in) {
            return new ActionRegisterSendMobile(in);
        }

        @Override
        public ActionRegisterSendMobile[] newArray(int size) {
            return new ActionRegisterSendMobile[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mMobileForConfirm);
    }
}
