package com.fsep.sova.network.events.decline_task;

import com.fsep.sova.network.events.BaseErrorEvent;

public class DecliningTaskErrorEvent extends BaseErrorEvent {
    public DecliningTaskErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }

    public DecliningTaskErrorEvent(int responseCode) {
        super(responseCode);
    }
}
