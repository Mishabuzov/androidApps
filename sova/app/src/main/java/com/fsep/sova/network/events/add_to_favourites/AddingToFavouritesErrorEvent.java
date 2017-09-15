package com.fsep.sova.network.events.add_to_favourites;

import com.fsep.sova.network.events.BaseErrorEvent;

public class AddingToFavouritesErrorEvent extends BaseErrorEvent {
    public AddingToFavouritesErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }

    public AddingToFavouritesErrorEvent(int responseCode) {
        super(responseCode);
    }
}
