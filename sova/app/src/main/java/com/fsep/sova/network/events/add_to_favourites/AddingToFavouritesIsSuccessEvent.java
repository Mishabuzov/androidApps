package com.fsep.sova.network.events.add_to_favourites;

public class AddingToFavouritesIsSuccessEvent {
    private long mId;

    public AddingToFavouritesIsSuccessEvent(long id) {
        mId = id;
    }

    public AddingToFavouritesIsSuccessEvent() {
    }

    public long getId() {
        return mId;
    }
}
