package com.kpfu.mikhail.gif.api;

import com.kpfu.mikhail.gif.content.LoginForm;
import com.kpfu.mikhail.gif.content.RegistrationAccessKey;
import com.kpfu.mikhail.gif.content.Token;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface GifRegAuthService {

    @POST("/oauth/webtoken")
    Observable<Token> getRegistrationToken(@Body RegistrationAccessKey accessKey);

    @POST("/oauth/weblogin")
    Observable<Token> getAuthToken(@Body LoginForm loginForm);

}
