package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Resume implements Parcelable {

    private long birthday;
    private CountryCity country;
    private CountryCity city;
    private ArrayList<String> skills;
    private ArrayList<Job> experience;
    private ArrayList<School> secondaryEducation;
    private ArrayList<University> higherEducation;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.birthday);
        dest.writeParcelable(this.country, flags);
        dest.writeParcelable(this.city, flags);
        dest.writeStringList(this.skills);
        dest.writeTypedList(this.experience);
        dest.writeTypedList(this.secondaryEducation);
        dest.writeTypedList(this.higherEducation);
    }

    public Resume() {
    }

    public Resume(long birthday, CountryCity country, CountryCity city, ArrayList<String> skills, ArrayList<Job> experience, ArrayList<School> secondaryEducation, ArrayList<University> higherEducation) {
        this.birthday = birthday;
        this.country = country;
        this.city = city;
        this.skills = skills;
        this.experience = experience;
        this.secondaryEducation = secondaryEducation;
        this.higherEducation = higherEducation;
    }

    protected Resume(Parcel in) {
        this.birthday = in.readLong();
        this.country = in.readParcelable(CountryCity.class.getClassLoader());
        this.city = in.readParcelable(CountryCity.class.getClassLoader());
        this.skills = in.createStringArrayList();
        this.experience = in.createTypedArrayList(Job.CREATOR);
        this.secondaryEducation = in.createTypedArrayList(School.CREATOR);
        this.higherEducation = in.createTypedArrayList(University.CREATOR);
    }

    public static final Creator<Resume> CREATOR = new Creator<Resume>() {
        @Override
        public Resume createFromParcel(Parcel source) {
            return new Resume(source);
        }

        @Override
        public Resume[] newArray(int size) {
            return new Resume[size];
        }
    };

    public long getBirthday() {
        return birthday;
    }

    public CountryCity getCountry() {
        return country;
    }

    public CountryCity getCity() {
        return city;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public ArrayList<Job> getExperience() {
        return experience;
    }

    public ArrayList<School> getSecondaryEducation() {
        return secondaryEducation;
    }

    public ArrayList<University> getHigherEducation() {
        return higherEducation;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public void setCountry(CountryCity country) {
        this.country = country;
    }

    public void setCity(CountryCity city) {
        this.city = city;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public void setExperience(ArrayList<Job> experience) {
        this.experience = experience;
    }

    public void setSecondaryEducation(ArrayList<School> secondaryEducation) {
        this.secondaryEducation = secondaryEducation;
    }

    public void setHigherEducation(ArrayList<University> higherEducation) {
        this.higherEducation = higherEducation;
    }
}
