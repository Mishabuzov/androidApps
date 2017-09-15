package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.TakingTaskSendingModel;
import com.fsep.sova.models.Task;
import com.fsep.sova.network.events.taketask.TakingTaskErrorEvent;
import com.fsep.sova.network.events.taketask.TakingTaskIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionTakeTask extends BaseAction<BaseResponseModel<Task>> {

    private TakingTaskSendingModel mSendingModel;

    public ActionTakeTask(TakingTaskSendingModel sendingModel) {
        mSendingModel = sendingModel;
    }

    private ActionTakeTask(Parcel in) {
        mSendingModel = in.readParcelable(TakingTaskSendingModel.class.getClassLoader());
    }

    @Override
    protected Response<BaseResponseModel<Task>> makeRequest() throws IOException {
        return getRest().takeTask(mSendingModel).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<Task>> response) {
        EventBus.getDefault().post(new TakingTaskIsSuccessEvent(response.body().getData()));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new TakingTaskErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActionTakeTask> CREATOR = new Creator<ActionTakeTask>() {
        @Override
        public ActionTakeTask createFromParcel(Parcel in) {
            return new ActionTakeTask(in);
        }

        @Override
        public ActionTakeTask[] newArray(int size) {
            return new ActionTakeTask[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mSendingModel, flags);
    }
}
