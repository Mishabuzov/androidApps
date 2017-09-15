package com.fsep.sova.network.events.add_response;

import com.fsep.sova.network.events.BaseErrorEvent;

public class AddingResponseErrorEvent extends BaseErrorEvent {
    public AddingResponseErrorEvent(int responseCode) {
        super(responseCode);
    }

    public AddingResponseErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
