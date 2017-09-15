package com.kpfu.mikhail.gif.screen.base.navigation;

import android.support.annotation.NonNull;

class NavigationPresenter {

    @NonNull private final NavigationView mView;

    NavigationPresenter(@NonNull NavigationView view) {
        mView = view;
    }

    void openNavigation() {
        mView.openNavigationMenu();
    }

    void closeNavigation() {
        mView.closeNavigationMenu();
    }

    void showFeed() {
        mView.showFeed();
    }
}
