package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Conversation;
import com.fsep.sova.models.ConversationLabelSendingModel;
import com.fsep.sova.network.events.get_conversations.GettingConversationsErrorEvent;
import com.fsep.sova.network.events.put_label_for_conversation.PuttingLabelForConversationIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionPutLabelForConversation extends BaseAction<BaseResponseModel<Conversation>> {

    private long mTaskId;
    private long mMessageId;
    private ConversationLabelSendingModel mLabelSendingModel;

    public ActionPutLabelForConversation() {
    }

    public ActionPutLabelForConversation(long taskId, long messageId, ConversationLabelSendingModel labelSendingModel) {
        mTaskId = taskId;
        mMessageId = messageId;
        mLabelSendingModel = labelSendingModel;
    }

    protected ActionPutLabelForConversation(Parcel in) {
        mTaskId = in.readLong();
        mMessageId = in.readLong();
        mLabelSendingModel = in.readParcelable(ConversationLabelSendingModel.class.getClassLoader());
    }

    @Override
    protected Response<BaseResponseModel<Conversation>> makeRequest() throws IOException {
        return getRest().putLabelForConversation(mTaskId, mMessageId, mLabelSendingModel).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<Conversation>> response) {
        EventBus.getDefault().post(new PuttingLabelForConversationIsSuccessEvent(response.body().getData()));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingConversationsErrorEvent(response.code(), response.message()));
    }

    public static final Creator<ActionPutLabelForConversation> CREATOR = new Creator<ActionPutLabelForConversation>() {
        @Override
        public ActionPutLabelForConversation createFromParcel(Parcel source) {
            return new ActionPutLabelForConversation(source);
        }

        @Override
        public ActionPutLabelForConversation[] newArray(int size) {
            return new ActionPutLabelForConversation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mTaskId);
        dest.writeLong(mMessageId);
        dest.writeParcelable(mLabelSendingModel, flags);
    }
}
