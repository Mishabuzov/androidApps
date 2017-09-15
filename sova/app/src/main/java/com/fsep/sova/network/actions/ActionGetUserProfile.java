package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Resume;
import com.fsep.sova.network.events.get_user_profile.GettingUserProfileErrorEvent;
import com.fsep.sova.network.events.get_user_profile.GettingUserProfileIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionGetUserProfile extends BaseAction<BaseResponseModel<Resume>> {

    private long mUserId;

    public ActionGetUserProfile(long userId) {
        mUserId = userId;
    }

    @Override
    protected Response<BaseResponseModel<Resume>> makeRequest() throws IOException {
        return getRest().getUserProfile(mUserId).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<Resume>> response) {
        EventBus.getDefault().post(new GettingUserProfileIsSuccessEvent(response.body().getData()));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingUserProfileErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mUserId);
    }

    protected ActionGetUserProfile(Parcel in) {
        this.mUserId = in.readLong();
    }

    public static final Creator<ActionGetUserProfile> CREATOR = new Creator<ActionGetUserProfile>() {
        @Override
        public ActionGetUserProfile createFromParcel(Parcel source) {
            return new ActionGetUserProfile(source);
        }

        @Override
        public ActionGetUserProfile[] newArray(int size) {
            return new ActionGetUserProfile[size];
        }
    };
}
