package com.kpfu.mikhail.gif.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedResponseModel {

    public FeedResponseModel() {
    }

    @SerializedName("gfycats")
    private List<Gif> mGifList;

    public FeedResponseModel(List<Gif> gifList) {
        mGifList = gifList;
    }

    @NonNull
    public List<Gif> getGifList() {
        return mGifList;
    }

    public void setGifList(List<Gif> gifList) {
        mGifList = gifList;
    }
}
