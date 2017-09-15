package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.ConversationInfo;
import com.fsep.sova.network.events.get_conversation_info.GettingConversationInfoErrorEvent;
import com.fsep.sova.network.events.get_conversation_info.GettingConversationInfoIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionGetConversationInfo extends BaseAction<BaseResponseModel<ConversationInfo>> {

    private long mTaskId;

    public ActionGetConversationInfo(long taskId) {
        mTaskId = taskId;
    }

    protected ActionGetConversationInfo(Parcel in) {
        mTaskId = in.readLong();
    }

    @Override
    protected Response<BaseResponseModel<ConversationInfo>> makeRequest() throws IOException {
        return getRest().getConversationDialogInfo(mTaskId).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<ConversationInfo>> response) {
        EventBus.getDefault().post(new GettingConversationInfoIsSuccessEvent(response.body().getData()));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingConversationInfoErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActionGetConversationInfo> CREATOR = new Creator<ActionGetConversationInfo>() {
        @Override
        public ActionGetConversationInfo createFromParcel(Parcel source) {
            return new ActionGetConversationInfo(source);
        }

        @Override
        public ActionGetConversationInfo[] newArray(int size) {
            return new ActionGetConversationInfo[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mTaskId);
    }
}
