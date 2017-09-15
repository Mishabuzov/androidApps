package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PostEvent implements Parcelable {

    private boolean published;
    private Photo cover;
    private String title;
    private String text;
    private List<Tag> tags;
    private Place place;
    private Media media;
    private long endingTime;

    public PostEvent(boolean published, Photo cover, String title, String text, List<Tag> tags, Place place, Media media, long endingTime) {
        this.published = published;
        this.cover = cover;
        this.title = title;
        this.text = text;
        this.tags = tags;
        this.place = place;
        this.media = media;
        this.endingTime = endingTime;
    }

    public PostEvent() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.published ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.cover, flags);
        dest.writeString(this.title);
        dest.writeString(this.text);
        dest.writeTypedList(this.tags);
        dest.writeParcelable(this.place, flags);
        dest.writeParcelable(this.media, flags);
        dest.writeLong(this.endingTime);
    }

    protected PostEvent(Parcel in) {
        this.published = in.readByte() != 0;
        this.cover = in.readParcelable(Photo.class.getClassLoader());
        this.title = in.readString();
        this.text = in.readString();
        this.tags = in.createTypedArrayList(Tag.CREATOR);
        this.place = in.readParcelable(Place.class.getClassLoader());
        this.media = in.readParcelable(Media.class.getClassLoader());
        this.endingTime = in.readLong();
    }

    public static final Parcelable.Creator<PostEvent> CREATOR = new Parcelable.Creator<PostEvent>() {
        @Override
        public PostEvent createFromParcel(Parcel source) {
            return new PostEvent(source);
        }

        @Override
        public PostEvent[] newArray(int size) {
            return new PostEvent[size];
        }
    };

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Photo getCover() {
        return cover;
    }

    public void setCover(Photo cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public long getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(long endingTime) {
        this.endingTime = endingTime;
    }
}
