package com.fsep.sova.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo extends BaseMediaModel implements Parcelable {

    /**
     * URL маленького изображения
     */
    private String thumbUrl;

    /**
     * URL оригинальной версии изображения
     */
    private String originalUrl;

    private long size;

    private String extension;

    public Photo() {
    }

    protected Photo(Parcel in) {
        super(in);
        extension = in.readString();
        size = in.readLong();
        thumbUrl = in.readString();
        originalUrl = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public String getThumbUrl() {
        return thumbUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getExtension() {
        return extension;
    }

    public long getSize() {
        return size;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(extension);
        parcel.writeLong(size);
        parcel.writeString(thumbUrl);
        parcel.writeString(originalUrl);
    }
}
