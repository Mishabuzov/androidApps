package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

public class InvitationSendingModel implements Parcelable {

    private long taskId;

    public InvitationSendingModel() {
    }

    protected InvitationSendingModel(Parcel in) {
        taskId = in.readLong();
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public static final Creator<InvitationSendingModel> CREATOR = new Creator<InvitationSendingModel>() {
        @Override
        public InvitationSendingModel createFromParcel(Parcel source) {
            return new InvitationSendingModel(source);
        }

        @Override
        public InvitationSendingModel[] newArray(int size) {
            return new InvitationSendingModel[size];
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
