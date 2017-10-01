package com.kpfu.mikhail.vk.api;

import android.support.annotation.NonNull;

import com.kpfu.mikhail.vk.utils.PreferenceUtils;

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
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();
        // Request customization: add request headers
        Request.Builder requestBuilder;
        if (PreferenceUtils.isSignedIn()) {
            requestBuilder = original.newBuilder().addHeader("Authorization", PreferenceUtils.getToken()).url(originalHttpUrl);
        } else {
            requestBuilder = original.newBuilder().url(originalHttpUrl);
        }
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
