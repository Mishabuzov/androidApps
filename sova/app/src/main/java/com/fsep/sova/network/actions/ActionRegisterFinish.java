package com.fsep.sova.network.actions;

import android.os.Parcel;
import android.os.Parcelable;

import com.fsep.sova.App;
import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.UserInfo;
import com.fsep.sova.network.events.registration.registration_user.FinishRegisterErrorEvent;
import com.fsep.sova.network.events.registration.registration_user.FinishRegisterIsSuccessEvent;
import com.fsep.sova.utils.PrefUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionRegisterFinish extends BaseAction<BaseResponseModel<UserInfo>> {

    private UserInfo mBody;

    public ActionRegisterFinish(UserInfo body) {
        mBody = body;
    }

    public ActionRegisterFinish(Parcel in) {
        mBody = in.readParcelable(UserInfo.class.getClassLoader());
    }

    @Override
    protected Response<BaseResponseModel<UserInfo>> makeRequest() throws IOException {
        return getRest().registerUser(PrefUtils.getTempAuthToken(App.context), mBody).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<UserInfo>> response) {
        PrefUtils.clearPreferences(context);
        PrefUtils.saveAuthToken(context, response.headers().get("auth-token"));
        PrefUtils.saveUserId(context, response.body().getData().getId());
        EventBus.getDefault().post(new FinishRegisterIsSuccessEvent());
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new FinishRegisterErrorEvent(response.code(), response.message()));
    }

    public static final Parcelable.Creator<ActionRegisterFinish> CREATOR = new Parcelable.Creator<ActionRegisterFinish>() {

        @Override
        public ActionRegisterFinish createFromParcel(Parcel in) {
            return new ActionRegisterFinish(in);
        }

        @Override
        public ActionRegisterFinish[] newArray(int size) {
            return new ActionRegisterFinish[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeParcelable(mBody, i);
    }
}
