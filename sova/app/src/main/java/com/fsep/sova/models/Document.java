package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Document extends BaseMediaModel implements Parcelable {

    private String extension;
    private int size;

    public Document() {
    }

    protected Document(Parcel in) {
        super(in);
        extension = in.readString();
        size = in.readInt();
    }

    public static final Creator<Document> CREATOR = new Creator<Document>() {
        @Override
        public Document createFromParcel(Parcel in) {
            return new Document(in);
        }

        @Override
        public Document[] newArray(int size) {
            return new Document[size];
        }
    };

    public String getExtension() {
        return extension;
    }

    public int getSize() {
        return size;
    }

    public void writeToParcel(Parcel parcel, int i){
        super.writeToParcel(parcel, i);
        parcel.writeString(extension);
        parcel.writeInt(size);
    }
}
