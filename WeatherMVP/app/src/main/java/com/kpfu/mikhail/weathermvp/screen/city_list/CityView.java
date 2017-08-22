package com.kpfu.mikhail.weathermvp.screen.city_list;

import android.support.annotation.NonNull;

import com.kpfu.mikhail.weathermvp.content.CurrentWeather;
import com.kpfu.mikhail.weathermvp.content.CurrentWeatherInfo;
import com.kpfu.mikhail.weathermvp.content.FullWeatherInfo;
import com.kpfu.mikhail.weathermvp.content.Weather;
import com.kpfu.mikhail.weathermvp.content.WeatherInfo;
import com.kpfu.mikhail.weathermvp.screen.general.LoadingView;

import java.util.List;

public interface CityView extends LoadingView {

    void showWeather(@NonNull List<CurrentWeatherInfo> currentWeatherInfos);

    void goToFullWeatherScreen(@NonNull String cityName);

    void showError();
}
