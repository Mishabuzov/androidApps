package com.kpfu.mikhail.vk.screen.base.fragments.base_fragment_recycler;

import com.kpfu.mikhail.vk.screen.base.fragments.base_fragment.BaseFragmentView;

public interface BaseRecyclerFragmentView extends BaseFragmentView {

    void configToolbarBehavior(int recyclerItemSize);

    void showScreenAndHideLoading();

    void showScreen();

    void hideScreenAndShowLoading();

    void hideScreen();

    void showEmptyView();

}
