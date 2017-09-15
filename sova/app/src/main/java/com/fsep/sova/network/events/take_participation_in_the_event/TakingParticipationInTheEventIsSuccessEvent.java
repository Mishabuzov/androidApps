package com.fsep.sova.network.events.take_participation_in_the_event;

import com.fsep.sova.models.Event;

public class TakingParticipationInTheEventIsSuccessEvent {
    private Event mMyEvent;

    public TakingParticipationInTheEventIsSuccessEvent(Event myEvent) {
        mMyEvent = myEvent;
    }

    public Event getMyEvent() {
        return mMyEvent;
    }
}
