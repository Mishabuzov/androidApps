package com.fsep.sova.network.events.refresh_event;

import com.fsep.sova.network.events.BaseErrorEvent;

public class RefreshingEventErrorEvent extends BaseErrorEvent {
    public RefreshingEventErrorEvent(int responseCode) {
        super(responseCode);
    }

    public RefreshingEventErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
