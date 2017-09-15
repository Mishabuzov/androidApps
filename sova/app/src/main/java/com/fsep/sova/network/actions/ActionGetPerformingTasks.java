package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Task;
import com.fsep.sova.network.events.getperformedtasks.GettingPerformingTasksErrorEvent;
import com.fsep.sova.network.events.getperformedtasks.GettingPerformingTasksIsEmptyEvent;
import com.fsep.sova.network.events.getperformedtasks.GettingPerformingTasksIsSuccessEvent;
import com.fsep.sova.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class ActionGetPerformingTasks extends BaseAction<BaseResponseModel<List<Task>>> {

    private int mCount;
    private int mFrom;

    public ActionGetPerformingTasks(Parcel in) {
        mCount = in.readInt();
        mFrom = in.readInt();
    }

    private ActionGetPerformingTasks(Builder builder) {
        mCount=builder.count;
        mFrom=builder.from;
    }

    @Override
    protected Response<BaseResponseModel<List<Task>>> makeRequest() throws IOException {
        return getRest().getPerformingTasks(createQueryMap()).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<List<Task>>> response) {
        List<Task> tasks = response.body().getData();
        if(tasks.size()>0){
            EventBus.getDefault().post(new GettingPerformingTasksIsSuccessEvent(tasks));
        } else {
            EventBus.getDefault().post(new GettingPerformingTasksIsEmptyEvent());
        }
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingPerformingTasksErrorEvent(response.code(), response.message()));
    }

    public static final Creator<ActionGetPerformingTasks> CREATOR = new Creator<ActionGetPerformingTasks>() {
        @Override
        public ActionGetPerformingTasks createFromParcel(Parcel in) {
            return new ActionGetPerformingTasks(in);
        }

        @Override
        public ActionGetPerformingTasks[] newArray(int size) {
            return new ActionGetPerformingTasks[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mCount);
        dest.writeInt(mFrom);
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


    public static class Builder {
        private int count;
        private int from;

        public Builder(){
        }

        public Builder count(int val){
            count = val;
            return this;
        }

        public Builder from(int val){
            from = val;
            return this;
        }

        public ActionGetPerformingTasks build() {
            return new ActionGetPerformingTasks(this);
        }
    }
}
