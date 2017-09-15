package com.kpfu.mikhail.gif.screen.feed;

import com.kpfu.mikhail.gif.R;
import com.kpfu.mikhail.gif.screen.base.feed.BaseGifPresenter;
import com.kpfu.mikhail.gif.screen.base.feed.BaseRecyclerGifFragment;

import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

public class FeedFragment extends BaseRecyclerGifFragment {

    @Override
    public BaseGifPresenter initPresenter() {
        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(getContext(),
                getActivity().getSupportLoaderManager());
        return new FeedPresenter(lifecycleHandler, this);
    }

    @Override
    protected int setToolbarTitle() {
        return R.string.menu_feed_item;
    }
}
