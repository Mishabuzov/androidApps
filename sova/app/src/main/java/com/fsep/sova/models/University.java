package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class University implements Parcelable {

    private StudyPlaceInfo university;
    private long beginingDate;
    private CountryCity country;
    private CountryCity city;
    private long endingDate;
    private boolean isCurrentUniversity;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.university, flags);
        dest.writeLong(this.beginingDate);
        dest.writeParcelable(this.country, flags);
        dest.writeParcelable(this.city, flags);
        dest.writeLong(this.endingDate);
        dest.writeByte(this.isCurrentUniversity ? (byte) 1 : (byte) 0);
    }

    public University() {
    }

    protected University(Parcel in) {
        this.university = in.readParcelable(StudyPlaceInfo.class.getClassLoader());
        this.beginingDate = in.readLong();
        this.country = in.readParcelable(CountryCity.class.getClassLoader());
        this.city = in.readParcelable(CountryCity.class.getClassLoader());
        this.endingDate = in.readLong();
        this.isCurrentUniversity = in.readByte() != 0;
    }

    public static final Creator<University> CREATOR = new Creator<University>() {
        @Override
        public University createFromParcel(Parcel source) {
            return new University(source);
        }

        @Override
        public University[] newArray(int size) {
            return new University[size];
        }
    };

    public long getBeginingDate() {
        return beginingDate;
    }

    public StudyPlaceInfo getUniversity() {
        return university;
    }

    public CountryCity getCountry() {
        return country;
    }

    public CountryCity getCity() {
        return city;
    }

    public long getEndingDate() {
        return endingDate;
    }

    public boolean isCurrentUniversity() {
        return isCurrentUniversity;
    }
}
