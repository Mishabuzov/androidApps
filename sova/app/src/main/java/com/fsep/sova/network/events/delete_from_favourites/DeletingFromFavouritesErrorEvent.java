package com.fsep.sova.network.events.delete_from_favourites;

import com.fsep.sova.network.events.BaseErrorEvent;

public class DeletingFromFavouritesErrorEvent extends BaseErrorEvent {
    public DeletingFromFavouritesErrorEvent(int responseCode) {
        super(responseCode);
    }

    public DeletingFromFavouritesErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
