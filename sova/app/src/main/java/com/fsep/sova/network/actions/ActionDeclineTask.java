package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.network.events.decline_task.DecliningTaskErrorEvent;
import com.fsep.sova.network.events.decline_task.DecliningTaskIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionDeclineTask extends BaseAction<BaseResponseModel> {

    private long mTaskId;

    public ActionDeclineTask(long taskId) {
        mTaskId = taskId;
    }

    protected ActionDeclineTask(Parcel in) {
        mTaskId = in.readLong();
    }

    @Override
    protected Response<BaseResponseModel> makeRequest() throws IOException {
        return getRest().declineTask(mTaskId).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel> response) {
        EventBus.getDefault().post(new DecliningTaskIsSuccessEvent());
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new DecliningTaskErrorEvent(response.code(), response.message()));
    }

    public static final Creator<ActionDeclineTask> CREATOR = new Creator<ActionDeclineTask>() {
        @Override
        public ActionDeclineTask createFromParcel(Parcel source) {
            return new ActionDeclineTask(source);
        }

        @Override
        public ActionDeclineTask[] newArray(int size) {
            return new ActionDeclineTask[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mTaskId);
    }
}
