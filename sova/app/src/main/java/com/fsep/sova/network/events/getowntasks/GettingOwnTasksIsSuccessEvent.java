package com.fsep.sova.network.events.getowntasks;

import java.util.List;

import com.fsep.sova.models.Task;

public class GettingOwnTasksIsSuccessEvent {

    List<Task> mTasks;

    public GettingOwnTasksIsSuccessEvent(List<Task> tasks) {
        mTasks = tasks;
    }

    public List<Task> getTasks() {
        return mTasks;
    }
}
