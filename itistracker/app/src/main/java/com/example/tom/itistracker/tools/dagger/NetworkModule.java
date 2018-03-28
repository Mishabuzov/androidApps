package com.example.tom.itistracker.tools.dagger;

import com.example.tom.itistracker.BuildConfig;
import com.example.tom.itistracker.network.ErrorHandlingFactory;
import com.example.tom.itistracker.network.ServiceApi;
import com.example.tom.itistracker.tools.Constants;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

/**
 * Created by Tom on 25.03.2018.
 */

@Module
public class NetworkModule {

    @Singleton
    @Provides
    HttpLoggingInterceptor provideInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(BuildConfig.USE_LOG ? BODY : NONE);
        return httpLoggingInterceptor;
    }

    @Singleton
    @Provides
    OkHttpClient provideHttpClient(HttpLoggingInterceptor logger) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS);
        httpClient.connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        httpClient.addInterceptor(logger);
        if (BuildConfig.DEBUG) {
            httpClient.networkInterceptors().add(new StethoInterceptor());
        }
        return httpClient.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.API_ENDPOINT)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(ErrorHandlingFactory.create())
                .build();
    }

    @Provides
    @Singleton
    ServiceApi provideServiceApi(Retrofit retrofit) {
        return retrofit.create(ServiceApi.class);
    }

}
