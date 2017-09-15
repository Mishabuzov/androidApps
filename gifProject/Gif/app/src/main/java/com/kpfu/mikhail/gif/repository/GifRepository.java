package com.kpfu.mikhail.gif.repository;

import android.support.annotation.NonNull;

import com.kpfu.mikhail.gif.content.DetailsResponseModel;
import com.kpfu.mikhail.gif.content.FavoritesResponseModel;
import com.kpfu.mikhail.gif.content.FeedResponseModel;
import com.kpfu.mikhail.gif.content.LikeRequestModel;
import com.kpfu.mikhail.gif.content.LoginForm;
import com.kpfu.mikhail.gif.content.RegistrationForm;
import com.kpfu.mikhail.gif.content.Token;

import rx.Observable;

public interface GifRepository {

    @NonNull
    Observable<FeedResponseModel> getFeed();

    @NonNull
    Observable<FavoritesResponseModel> getFavorites();

    @NonNull
    Observable<DetailsResponseModel> getGifInfo(String gifId);

    @NonNull
    Observable<Token> authorize(LoginForm loginForm);

    @NonNull
    Observable<Token> getRegistrationKey();

    @NonNull
    Observable<Token> registerUser(RegistrationForm form);

    Observable<Void> like(String gifId, LikeRequestModel likeRequestModel);

    Observable<Void> addToFavorites(String gifId);

    Observable<Void> deleteFromFavorites(String gifId);
}
