package com.kpfu.mikhail.gif.content;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Gif implements Parcelable {

    @SerializedName("gfyId")
    private String id;

    @SerializedName("gfyName")
    private String name;

    @SerializedName("max5mbGif")
    private String highUrl;

    @SerializedName("max2mbGif")
    private String lowUrl;

    @SerializedName("thumb360PosterUrl")
    private String poster;

    @SerializedName("createDate")
    private int date;

    @SerializedName("likes")
    private String likesCount;

    private List<String> tags;

    private String title;

    private String description;

    private long views;

    private boolean mIsLiked;

    private boolean mIsFavorite;

    private boolean mIsWatched;

    private boolean mIsPlaying;

    public Gif() {
    }

    public Gif(String id, String name, String highUrl, String lowUrl,
               String poster, int date, String likesCount, List<String> tags,
               String title, String description, long views) {
        this.id = id;
        this.name = name;
        this.highUrl = highUrl;
        this.lowUrl = lowUrl;
        this.poster = poster;
        this.date = date;
        this.likesCount = likesCount;
        this.tags = tags;
        this.title = title;
        this.description = description;
        this.views = views;
        mIsLiked = false;
        mIsFavorite = false;
        mIsWatched = false;
        mIsPlaying = false;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    public String getHighUrl() {
        return highUrl;
    }

    public void setHighUrl(String highUrl) {
        this.highUrl = highUrl;
    }

    @NonNull
    public String getLowUrl() {
        return lowUrl;
    }

    public void setLowUrl(String lowUrl) {
        this.lowUrl = lowUrl;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    @NonNull
    public String getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(String likeCount) {
        this.likesCount = likeCount;
    }

    @NonNull
    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    @NonNull
    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public boolean isLiked() {
        return mIsLiked;
    }

    public void setLiked(boolean liked) {
        mIsLiked = liked;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setFavorite(boolean favorite) {
        mIsFavorite = favorite;
    }

    public boolean isWatched() {
        return mIsWatched;
    }

    public void setWatched(boolean watched) {
        mIsWatched = watched;
    }

    public boolean isPlaying() {
        return mIsPlaying;
    }

    public void setPlaying(boolean playing) {
        mIsPlaying = playing;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.highUrl);
        dest.writeString(this.lowUrl);
        dest.writeString(this.poster);
        dest.writeInt(this.date);
        dest.writeString(this.likesCount);
        dest.writeStringList(this.tags);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeLong(this.views);
        dest.writeByte(this.mIsLiked ? (byte) 1 : (byte) 0);
        dest.writeByte(this.mIsFavorite ? (byte) 1 : (byte) 0);
        dest.writeByte(this.mIsWatched ? (byte) 1 : (byte) 0);
    }

    protected Gif(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.highUrl = in.readString();
        this.lowUrl = in.readString();
        this.poster = in.readString();
        this.date = in.readInt();
        this.likesCount = in.readString();
        this.tags = in.createStringArrayList();
        this.title = in.readString();
        this.description = in.readString();
        this.views = in.readLong();
        this.mIsLiked = in.readByte() != 0;
        this.mIsFavorite = in.readByte() != 0;
        this.mIsWatched = in.readByte() != 0;
    }

    public static final Creator<Gif> CREATOR = new Creator<Gif>() {
        @Override
        public Gif createFromParcel(Parcel source) {
            return new Gif(source);
        }

        @Override
        public Gif[] newArray(int size) {
            return new Gif[size];
        }
    };
}
