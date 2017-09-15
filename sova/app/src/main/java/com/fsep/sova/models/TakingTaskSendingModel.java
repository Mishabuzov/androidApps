package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

public class TakingTaskSendingModel implements Parcelable {

    private long taskId;

    public TakingTaskSendingModel() {
    }

    public TakingTaskSendingModel(long taskId) {
        this.taskId = taskId;
    }

    protected TakingTaskSendingModel(Parcel in) {
        taskId = in.readLong();
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public static final Parcelable.Creator<TakingTaskSendingModel> CREATOR = new Parcelable.Creator<TakingTaskSendingModel>() {
        @Override
        public TakingTaskSendingModel createFromParcel(Parcel source) {
            return new TakingTaskSendingModel(source);
        }

        @Override
        public TakingTaskSendingModel[] newArray(int size) {
            return new TakingTaskSendingModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(taskId);
    }

}
