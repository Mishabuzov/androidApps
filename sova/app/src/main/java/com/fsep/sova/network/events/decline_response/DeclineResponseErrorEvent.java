package com.fsep.sova.network.events.decline_response;

import com.fsep.sova.network.events.BaseErrorEvent;

public class DeclineResponseErrorEvent extends BaseErrorEvent {
    public DeclineResponseErrorEvent(int responseCode) {
        super(responseCode);
    }

    public DeclineResponseErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
