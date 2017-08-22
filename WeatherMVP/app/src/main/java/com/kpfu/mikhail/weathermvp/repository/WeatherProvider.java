package com.kpfu.mikhail.weathermvp.repository;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

public final class WeatherProvider {

    private static WeatherRepository sWeatherRepository;

    private WeatherProvider() {
    }

    @NonNull
    public static WeatherRepository provideWeatherRepository() {
        if (sWeatherRepository == null) {
            sWeatherRepository = new DefaultWeatherRepository();
        }
        return sWeatherRepository;
    }

    public static void setWeatherRepository(@NonNull WeatherRepository weatherRepository) {
        sWeatherRepository = weatherRepository;
    }

    @MainThread
    public static void init() {
        sWeatherRepository = new DefaultWeatherRepository();
    }

}
