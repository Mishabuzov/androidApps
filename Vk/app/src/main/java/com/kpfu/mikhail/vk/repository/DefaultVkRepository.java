package com.kpfu.mikhail.vk.repository;

import android.support.annotation.NonNull;

import com.kpfu.mikhail.vk.utils.PreferenceUtils;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;

import static com.kpfu.mikhail.vk.utils.AndroidUtils.getCurrentTimeInUnixFormat;
import static com.kpfu.mikhail.vk.utils.Constants.COUNT_PER_PAGE;
import static com.kpfu.mikhail.vk.utils.Constants.FILTERS_VALUES;
import static com.kpfu.mikhail.vk.utils.Constants.NEWSFEED_REQUEST;
import static com.kpfu.mikhail.vk.utils.Constants.START_FROM;
import static com.kpfu.mikhail.vk.utils.Constants.START_TIME;
import static com.kpfu.mikhail.vk.utils.Constants.USER_FIELDS;
import static com.kpfu.mikhail.vk.utils.Constants.VIEWS;
import static com.vk.sdk.api.VKApiConst.COUNT;
import static com.vk.sdk.api.VKApiConst.FIELDS;
import static com.vk.sdk.api.VKApiConst.FILTERS;

public class DefaultVkRepository implements VkRepository {

    @NonNull
    @Override
    public VKRequest getCurrentUserInfo() {
        return VKApi.users()
                .get(VKParameters.from(FIELDS, USER_FIELDS));
    }

    @NonNull
    @Override
    public VKRequest getNewsFeed() {
      /*  Map<String, Object> paramsMap = AndroidUtils.getParamsMap();
        if (!startFrom.isEmpty()) {
            paramsMap.put(START_FROM, startFrom);
        }
        paramsMap.put(COUNT, COUNT_PER_PAGE);
        paramsMap.put(FILTERS, FILTERS_VALUES);*/
        return new VKRequest(NEWSFEED_REQUEST, VKParameters.from(
                START_FROM, PreferenceUtils.getStartFromValue(),
                COUNT, COUNT_PER_PAGE,
                FILTERS, FILTERS_VALUES,
                START_TIME, getCurrentTimeInUnixFormat(),
                FIELDS, VIEWS));
    }

}
