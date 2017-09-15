package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tag implements Parcelable {

    private String name;
    private boolean subscribed;
    private int countRecords;

    public Tag() {
    }

    public Tag(boolean subscribed, int countRecords, String name) {
        this.subscribed = subscribed;
        this.countRecords = countRecords;
        this.name = name;
    }

    protected Tag(Parcel in) {
        name = in.readString();
        subscribed = in.readByte() != 0;
        countRecords = in.readInt();
    }

    public String getName() {
        return name;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public int getCountRecords() {
        return countRecords;
    }

    public static final Creator<Tag> CREATOR = new Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel source) {
            return new Tag(source);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (subscribed ? 1 : 0));
        dest.writeInt(countRecords);
    }
}
