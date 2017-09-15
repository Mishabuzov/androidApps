package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ConversationInfo implements Parcelable {

    private int imagesCount;
    private int videosCount;
    private int documentsCount;

    public ConversationInfo() {
    }

    protected ConversationInfo(Parcel in) {
        imagesCount = in.readInt();
        videosCount = in.readInt();
        documentsCount = in.readInt();
    }

    public static final Creator<ConversationInfo> CREATOR = new Creator<ConversationInfo>() {
        @Override
        public ConversationInfo createFromParcel(Parcel source) {
            return new ConversationInfo(source);
        }

        @Override
        public ConversationInfo[] newArray(int size) {
            return new ConversationInfo[size];
        }
    };

    public int getImagesCount() {
        return imagesCount;
    }

    public void setImagesCount(int imagesCount) {
        this.imagesCount = imagesCount;
    }

    public int getVideosCount() {
        return videosCount;
    }

    public void setVideosCount(int videosCount) {
        this.videosCount = videosCount;
    }

    public int getDocumentsCount() {
        return documentsCount;
    }

    public void setDocumentsCount(int documentsCount) {
        this.documentsCount = documentsCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imagesCount);
        dest.writeInt(videosCount);
        dest.writeInt(documentsCount);
    }
}
