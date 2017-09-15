package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserInfoModel implements Parcelable {

    private String firstName;
    private String lastName;
    private String nickName;
    private String description;
    private Photo avatar;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.nickName);
        dest.writeString(this.description);
        dest.writeParcelable(this.avatar, flags);
    }

    public UpdateUserInfoModel() {
    }

    protected UpdateUserInfoModel(Parcel in) {
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.nickName = in.readString();
        this.description = in.readString();
        this.avatar = in.readParcelable(Photo.class.getClassLoader());
    }

    public static final Creator<UpdateUserInfoModel> CREATOR = new Creator<UpdateUserInfoModel>() {
        @Override
        public UpdateUserInfoModel createFromParcel(Parcel source) {
            return new UpdateUserInfoModel(source);
        }

        @Override
        public UpdateUserInfoModel[] newArray(int size) {
            return new UpdateUserInfoModel[size];
        }
    };

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Photo getAvatar() {
        return avatar;
    }

    public void setAvatar(Photo avatar) {
        this.avatar = avatar;
    }
}
