package com.kpfu.mikhail.vk.screen.base.fragments.base_fragment;


import android.os.Parcelable;

import com.kpfu.mikhail.vk.screen.base.fragments.error_fragment.ErrorView;

import java.util.ArrayList;

public interface BaseFragmentView<T extends Parcelable> extends ErrorView {

    void setToolbarTitle(String title);

    void finish();

    void saveData(ArrayList<T> data);

}
