package com.fsep.sova.network.actions;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Task;
import com.fsep.sova.network.events.createtask.CreatingTaskErrorEvent;
import com.fsep.sova.network.events.createtask.CreatingTaskIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionCreateTask extends BaseAction<BaseResponseModel<Task>> {

    private Task mTask;

    public ActionCreateTask(@NonNull Task task) {
        mTask = task;
    }

    private ActionCreateTask(Parcel in) {
        mTask = in.readParcelable(Task.class.getClassLoader());
    }

    @Override
    protected Response<BaseResponseModel<Task>> makeRequest() throws IOException {
        return getRest().createTask(mTask).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<Task>> response) {
        EventBus.getDefault().post(new CreatingTaskIsSuccessEvent(response.body().getData()));
    }

    @Override
    protected void onHttpError(Response response) {
        EventBus.getDefault().post(new CreatingTaskErrorEvent(response.code(), response.message()));
    }

    public static final Creator<ActionCreateTask> CREATOR = new Creator<ActionCreateTask>() {
        @Override
        public ActionCreateTask createFromParcel(Parcel in) {
            return new ActionCreateTask(in);
        }

        @Override
        public ActionCreateTask[] newArray(int size) {
            return new ActionCreateTask[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(mTask, i);
    }
}
