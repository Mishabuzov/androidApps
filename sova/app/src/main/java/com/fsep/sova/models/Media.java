package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Media implements Parcelable {

    /**
     * Список фотографий
     */
    private List<Photo> images;

    /**
     * Список документов
     */
    private List<Document> documents;

    /**
     * Список видеофайлов
     */
    private List<Video> videos;

    public Media() {
    }

    protected Media(Parcel in) {
        images = in.createTypedArrayList(Photo.CREATOR);
        documents = in.createTypedArrayList(Document.CREATOR);
        videos = in.createTypedArrayList(Video.CREATOR);
    }

    public static final Creator<Media> CREATOR = new Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };

    public List<Photo> getImages() {
        return images;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setImages(List<Photo> images) {
        this.images = images;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(images);
        parcel.writeTypedList(documents);
        parcel.writeTypedList(videos);
    }
}
