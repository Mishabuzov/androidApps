package com.kpfu.mikhail.vk.screen.base;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.kpfu.mikhail.vk.widget.progressbar.LoadingView;

public interface BaseView extends LoadingView {

//    void showErrorPage(String errorMessage);

    void showNetworkErrorMessage();

    void showToastMessage(@NonNull String message);

    void showToastMessage(@StringRes int message);

    void finish();

//    void showUnidentifiedError();

}
