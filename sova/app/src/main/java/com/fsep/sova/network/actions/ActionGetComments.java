package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Comment;
import com.fsep.sova.network.events.getcomments.GettingCommentsErrorEvent;
import com.fsep.sova.network.events.getcomments.GettingCommentsIsEmptyEvent;
import com.fsep.sova.network.events.getcomments.GettingCommentsIsSuccessEvent;
import com.fsep.sova.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class ActionGetComments extends BaseAction<BaseResponseModel<List<Comment>>> {
    private long mTaskId;
    private int mCount;
    private int mFrom;

    public ActionGetComments(Builder builder) {
        mTaskId = builder.taskId;
        mCount = builder.count;
        mFrom = builder.from;
    }

    protected ActionGetComments(Parcel in) {
        mTaskId = in.readLong();
        mCount = in.readInt();
        mFrom = in.readInt();
    }

    public static final Creator<ActionGetComments> CREATOR = new Creator<ActionGetComments>() {
        @Override
        public ActionGetComments createFromParcel(Parcel in) {
            return new ActionGetComments(in);
        }

        @Override
        public ActionGetComments[] newArray(int size) {
            return new ActionGetComments[size];
        }
    };

    @Override
    protected Response<BaseResponseModel<List<Comment>>> makeRequest() throws IOException {
        return getRest().getAllComments(mTaskId, createQueryMap()).execute();
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
    protected void onResponseSuccess(Response<BaseResponseModel<List<Comment>>> response) {
        List<Comment> comments = response.body().getData();
        if (comments.size() > 0) {
            EventBus.getDefault().post(new GettingCommentsIsSuccessEvent(comments));
        } else {
            EventBus.getDefault().post(new GettingCommentsIsEmptyEvent());
        }
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingCommentsErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mTaskId);
        parcel.writeLong(mCount);
        parcel.writeLong(mFrom);
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

        public ActionGetComments build() {
            return new ActionGetComments(this);
        }
    }
}

