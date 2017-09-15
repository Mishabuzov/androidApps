package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Task;
import com.fsep.sova.network.events.gettaskbyid.GettingTaskByIdErrorEvent;
import com.fsep.sova.network.events.gettaskbyid.GettingTaskByIdIsSuccess;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionGetTaskById extends BaseAction<BaseResponseModel<Task>> {

    private long mTaskId;

    public ActionGetTaskById(long taskId) {
        mTaskId = taskId;
    }

    private ActionGetTaskById(Parcel in) {
        mTaskId = in.readLong();
    }

    @Override
    protected Response<BaseResponseModel<Task>> makeRequest() throws IOException {
        return getRest().getTaskById(mTaskId).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<Task>> response) {
        EventBus.getDefault().post(new GettingTaskByIdIsSuccess(response.body().getData()));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingTaskByIdErrorEvent(response.code(), response.message()));
    }

    public static final Creator<ActionGetTaskById> CREATOR = new Creator<ActionGetTaskById>() {
        @Override
        public ActionGetTaskById createFromParcel(Parcel in) {
            return new ActionGetTaskById(in);
        }

        @Override
        public ActionGetTaskById[] newArray(int size) {
            return new ActionGetTaskById[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mTaskId);
    }
}
