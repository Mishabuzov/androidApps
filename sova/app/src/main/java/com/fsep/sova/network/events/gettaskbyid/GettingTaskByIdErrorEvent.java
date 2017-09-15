package com.fsep.sova.network.events.gettaskbyid;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingTaskByIdErrorEvent extends BaseErrorEvent {
    public GettingTaskByIdErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
