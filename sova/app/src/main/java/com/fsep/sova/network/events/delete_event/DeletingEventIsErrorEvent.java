package com.fsep.sova.network.events.delete_event;

import com.fsep.sova.network.events.BaseErrorEvent;

public class DeletingEventIsErrorEvent extends BaseErrorEvent {
    public DeletingEventIsErrorEvent(int responseCode) {
        super(responseCode);
    }

    public DeletingEventIsErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
