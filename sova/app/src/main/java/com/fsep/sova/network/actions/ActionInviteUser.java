package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.InvitationSendingModel;
import com.fsep.sova.network.events.invite_user.InvitingUserErrorEvent;
import com.fsep.sova.network.events.invite_user.InvitingUserIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionInviteUser extends BaseAction<BaseResponseModel> {

    private long mUserId;
    private InvitationSendingModel mInvitation;

    public ActionInviteUser(long userId, InvitationSendingModel invitation) {
        mUserId = userId;
        mInvitation = invitation;
    }

    protected ActionInviteUser(Parcel in) {
        mUserId = in.readLong();
        mInvitation = in.readParcelable(InvitationSendingModel.class.getClassLoader());
    }

    @Override
    protected Response<BaseResponseModel> makeRequest() throws IOException {
        return getRest().inviteUser(mUserId, mInvitation).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel> response) {
        EventBus.getDefault().post(new InvitingUserIsSuccessEvent());
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new InvitingUserErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mUserId);
        dest.writeParcelable(mInvitation, flags);
    }

    public static final Creator<ActionInviteUser> CREATOR = new Creator<ActionInviteUser>() {
        @Override
        public ActionInviteUser createFromParcel(Parcel source) {
            return new ActionInviteUser(source);
        }

        @Override
        public ActionInviteUser[] newArray(int size) {
            return new ActionInviteUser[size];
        }
    };
}
