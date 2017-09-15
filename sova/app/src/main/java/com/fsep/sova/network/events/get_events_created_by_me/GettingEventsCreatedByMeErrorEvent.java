package com.fsep.sova.network.events.get_events_created_by_me;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingEventsCreatedByMeErrorEvent extends BaseErrorEvent {
    public GettingEventsCreatedByMeErrorEvent(int responseCode) {
        super(responseCode);
    }

    public GettingEventsCreatedByMeErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
