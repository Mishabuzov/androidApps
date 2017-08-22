package com.kpfu.mikhail.weathermvp.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CurrentWeatherInfo extends RealmObject {

    public CurrentWeatherInfo() {
    }

    @SerializedName("main")
    private CurrentWeather mCurrentWeather;

    @PrimaryKey
    private String name;

    public CurrentWeatherInfo(@NonNull CurrentWeather currentWeather, @NonNull String name) {
        mCurrentWeather = currentWeather;
        this.name = name;
    }

    @NonNull
    public CurrentWeather getCurrentWeather() {
        return mCurrentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        mCurrentWeather = currentWeather;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
