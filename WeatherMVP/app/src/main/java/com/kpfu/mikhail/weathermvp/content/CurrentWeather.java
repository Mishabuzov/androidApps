package com.kpfu.mikhail.weathermvp.content;

import io.realm.RealmObject;

public class CurrentWeather extends RealmObject {

    public CurrentWeather() {
    }

    private double temp;

    public CurrentWeather(double temp) {
        this.temp = temp;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
