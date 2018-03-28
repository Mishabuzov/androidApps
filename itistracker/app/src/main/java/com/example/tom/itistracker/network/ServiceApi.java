package com.example.tom.itistracker.network;

import com.example.tom.itistracker.models.network.Credentials;
import com.example.tom.itistracker.models.network.User;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceApi {

    @POST("auth")
    Single<User> login(@Body Credentials credentials);

}
