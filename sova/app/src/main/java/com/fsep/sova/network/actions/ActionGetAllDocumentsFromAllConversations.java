package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Document;
import com.fsep.sova.network.events.get_all_documents_from_all_conversations.GettingDocumentsFromAllConversationsErrorEvent;
import com.fsep.sova.network.events.get_all_documents_from_all_conversations.GettingDocumentsFromAllConversationsIsEmptyEvent;
import com.fsep.sova.network.events.get_all_documents_from_all_conversations.GettingDocumentsFromAllConversationsIsSuccessEvent;
import com.fsep.sova.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class ActionGetAllDocumentsFromAllConversations extends BaseAction<BaseResponseModel<List<Document>>> {

    private long mTaskId;
    private int mCount;
    private int mFrom;

    public ActionGetAllDocumentsFromAllConversations(Builder builder) {
        mTaskId = builder.taskId;
        mCount = builder.count;
        mFrom = builder.from;
    }

    protected ActionGetAllDocumentsFromAllConversations(Parcel in) {
        mTaskId = in.readLong();
        mCount = in.readInt();
        mFrom = in.readInt();
    }

    @Override
    protected Response<BaseResponseModel<List<Document>>> makeRequest() throws IOException {
        return getRest().getAllDocumentsFromAllConversations(mTaskId, createQueryMap()).execute();
    }

    private Map<String, Object> createQueryMap() {
        Map<String, Object> options = new HashMap<>();
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
    protected void onResponseSuccess(Response<BaseResponseModel<List<Document>>> response) {
        List<Document> documents = response.body().getData();
        if (documents.isEmpty()) {
            EventBus.getDefault().post(new GettingDocumentsFromAllConversationsIsEmptyEvent());
        } else {
            EventBus.getDefault().post(new GettingDocumentsFromAllConversationsIsSuccessEvent(documents));
        }
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingDocumentsFromAllConversationsErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActionGetAllDocumentsFromAllConversations> CREATOR = new Creator<ActionGetAllDocumentsFromAllConversations>() {
        @Override
        public ActionGetAllDocumentsFromAllConversations createFromParcel(Parcel source) {
            return new ActionGetAllDocumentsFromAllConversations(source);
        }

        @Override
        public ActionGetAllDocumentsFromAllConversations[] newArray(int size) {
            return new ActionGetAllDocumentsFromAllConversations[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mTaskId);
        dest.writeInt(mCount);
        dest.writeInt(mFrom);
    }

    public static class Builder {
        private long taskId;
        private int count;
        private int from;

        public Builder(long taskId) {
            this.taskId = taskId;
        }

        public Builder count(int val) {
            count = val;
            return this;
        }

        public Builder from(int val) {
            from = val;
            return this;
        }

        public ActionGetAllDocumentsFromAllConversations build() {
            return new ActionGetAllDocumentsFromAllConversations(this);
        }
    }
}
