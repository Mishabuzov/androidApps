package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SentInvitation implements Parcelable {

    private long id; //Идентификатор приглашения
    private UserInfo invited; //Информация о приглашенном пользователе

    public SentInvitation() {
    }

    protected SentInvitation(Parcel in) {
        id = in.readLong();
        invited = in.readParcelable(UserInfo.class.getClassLoader());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserInfo getInvited() {
        return invited;
    }

    public void setInvited(UserInfo invited) {
        this.invited = invited;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeParcelable(invited, flags);
    }

    public static final Creator<SentInvitation> CREATOR = new Creator<SentInvitation>() {
        @Override
        public SentInvitation createFromParcel(Parcel source) {
            return new SentInvitation(source);
        }

        @Override
        public SentInvitation[] newArray(int size) {
            return new SentInvitation[size];
        }
    };
}
