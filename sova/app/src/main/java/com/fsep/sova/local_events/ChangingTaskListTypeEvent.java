package com.fsep.sova.local_events;

import com.fsep.sova.models.TaskListType;

public class ChangingTaskListTypeEvent {
    private TaskListType mListType;

    public ChangingTaskListTypeEvent(TaskListType listType) {
        mListType = listType;
    }

    public TaskListType getListType() {
        return mListType;
    }
}
