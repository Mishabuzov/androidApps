package com.kpfu.mikhail.vk.content;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vk.sdk.api.model.VKApiModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsResponse extends VKApiModel {

    private News response;

    public News getResponse() {
        return response;
    }

    public void setResponse(News response) {
        this.response = response;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.response, flags);
    }

    public NewsResponse() {
    }

    protected NewsResponse(Parcel in) {
        this.response = in.readParcelable(News.class.getClassLoader());
    }

    public static final Parcelable.Creator<NewsResponse> CREATOR = new Parcelable.Creator<NewsResponse>() {
        @Override
        public NewsResponse createFromParcel(Parcel source) {
            return new NewsResponse(source);
        }

        @Override
        public NewsResponse[] newArray(int size) {
            return new NewsResponse[size];
        }
    };

}
