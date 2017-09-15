package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Photo;
import com.fsep.sova.network.events.get_all_images_from_all_conversations.GettingImagesFromAllConversationsErrorEvent;
import com.fsep.sova.network.events.get_all_images_from_all_conversations.GettingImagesFromAllConversationsIsEmptyEvent;
import com.fsep.sova.network.events.get_all_images_from_all_conversations.GettingImagesFromAllConversationsIsSuccessEvent;
import com.fsep.sova.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class ActionGetAllImagesFromAllConversations extends BaseAction<BaseResponseModel<List<Photo>>> {

    private long mTaskId;
    private int mCount;
    private int mFrom;

    public ActionGetAllImagesFromAllConversations(Builder builder) {
        mTaskId = builder.taskId;
        mCount = builder.count;
        mFrom = builder.from;
    }

    protected ActionGetAllImagesFromAllConversations(Parcel in) {
        mTaskId = in.readLong();
        mCount = in.readInt();
        mFrom = in.readInt();
    }

    @Override
    protected Response<BaseResponseModel<List<Photo>>> makeRequest() throws IOException {
        return getRest().getAllImagesFromAllConversations(mTaskId, createQueryMap()).execute();
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
    protected void onResponseSuccess(Response<BaseResponseModel<List<Photo>>> response) {
        List<Photo> allImages = response.body().getData();
        if (allImages.isEmpty()) {
            EventBus.getDefault().post(new GettingImagesFromAllConversationsIsEmptyEvent());
        } else {
            EventBus.getDefault().post(new GettingImagesFromAllConversationsIsSuccessEvent(allImages));
        }
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingImagesFromAllConversationsErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActionGetAllImagesFromAllConversations> CREATOR = new Creator<ActionGetAllImagesFromAllConversations>() {
        @Override
        public ActionGetAllImagesFromAllConversations createFromParcel(Parcel source) {
            return new ActionGetAllImagesFromAllConversations(source);
        }

        @Override
        public ActionGetAllImagesFromAllConversations[] newArray(int size) {
            return new ActionGetAllImagesFromAllConversations[size];
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

        public ActionGetAllImagesFromAllConversations build() {
            return new ActionGetAllImagesFromAllConversations(this);
        }
    }
}
