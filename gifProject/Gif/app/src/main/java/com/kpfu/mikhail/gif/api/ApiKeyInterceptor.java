package com.kpfu.mikhail.gif.api;

import android.support.annotation.NonNull;

import com.kpfu.mikhail.gif.utils.PreferenceUtils;

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
        // Request customization: add request headers
        Request.Builder requestBuilder;
        if (PreferenceUtils.isCurrentUserAuthorized()) {
            requestBuilder = original.newBuilder().addHeader("authorization", PreferenceUtils.getToken()).url(originalHttpUrl);
        } else {
            requestBuilder = original.newBuilder().url(originalHttpUrl);
        }
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
