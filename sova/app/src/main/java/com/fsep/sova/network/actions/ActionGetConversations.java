package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Conversation;
import com.fsep.sova.network.events.get_conversations.GettingConversationsErrorEvent;
import com.fsep.sova.network.events.get_conversations.GettingConversationsIsEmptyEvent;
import com.fsep.sova.network.events.get_conversations.GettingConversationsIsSuccessEvent;
import com.fsep.sova.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class ActionGetConversations extends BaseAction<BaseResponseModel<List<Conversation>>> {

    private long mTaskId;
    private String mLabel;
    private int mCount;
    private int mFrom;

    public ActionGetConversations(Builder builder) {
        mTaskId = builder.taskId;
        mLabel = builder.label;
        mCount = builder.count;
        mFrom = builder.from;
    }

    protected ActionGetConversations(Parcel in) {
        mTaskId = in.readLong();
        mLabel = in.readString();
        mCount = in.readInt();
        mFrom = in.readInt();
    }

    @Override
    protected Response<BaseResponseModel<List<Conversation>>> makeRequest() throws IOException {
        return getRest().getConversations(mTaskId, createQueryMap()).execute();
    }

    private Map<String, Object> createQueryMap() {
        Map<String, Object> options = new HashMap<>();
        if (mLabel != null && !mLabel.isEmpty()) {
            options.put("label", mLabel);
        }
        if (mCount != 0) {
            options.put("count", mCount);
        } else {
            options.put("count", Constants.DEFAULT_COUNT_VALUE);
        }
        if (mFrom != 0) {
            options.put("from", mFrom);
        }
        return options;
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<List<Conversation>>> response) {
        List<Conversation> conversations = response.body().getData();
        if (conversations.isEmpty()) {
            EventBus.getDefault().post(new GettingConversationsIsEmptyEvent());
        } else {
            EventBus.getDefault().post(new GettingConversationsIsSuccessEvent(conversations));
        }
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingConversationsErrorEvent(response.code(), response.message()));
    }

    public static final Creator<ActionGetConversations> CREATOR = new Creator<ActionGetConversations>() {
        @Override
        public ActionGetConversations createFromParcel(Parcel source) {
            return new ActionGetConversations(source);
        }

        @Override
        public ActionGetConversations[] newArray(int size) {
            return new ActionGetConversations[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mTaskId);
        dest.writeString(mLabel);
        dest.writeInt(mCount);
        dest.writeInt(mFrom);
    }

    public static class Builder {
        private long taskId;
        private String label;
        private int count;
        private int from;

        public Builder(long taskId) {
            this.taskId = taskId;
        }

        public Builder label(String val) {
            label = val;
            return this;
        }

        public Builder count(int val) {
            count = val;
            return this;
        }

        public Builder from(int val) {
            from = val;
            return this;
        }

        public ActionGetConversations build() {
            return new ActionGetConversations(this);
        }
    }
}
