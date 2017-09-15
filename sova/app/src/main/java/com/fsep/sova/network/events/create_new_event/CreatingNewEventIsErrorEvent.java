package com.fsep.sova.network.events.create_new_event;

import com.fsep.sova.network.events.BaseErrorEvent;

public class CreatingNewEventIsErrorEvent extends BaseErrorEvent {
    public CreatingNewEventIsErrorEvent(int responseCode) {
        super(responseCode);
    }

    public CreatingNewEventIsErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
