package com.kpfu.mikhail.vk.repository;

import android.support.annotation.NonNull;

import com.kpfu.mikhail.vk.utils.AndroidUtils;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;

import java.util.Map;

import static com.kpfu.mikhail.vk.utils.Constants.DEFAULT_NEWS_COUNT;
import static com.kpfu.mikhail.vk.utils.Constants.NEWSFEED_REQUEST;
import static com.kpfu.mikhail.vk.utils.Constants.USER_FIELDS;
import static com.kpfu.mikhail.vk.utils.Constants.POST;
import static com.kpfu.mikhail.vk.utils.Constants.START_FROM;
import static com.kpfu.mikhail.vk.utils.Constants.VIEWS;
import static com.vk.sdk.api.VKApiConst.COUNT;
import static com.vk.sdk.api.VKApiConst.FILTERS;

public class DefaultVkRepository implements VkRepository {

    @NonNull
    @Override
    public VKRequest getCurrentUserInfo() {
        return VKApi.users()
                .get(VKParameters.from(VKApiConst.FIELDS, USER_FIELDS));
    }

    @NonNull
    @Override
    public VKRequest getNewsFeed(String startFrom) {
        Map<String, Object> paramsMap = AndroidUtils.getParamsMap();
        if (!startFrom.isEmpty()) {
            paramsMap.put(START_FROM, startFrom);
        }
        paramsMap.put(COUNT, DEFAULT_NEWS_COUNT);
        paramsMap.put(FILTERS, POST);
        return new VKRequest(NEWSFEED_REQUEST, VKParameters.from(START_FROM, startFrom,
                COUNT, DEFAULT_NEWS_COUNT, FILTERS, POST, VKApiConst.FIELDS, VIEWS));
    }

}
