package com.kpfu.mikhail.weathermvp.screen.weather;

import com.kpfu.mikhail.weathermvp.content.FullWeatherInfo;
import com.kpfu.mikhail.weathermvp.screen.general.LoadingView;

public interface WeatherView extends LoadingView {

    void showFullWeatherList(FullWeatherInfo fullWeatherInfo);

    void showError();
}
