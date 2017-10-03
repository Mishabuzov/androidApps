package com.kpfu.mikhail.vk.content.attachments;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo implements Parcelable {

    @JsonProperty("photo_130")
    private String lowQualityPhoto;

    @JsonProperty("photo_604")
    private String mediumQualityPhoto;

    public Photo() {
    }

    public String getLowQualityPhoto() {
        return lowQualityPhoto;
    }

    public void setLowQualityPhoto(String lowQualityPhoto) {
        this.lowQualityPhoto = lowQualityPhoto;
    }

    public String getMediumQualityPhoto() {
        return mediumQualityPhoto;
    }

    public void setMediumQualityPhoto(String mediumQualityPhoto) {
        this.mediumQualityPhoto = mediumQualityPhoto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.lowQualityPhoto);
        dest.writeString(this.mediumQualityPhoto);
    }

    protected Photo(Parcel in) {
        this.lowQualityPhoto = in.readString();
        this.mediumQualityPhoto = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
