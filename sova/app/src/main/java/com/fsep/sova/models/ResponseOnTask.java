package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseOnTask implements Parcelable {

    // Идентификатор отклика
    private long id;

    // Информация об откликнувшемся пользователе
    private UserInfo responder;

    // Сообщение отклика
    private String message;

    public ResponseOnTask() {
    }

    protected ResponseOnTask(Parcel in){
        id = in.readLong();
        responder = in.readParcelable(UserInfo.class.getClassLoader());
        message = in.readString();
    }

    public static final Creator<ResponseOnTask> CREATOR = new Creator<ResponseOnTask>() {
        @Override
        public ResponseOnTask createFromParcel(Parcel source) {
            return new ResponseOnTask(source);
        }

        @Override
        public ResponseOnTask[] newArray(int size) {
            return new ResponseOnTask[size];
        }
    };

    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public UserInfo getResponder() {
        return responder;
    }

    public void setResponder(UserInfo responder) {
        this.responder = responder;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(message);
        dest.writeParcelable(responder, flags);
    }
}
