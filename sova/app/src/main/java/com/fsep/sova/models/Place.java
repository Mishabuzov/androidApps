package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Place implements Parcelable {

    /**
     * Название места проведения
     */
    private String placeName;

    /**
     * Координаты места проведения
     */
    private Location location;

    public Place() {
    }

    protected Place(Parcel in) {
        placeName = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public String getPlaceName() {
        return placeName;
    }

    public Location getLocation() {
        return location;
    }

    public void setPlaceName(String name) {
        this.placeName = name;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(placeName);
        parcel.writeParcelable(location, i);
    }
}
