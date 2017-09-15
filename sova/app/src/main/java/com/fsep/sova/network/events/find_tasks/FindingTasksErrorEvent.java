package com.fsep.sova.network.events.find_tasks;

import com.fsep.sova.network.events.BaseErrorEvent;

public class FindingTasksErrorEvent extends BaseErrorEvent {
    public FindingTasksErrorEvent(int responseCode) {
        super(responseCode);
    }

    public FindingTasksErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
