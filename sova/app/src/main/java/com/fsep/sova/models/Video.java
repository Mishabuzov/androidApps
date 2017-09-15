package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Video extends BaseMediaModel implements Parcelable {

    /**
     * Картинка-превью видео
     */
    private Photo previewImage;
    private long size;
    private String extension;

    public Video() {
    }

    public Photo getPreviewImage() {
        return previewImage;
    }

    public String getExtension() {
        return extension;
    }

    public long getSize() {
        return size;
    }

    public void setPreviewImage(Photo previewImage) {
        this.previewImage = previewImage;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    protected Video(Parcel in) {
        super(in);
        extension = in.readString();
        size = in.readLong();
        previewImage = in.readParcelable(Photo.class.getClassLoader());
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(extension);
        parcel.writeLong(size);
        parcel.writeParcelable(previewImage, i);
    }
}
