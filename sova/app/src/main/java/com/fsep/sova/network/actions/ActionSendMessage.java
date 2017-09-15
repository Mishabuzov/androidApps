package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Conversation;
import com.fsep.sova.network.events.send_conversation.SendingConversationErrorEvent;
import com.fsep.sova.network.events.send_conversation.SendingConversationIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionSendMessage extends BaseAction<BaseResponseModel<Conversation>> {

    private long mTaskId;
    private Conversation mNewMessage;

    public ActionSendMessage(long taskId, Conversation newMessage) {
        mTaskId = taskId;
        mNewMessage = newMessage;
    }

    private ActionSendMessage(Parcel in) {
        mTaskId = in.readLong();
        mNewMessage = in.readParcelable(Conversation.class.getClassLoader());
    }

    @Override
    protected Response<BaseResponseModel<Conversation>> makeRequest() throws IOException {
        return getRest().sendConversation(mTaskId, mNewMessage).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<Conversation>> response) {
        EventBus.getDefault().post(new SendingConversationIsSuccessEvent(response.body().getData()));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new SendingConversationErrorEvent(response.code(), response.message()));
    }

    public static final Creator<ActionSendMessage> CREATOR = new Creator<ActionSendMessage>() {
        @Override
        public ActionSendMessage createFromParcel(Parcel source) {
            return new ActionSendMessage(source);
        }

        @Override
        public ActionSendMessage[] newArray(int size) {
            return new ActionSendMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mTaskId);
        dest.writeParcelable(mNewMessage, flags);
    }
}
