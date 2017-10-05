package com.kpfu.mikhail.vk.screen.base.fragments.base_fragment_recycler;

import android.os.Parcelable;

import com.kpfu.mikhail.vk.screen.base.fragments.base_fragment.BaseFragmentView;

public interface BaseRecyclerFragmentView<Data extends Parcelable> extends BaseFragmentView<Data> {

    void configToolbarBehavior(int recyclerItemSize);

    void showScreenAndHideLoading();

    void showScreen();

    void hideScreenAndShowLoading();

    void hideScreen();

    void showEmptyView();

    void hideEmptyView();

    void showReloadFooterInterface();

}
