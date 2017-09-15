package com.kpfu.mikhail.gif.api;

import com.kpfu.mikhail.gif.content.DetailsResponseModel;
import com.kpfu.mikhail.gif.content.FavoritesResponseModel;
import com.kpfu.mikhail.gif.content.FeedResponseModel;
import com.kpfu.mikhail.gif.content.LikeRequestModel;
import com.kpfu.mikhail.gif.content.RegistrationForm;
import com.kpfu.mikhail.gif.content.Token;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface GifService {

    @GET("/v1/gfycats/trending")
    Observable<FeedResponseModel> getFeed(@Query("count") int count);

    @GET("/v1/me/gfycats/{gfyId}/full")
    Observable<DetailsResponseModel> getGifInfo(@Path("gfyId") String gifId);

    @POST("/v1/users")
    Observable<Token> registerUser(@Body RegistrationForm form);

    @PUT("v1/me/gfycats/{gfyId}/like ")
    Observable<Void> like(@Path("gfyId") String gifId, @Body LikeRequestModel like);

    @PUT("/v1/me/bookmarks/contents/{gfyId}")
    Observable<Void> addToFavorites(@Path("gfyId") String gifId);

    @DELETE("/v1/me/bookmarks/contents/{gfyId}")
    Observable<Void> deleteFromFavorites(@Path("gfyId") String gifId);

    @GET("/v1/me/bookmark-folders/3")
    Observable<FavoritesResponseModel> getFavorites();

}
