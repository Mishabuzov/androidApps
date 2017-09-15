package com.fsep.sova.network.events.taketask;

import com.fsep.sova.models.Task;

public class TakingTaskIsSuccessEvent {

    private Task mTookTask;

    public TakingTaskIsSuccessEvent(Task tookTask) {
        mTookTask = tookTask;
    }

    public Task getTookTask() {
        return mTookTask;
    }

    public void setTookTask(Task tookTask) {
        mTookTask = tookTask;
    }
}
