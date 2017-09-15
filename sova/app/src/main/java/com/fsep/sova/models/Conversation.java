package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Conversation implements Parcelable {

    private long id;
    private UserInfo sender;
    private int date;
    private Label label;
    private String text;
    private int videosCount;
    private int imagesCount;
    private int documentsCount;
    private List<Photo> images;
    private List<Document> documents;
    private List<Video> videos;

    public Conversation() {
    }


    protected Conversation(Parcel in) {
        id = in.readLong();
        sender = in.readParcelable(UserInfo.class.getClassLoader());
        date = in.readInt();
        label = Label.getEnum(in.readString());
        text = in.readString();
        videosCount = in.readInt();
        imagesCount = in.readInt();
        documentsCount = in.readInt();
        images = new ArrayList<>();
        in.readList(images, Photo.class.getClassLoader());
        documents = new ArrayList<>();
        in.readList(documents, Document.class.getClassLoader());
        videos = new ArrayList<>();
        in.readList(videos, Video.class.getClassLoader());
    }

    public static final Creator<Conversation> CREATOR = new Creator<Conversation>() {
        @Override
        public Conversation createFromParcel(Parcel source) {
            return new Conversation(source);
        }

        @Override
        public Conversation[] newArray(int size) {
            return new Conversation[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<Photo> getImages() {
        return images;
    }

    public void setImages(List<Photo> images) {
        this.images = images;
    }

    public int getDocumentsCount() {
        return documentsCount;
    }

    public void setDocumentsCount(int documentsCount) {
        this.documentsCount = documentsCount;
    }

    public int getImagesCount() {
        return imagesCount;
    }

    public void setImagesCount(int imagesCount) {
        this.imagesCount = imagesCount;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public int getVideosCount() {
        return videosCount;
    }

    public void setVideosCount(int videosCount) {
        this.videosCount = videosCount;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public UserInfo getSender() {
        return sender;
    }

    public void setSender(UserInfo sender) {
        this.sender = sender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeParcelable(sender, flags);
        dest.writeInt(date);
        dest.writeString(label.toString());
        dest.writeString(text);
        dest.writeInt(videosCount);
        dest.writeInt(imagesCount);
        dest.writeInt(documentsCount);
        dest.writeList(images);
        dest.writeList(documents);
        dest.writeList(videos);
    }
}
