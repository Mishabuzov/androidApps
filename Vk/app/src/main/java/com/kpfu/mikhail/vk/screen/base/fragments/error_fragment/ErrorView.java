package com.kpfu.mikhail.vk.screen.base.fragments.error_fragment;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.kpfu.mikhail.vk.screen.base.BaseView;
import com.kpfu.mikhail.vk.utils.Function;

public interface ErrorView extends BaseView {

    void handleError(Throwable e, Function reloadFunction);

    void cleanDataAndExit();

}
