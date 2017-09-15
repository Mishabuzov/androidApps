package com.kpfu.mikhail.gif.screen.favorites;

import com.kpfu.mikhail.gif.R;
import com.kpfu.mikhail.gif.screen.base.feed.BaseGifPresenter;
import com.kpfu.mikhail.gif.screen.base.feed.BaseRecyclerGifFragment;

import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

public class FavoritesFragment extends BaseRecyclerGifFragment implements FavoritesView {

    @Override
    public BaseGifPresenter initPresenter() {
        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(getContext(),
                getActivity().getSupportLoaderManager());
        return new FavoritesPresenter(lifecycleHandler, this);
    }

    @Override
    protected int setToolbarTitle() {
        return R.string.menu_favorites_item;
    }

    @Override
    public void onSuccessFavoritesRequest() {
        getGifAdapter().getItems().remove(getAdapterPosition());
        getGifAdapter().notifyItemRemoved(getAdapterPosition());
    }
}
