package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Respond implements Parcelable {
    private long taskId;
    private String message;

    public Respond() {
    }

    public Respond(long taskId, String message) {
        this.taskId = taskId;
        this.message = message;
    }

    public Respond(long taskId) {
        this.taskId = taskId;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    protected Respond(Parcel in) {
        taskId = in.readLong();
        message = in.readString();
    }

    public static final Creator<Respond> CREATOR = new Creator<Respond>() {
        @Override
        public Respond createFromParcel(Parcel source) {
            return new Respond(source);
        }

        @Override
        public Respond[] newArray(int size) {
            return new Respond[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(taskId);
        dest.writeString(message);
    }
}
