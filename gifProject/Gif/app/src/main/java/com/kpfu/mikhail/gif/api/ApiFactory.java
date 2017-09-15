package com.kpfu.mikhail.gif.api;

import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.kpfu.mikhail.gif.BuildConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static OkHttpClient sClient;

    private static volatile GifService sService;

    private static volatile GifRegAuthService sRegService;

    private ApiFactory() {
    }

    @NonNull
    public static GifService getGifService() {
        GifService service = sService;
        if (service == null) {
            synchronized (ApiFactory.class) {
                service = sService;
                if (service == null) {
                    service = sService = buildRetrofit().create(GifService.class);
                }
            }
        }
        return service;
    }

    @NonNull
    public static GifRegAuthService getGifRegAuthService() {
        GifRegAuthService service = sRegService;
        if (service == null) {
            synchronized (ApiFactory.class) {
                service = sRegService;
                if (service == null) {
                    service = sRegService = buildRegRetrofit().create(GifRegAuthService.class);
                }
            }
        }
        return service;
    }

    public static void recreate() {
        sClient = null;
        sClient = getClient();
        sService = buildRetrofit().create(GifService.class);
        sRegService = buildRegRetrofit().create(GifRegAuthService.class);
    }

    @NonNull
    private static Retrofit buildRegRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT_REG_AUTH)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @NonNull
    private static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //rx обертка
                .build();
    }

    @NonNull
    private static OkHttpClient getClient() {
        OkHttpClient client = sClient;
        if (client == null) {
            synchronized (ApiFactory.class) {
                client = sClient;
                if (client == null) {
                    client = sClient = buildClient();
                }
            }
        }
        return client;
    }

    @NonNull
    private static OkHttpClient buildClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(ApiKeyInterceptor.create());
        if (BuildConfig.DEBUG) {
            httpClient.networkInterceptors().add(new StethoInterceptor());
        }
        return httpClient.build();
    }

}
