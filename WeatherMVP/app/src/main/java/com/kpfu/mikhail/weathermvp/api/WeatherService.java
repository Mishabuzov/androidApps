package com.kpfu.mikhail.weathermvp.api;

import com.kpfu.mikhail.weathermvp.content.CurrentWeatherInfo;
import com.kpfu.mikhail.weathermvp.content.FullWeatherInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface WeatherService {

    @GET("/data/2.5/forecast/daily")
    Observable<FullWeatherInfo> getWeatherFor10DaysByCityName(@Query("q") String cityName, @Query("cnt") int cnt);

    @GET("/data/2.5/weather")
    Observable<CurrentWeatherInfo> getWeatherByCityName(@Query("q") String cityName);

}
