package com.fsep.sova.network.events.gettaskbyid;

import com.fsep.sova.models.Task;

public class GettingTaskByIdIsSuccess {

    private Task mTask;

    public GettingTaskByIdIsSuccess(Task task) {
        mTask = task;
    }

    public Task getTask() {
        return mTask;
    }
}
