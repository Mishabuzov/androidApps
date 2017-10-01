package com.kpfu.mikhail.vk.screen.base.activities.base_fragment_activity;

import android.support.annotation.NonNull;

class BaseActivityWithFragmentPresenter {

    @NonNull
    private final BaseActivityWithFragmentView mView;

    BaseActivityWithFragmentPresenter(@NonNull BaseActivityWithFragmentView view) {
        mView = view;
    }

    void defineToolbarScrollingState(int lastVisibleItemPosition, int size) {
        if (lastVisibleItemPosition == size - 1) {
            mView.turnOffToolbarScrolling();
        } else {
            mView.turnOnToolbarScrolling();
        }
    }

    void defineToolbarScrollingState(int scrollViewHeight,
                                     int scrollViewPaddingTop,
                                     int scrollViewPaddingBottom,
                                     int childHeight) {
        boolean isScrollable = scrollViewHeight < childHeight
                + scrollViewPaddingTop + scrollViewPaddingBottom;
        if (isScrollable) {
            mView.turnOnToolbarScrolling();
        } else {
            mView.turnOffToolbarScrolling();
        }
    }

}
