package com.fsep.sova.network.events.refresh_event;

import com.fsep.sova.models.Event;

public class RefreshingEventIsSuccessEvent {
    private Event mEvent;

    public RefreshingEventIsSuccessEvent(Event event) {
        mEvent = event;
    }

    public Event getEvent() {
        return mEvent;
    }
}
