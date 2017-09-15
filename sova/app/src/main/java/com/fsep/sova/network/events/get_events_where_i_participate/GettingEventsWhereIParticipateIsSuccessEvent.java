package com.fsep.sova.network.events.get_events_where_i_participate;

import com.fsep.sova.models.Event;

import java.util.List;

public class GettingEventsWhereIParticipateIsSuccessEvent {
    private List<Event> mEvents;

    public GettingEventsWhereIParticipateIsSuccessEvent(List<Event> events) {
        mEvents = events;
    }

    public List<Event> getEvents() {
        return mEvents;
    }
}
