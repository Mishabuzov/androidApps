package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryCity implements Parcelable {

    private long id;
    private String title;

    public CountryCity() {
    }

    public CountryCity(long id, String title) {
        this.id = id;
        this.title = title;
    }

    protected CountryCity(Parcel in) {
        id = in.readLong();
        title = in.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static final Creator<CountryCity> CREATOR = new Creator<CountryCity>() {
        @Override
        public CountryCity createFromParcel(Parcel source) {
            return new CountryCity(source);
        }

        @Override
        public CountryCity[] newArray(int size) {
            return new CountryCity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
    }
}
