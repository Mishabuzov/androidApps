package com.fsep.sova.network.actions;

import android.os.Parcel;
import android.support.annotation.Nullable;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Task;
import com.fsep.sova.network.events.updatetask.UpdatingTaskErrorEvent;
import com.fsep.sova.network.events.updatetask.UpdatingTaskIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionUpdateTask extends BaseAction<BaseResponseModel<Task>> {

    @Nullable
    private Task mTask;
    private long mTaskId;

    public ActionUpdateTask(long taskId, Task task) {
        mTask = task;
        mTaskId = taskId;
    }

    private ActionUpdateTask(Parcel in) {
        mTaskId = in.readLong();
        mTask = in.readParcelable(Task.class.getClassLoader());
    }

    @Override
    protected Response<BaseResponseModel<Task>> makeRequest() throws IOException {
        return getRest().updateTask(mTaskId, checkTaskOnNull()).execute();
    }

    private Task checkTaskOnNull() {
        if (mTask != null) {
            return mTask;
        } else {
            return new Task();
        }
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<Task>> response) {
        EventBus.getDefault().post(new UpdatingTaskIsSuccessEvent(mTask));
    }

    @Override
    protected void onHttpError(Response response) {
        EventBus.getDefault().post(new UpdatingTaskErrorEvent(response.code(), response.message()));
    }

    public static final Creator<ActionUpdateTask> CREATOR = new Creator<ActionUpdateTask>() {
        @Override
        public ActionUpdateTask createFromParcel(Parcel in) {
            return new ActionUpdateTask(in);
        }

        @Override
        public ActionUpdateTask[] newArray(int size) {
            return new ActionUpdateTask[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mTaskId);
        parcel.writeParcelable(mTask, i);
    }
}
