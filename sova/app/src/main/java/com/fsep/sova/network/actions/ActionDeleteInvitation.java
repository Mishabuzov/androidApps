package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.network.events.delete_invitation.DeletingInvitationErrorEvent;
import com.fsep.sova.network.events.delete_invitation.DeletingInvitationIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionDeleteInvitation extends BaseAction {
    private long mInvitationId;

    public ActionDeleteInvitation(long invitationId) {
        mInvitationId = invitationId;
    }

    protected ActionDeleteInvitation(Parcel in){
        mInvitationId = in.readLong();
    }

    @Override
    protected Response makeRequest() throws IOException {
        return getRest().deleteInvitation(mInvitationId).execute();
    }

    @Override
    protected void onResponseSuccess(Response response) {
        EventBus.getDefault().post(new DeletingInvitationIsSuccessEvent());
    }

    @Override
    protected void onHttpError(Response response) {
        EventBus.getDefault().post(new DeletingInvitationErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActionDeleteInvitation> CREATOR = new Creator<ActionDeleteInvitation>() {
        @Override
        public ActionDeleteInvitation createFromParcel(Parcel source) {
            return new ActionDeleteInvitation(source);
        }

        @Override
        public ActionDeleteInvitation[] newArray(int size) {
            return new ActionDeleteInvitation[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mInvitationId);
    }
}
