package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Resume;
import com.fsep.sova.network.events.update_profile.UpdatingProfileErrorEvent;
import com.fsep.sova.network.events.update_profile.UpdatingProfileIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionUpdateProfile extends BaseAction<BaseResponseModel<Resume>> {

    private Resume mProfile;

    @Override
    protected Response<BaseResponseModel<Resume>> makeRequest() throws IOException {
        return getRest().updateUserProfile(mProfile).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<Resume>> response) {
        EventBus.getDefault().post(new UpdatingProfileIsSuccessEvent(response.body().getData()));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new UpdatingProfileErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mProfile, flags);
    }

    public ActionUpdateProfile() {
    }

    public ActionUpdateProfile(Resume profile) {
        mProfile = profile;
    }

    protected ActionUpdateProfile(Parcel in) {
        mProfile = in.readParcelable(Resume.class.getClassLoader());
    }

    public static final Creator<ActionUpdateProfile> CREATOR = new Creator<ActionUpdateProfile>() {
        @Override
        public ActionUpdateProfile createFromParcel(Parcel source) {
            return new ActionUpdateProfile(source);
        }

        @Override
        public ActionUpdateProfile[] newArray(int size) {
            return new ActionUpdateProfile[size];
        }
    };
}
