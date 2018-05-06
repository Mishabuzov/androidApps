package com.kpfu.mikhail.weathermvp.repository;

import android.support.annotation.NonNull;

import com.kpfu.mikhail.weathermvp.api.ApiFactory;
import com.kpfu.mikhail.weathermvp.content.CurrentWeatherInfo;
import com.kpfu.mikhail.weathermvp.content.FullWeatherInfo;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.arturvasilov.rxloader.RxUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DefaultWeatherRepository implements WeatherRepository {

    @NonNull
    @Override
    public Observable<FullWeatherInfo> getWeatherFor10Days(@NonNull String cityName) {
        return ApiFactory.getWeatherService()
                .getWeatherFor10DaysByCityName(cityName, 10)
                .flatMap(fullWeatherInfo -> {
                    Realm.getDefaultInstance().executeTransaction(realm -> {
                        RealmResults<FullWeatherInfo> fullWeatherInfos = realm
                                .where(FullWeatherInfo.class)
                                .equalTo("city.name", fullWeatherInfo.getCity().getName())
                                .findAll();
                        fullWeatherInfos.deleteAllFromRealm();
                        realm.insert(fullWeatherInfo);
                    });
                    return Observable.just(fullWeatherInfo);
                })
                .onErrorResumeNext(throwable -> {
                    Realm realm = Realm.getDefaultInstance();
                    RealmResults<FullWeatherInfo> fullWeatherInfos = realm.where(FullWeatherInfo.class).findAll();
                    return Observable.just(realm.copyFromRealm(fullWeatherInfos).get(0));
                })
                .compose(RxUtils.async());
    }

    @NonNull
    @Override
    public Observable<List<CurrentWeatherInfo>> getCurrentWeather3(@NonNull List<String> cityNames) {
        List<Observable<CurrentWeatherInfo>> observables = new ArrayList<>();
        for (String cityName : cityNames) {
            Observable<CurrentWeatherInfo> observable =
                    ApiFactory.getWeatherService().getWeatherByCityName(cityName);
            observables.add(observable);
        }
        return Observable
                .combineLatest(observables, args -> {
                    List<CurrentWeatherInfo> currentWeatherInfos = new ArrayList<>();
                    for (Object value : args) {
                        CurrentWeatherInfo currentWeatherInfo = (CurrentWeatherInfo) value;
                        currentWeatherInfos.add(currentWeatherInfo);
                    }
                    return currentWeatherInfos;
                })
                .flatMap(currentWeatherInfos -> {
                    Realm.getDefaultInstance().executeTransaction(realm -> {
                        realm.delete(CurrentWeatherInfo.class);
                        realm.insert(currentWeatherInfos);
                    });
                    return Observable.just(currentWeatherInfos);
                })
                .onErrorResumeNext(throwable -> {
                    Realm realm = Realm.getDefaultInstance();
                    RealmResults<CurrentWeatherInfo> currentWeatherInfos = realm.where(CurrentWeatherInfo.class).findAll();
                    return Observable.just(realm.copyFromRealm(currentWeatherInfos));
                })
                .compose(RxUtils.async());
    }

}
