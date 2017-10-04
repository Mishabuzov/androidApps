package com.kpfu.mikhail.vk.screen.feed;

import com.kpfu.mikhail.vk.content.NewsLocal;
import com.kpfu.mikhail.vk.screen.base.fragments.base_fragment_recycler.BaseRecyclerFragmentView;
import com.kpfu.mikhail.vk.utils.Function;

import java.util.List;

interface FeedView extends BaseRecyclerFragmentView<NewsLocal> {

    void showFeed(List<NewsLocal> newsList);

    void handleNetworkErrorByErrorScreen(Function reloadFunction);

}
