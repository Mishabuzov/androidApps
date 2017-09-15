package com.fsep.sova.local_events;

import com.fsep.sova.models.Note;

public class RefreshingDataEvent {

    private Note mNote;
    private boolean mIsDeletingFromFavouritesEvent;

    public RefreshingDataEvent(Note note) {
        mNote = note;
    }

    public Note getNote() {
        return mNote;
    }

    public boolean isDeletingFromFavouritesEvent() {
        return mIsDeletingFromFavouritesEvent;
    }

    public void setDeletingFromFavouritesEvent() {
        mIsDeletingFromFavouritesEvent = true;
    }
}

