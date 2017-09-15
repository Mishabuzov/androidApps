package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

public class AssignTaskSendingModel implements Parcelable {
    private long taskId;

    public AssignTaskSendingModel() {
    }

    public AssignTaskSendingModel(long taskId) {
        this.taskId = taskId;
    }

    protected AssignTaskSendingModel(Parcel in) {
        taskId = in.readLong();
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public static final Parcelable.Creator<AssignTaskSendingModel> CREATOR = new Parcelable.Creator<AssignTaskSendingModel>() {
        @Override
        public AssignTaskSendingModel createFromParcel(Parcel source) {
            return new AssignTaskSendingModel(source);
        }

        @Override
        public AssignTaskSendingModel[] newArray(int size) {
            return new AssignTaskSendingModel[size];
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
