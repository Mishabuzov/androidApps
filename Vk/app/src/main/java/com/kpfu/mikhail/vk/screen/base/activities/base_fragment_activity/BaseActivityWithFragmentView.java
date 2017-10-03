package com.kpfu.mikhail.vk.screen.base.activities.base_fragment_activity;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;

import com.kpfu.mikhail.vk.screen.base.BaseView;
import com.kpfu.mikhail.vk.utils.Function;

public interface BaseActivityWithFragmentView extends BaseView {

    void setNewInstanceOfFragment(@NonNull Fragment fragment);

    void setToolbarBehavior(LinearLayoutManager llm, int size);

    void setToolbarBehavior(int scrollHeight,
                            int scrollPaddingTop,
                            int scrollPaddingBottom,
                            int childHeight);

    void turnOffToolbarScrolling();

    void turnOnToolbarScrolling();

    void setErrorScreenWithReloadButton(Function reloadFunction,
                                        @StringRes int errorText);

    void setToolbarTitle(String title);

    void setToolbarTitle(@StringRes int title);


}
