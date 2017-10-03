package com.kpfu.mikhail.vk.content.attachments;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Attachment implements Parcelable {

    private AttachmentType type;

    private Photo photo;

    private Video video;

    public Attachment() {
    }

    public AttachmentType getType() {
        return type;
    }

    public void setType(AttachmentType type) {
        this.type = type;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeParcelable(this.photo, flags);
        dest.writeParcelable(this.video, flags);
    }

    protected Attachment(Parcel in) {
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : AttachmentType.values()[tmpType];
        this.photo = in.readParcelable(Photo.class.getClassLoader());
        this.video = in.readParcelable(Video.class.getClassLoader());
    }

    public static final Creator<Attachment> CREATOR = new Creator<Attachment>() {
        @Override
        public Attachment createFromParcel(Parcel source) {
            return new Attachment(source);
        }

        @Override
        public Attachment[] newArray(int size) {
            return new Attachment[size];
        }
    };
}
