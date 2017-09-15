package com.fsep.sova.network.events.createtask;

import com.fsep.sova.models.Task;

public class CreatingTaskIsSuccessEvent {

    private Task mTask;

    public CreatingTaskIsSuccessEvent(Task task) {
        mTask = task;
    }

    public Task getTask() {
        return mTask;
    }
}
