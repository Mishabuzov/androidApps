package com.kpfu.mikhail.gif.screen.details;

import android.support.annotation.NonNull;

class GifDetailsPresenter {

//    private final LifecycleHandler mLifecycleHandler;

    private final GifDetailsView mDetailsView;

    GifDetailsPresenter(
//            @NonNull LifecycleHandler lifecycleHandler,
                               @NonNull GifDetailsView detailsView) {
//        mLifecycleHandler = lifecycleHandler;
        mDetailsView = detailsView;
    }

    void setInfoIntoViews() {
        mDetailsView.showLoading();
        mDetailsView.bindViews();
        mDetailsView.hideLoading();
    }
}
