package com.kpfu.mikhail.weathermvp.screen.city_list;

import android.support.annotation.NonNull;

import com.kpfu.mikhail.weathermvp.R;
import com.kpfu.mikhail.weathermvp.content.CurrentWeather;
import com.kpfu.mikhail.weathermvp.content.CurrentWeatherInfo;
import com.kpfu.mikhail.weathermvp.content.FullWeatherInfo;
import com.kpfu.mikhail.weathermvp.repository.WeatherProvider;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.arturvasilov.rxloader.LifecycleHandler;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func3;

public class CityPresenter {

    private final LifecycleHandler mLifecycleHandler;

    private final CityView mCityView;

    public CityPresenter(@NonNull LifecycleHandler lifecycleHandler,
                         @NonNull CityView cityView){
        mLifecycleHandler = lifecycleHandler;
        mCityView = cityView;
    }

    public void dispatchCreate(){
        WeatherProvider.provideWeatherRepository()
                .getCurrentWeather3(getCityNames())
                .doOnSubscribe(mCityView::showLoading)
                .doOnTerminate(mCityView::hideLoading)
                .compose(mLifecycleHandler.load(R.id.city_request))  //rx - Лоадер
                .subscribe(mCityView::showWeather, throwable -> mCityView.showError());
    }

    private List<String> getCityNames(){
        List<String> cityNames = new ArrayList<>();
        cityNames.add("Kazan");
        cityNames.add("Moscow");
        cityNames.add("London");
        cityNames.add("Amsterdam");
        cityNames.add("Tokyo");
        return cityNames;
    }

    public void onItemClick(String cityName){
        mCityView.goToFullWeatherScreen(cityName);
    }

}
