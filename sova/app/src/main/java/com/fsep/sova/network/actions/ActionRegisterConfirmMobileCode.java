package com.fsep.sova.network.actions;

import android.os.Parcel;
import android.os.Parcelable;

import com.fsep.sova.App;
import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.network.events.registration.registration_confirm_code.RegisterConfirmingMobileCodeErrorEvent;
import com.fsep.sova.network.events.registration.registration_confirm_code.RegisterConfirmingMobileCodeIsSuccessEvent;
import com.fsep.sova.utils.PrefUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionRegisterConfirmMobileCode extends BaseAction<BaseResponseModel> {

    private String mConfirmCode;
    private String mTempAuthToken;

    public ActionRegisterConfirmMobileCode(String confirmCode) {
        mTempAuthToken = PrefUtils.getTempAuthToken(App.context);
        mConfirmCode = confirmCode;
    }

    public ActionRegisterConfirmMobileCode(Parcel in) {
        mConfirmCode = in.readString();
        mTempAuthToken = in.readString();
    }

    @Override
    protected Response<BaseResponseModel> makeRequest() throws IOException {
        return getRest().confirmCode(mConfirmCode, mTempAuthToken).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel> response) {
        EventBus.getDefault().post(new RegisterConfirmingMobileCodeIsSuccessEvent());
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new RegisterConfirmingMobileCodeErrorEvent(response.code(), response.message()));
    }


    public static final Parcelable.Creator<ActionRegisterConfirmMobileCode> CREATOR = new Parcelable.Creator<ActionRegisterConfirmMobileCode>() {

        @Override
        public ActionRegisterConfirmMobileCode createFromParcel(Parcel in) {
            return new ActionRegisterConfirmMobileCode(in);
        }

        @Override
        public ActionRegisterConfirmMobileCode[] newArray(int size) {
            return new ActionRegisterConfirmMobileCode[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(mConfirmCode);
        dest.writeString(mTempAuthToken);
    }
}
