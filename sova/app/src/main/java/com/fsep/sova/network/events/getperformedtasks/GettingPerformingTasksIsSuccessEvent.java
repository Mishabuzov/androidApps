package com.fsep.sova.network.events.getperformedtasks;

import com.fsep.sova.models.Task;

import java.util.List;

public class GettingPerformingTasksIsSuccessEvent {

    private List<Task> mTasks;

    public GettingPerformingTasksIsSuccessEvent(List<Task> tasks) {
        mTasks = tasks;
    }

    public List<Task> getTasks(){
        return mTasks;
    }
}
