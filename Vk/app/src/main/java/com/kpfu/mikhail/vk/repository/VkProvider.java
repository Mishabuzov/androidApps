package com.kpfu.mikhail.vk.repository;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

public final class VkProvider {

    private static VkRepository sVkRepository;

    private VkProvider() {
    }

    @NonNull
    public static VkRepository provideVkRepository() {
        if (sVkRepository == null) {
            sVkRepository = new DefaultVkRepository();
        }
        return sVkRepository;
    }

    @MainThread
    public static void init() {
        sVkRepository = new DefaultVkRepository();
    }

}
