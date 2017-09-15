package com.fsep.sova.local_events;

public class RefreshingCommentsEvent {

    private long mTaskId;
    private int mNewCommentsAmount;

    public RefreshingCommentsEvent(int newCommentsAmount) {
        mNewCommentsAmount = newCommentsAmount;
    }

    public RefreshingCommentsEvent(long taskId, int newCommentsAmount) {
        mTaskId = taskId;
        mNewCommentsAmount = newCommentsAmount;
    }

    public int getNewCommentsAmount() {
        return mNewCommentsAmount;
    }

    public long getTaskId() {
        return mTaskId;
    }
}
