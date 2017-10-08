package com.kpfu.mikhail.vk.screen.feed;

import com.kpfu.mikhail.vk.content.NewsLocal;
import com.kpfu.mikhail.vk.screen.base.fragments.base_fragment_recycler.BaseRecyclerFragment;
import com.kpfu.mikhail.vk.utils.Function;

import java.util.List;

public class FeedFragment
        extends BaseRecyclerFragment<FeedAdapter, NewsLocal, FeedView, FeedPresenter>
        implements FeedView {

    private FeedAdapter mAdapter;

    private FeedPresenter mPresenter;

    @Override
    protected void getArgs() {
    }

    @Override
    protected void doActions() {
        enablePagination(this);
    }

    @Override
    protected FeedAdapter initAdapter() {
        mAdapter = new FeedAdapter(getContext(), this);
        return mAdapter;
    }

    @Override
    public FeedPresenter initPresenter() {
        mPresenter = new FeedPresenter(this, getContext(), isDataEmpty());
        return mPresenter;
    }

    @Override
    public void showFeed(List<NewsLocal> newsList) {
        mAdapter.enablePaginationView(true);
        mAdapter.add(newsList);
    }

    @Override
    public void handleNetworkErrorByErrorScreen(Function reloadFunction) {
        super.handleNetworkError(reloadFunction);
    }

    @Override
    public void showNetworkErrorInAdapter() {
        mAdapter.showNetworkErrorView(true);
    }

    @Override
    protected void handleNetworkError(Function reloadFunction) {
        mPresenter.handleNetworkError(reloadFunction);
    }

}