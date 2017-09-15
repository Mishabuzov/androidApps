package com.kpfu.mikhail.gif.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoritesResponseModel {

    @SerializedName("publishedGfys")
    private List<Gif> favorites;

    public FavoritesResponseModel() {
    }

    @NonNull
    public List<Gif> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Gif> favorites) {
        this.favorites = favorites;
    }
}
