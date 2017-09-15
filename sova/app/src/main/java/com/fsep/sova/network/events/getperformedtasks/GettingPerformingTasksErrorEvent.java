package com.fsep.sova.network.events.getperformedtasks;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingPerformingTasksErrorEvent extends BaseErrorEvent {

    public GettingPerformingTasksErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }

    public GettingPerformingTasksErrorEvent(int responseCode) {
        super(responseCode);
    }
}
