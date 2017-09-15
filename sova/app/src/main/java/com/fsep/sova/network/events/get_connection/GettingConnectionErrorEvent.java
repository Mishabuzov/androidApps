package com.fsep.sova.network.events.get_connection;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingConnectionErrorEvent extends BaseErrorEvent {
    public GettingConnectionErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }

    public GettingConnectionErrorEvent(int responseCode) {
        super(responseCode);
    }
}
