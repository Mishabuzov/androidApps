package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Task;
import com.fsep.sova.network.events.find_tasks.FindingTasksErrorEvent;
import com.fsep.sova.network.events.find_tasks.FindingTasksIsEmptyEvent;
import com.fsep.sova.network.events.find_tasks.FindingTasksIsSuccessEvent;
import com.fsep.sova.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class ActionFindTasks extends BaseAction<BaseResponseModel<List<Task>>> {

    private int mCount;
    private int mFrom;
    private String mFind;

    public ActionFindTasks(Builder builder) {
        mCount = builder.count;
        mFrom = builder.from;
        mFind = builder.find;
    }

    protected ActionFindTasks(Parcel in) {
        mCount = in.readInt();
        mFrom = in.readInt();
        mFind = in.readString();
    }

    @Override
    protected Response<BaseResponseModel<List<Task>>> makeRequest() throws IOException {
        return getRest().findTasks(createMap()).execute();
    }

    private Map<String, Object> createMap() {
        Map<String, Object> options = new HashMap<>();
        if (mCount != 0) {
            options.put("count", mCount);
        } else {
            options.put("count", Constants.DEFAULT_COUNT_VALUE);
        }
        if (mFrom != 0) {
            options.put("from", mFrom);
        }
        if (mFind != null && !mFind.isEmpty()) {
            options.put("find", mFind);
        }
        return options;
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<List<Task>>> response) {
        List<Task> foundedTasks = response.body().getData();
        if (!foundedTasks.isEmpty()) {
            EventBus.getDefault().post(new FindingTasksIsSuccessEvent(foundedTasks));
        } else {
            EventBus.getDefault().post(new FindingTasksIsEmptyEvent());
        }
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new FindingTasksErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mCount);
        dest.writeInt(mFrom);
        dest.writeString(mFind);
    }

    public static final Creator<ActionFindTasks> CREATOR = new Creator<ActionFindTasks>() {
        @Override
        public ActionFindTasks createFromParcel(Parcel source) {
            return new ActionFindTasks(source);
        }

        @Override
        public ActionFindTasks[] newArray(int size) {
            return new ActionFindTasks[size];
        }
    };

    public static class Builder {
        private int count;
        private int from;
        private String find;

        public Builder() {
        }

        public Builder count(int val) {
            count = val;
            return this;
        }

        public Builder from(int val) {
            from = val;
            return this;
        }

        public Builder find(String val) {
            find = val;
            return this;
        }

        public ActionFindTasks build() {
            return new ActionFindTasks(this);
        }
    }
}
