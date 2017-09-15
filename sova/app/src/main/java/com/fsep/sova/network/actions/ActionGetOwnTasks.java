package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Task;
import com.fsep.sova.network.events.getowntasks.GettingOwnTasksErrorEvent;
import com.fsep.sova.network.events.getowntasks.GettingOwnTasksIsEmptyEvent;
import com.fsep.sova.network.events.getowntasks.GettingOwnTasksIsSuccessEvent;
import com.fsep.sova.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class ActionGetOwnTasks extends BaseAction<BaseResponseModel<List<Task>>> {

    private boolean mPublishedTasks;
    private int mCount;
    private int mFrom;

    public ActionGetOwnTasks(Builder builder) {
        mPublishedTasks = builder.publishedTasks;
        mCount = builder.count;
        mFrom = builder.from;
    }

    public ActionGetOwnTasks(Parcel in) {
        mPublishedTasks = in.readByte() != 0;
        mCount = in.readInt();
        mFrom = in.readInt();
    }

    @Override
    protected Response<BaseResponseModel<List<Task>>> makeRequest() throws IOException {
        return getRest().getOwnTasks(createQueryMap()).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<List<Task>>> response) {
        List<Task> tasks = response.body().getData();
        if (tasks.size() > 0) {
            EventBus.getDefault().post(new GettingOwnTasksIsSuccessEvent(tasks));
        } else {
            EventBus.getDefault().post(new GettingOwnTasksIsEmptyEvent());
        }
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingOwnTasksErrorEvent(response.code(), response.message()));
    }

    public static final Creator<ActionGetOwnTasks> CREATOR = new Creator<ActionGetOwnTasks>() {
        @Override
        public ActionGetOwnTasks createFromParcel(Parcel in) {
            return new ActionGetOwnTasks(in);
        }

        @Override
        public ActionGetOwnTasks[] newArray(int size) {
            return new ActionGetOwnTasks[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (mPublishedTasks ? 1 : 0));
        parcel.writeInt(mCount);
        parcel.writeInt(mFrom);
    }

    private Map<String, Object> createQueryMap() {
        Map<String, Object> options = new HashMap<>();
        options.put("published", mPublishedTasks);
        if (mFrom != 0) {
            options.put("from", mFrom);
        }
        if (mCount != 0) {
            options.put("count", mCount);
        } else {
            options.put("count", Constants.DEFAULT_COUNT_VALUE);
        }
        return options;
    }

    public static class Builder {
        private boolean publishedTasks;

        private int count;
        private int from;

        public Builder(boolean publishedTasks) {
            this.publishedTasks = publishedTasks;
        }

        public Builder count(int val) {
            count = val;
            return this;
        }

        public Builder from(int val) {
            from = val;
            return this;
        }

        public ActionGetOwnTasks build() {
            return new ActionGetOwnTasks(this);
        }
    }
}
