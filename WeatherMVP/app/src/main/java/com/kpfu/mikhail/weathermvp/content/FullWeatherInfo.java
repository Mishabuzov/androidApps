package com.kpfu.mikhail.weathermvp.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class FullWeatherInfo extends RealmObject {

    public FullWeatherInfo() {
    }

    @SerializedName("list")
    private RealmList<WeatherInfo> mWeatherInfos;

    private City city;

    public FullWeatherInfo(RealmList<WeatherInfo> weatherInfos, City city) {
        mWeatherInfos = weatherInfos;
        this.city = city;
    }

    @NonNull
    public RealmList<WeatherInfo> getWeatherInfos() {
        return mWeatherInfos;
    }

    public void setWeatherInfos(RealmList<WeatherInfo> weatherInfos) {
        this.mWeatherInfos = weatherInfos;
    }

    @NonNull
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

}
