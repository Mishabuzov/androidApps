package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Job implements Parcelable {

    private CountryCity country;
    private CountryCity city;
    private String companyTitle;
    private String post;
    private long beginingDate;
    private long endingDate;
    private boolean isCurrentJob;

    public Job() {
    }

    public Job(CountryCity country, CountryCity city, String companyTitle, String post, long beginingDate, long endingDate, boolean isCurrentJob) {
        this.country = country;
        this.city = city;
        this.companyTitle = companyTitle;
        this.post = post;
        this.beginingDate = beginingDate;
        this.endingDate = endingDate;
        this.isCurrentJob = isCurrentJob;
    }

    protected Job(Parcel in) {
        country = in.readParcelable(CountryCity.class.getClassLoader());
        city = in.readParcelable(CountryCity.class.getClassLoader());
        companyTitle = in.readString();
        post = in.readString();
        beginingDate = in.readLong();
        endingDate = in.readLong();
        isCurrentJob = in.readByte() != 0;
    }

    public static final Creator<Job> CREATOR = new Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel source) {
            return new Job(source);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(country, flags);
        dest.writeParcelable(city, flags);
        dest.writeString(companyTitle);
        dest.writeString(post);
        dest.writeLong(beginingDate);
        dest.writeLong(endingDate);
        dest.writeByte((byte) (isCurrentJob ? 1 : 0));
    }

    public CountryCity getCountry() {
        return country;
    }

    public CountryCity getCity() {
        return city;
    }

    public String getPost() {
        return post;
    }

    public String getCompanyTitle() {
        return companyTitle;
    }

    public long getBeginingDate() {
        return beginingDate;
    }

    public boolean isCurrentJob() {
        return isCurrentJob;
    }

    public long getEndingDate() {
        return endingDate;
    }
}
