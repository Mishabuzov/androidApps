package com.kpfu.mikhail.gif.screen.favorites;

import com.kpfu.mikhail.gif.R;
import com.kpfu.mikhail.gif.content.Gif;
import com.kpfu.mikhail.gif.repository.GifProvider;
import com.kpfu.mikhail.gif.screen.base.feed.BaseGifPresenter;

import retrofit2.adapter.rxjava.HttpException;
import ru.arturvasilov.rxloader.LifecycleHandler;
import rx.functions.Action1;

class FavoritesPresenter extends BaseGifPresenter<FavoritesView> {

    FavoritesPresenter(LifecycleHandler lifecycleHandler, FavoritesView view) {
        super(lifecycleHandler, view);
    }

    @Override
    public void loadGifs() {
        GifProvider.provideGifRepository()
                .getFavorites()
                .doOnSubscribe(getGifView()::showLoading)
                .doOnTerminate(getGifView()::hideLoading)
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof HttpException){
                            HttpException httpException = (HttpException) throwable;
                            httpException.code();

                        }
                    }
                })
                .compose(mLifecycleHandler.load(R.id.favorites_request))
                .subscribe(favoritesResponse -> FavoritesPresenter.this.getGifView().showGifList(favoritesResponse.getFavorites()),
                        throwable -> FavoritesPresenter.this.getGifView().showErrorLoadingFeed());
    }

    public void addToFavoritesRequest(String gifId) {
        GifProvider.provideGifRepository()
                .addToFavorites(gifId)
                .compose(mLifecycleHandler.reload(R.id.add_to_favorites_request))
                .subscribe(aVoid -> getGifView().onSuccessFavoritesRequest());
    }

    public void deleteFromFavoritesRequest(String gifId) {
        GifProvider.provideGifRepository()
                .deleteFromFavorites(gifId)
                .compose(mLifecycleHandler.reload(R.id.add_to_favorites_request))
                .subscribe(aVoid -> getGifView().onSuccessFavoritesRequest());
    }

}
