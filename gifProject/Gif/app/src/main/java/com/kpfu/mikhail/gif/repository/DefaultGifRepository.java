package com.kpfu.mikhail.gif.repository;

import android.support.annotation.NonNull;

import com.kpfu.mikhail.gif.BuildConfig;
import com.kpfu.mikhail.gif.api.ApiFactory;
import com.kpfu.mikhail.gif.content.DetailsResponseModel;
import com.kpfu.mikhail.gif.content.FavoritesResponseModel;
import com.kpfu.mikhail.gif.content.FeedResponseModel;
import com.kpfu.mikhail.gif.content.LikeRequestModel;
import com.kpfu.mikhail.gif.content.LoginForm;
import com.kpfu.mikhail.gif.content.RegistrationAccessKey;
import com.kpfu.mikhail.gif.content.RegistrationForm;
import com.kpfu.mikhail.gif.content.Token;

import ru.arturvasilov.rxloader.RxUtils;
import rx.Observable;

class DefaultGifRepository implements GifRepository {

    @NonNull
    @Override
    public Observable<FeedResponseModel> getFeed() {
        return ApiFactory.getGifService()
                .getFeed(20)
                .compose(RxUtils.async());
    }

    @NonNull
    @Override
    public Observable<FavoritesResponseModel> getFavorites() {
        return ApiFactory.getGifService()
                .getFavorites()
                .compose(RxUtils.async());
    }

    @NonNull
    @Override
    public Observable<DetailsResponseModel> getGifInfo(String gifId) {
        return ApiFactory.getGifService()
                .getGifInfo(gifId)
                .compose(RxUtils.async());
    }

    @NonNull
    @Override
    public Observable<Token> authorize(LoginForm loginForm) {
        return ApiFactory.getGifRegAuthService()
                .getAuthToken(loginForm)
                .compose(RxUtils.async());
    }

    @NonNull
    @Override
    public Observable<Token> getRegistrationKey() {
        return ApiFactory.getGifRegAuthService()
                .getRegistrationToken(new RegistrationAccessKey(BuildConfig.ACCESS_KEY))
                .compose(RxUtils.async());
    }

    @NonNull
    @Override
    public Observable<Token> registerUser(RegistrationForm form) {
        return ApiFactory.getGifService()
                .registerUser(form)
                .compose(RxUtils.async());
    }

    @Override
    public Observable<Void> like(String gifId, LikeRequestModel likeRequestModel) {
        return ApiFactory.getGifService()
                .like(gifId, likeRequestModel)
                .compose(RxUtils.async());
    }

    @Override
    public Observable<Void> addToFavorites(String gifId) {
        return ApiFactory.getGifService()
                .addToFavorites(gifId)
                .compose(RxUtils.async());
    }

    @Override
    public Observable<Void> deleteFromFavorites(String gifId) {
        return ApiFactory.getGifService()
                .deleteFromFavorites(gifId)
                .compose(RxUtils.async());
    }

}
