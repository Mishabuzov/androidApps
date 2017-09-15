package com.kpfu.mikhail.gif.screen.base.feed;

import android.support.annotation.NonNull;

import com.kpfu.mikhail.gif.R;
import com.kpfu.mikhail.gif.content.LikeRequestModel;
import com.kpfu.mikhail.gif.repository.GifProvider;

import ru.arturvasilov.rxloader.LifecycleHandler;

public abstract class BaseGifPresenter<View extends BaseGifView> {

    protected LifecycleHandler mLifecycleHandler;

    private View mView;

    public BaseGifPresenter(@NonNull LifecycleHandler lifecycleHandler,
                            @NonNull View view) {
        mLifecycleHandler = lifecycleHandler;
        mView = view;
    }

    protected View getGifView() {
        return mView;
    }

    public abstract void loadGifs();

    void loadGifInfo(String gifId) {
        GifProvider.provideGifRepository()
                .getGifInfo(gifId)
                .compose(mLifecycleHandler.reload(R.id.gif_info_request))
                .subscribe(model -> mView.onGifInfoLoaded(model.getDetails()));
    }

    void likeRequest(String gifId, String like) {
        GifProvider.provideGifRepository()
                .like(gifId, new LikeRequestModel(like))
                .compose(mLifecycleHandler.reload(R.id.like_request))
                .doOnError(throwable -> mView.onErrorLikeRequest())
                .subscribe();
    }

    public void addToFavoritesRequest(String gifId) {
        GifProvider.provideGifRepository()
                .addToFavorites(gifId)
                .compose(mLifecycleHandler.reload(R.id.add_to_favorites_request))
                .doOnError(throwable -> mView.onErrorFavoritesRequest())
                .subscribe();
    }

    public void deleteFromFavoritesRequest(String gifId) {
        GifProvider.provideGifRepository()
                .deleteFromFavorites(gifId)
                .compose(mLifecycleHandler.reload(R.id.add_to_favorites_request))
                .doOnError(throwable -> mView.onErrorFavoritesRequest())
                .subscribe();
    }

}
