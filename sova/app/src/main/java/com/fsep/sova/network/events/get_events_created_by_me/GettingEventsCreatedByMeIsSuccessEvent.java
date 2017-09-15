package com.fsep.sova.network.events.get_events_created_by_me;

import com.fsep.sova.models.Event;

import java.util.List;

public class GettingEventsCreatedByMeIsSuccessEvent {
    private List<Event> mEvents;

    public GettingEventsCreatedByMeIsSuccessEvent(List<Event> events) {
        mEvents = events;
    }

    public List<Event> getEvents() {
        return mEvents;
    }
}
