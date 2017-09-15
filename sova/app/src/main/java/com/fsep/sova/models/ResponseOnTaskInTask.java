package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ResponseOnTaskInTask implements Parcelable {
    private long id;
    private String message;

    public ResponseOnTaskInTask() {
    }

    public ResponseOnTaskInTask(long id, String message) {
        this.id = id;
        this.message = message;
    }

    public ResponseOnTaskInTask(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    protected ResponseOnTaskInTask(Parcel in) {
        id = in.readLong();
        message = in.readString();
    }

    public static final Parcelable.Creator<ResponseOnTaskInTask> CREATOR = new Parcelable.Creator<ResponseOnTaskInTask>() {
        @Override
        public ResponseOnTaskInTask createFromParcel(Parcel source) {
            return new ResponseOnTaskInTask(source);
        }

        @Override
        public ResponseOnTaskInTask[] newArray(int size) {
            return new ResponseOnTaskInTask[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(message);
    }
}
