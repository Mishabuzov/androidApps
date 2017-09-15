package com.fsep.sova.network.events.updatetask;

import com.fsep.sova.models.Task;

public class UpdatingTaskIsSuccessEvent {
    private Task mTask;

    public UpdatingTaskIsSuccessEvent(Task task) {
        this.mTask = task;
    }

    public Task getTask() {
        return mTask;
    }
}
