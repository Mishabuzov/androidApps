package com.fsep.sova.network.events.get_responses;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingResponsesErrorEvent extends BaseErrorEvent {
    public GettingResponsesErrorEvent(int responseCode) {
        super(responseCode);
    }

    public GettingResponsesErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
