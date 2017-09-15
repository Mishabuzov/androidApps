package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyPlaceInfo implements Parcelable {

    private long id;
    private String title;
    private String cityTitle;
    private String countryTitle;

    public StudyPlaceInfo() {
    }

    public StudyPlaceInfo(long id, String title, String cityTitle, String countryTitle) {
        this.id = id;
        this.title = title;
        this.cityTitle = cityTitle;
        this.countryTitle = countryTitle;
    }

    protected StudyPlaceInfo(Parcel in) {
        id = in.readLong();
        title = in.readString();
        cityTitle = in.readString();
        countryTitle = in.readString();
    }

    public static final Creator<StudyPlaceInfo> CREATOR = new Creator<StudyPlaceInfo>() {
        @Override
        public StudyPlaceInfo createFromParcel(Parcel source) {
            return new StudyPlaceInfo(source);
        }

        @Override
        public StudyPlaceInfo[] newArray(int size) {
            return new StudyPlaceInfo[size];
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
        dest.writeString(cityTitle);
        dest.writeString(countryTitle);
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCityTitle() {
        return cityTitle;
    }

    public String getCountryTitle() {
        return countryTitle;
    }
}
