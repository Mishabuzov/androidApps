package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment implements Parcelable {
    private long id;
    private UserInfo author;
    private String text;
    private long publishedTime;

    public Comment() {
    }

    protected Comment(Parcel in) {
        id = in.readLong();
        author = in.readParcelable(UserInfo.class.getClassLoader());
        text = in.readString();
        publishedTime = in.readLong();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public long getId() {
        return id;
    }

    public UserInfo getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public long getPublishedTime() {
        return publishedTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeParcelable(author, i);
        parcel.writeLong(publishedTime);
        parcel.writeString(text);
    }
}
