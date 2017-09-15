package com.fsep.sova.network.events.get_event_by_id;

import com.fsep.sova.models.Event;

public class GettingEventByIdIsSuccessEvent {
    private Event mEvent;

    public GettingEventByIdIsSuccessEvent(Event event) {
        mEvent = event;
    }

    public Event getEvent() {
        return mEvent;
    }
}
