package com.fsep.sova.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.fsep.sova.BuildConfig;
import com.fsep.sova.utils.AndroidUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class SessionRestManager {

  private static volatile SessionRestManager sInstance;

  private SessionRestManager() {
  }

  public static SessionRestManager getInstance() {
    if (sInstance == null) {
      synchronized (SessionRestManager.class) {
        if (sInstance == null) sInstance = new SessionRestManager();
      }
    }
    return sInstance;
  }

  private OkHttpClient setupHttpClient() {
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    httpClient.addInterceptor(chain -> {
      Request original = chain.request();

      Request request = original.newBuilder()
          .header("Accept", "application/json")
          .method(original.method(), original.body())
          .build();

      return chain.proceed(request);
    });

    httpClient.addInterceptor(new AuthenticationInterceptor());

    // Задаём "уровень" логирования запросов
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    // set your desired log level
    loggingInterceptor.setLevel(
        BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
    httpClient.addInterceptor(loggingInterceptor);

    if (BuildConfig.DEBUG) httpClient.networkInterceptors().add(new StethoInterceptor());

    return httpClient.build();
  }

  private OkHttpClient setupStorageHttpClient() {
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    // Задаём "уровень" логирования запросов
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    // set your desired log level
    loggingInterceptor.setLevel(
        BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
    httpClient.addInterceptor(loggingInterceptor);

    if (BuildConfig.DEBUG) httpClient.networkInterceptors().add(new StethoInterceptor());

    return httpClient.build();
  }

  private final Retrofit REST_ADAPTER =
      new Retrofit.Builder().baseUrl(AndroidUtils.getRestEndpoint())
          .client(setupHttpClient())
          .addConverterFactory(JacksonConverterFactory.create())
          .build();

  private final Retrofit REST_STORAGE_ADAPTER =
      new Retrofit.Builder().baseUrl(AndroidUtils.getStorageRestEndpoint())
          .client(setupStorageHttpClient())
          .addConverterFactory(JacksonConverterFactory.create())
          .build();

  public SOVARest getRest() {
    return REST_ADAPTER.create(SOVARest.class);
  }

  public SOVAStorageRest getStorageRest() {
    return REST_STORAGE_ADAPTER.create(SOVAStorageRest.class);
  }
}
