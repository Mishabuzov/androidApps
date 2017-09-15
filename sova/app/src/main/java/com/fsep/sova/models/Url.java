package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Url implements Parcelable {

    private String mUrl;
    private boolean mIsVideo;

    public Url(String url) {
        mUrl = url;
    }

    protected Url(Parcel in) {
        mUrl = in.readString();
        mIsVideo = in.readByte() != 0;
    }

    public Url(String url, boolean isVideo) {
        mUrl = url;
        mIsVideo = isVideo;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public boolean isVideo() {
        return mIsVideo;
    }

    public void setVideo(boolean video) {
        mIsVideo = video;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Url> CREATOR = new Creator<Url>() {
        @Override
        public Url createFromParcel(Parcel source) {
            return new Url(source);
        }

        @Override
        public Url[] newArray(int size) {
            return new Url[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUrl);
        dest.writeByte((byte) (mIsVideo ? 1 : 0));
    }
}
