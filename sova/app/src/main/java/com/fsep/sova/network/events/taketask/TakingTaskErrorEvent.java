package com.fsep.sova.network.events.taketask;

import com.fsep.sova.network.events.BaseErrorEvent;

public class TakingTaskErrorEvent extends BaseErrorEvent {
    public TakingTaskErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
