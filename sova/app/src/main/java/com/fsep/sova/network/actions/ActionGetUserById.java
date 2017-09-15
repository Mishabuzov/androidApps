package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.UserInfo;
import com.fsep.sova.network.events.get_sent_invitations.GettingSentInvitationsErrorEvent;
import com.fsep.sova.network.events.get_user_by_id.GettingUserByIdIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionGetUserById extends BaseAction<BaseResponseModel<UserInfo>> {

    private long mUserId;

    public ActionGetUserById(long userId) {
        mUserId = userId;
    }

    protected ActionGetUserById(Parcel in) {
        mUserId = in.readLong();
    }

    @Override
    protected Response<BaseResponseModel<UserInfo>> makeRequest() throws IOException {
        return getRest().getUserById(mUserId).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<UserInfo>> response) {
        EventBus.getDefault().post(new GettingUserByIdIsSuccessEvent(response.body().getData()));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingSentInvitationsErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActionGetUserById> CREATOR = new Creator<ActionGetUserById>() {
        @Override
        public ActionGetUserById createFromParcel(Parcel source) {
            return new ActionGetUserById(source);
        }

        @Override
        public ActionGetUserById[] newArray(int size) {
            return new ActionGetUserById[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mUserId);
    }
}
