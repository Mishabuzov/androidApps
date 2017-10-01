package com.kpfu.mikhail.vk.screen.base.fragments.base_fragment;


import com.kpfu.mikhail.vk.screen.base.fragments.error_fragment.ErrorView;

public interface BaseFragmentView extends ErrorView {

    void setToolbarTitle(String title);

    void finish();

}
