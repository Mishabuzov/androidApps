package com.kpfu.mikhail.vk.repository;

import android.support.annotation.NonNull;

import com.vk.sdk.api.VKRequest;

public interface VkRepository {

    @NonNull
    VKRequest getCurrentUserInfo();

    @NonNull
    VKRequest getNewsFeed();

}
