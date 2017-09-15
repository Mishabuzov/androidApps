package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.network.events.delete_task.DeletingTaskErrorEvent;
import com.fsep.sova.network.events.delete_task.DeletingTaskIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionDeleteTask extends BaseAction<BaseResponseModel> {
    private long mTaskId;

    public ActionDeleteTask(long taskId) {
        mTaskId = taskId;
    }

    protected ActionDeleteTask(Parcel in){
        mTaskId = in.readLong();
    }

    @Override
    protected Response<BaseResponseModel> makeRequest() throws IOException {
        return getRest().deleteTask(mTaskId).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel> response) {
        EventBus.getDefault().post(new DeletingTaskIsSuccessEvent());
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new DeletingTaskErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActionDeleteTask> CREATOR = new Creator<ActionDeleteTask>() {
        @Override
        public ActionDeleteTask createFromParcel(Parcel source) {
            return new ActionDeleteTask(source);
        }

        @Override
        public ActionDeleteTask[] newArray(int size) {
            return new ActionDeleteTask[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mTaskId);
    }
}
