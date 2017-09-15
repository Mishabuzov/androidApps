package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceivedInvitation implements Parcelable {

    private long id; //Идентификатор приглашения
    private Task task; //Информация о задаче, в которую приглашают пользователя. Заполнены будут только некоторые поля

    public ReceivedInvitation() {
    }

    public ReceivedInvitation(long id, Task task) {
        this.id = id;
        this.task = task;
    }

    protected ReceivedInvitation(Parcel in) {
        id = in.readLong();
        task = in.readParcelable(Task.class.getClassLoader());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeParcelable(task, flags);
    }

    public static final Creator<ReceivedInvitation> CREATOR = new Creator<ReceivedInvitation>() {
        @Override
        public ReceivedInvitation createFromParcel(Parcel source) {
            return new ReceivedInvitation(source);
        }

        @Override
        public ReceivedInvitation[] newArray(int size) {
            return new ReceivedInvitation[size];
        }
    };
}
