package com.kpfu.mikhail.weathermvp.repository;

import android.support.annotation.NonNull;

import com.kpfu.mikhail.weathermvp.content.CurrentWeatherInfo;
import com.kpfu.mikhail.weathermvp.content.FullWeatherInfo;

import java.util.List;

import rx.Observable;

public interface WeatherRepository {

    @NonNull
    Observable<FullWeatherInfo> getWeatherFor10Days(@NonNull String cityName);

    @NonNull
    Observable<List<CurrentWeatherInfo>> getCurrentWeather3(@NonNull List<String> cityNames);

}
