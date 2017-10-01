package com.kpfu.mikhail.vk.api;

import com.kpfu.mikhail.vk.content.News;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface VkService {

    /*@GET("newsfeed.get")
    Observable<Token> login(@Query("start_from") String grantType,
                            @Query("count") String username);
*/
    @GET("newsfeed.get")
    Observable<News> getNews(@Query("start_from") String grantType,
                             @Query("count") String username);

}
