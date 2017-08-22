package com.kpfu.mikhail.weathermvp.screen.weather;

import com.kpfu.mikhail.weathermvp.R;
import com.kpfu.mikhail.weathermvp.content.FullWeatherInfo;
import com.kpfu.mikhail.weathermvp.repository.WeatherProvider;

import ru.arturvasilov.rxloader.LifecycleHandler;
import rx.functions.Action1;

public class WeatherPresenter {

    private final LifecycleHandler mLifecycleHandler;
    private final WeatherView mView;
    private String mCityName;

    public WeatherPresenter(LifecycleHandler lifecycleHandler, WeatherView view, String cityName) {
        mLifecycleHandler = lifecycleHandler;
        mView = view;
        mCityName = cityName;
    }

    public void init() {
        WeatherProvider.provideWeatherRepository()
                .getWeatherFor10Days(mCityName)
                .doOnSubscribe(mView::showLoading)
                .doOnTerminate(mView::hideLoading)
                .compose(mLifecycleHandler.load(R.id.weather_request))
                .subscribe(mView::showFullWeatherList, throwable -> mView.showError());
    }

}
