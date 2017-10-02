package com.kpfu.mikhail.vk.screen.feed;

import com.kpfu.mikhail.vk.content.NewsLocal;
import com.kpfu.mikhail.vk.screen.base.fragments.base_fragment_recycler.BaseRecyclerFragment;

import java.util.List;

public class FeedFragment extends BaseRecyclerFragment implements FeedView {

    private FeedAdapter mAdapter;

    @Override
    protected void getArgs() {
    }

    @Override
    protected void doActions() {
        getPresenter().connectData();
    }

    @Override
    protected FeedAdapter initAdapter() {
        mAdapter = new FeedAdapter(getContext());
        return mAdapter;
    }

    @Override
    public FeedPresenter initPresenter() {
        return new FeedPresenter(this, getContext());
    }

    @Override
    public void showFeed(List<NewsLocal> newsList) {
        mAdapter.changeDataSet(newsList);
    }

}