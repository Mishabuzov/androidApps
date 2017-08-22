package com.kpfu.mikhail.weathermvp.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class WeatherInfo extends RealmObject {

    @PrimaryKey
    @SerializedName("dt")
    private long date;

    @SerializedName("temp")
    private Temperature temperature;

    private RealmList<Weather> weather;

    private double pressure;

    private int humidity;

    private double speed;

    public WeatherInfo(long date, Temperature temperature, RealmList<Weather> weather, double pressure, int humidity, double speed) {
        this.date = date;
        this.temperature = temperature;
        this.weather = weather;
        this.pressure = pressure;
        this.humidity = humidity;
        this.speed = speed;
    }

    public long getDate() {
        return date;
    }

    @NonNull
    public List<Weather> getWeather() {
        return weather;
    }

    @NonNull
    public Temperature getTemperature() {
        return temperature;
    }

    public String getHumanReadableDate() {
        Date date = new Date(getDate() * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        return sdf.format(date);
    }

    public WeatherInfo() {
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public void setWeather(RealmList<Weather> weather) {
        this.weather = weather;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
