package com.fsep.sova.network.events.delete_from_favourites;

public class DeletingFromFavouritesIsSuccessEvent {
    private long mId;

    public DeletingFromFavouritesIsSuccessEvent(long id) {
        mId = id;
    }

    public DeletingFromFavouritesIsSuccessEvent() {
    }

    public long getId() {
        return mId;
    }
}
