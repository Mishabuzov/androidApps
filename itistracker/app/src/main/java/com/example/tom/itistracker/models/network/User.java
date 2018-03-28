package com.example.tom.itistracker.models.network;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Parcelable {

    @JsonProperty("auth_token")
    private String token;

    @JsonProperty("photo")
    private String avatarUrl;

    private String username;

    public User(String token, String avatarUrl, String username) {
        this.token = token;
        this.avatarUrl = avatarUrl;
        this.username = username;
    }

    public User() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token);
        dest.writeString(this.avatarUrl);
        dest.writeString(this.username);
    }

    protected User(Parcel in) {
        this.token = in.readString();
        this.avatarUrl = in.readString();
        this.username = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
