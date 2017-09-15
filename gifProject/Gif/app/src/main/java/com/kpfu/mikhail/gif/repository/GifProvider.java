package com.kpfu.mikhail.gif.repository;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

public final class GifProvider {

    private static GifRepository sGifRepository;

    private GifProvider() {
    }

    @NonNull
    public static GifRepository provideGifRepository() {
        if (sGifRepository == null) {
            sGifRepository = new DefaultGifRepository();
        }
        return sGifRepository;
    }

    public static void setGifRepository(@NonNull GifRepository gifRepository) {
        sGifRepository = gifRepository;
    }

    @MainThread
    public static void init() {
        sGifRepository = new DefaultGifRepository();
    }

}
