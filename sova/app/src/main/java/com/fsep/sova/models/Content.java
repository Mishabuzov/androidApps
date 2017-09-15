package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Content implements Parcelable {

    private Photo mPhoto;
    private Video mVideo;
    private boolean mIsPhoto;
    private boolean mIsVideo;

    public Content(Photo photo) {
        mIsPhoto = true;
        mPhoto = photo;
    }

    public Content(Video video) {
        mIsVideo = true;
        mVideo = video;
    }

    protected Content(Parcel in) {
        mPhoto = in.readParcelable(Photo.class.getClassLoader());
        mIsPhoto = in.readByte() != 0;
        mVideo = in.readParcelable(Video.class.getClassLoader());
        mIsVideo = in.readByte() != 0;
    }

    public Photo getPhoto() {
        return mPhoto;
    }

    public Video getVideo() {
        return mVideo;
    }

    public boolean isVideo() {
        return mIsVideo;
    }

    public boolean isPhoto() {
        return mIsPhoto;
    }

    public static final Creator<Content> CREATOR = new Creator<Content>() {
        @Override
        public Content createFromParcel(Parcel source) {
            return new Content(source);
        }

        @Override
        public Content[] newArray(int size) {
            return new Content[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mPhoto, flags);
        dest.writeByte((byte) (mIsPhoto ? 1 : 0));
        dest.writeParcelable(mVideo, flags);
        dest.writeByte((byte) (mIsVideo ? 1 : 0));
    }
}
