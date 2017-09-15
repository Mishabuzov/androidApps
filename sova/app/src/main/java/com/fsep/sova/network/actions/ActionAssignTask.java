package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.AssignTaskSendingModel;
import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.network.events.assign_task.AssignTaskErrorEvent;
import com.fsep.sova.network.events.assign_task.AssignTaskIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionAssignTask extends BaseAction<BaseResponseModel> {

    private long mUserId;
    private AssignTaskSendingModel mSendingModel;

    public ActionAssignTask(long userId, AssignTaskSendingModel sendingModel) {
        mUserId = userId;
        mSendingModel = sendingModel;
    }

    protected ActionAssignTask(Parcel in) {
        mUserId = in.readLong();
        mSendingModel = in.readParcelable(AssignTaskSendingModel.class.getClassLoader());
    }

    @Override
    protected Response<BaseResponseModel> makeRequest() throws IOException {
        return getRest().assignTaskToUser(mUserId, mSendingModel).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel> response) {
        EventBus.getDefault().post(new AssignTaskIsSuccessEvent());
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new AssignTaskErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActionAssignTask> CREATOR = new Creator<ActionAssignTask>() {
        @Override
        public ActionAssignTask createFromParcel(Parcel source) {
            return new ActionAssignTask(source);
        }

        @Override
        public ActionAssignTask[] newArray(int size) {
            return new ActionAssignTask[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mUserId);
        dest.writeParcelable(mSendingModel, flags);
    }
}
