package com.kpfu.mikhail.weathermvp.content;

import android.support.annotation.NonNull;

import io.realm.RealmObject;

public class Weather extends RealmObject {
    private String main;
    private String description;

    public Weather(String main, String description) {
        this.main = main;
        this.description = description;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    @NonNull
    public String getMain() {
        return main;
    }

    public Weather() {
    }

    public void setMain(String main) {
        this.main = main;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
