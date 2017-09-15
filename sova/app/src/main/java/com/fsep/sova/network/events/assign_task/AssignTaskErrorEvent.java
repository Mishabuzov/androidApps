package com.fsep.sova.network.events.assign_task;

import com.fsep.sova.network.events.BaseErrorEvent;

public class AssignTaskErrorEvent extends BaseErrorEvent {
    public AssignTaskErrorEvent(int responseCode) {
        super(responseCode);
    }

    public AssignTaskErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
