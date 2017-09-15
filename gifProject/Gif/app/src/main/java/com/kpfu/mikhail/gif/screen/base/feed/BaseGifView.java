package com.kpfu.mikhail.gif.screen.base.feed;

import com.kpfu.mikhail.gif.content.Gif;
import com.kpfu.mikhail.gif.content.GifInfo;
import com.kpfu.mikhail.gif.screen.general.LoadingView;

import java.util.List;

public interface BaseGifView extends LoadingView {

    void showErrorLoadingFeed();

    void showDefaultToastError();

    void showGifList(List<Gif> gifs);

    void onGifInfoLoaded(GifInfo gifInfo);

    void onErrorLikeRequest();

    void onErrorFavoritesRequest();

}
