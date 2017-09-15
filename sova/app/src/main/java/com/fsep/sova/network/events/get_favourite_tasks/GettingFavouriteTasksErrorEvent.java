package com.fsep.sova.network.events.get_favourite_tasks;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingFavouriteTasksErrorEvent extends BaseErrorEvent {
    public GettingFavouriteTasksErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }

    public GettingFavouriteTasksErrorEvent(int responseCode) {
        super(responseCode);
    }
}
