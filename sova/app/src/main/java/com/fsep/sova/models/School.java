package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class School implements Parcelable {

    private StudyPlaceInfo school;
    private long beginingDate;
    private CountryCity country;
    private CountryCity city;
    private long endingDate;
    private boolean isCurrentSchool;

    public School() {
    }

    public School(StudyPlaceInfo school, long beginingDate, CountryCity country, CountryCity city, long endingDate, boolean isCurrentSchool) {
        this.school = school;
        this.beginingDate = beginingDate;
        this.country = country;
        this.city = city;
        this.endingDate = endingDate;
        this.isCurrentSchool = isCurrentSchool;
    }

    protected School(Parcel in) {
        school = in.readParcelable(StudyPlaceInfo.class.getClassLoader());
        beginingDate = in.readLong();
        country = in.readParcelable(CountryCity.class.getClassLoader());
        city = in.readParcelable(CountryCity.class.getClassLoader());
        endingDate = in.readLong();
        isCurrentSchool = in.readByte() != 0;
    }

    public static final Creator<School> CREATOR = new Creator<School>() {
        @Override
        public School createFromParcel(Parcel source) {
            return new School(source);
        }

        @Override
        public School[] newArray(int size) {
            return new School[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(school, flags);
        dest.writeLong(beginingDate);
        dest.writeParcelable(country, flags);
        dest.writeParcelable(city, flags);
        dest.writeLong(endingDate);
        dest.writeByte((byte) (isCurrentSchool ? 1 : 0));
    }

    public StudyPlaceInfo getSchool() {
        return school;
    }

    public long getBeginingDate() {
        return beginingDate;
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

    public boolean isCurrentSchool() {
        return isCurrentSchool;
    }
}
