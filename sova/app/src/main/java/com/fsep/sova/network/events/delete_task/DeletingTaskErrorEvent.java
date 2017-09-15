package com.fsep.sova.network.events.delete_task;

import com.fsep.sova.network.events.BaseErrorEvent;

public class DeletingTaskErrorEvent extends BaseErrorEvent{
    public DeletingTaskErrorEvent(int responseCode) {
        super(responseCode);
    }

    public DeletingTaskErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
