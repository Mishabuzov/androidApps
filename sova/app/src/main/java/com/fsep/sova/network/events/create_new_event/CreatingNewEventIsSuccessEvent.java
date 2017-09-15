package com.fsep.sova.network.events.create_new_event;

import com.fsep.sova.models.Event;

public class CreatingNewEventIsSuccessEvent {
    private Event mEvent;

    public CreatingNewEventIsSuccessEvent(Event event) {
        mEvent = event;
    }

    public Event getEvent() {
        return mEvent;
    }
}
