package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Originality implements Parcelable {

    //Уникальность логина/телефона
    private boolean isUnique;

    public Originality(boolean isUnique) {
        this.isUnique = isUnique;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public void setIsUnique(boolean isUnique) {
        this.isUnique = isUnique;
    }

    protected Originality (Parcel in){
        isUnique = in.readByte() !=0;
    }



    public static final Creator<Originality> CREATOR = new Creator<Originality>() {
        @Override
        public Originality createFromParcel(Parcel in) {
            return new Originality(in);
        }

        @Override
        public Originality[] newArray(int size) {
            return new Originality[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (isUnique? 1:0));
    }
}
