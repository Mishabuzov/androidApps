package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.ResponseOnTask;
import com.fsep.sova.network.events.get_responses.GettingResponsesIsEmptyEvent;
import com.fsep.sova.network.events.find_users.FindingUsersErrorEvent;
import com.fsep.sova.network.events.get_responses.GettingResponsesIsSuccessEvent;
import com.fsep.sova.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class ActionGetResponses extends BaseAction<BaseResponseModel<List<ResponseOnTask>>> {
    private long mTaskId;
    private int mCount;
    private int mFrom;

    public ActionGetResponses(Builder builder){
        mTaskId = builder.taskId;
        mCount = builder.count;
        mFrom = builder.from;
    }

    protected ActionGetResponses(Parcel in){
        mTaskId = in.readLong();
        mCount = in.readInt();
        mFrom = in.readInt();
    }

    @Override
    protected Response<BaseResponseModel<List<ResponseOnTask>>> makeRequest() throws IOException {
        return getRest().getResponsesOnTask(mTaskId, createQueryMap()).execute();
    }

    private Map<String, Object> createQueryMap(){
        Map<String, Object> options = new HashMap<>();
        if(mCount!=0){
            options.put("count", mCount);
        } else {
            options.put("count", Constants.DEFAULT_COUNT_VALUE);
        }
        if(mFrom!=0){
            options.put("from", mFrom);
        }
        return options;
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<List<ResponseOnTask>>> response) {
        List<ResponseOnTask> responses = response.body().getData();
        if (responses.isEmpty()) {
            EventBus.getDefault().post(new GettingResponsesIsEmptyEvent());
        } else {
            EventBus.getDefault().post(new GettingResponsesIsSuccessEvent(response.body().getData()));
        }
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new FindingUsersErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActionGetResponses> CREATOR = new Creator<ActionGetResponses>() {
        @Override
        public ActionGetResponses createFromParcel(Parcel source) {
            return new ActionGetResponses(source);
        }

        @Override
        public ActionGetResponses[] newArray(int size) {
            return new ActionGetResponses[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mTaskId);
        dest.writeInt(mCount);
        dest.writeInt(mFrom);
    }

    public static class Builder{
        private long taskId;
        private int count;
        private int from;

        public Builder(long taskId) {
            this.taskId = taskId;
        }

        public Builder count(int val){
            count = val;
            return this;
        }

        public Builder from(int val){
            from = val;
            return this;
        }

        public ActionGetResponses build(){
            return new ActionGetResponses(this);
        }
    }
}
