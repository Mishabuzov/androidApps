package com.fsep.sova.network.events.get_event_by_id;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingEventByIdErrorEvent extends BaseErrorEvent {
    public GettingEventByIdErrorEvent(int responseCode) {
        super(responseCode);
    }

    public GettingEventByIdErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
