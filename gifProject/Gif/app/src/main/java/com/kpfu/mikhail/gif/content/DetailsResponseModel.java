package com.kpfu.mikhail.gif.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class DetailsResponseModel {

    @SerializedName("gfyItem")
    private GifInfo details;

    public DetailsResponseModel(GifInfo details) {
        this.details = details;
    }

    public DetailsResponseModel() {
    }

    @NonNull
    public GifInfo getDetails() {
        return details;
    }

    public void setDetails(GifInfo details) {
        this.details = details;
    }
}
