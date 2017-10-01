package com.kpfu.mikhail.vk.screen.feed;

import com.kpfu.mikhail.vk.content.NewsItem;
import com.kpfu.mikhail.vk.content.NewsLocal;
import com.kpfu.mikhail.vk.screen.base.fragments.base_fragment.BaseFragmentView;
import com.kpfu.mikhail.vk.screen.base.fragments.base_fragment_recycler.BaseRecyclerFragmentView;

import java.util.List;

interface FeedView extends BaseRecyclerFragmentView {

    void showFeed(List<NewsLocal> newsList);

}
