package com.kpfu.mikhail.gif.screen.full_screen;

class FullScreenPresenter {

    private FullScreenView mView;

    FullScreenPresenter(FullScreenView view) {
        mView = view;
    }

    void startLoadingGif() {
        mView.showLoading();
        mView.loadGif();
    }
}
