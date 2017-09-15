package com.fsep.sova.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseMediaModel implements Parcelable {

//    /**
//     * Идентификатор документа
//     */
//    protected long id;

    /*
     * Название файла
     */
    protected String name;

    /**
     * URL документа
     */
    protected String url;

    /**
     * MIME-тип изображения
     */
    protected String mimeType;

    public BaseMediaModel() {
    }

    protected BaseMediaModel(Parcel in) {
//        id = in.readLong();
        name = in.readString();
        url = in.readString();
        mimeType = in.readString();
    }

    public static final Creator<BaseMediaModel> CREATOR = new Creator<BaseMediaModel>() {
        @Override
        public BaseMediaModel createFromParcel(Parcel in) {
            return new BaseMediaModel(in);
        }

        @Override
        public BaseMediaModel[] newArray(int size) {
            return new BaseMediaModel[size];
        }
    };

//    public long getId() {
//        return id;
//    }

    public String getUrl() {
        return url;
    }

    public String getName() {return name;}

    public String getMimeType() {
        return mimeType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(url);
        parcel.writeString(mimeType);
    }
}
