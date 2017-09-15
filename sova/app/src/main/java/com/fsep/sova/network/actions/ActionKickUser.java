package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.network.events.kick_user.KickingUserErrorEvent;
import com.fsep.sova.network.events.kick_user.KickingUserIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionKickUser extends BaseAction<BaseResponseModel> {

    private long mTaskId;
    private long mUserId;

    public ActionKickUser(long userId, long taskId) {
        mUserId = userId;
        mTaskId = taskId;
    }

    protected ActionKickUser(Parcel in) {
        mTaskId = in.readLong();
        mUserId = in.readLong();
    }

    @Override
    protected Response<BaseResponseModel> makeRequest() throws IOException {
        return getRest().kickUser(mTaskId, mUserId).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel> response) {
        EventBus.getDefault().post(new KickingUserIsSuccessEvent());
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new KickingUserErrorEvent(response.code(), response.message()));
    }

    public static final Creator<ActionKickUser> CREATOR = new Creator<ActionKickUser>() {
        @Override
        public ActionKickUser createFromParcel(Parcel source) {
            return new ActionKickUser(source);
        }

        @Override
        public ActionKickUser[] newArray(int size) {
            return new ActionKickUser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mUserId);
        dest.writeLong(mTaskId);
    }
}
