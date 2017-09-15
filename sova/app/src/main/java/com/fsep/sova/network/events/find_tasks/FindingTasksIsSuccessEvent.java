package com.fsep.sova.network.events.find_tasks;

import com.fsep.sova.models.Task;

import java.util.List;

public class FindingTasksIsSuccessEvent {
    private List<Task> mTasks;

    public FindingTasksIsSuccessEvent(List<Task> tasks) {
        mTasks = tasks;
    }

    public List<Task> getTasks() {
        return mTasks;
    }
}
