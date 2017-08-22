package com.kpfu.mikhail.weathermvp.api;

import android.support.annotation.NonNull;

import com.kpfu.mikhail.weathermvp.Config;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiKeyInterceptor implements Interceptor {

    @NonNull
    public static Interceptor create() {
        return new ApiKeyInterceptor();
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url =
                originalHttpUrl.newBuilder()
                        .addQueryParameter("appid", Config.APPLICATION_ID)
                        .addQueryParameter("units", Config.UNITS)
                        .addQueryParameter("cnt", Config.CNT)
                        .addQueryParameter("lang", Config.LANG)
                        .build();



        // Request customization: add request headers
        Request.Builder requestBuilder = original.newBuilder().url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
