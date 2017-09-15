package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Video;
import com.fsep.sova.network.events.get_all_videos_from_all_converstions.GettingVideosFromAllConversationsErrorEvent;
import com.fsep.sova.network.events.get_all_videos_from_all_converstions.GettingVideosFromAllConversationsIsEmptyEvent;
import com.fsep.sova.network.events.get_all_videos_from_all_converstions.GettingVideosFromAllConversationsIsSuccessEvent;
import com.fsep.sova.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class ActionGetAllVideosFromAllConversations extends BaseAction<BaseResponseModel<List<Video>>> {

    private long mTaskId;
    private int mCount;
    private int mFrom;

    public ActionGetAllVideosFromAllConversations(Builder builder) {
        mTaskId = builder.taskId;
        mCount = builder.count;
        mFrom = builder.from;
    }

    protected ActionGetAllVideosFromAllConversations(Parcel in) {
        mTaskId = in.readLong();
        mCount = in.readInt();
        mFrom = in.readInt();
    }

    @Override
    protected Response<BaseResponseModel<List<Video>>> makeRequest() throws IOException {
        return getRest().getAllVideosFromAllConversations(mTaskId, createQueryMap()).execute();
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
    protected void onResponseSuccess(Response<BaseResponseModel<List<Video>>> response) {
        List<Video> videos = response.body().getData();
        if (videos.isEmpty()) {
            EventBus.getDefault().post(new GettingVideosFromAllConversationsIsEmptyEvent());
        } else {
            EventBus.getDefault().post(new GettingVideosFromAllConversationsIsSuccessEvent(videos));
        }
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingVideosFromAllConversationsErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActionGetAllVideosFromAllConversations> CREATOR = new Creator<ActionGetAllVideosFromAllConversations>() {
        @Override
        public ActionGetAllVideosFromAllConversations createFromParcel(Parcel source) {
            return new ActionGetAllVideosFromAllConversations(source);
        }

        @Override
        public ActionGetAllVideosFromAllConversations[] newArray(int size) {
            return new ActionGetAllVideosFromAllConversations[size];
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

        public ActionGetAllVideosFromAllConversations build() {
            return new ActionGetAllVideosFromAllConversations(this);
        }
    }
}
