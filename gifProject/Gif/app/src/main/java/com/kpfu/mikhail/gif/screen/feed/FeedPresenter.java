package com.kpfu.mikhail.gif.screen.feed;

import com.kpfu.mikhail.gif.R;
import com.kpfu.mikhail.gif.repository.GifProvider;
import com.kpfu.mikhail.gif.screen.base.feed.BaseGifPresenter;
import com.kpfu.mikhail.gif.screen.base.feed.BaseGifView;

import ru.arturvasilov.rxloader.LifecycleHandler;

public class FeedPresenter extends BaseGifPresenter {

    FeedPresenter(LifecycleHandler lifecycleHandler, BaseGifView view) {
        super(lifecycleHandler, view);
    }

    @Override
    public void loadGifs() {
        GifProvider.provideGifRepository()
                .getFeed()
                .doOnSubscribe(getGifView()::showLoading)
                .doOnTerminate(getGifView()::hideLoading)
                .compose(mLifecycleHandler.load(R.id.feed_request))
                .subscribe(feedRequestModel -> getGifView().showGifList(feedRequestModel.getGifList()),
                        throwable -> getGifView().showErrorLoadingFeed());
    }

}
