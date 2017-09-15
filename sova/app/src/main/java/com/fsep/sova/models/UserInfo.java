package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"id"}, allowSetters = true)
public class UserInfo implements Parcelable {

    /**
     * Идентификатор пользователя
     */
    private long id;

    /**
     * Имя пользователя
     */
    private String firstName;

    /**
     * Фамилия пользователя
     */
    private String lastName;

    /**
     * Ник пользователя
     */
    private String nickName;

    /*описание пользователя*/
    private String description;

    /**
     * Аватар пользователя
     */
    private Photo avatar;

    /* Количество подписчиков*/
    private int followersCount;

    /*Количество подписавшихся*/
    private int followingsCount;

    private int postsCount;

    /* Подписан я на пользователя, или нет*/
    private boolean subscribed;

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        id = in.readLong();
        firstName = in.readString();
        lastName = in.readString();
        nickName = in.readString();
        avatar = in.readParcelable(Photo.class.getClassLoader());
        description = in.readString();
        followersCount = in.readInt();
        followingsCount = in.readInt();
        postsCount = in.readInt();
        subscribed = in.readByte() != 0;
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public Photo getAvatar() {
        return avatar;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setAvatar(Photo avatar) {
        this.avatar = avatar;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFollowingsCount() {
        return followingsCount;
    }

    public void setFollowingsCount(int followingsCount) {
        this.followingsCount = followingsCount;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public int getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(int postsCount) {
        this.postsCount = postsCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(nickName);
        parcel.writeParcelable(avatar, i);
        parcel.writeString(description);
        parcel.writeInt(followersCount);
        parcel.writeInt(followingsCount);
        parcel.writeInt(postsCount);
        parcel.writeByte((byte) (subscribed ? 1 : 0));
    }
}
