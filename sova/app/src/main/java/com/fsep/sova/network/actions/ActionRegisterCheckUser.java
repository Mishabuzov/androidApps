package com.fsep.sova.network.actions;

import android.os.Parcel;
import android.os.Parcelable;

import com.fsep.sova.App;
import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.network.events.registration.registration_check_user.RegisterCheckUserErrorEvent;
import com.fsep.sova.network.events.registration.registration_check_user.RegisterCheckUserIsSuccess;
import com.fsep.sova.utils.PrefUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Response;

public class ActionRegisterCheckUser extends BaseAction<BaseResponseModel> {

    private String mCheckPhone;
    private String mCheckNick;

    public ActionRegisterCheckUser(String checkNick) {
        mCheckPhone = PrefUtils.getUserPhone(App.context);
        mCheckNick = checkNick;
    }

    public ActionRegisterCheckUser(Parcel in) {
        mCheckPhone = in.readString();
        mCheckNick = in.readString();
    }

    private Map<String, Object> createQueryMap() {
        Map<String, Object> options = new HashMap<>();
        if (!mCheckPhone.equals("")) {
            options.put("check_phone", mCheckPhone);
        }
        if (!mCheckNick.equals("")) {
            options.put("check_nickname", mCheckNick);
        }
        return options;
    }


    @Override
    protected Response<BaseResponseModel> makeRequest() throws IOException {
        return getRest().checkRegistration(createQueryMap()).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel> response) {
        EventBus.getDefault().post(new RegisterCheckUserIsSuccess());
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new RegisterCheckUserErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<ActionRegisterCheckUser> CREATOR = new Parcelable.Creator<ActionRegisterCheckUser>() {

        @Override
        public ActionRegisterCheckUser createFromParcel(Parcel in) {
            return new ActionRegisterCheckUser(in);
        }

        @Override
        public ActionRegisterCheckUser[] newArray(int size) {
            return new ActionRegisterCheckUser[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mCheckPhone);
        parcel.writeString(mCheckNick);
    }
}
