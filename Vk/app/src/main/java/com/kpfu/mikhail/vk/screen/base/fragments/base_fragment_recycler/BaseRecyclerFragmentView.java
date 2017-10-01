package com.kpfu.mikhail.vk.screen.base.fragments.base_fragment_recycler;

import com.kpfu.mikhail.vk.content.NewsItem;
import com.kpfu.mikhail.vk.screen.base.fragments.base_fragment.BaseFragmentView;

import java.util.List;

public interface BaseRecyclerFragmentView extends BaseFragmentView {

    void configToolbarBehavior(int recyclerItemSize);

    void showScreenAndHideLoading();

    void showScreen();

    void hideScreenAndShowLoading();

    void hideScreen();

}
