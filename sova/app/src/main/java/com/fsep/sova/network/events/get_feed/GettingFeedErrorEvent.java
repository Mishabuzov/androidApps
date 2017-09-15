package com.fsep.sova.network.events.get_feed;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingFeedErrorEvent extends BaseErrorEvent {
    public GettingFeedErrorEvent(int responseCode) {
        super(responseCode);
    }

    public GettingFeedErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
